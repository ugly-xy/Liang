<?php

class Model_User extends PhalApi_Model_NotORM {
	/* 用户全部信息 */
	public function getBaseInfo($uid) {
		$info=DI()->notorm->users
				->select("id,user_nicename,avatar,avatar_thumb,sex,signature,coin,consumption,votestotal,province,city,birthday,area,age,mobile,token_address,grade,mine")
				->where('id=?  and user_type="2"',$uid)
				->fetchOne();
				
		if($info){
			if($info['birthday']==''){
				$info['age']=T("年龄未填写");
			}else{
				$birthday=explode('-',$info['birthday']);
				$info['age']= (date('Y',time())-$birthday[0]).T("岁");
			}

			if($info['city']==""){
				$info['city']=T("城市未填写");
				$info['hometown']="";
			}else{
				$info['city']=T($info['city']);
				$info['hometown']=$info['province'].$info['city'].$info['area'];
			}	
			
			
			$info['coin2']=NumberFormat3($info['coin']);
			$info['mine2']=NumberFormat4($info['mine']);
			$info['avatar']=get_upload_path($info['avatar']);
			$info['avatar_thumb']=get_upload_path($info['avatar_thumb']);						
			$info['level']=getLevel($info['grade']);
			$info['level_anchor']=getLevel($info['grade']);
			$info['lives']=getLives($uid);
			$info['follows']=getFollows($uid);
			$info['fans']=getFans($uid);
			$info['vip']=getUserVip($uid);
			$info['liang']=getUserLiang($uid);
			$info['praise']=getPraises($uid);
			$info['workVideos']=getWorks($uid);
			$info['likeVideos']=getLikes($uid);
			$info['signature']=T($info['signature']);
			$config=getConfigPri();
			$info['name_coin']=$config['name_coin'];
			$isreg=0;
			//判断有没有签到
			$today = strtotime(date("Y-m-d"),time());
			$signin=DI()->notorm->users_weight_info
				->select("signin")
				->where('uid=? and addtime=?',$uid,$today)
				->fetchOne();
			if($signin && $signin['signin']>0){
				$isreg=1;
			}	
			$info['isreg']=$isreg;
		}
					
		return $info;
	}
			
	/* 判断昵称是否重复 */
	public function checkName($uid,$name){
		$isexist=DI()->notorm->users
					->select('id')
					->where('id!=? and user_nicename=?',$uid,$name)
					->fetchOne();
		if($isexist){
			return 0;
		}else{
			return 1;
		}
	}
	
	/* 修改信息 */
	public function userUpdate($uid,$fields){
		/* 清除缓存 */
		delCache("userinfo_".$uid);
        
        unset($fields['score']);
        unset($fields['coin']);
        unset($fields['consumption']);
        unset($fields['votestotal']);
        
        if(!$fields){
            return false;
        }

		return DI()->notorm->users
					->where('id=?',$uid)
					->update($fields);
	}

	/* 修改密码 */
	public function updatePass($uid,$oldpass,$pass){
		$userinfo=DI()->notorm->users
					->select("user_pass")
					->where('id=?',$uid)
					->fetchOne();
		$oldpass=setPass($oldpass);							
		if($userinfo['user_pass']!=$oldpass){
			return 1003;
		}							
		$newpass=setPass($pass);
		return DI()->notorm->users
					->where('id=?',$uid)
					->update( array( "user_pass"=>$newpass ) );
	}
	
	/* 我的钻石 */
	public function getBalance($uid){
		return DI()->notorm->users
				->select("coin")
				->where('id=?',$uid)
				->fetchOne();
	}
	
	/* 充值规则 */
	public function getChargeRules(){

		$rules= DI()->notorm->charge_rules
				->select('id,coin,money,money_ios,product_id,give')
				->order('orderno asc')
				->fetchAll();

		return 	$rules;
	}
    
