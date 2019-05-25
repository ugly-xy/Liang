<?php

/**
 * 客服列表
 */
namespace Admin\Controller;
use Common\Controller\AdminbaseController;
class ContactController extends AdminbaseController {

    function index(){	
    	$contact=M("contact");
    	$count=$contact->count();
    	$page = $this->page($count, 20);
    	$lists = $contact
			->order("orderno desc")
			->limit($page->firstRow . ',' . $page->listRows)
			->select();
    	$this->assign('lists', $lists);
    	$this->assign("page", $page->show('Admin'));
    	
    	$this->display();
    }
		
	function del(){
		$id=intval($_GET['id']);
		if($id){
			$result=M("contact")->delete($id);				
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
    //排序
    public function listorders() { 
		
        $ids = $_POST['listorders'];
        foreach ($ids as $key => $r) {
            $data['orderno'] = $r;
            M("contact")->where(array('id' => $key))->save($data);
        }
				
        $status = true;
        if ($status) {
            $action="更新坐骑排序";
            setAdminLog($action);
            $this->success("排序更新成功！");
        } else {
            $this->error("排序更新失败！");
        }
    }	
    

	function add(){
		$this->display();				
	}	
	function add_post(){
		if(IS_POST){
			
			
			
			$number=$_POST['number'];
			if($number==""){
				$this->error("请填写联系方式");
			}
			
			$icon=$_POST['icon'];
			if($icon==""){
				$this->error("请上传图标");
			}
			
			$contact=M("contact");
			 $contact->create();
			 $result=$contact->add(); 
			 if($result!==false){
				  $this->success('添加成功');
			 }else{
				  $this->error('添加失败');
			 }
		}			
	}		
	function edit(){
		$id=intval($_GET['id']);
		if($id){
			$contact=M("contact")->find($id);
			$this->assign('contact', $contact);		
		}else{				
			$this->error('数据传入失败！');
		}								  
		$this->display();				
	}
	
	function edit_post(){
		if(IS_POST){	
			$contact=M("contact");
			$contact->create();
			$result=$contact->save(); 
			if($result!==false){
				$this->success('修改成功');
			}else{
				$this->error('修改失败');
			}
		}			
	}
}
