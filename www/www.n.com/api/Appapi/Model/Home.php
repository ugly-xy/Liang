<?php
session_start();
class Model_Home extends PhalApi_Model_NotORM {

	/* 轮播 */
	public function getSlide(){

		$rs=DI()->notorm->slide
			->select("slide_pic,slide_url")
			->where("slide_status='1' and slide_cid='0' ")
			->order("listorder asc")
			->fetchAll();
		foreach($rs as $k=>$v){
			$rs[$k]['slide_pic']=get_upload_path($v['slide_pic']);
		}				

		return $rs;				
	}

	/* 热门 */
    public function getHot($p) {

		$pnum=50;
		$start=($p-1)*$pnum;
		$where=" l.islive= '1' and u.ishot='1'";

		if($p!=1){
			$endtime=$_SESSION['hot_starttime'];
            if($endtime){
                $where.=" and starttime < {$endtime}";
            }
			
		}
        $configpri=getConfigPri();
		$prefix= DI()->config->get('dbs.tables.__default__.prefix');
		
		$result=DI()->notorm->users_live
					->queryAll("select l.uid,l.avatar,l.avatar_thumb,l.user_nicename,l.title,l.city,l.stream,l.pull,l.thumb,l.isvideo,l.type,l.type_val,l.game_action,l.goodnum,l.anyway,u.sex,u.votestotal,u.grade,u.consumption from {$prefix}users_live l left join {$prefix}users u on l.uid=u.id where {$where} order by u.isrecommend desc,l.hotvotes desc,l.starttime desc limit {$start},{$pnum}");

		foreach($result as $k=>$v){
			$nums=DI()->redis->zSize('user_'.$v['stream']);

			$v['nums']=(string)$nums;
			
			$v['level']=getLevel($v['grade']);
			
			$v['game']=getGame($v['game_action']);
			
			$v['avatar']=get_upload_path($v['avatar']);
			$v['avatar_thumb']=get_upload_path($v['avatar_thumb']);
			if(!$v['thumb']){
				$v['thumb']=get_upload_path($v['avatar']);
			}
			if($v['isvideo']==0 && $configpri['cdn_switch']!=5){
				$v['pull']=PrivateKeyA('rtmp',$v['stream'],0);
			}
			
			if($v['type']==1){
				$v['type_val']='';
			}
            
            $result[$k]=$v;
			
		}	
		if($result){
			$last=end($result);
			$_SESSION['hot_starttime']=$last['starttime'];
		}
		
		return $result;
    }
	
