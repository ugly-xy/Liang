<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html>
<head>
<title><?php if($language_type == 'ko'): echo ($post_title_kr); else: echo ($post_title); endif; ?></title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<!-- Set render engine for 360 browser -->
<meta name="renderer" content="webkit">

<!-- No Baidu Siteapp-->
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<link href="/themes/simplebootx/Public/css/page.css?t=1545290181" rel="stylesheet">
</head>
<style>
body{
 background:#110D24;
 color:#FFF;
}

</style>
<body class="body-white">
	<div class="container tc-main">	
	   <div class="page_content">
		     <?php echo ($post_content); ?>
		 </div>
		
	</div>
	<!-- /container -->
</body>
</html>