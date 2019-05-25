package com.zb.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.common.Constant.ReCode;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.goods.BaseGoods;
import com.zb.models.room.Room;
import com.zb.service.room.RoomDefaultService;
import com.zb.service.room.RoomHandlerDispatcher;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

@Service
public class PropService extends BaseService {

	
	//
	// public DBObject findById(Long id) {
	// return super.findById(DocName.GOODS, id);
	// }
	//
	// public Page<DBObject> query(Integer baseId, Integer status, Integer page,
	// Integer size, Op op) {
	// size = getSize(size);
	// page = getPage(page);
	// List<DBObject> dbos = find(baseId, status, page, size, op);
	// Integer count = count(baseId, status, op);
	// return new Page<DBObject>(count, size, page, dbos);
	// }
	//
	// public List<DBObject> find(Integer baseId, Integer status, Integer page,
	// Integer size, Op op) {
	// DBObject q = new BasicDBObject();
	// if (baseId != null && baseId != 0) {
	// q.put("baseId", baseId);
	// }
	// if (op != null) {
	// op.getOp(q, "status", status);
	// } else if (null != status && status != 0) {
	// q.put("status", status);
	// }
	//
	// return getC(DocName.GOODS).find(q).sort(new BasicDBObject("sort", -1))
	// .skip(getStart(page, size)).limit(getSize(size)).toArray();
	// }
	//
	// public Integer count(Integer baseId, Integer status, Op op) {
	// DBObject q = new BasicDBObject();
	// if (baseId != null && baseId != 0) {
	// q.put("baseId", baseId);
	// }
	// if (op != null) {
	// op.getOp(q, "status", status);
	// } else if (null != status && status != 0) {
	// q.put("status", status);
	// }
	//
	// return getC(DocName.GOODS).find(q).count();
	// }
	//
	// public ReMsg update(Long id, String name, String img, Integer listPrice,
	// Integer price, Long amount, Long startTime, Long endTime,
	// Integer status, Integer sort) {
	//
	// DBObject a = findById(id);
	// if (null == a) {
	// return new ReMsg(ReCode.FAIL);
	// }
	//
	// DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
	// if (StringUtils.isNotBlank(name)) {
	// u.put("name", name);
	// }
	// if (StringUtils.isNotBlank(img)) {
	// u.put("img", img);
	// }
	//
	// if (listPrice != 0) {
	// u.put("listPrice", listPrice);
	// }
	// if (status != 0) {
	// u.put("status", status);
	// }
	// if (price != 0) {
	// u.put("price", price);
	// }
	// if (amount != 0) {
	// u.put("amount", amount);
	// }
	// if (sort != 0) {
	// u.put("sort", sort);
	// }
	// if (startTime != 0) {
	// u.put("startTime", startTime);
	// }
	// if (endTime != 0) {
	// u.put("endTime", endTime);
	// }
	//
	// if (getC(DocName.GOODS).update(new BasicDBObject("_id", id),
	// new BasicDBObject("$set", u)).getN() > 0) {
	// return new ReMsg(ReCode.OK);
	// }
	// return new ReMsg(ReCode.FAIL);
	//
	// }
	//
	// public ReMsg save(String name, String img, Integer listPrice,
	// Integer price, Long startTime, Long endTime, Integer status,
	// Integer sort, int cat, int limit, List<GoodsItem> items) {
	// Long id = super.getNextId(DocName.GOODS);
	// Goods g = new Goods(id, name, img, listPrice, price, startTime,
	// endTime, status, sort, cat, limit, items);
	// getMongoTemplate().save(g);
	// return new ReMsg(ReCode.OK, g);
	// }
	//
	// public DBObject findBaseGoodsById(Long id) {
	// return super.findById(DocName.BASE_GOODS, id);
	// }
	//
	// public Page<DBObject> queryBaseGoods(String goodsType, Integer page,
	// Integer size) {
	// size = getSize(size);
	// page = getPage(page);
	// List<DBObject> dbos = findBaseGoods(goodsType, page, size);
	// Integer count = countBaseGoods(goodsType);
	// return new Page<DBObject>(count, size, page, dbos);
	// }
	//
	// public List<DBObject> findBaseGoods(String goodsType, Integer page,
	// Integer size) {
	// DBObject q = new BasicDBObject();
	// if (StringUtils.isNotBlank(goodsType)) {
	// GoodsType gt = GoodsType.valueOf(goodsType);
	// if (goodsType != null) {
	// q.put("type", gt.getT());
	// }
	// }
	// return getC(DocName.BASE_GOODS).find(q)
	// .sort(new BasicDBObject("_id", -1)).skip(getStart(page, size))
	// .limit(getSize(size)).toArray();
	// }
	//
	// public Integer countBaseGoods(String goodsType) {
	// DBObject q = new BasicDBObject();
	// if (StringUtils.isNotBlank(goodsType)) {
	// GoodsType gt = GoodsType.valueOf(goodsType);
	// if (goodsType != null) {
	// q.put("type", gt.getT());
	// }
	// }
	//
	// return getC(DocName.BASE_GOODS).find(q).count();
	// }
	//
	// public ReMsg updateBaseGoods(Long id, String name, String goodsType,
	// String amountType, String unit) {
	//
	// DBObject a = findBaseGoodsById(id);
	// if (null == a) {
	// return new ReMsg(ReCode.FAIL);
	// }
	//
	// DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
	// if (StringUtils.isNotBlank(name)) {
	// u.put("name", name);
	// }
	// if (StringUtils.isNotBlank(goodsType)) {
	// GoodsType gt = GoodsType.valueOf(goodsType);
	// if (goodsType != null) {
	// u.put("type", gt.getT());
	// }
	// }
	//
	// if (StringUtils.isNotBlank(amountType)) {
	// AmountType gt = AmountType.valueOf(amountType);
	// if (gt != null) {
	// u.put("amountType", gt.ordinal());
	// }
	// }
	//
	// if (StringUtils.isNotBlank(unit)) {
	// Unit gt = Unit.valueOf(unit);
	// if (gt != null) {
	// u.put("unit", gt.name());
	// u.put("count", gt.getTimes());
	// }
	// }
	//
	// getC(DocName.BASE_GOODS).update(new BasicDBObject("_id", id),
	// new BasicDBObject("$set", u));
	// return new ReMsg(ReCode.OK);
	//
	// }
	//
	// public ReMsg saveBaseGoods(String name, String goodsType,
	// String amountType, String unit) {
	//
	// Long id = super.getNextId(DocName.BASE_GOODS);
	// BaseGoods g = new BaseGoods(id, name, GoodsType.valueOf(goodsType),
	// AmountType.valueOf(amountType), Unit.valueOf(unit));
	// getMongoTemplate().save(g);
	// return new ReMsg(ReCode.OK, g);
	// }

}