		/* 关注列表-直播 */
    public function getFollow($uid,$p) {
        /*$rs=array(
            'title'=>'你还没有关注任何主播',
            'des'=>'赶快去关注自己喜欢的主播吧~',
            'list'=>array(),
        );*/
		$rs=T('follow');
		$result=array();
		$pnum=50;
		$start=($p-1)*$pnum;
		$configpri=getConfigPri();
		$touid=DI()->notorm->users_attention
				->select("touid")
				->where('uid=?',$uid)
				->fetchAll();
				
		if($touid){
            $rs['title']=T('你关注的主播没有开播');
            $rs['des']=T('赶快去看看其他主播的直播吧~');
            $where=" islive='1' ";					
            if($p!=1){
                $endtime=$_SESSION['follow_starttime'];
                if($endtime){
                    $start=0;
                    $where.=" and starttime < {$endtime}";
                }
                
            }	
        
			$touids=array_column($touid,"touid");
			$touidss=implode(",",$touids);
			$where.=" and uid in ({$touidss})";
			$result=DI()->notorm->users_live
					->select("uid,avatar,avatar_thumb,user_nicename,title,city,stream,pull,thumb,isvideo,type,type_val,game_action,goodnum,anyway,starttime")
					->where($where)
					->order("starttime desc")
					->limit($start,$pnum)
					->fetchAll();
		}	
		foreach($result as $k=>$v){
			$nums=DI()->redis->zSize('user_'.$v['stream']);
			$v['nums']=(string)$nums;
			
			$userinfo=getUserInfo($v['uid']);
			$v['sex']=$userinfo['sex'];
			$v['level']=$userinfo['level'];
			
			$v['game']=getGame($v['game_action']);
			
			$v['avatar']=get_upload_path($v['avatar']);
			$v['avatar_thumb']=get_upload_path($v['avatar_thumb']);
			if(!$v['thumb']){
				$v['thumb']=get_upload_path($v['avatar']);
			}
			if($v['isvideo']==0 && $configpri['cdn_switch']!=5){
				$v['pull']=PrivateKeyA('rtmp',$v['stream'],0);
			}
			if($v['type']==1){
				$v['type_val']='';
			}
            $result[$k]=$v;
		}	

		if($result){
			$last=end($result);
			$_SESSION['follow_starttime']=$last['starttime'];
		}
        
        $rs['list']=$result;

		return $rs;					
    }
	
	
	
		
		/* 最新 */
    public function getNew($lng,$lat,$p) {
		$pnum=50;
		$start=($p-1)*$pnum;
		$where=" islive='1' ";

		if($p!=1){
			$endtime=$_SESSION['new_starttime'];
            if($endtime){
                $where.=" and starttime < {$endtime}";
            }
		}
		$configpri=getConfigPri();
		$result=DI()->notorm->users_live
				->select("uid,avatar,avatar_thumb,user_nicename,title,city,stream,lng,lat,pull,thumb,isvideo,type,type_val,game_action,goodnum,anyway,starttime")
				->where($where)
				->order("starttime desc")
				->limit($start,$pnum)
				->fetchAll();	
		foreach($result as $k=>$v){
			$nums=DI()->redis->zSize('user_'.$v['stream']);
			$v['nums']=(string)$nums;
			
			$userinfo=getUserInfo($v['uid']);
			$v['sex']=$userinfo['sex'];
			$v['level']=$userinfo['level'];
			
			$v['game']=getGame($v['game_action']);
			
			$v['avatar']=get_upload_path($v['avatar']);
			$v['avatar_thumb']=get_upload_path($v['avatar_thumb']);
			if(!$v['thumb']){
				$v['thumb']=get_upload_path($v['avatar']);
			}
			if($v['isvideo']==0 && $configpri['cdn_switch']!=5){
				$v['pull']=PrivateKeyA('rtmp',$v['stream'],0);
			}
			
			if($v['type']==1){
				$v['type_val']='';
			}
			
			$distance=T('好像在火星');
			if($lng!='' && $lat!='' && $v['lat']!='' && $v['lng']!=''){
				$distance=getDistance($lat,$lng,$v['lat'],$v['lng']);
			}else if($v['city']){
				$distance=$v['city'];	
			}
			
			$v['distance']=$distance;
			unset($v['lng']);
			unset($v['lat']);
            
            $result[$k]=$v;
			
		}		
		if($result){
			$last=end($result);
			$_SESSION['new_starttime']=$last['starttime'];
		}

		return $result;
    }
		
		/* 搜索 */
    public function search($uid,$key,$p) {
		
		$pnum=50;
		$start=($p-1)*$pnum;
		$where=' user_type="2" and ( id=? or user_nicename like ?) and id!=?';


		if($p!=1){
			$id=$_SESSION['search'];
			$where.=" and id < {$id}";
		}

		
		$result=DI()->notorm->users
				->select("id,user_nicename,avatar,coin,avatar_thumb,sex,signature,province,city,birthday,age")
				->where($where,$key,'%'.$key.'%',$uid)
				->order("id desc")
				->limit($start,$pnum)
				->fetchAll();
		
		foreach($result as $k=>$v){

			$result[$k]['isattention']=(string)isAttention($uid,$v['id']);
			$result[$k]['avatar']=get_upload_path($v['avatar']);
			$result[$k]['avatar_thumb']=get_upload_path($v['avatar_thumb']);
			if($v['age']<0){
				$result[$k]['age']=T("年龄未填写");
			}else{
				$result[$k]['age'].=T("岁");
			}

			if($v['city']==""){
				$result[$k]['city']=T("城市未填写");
			}

			$result[$k]['praise']=getPraises($v['id']);
			$result[$k]['fans']=getFans($v['id']);					
			$result[$k]['follows']=getFollows($v['id']);
			$result[$k]['coin']="0";


			unset($result[$k]['consumption']);
		}

		if($result){
			$last=array_slice($result,-1,1);
			$_SESSION['search']=$last[0]['id'];
		}

		
		return $result;
    }
	



