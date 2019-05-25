<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html>
<head lang="en">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta content="telephone=no" name="format-detection">
    <title><?php echo LangT('身份认证');?></title>
    <link rel="stylesheet" type="text/css" href="/public/appapi/auth/css/auth.css?t=<?php echo ($time); ?>">
    
</head>
<body>
    <div class="woring_title">
        <?php echo LangT('请认真填写实名信息，否则无法通过认证');?>
    </div>
    <ul class="auth_list">
        <li>
            <p class="auth_list_title fl"><?php echo LangT('真实姓名');?></p>
            <p class="auth_list_right fr">
                <input type="text" name="realname" id="realname" placeholder="<?php echo LangT('请输入姓名');?>"><span class="you"><img src="/public/appapi/images/you.png" /></span>
            </p>
            <p class="clearboth"></p>
        </li>

        <li>
            <p class="auth_list_title fl"><?php echo LangT('联系电话');?></p>
            <p class="auth_list_right fr">
                <input type="text" name="phone" id="phone" placeholder="<?php echo LangT('请输入电话号码');?>"><span class="you"><img src="/public/appapi/images/you.png" /></span>
            </p>
            <p class="clearboth"></p>
        </li>

        <li class="border_none">
            <p class="auth_list_title fl"><?php echo LangT('身份证号码');?></p>
            <p class="auth_list_right fr">
                <input type="text" name="cardno" id="cardno" placeholder="<?php echo LangT('请输入身份证号码');?>"><span class="you"><img src="/public/appapi/images/you.png" /></span>
            </p>
            <p class="clearboth"></p>
        </li>
    </ul>

   <div class="agreement">
        <!--<p class="agree_area "><img src="/public/appapi/auth/images/true.png"></p> <?php echo LangT('同意');?><a href="./index.php?g=portal&m=page&a=index&id=28&language=<?php echo ($language_type); ?>"><?php echo ($appname); echo LangT('隐私协议');?></a>-->
        <div class="clearboth"></div>
    </div>

    <div class="autharea">
        <input type="button" value="<?php echo LangT('开始认证');?>">
    </div>



<script type="text/javascript" src="/public/js/jquery.js"></script>
<script type="text/javascript">
	var lang='<?php echo ($language); ?>';
	var language_type="<?php echo ($language_type); ?>";
</script> 
<script type="text/javascript">
    var uid=<?php echo ($uid); ?>;
</script>
<script src="/public/js/function.js"></script>
<script type="text/javascript" src="/public/appapi/auth/js/auth.js?t=<?php echo ($time); ?>"></script>
<script type="text/javascript" src="/public/layer/layer.js"></script>
</body>
</html>