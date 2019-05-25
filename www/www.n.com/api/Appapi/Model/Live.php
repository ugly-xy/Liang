<?php

class Model_Live extends PhalApi_Model_NotORM {
	/* 创建房间 */
	public function createRoom($uid,$data) {
		$isexist=DI()->notorm->users_live
					->select("uid")
					->where('uid=?',$uid)
					->fetchOne();
		if($isexist){
			/* 更新 */
			$rs=DI()->notorm->users_live->where('uid = ?', $uid)->update($data);
		}else{
			/* 加入 */
			$rs=DI()->notorm->users_live->insert($data);
		}
		if(!$rs){
			return $rs;
		}
		return 1;
	}
	
	/* 主播粉丝 */
    public function getFansIds($touid) {
        
        $list=array();
		$fansids=DI()->notorm->users_attention
					->select("uid")
					->where('touid=?',$touid)
					->fetchAll();
                    
        if($fansids){
            $uids=array_column($fansids,'uid');
            
            $pushids=DI()->notorm->users_pushid
					->select("pushid")
					->where('uid',$uids)
					->fetchAll();
            $list=array_column($pushids,'pushid');
            $list=array_filter($list);
        }
        return $list;
    }	
	
	/* 修改直播状态 */
	public function changeLive($uid,$stream,$status){

		if($status==1){
            $info=DI()->notorm->users_live
                    ->select("*")
					->where('uid=? and stream=?',$uid,$stream)
                    ->fetchOne();
            if($info){
                DI()->notorm->users_live
					->where('uid=? and stream=?',$uid,$stream)
					->update(array("islive"=>1));
            }
			return $info;
		}else{
			$this->stopRoom($uid,$stream);
			return 1;
		}
	}
	
	/* 修改直播状态 */
	public function changeLiveType($uid,$stream,$data){
		return DI()->notorm->users_live
				->where('uid=? and stream=?',$uid,$stream)
				->update( $data );
	}
	
	/* 关播 */
	public function stopRoom($uid,$stream) {

		$info=DI()->notorm->users_live
				->select("uid,showid,starttime,title,province,city,stream,lng,lat,type,type_val,liveclassid")
				->where('uid=? and stream=? and islive="1"',$uid,$stream)
				->fetchOne();
		if($info){
			DI()->notorm->users_live
				->where('uid=?',$uid)
				->delete();
			$nowtime=time();
			$info['endtime']=$nowtime;
			$info['time']=date("Y-m-d",$info['showid']);
			$votes=DI()->notorm->users_coinrecord
				->where("action!='sendred' and touid=? and showid=?",$uid,$info['showid'])
				->sum('totalcoin');
			$info['votes']=0;
			if($votes){
				$info['votes']=$votes;
			}
			$nums=DI()->redis->zSize('user_'.$stream);			
			DI()->redis->hDel("livelist",$uid);
			DI()->redis->delete($uid.'_zombie');
			DI()->redis->delete($uid.'_zombie_uid');
			DI()->redis->delete('attention_'.$uid);
			DI()->redis->delete('user_'.$stream);
			$info['nums']=$nums;			
			$result=DI()->notorm->users_liverecord->insert($info);	
            
            /* 游戏处理 */
			$game=DI()->notorm->game
				->select("*")
				->where('stream=? and liveuid=? and state=?',$stream,$uid,"0")
				->fetchOne();
			$total=array();
			if($game)
			{
				$total=DI()->notorm->users_gamerecord
					->select("uid,sum(coin_1 + coin_2 + coin_3 + coin_4 + coin_5 + coin_6) as total")
					->where('gameid=?',$game['id'])
					->group('uid')
					->fetchAll();
				foreach($total as $k=>$v){
					DI()->notorm->users
						->where('id = ?', $v['uid'])
						->update(array('coin' => new NotORM_Literal("coin + {$v['total']}")));
					delcache('userinfo_'.$v['uid']);
					
					$insert=array("type"=>'income',"action"=>'game_return',"uid"=>$v['uid'],"touid"=>$v['uid'],"giftid"=>$game['id'],"giftcount"=>1,"totalcoin"=>$v['total'],"showid"=>0,"addtime"=>$nowtime );
					DI()->notorm->users_coinrecord->insert($insert);
				}

				DI()->notorm->game
					->where('id = ?', $game['id'])
					->update(array('state' =>'3','endtime' => time() ) );
				$brandToken=$stream."_".$game["action"]."_".$game['starttime']."_Game";
				DI()->redis->delete($brandToken);
			}

		}
		return 1;
	}
	/* 关播信息 */
	public function stopInfo($stream){
		
		$rs=array(
			'nums'=>0,
			'length'=>0,
			'votes'=>0,
		);
		
		$stream2=explode('_',$stream);
		$liveuid=$stream2[0];
		$starttime=$stream2[1];
		$liveinfo=DI()->notorm->users_liverecord
					->select("starttime,endtime,nums,votes")
					->where('uid=? and starttime=?',$liveuid,$starttime)
					->fetchOne();
		if($liveinfo){
            $cha=$liveinfo['endtime'] - $liveinfo['starttime'];
			$rs['length']=getSeconds($cha,1);
			$rs['nums']=$liveinfo['nums'];
		}
		if($liveinfo['votes']){
			$rs['votes']=round($liveinfo['votes']);
		}
		return $rs;
	}
	
