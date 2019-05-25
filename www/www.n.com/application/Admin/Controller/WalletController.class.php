<?php
namespace Admin\Controller;
use Common\Controller\AdminbaseController;

class WalletController extends AdminbaseController{
	public function index()
	{
		$keyword = I('keyword');
		$map = array();
		if ($keyword != '') {
			$where = array('uid'=>$keyword);
			$where['uid']  = array('like', "%{$keyword}%");
			$where['address']  = array('like',"%{$keyword}%");
			$where['_logic'] = 'or';
			$map['_complex'] = $where;
		}
		$count = M('token_address')->alias("a")
				->join(C('DB_PREFIX').'users b ON a.uid=b.id','LEFT')
				->where($map)->count();	
		$page = $this->page($count, 10);
		$list = M('token_address')->alias("a")
				->join(C('DB_PREFIX').'users b ON a.uid=b.id','LEFT')
				->where($map)
				->limit($page->firstRow . ',' . $page->listRows)
				->field('a.*,b.user_nicename')
				->order('a.addtime desc')
                ->select();	
		foreach ($list as $k => $v) {
			$list[$k]['user_name'] = $v['user_nicename'] . '(' .$v['uid'] .')'; 
		}		
		$this->assign('keyword',$keyword);
		$this->assign("page", $page->show('Admin'));
		$this->assign('list',$list);
		$this->display();
	}
	
}