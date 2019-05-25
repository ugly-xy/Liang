<?php
/**
 * 会员认证
 */
namespace Appapi\Controller;
use Common\Controller\HomebaseController;
use QCloud\Cos\Api;
use QCloud\Cos\Auth;
class AuthController extends HomebaseController {
	
	public function index(){
		$uid=I('uid');
		$token=I("token");  
		$language_type=I('language');
		$type=I("type");  
		/*if(checkToken($uid,$token)==700){
			$this->assign("reason",LangT('您的登陆状态失效，请重新登陆！'));
			$this->display(':error');
			exit;
		}*/
		      
		$this->assign("uid",$uid);
		$this->assign("token",$token);
		if($type!=1){
			$auth=M("users_auth")->where("uid='{$uid}'")->find();
			//获取app名称
			$appname=M("config")->where("id=1")->getField("app_name");
			if($auth){

				$this->assign("auth",$auth);
				$this->assign("appname",$appname);
				$this->assign("status",$auth['status']);
				$this->redirect("/index.php?g=Appapi&m=Auth&a=success&uid={$uid}&token={$token}&language={$language_type}");
				exit;
			}
		}

		$time=time();
		$this->assign("time",$time);
		$this->assign("appname",$appname);
		$this->display();
	    
	}


	function success(){
		$uid=I("uid");
		$token=I("token");
		$auth=M("users_auth")->where("uid={$uid}")->find();
		//获取app名称
		$appname=M("config")->where("id=1")->getField("app_name");
		$this->assign("status",$auth['status']);
		$this->assign("auth",$auth);
		$time=time();
		
		$this->assign("time",$time);
		$this->assign("appname",$appname);
		
		$this->assign("uid",$uid);
		$this->assign("token",$token);
		
		if($auth['status']==1){
			$this->display('success2');
			exit;
		}else{
			$this->display();
		}
	}
	function success2(){
		$this->display();
	}


	/*认证保存*/
	public function auth_save(){

		$rs=array("code"=>0,"msg"=>"","info"=>array());
		$uid=I("uid");
		$realname=I("realname");
		$phone=I("phone");
		$cardno=I("cardno");

		if($realname==""){
			$rs['code']=1001;
			$rs['msg']=LangT('请输入您的名字');
			return json_encode($rs);
		}
		if($phone==""){
			$rs['code']=1002;
			$rs['msg']=LangT("请输入11位手机号码<br />如:01012345678");
			return json_encode($rs);
		}
		if($cardno==""){
			$rs['code']=1003;
			$rs['msg']=LangT('请输入12位或13号身份证件号码<br />如:1234561234567');
			return json_encode($rs);
		}


		$data['uid']=$uid;
		$data['real_name']=$realname;
		$data['mobile']=$phone;
		$data['cer_no']=$cardno;
		$data['status']=0;
		$data['addtime']=time();

		$authid=M("users_auth")->where("uid='{$uid}'")->getField("uid");

		if($authid){
			$result=M("users_auth")->where("uid='{$authid}'")->save($data);
		}else{
			$result=M("users_auth")->add($data);
		}

		if($result!==false){
			$rs['msg']=LangT("认证成功");
		}else{
			$rs['code']=1003;
			$rs['msg']=LangT("认证失败，请重新提交");
		}

		echo json_encode($rs);

	}	
}