	/* 我的收益 */
	public function getProfit($uid){
		$info= DI()->notorm->users
				->select("coin,votestotal")
				->where('id=?',$uid)
				->fetchOne();

		$config=getConfigPri();
		
		//提现比例
		$cash_rate=$config['cash_rate'];
		//剩余票数
		$votes=$info['coin'];
        
		//总可提现数
		$total=(string)floor($votes/$cash_rate);

		$tips='温馨提示：每月'.$config['cash_start'].'-'.$config['cash_end'].'号可进行提现申请，收益将在'.($config['cash_end']+1).'-'.($config['cash_end']+5).'号统一发放，每月只可提现一次';
		$rs=array(
			"votes"=>$votes,
			"votestotal"=>$info['votestotal'],
			"total"=>$total,
			"cash_rate"=>$cash_rate,
			"tips"=>$tips,
		);
		return $rs;
	}	
	/* 提现  */
	public function setCash($data){
        
        $nowtime=time();
        
        $uid=$data['uid'];
        $accountid=$data['accountid'];
        $cashvote=$data['cashvote'];
        
        $config=getConfigPri();
        $cash_start=$config['cash_start'];
        $cash_end=$config['cash_end'];
        
        $day=(int)date("d",$nowtime);
        
        if($day < $cash_start || $day > $cash_end){
            return 1005;
        }
        
        //本月第一天
        $month=date('Y-m-d',strtotime(date("Ym",$nowtime).'01'));
        $month_start=strtotime(date("Ym",$nowtime).'01');

        //本月最后一天
        $month_end=strtotime("{$month} +1 month");      

        $isexist=DI()->notorm->users_cashrecord
				->select("id")
				->where('uid=? and addtime > ? and addtime < ?',$uid,$month_start,$month_end)
				->fetchOne();
        if($isexist){
            return 1006;
        }
        
		$isrz=DI()->notorm->users_auth
				->select("status")
				->where('uid=?',$uid)
				->fetchOne();
		if(!$isrz || $isrz['status']!=1){
			return 1003;
		}
        
        /* 钱包信息 */
		$accountinfo=DI()->notorm->users_cash_account
				->select("*")
				->where('id=?',$accountid)
				->fetchOne();
        if(!$accountinfo){
            return 1006;
        }
        

		//提现比例
		$cash_rate=$config['cash_rate'];
		/* 最低额度 */
		$cash_min=$config['cash_min'];
		
		//提现钱数
        $money=floor($cashvote/$cash_rate);
		
		if($money < $cash_min){
			return 1004;
		}
		
		$cashvotes=$money*$cash_rate;
        
        
        $ifok=DI()->notorm->users
            ->where('id = ? and coin>=?', $uid,$cashvotes)
            ->update(array('coin' => new NotORM_Literal("coin - {$cashvotes}")) );
        if(!$ifok){
            return 1001;
        }
		
		
		
		$data=array(
			"uid"=>$uid,
			"money"=>$money,
			"votes"=>$cashvotes,
			"orderno"=>$uid.'_'.$nowtime.rand(100,999),
			"status"=>0,
			"addtime"=>$nowtime,
			"uptime"=>$nowtime,
			"type"=>$accountinfo['type'],
			"account_bank"=>$accountinfo['account_bank'],
			"account"=>$accountinfo['account'],
			"name"=>$accountinfo['name'],
		);
		
		$rs=DI()->notorm->users_cashrecord->insert($data);
		if(!$rs){
            return 1002;
		}	        
            
        
						
		
		return $rs;
	}
	
	/* 关注 */
	public function setAttent($uid,$touid){
		$isexist=DI()->notorm->users_attention
					->select("*")
					->where('uid=? and touid=?',$uid,$touid)
					->fetchOne();
		if($isexist){
			DI()->notorm->users_attention
				->where('uid=? and touid=?',$uid,$touid)
				->delete();
			return 0;
		}else{
			DI()->notorm->users_black
				->where('uid=? and touid=?',$uid,$touid)
				->delete();
			DI()->notorm->users_attention
				->insert(array("uid"=>$uid,"touid"=>$touid));
				
			$isexist1=DI()->notorm->users_attention_messages
					->select("*")
					->where('uid=? and touid=?',$uid,$touid)
					->fetchOne();

			if($isexist1){
				DI()->notorm->users_attention_messages->where('uid=? and touid=?',$uid,$touid)->update(array("addtime"=>time()));
			}else{

				DI()->notorm->users_attention_messages
					->insert(array("uid"=>$uid,"touid"=>$touid,"addtime"=>time()));
			}
			return 1;
		}			 
	}	
	
