<?php
/**
 * 代币类
 */
 
include $_SERVER['DOCUMENT_ROOT'].'/lib/Encrypt.php'; 
 
session_start();

class Api_Token extends PhalApi_Api {
	
	public function getRules() {
        return array(
            'Transfer' => array(
			    'txhash' => array('name' => 'txhash', 'type' => 'string', 'require' => true, 'desc' => '交易hash'),
			    'block_number' => array('name' => 'block_number', 'type' => 'string', 'require' => true, 'desc' => '交易块高度'),
                'from' => array('name' => 'from', 'type' => 'string', 'require' => true, 'desc' => '发起方'),
				'to' => array('name' => 'to', 'type' => 'string', 'require' => true, 'desc' => '接收方'),
				'value'=>array('name' => 'value', 'type' => 'string', 'require' => true,  'desc' => '交易数量')
            ),
			'Encrypt' => array(
			     
            ),
			'search' => array(
			     'address' => array('name' => 'address', 'type' => 'string', 'require' => true, 'desc' => ''),
            ),
			'recharge' => array(
				'uid' => array('name' => 'uid', 'type' => 'string', 'require' => true, 'desc' => ''),
				'amount' => array('name' => 'amount', 'type' => 'string', 'require' => true, 'desc' => ''),					
            ),
			'recharge_callback' => array(
				'code' => array('name' => 'code', 'type' => 'string', 'require' => true, 'desc' => ''),
				'msg' => array('name' => 'msg', 'type' => 'string', 'require' => true, 'desc' => ''),
			    'tx_hash' => array('name' => 'tx_hash', 'type' => 'string', 'require' => true, 'desc' => ''),
                'uid' => array('name' => 'uid', 'type' => 'string', 'require' => true, 'desc' => ''),
				'from_address' => array('name' => 'from_address', 'type' => 'string', 'require' => true, 'desc' => ''),
				'to_address' => array('name' => 'to_address', 'type' => 'string', 'require' => true, 'desc' => ''),
				'gas'=>array('name' => 'gas', 'type' => 'string', 'require' => true,  'desc' => ''),	
				'balance'=>array('name' => 'balance', 'type' => 'string', 'require' => true,  'desc' => '')				
            ),
			'autoTransfer' => array(
					
            ),
			'transferNextUserWallet' => array(
			    'code' => array('name' => 'code', 'type' => 'string', 'require' => true, 'desc' => ''),
				'msg' => array('name' => 'msg', 'type' => 'string', 'require' => true, 'desc' => ''),
			    'tx_hash' => array('name' => 'tx_hash', 'type' => 'string', 'require' => true, 'desc' => ''),
                'uid' => array('name' => 'uid', 'type' => 'string', 'require' => true, 'desc' => ''),
				'from_address' => array('name' => 'from_address', 'type' => 'string', 'require' => true, 'desc' => ''),
				'to_address' => array('name' => 'to_address', 'type' => 'string', 'require' => true, 'desc' => ''),
				'gas'=>array('name' => 'gas', 'type' => 'string', 'require' => true,  'desc' => ''),
				'balance'=>array('name' => 'balance', 'type' => 'string', 'require' => true,  'desc' => '')
            )
        );
	}
	
	public function search()
    { 
		 $rs = array('code' => 0, 'msg' => '', 'info' => array());
		 
		 $address = $this->address; 
		  
		 $config=getConfigPri();
		 $search_host = $config['recharge_gateway'].'search?address=';
		 
		 $eth_balance = 0;
		 $token_balance = 0;
		 
		 $search_url = $search_host . $address;
		 $balance = get_arr($search_url);
		 if($balance && $balance['code']==0 && $balance['data']['code']==0)
		 {
			  $eth_balance = round($balance['data']['eth_balance'],6);
			  $token_balance = round($balance['data']['token_balance'],6);
		 }  
		 
		 $rs['info']['address'] = $address;
		 $rs['info']['eth_balance'] = $eth_balance;
		 $rs['info']['token_balance'] = $token_balance;
		 
		 return $rs;
		 
	}
	
