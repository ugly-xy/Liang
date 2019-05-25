<?php

/**
 * 资金管理
 */
namespace Admin\Controller;
use Common\Controller\AdminbaseController;
class PercentageController extends AdminbaseController {
	
	/*抽水记录*/
    public function index(){
    	$gift_model=M("gift_pumping");
    	$count=$gift_model->count();
    	$page = $this->page($count, 20);
    	$lists = $gift_model
			->order("addtime desc")
			->limit($page->firstRow . ',' . $page->listRows)
			->select();
		foreach($lists as $k=>$v){
			$lists[$k]['userinfo']=getUserInfo($v['uid']);
			$lists[$k]['liveinfo']=getUserInfo($v['liveuid']);
			$gift=M('gift')->where("id={$v['giftid']}")->find();
			if(!$gift){
				$gift=array(
					'giftname'=>'该礼物已被删除',
				);
			}
			$lists[$k]['giftinfo']=$gift;
		}
    	$this->assign('lists', $lists);
    	$this->assign("page", $page->show('Admin'));    	
    	$this->display();
    }
	
	/*销毁记录*/
	public function lists(){
		
    	$destruction=M("money_destruction");
    	$count=$destruction->count();
    	$page = $this->page($count, 20);
    	$lists = $destruction
			->order("addtime desc")
			->limit($page->firstRow . ',' . $page->listRows)
			->select();
		$config= M("config_private")->where("id='1'")->find();
    	$this->assign('config', $config);
    	$this->assign('lists', $lists);
    	$this->assign("page", $page->show('Admin'));    	
    	$this->display();
    }
    /*销毁记录添加*/
	public function add(){	
		$this->display();				
	}

	public function add_post(){		
		if(IS_POST){
			if($_POST['money']=='' || $_POST['money']==0){
				$this->error('请正确填写销毁金额!');
			}
			 $destruction=M("money_destruction");
			 $destruction->create();
			 $destruction->adminid=$_SESSION['ADMIN_ID'];
			 $destruction->admin=$_SESSION['name'];
			 $destruction->ip=$_SERVER["REMOTE_ADDR"];
			 $destruction->addtime=time();
			 $result=$destruction->add(); 
			 if($result){
				 M("config_private")->where("id='1'")->setDec('pooloffunds',$_POST['money']);
				$this->success('添加成功');
			 }else{
				$this->error('添加失败');
			 }
		}			
	}
	/*销毁记录修改*/
	public function edit(){
		$id=intval($_GET['id']);
		if($id){
			$gift=M("money_destruction")->find($id);
			$this->assign('gift', $gift);						
		}else{				
			$this->error('数据传入失败！');
		}								  
		$this->display();				
	}
	
	public function edit_post(){
		if(IS_POST){
			 $user=M("money_destruction");
			 $user->create();
			 $result=$user->save(); 
			 if($result!==false){
				setAdminLog($action);
				$this->resetcache();
				$this->success('修改成功');
			}else{
				$this->error('修改失败');
			}
		}
	}
	
	
	/*交易所列表*/
	public function exchange(){
		$Coinrecird=M("exchange");
		$list=$Coinrecird->order("addtime desc")->select();
		foreach($list as $k=>$v){
			$list[$k]['addtime']=date("Y-m-d H:i",$v['addtime']);
		}
		
		$this->assign("list",$list);
		$this->display();	
	}
	
	 /*交易所添加*/
	public function exchange_add(){
		$this->display();				
	}
	
	public function exchange_add_post(){
		if(IS_POST){
			if($_POST['title']=='' || $_POST['url']=='' || $_POST['introduce']=='' || $_POST['avatar']==''){
				$this->error('请认真填写,不能为空!');
			}
			 $destruction=M("exchange");
			 $destruction->create();
			 $destruction->addtime=time();
			 $result=$destruction->add(); 
			 if($result){
				$this->success('添加成功');
			 }else{
				$this->error('添加失败');
			 }
		}			
	}
	
