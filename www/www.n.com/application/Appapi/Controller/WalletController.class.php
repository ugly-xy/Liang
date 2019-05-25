<?php
/**
 * 我的钱包
 */
namespace Appapi\Controller;
use Common\Controller\HomebaseController;
class WalletController extends HomebaseController {
	
	// 充提币记录
	public function index(){
        $uid=checkNull(I('uid'));
        $token=checkNull(I('token'));
        if(checkToken($uid,$token)==700){
			$this->assign("reason",LangT('您的登陆状态失效，请重新登陆！'));
			$this->display(':error');
			exit;
		}
        
		$touids=M("token_order")
				->field("id,txhash,address,amount,withdraw_fee,fast_fee,withdraw,is_fast,status,type,uptime")
				->where("uid={$uid}")
				->order("uptime desc")
				->select();
		
		foreach($touids as $k=>$v){
			$touids[$k]['uptime']=date("Y-m-d H:i",$v['uptime']);
			$touids[$k]['uptimes']=date("Y.m.d H:i",$v['uptime']);
			if($v['is_fast']==1){
				$touids[$k]['is_fast_text']=LangT('加速');
			}else{
				$touids[$k]['is_fast_text']=LangT('不加速');
			}
			
			if($v['status']==0){
				$touids[$k]['status_text']=LangT('申请中');
			}else if($v['status']==1){
				$touids[$k]['status_text']=LangT('成功');
			}else if($v['status']==2){
				$touids[$k]['status_text']=LangT('失败');
			}else if($v['status']==3){
				$touids[$k]['status_text']=LangT('退回');
			}
			
			$touids[$k]['txhash_jie']=msubstr($v['txhash'],0,18);
			$touids[$k]['address_jie']=msubstr($v['address'],0,18);
			$touids[$k]['amount']=number_format($v['amount'],2);
			$touids[$k]['withdraw_fee']=number_format($v['withdraw_fee'],2);
			$touids[$k]['fast_fee']=number_format($v['fast_fee'],2);
			$touids[$k]['withdraw']=number_format($v['withdraw'],2);
			
		}		
		$touids=array_values($touids);
        $config=getConfigPub();
		$this->assign("config",$config);
        $this->assign("touids",$touids);
		$this->display();
	}
	
	//消费记录
	public function consumer(){
        $uid=checkNull(I('uid'));
        $token=checkNull(I('token'));
        if(checkToken($uid,$token)==700){
			$this->assign("reason",LangT('您的登陆状态失效，请重新登陆！'));
			$this->display(':error');
			exit;
		}
		$this->assign("uid",$uid);	
		$this->assign("token",$token);
		// $action=array("sendgift"=>"直播间赠送礼物","sendbarrage"=>"弹幕","loginbonus"=>"登录奖励","buyvip"=>"购买VIP","buycar"=>"购买坐骑","buyliang"=>"购买靓号","sharereward"=>"分享奖励",'game_bet'=>'游戏下注','game_return'=>'游戏返还','game_win'=>'游戏获胜','game_banker'=>'庄家收益','roomcharge'=>'房间扣费','timecharge'=>'计时扣费','sendred'=>'发送红包','robred'=>'抢红包','buyguard'=>'开通守护','giftvideo'=>'短视频赠送礼物');
		$action=LangT('xtype');

        
		$Coinrecird=M("users_coinrecord");
		$list=$Coinrecird->field("action,touid,totalcoin,addtime")->where("type='expend' and uid={$uid}")->order("addtime desc")->limit(0,20)->select();
		foreach($list as $k=>$v){
			$userinfo=getUserInfo($v['touid']);
			/*if($v['action']=='sendred'){
				$userinfo=getUserInfo($v['uid']);
			}*/
			
			if(!$userinfo){
				$userinfo=array(
					"user_nicename"=>LangT('用户已删除')
				);
			}
			$list[$k]['userinfo']=$userinfo;
			$list[$k]['addtime']=date("Y.m.d H:i",$v['addtime']);
		}
		$this->assign("action",$action);	
		$this->assign("list",$list);	
        $config=getConfigPub();
		$this->assign("config",$config);
		$this->display();
	}
	
	public function consumer_more(){
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
		$pnums=20;
		$start=($p-1)*$pnums;
		
		// $action=array("sendgift"=>"直播间赠送礼物","sendbarrage"=>"弹幕","loginbonus"=>"登录奖励","buyvip"=>"购买VIP","buycar"=>"购买坐骑","buyliang"=>"购买靓号","sharereward"=>"分享奖励",'game_bet'=>'游戏下注','game_return'=>'游戏返还','game_win'=>'游戏获胜','game_banker'=>'庄家收益','roomcharge'=>'房间扣费','timecharge'=>'计时扣费','sendred'=>'发送红包','robred'=>'抢红包','buyguard'=>'开通守护','giftvideo'=>'短视频赠送礼物');
		$action=LangT('xtype');
        
        
		$Coinrecird=M("users_coinrecord");
		$list=$Coinrecird->field("action,touid,totalcoin,addtime")
			->where("type='expend' and uid={$uid}")
			->order("addtime desc")
			->limit($start,$pnums)
			->select();
		foreach($list as $k=>$v){
			$userinfo=getUserInfo($v['touid']);
			if(!$userinfo){
				$userinfo=array(
					"user_nicename"=>LangT('用户已删除')
				);
			}
			$list[$k]['userinfo']=$userinfo;
			$list[$k]['action']=$action[$v['action']];
			$list[$k]['addtime']=date("Y.m.d H:i",$v['addtime']);
		}
		
		$nums=count($list);
		if($nums<$pnums){
			$isscroll=0;
		}else{
			$isscroll=1;
		}
		
		$result=array(
			'data'=>$list,
			'nums'=>$nums,
			'isscroll'=>$isscroll,
		);

		echo json_encode($result);
		exit;
	}
	
