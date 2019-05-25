<?php if (!defined('THINK_PATH')) exit();?><!--<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="telephone=no" name="format-detection">
<meta name="baidu-site-verification" content="QpfdfPmoBr" />
<title><?php echo ($site_name); ?></title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>

<link rel="stylesheet" type="text/css" href="/public/index/css/index.css">

</head>
<body>
<div class="content pr">
    <div class="log"><img src="/public/index/images/log.png"></div>
    <div class="erweima display"><img src="<?php echo ($config['apk_ewm']); ?>"></div>
    <div class="erweima1"><img src="<?php echo ($config['ipa_ewm']); ?>"></div>
    <div class="button">
        <a class="ios" href="<?php echo ($config["app_ios"]); ?>"><img src="/public/index/images/ios2.png"></a>
        <a class="ios display" href="<?php echo ($config["app_ios"]); ?>"><img src="/public/index/images/ios.png"></a>
        <a class="adr" href="<?php echo ($config["app_android"]); ?>"><img src="/public/index/images/adr2.png"></a>
        <a class="adr display" href="<?php echo ($config["app_android"]); ?>"><img src="/public/index/images/adr.png"></a>
    </div>
    <div class="foot">
        <div></div>
        <div>地址：<?php echo ($config["address"]); ?></div>
        <div>电话:<?php echo ($config["mobile"]); ?></div>
        <div><?php echo ($config["copyright"]); ?></div>
    </div>
</div>

</body>
<script src="/public/js/jquery.js"></script>
<script>
    $(function(){
        $(".ios").mouseover(function(){
            $(".erweima1").removeClass("display")
            $(".erweima").addClass("display")
            $(".ios").eq(0).addClass("display")
            $(".ios").eq(1).removeClass("display")
        })
        $(".ios").mouseout(function(){
            $(".erweima1").removeClass("display")
            $(".erweima").addClass("display")
            $(".ios").eq(0).removeClass("display")
            $(".ios").eq(1).addClass("display")

        })
        $(".adr").mouseover(function(){
            $(".erweima").removeClass("display")
            $(".erweima1").addClass("display")
            $(".adr").eq(0).addClass("display")
            $(".adr").eq(1).removeClass("display")
        })
        $(".adr").mouseout(function(){
            $(".erweima").removeClass("display")
            $(".erweima1").addClass("display")
            $(".adr").eq(0).removeClass("display")
            $(".adr").eq(1).addClass("display")

        })
    })
</script>
</html>-->