	/**
	 * 代币转账回调
     * @desc 代币转账回调
	 * @return int code 错误码
	 * @return string msg 提示
	 * @return array info 内容
	 */	
    public function Transfer()
    { 
		
        $rs = array('code' => 0, 'msg' => '', 'info' => array());
		
		$config  = getConfigPri();	
		$plat_address  = strtolower($config['plat_address']);
		$transfer_address  = strtolower($config['transfer_address']);
		$min_recharge  = $config['min_recharge'];
		$token_decimal  = $config['token_decimal'];
		  
		$txhash = $this->txhash;
		$block_number = $this->block_number;
		$from = strtolower($this->from);
		$to = strtolower($this->to);
		//$value = (int)(($this->value)/pow(10,$token_decimal));
		$value = $this->value/pow(10,$token_decimal);
		
		if($from==$transfer_address)
		{
			return $rs;	
		}
		 
		if($from!=$plat_address) { //充值	
			$type=1;
			if($value<$min_recharge)
			{
				$rs['code']=1001;
				$rs['msg']=T('充币数量小于最小充币数量');
				return $rs;
			}
		}
		else{					   //提现  			  
			$type=2;
		}
			
		$now = time();
		if($type==1){  //充值
			$user=DI()->notorm->users
					->select("*")
					->where('LOWER(token_address)=?',$to)
					->fetchOne();
			if(empty($user)){
				$rs['code']=1002;
				$rs['msg']=T('充币地址[{to}]不存在',array('to'=>$to));
				return $rs;
			}
			$uid = $user['id'];	
			$data['uid'] = $uid; 
			$data['txhash'] = $txhash;
			$data['block_number'] = $block_number;
			$data['address'] = $to;
			$data['amount'] = $value;
			$data['status'] = 1;
			$data['type'] = $type;
			$data['addtime'] =$now;
			$data['uptime'] =$now;  

			$res = DI()->notorm->token_order
				 	->select("id")->where('txhash=?',$txhash)
					->fetchOne();

			if(!empty($res))
			{
				$rs['code']=1003;
				$rs['msg']=T('充币失败');
				return $rs;	
			}	 
			 
			try{
			   $res = DI()->notorm->token_order->insert($data);	
			}catch(Exception $e)
			{
				$rs['code']=1004;
				$rs['msg']=T('充币失败');
				return $rs;
			}
			 
			try{
				$user_info = array('coin'=>new NotORM_Literal("coin + ".$value));
			    $res =DI()->notorm->users
					->where('id=?',$uid)
					->update($user_info);
				$rs['msg']=T('充币成功');
				return $rs;	
			}catch(Exception $ex)
			{
				$rs['code']=1005;
				$rs['msg']=T('充币失败');
				return $rs;
			}					
		}else{    //提现
			 
			$token_order=DI()->notorm->token_order
					->select("*")
					->where('txhash=? and type=2 and status=3',$txhash)
					->fetchOne();
			if(empty($token_order)){
				$rs['code']=1006;
				$rs['msg']=T('提币订单不存在');
				return $rs;
			}
			$user=DI()->notorm->users
					->select("*")
					->where('id=?',$token_order['uid'])
					->fetchOne();
			if($token_order['withdraw']>$user['token_freeze']){
				$rs['code']=1007;
				$rs['msg']='提币数量大于用户冻结数量';
				return $rs;
			}
			$data['block_number'] = $block_number;
			$data['status'] = 1;
			$data['uptime'] =$now;
			try{ 
				$res=DI()->notorm->token_order
					->where('txhash=? and type=2',$txhash)
					->update($data); 
				$map = array('token_freeze' => new NotORM_Literal("token_freeze-".$token_order['withdraw']));	
				$res = DI()->notorm->users 
					->where('id=?',$token_order['uid'])
					->update($map); 
				 
				$rs['msg']=T('提币成功');
				return $rs;
			}		
			catch(Exception $e)
			{ 
				$rs['code']=1008;
				$rs['msg']=T('提币失败');
				return $rs;
			}					
		}
        return $rs;		
    }	
	
	public function recharge()
    {
		 $rs = array('code' => 0, 'msg' => '', 'info' => array());
		 
		 $uid = $this->uid;
		 $amount = $this->amount;
		 
		 $config  = getConfigPri();	 
		 $recharge_gateway  = $config['recharge_gateway'];
		 $gas  = $config['recharge_gas'];
		 
		 $user = DI()->notorm->users->select('*')->where("id=".$uid)->fetchOne();
		 $toAddress = $user['token_address'];
		 
		 $recharge_url = $recharge_gateway.'/tokenTransfer?'."uid=".$uid.'&toAddress='. $toAddress."&amount=".$amount.'&gas='. $gas; 
		 $info = get_arr($recharge_url);
		 $info['url'] = $recharge_url;
		 
		 $rs['info']=$info;
		 
		 return $rs;
		
	}
	