	/* 拉黑 */
	public function setBlack($uid,$touid){
		$isexist=DI()->notorm->users_black
					->select("*")
					->where('uid=? and touid=?',$uid,$touid)
					->fetchOne();
		if($isexist){
			DI()->notorm->users_black
				->where('uid=? and touid=?',$uid,$touid)
				->delete();
			return 0;
		}else{
			DI()->notorm->users_attention
				->where('uid=? and touid=?',$uid,$touid)
				->delete();
			DI()->notorm->users_black
				->insert(array("uid"=>$uid,"touid"=>$touid,"addtime"=>time()));

			return 1;
		}			 
	}
	
	
	/* 关注列表 */
	public function getFollowsList($uid,$touid,$p,$key){
		$pnum=50;
		$start=($p-1)*$pnum;



		if($key!=0&&!$key){
			$touids=DI()->notorm->users_attention
					->select("touid")
					->where('uid=?',$touid)
					->limit($start,$pnum)
					->fetchAll();
		}else{
			$where="a.uid='{$touid}' and u.user_nicename like '%".$key."%'";
			$prefix= DI()->config->get('dbs.tables.__default__.prefix');
			$touids=DI()->notorm->users_attention->queryAll("select a.touid,u.user_nicename from {$prefix}users_attention as a left join {$prefix}users as u on a.touid=u.id where ".$where." limit {$start},{$pnum}");
		}


		foreach($touids as $k=>$v){
			$userinfo=getUserInfo($v['touid']);
			if($userinfo){
				if($uid==$touid){
					$isattent=1;
				}else{
					$isattent=isAttention($uid,$v['touid']);
				}
				$userinfo['isattention']=$isattent;
				$touids[$k]=$userinfo;
			}else{
				DI()->notorm->users_attention->where('uid=? or touid=?',$v['touid'],$v['touid'])->delete();
				unset($touids[$k]);
			}
		}		
		$touids=array_values($touids);
		return $touids;
	}
	
	/* 关注列表 */
	public function getFollowsLists($uid,$touid,$p){
		$pnum=50;
		$start=($p-1)*$pnum;
		$touids=DI()->notorm->users_attention
					->select("touid")
					->where('uid=?',$touid)
					->limit($start,$pnum)
					->fetchAll();
		foreach($touids as $k=>$v){
			$userinfo=getUserInfo($v['touid']);
			if($userinfo){
				if($uid==$touid){
					$isattent=1;
				}else{
					$isattent=isAttention($uid,$v['touid']);
				}
				$userinfo['isattention']=$isattent;
				$touids[$k]=$userinfo;
			}else{
				DI()->notorm->users_attention->where('uid=? or touid=?',$v['touid'],$v['touid'])->delete();
				unset($touids[$k]);
			}
		}		
		$touids=array_values($touids);
		return $touids;
	}
	
	/* 粉丝列表 */
	public function getFansList($uid,$touid,$p){
		$pnum=50;
		$start=($p-1)*$pnum;
		$touids=DI()->notorm->users_attention
					->select("uid")
					->where('touid=?',$touid)
					->limit($start,$pnum)
					->fetchAll();
		foreach($touids as $k=>$v){
			$userinfo=getUserInfo($v['uid']);
			if($userinfo){
				$userinfo['isattention']=isAttention($uid,$v['uid']);
				$touids[$k]=$userinfo;
			}else{
				DI()->notorm->users_attention->where('uid=? or touid=?',$v['uid'],$v['uid'])->delete();
				unset($touids[$k]);
			}
			
		}		
		$touids=array_values($touids);
		return $touids;
	}	

	/* 黑名单列表 */
	public function getBlackList($uid,$touid,$p){
		$pnum=50;
		$start=($p-1)*$pnum;
		$touids=DI()->notorm->users_black
					->select("touid,addtime")
					->where('uid=?',$touid)
					->limit($start,$pnum)
					->fetchAll();
		foreach($touids as $k=>$v){
			
			$userinfo=getUserInfo($v['touid']);
			
			if($userinfo){
				$userinfo['addtime']=date("Y-m-d",$v['addtime']);
				$touids[$k]=$userinfo;
				
			}else{
				DI()->notorm->users_black->where('uid=? or touid=?',$v['touid'],$v['touid'])->delete();
				unset($touids[$k]);
			}
		}
		$touids=array_values($touids);
		return $touids;
	}
	
