<?php

include $_SERVER['DOCUMENT_ROOT'].'/lib/Encrypt.php';

class Model_Login extends PhalApi_Model_NotORM {
	protected $fields='id,user_nicename,avatar,avatar_thumb,sex,signature,coin,consumption,votestotal,province,city,birthday,user_status,login_type,last_login_time,grade';

	/* 会员登录 */   	
    public function userLogin($user_login,$user_pass) {

		$user_pass=setPass($user_pass);
		
		$info=DI()->notorm->users
				->select($this->fields.',user_pass')
				->where('user_login=? and user_type="2"',$user_login) 
				->fetchOne();
		if(!$info || $info['user_pass'] != $user_pass){
			return 1001;
		}
		unset($info['user_pass']);
		if($info['user_status']=='0'){
			return 1002;					
		}
		unset($info['user_status']);
		
		// $info['isreg']='0';
		$info['isagent']='0';
        
		if($info['last_login_time']==''){
			// $info['isreg']='1';
			$info['isagent']='1';
		}
		/*else{
			//查询是否签到过该用户
			$today = strtotime(date("Y-m-d"),time());
			
			$res=DI()->notorm->users_weight_info->where('uid=? and addtime=?',$info['id'],$today)->fetchOne();
			
			if(!$res || $res['signin']==0){
				$info['isreg']='1';
			}
		}*/
		
        $configpri=getConfigPri();
        if($configpri['agent_switch']==0){
            $info['isagent']='0';
        }
		
		$info['level']=getLevel($info['grade']);

		$token=md5(md5($info['id'].$user_login.time()));
		
		$info['token']=$token;
		$info['avatar']=get_upload_path($info['avatar']);
		$info['avatar_thumb']=get_upload_path($info['avatar_thumb']);
		
		$this->updateToken($info['id'],$token);
		
        return $info;
    }	
	
	/* 会员注册 */
    public function userReg($user_login,$user_pass,$source,$invite) {

		$user_pass=setPass($user_pass);
		
		$configpri=getConfigPri();
		
		//获取钱包地址
		$getaddress=$this->getaddress();
		
		$now=time();
		$nowYear=date("Y",$now);
		$birthdayYear=2000;
		$data=array(
			'user_login' => $user_login,
			'mobile' =>$user_login,
			'user_nicename' =>T('手机用户').substr($user_login,-4),
			'user_pass' =>$user_pass,
			'signature' =>T('这家伙很懒，什么都没留下'),
			'avatar' =>'/default.jpg',
			'avatar_thumb' =>'/default_thumb.jpg',
			'last_login_ip' =>$_SERVER['REMOTE_ADDR'],
			'create_time' => date("Y-m-d H:i:s"),
			'user_status' => 1,
			"user_type"=>2,//会员
			"source"=>$source,
			"coin"=>$configpri['reg_reward'],
			"age"=>$nowYear-$birthdayYear,
			"mobile"=>$user_login,
			"birthday"=>'2000-01-01',
			"token_address"=>$getaddress['token_address'],
			"token_privatekey"=>$getaddress['token_privatekey'],

		);
		
		
		$isexist=DI()->notorm->users
				->select('id')
				->where('user_login=?',$user_login) 
				->fetchOne();
		if($isexist){
			return 1006;
		}
		if($invite!=''){
			$isinvite=DI()->notorm->users_agent_code
					->select("*")
					->where('code = ?',$invite)
					->fetchOne();
			if(!$isinvite){
				return 1008;
			}
		}
		$rs=DI()->notorm->users->insert($data);	
		if(!$rs){
			return 1007;
		}
		$code=createCode();
		$code_info=array('uid'=>$rs['id'],'code'=>$code);
		$isexist=DI()->notorm->users_agent_code
					->select("*")
					->where('uid = ?',$rs['id'])
					->fetchOne();
					
					
		//邀请用户  奖励
		if($invite!=''){
			if($isinvite){
				$invite_info=array(
					'uid'=>$rs['id'],
					'touid'=>$isinvite['uid'],
					'uid_currency'=>$configpri['uid_currency'],
					'touid_currency'=>$configpri['touid_currency'],
					'addtime' => time(),
				);
				DI()->notorm->users_invitation->insert($invite_info);
				
				DI()->notorm->users->where('id = ?', $rs['id'])->update(array('coin' => new NotORM_Literal("coin + {$configpri['uid_currency']}")));
				DI()->notorm->users->where('id = ?', $isinvite['uid'])->update(array('coin' => new NotORM_Literal("coin + {$configpri['touid_currency']}")));
			}
		}
		
		
		if($isexist){
			DI()->notorm->users_agent_code->where('uid = ?',$rs['id'])->update($code_info);	
		}else{
			DI()->notorm->users_agent_code->insert($code_info);	
		}
		return 1;
    }	