    public function videoSearch($uid,$key,$p) {
		$pnum=50;
		$start=($p-1)*$pnum;

		$where="v.isdel=0 and v.status=1 and v.is_ad=0";

		$where.=" and v.uid like '%".$key."%' or v.title like '%".$key."%' or u.user_nicename like '%".$key."%'";
		if($p!=1){
			$id=$_SESSION['videosearch'];
			$where.=" and v.id < {$id}";
		}

		$prefix= DI()->config->get('dbs.tables.__default__.prefix');

		$result=DI()->notorm->users_video
				->queryAll("select v.*,u.user_nicename,u.avatar from {$prefix}users_video v left join {$prefix}users u on v.uid=u.id where {$where} order by v.addtime desc limit {$start},{$pnum}");

		if($result){
			$last=array_slice($result,-1,1);
			$_SESSION['videosearch']=$last['id'];
		}



		foreach ($result as $k => $v) {
			$userinfo=getUserInfo($v['uid']);
			if(!$userinfo){
				$userinfo['user_nicename']=T("已删除");
			}

			$result[$k]['userinfo']=$userinfo;
			$result[$k]['datetime']=datetime($v['addtime']);
			$result[$k]['comments']=NumberFormat($v['comments']);
			$result[$k]['likes']=NumberFormat($v['likes']);
			$result[$k]['steps']=NumberFormat($v['steps']);
			if($uid){
				$result[$k]['islike']=(string)ifLike($uid,$v['id']);	
				$result[$k]['isstep']=(string)ifStep($uid,$v['id']);	
				$result[$k]['isattent']=(string)isAttention($uid,$v['uid']);	
			}else{
				$result[$k]['islike']=0;	
				$result[$k]['isstep']=0;	
				$result[$k]['isattent']=0;	
			}

			$result[$k]['musicinfo']=getMusicInfo($result[$k]['userinfo']['user_nicename'],$v['music_id']);
		}

		
		return $result;
    }
	
	/* 附近 */
    public function getNearby($lng,$lat,$p) {
		$pnum=50;
		$start=($p-1)*$pnum;
		$where=" islive='1' and lng!='' and lat!='' ";
		$configpri=getConfigPri();
		$result=DI()->notorm->users_live
				->select("uid,avatar,avatar_thumb,user_nicename,title,province,city,stream,lng,lat,pull,isvideo,thumb,islive,type,type_val,game_action,goodnum,anyway,getDistance('{$lat}','{$lng}',lat,lng) as distance")
				->where($where)
                ->order("distance asc")
                ->limit($start,$pnum)
				->fetchAll();	
		foreach($result as $k=>$v){
			$nums=DI()->redis->zSize('user_'.$v['stream']);
			$v['nums']=(string)$nums;
			
			$userinfo=getUserInfo($v['uid']);
			$v['sex']=$userinfo['sex'];
			$v['level']=$userinfo['level'];
			
			$v['game']=getGame($v['game_action']);
			
			$v['avatar']=get_upload_path($v['avatar']);
			$v['avatar_thumb']=get_upload_path($v['avatar_thumb']);
			if(!$v['thumb']){
				$v['thumb']=get_upload_path($v['avatar']);
			}
			if($v['isvideo']==0 && $configpri['cdn_switch']!=5){
				$v['pull']=PrivateKeyA('rtmp',$v['stream'],0);
			}
			
			if($v['type']==1){
				$v['type_val']='';
			}
            if($v['distance']>1000){
                $v['distance']=1000;
            }
            $v['distance']=$v['distance'].'km';

            $result[$k]=$v;
		}
		
		return $result;
    }