	/* 直播记录 */
	public function getLiverecord($touid,$p){
		$pnum=50;
		$start=($p-1)*$pnum;
		$record=DI()->notorm->users_liverecord
					->select("id,uid,nums,starttime,endtime,title,city")
					->where('uid=?',$touid)
					->order("id desc")
					->limit($start,$pnum)
					->fetchAll();
		foreach($record as $k=>$v){
			$record[$k]['datestarttime']=date("Y.m.d",$v['starttime']);
			$record[$k]['dateendtime']=date("Y.m.d",$v['endtime']);
            $cha=$v['endtime']-$v['starttime'];
			$record[$k]['length']=getSeconds($cha);
		}						
		return $record;						
	}	
	
		/* 个人主页 */
	public function getUserHome($uid,$touid){
		$info=getUserInfo($touid);
		$info['coin2']=NumberFormat3($info['coin']);
		$info['mine2']=NumberFormat4($info['mine']);
		$info['follows']=(string)getFollows($touid);
		$info['fans']=(string)getFans($touid);
		$info['isattention']=(string)isAttention($uid,$touid);
		$info['isblack']=(string)isBlack($uid,$touid);
		$info['isblack2']=(string)isBlack($touid,$uid);
		$info['praise']=getPraises($touid);
		$info['workVideos']=getWorks($touid);
		$info['likeVideos']=getLikes($touid);
		
		/* 贡献榜前三 */
		$rs=array();
		$rs=DI()->notorm->users_coinrecord
				->select("uid,sum(totalcoin) as total")
				->where('action="sendgift" and touid=?',$touid)
				->group("uid")
				->order("total desc")
				->limit(0,3)
				->fetchAll();
		foreach($rs as $k=>$v){
			$userinfo=getUserInfo($v['uid']);
			$rs[$k]['avatar']=$userinfo['avatar'];
		}		
		$info['contribute']=$rs;	
		
        /* 视频数 */

		if($uid==$touid){  //自己的视频（需要返回视频的状态前台显示）
			$where=" uid={$uid} and isdel='0' and status=1  and is_ad=0";
			$isreg=0;
			//判断有没有签到
			$today = strtotime(date("Y-m-d"),time());
			$signin=DI()->notorm->users_weight_info
				->select("signin")
				->where('uid=? and addtime=?',$uid,$today)
				->fetchOne();
			if($signin && $signin['signin']>0){
				$isreg=1;
			}	
			$info['isreg']=$isreg;
			
		}else{  //访问其他人的主页视频
            $videoids_s=getVideoBlack($uid);
			$where="id not in ({$videoids_s}) and uid={$touid} and isdel='0' and status=1  and is_ad=0";
		}
        
		$videonums=DI()->notorm->users_video
				->where($where)
				->count();
        if(!$videonums){
            $videonums=0;
        }

        $info['videonums']=(string)$videonums;
        /* 直播数 */
        $livenums=DI()->notorm->users_liverecord
					->where('uid=?',$touid)
					->count();
                    
        $info['livenums']=$livenums;        
		/* 直播记录 */
		$record=array();
		$record=DI()->notorm->users_liverecord
					->select("id,uid,nums,starttime,endtime,title,city")
					->where('uid=?',$touid)
					->order("id desc")
					->limit(0,20)
					->fetchAll();
		foreach($record as $k=>$v){
			$record[$k]['datestarttime']=date("Y.m.d",$v['starttime']);
			$record[$k]['dateendtime']=date("Y.m.d",$v['endtime']);
            $cha=$v['endtime']-$v['starttime'];
            $record[$k]['length']=getSeconds($cha);
		}		
		$info['liverecord']=$record;	
		return $info;
	}
	
	/* 贡献榜 */
	public function getContributeList($touid,$p){
		
		$pnum=50;
		$start=($p-1)*$pnum;

		$rs=array();
		$rs=DI()->notorm->users_coinrecord
				->select("uid,sum(totalcoin) as total")
				->where('touid=?',$touid)
				->group("uid")
				->order("total desc")
				->limit($start,$pnum)
				->fetchAll();
				
		foreach($rs as $k=>$v){
			$rs[$k]['userinfo']=getUserInfo($v['uid']);
		}		
		
		return $rs;
	}
	