	/* 直播状态 */
	public function checkLive($uid,$liveuid,$stream){
		$getConfig=getConfigPub();	
		$islive=DI()->notorm->users_live
					->select("islive,type,type_val,starttime")
					->where('uid=? and stream=?',$liveuid,$stream)
					->fetchOne();
					
		if(!$islive || $islive['islive']==0){
			return 1005;
		}
		$rs['type']=$islive['type'];
		$rs['type_val']='0';
		$rs['type_msg']='';
		
			$userinfo=DI()->notorm->users
					->select("issuper")
					->where('id=?',$uid)
					->fetchOne();
			if($userinfo && $userinfo['issuper']==1){
                
                if($islive['type']==6){
                    
                    return 1007;
                }
				$rs['type']='0';
				$rs['type_val']='0';
				$rs['type_msg']='';
				
				return $rs;
			}
		
		if($islive['type']==1){
			$rs['type_msg']=md5($islive['type_val']);
		}else if($islive['type']==2){
			$rs['type_msg']=T('本房间为收费房间，需支付').$islive['type_val'].$getConfig['name_coin'];
			$rs['type_val']=$islive['type_val'];
			$isexist=DI()->notorm->users_coinrecord
						->select('id')
						->where('uid=? and touid=? and showid=? and action="roomcharge" and type="expend"',$uid,$liveuid,$islive['starttime'])
						->fetchOne();
			if($isexist){
				$rs['type']='0';
				$rs['type_val']='0';
				$rs['type_msg']='';
			}
		}else if($islive['type']==3){
			$rs['type_val']=$islive['type_val'];
			$rs['type_msg']="이 방송은 유료타임 방송 입니다 \n 1분마다 ".$islive['type_val'].$getConfig['name_coin']."지불";
		}
		
		return $rs;
		
	}
	
	/* 用户余额 */
	public function getUserCoin($uid){
		$userinfo=DI()->notorm->users
					->select("coin")
					->where('id=?',$uid)
					->fetchOne();
		return $userinfo;
	}
	
	/* 房间扣费 */
	public function roomCharge($uid,$token,$liveuid,$stream){
		$islive=DI()->notorm->users_live
					->select("islive,type,type_val,starttime")
					->where('uid=? and stream=?',$liveuid,$stream)
					->fetchOne();
		if(!$islive || $islive['islive']==0){
			return 1005;
		}
		
		if($islive['type']==0 || $islive['type']==1 ){
			return 1006;
		}
		
		$userinfo=DI()->notorm->users
					->select("token,expiretime,coin")
					->where('id=?',$uid)
					->fetchOne();
		if($userinfo['token']!=$token || $userinfo['expiretime']<time()){
			return 700;				
		}
        
        
		
		$total=$islive['type_val'];
		if($total<=0){
			return 1007;
		}
        
        /* 更新用户余额 消费 */
		$ifok=DI()->notorm->users
				->where('id = ? and coin >= ?', $uid,$total)
				->update(array('coin' => new NotORM_Literal("coin - {$total}"),'consumption' => new NotORM_Literal("consumption + {$total}")) );
        if(!$ifok){
            return 1008;
        }
		
		
		
		
		
		$action='roomcharge';
		if($islive['type']==3){
			$action='timecharge';
		}
		
		$giftid=0;
		$giftcount=0;
		$showid=$islive['starttime'];
		$addtime=time();
		
                
        /* 分销 */	
		// setAgentProfit($uid,$total);
		/* 分销 */	

		/* 更新直播 映票 累计映票 */
		DI()->notorm->users
				->where('id = ?', $liveuid)
				->update( array('coin' => new NotORM_Literal("coin + {$total}"),'votestotal' => new NotORM_Literal("votestotal + {$total}") ));

		/* 更新直播 映票 累计映票 */
		DI()->notorm->users_coinrecord
				->insert(array("type"=>'expend',"action"=>$action,"uid"=>$uid,"touid"=>$liveuid,"giftid"=>$giftid,"giftcount"=>$giftcount,"totalcoin"=>$total,"showid"=>$showid,"addtime"=>$addtime ));	
		/* 更新用户等级 */
		
		$getConfigPri=getConfigPri();		
		
		$expend_room=floor($total*$getConfigPri['expend_room']); //用户房间扣费获得权重值(消费)
		
		$income_room=floor($total*$getConfigPri['income_room']); //主播 "房间扣费" 获得权重值(收益)
		
		DI()->notorm->users
				->where('id = ?', $uid)
				->update( array('grade' => new NotORM_Literal("grade + {$expend_room}")));
				
		DI()->notorm->users
				->where('id = ?', $liveuid)
				->update( array('grade' => new NotORM_Literal("grade + {$income_room}")));
				
		$userinfo2=DI()->notorm->users
					->select('coin')
					->where('id = ?', $uid)
					->fetchOne();	
		$rs['coin']=$userinfo2['coin'];
		return $rs;
		
	}
	
