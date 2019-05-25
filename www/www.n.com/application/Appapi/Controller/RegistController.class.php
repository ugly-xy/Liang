<?php
/*
 *分享页注册
 * 
 * */
namespace Appapi\Controller;
use Common\Controller\HomebaseController;

class RegistController extends HomebaseController {
	public function index(){
		$code=I("code");
		$this->assign("code",$code); //uid
		$configpub=getConfigPub();//公共配置
		$this->assign("configpub",$configpub); //uid
		$this->display();
	}
	/*获取验证码*/	
	public function getcode(){
			$num =I('mobile');
			$user=M("users");//判断号码是否存在
			if(!preg_match("/^0[1]\d{9}$/",$num)){
			// if(!$num)){
				$this->ajaxReturn(array('type'=>0,'msg'=>LangT('请输入正确的手机号!')));
				return;
			}
			$info=$user->where("user_login='{$num}' or mobile='{$num}'")->getField("id");
			if($info!=''){
				$this->ajaxReturn(array('type'=>0,'msg'=>LangT('手机号已存在!')));
				return;
			}/*
			if($_SESSION['reg_mobile']==$num && $_SESSION['reg_expiretime']>time() ){
				$this->ajaxReturn(array('type'=>1002,'msg'=>LangT('验证码5分钟有效，请勿多次发送')));
			}*/
			if($num!=""){
				
				//读取后台配置信息
				$getConfigPri=getConfigPri();
				if($getConfigPri['sendcode_switch']==1){
					$length = 6;
					$min = pow(10 , ($length - 1));
					$max = pow(10, $length) - 1;
					$code =  rand($min, $max);
					$rs=sendCode($num,$code);
					if ($rs['code']==0){
						$_SESSION['mobile_code'] = $code ;
						$_SESSION['reg_mobile'] = $num;
						$_SESSION['reg_expiretime'] = time() +60*5;
						$this->ajaxReturn(array('type'=>1));					
					}elseif($rs['code'] == 1002){
						$this->ajaxReturn(array('type'=>0,'msg'=>LangT('验证码已发送')));
					}
				}else{
						$_SESSION['mobile_code']='哈哈哈';
						$_SESSION['reg_mobile']=$num;
						$_SESSION['reg_expiretime']=time() +60*5;
						$this->ajaxReturn(array('type'=>2));
				}
			}else{
				$this->ajaxReturn(array('type'=>0,'msg'=>LangT('验证码发送失败')));
			}
        }
	function regist_post(){
		$user_login=I("phone");
		$phoneCode=I("phoneCode");
		$pass=I("firstPass");
		$code=I("code");
		$leng=strlen($pass);
		
		if($phoneCode!=$_SESSION['mobile_code']){	
			$this->ajaxReturn(array('msg'=>LangT('验证码错误'),'type'=>0));
		}
		if(!(preg_match("/[A-Za-z]/",$pass)&& preg_match("/\d/",$pass)) || $leng<8 || $leng>20){
			$this->ajaxReturn(array('msg'=>LangT('密码必须由 8-20位字母、数字组成'),'type'=>0));
		}
		$user=M("users");//判断号码是否存在
		$info=$user->where("user_login='{$user_login}' or mobile='{$user_login}'")->getField("id");
		if($info){
			$this->ajaxReturn(array('msg'=>LangT('手机号已存在!')));
			return;
		}
		if($code==''){
			$this->ajaxReturn(array('msg'=>LangT('邀请码不能为空!')));
			return;
		}
		$configpri=getConfigPri();
		/*加密方式跟接口相同*/
		$user_pass=sp_password($pass);
		
		//获取钱包地址
		$getaddress=getaddress();
		
		$now=time();
		$nowYear=date("Y",$now);
		$birthdayYear=2000;
		
	    /*加密方式跟接口相同*/
	    $data=array(
			'user_login' => $user_login,
			'mobile' =>$user_login,
			'user_nicename' =>LangT('手机用户').substr($user_login,-4),
			'user_pass' =>$user_pass,
			'signature' =>LangT('这家伙很懒，什么都没留下'),
			'avatar' =>'/default.jpg',
			'avatar_thumb' =>'/default_thumb.jpg',
			'last_login_ip' =>$_SERVER['REMOTE_ADDR'],
			'create_time' => date("Y-m-d H:i:s"),
			'user_status' => 1,
			"user_type"=>2,//会员
			"coin"=>$configpri['reg_reward'],
			"age"=>$nowYear-$birthdayYear,
			"mobile"=>$user_login,
			"birthday"=>'2000-01-01',
			"token_address"=>$getaddress['token_address'],
			"token_privatekey"=>$getaddress['token_privatekey'],
		);
		$result=$user->add($data);//注册成功
		
		if($result){
			//生成邀请码
			$addcode=createCode();
			$code_info=array('uid'=>$result,'code'=>$addcode);
			$Agent_code=M('users_agent_code');
			$isexist=$Agent_code
					->field("uid")
					->where("uid = {$result}")
					->find();
			if($isexist){
				$Agent_code->where("uid = {$result}")->save($code_info);	
			}else{
				$Agent_code->add($code_info);
			}
			
			
			//邀请用户  奖励
			$isinvite=M('users_agent_code')
					->where("code ='{$code}'")
					->find();
					
			
			if($isinvite){
				$invite_info=array(
					'uid'=>$result,
					'touid'=>$isinvite['uid'],
					'uid_currency'=>$configpri['uid_currency'],
					'touid_currency'=>$configpri['touid_currency'],
					'addtime' => time(),
				);
				M('users_invitation')->add($invite_info);
				M('users')->where("id={$result}")->setInc("coin",$configpri['uid_currency']);
				M('users')->where("id={$isinvite['uid']}")->setInc("coin",$configpri['touid_currency']);
			}
			
			$data=array(
				"code"=>1,
				"msg"=>LangT("注册成功"),
			);
			
			echo json_encode($data);
			exit;
		}else{
			$data=array(
				"code"=>2,
				"msg"=>LangT("注册失败"),
			);
			echo json_encode($data);
			exit;
		}
	}
	
	
	
}