<?php
/**
 * 联系客服
 */
namespace Appapi\Controller;
use Common\Controller\HomebaseController;

class ServiceController extends HomebaseController {
	
	public function index(){
		//获取配置信息中的客服QQ和客服电话
		$list=M("contact")->order("orderno desc")->select();
		
		$this->assign("list",$list);

		$this->display();
	    
	}


	


		
}