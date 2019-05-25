<?php
/**
 * 交易订单
 */
session_start();
require_once API_ROOT.'/Library/Eth/vendor/autoload.php';
use Murich\PhpCryptocurrencyAddressValidation\Validation\ETH;

class Api_Trans extends PhalApi_Api {
	
	public function getRules() {
        return array(
            'withdraw' => array(
			    'uid' => array('name' => 'uid', 'type' => 'string', 'require' => true, 'desc' => '用户Id'),
                'token' => array('name' => 'token', 'type' => 'string', 'require' => false, 'desc' => '用户token'),
				'address' => array('name' => 'address', 'type' => 'string', 'require' => true, 'desc' => '提币地址'),
				'withdraw' => array('name' => 'withdraw', 'type' => 'float', 'require' => true, 'desc' => '提币数量'),
				'is_fast' => array('name' => 'is_fast', 'type' => 'int', 'require' => true, 'desc' => '是否加速'),
				'remark' => array('name' => 'remark', 'type' => 'string', 'require' => false, 'desc' => '备注'),
            )
        );
	}
	/**
	 * 提币申请
     * @desc 代币转账回调
	 * @return int code 错误码
	 * @return string msg 提示
	 * @return array info 内容
	 */	
    public function withdraw()
    { 
        $rs = array('code' => 0, 'msg' => '', 'info' => array());
		
		$uid = $this->uid;
		$token = $this->token;
		$address = $this->address;
		$withdraw = $this->withdraw;
		$is_fast = $this->is_fast;
		$remark = $this->remark;
		
		$checkToken=checkToken($uid,$token);
		if($checkToken==700){
			$rs['code'] = $checkToken;
			$rs['msg'] = T('您的登陆状态失效，请重新登陆！');
			return $rs;
		} 
		 
		 
		$validator = new ETH($address);
		if(empty($validator->validate()))
		{
			$rs['code']=1001;
			$rs['msg']=T('请输入正确的提币地址');
			return $rs;
		}
	
		$configPub  = getConfigPub();	 

		$name_coin =  $configPub['name_coin'];
		  
		$config  = getConfigPri();	
		$min_withdraw  = $config['min_withdraw'];
		$min_withdraw_fee  = $config['min_withdraw_fee'];
		$minimum_fee_percentage = $config['minimum_fee_percentage'];
		$withdraw_fee_type = $config['withdraw_fee_type'];
		
		$fast_fee  = 0;
		if($is_fast==1)
		{
			$fast_fee  =  $config['fast_fee'];
		}
		
		// $extra_fee = $withdraw - $min_withdraw_fee;
		// if($extra_fee>0)
		// {
		// 	$extra_fee = ($withdraw - $min_withdraw_fee)*$minimum_fee_percentage/100;
		// 	$extra_fee = round($extra_fee,2); 
		// }else{
		// 	$extra_fee =  0;
		// }
		// $withdraw_fee = $min_withdraw_fee +  $extra_fee ;
		if($withdraw_fee_type==0)
		{
			$withdraw_fee = $min_withdraw_fee;
		}else{
			$withdraw_fee = $withdraw*$minimum_fee_percentage/100;
		}
		  
		$user=DI()->notorm->users
					->select("*")
					->where('id=? ',$uid)
					->fetchOne();	
		//判断账户余额 
		if($withdraw>$user['coin'])
		{
			$rs['code']=1002;
			$rs['msg']=T('账户余额不足');
			return $rs;
		}
		  
		$amount =  $withdraw - $withdraw_fee - $fast_fee;
	    //判断最小提现
		if($withdraw<$min_withdraw_fee)
		{
			$rs['code']=1003;
			$rs['msg']=T('最低提币数量为').$min_withdraw.$name_coin;
			return $rs;
		}
		
		//冻结余额
		$user_info = array('coin'=>new NotORM_Literal("coin - ".$withdraw),'token_freeze'=>new NotORM_Literal("token_freeze + ".$withdraw));
	 
		DI()->notorm->users->where('id=?',$uid)->update($user_info);	
		
		$now = time();
		$data['uid']=$uid;
		$data['address']=$address;
		$data['amount']=$amount;
		$data['withdraw_fee']=$withdraw_fee;
		$data['withdraw']=$withdraw;
		$data['is_fast']=$is_fast;
		$data['fast_fee']=$fast_fee;
		$data['is_auto']=1;
		$data['type']=2;
		$data['addtime']=$now;
		$data['uptime']=$now;
		$data['remark']=$remark;
		
		//var_dump($data);
		try{
			DI()->notorm->token_order->insert($data);	
		}catch(Exception $e){
			return;	
		}
		 
		$rs['msg']=T('提交成功');
		return $rs; 
		}
     	 
}