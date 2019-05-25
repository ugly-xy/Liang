<?php
/**
 * 邀请明细
 */
namespace Appapi\Controller;
use Common\Controller\HomebaseController;
class InvitationController extends HomebaseController {
	function invitation(){       
		$uid=I("uid");
		$token=I("token");
		
		if( !$uid || !$token || checkToken($uid,$token)==700 ){
			$this->assign("reason",LangT('您的登陆状态失效，请重新登陆！'));
			$this->display(':error');
			exit;
		}
		$this->assign("uid",$uid);
		$this->assign("token",$token);
		$lists=M("users_invitation")
			->where("touid={$uid}")
			->order("addtime desc")
			->limit(0,5)
			->select();
		foreach($lists as $k=>$v){
			$userinfo=getUserInfo($v['uid']);
			if(!$userinfo){
				$userinfo=array(
					"avatar"=>'',
					"user_nicename"=>LangT('用户已删除'),
				);
			}
			$lists[$k]['userinfo']=$userinfo;
			$lists[$k]['addtime']=date("Y-m-d",$v['addtime']);
		}		
		$config=getConfigPub();
		$this->assign("config",$config);
		$this->assign("lists",$lists);
		$this->display();
	}
	
	public function invitation_more()
	{
		$uid=I('uid');
		$token=I('token');
		
		$result=array(
			'data'=>array(),
			'nums'=>0,
			'isscroll'=>0,
		);
	
		if(checkToken($uid,$token)==700){
			echo json_encode($result);
			exit;
		} 
		
		$p=I('page');
		$pnums=5;
		$start=($p-1)*$pnums;
		
		$lists=M("users_invitation")
			->where("touid={$uid}")
			->order("addtime desc")
			->limit($start,$pnums)
			->select();
		
		foreach($lists as $k=>$v){
			$userinfo=getUserInfo($v['uid']);
			if(!$userinfo){
				$userinfo=array(
					"user_nicename"=>LangT('用户已删除')
				);
			}
			$lists[$k]['userinfo']=$userinfo;
			$lists[$k]['addtime']=date("Y-m-d",$v['addtime']);
		}

		$nums=count($lists);
		if($nums<$pnums){
			$isscroll=0;
		}else{
			$isscroll=1;
		}
		
		$result=array(
			'data'=>$lists,
			'nums'=>$nums,
			'isscroll'=>$isscroll,
		);

		echo json_encode($result);
		exit;
	}

}