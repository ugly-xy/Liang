<?php

class Model_Video extends PhalApi_Model_NotORM {
	/* 发布视频 */
	public function setVideo($data,$music_id) {
		$uid=$data['uid'];

		//获取后台配置的初始曝光值
		$configPri=getConfigPri();
		$data['show_val']=$configPri['show_val'];

		if($configPri['video_audit_switch']==0){
			$data['status']=1;
		}

		$result= DI()->notorm->users_video->insert($data);
		
		
		/***..............加权重值******************/
		$today = strtotime(date("Y-m-d"),time());
		$upload_video=$configPri['mining_uploadvideo']; //上传视频所得权重值
		
		//用户当天总权重值
		$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$uid,$today)->update(array( "weight"=>new NotORM_Literal("weight + {$upload_video}")));
		if(!$userweight){
			$userweight=DI()->notorm->users_total_weight->insert(array('uid'=>$uid,'weight'=>$upload_video,'addtime'=>$today));
		}
		
		
		//用户当天权重值详情
		$weight_info=DI()->notorm->users_weight_info->where('uid=? and addtime=?',$uid,$today)->update(array( "upload_video"=>new NotORM_Literal("upload_video + {$upload_video}")));
		if(!$weight_info){
			//挖矿-签到添加
			if(!$userweight['id']){
			 	$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$uid,$today)->fetchOne();
			}
			DI()->notorm->users_weight_info->insert(array('uid'=>$uid,'weight_id'=>$userweight['id'],'upload_video'=>$upload_video,'addtime'=>$today));
		}
		/**************************************************/
		

		if($music_id>0){ //更新背景音乐被使用次数
			DI()->notorm->users_music
            ->where("id = '{$music_id}'")
		 	->update( array('use_nums' => new NotORM_Literal("use_nums + 1") ) );
		}
		
		return $result;
	}	

	/* 评论/回复 */
    public function setComment($data) {
    	$videoid=$data['videoid'];
		$uid=$data['uid'];
		/* 更新 视频 */
		DI()->notorm->users_video
            ->where("id = '{$videoid}'")
		 	->update( array('comments' => new NotORM_Literal("comments + 1") ) );
		
		
		
		
        DI()->notorm->users_video_comments
            ->insert($data);
			
		$videoinfo=DI()->notorm->users_video
					->select("uid,comments")
					->where('id=?',$videoid)
					->fetchOne();
					
		if($data['commentid']==0 && $data['parentid']==0){
			//私密设置
			$configpri=getConfigPri();
			$today = strtotime(date("Y-m-d"),time());
			$comment=$configpri['mining_comment']; //挖矿-给用户视频评论
			$collect_comment=$configpri['mining_collectcomment']; //挖矿-被用户视频评论
			
			/***..............加权重值******************/
			//用户当天总权重值
			$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$uid,$today)->update(array( "weight"=>new NotORM_Literal("weight + {$comment}")));
			if(!$userweight){
				$userweight=DI()->notorm->users_total_weight->insert(array('uid'=>$uid,'weight'=>$comment,'addtime'=>$today));
			}
			
			//用户当天权重值详情
			$weight_info=DI()->notorm->users_weight_info->where('uid=? and addtime=?',$uid,$today)->update(array( "comment"=>new NotORM_Literal("comment + {$comment}")));
			if(!$weight_info){
				//挖矿-签到添加
				if(!$userweight['id']){
					$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$uid,$today)->fetchOne();
				}
				DI()->notorm->users_weight_info->insert(array('uid'=>$uid,'weight_id'=>$userweight['id'],'comment'=>$comment,'addtime'=>$today));
			}
			
			//收评论的用户
			$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$videoinfo['uid'],$today)->update(array( "weight"=>new NotORM_Literal("weight + {$collect_comment}")));
			if(!$userweight){
				$userweight=DI()->notorm->users_total_weight->insert(array('uid'=>$videoinfo['uid'],'weight'=>$collect_comment,'addtime'=>$today));
			}
			//用户当天权重值详情
			$weight_info=DI()->notorm->users_weight_info->where('uid=? and addtime=?',$videoinfo['uid'],$today)->update(array( "collect_comment"=>new NotORM_Literal("collect_comment + {$collect_comment}")));
			if(!$weight_info){
				//挖矿-收评论的用户
				if(!$userweight['id']){
					$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$videoinfo['uid'],$today)->fetchOne();
				}
				DI()->notorm->users_weight_info->insert(array('uid'=>$videoinfo['uid'],'weight_id'=>$userweight['id'],'collect_comment'=>$collect_comment,'addtime'=>$today));
			}
			/**************************************************/
		}
					
		$count=DI()->notorm->users_video_comments
					->where("commentid='{$data['commentid']}'")
					->count();
		$rs=array(
			'comments'=>$videoinfo['comments'],
			'replys'=>$count,
		);
		
		//如果有人发评论@了其他人，写入评论@记录
		$arr=json_decode($data['at_info'],true); //将json串转为数组

		if(!empty($arr)){

			$data1=array("videoid"=>$data['videoid'],"addtime"=>time(),"uid"=>$data['uid']);
			foreach ($arr as $k => $v) {
				$data1['touid']=$v['uid'];
				DI()->notorm->users_video_comments_at_messages->insert($data1);
			}
		}

		//直接对视频进行的评论，向评论信息表中写入记录
		if($data['commentid']==0){
			$data2=array("uid"=>$data['uid'],"touid"=>$data['touid'],"videoid"=>$data['videoid'],"content"=>$data['content'],"addtime"=>time());
			DI()->notorm->users_video_comments_messages->insert($data2);
		}	
		

		return $rs;	
    }			

	/* 阅读 */
	public function addView($uid,$videoid){
		/*$view=DI()->notorm->users_video_view
				->select("id")
				->where("uid='{$uid}' and videoid='{$videoid}'")
				->fetchOne();

		if(!$view){
			DI()->notorm->users_video_view
						->insert(array("uid"=>$uid,"videoid"=>$videoid,"addtime"=>time() ));
						
			DI()->notorm->users_video
				->where("id = '{$videoid}'")
				->update( array('view' => new NotORM_Literal("view + 1") ) );
		}*/

		/*//用户看过的视频存入redis中
		$readLists=DI()->redis -> Get('readvideo_'.$uid);
		$readArr=array();
		if($readLists){
			$readArr=json_decode($readLists,true);
			if(!in_array($videoid,$readArr)){
				$readArr[]=$videoid;
			}
		}else{
			$readArr[]=$videoid;
		}

		DI()->redis -> Set('readvideo_'.$uid,json_encode($readArr));*/

		DI()->notorm->users_video
				->where("id = '{$videoid}'")
				->update( array('views' => new NotORM_Literal("views + 1") ) );

		return 0;
	}
	/* 点赞 */
	public function addLike($uid,$videoid){
		$rs=array(
			'islike'=>'0',
			'likes'=>'0',
		);
		$video=DI()->notorm->users_video
				->select("likes,uid,thumb")
				->where("id = '{$videoid}'")
				->fetchOne();

		if(!$video){
			return 1001;
		}
		if($video['uid']==$uid){
			return 1002;//不能给自己点赞
		}
		//私密设置
		$configpri=getConfigPri();
		$today = strtotime(date("Y-m-d"),time());
		$praise=$configpri['mining_spotzan']; //挖矿-给用户点赞
		$collect_praise=$configpri['mining_collectzan']; //挖矿-收用户点赞
		
		$like=DI()->notorm->users_video_like
						->select("id")
						->where("uid='{$uid}' and videoid='{$videoid}'")
						->fetchOne();
		if($like){
			DI()->notorm->users_video_like
						->where("uid='{$uid}' and videoid='{$videoid}'")
						->delete();
			
			DI()->notorm->users_video
				->where("id = '{$videoid}' and likes>0")
				->update( array('likes' => new NotORM_Literal("likes - 1") ) );
			$rs['islike']='0';
			
			/******************减权重值******************/
			//用户当天总权重值
			$zong=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$uid,$today)->fetchOne();
			if($zong){
				DI()->notorm->users_total_weight->where('id=? and weight>0',$zong['id'])->update(array( "weight"=>new NotORM_Literal("weight - {$praise}")));
			}
			//用户当天权重值详情
			
			$info=DI()->notorm->users_weight_info->where('uid=? and addtime=?',$uid,$today)->fetchOne();
			if($info){
				DI()->notorm->users_weight_info->where('id=? and praise>0',$info['id'])->update(array( "praise"=>new NotORM_Literal("praise - {$praise}")));
			}
			
			//收点赞的用户
			$shouzong=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$video['uid'],$today)->fetchOne();
			if($shouzong){
				DI()->notorm->users_total_weight->where('id=? and weight>0',$shouzong['id'])->update(array( "weight"=>new NotORM_Literal("weight - {$collect_praise}")));
			}
			//用户当天权重值详情
			$shouinfo=DI()->notorm->users_weight_info->where('uid=? and addtime=?',$video['uid'],$today)->fetchOne();
			if($shouinfo){
				DI()->notorm->users_weight_info->where('id=? and collect_praise>0',$shouinfo['id'])->update(array( "collect_praise"=>new NotORM_Literal("collect_praise - {$collect_praise}")));
			}
			/**************************************************/
			
			
		}else{
			DI()->notorm->users_video_like
						->insert(array("uid"=>$uid,"videoid"=>$videoid,"addtime"=>time() ));
			
			DI()->notorm->users_video
				->where("id = '{$videoid}'")
				->update( array('likes' => new NotORM_Literal("likes + 1") ) );
			$rs['islike']='1';
			
			
			/***..............加权重值******************/
			//用户当天总权重值
			$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$uid,$today)->update(array( "weight"=>new NotORM_Literal("weight + {$praise}")));
			if(!$userweight){
				$userweight=DI()->notorm->users_total_weight->insert(array('uid'=>$uid,'weight'=>$praise,'addtime'=>$today));
			}
			//用户当天权重值详情
			$weight_info=DI()->notorm->users_weight_info->where('uid=? and addtime=?',$uid,$today)->update(array( "praise"=>new NotORM_Literal("praise + {$praise}")));
			if(!$weight_info){
				//挖矿-签到添加
				if(!$userweight['id']){
					$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$uid,$today)->fetchOne();
				}
				DI()->notorm->users_weight_info->insert(array('uid'=>$uid,'weight_id'=>$userweight['id'],'praise'=>$praise,'addtime'=>$today));
			}
			
			//收点赞的用户
			$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$video['uid'],$today)->update(array( "weight"=>new NotORM_Literal("weight + {$collect_praise}")));
			if(!$userweight){
				$userweight=DI()->notorm->users_total_weight->insert(array('uid'=>$video['uid'],'weight'=>$collect_praise,'addtime'=>$today));
			}
			//用户当天权重值详情
			$weight_info=DI()->notorm->users_weight_info->where('uid=? and addtime=?',$video['uid'],$today)->update(array( "collect_praise"=>new NotORM_Literal("collect_praise + {$collect_praise}")));
			if(!$weight_info){
				//挖矿-收点赞添加
				if(!$userweight['id']){
					$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$video['uid'],$today)->fetchOne();
				}
				DI()->notorm->users_weight_info->insert(array('uid'=>$video['uid'],'weight_id'=>$userweight['id'],'collect_praise'=>$collect_praise,'addtime'=>$today));
			}
			/**************************************************/
			
		}	
		
		$video=DI()->notorm->users_video
				->select("likes,uid,thumb")
				->where("id = '{$videoid}'")
				->fetchOne();
				
		$rs['likes']=$video['likes'];
		
		//获取视频点赞信息列表
		$fabulous=DI()->notorm->praise_messages->where("uid='{$uid}' and obj_id='{$videoid}' and type=1")->fetchOne();
		if(!$fabulous){
			DI()->notorm->praise_messages->insert(array("uid"=>$uid,"touid"=>$video['uid'],"obj_id"=>$videoid,"videoid"=>$videoid,"addtime"=>time(),"type"=>1,"video_thumb"=>$video['thumb']));
		}else{
			DI()->notorm->praise_messages->where("uid='{$uid}' and type=1 and obj_id='{$videoid}'")->update(array("addtime"=>time()));
		}
		
		return $rs; 		
	}

	/* 踩 */
	public function addStep($uid,$videoid){
		$rs=array(
			'isstep'=>'0',
			'steps'=>'0',
		);
		$like=DI()->notorm->users_video_step
						->select("id")
						->where("uid='{$uid}' and videoid='{$videoid}'")
						->fetchOne();
		if($like){
			DI()->notorm->users_video_step
						->where("uid='{$uid}' and videoid='{$videoid}'")
						->delete();
			
			DI()->notorm->users_video
				->where("id = '{$videoid}' and steps>0")
				->update( array('steps' => new NotORM_Literal("steps - 1") ) );
			$rs['isstep']='0';
		}else{
			DI()->notorm->users_video_step
						->insert(array("uid"=>$uid,"videoid"=>$videoid,"addtime"=>time() ));
			
			DI()->notorm->users_video
				->where("id = '{$videoid}'")
				->update( array('steps' => new NotORM_Literal("steps + 1") ) );
			$rs['isstep']='1';
		}	
		
		$video=DI()->notorm->users_video
				->select("steps")
				->where("id = '{$videoid}'")
				->fetchOne();
		$rs['steps']=$video['steps'];
		return $rs; 		
	}

	/* 分享 */
	public function addShare($uid,$videoid){

		
		$rs=array(
			'isshare'=>'0',
			'shares'=>'0',
		);
		DI()->notorm->users_video
			->where("id = '{$videoid}'")
			->update( array('shares' => new NotORM_Literal("shares + 1") ) );
		$rs['isshare']='1';

		
		$video=DI()->notorm->users_video
				->select("shares")
				->where("id = '{$videoid}'")
				->fetchOne();
		$rs['shares']=$video['shares'];
		
		return $rs; 		
	}

	/* 拉黑视频 */
	public function setBlack($uid,$videoid){
		$rs=array(
			'isblack'=>'0',
		);
		$like=DI()->notorm->users_video_black
						->select("id")
						->where("uid='{$uid}' and videoid='{$videoid}'")
						->fetchOne();
		if($like){
			DI()->notorm->users_video_black
						->where("uid='{$uid}' and videoid='{$videoid}'")
						->delete();
			$rs['isshare']='0';
		}else{
			DI()->notorm->users_video_black
						->insert(array("uid"=>$uid,"videoid"=>$videoid,"addtime"=>time() ));
			$rs['isshare']='1';
		}	
		return $rs; 		
	}


	/* 评论/回复 点赞 */
	public function addCommentLike($uid,$commentid){
		$rs=array(
			'islike'=>'0',
			'likes'=>'0',
		);

		//根据commentid获取对应的评论信息
		$commentinfo=DI()->notorm->users_video_comments
			->where("id='{$commentid}'")
			->fetchOne();

		if(!$commentinfo){
			return 1001;
		}

		$like=DI()->notorm->users_video_comments_like
			->select("id")
			->where("uid='{$uid}' and commentid='{$commentid}'")
			->fetchOne();

		if($like){
			DI()->notorm->users_video_comments_like
						->where("uid='{$uid}' and commentid='{$commentid}'")
						->delete();
			
			DI()->notorm->users_video_comments
				->where("id = '{$commentid}' and likes>0")
				->update( array('likes' => new NotORM_Literal("likes - 1") ) );
			$rs['islike']='0';

		}else{
			DI()->notorm->users_video_comments_like
						->insert(array("uid"=>$uid,"commentid"=>$commentid,"addtime"=>time(),"touid"=>$commentinfo['uid'],"videoid"=>$commentinfo['videoid'] ));
			
			DI()->notorm->users_video_comments
				->where("id = '{$commentid}'")
				->update( array('likes' => new NotORM_Literal("likes + 1") ) );
			$rs['islike']='1';
		}	
		
		$video=DI()->notorm->users_video_comments
				->select("likes")
				->where("id = '{$commentid}'")
				->fetchOne();

		//获取视频信息
		$videoinfo=DI()->notorm->users_video->select("thumb")->where("id='{$commentinfo['videoid']}'")->fetchOne();

		$rs['likes']=$video['likes'];
		
		//获取评论点赞信息列表
		$fabulous=DI()->notorm->praise_messages->where("uid='{$uid}' and obj_id='{$commentid}' and type=0")->fetchOne();
		if(!$fabulous){
			DI()->notorm->praise_messages->insert(array("uid"=>$uid,"touid"=>$commentinfo['uid'],"obj_id"=>$commentid,"videoid"=>$commentinfo['videoid'],"addtime"=>time(),"type"=>0,"video_thumb"=>$videoinfo['thumb']));
		}else{
			DI()->notorm->praise_messages->where("uid='{$uid}' and type=0 and obj_id='{$commentid}'")->update(array("addtime"=>time()));
		}

		return $rs; 		
	}
	
	/* 热门视频 */
	public function getVideoList($uid,$p){


		$nums=20;
		$start=($p-1)*$nums;

		$videoids_s='';
		$where="isdel=0 and status=1 and is_ad=0";  //上架且审核通过
		
		$video=DI()->notorm->users_video
				->select("*")
				->where($where)
				->order("RAND()")
				->limit($start,$nums)
				->fetchAll();

		foreach($video as $k=>$v){
			
			$userinfo=getUserInfo($v['uid']);
			if(!$userinfo){
				$userinfo['user_nicename']=T("已删除");
			}

			$video[$k]['userinfo']=$userinfo;
			$video[$k]['datetime']=datetime($v['addtime']);	
			$video[$k]['comments']=NumberFormat($v['comments']);	
			$video[$k]['likes']=NumberFormat($v['likes']);	
			$video[$k]['steps']=NumberFormat($v['steps']);	
			if($uid>0){
				$video[$k]['islike']=(string)ifLike($uid,$v['id']);	
				$video[$k]['isstep']=(string)ifStep($uid,$v['id']);	
				$video[$k]['isattent']=(string)isAttention($uid,$v['uid']);	
			}else{
				$video[$k]['islike']=0;	
				$video[$k]['isstep']=0;	
				$video[$k]['isattent']=0;	
			}

			$video[$k]['musicinfo']=getMusicInfo($video[$k]['userinfo']['user_nicename'],$v['music_id']);	
			$video[$k]['thumb']=get_upload_path($v['thumb']);
			$video[$k]['thumb_s']=get_upload_path($v['thumb_s']);
			$video[$k]['href']=get_upload_path($v['href']);

		}


		return $video;
	}


	/* 关注人视频 */
	public function getAttentionVideo($uid,$p){
		$nums=20;
		$start=($p-1)*$nums;
		
		$video=array();
		$attention=DI()->notorm->users_attention
				->select("touid")
				->where("uid='{$uid}'")
				->fetchAll();
		
		if($attention){
			
			$uids=array_column($attention,'touid');
			$touids=implode(",",$uids);
			
			$videoids_s=getVideoBlack($uid);
			$where="uid in ({$touids}) and id not in ({$videoids_s})  and isdel=0 and status=1";
			
			$video=DI()->notorm->users_video
					->select("*")
					->where($where)
					->order("addtime desc")
					->limit($start,$nums)
					->fetchAll();


			if(!$video){
				return 0;
			}
			
			foreach($video as $k=>$v){
				$video[$k]['userinfo']=getUserInfo($v['uid']);
				$video[$k]['datetime']=datetime($v['addtime']);	
				$video[$k]['comments']=NumberFormat($v['comments']);	
				$video[$k]['likes']=NumberFormat($v['likes']);	
				$video[$k]['steps']=NumberFormat($v['steps']);	
				$video[$k]['islike']=(string)ifLike($uid,$v['id']);	
				$video[$k]['isstep']=(string)ifStep($uid,$v['id']);	
				$video[$k]['isdialect']='0';
				$video[$k]['musicinfo']=getMusicInfo($video[$k]['userinfo']['user_nicename'],$v['music_id']);
				$video[$k]['thumb']=get_upload_path($v['thumb']);	
				$video[$k]['thumb_s']=get_upload_path($v['thumb_s']);	
				$video[$k]['href']=get_upload_path($v['href']);		
			}
			$arr1 = array_map(create_function('$n', 'return $n["addtime"];'), $video);
			
			array_multisort($arr1,SORT_DESC,$video);//多维数组的排序					
			
		}
		

		return $video;		
	} 			
	
	/* 视频详情 */
	public function getVideo($uid,$videoid){
		$video=DI()->notorm->users_video
					->select("*")
					->where("id = {$videoid}")
					->fetchOne();
		if(!$video){
			return 1000;
		}
		
		$video['userinfo']=getUserInfo($video['uid']);	
		$video['isattent']=(string)isAttention($uid,$video['uid']);	
		$video['datetime']=datetime($video['addtime']);	
		$video['comments']=NumberFormat($video['comments']);	
		$video['likes']=NumberFormat($video['likes']);	
		$video['steps']=NumberFormat($video['steps']);	
		$video['shares']=NumberFormat($video['shares']);	
		$video['islike']=(string)ifLike($uid,$videoid);			
		$video['isstep']=(string)ifStep($uid,$videoid);

		$video['musicinfo']=getMusicInfo($video['userinfo']['user_nicename'],$v['music_id']);

		$video['thumb']=get_upload_path($video['thumb']);	
		$video['thumb_s']=get_upload_path($video['thumb_s']);	
		$video['href']=get_upload_path($video['href']);
		
		
		return 	$video;
	}
	
	/* 评论列表 */
	public function getComments($uid,$videoid,$p){
		$nums=20;
		$start=($p-1)*$nums;
		$comments=DI()->notorm->users_video_comments
					->select("*")
					->where("videoid='{$videoid}' and parentid='0'")
					->order("addtime desc")
					->limit($start,$nums)
					->fetchAll();
		foreach($comments as $k=>$v){
			$comments[$k]['userinfo']=getUserInfo($v['uid']);				
			$comments[$k]['datetime']=datetime($v['addtime']);	
			$comments[$k]['likes']=NumberFormat($v['likes']);	
			if($uid){
				$comments[$k]['islike']=(string)$this->ifCommentLike($uid,$v['id']);	
			}else{
				$comments[$k]['islike']='0';	
			}
			
			if($v['touid']>0){
				$touserinfo=getUserInfo($v['touid']);
			}
			if(!$touserinfo){
				$touserinfo=(object)array();
				$comments[$k]['touid']='0';
			}
			$comments[$k]['touserinfo']=$touserinfo;

			$count=DI()->notorm->users_video_comments
					->where("commentid='{$v['id']}'")
					->count();
			$comments[$k]['replys']=$count;
            
            /* 回复 */
            $reply=DI()->notorm->users_video_comments
					->select("*")
					->where("commentid='{$v['id']}'")
					->order("addtime desc")
					->limit(0,1)
					->fetchAll();
            foreach($reply as $k1=>$v1){
                
                $v1['userinfo']=getUserInfo($v1['uid']);				
                $v1['datetime']=datetime($v1['addtime']);	
                $v1['likes']=NumberFormat($v1['likes']);	
                $v1['islike']=(string)$this->ifCommentLike($uid,$v1['id']);
                if($v1['touid']>0){
                    $touserinfo=getUserInfo($v1['touid']);
                }
                if(!$touserinfo){
                    $touserinfo=(object)array();
                    $v1['touid']='0';
                }
                
                if($v1['parentid']>0 && $v1['parentid']!=$v['id']){
                    $tocommentinfo=DI()->notorm->users_video_comments
                        ->select("content,at_info")
                        ->where("id='{$v1['parentid']}'")
                        ->fetchOne();
                }else{
                    $tocommentinfo=(object)array();
                    $touserinfo=(object)array();
                    $v1['touid']='0';
                }
                $v1['touserinfo']=$touserinfo;
                $v1['tocommentinfo']=$tocommentinfo;


                $reply[$k1]=$v1;
            }
            
            $comments[$k]['replylist']=$reply;
		}
		
		$commentnum=DI()->notorm->users_video_comments
					->where("videoid='{$videoid}'")
					->count();
		
		$rs=array(
			"comments"=>$commentnum,
			"commentlist"=>$comments,
		);
		
		return $rs;
	}

	/* 回复列表 */
	public function getReplys($uid,$commentid,$p){
		$nums=20;
		$start=($p-1)*$nums;
		$comments=DI()->notorm->users_video_comments
					->select("*")
					->where("commentid='{$commentid}'")
					->order("addtime desc")
					->limit($start,$nums)
					->fetchAll();


		foreach($comments as $k=>$v){
			$comments[$k]['userinfo']=getUserInfo($v['uid']);				
			$comments[$k]['datetime']=datetime($v['addtime']);	
			$comments[$k]['likes']=NumberFormat($v['likes']);	
			$comments[$k]['islike']=(string)$this->ifCommentLike($uid,$v['id']);
			if($v['touid']>0){
				$touserinfo=getUserInfo($v['touid']);
			}
			if(!$touserinfo){
				$touserinfo=(object)array();
				$comments[$k]['touid']='0';
			}
			


			if($v['parentid']>0 && $v['parentid']!=$commentid){
				$tocommentinfo=DI()->notorm->users_video_comments
					->select("content,at_info")
					->where("id='{$v['parentid']}'")
					->fetchOne();
			}else{

				$tocommentinfo=(object)array();
				$touserinfo=(object)array();
				$comments[$k]['touid']='0';

			}
			$comments[$k]['touserinfo']=$touserinfo;
			$comments[$k]['tocommentinfo']=$tocommentinfo;
		}
		
		return $comments;
	}
	
	
	
	/* 评论/回复 是否点赞 */
	public function ifCommentLike($uid,$commentid){
		$like=DI()->notorm->users_video_comments_like
				->select("id")
				->where("uid='{$uid}' and commentid='{$commentid}'")
				->fetchOne();
		if($like){
			return 1;
		}else{
			return 0;
		}	
	}
	
	/* 我的视频 */
	public function getMyVideo($uid,$p){
		$nums=20;
		$start=($p-1)*$nums;
		
		$video=DI()->notorm->users_video
				->select("*")
				->where('uid=?  and isdel=0',$uid)
				->order("addtime desc")
				->limit($start,$nums)
				->fetchAll();
		
		foreach($video as $k=>$v){
			$video[$k]['userinfo']=getUserInfo($v['uid']);
			$video[$k]['datetime']=datetime($v['addtime']);
			$video[$k]['comments']=NumberFormat($v['comments']);	
			$video[$k]['likes']=NumberFormat($v['likes']);	
			$video[$k]['steps']=NumberFormat($v['steps']);
			$video[$k]['islike']='0';	
			$video[$k]['isattent']='0';	
			$video[$k]['isdialect']='0';
			$video[$k]['musicinfo']=getMusicInfo($video[$k]['userinfo']['user_nicename'],$v['music_id']);

			$video[$k]['thumb']=get_upload_path($v['thumb']);	
			$video[$k]['thumb_s']=get_upload_path($v['thumb_s']);	
			$video[$k]['href']=get_upload_path($v['href']);	
			
		}		
		
		
		$arr1 = array_map(create_function('$n', 'return $n["addtime"];'), $video);
		
		array_multisort($arr1,SORT_DESC,$video);//多维数组的排序
				
		return $video;
	} 	
	/* 删除视频 */
	public function del($uid,$videoid){
		
		$result=DI()->notorm->users_video
					->select("*")
					->where("id='{$videoid}' and uid='{$uid}'")
					->fetchOne();	
		if($result){
			// 删除 评论记录
			 /*DI()->notorm->users_video_comments
						->where("videoid='{$videoid}'")
						->delete(); 
			//删除视频评论喜欢
			DI()->notorm->users_video_comments_like
						->where("videoid='{$videoid}'")
						->delete(); 
			
			// 删除  点赞
			 DI()->notorm->users_video_like
						->where("videoid='{$videoid}'")
						->delete(); 
			//删除视频举报
			DI()->notorm->users_video_report
						->where("videoid='{$videoid}'")
						->delete(); 
			// 删除视频 
			 DI()->notorm->users_video
						->where("id='{$videoid}'")
						->delete();	*/ 

			//将喜欢的视频列表状态修改
			DI()->notorm->users_video_like
				->where("videoid='{$videoid}'")
				->update(array("status"=>0));	

			DI()->notorm->users_video
				->where("id='{$videoid}'")
				->update( array( 'isdel'=>1 ) );
				
				//将点赞信息列表里的状态修改
			DI()->notorm->praise_messages
				->where("videoid='{$videoid}'")
				->update(array("status"=>0));

			//将视频评论@信息列表的状态更改
			DI()->notorm->users_video_comments_at_messages
				->where("videoid='{$videoid}'")
				->update(array("status"=>0));

			//将评论信息列表的状态更改	

			DI()->notorm->users_video_comments_messages
				->where("videoid='{$videoid}'")
				->update(array("status"=>0));
		}				
		return 0;
	}	

	/* 个人主页视频 */
	public function getHomeVideo($uid,$touid,$p){
		$nums=20;
		$start=($p-1)*$nums;
		
		
		if($uid==$touid){  //自己的视频（需要返回视频的状态前台显示）
			$where=" uid={$uid} and isdel='0' and status=1  and is_ad=0";
		}else{  //访问其他人的主页视频
            $videoids_s=getVideoBlack($uid);
			$where="id not in ({$videoids_s}) and uid={$touid} and isdel='0' and status=1  and is_ad=0";
		}
		$video=DI()->notorm->users_video
				->select("*")
				->where($where)
				->order("addtime desc")
				->limit($start,$nums)
				->fetchAll();
				
		

		foreach($video as $k=>$v){
			$video[$k]['userinfo']=getUserInfo($v['uid']);
			$video[$k]['datetime']=datetime($v['addtime']);	
			$video[$k]['comments']=NumberFormat($v['comments']);	
			$video[$k]['likes']=NumberFormat($v['likes']);	
			$video[$k]['steps']=NumberFormat($v['steps']);	
			$video[$k]['islike']=(string)ifLike($uid,$v['id']);	
			$video[$k]['isstep']=(string)ifStep($uid,$v['id']);	
			$video[$k]['isattent']=(string)isAttention($uid,$v['uid']);	
			$video[$k]['isdialect']='0';
			$video[$k]['musicinfo']=getMusicInfo($video[$k]['userinfo']['user_nicename'],$v['music_id']);

			$video[$k]['thumb']=get_upload_path($v['thumb']);	
			$video[$k]['thumb_s']=get_upload_path($v['thumb_s']);	
			$video[$k]['href']=get_upload_path($v['href']);
		}		

		
		//$arr1 = array_map(create_function('$n', 'return $n["addtime"];'), $video);
		
		//array_multisort($arr1,SORT_DESC,$video);//多维数组的排序
		return $video;
		
	}
	/* 举报 */
	public function report($data) {
		
		$video=DI()->notorm->users_video
					->select("uid")
					->where("id='{$data['videoid']}'")
					->fetchOne();
		if(!$video){
			return 1000;
		}
		
		$data['touid']=$video['uid'];
					
		$result= DI()->notorm->users_video_report->insert($data);
		return 0;
	}	
	



	public function getRecommendVideos($uid,$p,$isstart){
		$pnums=20;
		$start=($p-1)*$pnums;


		
		$configPri=getConfigPri();
		$video_showtype=$configPri['video_showtype'];


		


		if($video_showtype==0){ //随机

			if($p==1){
				DI()->redis -> delete('readvideo_'.$uid);
			}

			//去除看过的视频
			$where=array();
			$readLists=DI()->redis -> Get('readvideo_'.$uid);
			if($readLists){
				$where=json_decode($readLists,true);
			}

			$info=DI()->notorm->users_video
			->where("isdel=0 and status=1 and is_ad=0")
			->where('not id',$where)
			->order("rand()")
			->limit($pnums)
			
			->fetchAll();
			$where1=array();
			foreach ($info as $k => $v) {
				if(!in_array($v['id'],$where)){
					$where1[]=$v['id'];
				}
			}

			//将两数组合并
			$where2=array_merge($where,$where1);

			DI()->redis -> set('readvideo_'.$uid,json_encode($where2));



		}else{

			//获取私密配置里的评论权重和点赞权重
			$comment_weight=$configPri['comment_weight'];
			$like_weight=$configPri['like_weight'];
			$share_weight=$configPri['share_weight'];

			$prefix= DI()->config->get('dbs.tables.__default__.prefix');

			//热度值 = 点赞数*点赞权重+评论数*评论权重+分享数*分享权重
			//转化率 = 完整观看次数/总观看次数
			//排序规则：（曝光值+热度值）*转化率
			//曝光值从视频发布开始，每小时递减1，直到0为止


			/*废弃$info=DI()->notorm->users_video->queryAll("select *,format(watch_ok/views,2) as aaa, (ceil(comments *".$comment_weight." + likes *".$like_weight." + shares *".$share_weight.") + show_val)*format(watch_ok/views,2) as recomend from ".$prefix."users_video where isdel=0 and status=1  order by recomend desc,addtime desc limit ".$start.",".$pnums);*/

			$info=DI()->notorm->users_video
            ->select("*,(ceil(comments * ".$comment_weight." + likes * ".$like_weight." + shares * ".$share_weight.") + show_val)* if(format(watch_ok/views,2) >1,'1',format(watch_ok/views,2)) as recomend")
            ->where("isdel=0 and status=1 and is_ad=0")
            // ->where('not id',$where)
            ->order("recomend desc,addtime desc")
            ->limit($start,$pnums)
            ->fetchAll();
		}


		if(!$info){
			return 1001;
		}
		
		$videoCount=count($info);

		$now=time();

		//获取私密配置里的视频间隔数
		$configPri=getConfigPri();
		//广告视频开关
		$ad_video_switch=$configPri['ad_video_switch'];

		if($ad_video_switch){ //广告开关打开

			$video_ad_num=$configPri['video_ad_num']; //广告视频间隔数
			$ad_pnums=(int)($pnums/$video_ad_num);
			$start1=($p-1)*$ad_pnums;

			$Adwhere=array();

			/*if($uid>0){ //广告随机显示时使用

				if($isstart==1){ //将用户观看广告缓存删除
					DI()->redis -> delete('readad_'.$uid);
				}

				//获取缓存的广告id组
				$adHasLists=DI()->redis -> Get('readad_'.$uid);
				if($adHasLists){
					$Adwhere=json_decode($adHasLists,true);
				}
			}*/



			$orderStr="orderno desc,addtime desc";

			/*if($uid>0){

				//获取广告位
				$adLists=DI()->notorm->users_video->where("isdel=0 and status=1 and is_ad=1 and (ad_endtime=0 or (ad_endtime>0 and ad_endtime>{$now}))")->where("not id",$Adwhere)->limit(0,$ad_pnums)->order($orderStr)->fetchAll();
			}else{*/
				$adLists=DI()->notorm->users_video->where("isdel=0 and status=1 and is_ad=1 and (ad_endtime=0 or (ad_endtime>0 and ad_endtime>{$now}))")->limit($start1,$ad_pnums)->order($orderStr)->fetchAll();
			//}




			if($adLists){
				foreach ($adLists as $k => $v) {
					if($v){

						$videoNum=($k+1)*$video_ad_num;

						if($videoCount>=$videoNum){
							
							array_splice($info, ($k+1)*$video_ad_num+$k, 0, array($v)); //向推荐视频列表中插入广告位视频

							//广告随机显示时使用
							/*if($uid>0){
								if($videoCount/$video_ad_num>=($k+1)){
									//用户看过的广告存入redis中
									$adHasLists=DI()->redis -> Get('readad_'.$uid);
									$readadArr=array();
									if($adHasLists){
										$readadArr=json_decode($adHasLists,true);
										if(!in_array($v['id'],$readadArr)){
											$readadArr[]=$v['id'];
										}
									}else{
										$readadArr[]=$v['id'];
									}

									DI()->redis -> Set('readad_'.$uid,json_encode($readadArr));
								}
							}*/
							
							
						}
						
					}
				}

			}
		}
		
		
		foreach ($info as $k => $v) {
			$info[$k]['userinfo']=getUserInfo($v['uid']);
			$info[$k]['datetime']=datetime($v['addtime']);
			$info[$k]['thumb']=get_upload_path($v['thumb']);
			$info[$k]['thumb_s']=get_upload_path($v['thumb_s']);
			$info[$k]['addtime']=date("Y-m-d H:i:s",$v['addtime']);
			if($uid<0){
				$info[$k]['islike']='0';
				$info[$k]['isattent']='0';

			}else{
				$info[$k]['islike']=(string)ifLike($uid,$v['id']);
				$info[$k]['isattent']=(string)isAttention($uid,$v['uid']);
			}


			$info[$k]['musicinfo']=getMusicInfo($info[$k]['userinfo']['user_nicename'],$v['music_id']);
			$info[$k]['href']=get_upload_path($v['href']);
			if($v['ad_url']){
				$info[$k]['ad_url']=get_upload_path($v['ad_url']);
			}
			

			$info[$k]['isstep']='0'; //以下字段基本无用
			unset($info[$k]['status']);
			unset($info[$k]['ad_endtime']);
			unset($info[$k]['orderno']);
		}


		return $info;
	}

	/*获取附近的视频*/
	public function getNearby($uid,$lng,$lat,$p){
		$pnum=20;
		$start=($p-1)*$pnum;

		$prefix= DI()->config->get('dbs.tables.__default__.prefix');

		$info=DI()->notorm->users_video->queryAll("select *, round(6378.138 * 2 * ASIN(SQRT(POW(SIN(( ".$lat." * PI() / 180 - lat * PI() / 180) / 2),2) + COS(".$lat." * PI() / 180) * COS(lat * PI() / 180) * POW(SIN((".$lng." * PI() / 180 - lng * PI() / 180) / 2),2))) * 1000) AS distance FROM ".$prefix."users_video  where uid !=".$uid." and isdel=0 and status=1  and is_ad=0 order by distance asc,addtime desc limit ".$start.",".$pnum);

		if(!$info){
			return 1001;
		}


		foreach ($info as $k => $v) {
			$info[$k]['userinfo']=getUserInfo($v['uid']);
			$info[$k]['datetime']=datetime($v['addtime']);
			$info[$k]['thumb']=get_upload_path($v['thumb']);
			$info[$k]['thumb_s']=get_upload_path($v['thumb_s']);
			$info[$k]['addtime']=date("Y-m-d H:i:s",$v['addtime']);
			if($uid<0){
				$info[$k]['islike']='0';
				$info[$k]['isattent']='0';

			}else{
				$info[$k]['islike']=(string)ifLike($uid,$v['id']);
				$info[$k]['isattent']=(string)isAttention($uid,$v['uid']);
			}


			$info[$k]['musicinfo']=getMusicInfo($info[$k]['userinfo']['user_nicename'],$v['music_id']);

			$info[$k]['href']=get_upload_path($v['href']);

			$info[$k]['distance']=distanceFormat($v['distance']);

			$info[$k]['isstep']='0'; //以下字段基本无用
			unset($info[$k]['status']);
			
		}
		
		return $info;
	}

	/* 举报分类列表 */
	public function getReportContentlist() {
		
		$reportlist=DI()->notorm->users_video_report_classify
					->select("*")
					->order("orderno asc")
					->fetchAll();
		if(!$reportlist){
			return 1001;
		}
		foreach($reportlist as $k=>$v){
			if(DI()->language=='ko'){
				$reportlist[$k]['name']=$v['name_kr'];
			}
		}
		
		return $reportlist;
		
	}

	/*更新视频看完次数*/
	public function setConversion($videoid){


		//更新视频看完次数
		$res=DI()->notorm->users_video
				->where("id = '{$videoid}' and isdel=0 and status=1")
				->update( array('watch_ok' => new NotORM_Literal("watch_ok + 1") ) );

		return 1;
	}	
	
	
	
	/*浏览视频 计时*/
	public function lookVideo($uid,$status){
		//开始计时 存入redis
        if($status==2){
			//私密设置
			$configpri=getConfigPri();
			//当天零点的时间戳
			$today = strtotime(date("Y-m-d"),time());
		
			$mining_browsevideo=$configpri['mining_browsevideo'];
			$starttime=DI()->redis -> Get('lookvideo_'.$uid);
			
			if($starttime){
				$endtime=time(); //结束时间
				$looktime=($endtime-$starttime)/60;  //观看时长
				$fenzhong=floor($looktime);
				
				$browsevideo = explode("/", $mining_browsevideo);
				
				if($browsevideo[1]<=$fenzhong){
					//用户当天总权重值
					$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$uid,$today)->update(array( "weight"=>new NotORM_Literal("weight + {$browsevideo[0]}")));
					if(!$userweight){
						$userweight=DI()->notorm->users_total_weight->insert(array('uid'=>$uid,'weight'=>$browsevideo[0],'addtime'=>$today));
					}
					
					//挖矿-浏览视频添加 (用户详情)
					
					//用户当天权重值详情
					$weight_info=DI()->notorm->users_weight_info->where('uid=? and addtime=?',$uid,$today)->update(array( "browse_video"=>new NotORM_Literal("browse_video + {$browsevideo[0]}")));
					
					if(!$weight_info){
						//挖矿-浏览视频
						if(!$userweight['id']){
							$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$uid,$today)->fetchOne;
						}
						
						DI()->notorm->users_weight_info->insert(array('uid'=>$uid,'weight_id'=>$userweight['id'],'browse_video'=>$browsevideo[0],'addtime'=>$today));
					}
				}
				DI()->redis -> delete('lookvideo_'.$uid);
			}
			
		}else{
			DI()->redis -> Set('lookvideo_'.$uid,time()); //开始观看时间
		}

        return 0;
    }
	
	
	
	
	
}