	/* 推荐 */
	public function getRecommend(){

		$result=DI()->notorm->users
				->select("id,user_nicename,avatar,avatar_thumb")
				->where("isrecommend='1'")
				->order("votestotal desc")
				->limit(0,12)
				->fetchAll();
		foreach($result as $k=>$v){
			$v['avatar']=get_upload_path($v['avatar']);
			$v['avatar_thumb']=get_upload_path($v['avatar_thumb']);
			$fans=getFans($v['id']);
			$v['fans']=T('粉丝').' · '.$fans;
            
            $result[$k]=$v;
		}
		return  $result;
	}
	/* 关注推荐 */
	public function attentRecommend($uid,$touids){
		//$users=$this->getRecommend();
		$users=explode(',',$touids);
		foreach($users as $k=>$v){
			$touid=$v;
			if($touid && !isAttention($uid,$touid)){
				DI()->notorm->users_black
					->where('uid=? and touid=?',$uid,$touid)
					->delete();
				DI()->notorm->users_attention
					->insert(array("uid"=>$uid,"touid"=>$touid));
			}
			
		}
		return 1;
	}

	/*获取收益排行榜*/

	public function profitList($uid,$type,$p){

		$pnum=50;
		$start=($p-1)*$pnum;

		switch ($type) {
			case 'day':
				//获取今天开始结束时间
				$dayStart=strtotime(date("Y-m-d"));
				$dayEnd=strtotime(date("Y-m-d 23:59:59"));
				$where=" r.addtime >={$dayStart} and r.addtime<={$dayEnd} and ";

			break;

			case 'week':
                $w=date('w'); 
                //获取本周开始日期，如果$w是0，则表示周日，减去 6 天 
                $first=1;
                //周一
                $week=date('Y-m-d H:i:s',strtotime( date("Ymd")."-".($w ? $w - $first : 6).' days')); 
                $week_start=strtotime( date("Ymd")."-".($w ? $w - $first : 6).' days'); 

                //本周结束日期 
                //周天
                $week_end=strtotime("{$week} +1 week")-1;
                
				$where=" r.addtime >={$week_start} and r.addtime<={$week_end} and ";

			break;

			case 'month':
                //本月第一天
                $month=date('Y-m-d',strtotime(date("Ym").'01'));
                $month_start=strtotime(date("Ym").'01');

                //本月最后一天
                $month_end=strtotime("{$month} +1 month")-1;

				$where=" r.addtime >={$month_start} and r.addtime<={$month_end} and ";

			break;

			case 'total':
				$where=" ";
			break;
			
			default:
				//获取今天开始结束时间
				$dayStart=strtotime(date("Y-m-d"));
				$dayEnd=strtotime(date("Y-m-d 23:59:59"));
				$where=" r.addtime >={$dayStart} and r.addtime<={$dayEnd} and ";
			break;
		}




		// $where.=" r.type='expend' and r.action in ('sendgift','sendbarrage')";
		$where.=" r.type='expend' and r.action in ('sendgift','sendbarrage','giftvideo')";

		$prefix= DI()->config->get('dbs.tables.__default__.prefix');
		
		$result=DI()->notorm->users_coinrecord

			->queryAll("select sum(r.totalcoin) as totalcoin,r.touid as uid,u.grade,u.user_nicename,u.avatar_thumb,u.sex from {$prefix}users_coinrecord r left join {$prefix}users u on r.touid=u.id where {$where}  group by r.touid order by totalcoin desc limit {$start},{$pnum}");
		foreach ($result as $k => $v) {
			$v['levelAnchor']=getLevel($v['grade']); //主播等级
			$v['isAttention']=isAttention($uid,$v['uid']);//判断当前用户是否关注了该主播
			$v['avatar_thumb']=get_upload_path($v['avatar_thumb']);
			$v['totalcoin']=$v['totalcoin'];
		
            
            $result[$k]=$v;
		}


		return $result;
	}