	/* 判断是否僵尸粉 */
	public function isZombie($uid) {
        $userinfo=DI()->notorm->users
					->select("iszombie")
					->where("id='{$uid}'")
					->fetchOne();
		
		return $userinfo['iszombie'];				
    }
	
	/* 僵尸粉 */
    public function getZombie($stream,$where) {
		$ids= DI()->notorm->users_zombie
            ->select('uid')
            ->where("uid not in ({$where})")
			->limit(0,10)
            ->fetchAll();	

		$info=array();

		if($ids){
            foreach($ids as $k=>$v){
                
                $userinfo=getUserInfo($v['uid'],1);
                if(!$userinfo){
                    DI()->notorm->users_zombie->where("uid={$v['uid']}")->delete();
                    continue;
                }
                
                $info[]=$userinfo;

                $score='0.'.($userinfo['level']+100).'1';
				DI()->redis -> zAdd('user_'.$stream,$score,$v['uid']);
            }	
		}
		return 	$info;		
    }
	
	/* 礼物列表 */
	public function getGiftList(){

		$rs=DI()->notorm->gift
			->select("id,type,mark,giftname,giftname_kr,needcoin,gifticon")
			->order("orderno asc,addtime desc")
			->fetchAll();
		foreach($rs as $k=>$v){
			$rs[$k]['gifticon']=get_upload_path($v['gifticon']);
			
			if(DI()->language=='ko'){
				$rs[$k]['giftname']=$v['giftname_kr'];
			}
			unset($v['giftname_kr']);
		}	

		return $rs;
	}
	
