<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html>
<head>
<title><?php echo ($news["post_title"]); ?></title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<!-- Set render engine for 360 browser -->
<meta name="renderer" content="webkit">

<!-- No Baidu Siteapp-->
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<link href="/themes/simplebootx/Public/css/page.css" rel="stylesheet">
</head>
<style>
body{
 background:#110D24;
 color:#FFF;
}
p{
	color: #FFF;
}
.news_content .news_bd{
	margin:0;
}
</style>
<body class="body-white">
	<div class="container tc-main">	
	   <div class="news_content">
		     <!-- <div class="news_hd">
				   <?php echo ($news["post_title"]); ?>
				 </div> -->
				 
				 <div class="news_bd">
				   <?php echo ($news["post_content"]); ?>
				 </div>
		 
		 </div>
		 
	</div>
	<!-- /container -->
</body>
</html>