package com.zb.web.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.ContactHeadService;
import com.zb.service.ResService;

@Controller
@RequestMapping("/res")
public class ZbResCtl extends BaseCtl {

	@Autowired
	ResService resService;

	@Autowired
	ContactHeadService contactHeadService;

	@ResponseBody
	@RequestMapping("/packs/{cateId}")
	public ReMsg resPacks(@PathVariable Long cateId, Integer page, Integer size) {
		Page<DBObject> p = resService.queryResPack(null, cateId,
				Const.STATUS_UP, null, page, size);
		return new ReMsg(ReCode.OK, p.getSize(), p.getTotalPage(), p.getList());
	}

	@ResponseBody
	@RequestMapping("/pack/{packId}")
	public ReMsg resList(@PathVariable Long packId) {
		DBObject dbo = resService.findResPackById(packId);
		return new ReMsg(ReCode.OK, dbo);
	}

	/**
	 * 自己上传的联系人资源（微信，红包用）
	 * 
	 * @param page
	 * @param size
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/contactsHeads")
	public ReMsg ContactHeadList(String label, HttpServletRequest req) {
		return contactHeadService.getContactsHead(label);
	}

	/**
	 * 微信，红包联系人上传
	 * 
	 * @param name
	 * @param resPath
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/contactsHead", method = RequestMethod.POST)
	public ReMsg saveContactsHead(String name, String headPic,
			HttpServletRequest req) {
		return contactHeadService.saveContactHead(name, headPic);
	}

	/**
	 * 微信，红包联系人上传
	 * 
	 * @param name
	 * @param resPath
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delContactsHead")
	public ReMsg delContactsHead(Long id, HttpServletRequest req) {
		return contactHeadService.delContactHead(id);
	}

	/**
	 * 微信，红包联系人分类
	 * 
	 * @param name
	 * @param resPath
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/contactsLabels")
	public ReMsg getContactsCate(HttpServletRequest req) {
		return new ReMsg(ReCode.OK, ContactHeadService.CATE_NAME);
	}

	@ResponseBody
	@RequestMapping("/search")
	public ReMsg toolSearch(String qtxt, Integer page, Integer size) {
		return new ReMsg(ReCode.OK, resService.searchReses(qtxt, page, size));
	}

	@ResponseBody
	@RequestMapping("/searchRecommend")
	public ReMsg toolSearch() {
		return new ReMsg(ReCode.OK, resService.searchRecommend());
	}
}
