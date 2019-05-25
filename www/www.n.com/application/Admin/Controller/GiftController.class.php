<?php

/**
 * 礼物
 */
namespace Admin\Controller;
use Common\Controller\AdminbaseController;
class GiftController extends AdminbaseController {
    var $type=array("0"=>"普通礼物","1"=>"豪华礼物");
    var $mark=array("0"=>"普通","1"=>"热门","2"=>"守护");
    function index(){

        /* $gift_sort=M("gift_sort")->getField("id,sortname");
        $gift_sort[0]="默认分类";
        $this->assign('gift_sort', $gift_sort); */
			
    	$gift_model=M("gift");
    	$count=$gift_model->count();
    	$page = $this->page($count, 20);
    	$lists = $gift_model
    	//->where()
    	->order("orderno, addtime desc")
    	->limit($page->firstRow . ',' . $page->listRows)
    	->select();
    	$this->assign('lists', $lists);
    	$this->assign('type', $this->type);
    	$this->assign('mark', $this->mark);
    	$this->assign("page", $page->show('Admin'));
    	
    	$this->display();
    }
		
    function del(){
        $id=intval($_GET['id']);
        if($id){
            $result=M("gift")->delete($id);				
                if($result){
                    $action="删除礼物：{$id}";
                    setAdminLog($action);
                    $this->resetcache();
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
            M("gift")->where(array('id' => $key))->save($data);
        }
				
        $status = true;
        if ($status) {
            $action="更新礼物排序";
                    setAdminLog($action);
                    $this->resetcache();
            $this->success("排序更新成功！");
        } else {
            $this->error("排序更新失败！");
        }
    }	
    

		function add(){
            // $gift_sort=M("gift_sort")->getField("id,sortname");
            // $this->assign('gift_sort', $gift_sort);					
        
            $this->display();				
		}	
		function add_post(){
            if(IS_POST){			
                 $gift=M("gift");
                 $gift->create();
                 $gift->addtime=time();
                 $result=$gift->add(); 
                 if($result){
                    $action="添加礼物：{$result}";
                    setAdminLog($action);
                    $this->resetcache();
                    $this->success('添加成功');
                 }else{
                    $this->error('添加失败');
                 }
            }			
		}		
		function edit(){
            $id=intval($_GET['id']);
            if($id){
                $gift=M("gift")->find($id);
                $this->assign('gift', $gift);						
            }else{				
                $this->error('数据传入失败！');
            }								  
            $this->display();				
		}
		
		function edit_post(){
            if(IS_POST){			
                 $user=M("gift");
                 $user->create();
                 $result=$user->save(); 
                 if($result!==false){
                     $action="修改礼物：{$_POST['id']}";
                    setAdminLog($action);
                    $this->resetcache();
                      $this->success('修改成功');
                 }else{
                      $this->error('修改失败');
                 }
            }			
		}
        
    function resetcache(){
        $key='getGiftList';
        
		$rs=M('gift')
			->field("id,type,mark,giftname,needcoin,gifticon")
			->order("orderno asc,addtime desc")
			->select();
		foreach($rs as $k=>$v){
			$rs[$k]['gifticon']=get_upload_path($v['gifticon']);
		}	
        if($rs){
            setcaches($key,$rs);
        }   
        return 1;
    }
		
    function sort_index(){
	
    	$gift_sort=M("gift_sort");
    	$count=$gift_sort->count();
    	$page = $this->page($count, 20);
    	$lists = $gift_sort
    	//->where()
    	->order("orderno asc")
    	->limit($page->firstRow . ',' . $page->listRows)
    	->select();
    	$this->assign('lists', $lists);
    	$this->assign("page", $page->show('Admin'));
    	
    	$this->display();
    }		
		
		function sort_del(){
			 	$id=intval($_GET['id']);
					if($id){
						$result=M("gift_sort")->delete($id);				
							if($result){
                                $action="删除礼物分类：{$id}";
                    setAdminLog($action);
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
    public function sort_listorders() { 
		
        $ids = $_POST['listorders'];
        foreach ($ids as $key => $r) {
            $data['orderno'] = $r;
            M("gift_sort")->where(array('id' => $key))->save($data);
        }
				
        $status = true;
        if ($status) {
            $action="更新礼物分类排序";
                    setAdminLog($action);
            $this->success("排序更新成功！");
        } else {
            $this->error("排序更新失败！");
        }
    }				
    function sort_add(){
		    	
    	$this->display();
    }		
		function do_sort_add(){

				if(IS_POST){	
            if($_POST['sortname']==''){
							  $this->error('分类名称不能为空');
							
						}
				
					 $gift_sort=M("gift_sort");
					 $gift_sort->create();
					 $gift_sort->addtime=time();
					 
					 $result=$gift_sort->add(); 
					 if($result){
                         $action="添加礼物分类：{$result}";
                    setAdminLog($action);
						  $this->success('添加成功');
					 }else{
						  $this->error('添加失败');
					 }
				}				
    }		
    function sort_edit(){

			 	$id=intval($_GET['id']);
					if($id){
						$sort	=M("gift_sort")->find($id);
						$this->assign('sort', $sort);						
					}else{				
						$this->error('数据传入失败！');
					}								      	
    	$this->display();
    }			
		function do_sort_edit(){
				if(IS_POST){			
					 $gift_sort=M("gift_sort");
					 $gift_sort->create();
					 $result=$gift_sort->save(); 
					 if($result){
                         $action="编辑礼物分类：{$_POST['id']}";
                    setAdminLog($action);
						  $this->success('修改成功');
					 }else{
						  $this->error('修改失败');
					 }
				}	
    }
}
