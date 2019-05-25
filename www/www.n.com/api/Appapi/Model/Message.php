<?php

class Model_Message extends PhalApi_Model_NotORM {
	/* 信息列表 */
	public function getList($uid,$p) {
		$pnum=50;
		$start=($p-1)*$pnum;
        
		$list=DI()->notorm->pushrecord
                    ->select('content,addtime')
                    ->where("touid='' or( touid!='' and (touid = '{$uid}' or touid like '{$uid},%' or touid like '%,{$uid},%' or touid like '%,{$uid}') )")
                    ->order('addtime desc')
                    ->limit($start,$pnum)
                    ->fetchAll();

		return $list;
	}

	/*获取粉丝关注信息列表*/
	public function fansLists($uid,$p){
		$nums=20;
		$start=($p-1)*$nums;
		$list=DI()->notorm->users_attention_messages->select("*")->where("touid=?",$uid)->order("addtime desc")->limit($start,$nums)->fetchAll();
		if(!$list){
			return 0;
		}
		foreach ($list as $k => $v) {
			$list[$k]['addtime']=datetime($v['addtime']);
			unset($list[$k]['touid']);
			$userinfo=getUserInfo($v['uid']);
			$list[$k]['userinfo']=$userinfo;
			$list[$k]['isattention']=isAttention($uid,$v['uid']);
		}

		return $list;
	}

	public function praiseLists($uid,$p){
		$nums=20;
		$start=($p-1)*$nums;
		$list=DI()->notorm->praise_messages->select("*")->where("touid='{$uid}' and status=1")->order("addtime desc")->limit($start,$nums)->fetchAll();

		if(!$list){
			return 0;
		}

		foreach ($list as $k => $v) {
			$list[$k]['addtime']=datetime($v['addtime']);
			unset($list[$k]['touid']);
			$userinfo=DI()->notorm->users->select("id,avatar,user_nicename")->where("id='{$v['uid']}'")->fetchOne();
			$list[$k]['user_nicename']=$userinfo['user_nicename'];
			$list[$k]['avatar']=get_upload_path($userinfo['avatar']);
			
			$video=DI()->notorm->users_video->select("*")->where("id=?",$v['videoid'])->fetchOne();
			
			$userinfo=getUserInfo($video['uid']);
			if(!$userinfo){
				$userinfo['user_nicename']=T("已删除");
			}
			$video['userinfo']=$userinfo;
			$video['datetime']=datetime($video['addtime']);	
			$video['comments']=NumberFormat($video['comments']);	
			$video['likes']=NumberFormat($video['likes']);	
			$video['steps']=NumberFormat($video['steps']);	
			if($video['uid']>0){
				$video['islike']=(string)ifLike($video['uid'],$video['id']);	
				$video['isstep']=(string)ifStep($video['uid'],$video['id']);	
				$video['isattent']=(string)isAttention($uid,$video['uid']);	
			}else{
				$video['islike']=0;	
				$video['isstep']=0;	
				$video['isattent']=0;	
			}

			$video['musicinfo']=getMusicInfo($video['userinfo']['user_nicename'],$video['music_id']);	
			$video['thumb']=get_upload_path($video['thumb']);
			$video['thumb_s']=get_upload_path($video['thumb_s']);
			$video['href']=get_upload_path($video['href']);
			
			$list[$k]['video_info'] = $video;
			
			$list[$k]['videouid']=$video['uid'];
			 
			
		}

		return $list;
	}

	public function atLists($uid,$p){
		$nums=20;
		$start=($p-1)*$nums;
		$list=DI()->notorm->users_video_comments_at_messages->select("*")->where("touid='{$uid}' and status=1")->order("addtime desc")->limit($start,$nums)->fetchAll();
		if(!$list){
			return 0;
		}

		foreach ($list as $k => $v) {
			$userinfo=DI()->notorm->users->select("avatar,user_nicename")->where("id='{$v['uid']}'")->fetchOne();
			$list[$k]['avatar']=get_upload_path($userinfo['avatar']);
			$list[$k]['user_nicename']=$userinfo['user_nicename'];
			$video=DI()->notorm->users_video->select("*")->where("id='{$v['videoid']}'")->fetchOne();
			$list[$k]['video_title']=$video['title'];
			$list[$k]['video_thumb']=$video['thumb'];
			$list[$k]['addtime']=datetime($v['addtime']);
			 
			
			$userinfo=getUserInfo($video['uid']);
			if(!$userinfo){
				$userinfo['user_nicename']=T("已删除");
			}
			$video['userinfo']=$userinfo;
			$video['datetime']=datetime($video['addtime']);	
			$video['comments']=NumberFormat($video['comments']);	
			$video['likes']=NumberFormat($video['likes']);	
			$video['steps']=NumberFormat($video['steps']);	
			if($video['uid']>0){
				$video['islike']=(string)ifLike($video['uid'],$video['id']);	
				$video['isstep']=(string)ifStep($video['uid'],$video['id']);	
				$video['isattent']=(string)isAttention($uid,$video['uid']);	
			}else{
				$video['islike']=0;	
				$video['isstep']=0;	
				$video['isattent']=0;	
			}

			$video['musicinfo']=getMusicInfo($video['userinfo']['user_nicename'],$video['music_id']);	
			$video['thumb']=get_upload_path($video['thumb']);
			$video['thumb_s']=get_upload_path($video['thumb_s']);
			$video['href']=get_upload_path($video['href']);
			
			$list[$k]['video_info'] = $video;
			
			$list[$k]['videouid']=$video['uid'];
		}

		return $list;

	}