	/*获取消费排行榜*/
	public function consumeList($uid,$type,$p){

		$pnum=50;
		$start=($p-1)*$pnum;

		switch ($type) {
			case 'day':
				//获取今天开始结束时间
				$dayStart=strtotime(date("Y-m-d"));
				$dayEnd=strtotime(date("Y-m-d 23:59:59"));
				$where=" r.addtime >={$dayStart} and r.addtime<={$dayEnd} and ";

			break;
            
            case 'week':
                $w=date('w'); 
                //获取本周开始日期，如果$w是0，则表示周日，减去 6 天 
                $first=1;
                //周一
                $week=date('Y-m-d H:i:s',strtotime( date("Ymd")."-".($w ? $w - $first : 6).' days')); 
                $week_start=strtotime( date("Ymd")."-".($w ? $w - $first : 6).' days'); 

                //本周结束日期 
                //周天
                $week_end=strtotime("{$week} +1 week")-1;
                
				$where=" r.addtime >={$week_start} and r.addtime<={$week_end} and ";

			break;

			case 'month':
                //本月第一天
                $month=date('Y-m-d',strtotime(date("Ym").'01'));
                $month_start=strtotime(date("Ym").'01');

                //本月最后一天
                $month_end=strtotime("{$month} +1 month")-1;

				$where=" r.addtime >={$month_start} and r.addtime<={$month_end} and ";

			break;

			case 'total':
				$where=" ";
			break;
			
			default:
				//获取今天开始结束时间
				$dayStart=strtotime(date("Y-m-d"));
				$dayEnd=strtotime(date("Y-m-d 23:59:59"));
				$where=" r.addtime >={$dayStart} and r.addtime<={$dayEnd} and ";
			break;
		}




		// $where.=" r.type='expend' and r.action in ('sendgift','sendbarrage')";
		$where.=" r.type='expend'";

		$prefix= DI()->config->get('dbs.tables.__default__.prefix');
		
		$result=DI()->notorm->users_coinrecord

			->queryAll("select sum(r.totalcoin) as totalcoin,r.uid as uid,u.consumption,u.grade,u.user_nicename,u.avatar_thumb,u.sex from {$prefix}users_coinrecord r left join {$prefix}users u on r.uid=u.id where {$where}  group by r.uid order by totalcoin desc limit {$start},{$pnum}");
		foreach ($result as $k => $v) {
			$v['level']=getLevel($v['grade']); //用户等级
			$v['isAttention']=isAttention($uid,$v['uid']);//判断当前用户是否关注了该用户
			$v['avatar_thumb']=get_upload_path($v['avatar_thumb']);
			$v['totalcoin']=$v['totalcoin'];
			unset($v['consumption']);
            
            $result[$k]=$v;

		}


		return $result;
	}
    
    /* 分类下直播 */
    public function getClassLive($liveclassid,$p) {
		$pnum=50;
		$start=($p-1)*$pnum;
		$where=" islive='1' and liveclassid={$liveclassid} ";
        $configpri=getConfigPri();
		if($p!=1){
			$endtime=$_SESSION['getClassLive_starttime'];
            if($endtime){
                $where.=" and starttime < {$endtime}";
            }
			
		}
		$last_starttime=0;
		$result=DI()->notorm->users_live
				->select("uid,avatar,avatar_thumb,user_nicename,title,city,stream,pull,thumb,isvideo,type,type_val,game_action,goodnum,anyway,starttime")
				->where($where)
				->order("starttime desc")
				->limit($start,$pnum)
				->fetchAll();	
		foreach($result as $k=>$v){
			$nums=DI()->redis->zSize('user_'.$v['stream']);
			$v['nums']=(string)$nums;
			
			$userinfo=getUserInfo($v['uid']);
			$v['sex']=$userinfo['sex'];
			$v['level']=$userinfo['level'];
			
			$v['game']=getGame($v['game_action']);
			
			$v['avatar']=get_upload_path($v['avatar']);
			$v['avatar_thumb']=get_upload_path($v['avatar_thumb']);
			if(!$v['thumb']){
				$v['thumb']=get_upload_path($v['avatar']);
			}
			if($v['isvideo']==0 && $configpri['cdn_switch']!=5){
				$v['pull']=PrivateKeyA('rtmp',$v['stream'],0);
			}
			
			if($v['type']==1){
				$v['type_val']='';
			}
            $result[$k]=$v;
		}		
		if($result){
            $last=end($result);
			$_SESSION['getClassLive_starttime']=$last['starttime'];
		}

		return $result;
    }

}