	//收入记录
	public function income(){
        $uid=checkNull(I('uid'));
        $token=checkNull(I('token'));
        if(checkToken($uid,$token)==700){
			$this->assign("reason",LangT('您的登陆状态失效，请重新登陆！'));
			$this->display(':error');
			exit;
		}
		$this->assign("uid",$uid);	
		$this->assign("token",$token);
		// $action=array("sendgift"=>"直播间赠送礼物","sendbarrage"=>"弹幕","loginbonus"=>"登录奖励","buyvip"=>"购买VIP","buycar"=>"购买坐骑","buyliang"=>"购买靓号","sharereward"=>"分享奖励",'game_bet'=>'游戏下注','game_return'=>'游戏返还','game_win'=>'游戏获胜','game_banker'=>'庄家收益','roomcharge'=>'房间扣费','timecharge'=>'计时扣费','sendred'=>'发送红包','robred'=>'抢红包','buyguard'=>'开通守护','giftvideo'=>'短视频赠送礼物');
		$action=LangT('stype');
        
        
		$Coinrecird=M("users_coinrecord");
		$list=$Coinrecird->field("action,uid,totalcoin,addtime")->where("(type='expend' or action='mining' or action='robred') and action!='sendred' and touid={$uid}")->order("addtime desc")->limit(0,20)->select();
		foreach($list as $k=>$v){
			$userinfo=getUserInfo($v['uid']);
			if(!$userinfo){
				$userinfo=array(
					"user_nicename"=>LangT('用户已删除')
				);
			}
			$list[$k]['userinfo']=$userinfo;
			$list[$k]['addtime']=date("Y.m.d H:i",$v['addtime']);
		}
		$this->assign("action",$action);	
		$this->assign("list",$list);	
        $config=getConfigPub();
		$this->assign("config",$config);
		$this->display();
	}
	
	public function income_more(){
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
		$pnums=20;
		$start=($p-1)*$pnums;
		// $action=array("sendgift"=>"直播间赠送礼物","sendbarrage"=>"弹幕","loginbonus"=>"登录奖励","buyvip"=>"购买VIP","buycar"=>"购买坐骑","buyliang"=>"购买靓号","sharereward"=>"分享奖励",'game_bet'=>'游戏下注','game_return'=>'游戏返还','game_win'=>'游戏获胜','game_banker'=>'庄家收益','roomcharge'=>'房间扣费','timecharge'=>'计时扣费','sendred'=>'发送红包','robred'=>'抢红包','buyguard'=>'开通守护','giftvideo'=>'短视频赠送礼物');
		$action=LangT('stype');
        
      
		$Coinrecird=M("users_coinrecord");
		$list=$Coinrecird->field("action,uid,totalcoin,addtime")
			->where("(type='expend' or action='mining' or action='robred') and action!='sendred' and touid={$uid}")
			->order("addtime desc")
			->limit($start,$pnums)
			->select();
		foreach($list as $k=>$v){
			$userinfo=getUserInfo($v['uid']);
			if(!$userinfo){
				$userinfo=array(
					"user_nicename"=>LangT('用户已删除')
				);
			}
			$list[$k]['userinfo']=$userinfo;
			$list[$k]['action']=$action[$v['action']];
			$list[$k]['addtime']=date("Y.m.d H:i",$v['addtime']);
		}
		
		$nums=count($list);
		if($nums<$pnums){
			$isscroll=0;
		}else{
			$isscroll=1;
		}
		
		$result=array(
			'data'=>$list,
			'nums'=>$nums,
			'isscroll'=>$isscroll,
		);

		echo json_encode($result);
		exit;
	}
	
	//关于资金池
	public function pooloffunds(){
		$uid=checkNull(I('uid'));
        $token=checkNull(I('token'));
        if(checkToken($uid,$token)==700){
			$this->assign("reason",LangT('您的登陆状态失效，请重新登陆！'));
			$this->display(':error');
			exit;
		}
		$user=M("config_private")->field("pooloffunds")->where("id=1")->find();
		if($user){
			$user['coin']=number_format($user['pooloffunds'],2);
		}
		$Coinrecird=M("money_destruction");
		$list=$Coinrecird->order("addtime desc")->limit(0,50)->select();
		foreach($list as $k=>$v){
			$list[$k]['money']=number_format($v['money'],2);
			$list[$k]['addtime']=date("Y.m.d H:i",$v['addtime']);
		}
		$this->assign("user",$user);
		$this->assign("list",$list);
		$this->display();
	}
	
	public function pooloffunds_more(){
		$p=I('page');
		$pnums=20;
		$start=($p-1)*$pnums;
		$Coinrecird=M("money_destruction");
		$list=$Coinrecird->order("addtime desc")->limit($start,$pnums)->select();
		foreach($list as $k=>$v){
			$list[$k]['money']=number_format($v['money'],2);
			$list[$k]['addtime']=date("Y.m.d H:i",$v['addtime']);
		}
		$nums=count($list);
		if($nums<$pnums){
			$isscroll=0;
		}else{
			$isscroll=1;
		}
		
		$result=array(
			'data'=>$list,
			'nums'=>$nums,
			'isscroll'=>$isscroll,
		);
		echo json_encode($result);
		exit;
	}
	
	/*交易所*/
	public function exchange(){
		
		$Coinrecird=M("exchange");
		$list=$Coinrecird->order("addtime desc")->select();
		foreach($list as $k=>$v){
			$list[$k]['addtime']=date("Y.m.d H:i",$v['addtime']);
		}
		
		$this->assign("list",$list);
		$this->display();
	}
	
	

}