	/* 设置分销 */
	public function setDistribut($uid,$code){
        
        $isexist=DI()->notorm->users_agent
				->select("*")
				->where('uid=?',$uid)
				->fetchOne();
        if($isexist){
            return 1004;
        }
        
		$oneinfo=DI()->notorm->users_agent_code
				->select("uid")
				->where('code=? and uid!=?',$code,$uid)
				->fetchOne();
		if(!$oneinfo){
			return 1002;
		}
		
		$agentinfo=DI()->notorm->users_agent
				->select("*")
				->where('uid=?',$oneinfo['uid'])
				->fetchOne();
		if(!$agentinfo){
			$agentinfo=array(
				'uid'=>$oneinfo['uid'],
				'one_uid'=>0,
				'two_uid'=>0,
			);
		}
        /* 判断对方是否自己下级 */
        if($agentinfo['one_uid']==$uid  || $agentinfo['two_uid']==$uid ){
            return 1003;
        }
		
		$data=array(
			'uid'=>$uid,
			'one_uid'=>$agentinfo['uid'],
			'two_uid'=>$agentinfo['one_uid'],
			'three_uid'=>$agentinfo['two_uid'],
			'addtime'=>time(),
		);
		DI()->notorm->users_agent->insert($data);
		return 0;
	}
    
    
    /* 印象标签 */
    public function getImpressionLabel(){
        
        /*$key="getImpressionLabel";
		$list=getcaches($key);
		if(!$list){*/
            $list=DI()->notorm->impression_label
				->select("*")
				->order("orderno asc,id desc")
				->fetchAll();
            foreach($list as $k=>$v){
				if(DI()->language=='ko'){
					$list[$k]['name']=$v['name_kr'];
				}
                $list[$k]['colour']='#'.$v['colour'];
            }
			    
			/*setcaches($key,$list); 
		}*/

        return $list;
    }       
    /* 用户标签 */
    public function getUserLabel($uid,$touid){
        $list=DI()->notorm->users_label
				->select("label")
                ->where('uid=? and touid=?',$uid,$touid)
				->fetchOne();  
   
        return $list;
        
    }    

    /* 设置用户标签 */
    public function setUserLabel($uid,$touid,$labels){
        $nowtime=time();
        $isexist=DI()->notorm->users_label
				->select("*")
                ->where('uid=? and touid=?',$uid,$touid)
				->fetchOne();
        if($isexist){
            $rs=DI()->notorm->users_label
                ->where('uid=? and touid=?',$uid,$touid)
				->update(array( 'label'=>$labels,'uptime'=>$nowtime ) );
            
        }else{
            $data=array(
                'uid'=>$uid,
                'touid'=>$touid,
                'label'=>$labels,
                'addtime'=>$nowtime,
                'uptime'=>$nowtime,
            );
            $rs=DI()->notorm->users_label->insert($data);
        }
                
        return $rs;
        
    }    
    
    /* 获取我的标签 */
    public function getMyLabel($uid){
        $rs=array();
        $list=DI()->notorm->users_label
				->select("label")
                ->where('touid=?',$uid)
				->fetchAll();
        $label=array();
        foreach($list as $k=>$v){
            $v_a=preg_split('/,|，/',$v['label']);
            $v_a=array_filter($v_a);
            if($v_a){
                $label=array_merge($label,$v_a);
            }
            
        }

        if(!$label){
            return $rs;
        }
        
        
        $label_nums=array_count_values($label);
        
        $label_key=array_keys($label_nums);
        
        $labels=$this->getImpressionLabel();
        
        $order_nums=array();
        foreach($labels as $k=>$v){
            if(in_array($v['id'],$label_key)){
                $v['nums']=(string)$label_nums[$v['id']];
                $order_nums[]=$v['nums'];
                $rs[]=$v;
            }
        }
        
        array_multisort($order_nums,SORT_DESC,$rs);
        
        return $rs;
        
    }   
    
    /* 获取关于我们列表 */
    public function getPerSetting(){
        $rs=array();
        
        $list=DI()->notorm->posts
				->select("id,post_title")
                ->where("type='2'")
                ->order('orderno asc')
				->fetchAll();
        foreach($list as $k=>$v){
            
            $rs[]=array('id'=>'0','name'=>$v['post_title'],'thumb'=>'' ,'href'=>get_upload_path("/index.php?g=portal&m=page&a=index&id={$v['id']}"));
        }
        
        return $rs;
    }
    