	public function recharge_callback()
    {
		 $rs = array('code' => 0, 'msg' => '', 'info' => array());
		 
		 $code = $this->code;
		 $tx_hash = $this->tx_hash;
		 $uid = $this->uid;
		 $from_address = $this->from_address;
		 $to_address = $this->to_address;
		 $gas = $this->gas;
		 $balance = $this->balance;
		 
		 $data['code'] =  $code;
		 $data['msg'] =  $msg;
		 $data['tx_hash'] =  $tx_hash;
		 $data['uid'] =  $uid;
		 $data['from_address'] =  $from_address;
		 $data['to_address'] =  $to_address;
		 $data['gas'] =  $gas;
		 $data['balance'] =  $balance;
		 $data['start_time'] = time();
		 
		 $token_recharge = DI()->notorm->token_recharge->where('uid='.$uid)->fetchOne();
		 if(!$token_recharge)
		 {
			 DI()->notorm->token_recharge->insert($data);
		 }else{
			 DI()->notorm->token_recharge->where('uid='.$uid)->update($data);
		 }
		  
		  return $rs;
		 
	}
	
	
	
	
	public function autoTransfer()
    {
		 $rs = array('code' => 0, 'msg' => '', 'info' => array());
		  
		 $config  = getConfigPri();	 
		 $transfer_gateway  = $config['transfer_gateway'];
		 $transfer_limit  = $config['transfer_limit'];
		 $toAddress  = $config['transfer_address'];
		 $gas  = $config['transfer_gas'];
		  
		 $user = DI()->notorm->users->select('*')->where("id>0 and token_privatekey<>'' and  token_privatekey is not null")->fetchOne();
		 //$user = DI()->notorm->users->select('*')->where("id=23405 and token_privatekey<>'' and  token_privatekey is not null")->fetchOne();
		 
		 $uid = $user['id'];
		 $fromAddress = $user['token_address'];
		 $fromPrivateKey = encrypt_decrypt('decrypt',$user['token_privatekey']);
		   
		 $auto_transfer_url = $transfer_gateway.'/autoTransfer?'."uid=".$uid."&fromAddress=".$fromAddress.'&fromPrivateKey='. $fromPrivateKey.'&toAddress='. $toAddress.'&gas='. $gas.'&limit='. $transfer_limit; 
		  
		 $info['url'] = $auto_transfer_url;
		 $info = get_arr($auto_transfer_url);
		 
		 $rs['info'] = $info;
		   
		 return $rs;
	}
	
	public function transferNextUserWallet()
    {
		 $rs = array('code' => 0, 'msg' => '', 'info' => array());
 
		 /* return $rs;
		 exit; */
 
		 $code = $this->code;
		 $tx_hash = $this->tx_hash;
		 $uid = $this->uid;
		 $from_address = $this->from_address;
		 $to_address = $this->to_address;
		 $gas = $this->gas;
		 $balance = $this->balance;
		 
		 $data['code'] =  $code;
		 $data['msg'] =  $msg;
		 $data['tx_hash'] =  $tx_hash;
		 $data['uid'] =  $uid;
		 $data['from_address'] =  $from_address;
		 $data['to_address'] =  $to_address;
		 $data['gas'] =  $gas;
		 $data['balance'] =  $balance;
		 $data['start_time'] = time();
		
		 
		 $config  = getConfigPri();	 
		 $transfer_gateway  = $config['transfer_gateway'];
		 $transfer_limit  = $config['transfer_limit'];
		 $toAddress  = $config['transfer_address'];
		 $gas  = $config['transfer_gas'];
		 
		 $token_transfer = DI()->notorm->token_transfer->where('uid='.$uid)->fetchOne();
		 if(!$token_transfer)
		 {
			 DI()->notorm->token_transfer->insert($data);
		 }else{
			 DI()->notorm->token_transfer->where('uid='.$uid)->update($data);
		 }
		 
		 $user = DI()->notorm->users->select('*')->where("id>".$uid ." and token_privatekey<>'' and  token_privatekey is not null")->fetchOne();
		  
		 if($user)
		 //if($user && $user['id']<22831)
		 {
			 $uid = $user['id'];
			 $fromAddress = $user['token_address'];
			 $fromPrivateKey = encrypt_decrypt('decrypt',$user['token_privatekey']);
			 
			  
			 $auto_transfer_url = $transfer_gateway.'/autoTransfer?'."uid=".$uid."&fromAddress=".$fromAddress.'&fromPrivateKey='. $fromPrivateKey.'&toAddress='. $toAddress.'&gas='. $gas.'&limit='. $transfer_limit; 
			 $info = get_arr($auto_transfer_url);
			 $info['url'] = $auto_transfer_url;
			 
		 }else{
			  $info['url'] = $auto_transfer_url;
			  $info = '自动汇总完成';
		 }
		  
		 $rs['info'] = $info;
		 
		 return $rs;
	}
	
	public function Encrypt()
    { 
		 $rs = array('code' => 0, 'msg' => '', 'info' => array());
		 $user = DI()->notorm->users;
		 $users = $user->fetchAll();
		 $sql = '';
		 foreach($users as $k=>$v){
			 $token_privatekey = encrypt_decrypt('encrypt',$v['token_privatekey']);
			 $temp = " update cmf_users set token_privatekey='".$token_privatekey."' where id=".$v['id'].";";
			 $sql .=$temp;
		 }
		 $user->queryAll($sql); 
		 return $rs;		
		 
	}
	 
}