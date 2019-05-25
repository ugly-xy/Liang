<?php if (!defined('THINK_PATH')) exit();?><!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<!-- Set render engine for 360 browser -->
	<meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- HTML5 shim for IE8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <![endif]-->

	<link href="/public/simpleboot/themes/<?php echo C('SP_ADMIN_STYLE');?>/theme.min.css" rel="stylesheet">
    <link href="/public/simpleboot/css/simplebootadmin.css" rel="stylesheet">
    <link href="/public/js/artDialog/skins/default.css" rel="stylesheet" />
    <link href="/public/simpleboot/font-awesome/4.7.0/css/font-awesome.min.css"  rel="stylesheet" type="text/css">
    <style>
		.length_3{width: 180px;}
		form .input-order{margin-bottom: 0px;padding:3px;width:40px;}
		.table-actions{margin-top: 5px; margin-bottom: 5px;padding:0px;}
		.table-list{margin-bottom: 0px;}
	</style>
	<!--[if IE 7]>
	<link rel="stylesheet" href="/public/simpleboot/font-awesome/4.4.0/css/font-awesome-ie7.min.css">
	<![endif]-->
<script type="text/javascript">
//全局变量
var GV = {
    DIMAUB: "/",
    JS_ROOT: "public/js/",
    TOKEN: ""
};
</script>
<!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="/public/js/jquery.js"></script>
    <script src="/public/js/wind.js"></script>
    <script src="/public/simpleboot/bootstrap/js/bootstrap.min.js"></script>
	
	<script src="/public/layer/layer.js"></script>
	
<?php if(APP_DEBUG): ?><style>
		#think_page_trace_open{
			z-index:9999;
		}
	</style><?php endif; ?>
<style>
.comment_lists_table{
	border: 0;
	width: 100%;
}
.comment_lists_table td{
	border:0;
}
</style>
</head>
<body>
	<div class="wrap">
		

		<form method="post" class="js-ajax-form">

			<?php if(!empty($lists)): ?><table class="table table-hover table-bordered">
				
				<tbody>
					
					<?php if(is_array($lists)): foreach($lists as $key=>$vo): ?><tr>
							<td align="center">
								<table class="table table-hover table-bordered comment_lists_table">
									<tbody>
										<tr>
											<td width="70%"><?php echo ($vo['content']); ?></td>
											<td width="30%"><?php echo ($vo['user_nicename']); ?>(<?php echo ($vo['uid']); ?>)</td>
										</tr>
										<tr>
											<td>评论时间 : <?php echo (date("Y-m-d H:i:s",$vo["addtime"])); ?></td>
											<td>获赞数:<?php echo ($vo['likes']); ?></td>
										</tr>
									</tbody>
								</table>
							</td>
							
						</tr><?php endforeach; endif; ?>
					
				</tbody>
			</table>

			<?php else: ?>
				<table style="border: none;width: 100%;">
					<tbody>
						<tr>
							<td style="border: none;height: 50px;line-height: 50px;text-align: center;">暂无评论数据~</td>
						</tr>
					</tbody>
				</table><?php endif; ?>
			<div class="pagination"><?php echo ($page); ?></div>
		</form>
	</div>
	<script src="/public/js/common.js"></script>
	<script src="/public/layer/layer.js"></script>
	<script type="text/javascript">


		var xiajia_status=0;
		var del_status=0;

		function xiajia(id){
			var p=<?php echo ($p); ?>;

			layer.open({
			  type: 1,
			  title:"是否确定将该视频下架",
			  skin: 'layui-layer-rim', //加上边框
			  area: ['30%', '30%'], //宽高
			  content: '<div class="textArea"><textarea id="xiajia_reason" maxlength="50" placeholder="请输入下架原因,最多50字" /> </div><div class="textArea_btn" ><input type="button" id="xiajia" value="下架" onclick="xiajia_submit('+id+','+p+')" /><input type="button" onclick="layer.closeAll();" value="取消" /></div>'
			});
		}

		function xiajia_submit(id,p){

			var reason=$("#xiajia_reason").val();
			if(xiajia_status==1){
				return;
			}
			xiajia_status=1;
			$.ajax({
				url: '/index.php?g=Admin&m=Video&a=setXiajia',
				type: 'POST',
				dataType: 'json',
				data: {id:id,reason: reason},
				success:function(data){
					var code=data.code;
					if(code!=0){
						layer.msg(data.msg);
						return;
					}
					xiajia_status=0;
					//设置按钮不可用
					$("#xiajia").attr("disabled",true);
					layer.msg("下架成功",{icon: 1,time:1000},function(){
						layer.closeAll();
						location.href='/index.php?g=Admin&m=Video&a=index&p='+p;
					});
				},
				error:function(e){
					$("#xiajia").attr("disabled",false);
					console.log(e);
				}
			});
			
			
		}

		function del(id){
			var p=<?php echo ($p); ?>;

			layer.open({
			  type: 1,
			  title:"是否确定将该视频删除",
			  skin: 'layui-layer-rim', //加上边框
			  area: ['30%', '30%'], //宽高
			  content: '<div class="textArea"><textarea id="del_reason" maxlength="50" placeholder="请输入删除原因,最多50字" /> </div><div class="textArea_btn" ><input type="button" id="delete" value="删除" onclick="del_submit('+id+','+p+')" /><input type="button" onclick="layer.closeAll();" value="取消" /></div>'
			});
		}

		function del_submit(id,p){

			var reason=$("#del_reason").val();

			if(del_status==1){
				return;
			}

			del_status=1;

			$.ajax({
				url: '/index.php?g=Admin&m=Video&a=del',
				type: 'POST',
				dataType: 'json',
				data: {id:id,reason: reason},
				success:function(data){
					var code=data.code;
					if(code!=0){
						layer.msg(data.msg);
						return;
					}

					del_status=0;
					//设置按钮不可用
					$("#delete").attr("disabled",true);

					layer.msg("删除成功",{icon: 1,time:1000},function(){
						layer.closeAll();
						location.href='/index.php?g=Admin&m=Video&a=index&p='+p;
					});
				},
				error:function(e){
					$("#delete").attr("disabled",false);
					console.log(e);
				}
			});
			
			
		}

		/*获取视频评论列表*/
		function commentlists(videoid){
			layer.open({
				type: 2,
				title: '视频评论列表',
				shadeClose: true,
				shade: 0.8,
				area: ['60%', '90%'],
				content: '/index.php?g=Admin&m=Video&a=commentlists&videoid='+videoid 
			}); 
		}
	</script>

	<script type="text/javascript">
		function videoListen(id){
			layer.open({
			  type: 2,
			  title: '观看视频',
			  shadeClose: true,
			  shade: 0.8,
			  area: ['500px', '750px'],
			  content: '/index.php?g=Admin&m=Video&a=video_listen&id='+id
			}); 
		}
	</script>
</body>
</html>