    /* 提现账号列表 */
    public function getUserAccountList($uid){
        
        $list=DI()->notorm->users_cash_account
                ->select("*")
                ->where('uid=?',$uid)
                ->order("addtime desc")
                ->fetchAll();
                
        return $list;
    }

    /* 设置提账号 */
    public function setUserAccount($data){
        
        $rs=DI()->notorm->users_cash_account
                ->insert($data);
                
        return $rs;
    }

    /* 删除提账号 */
    public function delUserAccount($data){
        
        $rs=DI()->notorm->users_cash_account
                ->where($data)
                ->delete();
                
        return $rs;
    }
    
	/* 登录奖励信息 */
	public function LoginBonus($uid){
		$rs=array(
			'bonus_switch'=>'0',
			'bonus_day'=>'0',
			'count_day'=>'0',
			'bonus_list'=>array(),
		);
		$configpri=getConfigPri();
		if(!$configpri['bonus_switch']){
			return $rs;
		}
		$rs['bonus_switch']=$configpri['bonus_switch'];

		
		/* 获取登录设置 */
        $key='loginbonus';
		$list=getcaches($key);
		if(!$list){
            $list=DI()->notorm->loginbonus
					->select("day,coin")
					->fetchAll();
			if($list){
				setcaches($key,$list);
			}
		}
		$rs['bonus_list']=$list;
		$bonus_coin=array();
		foreach($list as $k=>$v){
			$bonus_coin[$v['day']]=$v['coin'];
		}

		/* 登录奖励 */
		$signinfo=DI()->notorm->users_sign
					->select("bonus_day,bonus_time,count_day")
					->where('uid=?',$uid)
					->fetchOne();
		if(!$signinfo){
			$signinfo=array(
				'bonus_day'=>0,
				'bonus_time'=>0,
				'count_day'=>0,
			);
        }
        if($nowtime - $signinfo['bonus_time'] > 60*60*24){
            $signinfo['count_day']=0;
        }
        $rs['count_day']=(string)$signinfo['count_day'];
		$nowtime=time();
		if($nowtime>$signinfo['bonus_time']){
			//更新
			$bonus_time=strtotime(date("Ymd",$nowtime))+60*60*24;
			$bonus_day=$signinfo['bonus_day'];
			if($bonus_day>6){
				$bonus_day=0;
			}
			$bonus_day++;
            $coin=$bonus_coin[$bonus_day];
            
			if($coin){
                $rs['bonus_day']=(string)$bonus_day;
            }
			
		}
		return $rs;
	}
    
	/* 获取登录奖励 */
	public function getLoginBonus($uid){
		$rs=0;
		$configpri=getConfigPri();
		if(!$configpri['bonus_switch']){
			return $rs;
		}
		
		/* 获取登录设置 */
        $key='loginbonus';
		$list=getcaches($key);
		if(!$list){
            $list=DI()->notorm->loginbonus
					->select("day,coin")
					->fetchAll();
			if($list){
				setcaches($key,$list);
			}
		}

		$bonus_coin=array();
		foreach($list as $k=>$v){
			$bonus_coin[$v['day']]=$v['coin'];
		}
		
		$isadd=0;
		/* 登录奖励 */
		$signinfo=DI()->notorm->users_sign
					->select("bonus_day,bonus_time")
					->where('uid=?',$uid)
					->fetchOne();
		if(!$signinfo){
			$isadd=1;
			$signinfo=array(
				'bonus_day'=>0,
				'bonus_time'=>0,
			);
        }
		$nowtime=time();
		if($nowtime>$signinfo['bonus_time']){
			//更新
			$bonus_time=strtotime(date("Ymd",$nowtime))+60*60*24;
			$bonus_day=$signinfo['bonus_day'];
			$count_day=$signinfo['count_day'];
			if($bonus_day>6){
				$bonus_day=0;
			}
            if($nowtime - $signinfo['bonus_time'] > 60*60*24){
                $count_day=0;
            }
			$bonus_day++;
			$count_day++;
            
 
            if($isadd){
                DI()->notorm->users_sign
                    ->insert(array("uid"=>$uid,"bonus_time"=>$bonus_time,"bonus_day"=>$bonus_day,"count_day"=>$count_day ));
            }else{
                DI()->notorm->users_sign
                    ->where('uid=?',$uid)
                    ->update(array("bonus_time"=>$bonus_time,"bonus_day"=>$bonus_day,"count_day"=>$count_day ));
            }
            
            $coin=$bonus_coin[$bonus_day];
            
			if($coin){
                DI()->notorm->users
                    ->where('id=?',$uid)
                    ->update(array( "coin"=>new NotORM_Literal("coin + {$coin}") ));
				

                /* 记录 */
                $insert=array("type"=>'income',"action"=>'loginbonus',"uid"=>$uid,"touid"=>$uid,"giftid"=>$bonus_day,"giftcount"=>'0',"totalcoin"=>$coin,"showid"=>'0',"addtime"=>$nowtime );
                DI()->notorm->users_coinrecord->insert($insert);
            }
            $rs=1;
		}
		
		return $rs;
		
	}
	
