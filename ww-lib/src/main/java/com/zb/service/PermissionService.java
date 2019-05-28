package com.zb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.permission.Menu;
import com.zb.models.permission.Permission;
import com.zb.models.permission.UserPerms;

@Service
public class PermissionService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(PermissionService.class);

	public DBObject findPermById(Long id) {
		return super.findById(DocName.PERMISSION, id);
	}

	public Permission getPermission(Long id) {
		return DboUtil.toBean(findPermById(id), Permission.class);
	}

	public Map<String, List<DBObject>> getPermissions(Long uid) {
		Menu[] ms = Menu.values();
		Map<String, List<DBObject>> mps = new HashMap<String, List<DBObject>>();
		UserPerms ups = getUserPerms(uid);
		for (Menu m : ms) {
			int size = countPermission(m.getId(), null, null, null);
			List<DBObject> perms = findPermission(m.getId(), null, null, null, 0, size);
			for (DBObject perm : perms) {
				if (ups != null && ups.contains(DboUtil.getString(perm, "uri"))) {
					perm.put("has", 1);
				}
			}
			mps.put(m.name(), perms);
		}
		return mps;
	}

	public Page<DBObject> queryPermission(Integer menuId, String uri, String name, Integer role, Integer page,
			Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findPermission(menuId, uri, name, role, page, size);
		int count = this.countPermission(menuId, uri, name, role);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> findPermission(Integer menuId, String uri, String name, Integer role, Integer page,
			Integer size) {
		DBObject q = new BasicDBObject();
		if (null != menuId && menuId > 0) {
			q.put("menuId", menuId);
		}
		if (StringUtils.isNotBlank(uri)) {
			q.put("uri", uri);
		}
		if (StringUtils.isNotBlank(name)) {
			q.put("name", name);
		}
		if (role != null && role > 0) {
			q.put("roles", role);
		}
		return getC(DocName.PERMISSION).find(q).skip(super.getStart(page, size)).limit(super.getSize(size)).toArray();
	}

	public int countPermission(Integer menuId, String uri, String name, Integer role) {
		DBObject q = new BasicDBObject();
		if (null != menuId && menuId > 0) {
			q.put("menuId", menuId);
		}
		if (StringUtils.isNotBlank(uri)) {
			q.put("uri", uri);
		}
		if (StringUtils.isNotBlank(name)) {
			q.put("name", name);
		}
		if (role != null && role > 0) {
			q.put("roles", role);
		}
		return getC(DocName.PERMISSION).find(q).count();
	}

	public ReMsg upsertPermission(Long id, String name, String uri, int menuId, String ids) {
		List<Integer> roles = new ArrayList<Integer>();
		String[] idsArr = ids.split(",");
		for (String idStr : idsArr) {
			Integer role = Integer.parseInt(idStr);
			roles.add(role);
		}
		if (id != null && id > 0) {
			DBObject a = findPermById(id);
			if (null == a) {
				return new ReMsg(ReCode.FAIL);
			}
			DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis()).append("roles", roles);
			if (StringUtils.isNotBlank(name)) {
				u.put("name", name);
			}
			if (StringUtils.isNotBlank(uri)) {
				u.put("uri", uri);
			}

			if (menuId != 0) {
				u.put("menuId", menuId);
			}

			getC(DocName.PERMISSION).update(new BasicDBObject("_id", id), new BasicDBObject("$set", u));
		} else {
			int c = this.countPermission(null, uri, null, 0);
			if (c > 0) {
				return new ReMsg(ReCode.PARAMETER_ERR);
			}
			Permission p = new Permission(super.getNextId(DocName.PERMISSION), name, uri, menuId, super.getUid(),
					roles);
			super.getMongoTemplate().save(p);
		}
		return new ReMsg(ReCode.OK);
	}

	public DBObject findUserPermById(Long id) {
		return super.findById(DocName.USER_PERMS, id);
	}

	public UserPerms getUserPerms(Long uid) {
		return DboUtil.toBean(findUserPermById(uid), UserPerms.class);
	}

	public ReMsg upsertUserPerms(Long uid, String ids) {
		String[] idsArr = ids.split(",");
		UserPerms up = new UserPerms(uid, super.getUid());
		for (String idStr : idsArr) {
			Long id = Long.parseLong(idStr);
			Permission p = this.getPermission(id);
			up.addPerm(p);
		}
		super.getMongoTemplate().save(up);
		return new ReMsg(ReCode.OK);
	}
}