	/* 找回密码 */
	public function userFindPass($user_login,$user_pass){
		$isexist=DI()->notorm->users
				->select('id')
				->where('user_login=? and user_type="2"',$user_login) 
				->fetchOne();
		if(!$isexist){
			return 1006;
		}		
		$user_pass=setPass($user_pass);

		return DI()->notorm->users
				->where('id=?',$isexist['id']) 
				->update(array('user_pass'=>$user_pass));
		
	}	
		
	/* 第三方会员登录 */
    public function userLoginByThird($openid,$type,$nickname,$avatar,$source) {			
        $info=DI()->notorm->users
            ->select($this->fields)
            ->where('openid=? and login_type=? ',$openid,$type)
            ->fetchOne();
		$configpri=getConfigPri();
		if(!$info){
			/* 注册 */
			$user_pass='yunbaokeji';
			$user_pass=setPass($user_pass);
			$user_login=$type.'_'.time().rand(100,999);

			if(!$nickname){
				$nickname=$type.T('用户-').substr($openid,-4);
			}else{
				$nickname=urldecode($nickname);
			}
			if(!$avatar){
				$avatar='/default.jpg';
				$avatar_thumb='/default_thumb.jpg';
			}else{
				$avatar=urldecode($avatar);
				$avatar_a=explode('/',$avatar);
				$avatar_a_n=count($avatar_a);
				if($type=='qq'){
					$avatar_a[$avatar_a_n-1]='100';
					$avatar_thumb=implode('/',$avatar_a);
				}else if($type=='wx'){
					$avatar_a[$avatar_a_n-1]='64';
					$avatar_thumb=implode('/',$avatar_a);
				}else{
					$avatar_thumb=$avatar;
				}
				
			}
			//获取钱包地址
			$getaddress=$this->getaddress();
			
			$now=time();
			$nowYear=date("Y",$now);
			$birthdayYear=2000;
			
			$data=array(
				'user_login' => $user_login,
				'user_nicename' =>$nickname,
				'user_pass' =>$user_pass,
				'signature' =>T('这家伙很懒，什么都没留下'),
				'avatar' =>$avatar,
				'avatar_thumb' =>$avatar_thumb,
				'last_login_ip' =>$_SERVER['REMOTE_ADDR'],
				'create_time' => date("Y-m-d H:i:s"),
				'user_status' => 1,
				'openid' => $openid,
				'login_type' => $type, 
				"user_type"=>2,//会员
				"source"=>$source,
				"coin"=>$configpri['reg_reward'],
				"age"=>$nowYear-$birthdayYear,
				"birthday"=>'2000-01-01',
				"token_address"=>$getaddress['token_address'],
				"token_privatekey"=>$getaddress['token_privatekey'],
			);
			
			$rs=DI()->notorm->users->insert($data);

			$code=createCode();
			$code_info=array('uid'=>$rs['id'],'code'=>$code);
			$isexist=DI()->notorm->users_agent_code
						->select("*")
						->where('uid = ?',$rs['id'])
						->fetchOne();
			if($isexist){
				DI()->notorm->users_agent_code->where('uid = ?',$rs['id'])->update($code_info);	
			}else{
				DI()->notorm->users_agent_code->insert($code_info);	
			}
            
			$info['id']=$rs['id'];
			$info['user_nicename']=$data['user_nicename'];
			$info['avatar']=get_upload_path($data['avatar']);
			$info['avatar_thumb']=get_upload_path($data['avatar_thumb']);
			$info['sex']='2';
			$info['signature']=$data['signature'];
			$info['coin']='0';
			$info['login_type']=$data['login_type'];
			$info['province']='';
			$info['city']='';
			$info['birthday']='';
			$info['consumption']='0';
			$info['user_status']=1;
			$info['last_login_time']='';
		}else{
			if(!$avatar){
				$avatar='/default.jpg';
				$avatar_thumb='/default_thumb.jpg';
			}else{
				$avatar=urldecode($avatar);
				$avatar_a=explode('/',$avatar);
				$avatar_a_n=count($avatar_a);
				if($type=='qq'){
					$avatar_a[$avatar_a_n-1]='100';
					$avatar_thumb=implode('/',$avatar_a);
				}else if($type=='wx'){
					$avatar_a[$avatar_a_n-1]='64';
					$avatar_thumb=implode('/',$avatar_a);
				}else{
					$avatar_thumb=$avatar;
				}
				
			}
			
			$info['avatar']=$avatar;
			$info['avatar_thumb']=$avatar_thumb;
			
			$data=array(
				'avatar' =>$avatar,
				'avatar_thumb' =>$avatar_thumb,
			);
			
		}
		
		if($info['user_status']=='0'){
			return 1001;					
		}
		unset($info['user_status']);
		
		// $info['isreg']='0';
		$info['isagent']='0';
		if($info['last_login_time']=='' ){
			// $info['isreg']='1';
			$info['isagent']='1';
		}
		/*else{
			//查询是否签到过该用户
			$today = strtotime(date("Y-m-d"),time());
			
			$res=DI()->notorm->users_weight_info->where('uid=? and addtime=?',$info['id'],$today)->fetchOne();
			
			if(!$res || $res['signin']==0){
				$info['isreg']='1';
			}
		}*/

        if($configpri['agent_switch']==0){
            $info['isagent']='0';
        }
		unset($info['last_login_time']);
		
		$info['level']=getLevel($info['grade']);


		$token=md5(md5($info['id'].$openid.time()));
		
		$info['token']=$token;
		$info['avatar']=get_upload_path($info['avatar']);
		$info['avatar_thumb']=get_upload_path($info['avatar_thumb']);
		
		$this->updateToken($info['id'],$token);
		
        return $info;
    }		
	