	/*销毁记录修改*/
	public function exchange_edit(){
		$id=intval($_GET['id']);
		if($id){
			$exchange=M("exchange")->find($id);
			$this->assign('exchange', $exchange);						
		}else{				
			$this->error('数据传入失败！');
		}								  
		$this->display();				
	}
	
	public function exchange_edit_post(){
		if(IS_POST){
			 $exchange=M("exchange");
			 $exchange->create();
			 $result=$exchange->save(); 
			 if($result!==false){
				  $this->success('修改成功');
			 }else{
				  $this->error('修改失败');
			 }
		}			
	}
	
	public function exchange_del(){
		$id=intval($_GET['id']);
		if($id){
			$info=M("exchange")->where("id={$id}")->find();
			$result=M("exchange")->delete($id);				
				if($result){
					$this->success('删除成功');
				}else{
					$this->error('删除失败');
				}			
		}else{				
			$this->error('数据传入失败！');
		}								  
		$this->display();				
	}	
	
	/*权重明细*/
	
	public function weight_list(){
		 if($_REQUEST['start_time']!=''){
            $map['addtime']=array("gt",strtotime($_REQUEST['start_time']));
            $_GET['start_time']=$_REQUEST['start_time'];
        }
         
        if($_REQUEST['end_time']!=''){
             
            $map['addtime']=array("lt",strtotime($_REQUEST['end_time']));
            $_GET['end_time']=$_REQUEST['end_time'];
        }
        if($_REQUEST['start_time']!='' && $_REQUEST['end_time']!='' ){
             
            $map['addtime']=array("between",array(strtotime($_REQUEST['start_time']),strtotime($_REQUEST['end_time'])));
            $_GET['start_time']=$_REQUEST['start_time'];
            $_GET['end_time']=$_REQUEST['end_time'];
        }

		 if($_REQUEST['keyword']!=''){
			$map['id']	=array("like","%".$_REQUEST['keyword']."%");			
			$_GET['keyword']=$_REQUEST['keyword'];
		 }
		$total_weight=M("platform_total_weight");
    	$count=$total_weight->where($map)->count();
    	$page = $this->page($count, 20);
    	$lists = $total_weight
			->where($map)
			->order("addtime desc")
			->limit($page->firstRow . ',' . $page->listRows)
			->select();
    	$this->assign('formget', $_GET);
    	$this->assign('lists', $lists);
    	$this->assign("page", $page->show('Admin'));    	
    	$this->display();
	}
	
	public function weight_user(){
		if($_REQUEST['start_time']!=''){
            $map['addtime']=array("gt",strtotime($_REQUEST['start_time']));
            $_GET['start_time']=$_REQUEST['start_time'];
        }
         
        if($_REQUEST['end_time']!=''){
             
            $map['addtime']=array("lt",strtotime($_REQUEST['end_time']));
            $_GET['end_time']=$_REQUEST['end_time'];
        }
        if($_REQUEST['start_time']!='' && $_REQUEST['end_time']!='' ){
             
            $map['addtime']=array("between",array(strtotime($_REQUEST['start_time']),strtotime($_REQUEST['end_time'])));
            $_GET['start_time']=$_REQUEST['start_time'];
            $_GET['end_time']=$_REQUEST['end_time'];
        }

        if($_REQUEST['keyword']!=''){
            $map['uid|id']=array("like","%".$_REQUEST['keyword']."%"); 
            $_GET['keyword']=$_REQUEST['keyword'];
        }
		$weight_info=M("users_weight_info");
    	$count=$weight_info->where($map)->count();
    	$page = $this->page($count, 20);
    	$lists = $weight_info
			->where($map)
			->order("addtime desc")
			->limit($page->firstRow . ',' . $page->listRows)
			->select();
		foreach($lists as $k=>$v){
			$lists[$k]['weight']=M('users_total_weight')->where("id={$v['weight_id']}")->find();
			$lists[$k]['userinfo']=getUserInfo($v['uid']);
		}
    	$this->assign('lists', $lists);
    	$this->assign("page", $page->show('Admin'));    	
    	$this->display();
	}
	
}
