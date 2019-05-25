package com.zb.service.article;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.OperationType;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.mongo.Op;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.Message;
import com.zb.models.User;
import com.zb.models.article.Article;
import com.zb.models.article.ArticleComment;
import com.zb.models.article.ArticleCommentPraiseLog;
import com.zb.models.finance.CoinLog;
import com.zb.service.BaseService;
import com.zb.service.MessageService;
import com.zb.service.UserService;
import com.zb.service.pay.CoinService;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.Msg;
import com.zb.socket.model.MsgType;

@Service
public class ArticleCommentService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(ArticleCommentService.class);

	@Autowired
	ArticleService articleService;

	@Autowired
	UserService userService;

	@Autowired
	MessageService messageService;
	@Autowired
	ArticleGroupService groupService;
	@Autowired
	CoinService coinService;

	public DBObject getById(Long id) {
		return super.findById(DocName.ARTICLE_COMMENT, id);
	}

	// 删除评论
	public ReMsg removeComment(Long userId, Long id) {
		// 设置为删除状态
		DBObject update = new BasicDBObject("$set",
				new BasicDBObject("updateTime", System.currentTimeMillis()).append("status", Article.DEL));
		BasicDBObject q = new BasicDBObject("_id", id).append("userId", userId);
		DBObject comment = super.getC(DocName.ARTICLE_COMMENT).findAndModify(q, update);
		if (null == comment) {
			return new ReMsg(ReCode.FAIL);
		}
		// 查询该评论对应的文章
		DBObject article = articleService.findById(DboUtil.getLong(comment, "aid"));
		if (null == article) {
			return new ReMsg(ReCode.OK);
		}
		BasicDBObject q2 = new BasicDBObject("_id", DboUtil.getLong(comment, "aid"));
		// 如果该文章对应的神评论是该评论
		Long godCid = DboUtil.getLong(article, "godCid");
		if (null != godCid && godCid - id == 0) {
			// 设置为没有神评论
			BasicDBObject u = new BasicDBObject();
			u.append("godComment", Article.GOD_COMMENT_NO).append("godCid", null).append("godUid", null)
					.append("godContent", null).append("godCount", null).append("godPics", null);
			getC(DocName.ARTICLE).update(q2, new BasicDBObject("$set", u), false, true);
		}
		getC(DocName.ARTICLE).update(q2,
				new BasicDBObject("$inc", new BasicDBObject("commentCnt", Integer.valueOf(-1))), false, true);
		return new ReMsg(ReCode.OK);
	}

	public Page<DBObject> query(Long userId, Long aid, Integer status, Integer page, Integer size, Op op) {
		return query(userId, aid, status, page, size, op, false);
	}

	public Page<DBObject> query(Long userId, Long aid, Integer status, Integer page, Integer size, Op op, boolean adm) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(userId, aid, status, page, size, op, adm);
		int count = conunt(userId, aid, status, op);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public Integer conunt(Long userId, Long aid, Integer status, Op op) {
		DBObject q = new BasicDBObject();
		if (userId != null && userId != 0) {
			q.put("userId", userId);
		}
		if (aid != null && aid != 0) {
			q.put("aid", aid);
		}
		if (op != null) {
			op.getOp(q, "status", status);
		} else if (null != status && status != 0) {
			q.put("status", status);
		}
		return getC(DocName.ARTICLE_COMMENT).find(q).count();
	}

	public List<DBObject> find(Long userId, Long aid, Integer status, Integer page, Integer size, Op op, boolean adm) {
		DBObject q = new BasicDBObject();
		if (userId != null && userId != 0) {
			q.put("userId", userId);
		}
		if (aid != null && aid != 0) {
			q.put("aid", aid);
		}
		if (op != null) {
			op.getOp(q, "status", status);
		} else if (null != status && status != 0) {
			q.put("status", status);
		}

		List<DBObject> dbos = getC(DocName.ARTICLE_COMMENT).find(q).sort(new BasicDBObject("_id", -1))
				.skip(getStart(page, size)).limit(getSize(size)).toArray();
		return addUserInfos(dbos, adm);
	}

	public static final int GOD_MIN_COUNT = 1;
	public static final int HOT_MIN_COUNT = 1;

	/**
	 * @param aid
	 * @param size
	 * @param adm
	 * @return
	 */
	public List<DBObject> getTopComments(Long aid, Integer size, boolean adm) {
		DBObject q = new BasicDBObject("status", Const.STATUS_OK).append("aid", aid).append("praiseCnt",
				new BasicDBObject("$gt", HOT_MIN_COUNT));
		List<DBObject> dbos = getC(DocName.ARTICLE_COMMENT).find(q)
				.sort(new BasicDBObject("praiseCnt", -1).append("_id", -1)).skip(0).limit(getSize(size)).toArray();
		return addUserInfos(dbos, adm);
	}

	public List<DBObject> getTopComments(Long aid, Integer size) {
		return getTopComments(aid, size, false);
	}

	private List<DBObject> addUserInfos(List<DBObject> dbos, boolean adm) {
		List<DBObject> rdbo = new ArrayList<DBObject>();
		for (DBObject dbo : dbos) {
			dbo = addUserInfo(dbo, adm);
			if (dbo != null) {
				rdbo.add(dbo);
			}
		}
		return rdbo;
	}

	private DBObject addUserInfo(DBObject dbo, boolean adm) {
		// 转换艾特的人
		dbo.put("commentAt", userService.getAtUsers(DboUtil.get(dbo, "atUsers", ArrayList.class)));
		// Long baseId = DboUtil.getLong(dbo, "baseId");
		// Long id = DboUtil.getLong(dbo, "_id");
		// 不再需要二级评论
		// if (baseId != null && baseId < id) {// 是否有回复评论
		// dbo.put("replys", getReplys(id, baseId, dbo));
		// }
		Long uid = DboUtil.getLong(dbo, "userId");
		DBObject user = userService.findById(uid);

		if (user != null && DboUtil.getInt(user, "status") == User.STATUS_OK || adm) {
			dbo.put("nickname", DboUtil.getString(user, "nickname"));
			dbo.put("avatar", DboUtil.getString(user, "avatar"));
			dbo.put("sex", DboUtil.getString(user, "sex"));
			dbo.put("point", DboUtil.getString(user, "point"));
			dbo.put("vip", DboUtil.getString(user, "vip"));
		} else {
			dbo = null;
		}

		return dbo;
	}

	static final int REPLYS_MAX_COUNT = 5;

	// private List<DBObject> getReplys(Long id, Long baseId, DBObject comment)
	// {
	// DBObject q = new BasicDBObject("_id", new BasicDBObject("$lt",
	// id)).append("baseId", baseId).append("status",
	// Const.STATUS_OK);
	// List<DBObject> dbos = new ArrayList<DBObject>();
	// List<DBObject> tmps = getC(DocName.ARTICLE_COMMENT).find(q).sort(new
	// BasicDBObject("_id", -1))
	// .limit(REPLYS_MAX_COUNT * 2).toArray();
	// Long cid = DboUtil.getLong(comment, "cid");
	// for (DBObject dbo : tmps) {
	// if (cid != null) {
	// long curid = DboUtil.getLong(dbo, "_id");
	// // System.out.println("curid:" + curid + ",cid:" + cid
	// // + "curid == cid is " + (curid == cid));
	// if (curid == cid) {
	// // System.out.println("OK");
	// cid = DboUtil.getLong(dbo, "cid");
	// dbos.add(new BasicDBObject("_id", curid).append("userId",
	// DboUtil.getLong(dbo, "userId"))
	// .append("nickname", DboUtil.getString(dbo, "nickname"))
	// .append("content", DboUtil.getString(dbo, "content")));
	// }
	// // else {
	// // System.out.println("Failed");
	// // }
	// if (dbos.size() >= REPLYS_MAX_COUNT) {
	// break;
	// }
	// }
	// }
	// return dbos;
	// }

	public ReCode saveComment(long aid, long uid, String content, String nickname, String[] atUsers, String[] pics,
			List<Map> draws, String voiceUrl) {
		DBObject q = new BasicDBObject("aid", aid).append("userId", uid);
		List<DBObject> dbos = getC(DocName.ARTICLE_COMMENT).find(q).sort(new BasicDBObject("_id", -1)).limit(3)
				.toArray();
		for (DBObject dbo : dbos) {
			if (System.currentTimeMillis() - DboUtil.getLong(dbo, "createTime") < 9 * 1000) {
				return ReCode.TOO_COMMENT_FREQUENTLY;
			} else if (DboUtil.getString(dbo, "content").equals(content)) {
				return ReCode.TOO_COMMENT_SIMILARITY;
			}
		}

		// int count = getTodayCommentCount(uid);
		// if (count == 0) {
		// userService.changePoint(uid, Point.COMMENT_ARTICLE_FIRST, 0L);
		// } else if (count == 1 || count == 2 || count == 4 || count == 7 ||
		// count == 11 || count == 20) {
		// userService.changePoint(uid, Point.COMMENT_ARTICLE, 0L);
		// }
		if (nickname == null)
			nickname = super.getUser("nickname") != null ? super.getUser("nickname").toString() : "";
		long id = super.getNextId(DocName.ARTICLE_COMMENT);
		ArticleComment arl = new ArticleComment(id, aid, content, uid, id, nickname, 0L, atUsers, pics, draws,
				voiceUrl);
		getMongoTemplate().save(arl);
		// 发送提醒
		if (null != atUsers && atUsers.length > 0) {
			nickname = null == nickname ? "有人" : nickname;
			for (int i = 0; i < atUsers.length; i++) {
				MapBody mb = new MapBody("op", OperationType.ARTICLE.getOp()).append("opId", aid).append("content",
						nickname + "回复了你的评论:" + content);
				Msg msg = new Msg(super.incrMsgId(), MsgType.NOTICE, 0, Long.parseLong(atUsers[i]),
						System.currentTimeMillis(), mb.getData());
				messageService.msgHandler(msg);
			}
		}
		return ReCode.OK;
	}

	public ReCode updateComment(long id, int status) {
		Boolean water = null;
		if (status == Const.STATUS_WATER) {
			status = Const.STATUS_DEL;
			water = true;
		}
		DBObject comment = super.getC(DocName.ARTICLE_COMMENT).findAndModify(new BasicDBObject("_id", id),
				new BasicDBObject("$set", new BasicDBObject("status", status)));
		if (null != water && water) {// 水评论 扣钱 发消息
			Long uid = DboUtil.getLong(comment, "userId");
			coinService.reduce(uid, CoinLog.GROUP, id, 5, 0, "水评论扣5金币");
			String content = DboUtil.getString(comment, "content");
			if (StringUtils.isNotBlank(content)) {
				if (content.length() > 10) {
					content = content.substring(0, 10) + "...";
				}
			} else {
				content = "图";
			}
			MapBody mb = new MapBody("op", OperationType.ARTICLE.getOp())
					.append("opId", DboUtil.getLong(comment, "aid"))
					.append("content", "您发出的评论[" + content + "]被判定为恶意水贴,系统扣除5金币");
			Msg msg = new Msg(super.incrMsgId(), MsgType.NOTICE, 0, uid, System.currentTimeMillis(), mb.getData());
			messageService.msgHandler(msg);
		}
		// 更改评论数量
		BasicDBObject q = new BasicDBObject("_id", DboUtil.getLong(comment, "aid"));
		BasicDBObject q2 = new BasicDBObject("commentCnt", Integer.valueOf(1));
		if (status < 0) {
			q2 = new BasicDBObject("commentCnt", Integer.valueOf(-1));
		}
		getC(DocName.ARTICLE).update(q, new BasicDBObject("$inc", q2), false, true);
		return ReCode.OK;
	}

	public ReCode replyComment(long aid, long cid, long uid, String content, String[] atUsers) {
		String nickname = super.getUser("nickname") != null ? super.getUser("nickname").toString() : "";
		return replyComment(aid, cid, uid, nickname, content, atUsers);
	}

	public ReCode replyComment(long aid, long cid, long uid, String nickname, String content, String[] atUsers) {
		DBObject q = new BasicDBObject("aid", aid).append("userId", uid);
		List<DBObject> dbos = getC(DocName.ARTICLE_COMMENT).find(q).sort(new BasicDBObject("_id", -1)).limit(3)
				.toArray();
		for (DBObject dbo : dbos) {
			if (System.currentTimeMillis() - DboUtil.getLong(dbo, "createTime") < 3 * 1000) {
				return ReCode.FAIL;
			} else if (DboUtil.getString(dbo, "content").equals(content)) {
				return ReCode.FAIL;
			}
		}
		DBObject dbo = this.getById(cid);
		if (dbo != null) {
			Long baseId = DboUtil.getLong(dbo, "baseId");
			if (baseId == null || baseId == 0) {
				baseId = cid;
			}

			// int count = getTodayCommentCount(uid);
			// if (count == 0) {
			// userService.changePoint(uid, Point.COMMENT_ARTICLE_FIRST, 0L);
			// } else if (count == 1 || count == 2 || count == 4 || count == 7
			// || count == 11 || count == 20) {
			// userService.changePoint(uid, Point.COMMENT_ARTICLE, 0L);
			// }

			long id = super.getNextId(DocName.ARTICLE_COMMENT);
			ArticleComment arl = new ArticleComment(id, aid, content, uid, baseId, nickname, cid, atUsers, null, null,
					null);
			getMongoTemplate().save(arl);
			// 发送提醒
			if (null != atUsers && atUsers.length > 0) {
				nickname = null == nickname ? "有人" : nickname;
				for (int i = 0; i < atUsers.length; i++) {
					MapBody mb = new MapBody("op", OperationType.COMMENT.getOp()).append("opId", id).append("content",
							nickname + "在评论中提到了你");
					Msg msg = new Msg(super.incrMsgId(), MsgType.AT_USER, 0, Long.parseLong(atUsers[i]),
							System.currentTimeMillis(), mb.getData());
					messageService.msgHandler(msg);
				}
			}
			Long author = DboUtil.getLong(dbo, "userId");
			if (StringUtils.isNotBlank(content)) {
				if (content.length() > 10) {
					content = content.substring(0, 10) + "...";
				}
			} else {
				content = "图";
			}
			messageService.save(Message.TYPE_NOTICE, nickname + "的回复:［" + content + "]", author, 0L,
					OperationType.ARTICLE, "" + aid, null);

			return ReCode.OK;
		}
		return ReCode.FAIL;
	}

	// private int getTodayCommentCount(Long uid) {
	// DBObject q = new BasicDBObject("createTime", new BasicDBObject("$gt",
	// DateUtil.getTodayZeroTime()))
	// .append("userId", uid);
	// return getC(DocName.ARTICLE_COMMENT).find(q).count();
	// }

	public ReMsg praiseComment(Long cid) {
		Long uid = super.getUid();
		if (uid <= 0) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (isCommentPraise(cid, uid)) {
			return new ReMsg(ReCode.ARTICLE_COMMENT_PRAISE_EXIST);
		}
		DBObject rc = getById(cid);
		if (rc != null) {
			// 非本人点赞
			String content = DboUtil.getString(rc, "content");
			if (StringUtils.isNotBlank(content)) {
				if (content.length() > 10) {
					content = content.substring(0, 10) + "...";
				}
			} else {
				content = "图";
			}
			this.praiseComment(rc, content);// 评论点赞奖励
			if (uid - DboUtil.getLong(rc, "userId") != 0) {
				MapBody mb = new MapBody("op", OperationType.ARTICLE.getOp()).append("opId", DboUtil.getLong(rc, "aid"))
						.append("content", super.getUser("nickname").toString() + "赞了这条评论:[" + content + "]");
				Msg msg = new Msg(super.incrMsgId(), MsgType.NOTICE, 0, DboUtil.getLong(rc, "userId"),
						System.currentTimeMillis(), mb.getData());
				messageService.msgHandler(msg);
			}

			Long id = super.getNextId(DocName.ARTICLE_COMMENT_PRAISE_LOG);
			ArticleCommentPraiseLog arl = new ArticleCommentPraiseLog(id, cid, uid);
			getMongoTemplate().save(arl);
			int count = DboUtil.getInt(rc, "praiseCnt") + 1;
			DBObject uo = new BasicDBObject("$inc", new BasicDBObject("praiseCnt", Integer.valueOf(1))).append("$set",
					new BasicDBObject("updateTime", System.currentTimeMillis()));
			getC(DocName.ARTICLE_COMMENT).update(new BasicDBObject("_id", cid), uo);
			if (count > GOD_MIN_COUNT) {
				Long aid = DboUtil.getLong(rc, "aid");
				content = DboUtil.getString(rc, "content");
				ArrayList list = DboUtil.get(rc, "pics", ArrayList.class);
				articleService.updateGodArticle(aid, DboUtil.getLong(rc, "userId"), cid, content, count, list);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	/** 评论被点赞奖励 */
	private void praiseComment(DBObject comment, String content) {
		Long userId = DboUtil.getLong(comment, "userId");
		Long commentId = DboUtil.getLong(comment, "_id");
		Long aid = DboUtil.getLong(comment, "aid");
		int count = DboUtil.getInt(comment, "praiseCnt") + 1;
		Boolean sendMsg = null;
		int coin = 0;
		if (count == 3) {// 3次点赞10金币
			coinService.addCoin(userId, CoinLog.GROUP, commentId, 10, 0, "评论第3次被点赞奖励10金币");
			sendMsg = true;
			coin = 10;
		} else if (count == 10) {// 10次点赞20金币
			coinService.addCoin(userId, CoinLog.GROUP, commentId, 20, 0, "评论第10次被点赞奖励20金币");
			sendMsg = true;
			coin = 20;
		} else if (count == 50) {// 50次点赞100金币
			coinService.addCoin(userId, CoinLog.GROUP, commentId, 100, 0, "评论第50次被点赞奖励100金币");
			sendMsg = true;
			coin = 100;
		}
		if (null != sendMsg && sendMsg) {
			// 发送消息 您的评论被点赞xx次 奖励xx金币
			MapBody mb = new MapBody("op", OperationType.ARTICLE.getOp()).append("opId", aid).append("content",
					"您的评论[" + content + "]累计被点赞" + count + "次,系统奖励您" + coin + "金币   点击查看详情>");
			Msg msg = new Msg(super.incrMsgId(), MsgType.NOTICE, 0, userId, System.currentTimeMillis(), mb.getData());
			messageService.msgHandler(msg);
		}
	}

	public ReMsg praiseComment(Long cid, int count) {
		DBObject rc = getById(cid);
		if (rc != null) {
			DBObject uo = new BasicDBObject("$inc", new BasicDBObject("praiseCnt", count)).append("$set",
					new BasicDBObject("updateTime", System.currentTimeMillis()));
			getC(DocName.ARTICLE_COMMENT).update(new BasicDBObject("_id", cid), uo);
			count = DboUtil.getInt(rc, "praiseCnt") + count;
			if (count > GOD_MIN_COUNT) {
				Long aid = DboUtil.getLong(rc, "aid");
				String content = DboUtil.getString(rc, "content");
				ArrayList list = DboUtil.get(rc, "pics", ArrayList.class);
				articleService.updateGodArticle(aid, DboUtil.getLong(rc, "userId"), cid, content, count, list);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	private boolean isCommentPraise(Long cid, Long uid) {
		return super.getC(DocName.ARTICLE_COMMENT_PRAISE_LOG).find(new BasicDBObject("cid", cid).append("userId", uid))
				.count() > 0;
	}
	//
	// public List<DBObject> queryHotCom(Long articleId, Integer page, Integer
	// size) {
	// DBObject q = new BasicDBObject();
	// if (null != articleId && articleId != 0) {
	// q.put("aid", articleId);
	// q.put("praiseCnt", new BasicDBObject("$gt", 0));
	// } else {
	// return new ArrayList<DBObject>();
	// }
	// List<DBObject> list = getC(DocName.ARTICLE_COMMENT).find(q).sort(new
	// BasicDBObject("praiseCnt", -1))
	// .skip(getStart(page, size)).limit(getSize(size)).toArray();
	// for (DBObject comment : list) {
	// long uid = (long) comment.get("userId");
	// DBObject user = userService.findById(uid);
	// comment.put("nickname", DboUtil.getString(user, "nickname"));
	// comment.put("avatar", DboUtil.getString(user, "avatar"));
	// comment.put("sex", DboUtil.getString(user, "sex"));
	// comment.put("point", DboUtil.getString(user, "point"));
	// comment.put("vip", DboUtil.getInt(user, "vip"));
	// }
	// return list;
	// }

	// 我的回帖
	public Page<DBObject> findMyComments(Long uid, Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		List<DBObject> dbos = new ArrayList<DBObject>();
		// 找到最近回复文章评论
		List<DBObject> list = find(uid, null, Const.STATUS_OK, page, size, null, false);
		for (DBObject comment : list) {
			Long aid = DboUtil.getLong(comment, "aid");
			// 查询文章
			DBObject article = articleService.findById(aid);
			if (null == article || null == DboUtil.getInt(article, "status")
					|| Article.DEL == DboUtil.getInt(article, "status")) {
				continue;
			}
			// 添加group标签图
			DBObject group = groupService.findById(DboUtil.getLong(article, "groupId"));
			if (null != group) {
				article.put("tagPic", DboUtil.getString(group, "tagPic"));
			}
			// 组合dbo 主体是文章
			article.put("myCid", DboUtil.getLong(comment, "_id")); // 评论id
			article.put("commentAt", userService.getAtUsers(DboUtil.get(comment, "atUsers", ArrayList.class))); // @的人
			article.put("myComment", DboUtil.getString(comment, "content")); // 评论
			article.put("myPics", DboUtil.get(comment, "pics", ArrayList.class)); // 评论图片
			article.put("myTime", DboUtil.getLong(comment, "updateTime")); // 回帖时间
			article.put("myDraws", DboUtil.get(comment, "draws", ArrayList.class)); // 评论画板
			article.put("myVoiceUrl", DboUtil.getString(comment, "voiceUrl")); // 评论语音
			dbos.add(article);
		}
		Integer count = conunt(uid, null, null, null);
		return new Page<DBObject>(count, size, page, dbos);
	}
}