	/* 更新token 登陆信息 */
    public function updateToken($uid,$token,$data=array()) {
		$configpub=getConfigPub();
		$expiretime=time()+(60*60*$configpub['times']);

		DI()->notorm->users
			->where('id=?',$uid)
			->update(array("token"=>$token, "expiretime"=>$expiretime ,'last_login_time' => date("Y-m-d H:i:s"), "last_login_ip"=>$_SERVER['REMOTE_ADDR'] ));

		$token_info=array(
			'uid'=>$uid,
			'token'=>$token,
			'expiretime'=>$expiretime,
		);
		
		setcaches("token_".$uid,$token_info);		
        
		return 1;
    }	
	
	
    
    /* 更新极光ID */
    public function upUserPush($uid,$pushid){
        
        $isexist=DI()->notorm->users_pushid
                    ->select('*')
                    ->where('uid=?',$uid)
                    ->fetchOne();
        if(!$isexist){
            DI()->notorm->users_pushid->insert(array('uid'=>$uid,'pushid'=>$pushid));
        }else if($isexist['pushid']!=$pushid){
            DI()->notorm->users_pushid->where('uid=?',$uid)->update(array('pushid'=>$pushid));
        }
        return 1;
    }
	
	/* 会员登录 */   	
    public function codeLogin($user_login,$source) {
			
		$configpri=getConfigPri();	
		$info=DI()->notorm->users
				->select($this->fields)
				->where('user_login=? and user_type="2"',$user_login) 
				->fetchOne();

		$now=time();
		$nowYear=date("Y",$now);

		if(!$info){
			
			$birthdayYear=2000;
			//获取钱包地址
			$getaddress=$this->getaddress();
			
			//新注册该用户
			$user_pass='yunbaokeji';
			$user_pass=setPass($user_pass);
			$user_login=$user_login;
			$code=createCode();
			$data=array(
				'user_login' => $user_login,
				'mobile' =>$user_login,
				'user_nicename' =>T('手机用户').substr($user_login,-4),
				'user_pass' =>$user_pass,
				'signature' =>T('这家伙很懒，什么都没留下'),
				'avatar' =>'/default.jpg',
				'avatar_thumb' =>'/default_thumb.jpg',
				'last_login_ip' =>$_SERVER['REMOTE_ADDR'],
				'create_time' => date("Y-m-d H:i:s"),
				'user_status' => 1,
				"user_type"=>2,//会员
				"source"=>$source,
				"coin"=>$configpri['reg_reward'],
				"age"=>$nowYear-$birthdayYear,
				"mobile"=>$user_login,
				"birthday"=>'2000-01-01',
				"token_address"=>$getaddress['token_address'],
				"token_privatekey"=>$getaddress['token_privatekey'],
				
			);
			
			$rs=DI()->notorm->users->insert($data);	
			$code=createCode();
			$code_info=array('uid'=>$rs['id'],'code'=>$code);
			$isexist=DI()->notorm->users_agent_code
							->select("*")
							->where('uid = ?',$rs['id'])
							->fetchOne();
			if($isexist){
				DI()->notorm->users_agent_code->where('uid = ?',$rs['id'])->update($code_info);	
			}else{
				DI()->notorm->users_agent_code->insert($code_info);	
			}
			
			$info['id']=$rs['id'];
			$info['user_nicename']=$data['user_nicename'];
			$info['avatar']=get_upload_path($data['avatar']);
			$info['avatar_thumb']=get_upload_path($data['avatar_thumb']);
			$info['sex']='2';
			$info['signature']=$data['signature'];
			$info['coin']='0';
			$info['login_type']=$data['login_type'];
			$info['province']='';
			$info['city']='';
			$info['birthday']='';
			$info['last_login_time']='';
			$info['code']=$code;
			$info['age']="0";
			$info['mobile']=$user_login;
			// $info['isreg']='1';
			$info['hometown']='';
			
			
		}else{

			//重新计算用户的年龄
			$month=date("m",strtotime($info['birthday']));
			$nowMonth=date("m",$now);
			if($nowMonth>=$month){
				$cha=0;
			}else{
				$cha=1;
			}

			$birthdayYear=date("Y",strtotime($info['birthday']));
			$age=$nowYear-$birthdayYear-$cha;

			DI()->notorm->users->where("id=?",$info['id'])->update(array("age"=>$age));

			if($info['user_status']=='0'){
				return 1002;					
			}
			unset($info['user_status']);
			
		
			
			$info['avatar']=get_upload_path($info['avatar']);
			$info['avatar_thumb']=get_upload_path($info['avatar_thumb']);
			// $info['isreg']='0';
			$info['hometown']=$info['province'].$info['city'].$info['area'];

		}
		
		//查询是否签到过该用户
		/*$today = strtotime(date("Y-m-d"),time());
		
		$res=DI()->notorm->users_weight_info->where('uid=? and addtime=?',$info['id'],$today)->fetchOne();
		
		if(!$res || $res['signin']==0){
			$info['isreg']='1';
		}*/

		$token=md5(md5($info['id'].$user_login.time()));
		$info['token']=$token;
		$this->updateToken($info['id'],$token);
		

		$cache=array("token_".$info['id'],"userinfo_".$info['id']);
		delcache($cache);

        return $info;
    }
	
	//获取虚拟币地址
	public function getaddress(){
		$data=array('token_address'=>'','token_privatekey'=>'');
		$configpri=getConfigPri();	
		$withdraw_gateway = $configpri['withdraw_gateway'];
		$addrApi=$withdraw_gateway.'getAddress'; 
		$rs = get_arr($addrApi);
		  
		if(!empty($rs['code']))
		{
			//$this->error('获取失败,错误码:'.$rs['code'].",错误信息:".$rs['msg']);
			return  $data;
			exit;
		}else if(!empty($rs['data']['code'])){
			//$this->error('获取失败,错误码:'.$rs['data']['code'].",错误信息:".$rs['data']['msg']);
			return  $data;
			exit;
		}	
		$data['token_address'] =  '0x'.$rs['data']['data']['address'];	
		$privateKey = encrypt_decrypt('encrypt',$rs['data']['data']['privateKey']);
		$data['token_privatekey'] = $privateKey  ;	
		return  $data;
	}
		
}