	/*获取邀请码*/
	public function getUserinvite($uid){
	
		/*查询邀请码*/
		$isexist=DI()->notorm->users_agent_code
					->select("*")
					->where('uid = ?',$uid)
					->fetchOne();
		if(!$isexist){
			$isexist['code']='';
		}
		
		//邀请的人数
		$count=DI()->notorm->users_invitation
					->where('touid=?',$uid)
					->count();
		if(!$count){
			$count=0;
		}
		//邀请得到的钱
		$currency=DI()->notorm->users_invitation
					->where('touid=?',$uid)
					->sum("touid_currency");
		if(!$currency){
			$currency=0;
		}
		
		$info=array(
			'code'=>$isexist['code'],
			'count'=>$count,
			'currency'=>$currency,
		);
		
		return $info;
		
	}
	
	
	/*获取钱包*/
	public function getwallet($uid,$p){
		$users=DI()->notorm->users
				->select("id,user_nicename,coin,token_address,token_freeze,qr_code")
				->where('id=?  and user_type="2"',$uid)
				->fetchOne();
		if(!$users){
			$users=array();
		}else{
			$users['coin2']=$users['coin'];
			if($users['qr_code']==''){
				$users['qr_code']=scerweima($users['token_address']);
				DI()->notorm->users->where('id=?  and user_type="2"',$uid)->update(array('qr_code'=>$users['qr_code']));
			}
			$img=explode("/", $users['qr_code']);
			
			$users['qr_code']=get_host().'/'.$img[2].'/'.$img[3].'/'.$img[4];
		}
		return $users;
	}
	
	/*获取提币账号*/
	public function getwithdraw($uid,$p){				
		$pnum=50;
		$start=($p-1)*$pnum;
		$list=DI()->notorm->token_address
					->select("id,address")
					->where('uid=?',$uid)
					->limit($start,$pnum)
					->order("addtime desc")
					->fetchAll();
		return $list;
	}
	
	
	/*添加提币账号*/
	public function addwithdraw($uid,$account) {
		$info=DI()->notorm->token_address->where('uid=? and address=?',$uid,$account)->fetchOne();
		if($info){
			return 1002;
		}else{
			$data=array('uid'=>$uid,'address'=>$account,'addtime'=>time());
			$rs=DI()->notorm->token_address->insert($data);
			if(!$rs){
				return 1003;
			}
		}
		$data['id']=$rs['id'];
        return $data;
    }
	
	/*删除提币账号*/
	public function delwithdraw($uid,$number_id) {
		$info=DI()->notorm->token_address->where('uid=? and id=?',$uid,$number_id)->fetchOne();
		if(!$info){
			return 1002;
		}else{
			$rs=DI()->notorm->token_address->where('uid=? and id=?',$uid,$number_id)->delete();
			if(!$rs){
				return 1003;
			}
		}
        return 0;
    }
	
