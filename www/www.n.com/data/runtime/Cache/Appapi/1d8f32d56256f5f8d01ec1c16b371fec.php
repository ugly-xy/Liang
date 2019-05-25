<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html>
<head lang="en">
    
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta content="telephone=no" name="format-detection" />
    <link type="text/css" rel="stylesheet" href="/public/home/css/layer.css"/>
    <link href='/public/appapi/css/common.css?t=1540432563' rel="stylesheet" type="text/css" >

    <title><?php echo LangT('错误提示');?></title>
    <style type="text/css">
        body{text-align:center;background: #110D24;}
        .title2{color: #526165;font-size: 1.0em;text-align: center;margin-top: 4%;}
        img{width: 18%;margin: 30% auto 9%;}
        p{font-size: 0.9em;color: #B4C1C7;text-align: center;line-height: 150%}
        .alink{color:#00D8C9;text-decoration:underline; }
    </style>
</head>
<body>
<img src="/public/appapi/images/error.jpg">
<div class="title2"><p><?php echo ($reason); ?><br></p></div>

    <script src="/public/js/jquery.js"></script>
    <script src="/public/home/js/layer.js"></script>


</body>
</html>