package com.we.service.article;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.asm.internal.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.model.geojson.Point;
import com.we.common.Constant.Const;
import com.we.common.Constant.OperationType;
import com.we.common.Constant.ReCode;
import com.we.common.Constant.Role;
import com.we.common.mongo.DboUtil;
import com.we.common.mongo.Op;
import com.we.common.utils.DateUtil;
import com.we.common.utils.HtmlUtil;
import com.we.common.utils.MapUtil;
import com.we.core.Page;
import com.we.core.web.ReMsg;
import com.we.models.DocName;
import com.we.models.User;
import com.we.models.article.Article;
import com.we.models.article.Group;
import com.we.models.finance.CoinLog;
import com.we.service.BaseService;
import com.we.service.UserService;
import com.we.service.userTask.UserTaskService;

@Service
public class ArticleService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(ArticleService.class);

	@Autowired
	UserService userService;

	@Autowired
	ArticleShareService articleShareService;

	@Autowired
	ArticleReadService articleReadService;

	@Autowired
	ArticleCommentService articleCommentService;

	@Autowired
	ArticlePraiseService articlePraiseService;

	@Autowired
	ArticleTopicService articleTopicService;

	@Autowired
	UserTaskService userTaskService;

	@Autowired
	ArticleGroupService groupService;

	public static final Map<String, Integer> TYPES = new HashMap() {
		{
			put("nomal", 1);
			put("draw", 2);
			put("voice", 3);
		}
	};

	public static final int[] NUM = new int[] { 1, 2, 5, 10, 15, 20 };// 回帖次数
	public static final int TYPE_HOT = 1;
	public static final int TYPE_NEW = 2;
	public static final int SIZE = 3;
	public static final int FRIEND_COMMENTSIZE = 6;

	public DBObject findById(Long id) {
		DBObject dbo = super.findById(DocName.ARTICLE, id);
		addUserInfo(false, dbo, false);
		return dbo;
	}

	///// --------------------new start

	// 获取某些用户的文章
	public ReMsg getUsersArticles(Long[] uids, Integer size, Integer page) {

		size = super.getSize(size);
		page = super.getPage(page);
		DBObject q = new BasicDBObject("userId", new BasicDBObject("$in", uids))
				.append("status", new BasicDBObject("$gt", Article.DOWN))
				.append("publishTime", new BasicDBObject("$lt", System.currentTimeMillis()));
		DBCursor dbc = getC(DocName.ARTICLE).find(q);
		int count = dbc.count();
		List<DBObject> dbos = dbc.sort(new BasicDBObject("publishTime", -1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
		List<DBObject> result = new ArrayList<DBObject>();
		for (DBObject dbo : dbos) {
			dbo = addUserInfo(false, dbo, false);
			// 六条评论
			if (dbo != null) {
				dbo.put("comments", articleCommentService.query(null, DboUtil.getLong(dbo, "_id"), Const.STATUS_OK, 1,
						FRIEND_COMMENTSIZE, null));
				result.add(dbo);
			}
		}
		return new ReMsg(ReCode.OK, size, count, result);
	}

	// 获取用户自己的文章
	public ReMsg getMyArticles(Integer size, Integer page) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.AUTHCODE_ERR);
		}
		DBObject q = new BasicDBObject("userId", uid).append("status", new BasicDBObject("$gt", Article.DEL));
		DBCursor dbc = getC(DocName.ARTICLE).find(q);
		int count = dbc.count();
		List<DBObject> dbos = dbc.sort(new BasicDBObject("publishTime", -1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
		// TODO 当前版本需要，以后可去掉
		for (DBObject dbo : dbos) {
			addUserInfo(false, dbo, false);
		}
		return new ReMsg(ReCode.OK, size, count, dbos);
	}

	// 获取某个用户的文章
	public ReMsg getUserArticles(Long uid, Integer size, Integer page) {
		size = super.getSize(size);
		page = super.getPage(page);
		DBObject q = new BasicDBObject("userId", uid).append("status", new BasicDBObject("$gt", Article.DOWN))
				.append("publishTime", new BasicDBObject("$lt", System.currentTimeMillis()));
		DBCursor dbc = getC(DocName.ARTICLE).find(q);
		int count = dbc.count();
		List<DBObject> dbos = dbc.sort(new BasicDBObject("publishTime", -1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
		List<DBObject> result = new ArrayList<DBObject>();
		for (DBObject dbo : dbos) {
			dbo = addUserInfo(false, dbo, false);
			if (dbo != null) {
				// 六条评论
				dbo.put("comments", articleCommentService.query(null, DboUtil.getLong(dbo, "_id"), Const.STATUS_OK, 1,
						FRIEND_COMMENTSIZE, null));
				result.add(dbo);
			}
		}
		return new ReMsg(ReCode.OK, size, count, result);
	}

	// 用户最新三张图
	public ReMsg getUserArticlePics(Long uid) {
		DBObject q = new BasicDBObject("userId", uid).append("status", new BasicDBObject("$gt", Article.DOWN))
				.append("type", Article.TYPE_IMG)
				.append("publishTime", new BasicDBObject("$lt", System.currentTimeMillis()));
		List<DBObject> dbos = getC(DocName.ARTICLE).find(q).sort(new BasicDBObject("_id", -1)).limit(3).toArray();
		List<String> pics = new ArrayList<String>();
		int i = 0;
		for (DBObject dbo : dbos) {
			ArrayList<String> arrPic = DboUtil.get(dbo, "pics", ArrayList.class);
			for (String pic : arrPic) {
				pics.add(pic);
				i++;
				if (i == 3) {
					break;
				}
			}
			if (i == 3) {
				break;
			}
		}
		return new ReMsg(ReCode.OK, pics);
	}
	
	public Page<DBObject> queryArticles(Long groupId, Integer page, Integer size, Long topicId, Integer from){
		return queryArticles(groupId, page, size, topicId, from ,null);
	}

	// type:1最热，2最新
	public Page<DBObject> queryArticles(Long groupId, Integer page, Integer size, Long topicId, Integer from ,Integer ver) {
		if (null == groupId) {
			groupId = Group.SYSTEM_GROUP;
		}
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = null;
		int count = 0;
		BasicDBObject q = new BasicDBObject("groupId", groupId).append("pri", Article.PRI_ALL)
				.append("status", new BasicDBObject("$gt", Article.DOWN))
				.append("publishTime", new BasicDBObject("$lt", System.currentTimeMillis()));
		if(ver==null) {
			q.append("source",new BasicDBObject("$ne",1));
		}
		if (null != from && from == 1) {
			q.append("hotTime", new BasicDBObject("$gt", 0));
			DBCursor dbc = getC(DocName.ARTICLE).find(q);
			count = dbc.count();
			dbos = dbc.sort(new BasicDBObject("hotTime", -1)).skip(getStart(page, size)).limit(getSize(size)).toArray();
		} else {
			DBCursor dbc = getC(DocName.ARTICLE).find(q);
			count = dbc.count();
			dbos = dbc.sort(new BasicDBObject("publishTime", -1)).skip(getStart(page, size)).limit(getSize(size))
					.toArray();
		}
		// List<DBObject> result = new ArrayList<DBObject>();
		// for (DBObject dbo : dbos) {
		// dbo = addUserInfo(false, dbo, true);
		// if (dbo != null) {
		// // 添加三条最热评论
		// dbo.put("comments", articleCommentService.getTopComments(DboUtil.getLong(dbo,
		// "_id"), SIZE));
		// result.add(dbo);
		// }
		// }
		return new Page<DBObject>(count, size, page, dbos);
	}

	/////// ----------------end new

	///// -------------------------old start
	/**
	 * 用户后台的查询
	 * 
	 * @param groupId
	 * @param userId
	 * @param type
	 * @param status
	 * @param essence
	 * @param audited
	 * @param page
	 * @param size
	 * @param isAdmin
	 * @param publishTime
	 * @param topicId
	 * @param op
	 * @param from
	 * @return
	 */
	public Page<DBObject> query(Long groupId, Long userId, Integer type, Integer status, Integer essence,
			Integer audited, Integer page, Integer size, boolean isAdmin, Long publishTime, Long topicId, Op op,
			Integer from, Integer pri) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = null;
		// 如果是查询最新或者最热 调用新的find 排序不同
		// if (null != from && from != 0) {
		dbos = findNew(groupId, userId, type, status, essence, audited, page, size, isAdmin, publishTime, topicId, op,
				from, pri);
		// } else {
		// // 走原来的方法
		// dbos = find(groupId, userId, type, status, essence, audited, page,
		// size, isAdmin, publishTime, topicId, op,
		// from);
		// }
		int count = count(groupId, userId, type, status, essence, audited, publishTime, topicId, op, from, pri);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public Integer count(Long groupId, Long userId, Integer type, Integer status, Integer essence, Integer audited,
			Long publishTime, Long topicId, Op op, Integer from, Integer pri) {
		DBObject q = getQuery(groupId, userId, type, status, essence, audited, publishTime, topicId, op, from, pri);
		return getC(DocName.ARTICLE).find(q).count();
	}

	public List<DBObject> findNew(Long groupId, Long userId, Integer type, Integer status, Integer essence,
			Integer audited, Integer page, Integer size, boolean isAdmin, Long publishTime, Long topicId, Op op,
			Integer from, Integer pri) {
		DBObject q = getQuery(groupId, userId, type, status, essence, audited, publishTime, topicId, op, from, pri);
		List<DBObject> dbos = new ArrayList<DBObject>();
		if (from != null && from == TYPE_HOT) {
			// 热度排序
			dbos = getC(DocName.ARTICLE).find(q).sort(new BasicDBObject("hotTime", -1)).skip(getStart(page, size))
					.limit(getSize(size)).toArray();
		} else {
			// 最新排序
			dbos = getC(DocName.ARTICLE).find(q).sort(new BasicDBObject("publishTime", -1)).skip(getStart(page, size))
					.limit(getSize(size)).toArray();
		}
		List<DBObject> rs = new ArrayList<DBObject>();
		for (DBObject dbo : dbos) {
			dbo = addUserInfo(isAdmin, dbo, true);
			if (dbo != null) {
				// 添加三条最热评论
				dbo.put("comments", articleCommentService.getTopComments(DboUtil.getLong(dbo, "_id"), SIZE));
				rs.add(dbo);
			}
		}
		return rs;
	}

	public DBObject getQuery(Long groupId, Long userId, Integer type, Integer status, Integer essence, Integer audited,
			Long publishTime, Long topicId, Op op, Integer from, Integer pri) {
		DBObject q = new BasicDBObject();
		if (userId != null && userId != 0) {
			q.put("userId", userId);
		}
		if (type != null && type != 0) {
			q.put("type", type);
		}
		if (status != null) {
			if (op != null) {
				q = op.getOp(q, "status", status);
			} else if (status != 0) {
				q.put("status", status);
			}
		}
		if (essence != null && essence != 0) {
			q.put("essence", essence);
		}
		if (audited != null && audited != 0) {
			q.put("audited", audited);
		}
		if (groupId != null && groupId != 0) {
			q.put("groupId", groupId);
		}
		if (publishTime != null && publishTime != 0) {
			q.put("publishTime", new BasicDBObject("$lt", publishTime));
		}
		if (topicId != null && topicId != 0) {
			q.put("topicId", topicId);
		}
		// 设置热度限制
		if (from != null && from == TYPE_HOT) {
			q.put("hotTime", new BasicDBObject("$gt", 0));
		}
		if (pri != null && pri != 0) {
			q.put("pri", pri);
		}
		return q;
	}

	///// -------------------------old end
	public DBObject addUserInfo(boolean isAdmin, DBObject dbo, boolean isList) {
		DBObject user = userService.findById(DboUtil.getLong(dbo, "userId"));
		if (isAdmin) {
			dbo.put("nickname", DboUtil.getString(user, "nickname"));
			dbo.put("avatar", DboUtil.getString(user, "avatar"));
			dbo.put("sex", DboUtil.getString(user, "sex"));
			dbo.put("point", DboUtil.getString(user, "point"));
			dbo.put("vip", DboUtil.getString(user, "vip"));
			// rs.add(dbo);
		} else {
			if (user != null) {
				Integer userStatus = DboUtil.getInt(user, "status");
				if (userStatus != null && userStatus == User.STATUS_OK) {
					dbo.put("nickname", DboUtil.getString(user, "nickname"));
					dbo.put("avatar", DboUtil.getString(user, "avatar"));
					dbo.put("sex", DboUtil.getString(user, "sex"));
					dbo.put("point", DboUtil.getString(user, "point"));
					dbo.put("vip", DboUtil.getString(user, "vip"));
					dbo.put("role", DboUtil.getString(user, "role"));
					if (isList) {
						// System.out.println(2 + " isList:" + isList);
						Integer godComment = DboUtil.getInt(dbo, "godComment");
						if (godComment != null && godComment == Article.GOD_COMMENT_YES) {

							DBObject godUser = userService.findById(DboUtil.getLong(dbo, "godUid"));
							// System.out.println(3 + " godUser is " + godUser);
							if (godUser != null && DboUtil.getInt(godUser, "status") == User.STATUS_OK) {
								// System.out.println(4);
								dbo.put("godNickname", DboUtil.getString(godUser, "nickname"));
								dbo.put("godAvatar", DboUtil.getString(godUser, "avatar"));
								dbo.put("godSex", DboUtil.getString(godUser, "sex"));
								dbo.put("godPoint", DboUtil.getString(godUser, "point"));
								dbo.put("godVip", DboUtil.getString(godUser, "vip"));
							} else {
								dbo.put("godUid", Article.GOD_COMMENT_NO);
							}
						}
					}
					// rs.add(dbo);
				} else {
					return null;
				}
			}
		}
		return dbo;
	}

	public DBObject readArticle(Long aid) {
		DBObject dbo = findById(aid);
		if (null != dbo) {
			DBObject user = userService.findById(DboUtil.getLong(dbo, "userId"));
			dbo.put("id", DboUtil.getString(user, "_id"));
			dbo.put("nickname", DboUtil.getString(user, "nickname"));
			dbo.put("avatar", DboUtil.getString(user, "avatar"));
			dbo.put("sex", DboUtil.getString(user, "sex"));
			dbo.put("point", DboUtil.getString(user, "point"));
			dbo.put("vip", DboUtil.getString(user, "vip"));
			ReCode r = articleReadService.saveReadLog(aid);
			if (r.reCode() == ReCode.OK.reCode()) {
				int readCnt = DboUtil.getInt(dbo, "readCnt");
				double sort = complateSort(SORT_READ, (long) readCnt + 1);
				DBObject uo = new BasicDBObject("$inc",
						new BasicDBObject("readCnt", Integer.valueOf(1)).append("sort", sort)).append("$set",
								new BasicDBObject("updateTime", System.currentTimeMillis()));
				getC(DocName.ARTICLE).update(new BasicDBObject("_id", aid), uo);
			}
		}
		return dbo;
	}

	public ReMsg praiseArticle(Long aid, int type) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject dbo = findById(aid);
		if (null == dbo)
			return new ReMsg(ReCode.FAIL);
		ReCode r = articlePraiseService.saveLog(aid, type);
		if (r.reCode() == ReCode.OK.reCode()) {
			if (type == 1) {
				int praiseCnt = DboUtil.getInt(dbo, "praiseCnt");
				double sort = complateSort(SORT_PRAISE, (long) praiseCnt + 1);
				DBObject uo = new BasicDBObject("$inc",
						new BasicDBObject("praiseCnt", Integer.valueOf(1)).append("sort", sort)).append("$set",
								new BasicDBObject("updateTime", System.currentTimeMillis()));
				getC(DocName.ARTICLE).update(new BasicDBObject("_id", aid), uo);
				// 刷新热度
				upHot(dbo, 0);
			} else {
				int praiseCnt = DboUtil.getInt(dbo, "hitCnt");
				double sort = complateSort(SORT_PRAISE, (long) praiseCnt + 1);
				DBObject uo = new BasicDBObject("$inc",
						new BasicDBObject("hitCnt", Integer.valueOf(1)).append("sort", -1 * sort)).append("$set",
								new BasicDBObject("updateTime", System.currentTimeMillis()));
				getC(DocName.ARTICLE).update(new BasicDBObject("_id", aid), uo);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	public ReMsg shareArticle(Long aid, String platform) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject dbo = findById(aid);
		if (null == dbo)
			return new ReMsg(ReCode.FAIL);
		ReCode r = articleShareService.saveLog(aid, platform);
		if (r.reCode() == ReCode.OK.reCode()) {
			int shareCnt = DboUtil.getInt(dbo, "shareCnt");
			double sort = complateSort(SORT_SHARE, (long) shareCnt + 1);
			DBObject uo = new BasicDBObject("$inc",
					new BasicDBObject("shareCnt", Integer.valueOf(1)).append("sort", sort)).append("$set",
							new BasicDBObject("updateTime", System.currentTimeMillis()));
			getC(DocName.ARTICLE).update(new BasicDBObject("_id", aid), uo);
			int count = getTodayShareCount(uid);
			if (count == 0 || count == 1 || count == 2 || count == 4 || count == 7 || count == 11 || count == 19) {
				// userService.changePoint(uid, Point.SHARE_ARTICLE, 0L);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	private int getTodayShareCount(Long uid) {
		DBObject q = new BasicDBObject("updateTime", new BasicDBObject("$gt", DateUtil.getTodayZeroTime()))
				.append("userId", uid);
		return getC(DocName.ARTICLE_SHARE_LOG).find(q).count();
	}

	public ReCode robitArticle() {
		//
		List<DBObject> dbos = this.findNew(0L, 0L, 0, Article.DOWN, 0, Article.AUDITED_Y, 0, 60, true, 0L, 0L, Op.GT,
				null, null);
		// .find(0L, 0L, 0, Article.DOWN, 0, Article.AUDITED_Y, 0, 60, true, 0L,
		// 0L, Op.GT,
		// null);
		Random r = ThreadLocalRandom.current();
		for (DBObject dbo : dbos) {
			Long aid = DboUtil.getLong(dbo, "_id");
			int praiseCnt = r.nextInt(3);
			BasicDBObject up = new BasicDBObject("praiseCnt", praiseCnt);
			if (r.nextInt(1000) < 3) {
				int hitCnt = r.nextInt(2);
				if (hitCnt > 0) {
					up.append("hitCnt", hitCnt);
				}
			}
			if (r.nextInt(1000) < 5) {
				int shareCnt = r.nextInt(2);
				if (shareCnt > 0)
					up.append("shareCnt", shareCnt);
			}
			DBObject uo = new BasicDBObject("$inc", up);
			getC(DocName.ARTICLE).update(new BasicDBObject("_id", aid), uo);
		}
		return ReCode.OK;
	}

	public ReMsg commentArticle(Long aid, String content, String[] atUsers, String[] pics, List<Map> draws,
			String voiceUrl, HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return commentArticle(aid, uid, content, null, atUsers, pics, draws, voiceUrl, req);
	}

	public ReMsg commentArticle(Long aid, Long uid, String content, String nickname, String[] atUsers, String[] pics,
			List<Map> draws, String voiceUrl, HttpServletRequest req) {
		DBObject dbo = findById(aid);
		if (null == dbo)
			return new ReMsg(ReCode.FAIL);
		if (nickname == null) {
			nickname = super.getUser("nickname") != null ? super.getUser("nickname").toString() : null;
		}
		if (null == content) {// 判空
			content = "";
		}
		ReCode r = articleCommentService.saveComment(aid, uid, content, nickname, atUsers, pics, draws, voiceUrl);
		if (r.reCode() == ReCode.OK.reCode()) {
			DBObject user = userService.findById(uid);
			Long author = DboUtil.getLong(dbo, "userId");
			int comCnt = DboUtil.getInt(dbo, "commentCnt");
			double sort = complateSort(SORT_COMMENT, (long) comCnt + 1);
			DBObject uo = new BasicDBObject("$inc",
					new BasicDBObject("commentCnt", Integer.valueOf(1)).append("sort", sort)).append("$set",
							new BasicDBObject("updateTime", System.currentTimeMillis()));
			getC(DocName.ARTICLE).update(new BasicDBObject("_id", aid), uo);// 修改文章权重
			// 刷新文章热度
			upHot(dbo, 0);
			return new ReMsg(r, this.commentArticle(uid, aid, DboUtil.getLong(dbo, "userId"), req));
		}
		return new ReMsg(r);
	}

	private Page<?> commentArticle(Long uid, Long aid, Long long1, HttpServletRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	public ReMsg commentReply(Long aid, Long cid, String content, String[] atUsers) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return commentReply(aid, cid, uid, content, null, atUsers);
	}

	public ReMsg commentReply(Long aid, Long cid, Long uid, String content, String nickname, String[] atUsers) {
		DBObject dbo = findById(aid);
		if (null == dbo)
			return new ReMsg(ReCode.FAIL);
		ReCode r = null;
		if (nickname != null) {
			r = articleCommentService.replyComment(aid, cid, uid, nickname, content, atUsers);
		} else {
			r = articleCommentService.replyComment(aid, cid, uid, content, atUsers);
		}
		if (r.reCode() == ReCode.OK.reCode()) {
			int comCnt = DboUtil.getInt(dbo, "commentCnt");
			double sort = complateSort(SORT_COMMENT, (long) comCnt + 1);
			DBObject uo = new BasicDBObject("$inc",
					new BasicDBObject("commentCnt", Integer.valueOf(1)).append("sort", sort)).append("$set",
							new BasicDBObject("updateTime", System.currentTimeMillis()));
			getC(DocName.ARTICLE).update(new BasicDBObject("_id", aid), uo);
		}
		return new ReMsg(r);
	}

	public ReMsg updateArticle(Long aid, String title, String content, String[] tags, Integer status, Integer essence,
			Integer audited, String[] pics, String video, Long publishTime, String topic, Integer userType,
			String[] atUsers, HttpServletRequest request, List<Map> draws, String voiceUrl, Long groupId) {
		long uid = 0;
		if (userType == 1) {
			long curT = System.currentTimeMillis();
			if (users == null || curT - lastTime > 1000 * 60 * 10) {
				users = userService.findUser(null, Const.STATUS_DEF, Role.ROBOT.getRole(), null, 0, 1000);
			}
			int size = users.size();
			// System.out.println(size);
			if (size == 0) {
				uid = super.getUid();
			} else {
				Random random = ThreadLocalRandom.current();
				DBObject user = users.get(random.nextInt(size));
				uid = DboUtil.getLong(user, "_id");
			}
		}
		double sort = 0;
		DBObject a = findById(aid);
		if (null == a) {
			return new ReMsg(ReCode.FAIL);
		}

		String cover = getCover(pics);
		int type = getType(video, pics, title, draws, voiceUrl);
		if (StringUtils.isBlank(video)) {
			video = null;
		}

		long curT = System.currentTimeMillis();

		DBObject u = new BasicDBObject("updateTime", curT);
		long adminId = super.getUid();
		if (adminId != DboUtil.getLong(a, "userId")) {
			u.put("adminId", adminId);
		}
		if (uid != 0) {
			u.put("userId", uid);
		}
//		if (StringUtils.isNotBlank(video)) {
//			u.put("video", video);
//		}
//		if (pics != null && pics.length > 0) {
//			u.put("pics", pics);
//		}
//		if (atUsers != null && atUsers.length > 0) {
//			u.put("atUsers", atUsers);
//		}
//		if (draws != null && draws.size() > 0) {
//			u.put("draws", draws);
//		}
//		if (StringUtils.isNotBlank(voiceUrl)) {
//			u.put("voiceUrl", voiceUrl);
//		}
		if (StringUtils.isNotBlank(title)) {
			u.put("title", title);
		}
		if (StringUtils.isNotBlank(content)) {
			u.put("content", content);
			String summary = HtmlUtil.removeP(content);
			if (summary.length() > 50) {
				summary = summary.substring(0, 50);
			}
			u.put("summary", summary);
		}
		if (StringUtils.isBlank(cover)) {
			u.put("cover", "");
		}else {
			u.put("cover", cover);
		}
		if (tags != null && tags.length > 0) {
			u.put("tags", tags);
		}
		if (type != 0) {
			u.put("type", type);
		}
		if (publishTime != null && publishTime > 0) {
			u.put("publishTime", publishTime);
		}
		if (groupId == null || groupId < Group.SYSTEM_GROUP) {
			groupId = Group.SYSTEM_GROUP;
		}
		u.put("groupId", groupId);
		// String curTopic = DboUtil.getString(a, "topic");
		// if (curTopic != null && StringUtils.isBlank(topic)) {
		// u.put("topicId", null);
		// u.put("topic", null);
		// } else
		if (StringUtils.isNotBlank(topic)) {
			Long topicId = null;
			topic = getTopic(topic);
			if (topic != null) {
				topicId = articleTopicService.getTopicId(topic, groupId);
			}
			u.put("topicId", topicId);
			u.put("topic", topic);
		}
		boolean isEssence = false;
		if (essence != 0) {
			if (DboUtil.getInt(a, "essence") == Article.ESSENCE_Y) {
				if (essence == Article.ESSENCE_N) {
					sort = sort - complateSort(SORT_ESSENCE, 1L);
				}
			} else {
				if (essence == Article.AUDITED_Y) {
					sort = sort + complateSort(SORT_ESSENCE, 1L);
					isEssence = true;
				}
			}
			u.put("essence", essence);
		}
		if (audited != 0) {
			u.put("audited", audited);
		}

		if (publishTime != null && publishTime > curT) {
			u.put("sort", complateSort(SORT_CREATE, publishTime) + sort);
		} else {
			u.put("sort", DboUtil.getDouble(a, "sort") + sort);
		}
		if (status != 0) {
			u.put("status", status);
		}
		Long userId = (Long) getC(DocName.ARTICLE)
				.findAndModify(new BasicDBObject("_id", aid), new BasicDBObject("$set", u)).get("userId");
		return new ReMsg(ReCode.OK);
	}

	public ReMsg addWeight(long id, double weight) {
		DBObject uo = new BasicDBObject("$inc", new BasicDBObject("sort", weight)).append("$set",
				new BasicDBObject("updateTime", System.currentTimeMillis()));
		getC(DocName.ARTICLE).update(new BasicDBObject("_id", id), uo);
		return new ReMsg(ReCode.OK);
	}

	public ReCode updateGodArticle(Long aid, Long godUid, Long cid, String content, int count, List list) {
		DBObject a = findById(aid);
		if (a != null) {
			DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
			Long curCid = DboUtil.getLong(a, "godCid");
			Integer curCount = DboUtil.getInt(a, "godCount");

			if (curCid == cid) {
				u.put("godCount", count);
				getC(DocName.ARTICLE).update(new BasicDBObject("_id", aid), new BasicDBObject("$set", u));
			} else {
				if (curCount == null || count > curCount) {
					u.put("godUid", godUid);
					u.put("godCount", count);
					u.put("godContent", content);
					u.put("godCid", cid);
					u.put("godComment", 2);
					if (null != list && list.size() != 0) {
						u.put("godPics", list);
					}
					getC(DocName.ARTICLE).update(new BasicDBObject("_id", aid), new BasicDBObject("$set", u));
				}
			}
			// }

		}
		return ReCode.OK;
	}

	long lastTime = 0L;
	List<DBObject> users = null;

	public ReMsg saveArticle(String title, String content, String[] tags, Integer status, Integer essence,
			Integer audited, Long adminId, Long groupId, String[] pics, String video, int anonymous, Long topicId,
			String topic, Long publishTime, Integer userType, Long curUid, String[] atUsers, HttpServletRequest request,
			List<Map> draws, String voiceUrl, Integer pri) {
		long uid = 0;
		if (userType == 3) {
			if (curUid == null || curUid < 1) {
				return new ReMsg(ReCode.FAIL);
			}
			uid = curUid;
			DBObject dbo = userService.findById(uid);
			if (dbo == null) {
				return new ReMsg(ReCode.FAIL);
			}
		} else if (userType == 2) {
			uid = super.getUid();
		} else {
			long curT = System.currentTimeMillis();
			if (users == null || curT - lastTime > 1000 * 60 * 10) {
				users = userService.findUser(null, Const.STATUS_DEF, Role.ROBOT.getRole(), null, 0, 1000);
			}
			int size = users.size();
			// System.out.println(size);
			if (size == 0) {
				uid = super.getUid();
			} else {
				Random random = ThreadLocalRandom.current();
				DBObject user = users.get(random.nextInt(size));
				uid = DboUtil.getLong(user, "_id");
			}
		}
		long id = super.getNextId(DocName.ARTICLE);
		String cover = getCover(pics);
		int type = getType(video, pics, title, draws, voiceUrl);
		if (StringUtils.isBlank(title)) {
			title = null;
		}
		if (StringUtils.isBlank(video)) {
			video = null;
		}
		double sort = 0d;
		long curT = System.currentTimeMillis();
		if (publishTime != null && publishTime > curT) {
			curT = publishTime;
		}
		if (status == Article.OK || status == Article.PERSONAL)
			sort = complateSort(SORT_CREATE, curT);
		if (essence != null && essence == Article.ESSENCE_Y) {
			sort = sort + complateSort(SORT_ESSENCE, curT);
		}
		if (groupId == null || groupId < Group.SYSTEM_GROUP) {
			groupId = Group.SYSTEM_GROUP;
		}
		if (topicId == null || topicId < 1) {
			topic = getTopic(topic);
			if (topic != null) {
				topicId = articleTopicService.getTopicId(topic, groupId);
			}
		}
		if (pri == null) {
			pri = Article.PRI_ALL;
		}
		String summary = null;
		if (StringUtils.isNotBlank(content)) {
			summary = HtmlUtil.removeP(content);
			if (summary.length() > 50) {
				summary = summary.substring(0, 50);
			}
		}
		Article a = new Article(id, type, title, content, cover, uid, tags, status, essence, audited, sort, adminId,
				groupId, anonymous, topicId, topic, curT, summary, pri);
		getMongoTemplate().save(a);
		return new ReMsg(ReCode.OK, a);
	}

	// @TaskAspect(CRICLEPUBLISH)
	public ReMsg userPublishArticle(String content, Long groupId, String[] pics, String video, int anonymous,
			Long topicId, String topic, String[] atUsers, HttpServletRequest request, ArrayList<Map> draws,
			String voiceUrl, Integer pri) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (groupId == null || groupId == 0) {
			groupId = Group.SYSTEM_GROUP;
		}
		if (null != pics && pics.length > 9) {
			// return new ReMsg(ReCode.TOO_MANY_PIC);
		}
		long id = super.getNextId(DocName.ARTICLE);
		double sort = complateSort(SORT_CREATE, System.currentTimeMillis());
		String title = null;
		String cover = getCover(pics);
		int type = getType(video, pics, title, draws, voiceUrl);
		if (StringUtils.isBlank(title)) {
			title = null;
		}
		if (StringUtils.isNotBlank(video)) {
			video = null;
		}
		if ((topicId == null || topicId < 1)) {
			topic = getTopic(topic);
			if (topic != null) {
				topicId = articleTopicService.getTopicId(topic, groupId);
			}
		}
		if (pri == null) {
			pri = Article.PRI_ALL;
		}
		String summary = null;
		if (StringUtils.isNotBlank(content)) {
			summary = HtmlUtil.removeP(content);
			if (summary.length() > 50) {
				summary = summary.substring(0, 50);
			}
		}

		// 发表文章默认是发表person文章 经审核后变为精选
		Article a = new Article(id, type, title, content, cover, uid, null, Article.PERSONAL, Article.ESSENCE_N,
				Article.AUDITED_N, sort, 0L, groupId, anonymous, topicId, topic, null, summary, pri);
		getMongoTemplate().save(a);
		// 发送提醒
		if (null != atUsers && atUsers.length > 0) {
			String nickname = null;
			try {
				nickname = DboUtil.getString(userService.findById(uid), "nickname");
			} catch (Exception e) {
				log.error("查询@用户昵称失败", e);
			}
			nickname = null == nickname ? "有人" : nickname;
			for (int i = 0; i < atUsers.length; i++) {
				// MapBody mb = new MapBody("op", OperationType.ARTICLE.getOp()).append("opId",
				// id).append("content",
				// nickname + "在圈子中提到了你");
				// Msg msg = new Msg(super.incrMsgId(), MsgType.AT_USER, 0,
				// Long.parseLong(atUsers[i]),
				// System.currentTimeMillis(), mb.getData());
				// messageService.msgHandler(msg);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	private String getTopic(String topic) {
		if (StringUtils.isBlank(topic)) {
			return null;
		}
		topic = topic.trim();
		if ("##".equals(topic)) {
			return null;
		}

		if (topic.charAt(0) == '#') {
			topic = topic.substring(1);
		}
		if (topic.charAt(topic.length() - 1) == '#') {
			topic = topic.substring(0, topic.length() - 2);
		}
		if (StringUtils.isBlank(topic))
			return null;
		return topic;
	}

	private int getTodayPublishCount(Long uid) {
		DBObject q = new BasicDBObject("publishTime", new BasicDBObject("$gt", DateUtil.getTodayZeroTime()))
				.append("userId", uid);
		return getC(DocName.ARTICLE).find(q).count();
	}

	private static int getType(String video, String[] pics, String title, List<Map> draws, String voiceUrl) {
		int type = Article.TYPE_TXT;
		if (StringUtils.isNotBlank(title)) {
			type = Article.TYPE_MULT;
		} else if (StringUtils.isNotBlank(video)) {
			type = Article.TYPE_VIDEO;
		} else if (voiceUrl != null && voiceUrl.length() > 0) {
			type = Article.TYPE_VOICE;
		} else if (pics != null && pics.length > 0) {
			type = Article.TYPE_IMG;
		} else if (draws != null && draws.size() > 0) {
			type = Article.TYPE_DRAW;
		}
		return type;
	}

	private static String getCover(String[] pics) {
		if (pics != null && pics.length > 0) {
			return pics[0];
		}
		return null;
	}

	private static final double UNIT = 60.0;// 秒

	private static final int SORT_CREATE = 1;// 1 创建
	private static final int SORT_ESSENCE = 2;// 2 加精
	private static final int SORT_COMMENT = 3;// 3 评论
	private static final int SORT_SHARE = 4;// 4 分享
	private static final int SORT_PRAISE = 5;// 5 点赞
	private static final int SORT_READ = 6;// 6 阅读

	private static double complateSort(int type, Long num) {
		double ln = 0d;
		if (num <= 1) {
			ln = Math.log(1.5);
		} else {
			ln = Math.log(num) - Math.log(num - 1);
		}
		if (type > SORT_ESSENCE) {
			return ln * UNIT * (7 - type);
		} else if (type == SORT_ESSENCE) {
			return 60d * 60 * 12;
		} else if (type == SORT_CREATE) {// 发布
			return 1d * num / 1000;
		}
		return 0d;
	}

	// // 更新文章收花数
	// public void updateArtF(long localId, long count) {
	// getMongoTemplate().getCollection(DocName.ARTICLE).findAndModify(new
	// BasicDBObject("_id", localId), null, null,
	// false, new BasicDBObject("$inc", new BasicDBObject("flower", count)),
	// true, true);
	// }

	// 热度每10一刷新
	public void upHot(DBObject article, int count) {
		if (null == article) {
			return;
		}
		// 点赞总数
		Long praiseCnt = DboUtil.getLong(article, "praiseCnt");
		if (null == praiseCnt) {
			praiseCnt = 0L;
		}
		long praise = praiseCnt + count;
		// 文章评论总数
		int commentCnt = DboUtil.getInt(article, "commentCnt");
		Integer oldLevel = DboUtil.getInt(article, "hotLevel");
		int praiseLevel = (int) praise / 10;
		int commentLevel = commentCnt / 10;
		if (null == oldLevel) {
			oldLevel = 0;
		}
		DBObject u = new BasicDBObject();
		u.put("praiseCnt", praise);
		if (praiseLevel + commentLevel > oldLevel) {
			u.put("hotLevel", praiseLevel + commentLevel);
			u.put("hotTime", System.currentTimeMillis());
		}
		getC(DocName.ARTICLE).update(new BasicDBObject("_id", DboUtil.getLong(article, "_id")),
				new BasicDBObject("$set", u));
	}

	public ReMsg bitchRemoveSift(String ids, int status) {
		if (StringUtils.isNotBlank(ids)) {
			String[] rids = ids.split(",");
			List<Long> idl = new ArrayList<>();
			for (String e : rids) {
				idl.add(Long.parseLong(e));
			}
			BasicDBObject q = new BasicDBObject();
			q.put("_id", new BasicDBObject("$in", idl));
			BasicDBObject u = new BasicDBObject();
			u.put("$set", new BasicDBObject("status", status).append("audited", Article.AUDITED_Y));
			super.getC(DocName.ARTICLE).update(q, u, false, true);
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public ReMsg removeArticle(Long userId, Long id) {
		if (null == id || id < 1) {
			return new ReMsg(ReCode.FAIL);
		}
		// 先查询到这个文章
		DBObject article = this.findById(id);
		if (null == article) {
			return new ReMsg(ReCode.FAIL);
		}
		if (DboUtil.getLong(article, "userId") - userId != 0) {
			return new ReMsg(ReCode.FAIL);
		}
		// if (null == DboUtil.getInt(article, "status") || Article.OK ==
		// DboUtil.getInt(article, "status")) {
		// return new ReMsg(ReCode.DEL_ARTICLE_OK_FAIL);
		// }
		BasicDBObject up = new BasicDBObject("updateTime", System.currentTimeMillis()).append("status", Article.DEL);
		getC(DocName.ARTICLE).update(new BasicDBObject("_id", id), new BasicDBObject("$set", up), false, true);
		return new ReMsg(ReCode.OK);
	}

	public Page<DBObject> articleFlower(Long st, Long et, Long groupId, Long topicId, Long userId, Integer page,
			Integer size) {
		size = getSize(size);
		page = getPage(page);
		DBObject q = new BasicDBObject();
		q.put("flower", new BasicDBObject("$gt", 0));
		if (st != null && st != 0 && et != null && et != 0) {
			q.put("publishTime", new BasicDBObject("$gte", st).append("$lt", et));
		} else if (st != null && st != 0) {
			q.put("publishTime", new BasicDBObject("$gte", st));
		} else if (et != null && et != 0) {
			q.put("publishTime", new BasicDBObject("$lt", et));
		}
		if (groupId != null && groupId != 0) {
			q.put("groupId", groupId);
		}
		if (topicId != null && topicId != 0) {
			q.put("topicId", topicId);
		}
		if (userId != null && userId != 0) {
			q.put("userId", userId);
		}
		List<DBObject> list = getC(DocName.ARTICLE).find(q).sort(new BasicDBObject("flower", -1))
				.skip(getStart(page, size)).limit(getSize(size)).toArray();
		int count = getC(DocName.ARTICLE).find(q).count();
		return new Page<DBObject>(count, size, page, list);
	}

}
