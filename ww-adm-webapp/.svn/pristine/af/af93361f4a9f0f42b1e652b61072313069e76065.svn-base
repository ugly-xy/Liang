package com.zb.web.view.admin;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.paper.*;
import com.mongodb.DBObject;
import com.zb.service.PaperService;

@Controller
@RequestMapping("/admin")
public class PaperCtl {
	
	static final Logger log = LoggerFactory.getLogger(PaperCtl.class);
	
	@Autowired
	PaperService paperService;
	
	@RequestMapping("/papers")
	public ModelAndView paper(Long id,Integer type,String title,String content,Integer pay,
			Integer sort,Integer status, Integer page,
			Integer size, HttpServletRequest request){
		
		Page<DBObject> curPage  =paperService.queryPaper(id, type, pay, status, page, size);
		
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("type", type);
		model.put("id", id);
		model.put("pay", pay);
		return new ModelAndView("admin/paper/papers", model);
		
	}
	
	// 查询单个baseGood
	@RequestMapping("/paper")
	public ModelAndView paper(Long id) {
		
		DBObject dbo = paperService.findById(id);
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("dbo", dbo);
		
		return new ModelAndView("admin/paper/paper", model);

	}
	
	// 新增、更新单个baseGood
	@ResponseBody
	@RequestMapping(value = "/paper", method = RequestMethod.POST)
	public ReMsg updatePaper(Long id,int type,String title,String introduce,String logo,String backPic,String content,int count,int pay,double sort,int status,
			HttpServletRequest request) {
		List<Question> qList = new ArrayList<Question>();
		List<Result> rList = new ArrayList<Result>();
		List<Item> iList = new ArrayList<Item>();
		String quesNum = request.getParameter("quesNum");
		String resNum = request.getParameter("resultNum");
		//System.out.println(request.getParameter("quesNum"));
		if(!quesNum.equals("0")){
			qList = new ArrayList<Question>();
			for(int i = 0 ;i<Integer.parseInt(quesNum);i++){
				//System.out.println("title"+request.getParameter("quesTitle"+i));
				String quesTitle = request.getParameter("quesTitle"+i);
				String quesContent = request.getParameter("quesContent"+i);
				String quesType = request.getParameter("quesType"+i);
				String itemNum = request.getParameter("itemNum"+i);
				iList = new ArrayList<Item>();
					if(itemNum !=null){
						for(int y=0;y<Integer.parseInt(itemNum);y++){
							if(request.getParameter("itemContent"+i+""+y)!=null){
								Item item = new Item(request.getParameter("itemContent"+i+""+y),Double.valueOf(request.getParameter("itemScore"+i+""+y)),Integer.parseInt(request.getParameter("itemContentType"+i+""+y)));
								iList.add(item);
							}
					}
					
					}
				if(quesTitle !=null && quesContent !=null && quesType !=null && iList !=null){
					qList.add(new Question(quesTitle,quesContent,iList,Integer.parseInt(quesType)));
				}	
					
			}
		}
		
		if(!resNum.equals("0")){
			rList = new ArrayList<Result>();
			for(int i = 0;i<Integer.parseInt(resNum);i++){
				String resContentType = request.getParameter("resultContentType"+i);
				String resScore = request.getParameter("resultScore"+i);
				String resultTitle = request.getParameter("resultTitle"+i);
				String resultContent = request.getParameter("resultContent"+i);
				
				if(resContentType !=null && resScore !=null && resultTitle !=null && resultContent !=null){
					Result r = new Result(Integer.parseInt(resContentType),Double.valueOf(resScore),resultTitle,resultContent);
					rList.add(r);
				}
			}
		}
		return this.paperService.savePaper(id, type, title, introduce, logo, backPic, content, count, pay, sort, status, qList, rList);
	}
	
	
	
}
