<?php
namespace Admin\Controller;
use Common\Controller\AdminbaseController;

class RechargeController extends AdminbaseController{
	
    var $status=array(
        '1'=>'已完成',
        '2'=>'已失败'
    );
	
	public function index()
	{
		$keyword = I('keyword');
		$map = array();
		if ($keyword != '') {
			$where = array('uid'=>$keyword);
			$where['uid']  = array('like', "%{$keyword}%");
			$where['address']  = array('like',"%{$keyword}%");
			$where['txhash']  = array('like',"%{$keyword}%");
			$where['_logic'] = 'or';
			$map['_complex'] = $where;
		}
		$map['type']  = 1;
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
            $list[$k]['status'] = $this->status[$v['status']];
		}		
		$this->assign('keyword',$keyword);
		$this->assign("page", $page->show('Admin'));
		$this->assign('list',$list);
		$this->display();
	}
	 
	 
	public function edit_post()
	{
		
	}
	
}