	/* 赠送礼物 */
	public function sendGift($uid,$liveuid,$stream,$giftid,$giftcount,$sendtype) {
		
		
		$getConfigPri=getConfigPri();	
        /* 礼物信息 */
		$giftinfo=DI()->notorm->gift
					->select("type,mark,giftname,gifticon,needcoin,giftname_kr,swf,swftime")
					->where('id=?',$giftid)
					->fetchOne();
		if(!$giftinfo){
			/* 礼物信息不存在 */
			return 1002;
		}
        
		$total= $giftinfo['needcoin']*$giftcount;
		 
		$addtime=time();
		$type='expend';
		
		
		/* 更新用户余额 消费 */
		$ifok =DI()->notorm->users
				->where('id = ? and coin >=?', $uid,$total)
				->update(array('coin' => new NotORM_Literal("coin - {$total}"),'consumption' => new NotORM_Literal("consumption + {$total}") ) );
        if(!$ifok){
            /* 余额不足 */
			return 1001;
        }
				
		/* 分销 */	
		// setAgentProfit($uid,$total);
		/* 分销 */		
        
        /*家族分成之后的金额*/
		$family_total=setFamilyDivide($liveuid,$total);
		
		/*平台礼物抽成*/

		
		$pumping=$total*($getConfigPri['gift_pumping']/100); 
		
		$pumping_total=round($pumping,4); //抽取的金额
		
		$anthor_total=$family_total-$pumping_total; //抽成后的钱
		
		$pumping_info=array(
			"uid"=>$uid,
			"liveuid"=>$liveuid,
			"giftid"=>$giftid,
			"giftcount"=>$giftcount,
			"totalcoin"=>$total,
			"pumping_total"=>$pumping_total,
			"gift_pumping"=>$anthor_total,
			"addtime"=>$addtime
		);
		
		DI()->notorm->gift_pumping->insert($pumping_info);
		DI()->notorm->config_private->where("1=1")->update( array('pooloffunds' => new NotORM_Literal("pooloffunds + {$pumping_total}")));
		
		/* 更新直播 魅力值 累计魅力值 */
		$istouid =DI()->notorm->users
					->where('id = ?', $liveuid)
					->update( array('coin' => new NotORM_Literal("coin + {$anthor_total}"),'votestotal' => new NotORM_Literal("votestotal + {$total}") ));
		if($sendtype==1){
			$action='giftvideo';
			$showid=$stream;
		}else{
			$action='sendgift';
			$stream2=explode('_',$stream);
			$showid=$stream2[1];
		}
		
	
		$insert=array("type"=>$type,"action"=>$action,"uid"=>$uid,"touid"=>$liveuid,"giftid"=>$giftid,"giftcount"=>$giftcount,"totalcoin"=>$total,"showid"=>$showid,"mark"=>$giftinfo['mark'],"addtime"=>$addtime );
		DI()->notorm->users_coinrecord->insert($insert);

		$userinfo2 =DI()->notorm->users
				->select('grade,coin')
				->where('id = ?', $uid)
				->fetchOne();
			 
		$level=getLevel($userinfo2['grade']);	
		
		
		/*更新经验值(权重)*/
			
		
		$expend_gift=floor($total*$getConfigPri['expend_gift']); //用户送礼物获得权重值(消费)
		
		$income_gift=floor($total*$getConfigPri['income_gift']); //主播收礼物获得权重值(收益)
		
		DI()->notorm->users
				->where('id = ?', $uid)
				->update( array('grade' => new NotORM_Literal("grade + {$expend_gift}")));
				
		DI()->notorm->users
				->where('id = ?', $liveuid)
				->update( array('grade' => new NotORM_Literal("grade + {$income_gift}")));
				
				

        /* 更新主播热门 */
        if($giftinfo['mark']==1){
            DI()->notorm->users_live
                ->where('uid = ?', $liveuid)
                ->update( array('hotvotes' => new NotORM_Literal("hotvotes + {$total}") ));
        }
        
        DI()->redis->zIncrBy('user_'.$stream,$total,$uid);
		
		/* 清除缓存 */
		delCache("userinfo_".$uid); 
		delCache("userinfo_".$liveuid); 
	
		$votestotal=$this->getVotes($liveuid);
		
		$gifttoken=md5(md5($action.$uid.$liveuid.$giftid.$giftcount.$total.$showid.$addtime.rand(100,999)));
        
        $swf=$giftinfo['swf'] ? get_upload_path($giftinfo['swf']):'';
		
		$result=array("uid"=>$uid,"giftid"=>$giftid,"type"=>$giftinfo['type'],"giftcount"=>$giftcount,"totalcoin"=>$total,"giftname"=>$giftinfo['giftname'],"giftname_kr"=>$giftinfo['giftname_kr'],"gifticon"=>get_upload_path($giftinfo['gifticon']),"swftime"=>$giftinfo['swftime'],"swf"=>$swf,"level"=>$level,"level_anchor"=>$level,"coin"=>$userinfo2['coin'],"votestotal"=>$votestotal,"gifttoken"=>$gifttoken);
		
		
		//私密设置
		$configpri=getConfigPri();
		$today = strtotime(date("Y-m-d"),time());
		$mining_sendgift=$configpri['mining_sendgift']; //挖矿-送礼物
		$mining_gift=$configpri['mining_gift']; //挖矿-收礼物
		
		/***..............加权重值******************/
		
		$sendgiftsss= explode("/", $mining_sendgift);
		
		if($sendgiftsss[1]<=$total){
		
			//用户当天总权重值
			$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$uid,$today)->update(array( "weight"=>new NotORM_Literal("weight + {$sendgiftsss[0]}")));
			if(!$userweight){
				$userweight=DI()->notorm->users_total_weight->insert(array('uid'=>$uid,'weight'=>$sendgiftsss[0],'addtime'=>$today));
			}
			
			//用户当天权重值详情
			$weight_info=DI()->notorm->users_weight_info->where('uid=? and addtime=?',$uid,$today)->update(array( "send_gifts"=>new NotORM_Literal("send_gifts + {$sendgiftsss[0]}")));
			if(!$weight_info){
				//挖矿-送礼物
				if(!$userweight['id']){
					$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$uid,$today)->fetchOne();
				}
				DI()->notorm->users_weight_info->insert(array('uid'=>$uid,'weight_id'=>$userweight['id'],'send_gifts'=>$sendgiftsss[0],'addtime'=>$today));
			}
		}
		
		$giftsss= explode("/", $mining_gift);
		if($giftsss[0]<=$total){
		
			//收礼物的用户
			$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$liveuid,$today)->update(array( "weight"=>new NotORM_Literal("weight + {$giftsss[1]}")));
			if(!$userweight){
				$userweight=DI()->notorm->users_total_weight->insert(array('uid'=>$liveuid,'weight'=>$giftsss[1],'addtime'=>$today));
			}
			//用户当天权重值详情
			$weight_info=DI()->notorm->users_weight_info->where('uid=? and addtime=?',$liveuid,$today)->update(array( "collect_gifts"=>new NotORM_Literal("collect_gifts + {$giftsss[1]}")));
			if(!$weight_info){
				//挖矿-收礼物
				if(!$userweight['id']){
					$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$liveuid,$today)->fetchOne();
				}
				DI()->notorm->users_weight_info->insert(array('uid'=>$liveuid,'weight_id'=>$userweight['id'],'collect_gifts'=>$giftsss[1],'addtime'=>$today));
			}
			/**************************************************/
		}		
		return $result;
	}
			
	
	/* 发送弹幕 */
	public function sendBarrage($uid,$liveuid,$stream,$giftid,$giftcount,$content) {

		$configpri=getConfigPri();
					 
		$giftinfo=array(
			"giftname"=>'弹幕',
			"gifticon"=>'',
			"needcoin"=>$configpri['barrage_fee'],
		);		
		
		$total= $giftinfo['needcoin']*$giftcount;
		 
		$addtime=time();
		$type='expend';
		$action='sendbarrage';

		/* 更新用户余额 消费 */
		$ifok =DI()->notorm->users
				->where('id = ? and coin >=?', $uid,$total)
				->update(array('coin' => new NotORM_Literal("coin - {$total}"),'consumption' => new NotORM_Literal("consumption + {$total}") ) );
        if(!$ifok){
            /* 余额不足 */
			return 1001;
        }
		/* 更新直播 魅力值 累计魅力值 */
		$istouid =DI()->notorm->users
				->where('id = ?', $liveuid)
				->update( array('coin' => new NotORM_Literal("coin + {$total}"),'votestotal' => new NotORM_Literal("votestotal + {$total}") ));
				
				
		/*更新经验值(权重)*/	
		
		$expend_barrage=floor($total*$configpri['expend_barrage']); //用户送礼物获得权重值(消费)
		
		$income_barrage=floor($total*$configpri['income_barrage']); //主播收礼物获得权重值(收益)
		
		DI()->notorm->users
				->where('id = ?', $uid)
				->update( array('grade' => new NotORM_Literal("grade + {$expend_barrage}")));
				
		DI()->notorm->users
				->where('id = ?', $liveuid)
				->update( array('grade' => new NotORM_Literal("grade + {$income_barrage}")));
				
		$stream2=explode('_',$stream);
		$showid=$stream2[1];
        if(!$showid){
            $showid=0;
        }

		/* 写入记录 或更新 */
		$insert=array("type"=>$type,"action"=>$action,"uid"=>$uid,"touid"=>$liveuid,"giftid"=>$giftid,"giftcount"=>$giftcount,"totalcoin"=>$total,"showid"=>$showid,"addtime"=>$addtime );
		$isup=DI()->notorm->users_coinrecord->insert($insert);
					 
		$userinfo2 =DI()->notorm->users
				->select('grade,coin')
				->where('id = ?', $uid)
				->fetchOne();	
			 
		$level=getLevel($userinfo2['grade']);			
		
		/* 清除缓存 */
		delCache("userinfo_".$uid); 
		delCache("userinfo_".$liveuid); 
		
		$votestotal=$this->getVotes($liveuid);
		
		$barragetoken=md5(md5($action.$uid.$liveuid.$giftid.$giftcount.$total.$showid.$addtime.rand(100,999)));
		 
		$result=array("uid"=>$uid,"content"=>$content,"giftid"=>$giftid,"giftcount"=>$giftcount,"totalcoin"=>$total,"giftname"=>$giftinfo['giftname'],"gifticon"=>$giftinfo['gifticon'],"level"=>$level,"level_anchor"=>$level,"coin"=>$userinfo2['coin'],"votestotal"=>$votestotal,"barragetoken"=>$barragetoken);
		
		return $result;
	}			
	
	/* 设置/取消 管理员 */
	public function setAdmin($liveuid,$touid){
					
		$isexist=DI()->notorm->users_livemanager
					->select("*")
					->where('uid=? and  liveuid=?',$touid,$liveuid)
					->fetchOne();			
		if(!$isexist){
			$count =DI()->notorm->users_livemanager
						->where('liveuid=?',$liveuid)
						->count();	
			if($count>=5){
				return 1004;
			}		
			$rs=DI()->notorm->users_livemanager
					->insert(array("uid"=>$touid,"liveuid"=>$liveuid) );	
			if($rs!==false){
				return 1;
			}else{
				return 1003;
			}				
			
		}else{
			$rs=DI()->notorm->users_livemanager
				->where('uid=? and  liveuid=?',$touid,$liveuid)
				->delete();		
			if($rs!==false){
				return 0;
			}else{
				return 1003;
			}						
		}
	}
	
	/* 管理员列表 */
	public function getAdminList($liveuid){
		$rs=DI()->notorm->users_livemanager
						->select("uid")
						->where('liveuid=?',$liveuid)
						->fetchAll();	
		foreach($rs as $k=>$v){
			$rs[$k]=getUserInfo($v['uid']);
		}	

        $info['list']=$rs;
        $info['nums']=(string)count($rs);
        $info['total']='5';
		return $info;
	}
	
	/* 举报 */
	public function setReport($uid,$touid,$content){
		return  DI()->notorm->users_report
				->insert(array("uid"=>$uid,"touid"=>$touid,'content'=>$content,'addtime'=>time() ) );	
	}
	
	/* 主播总映票 */
	public function getVotes($liveuid){
		$userinfo=DI()->notorm->users
					->select("votestotal")
					->where('id=?',$liveuid)
					->fetchOne();	
		return $userinfo['votestotal'];					
	}
	
	/* 超管关闭直播间 */
	public function superStopRoom($uid,$token,$liveuid,$type){
		
		$userinfo=DI()->notorm->users
					->select("token,expiretime,issuper")
					->where('id=? ',$uid)
					->fetchOne();
		if($userinfo['token']!=$token || $userinfo['expiretime']<time()){
			return 700;				
		} 	
		
		if($userinfo['issuper']==0){
			return 1001;
		}
		
		if($type==1){
			/* 关闭并禁用 */
			DI()->notorm->users->where('id=? ',$liveuid)->update(array('user_status'=>0));
		}
		
	
		$info=DI()->notorm->users_live
				->select("uid,showid,starttime,title,province,city,stream,lng,lat,type,type_val")
				->where('uid=? and islive="1"',$liveuid)
				->fetchOne();
		if($info){
			$nowtime=time();
			$stream=$info['stream'];
			$info['endtime']=$nowtime;
			
			$nums=DI()->redis->zSize('user_'.$stream);
			DI()->redis->hDel("livelist",$liveuid);
			DI()->redis->delete($liveuid.'_zombie');
			DI()->redis->delete($liveuid.'_zombie_uid');
			DI()->redis->delete('attention_'.$liveuid);
			DI()->redis->delete('user_'.$stream);

			$info['nums']=$nums;			
			$result=DI()->notorm->users_liverecord->insert($info);	
		}
		DI()->notorm->users_live->where('uid=?',$liveuid)->delete();
		
		return 0;
		
	}
    
    /* 获取用户本场贡献 */
    public function getContribut($uid,$liveuid,$showid){
        $sum=DI()->notorm->users_coinrecord
				->where('action="sendgift" and uid=? and touid=? and showid=? ',$uid,$liveuid,$showid)
				->sum('totalcoin');
        if(!$sum){
            $sum=0;
        }
        
        return (string)$sum;
    }
	
	
	public function enterRoom($liveuid){
		$info=DI()->notorm->users_live
				->select("*")
				->where('uid=?',$liveuid)
				->fetchOne();
				
		return $info;
	}
}