	public function getsign($uid){
		//私密设置
		$configpri=getConfigPri();
		//当天零点的时间戳
		$today = strtotime(date("Y-m-d"),time());
		
		$info=DI()->notorm->users_weight_info->where('uid=? and addtime=?',$uid,$today)->fetchOne();
		if($info && $info['signin']!=0){
			return 1001;
		}
		
		$count=DI()->notorm->users_weight_info->where('signin!=0 and addtime=?',$today)->count();
		if($count==0 && !$count){
			$count=1;
		}else{
			$count=$count+1;
		}
		
		
		//平台当天总权重值
		$log=log($count);
		if($log<=0){
			$log=1;
		}
		$wet=$configpri['mining_fanwi']*$count/$log;
		$zwet=round($wet,4);  //四舍五入 到整数
		
		$pingtai=DI()->notorm->platform_total_weight->where("addtime={$today}")->fetchOne();
		if(!$pingtai){
			DI()->notorm->platform_total_weight->insert(array('totalweight'=>$zwet,'addtime'=>$today));
		}else{
			DI()->notorm->platform_total_weight->where("addtime={$today}")->update(array("totalweight"=>$zwet));
		}
		
		$mining_sign=$configpri['mining_sign']; //签到所得权重值
		
		//用户当天总权重值
		$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$uid,$today)->update(array( "weight"=>new NotORM_Literal("weight + {$mining_sign}")));
		
		
		if(!$userweight){
			$userweight=DI()->notorm->users_total_weight->insert(array('uid'=>$uid,'weight'=>$mining_sign,'addtime'=>$today));
		}
		
		//挖矿-签到添加 (用户详情)
		
		//用户当天权重值详情
		$weight_info=DI()->notorm->users_weight_info->where('uid=? and addtime=?',$uid,$today)->update(array( "signin"=>new NotORM_Literal("signin + {$mining_sign}")));
		
		if(!$weight_info){
			//挖矿-签到添加
			if(!$userweight['id']){
				$userweight=DI()->notorm->users_total_weight->where('uid=? and addtime=?',$uid,$today)->fetchOne();
			}
			
			DI()->notorm->users_weight_info->insert(array('uid'=>$uid,'weight_id'=>$userweight['id'],'signin'=>$mining_sign,'addtime'=>$today));
		}
			
		return 0;
		
	}
	
	/*获取用户喜欢的视频列表*/
	public function getLikeVideos($uid,$p){


		$pnum=18;

		$start=($p-1)*$pnum;

		//获取用户喜欢的视频列表
		$list=DI()->notorm->users_video_like->where("uid=? and status=1 ",$uid)->limit($start,$pnum)->fetchAll();

		if(!$list){
			return 1001;
		}

		foreach ($list as $k => $v) {
			
			$videoinfo=DI()->notorm->users_video->where("id=? and status=1 and isdel=0  and is_ad=0",$v['videoid'])->fetchOne();

			if(!$videoinfo){
				//DI()->notorm->users_video_like->where("videoid=?",$v['videoid'])->delete();
				unset($list[$k]);
				continue;
			}
			$list[$k]['id']=$videoinfo['id'];
			$list[$k]['addtime']=date('Y-m-d H:i:s', $v['addtime']);
			$list[$k]['uid']=$videoinfo['uid'];
			$list[$k]['title']=$videoinfo['title'];
			$list[$k]['thumb']=get_upload_path($videoinfo['thumb']);
			$list[$k]['thumb_s']=get_upload_path($videoinfo['thumb_s']);
			$list[$k]['href']=$videoinfo['href'];
			$list[$k]['likes']=$videoinfo['likes'];
			$list[$k]['views']=$videoinfo['views'];
			$list[$k]['comments']=$videoinfo['comments'];
			$list[$k]['shares']=$videoinfo['shares'];
			$list[$k]['city']=$videoinfo['city']?$videoinfo['city']:'';
			$list[$k]['lat']=$videoinfo['lat'];
			$list[$k]['lng']=$videoinfo['lng'];
			$list[$k]['datetime']=datetime($v['addtime']);
			$list[$k]['islike']='1';
			$list[$k]['userinfo']=getUserInfo($videoinfo['uid']);
			$list[$k]['isattent']=(string)isAttention($uid,$videoinfo['uid']);

			$list[$k]['isdel']='0';  //暂时跟getAttentionVideo统一(包含下面的)
			$list[$k]['steps']='0';
			$list[$k]['isstep']='0';
			$list[$k]['isdialect']='0';
			$list[$k]['musicinfo']=getMusicInfo($list[$k]['userinfo']['user_nicename'],$v['music_id']);	

			unset($list[$k]['videoid']);

			$lista[]=$list[$k];  //因为unset掉某个数组后，k值连不起来了，所以重新赋值给新数组

		}

		if(empty($lista)){
			$lista=array();
		}

		return $lista;
	}
	
}
