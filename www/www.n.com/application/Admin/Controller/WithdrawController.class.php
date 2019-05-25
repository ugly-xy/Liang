<?php
namespace Admin\Controller;
use Common\Controller\AdminbaseController;

class WithdrawController extends AdminbaseController{
    
    var $status=array(
        '0'=>'未处理',
        '1'=>'已完成',
        '2'=>'已取消',
		'3'=>'提现中'
    );
	
	public function index()
	{
		$keyword = I('keyword');
		//$token_name = getConfigPri()['token_name'];
		$token_name = getConfigPub()['name_coin'];
		$map = array();
		if ($keyword != '') {
			$where = array('uid'=>$keyword);
			$where['uid']  = array('like', "%{$keyword}%");
			$where['address']  = array('like',"%{$keyword}%");
			$where['txhash']  = array('like',"%{$keyword}%");
			$where['_logic'] = 'or';
			$map['_complex'] = $where;
		}
		$map['type']  = 2;
		$count = M('token_order')->alias("a")
				->join(C('DB_PREFIX').'users b ON a.uid=b.id','LEFT')
				->where($map)->count();	
		$page = $this->page($count, 10);
		$list = M('token_order')->alias("a")
				->join(C('DB_PREFIX').'users b ON a.uid=b.id','LEFT')
				->where($map)
				->limit($page->firstRow . ',' . $page->listRows)
				->field( 'a.*,b.user_nicename')
				->order('a.addtime desc')
                ->select();
		foreach ($list as $k => $v) {
			$list[$k]['user_name'] = $v['user_nicename'] . '(' .$v['uid'] .')'; 
			$list[$k]['is_auto'] = $v['is_auto']==1?"自动":"人工"; 
			$list[$k]['is_fast'] = $v['is_fast']==1?"是":"否"; 
            $list[$k]['status'] = $this->status[$v['status']];
		}		
		$this->assign('keyword',$keyword);
		$this->assign('token_name',$token_name);
		$this->assign("page", $page->show('Admin'));
		$this->assign('list',$list);
		$this->display();
	}
	
	 
	public function edit()
	{
		$config = getConfigPri();
		//$token_name = $config['token_name'];
		$token_name = getConfigPub()['name_coin'];
		$ethscan_gateway = $config['ethscan_gateway'];
		$map['a.id'] = I('id');
		$data = M('token_order')->alias("a")
				->join(C('DB_PREFIX').'users b ON a.uid=b.id','LEFT')
				->where($map)
				->limit($page->firstRow . ',' . $page->listRows)
				->field( 'a.*,b.user_nicename')
				->order('a.addtime desc')
                ->find(); 
		$data['user_name']=	$data['user_nicename'] . '(' . $data['uid'] .')'; 
		$data['addtime'] = date("Y-m-d H:i",$data['addtime']);
		$data['ethscan_tx_url'] = $ethscan_gateway.$data['txhash'];
		$ethscan_gateway = str_replace("tx","address",$ethscan_gateway);
		$data['ethscan_plant_url'] = $ethscan_gateway.$config['plat_address'];
		$ethscan_gateway = str_replace("address","token",$ethscan_gateway);
		$data['ethscan_token_url'] = $ethscan_gateway.'0x36d10c6800d569bb8c4fe284a05ffe3b752f972c?a='.$config['plat_address'];
		$this->assign('data',$data);
		$this->assign('token_name',$token_name);
		$this->display(); 		
	}
	 
		
	public function edit_post()
	{
	    $id=I('id');
		$uid=I('uid');
		$withdraw  = I('withdraw');
		$amount  = I('amount');
		$address  = I('address');
		$status = I('status');
		$is_auto = I('is_auto');
		$pay_channel = I('pay_channel');

		$config = getConfigPri();
		$plat_address = $config['plat_address'];
		$token_decimal = $config['token_decimal'];
		$gas_price = $config['gas_price'];
		$gas_fast_price = $config['gas_fast_price'];
  		
		$withdraw_gateway = "";

		if($pay_channel==0)
		{
			$withdraw_gateway = $config['withdraw_gateway'];
		}else if($pay_channel==1){
			$withdraw_gateway = $config['withdraw_gateway1'];
		}else if($pay_channel==2){
			$withdraw_gateway = $config['withdraw_gateway2'];
		}
 
		$data['pay_channel']  = $pay_channel;  
		$data['is_auto']  = $is_auto; //是否自动 1：自动 2：人工
		$data['uptime']  = time(); 
		
		if($is_fast==1)
		{
			$gas_price =  $gas_price  + $gas_fast_price;
		}	
		if($is_auto==1) //自动
		{
			$status =3;
		} 
		$data['status']  = $status;
		
		if($status==1)
		{ 
			//通过，仅仅修改状态
			M('token_order')->where("id ={$id}")->save($data);	
			$this->success('修改成功');
		}else if($status==2){
			//不通过，退回扣除的币种，更新账户资产 (区块失败)
			$user_info['coin']= array('exp','coin+'.$withdraw);
		    $user_info['token_freeze']= array('exp','token_freeze-'.$withdraw);
		    M("users")->where("id={$uid}")->setField($user_info); 
			M('token_order')->where("id ={$id}")->save($data);		
			$this->success('修改成功');  			
		}else if($status==3){
			 
			$res = M('token_order')->where("id={$id} and status=3")->find();  
			if(!empty($res))
			{
				$this->error('账户提现中，请等待交易结束');
			}
			$withdrawApi=$withdraw_gateway.'tokenTransfer?'."fromAddress=".$plat_address."&toAddress=".$address.'&amount='. $amount.'&decimal='. $token_decimal."&gas=".$gas_price; 
			
			// $this->error($withdrawApi);
			 
			$rs = get_arr($withdrawApi);
			  
			if(!empty($rs['code']))
			{
				$this->error('修改失败,错误码:'.$rs['code'].",错误信息:".$rs['msg']);
			}	
			if(!empty($rs['data']['code']))
			{
				$this->error('修改失败,错误码:'.$rs['data']['code'].",错误信息:".$rs['data']['msg']);
			}	
			if(empty($rs['data']['hash']))
			{
				$this->error('修改失败,无法获取交易订单号');
			}


			$data['txhash'] =  $rs['data']['hash'];	
			M('token_order')->where("id={$id}")->save($data);
			$this->success('修改成功');  
		} 
	}
	
	public function auto_transfer()
	{
		$host = $_SERVER["REQUEST_SCHEME"].'://'.$_SERVER["SERVER_NAME"];
		$this->assign('host',$host);
		$this->display();
	}
	 
}