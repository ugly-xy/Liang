<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html>
	<head>
        
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta content="telephone=no" name="format-detection" />
    <link type="text/css" rel="stylesheet" href="/public/home/css/layer.css"/>
    <link href='/public/appapi/css/common.css?t=1540432563' rel="stylesheet" type="text/css" >

		<title><?php echo LangT('交易所');?></title>
		<link href='/public/appapi/css/wallet.css' rel="stylesheet" type="text/css" >
	</head>
<body id="body">
	<div class="exchange">
		<ul>
		<?php if(is_array($list)): $i = 0; $__LIST__ = $list;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$vo): $mod = ($i % 2 );++$i;?><li onclick="jump('<?php echo ($vo['url']); ?>')">
				<div class="exchange_img">
					<img src="<?php echo ($vo['avatar']); ?>" />
				</div>
				<div class="exchange_text">
					<div class="exchange_title"><?php echo ($vo['title']); ?></div>
					<div class="exchange_introduce"><?php echo ($vo['introduce']); ?></div>
				</div>
				<div class="getinto"><a href="<?php echo ($vo['url']); ?>"><?php echo LangT('进入交易所');?>></a></div>
			</li>
			<div class="every"></div><?php endforeach; endif; else: echo "" ;endif; ?>
		</ul>
	</div>
    <script src="/public/js/jquery.js"></script>
    <script src="/public/home/js/layer.js"></script>


<script type="text/javascript">
	var lang='<?php echo ($language); ?>';
	var language_type="<?php echo ($language_type); ?>";
</script> 

<script>
	function jump(url){
		console.log(url);
		location.href=url;
	}
</script>
<script src="/public/js/function.js"></script>
</body>
</html>