	public function commentLists($uid,$p){
		$nums=20;
		$start=($p-1)*$nums;
		$list=DI()->notorm->users_video_comments_messages->select("*")->where("touid='{$uid}' and status=1")->order("addtime desc")->limit($start,$nums)->fetchAll();
		if(!$list){
			return 0;
		}

		foreach ($list as $k => $v) {
			$video=DI()->notorm->users_video->select("*")->where("id='{$v['videoid']}'")->fetchOne();
			$list[$k]['addtime']=datetime($v['addtime']);
			$list[$k]['video_thumb']=$video['thumb'];
			$userinfo=DI()->notorm->users->select("avatar,user_nicename")->where("id='{$v['uid']}'")->fetchOne();
			$list[$k]['user_nicename']=$userinfo['user_nicename'];
			$list[$k]['avatar']=get_upload_path($userinfo['avatar']);
			
			 
			$userinfo=getUserInfo($video['uid']);
			if(!$userinfo){
				$userinfo['user_nicename']=T("已删除");
			}
			$video['userinfo']=$userinfo;
			$video['datetime']=datetime($video['addtime']);	
			$video['comments']=NumberFormat($video['comments']);	
			$video['likes']=NumberFormat($video['likes']);	
			$video['steps']=NumberFormat($video['steps']);	
			if($video['uid']>0){
				$video['islike']=(string)ifLike($video['uid'],$video['id']);	
				$video['isstep']=(string)ifStep($video['uid'],$video['id']);	
				$video['isattent']=(string)isAttention($uid,$video['uid']);	
			}else{
				$video['islike']=0;	
				$video['isstep']=0;	
				$video['isattent']=0;	
			}

			$video['musicinfo']=getMusicInfo($video['userinfo']['user_nicename'],$video['music_id']);	
			$video['thumb']=get_upload_path($video['thumb']);
			$video['thumb_s']=get_upload_path($video['thumb_s']);
			$video['href']=get_upload_path($video['href']);
			
			$list[$k]['video_info'] = $video;
			
			$list[$k]['videouid']=$video['uid'];
		}

		return $list;
	}

	/*官方通知列表*/
	public function officialLists($p){
		$nums=20;
		$start=($p-1)*$nums;

		$list=DI()->notorm->admin_push->select("id,title,synopsis,type,url,addtime")->order("addtime desc")->limit($start,$nums)->fetchAll();


		if(!$list){
			return 0;
		}


		//获取公共配置
		$config=getConfigPub();
		foreach ($list as $k => $v) {
			if($v['type']==1){
				$list[$k]['url']=$config['site'].'/index.php?g=Appapi&m=Message&a=msginfo&id='.$v['id'];	
			}

			$list[$k]['addtime']=datetime($v['addtime']);

			unset($list[$k]['type']);
		}

		return $list;
	}

	/*用户的系统通知列表*/
	public function systemnotifyLists($uid,$p){

		$nums=20;
		$start=($p-1)*$nums;
		$list=DI()->notorm->system_push->select("id,title,content,addtime")->where("uid=?",$uid)->order("addtime desc")->limit($start,$nums)->fetchAll();

		if(!$list){
			return 0;
		}

		foreach ($list as $k => $v) {
			$list[$k]['addtime']=datetime($v['addtime']);
		}

		return $list;
	}

	/*获取用户系统消息最新时间*/
	public function getLastTime($uid){

		$res=array();

		$sysInfo=DI()->notorm->system_push->where("uid=?",$uid)->order("addtime desc")->fetchOne();
		$officeInfo=DI()->notorm->admin_push->order("addtime desc")->fetchOne();

		if($sysInfo){
			$res['sysInfo']	=$sysInfo;
		}

		if($officeInfo){
			$res['officeInfo']	=$officeInfo;
		}
		
		

		return (object)$res;
	}

}
