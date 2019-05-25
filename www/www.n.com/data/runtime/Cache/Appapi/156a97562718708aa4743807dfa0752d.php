<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html>
<head lang="en">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta content="telephone=no" name="format-detection">
    <title><?php echo LangT('关于'); echo ($sitename); ?></title>
    <link rel="stylesheet" type="text/css" href="/public/appapi/about/css/about.css?t=<?php echo ($time); ?>">

</head>
<body>
    <div class="icon_area">
        <img src="/icon.png">
    </div>
    <div class="edition"><?php echo LangT('版本号');?> <?php echo ($version); ?></div>
    <ul class="artice_list">
        <?php if(is_array($articleList)): foreach($articleList as $key=>$vo): ?><a href="/index.php?g=portal&m=page&a=news&id=<?php echo ($vo['id']); ?>&language=<?php echo ($language_type); ?>">
                <li><?php echo ($vo['post_title']); ?></li>
            </a><?php endforeach; endif; ?>
        <a href="/index.php?g=portal&m=page&a=questions&language=<?php echo ($language_type); ?>">
            <li><?php echo ($name); ?></li>
        </a>
        <a href="/index.php?g=Appapi&m=service&a=index&language=<?php echo ($language_type); ?>">
            <li><?php echo LangT('联系客服');?></li>
        </a>
        <a href="/index.php?g=Appapi&m=feedback&a=index&uid=<?php echo ($uid); ?>&token=<?php echo ($token); ?>&language=<?php echo ($language_type); ?>">
            <li><?php echo LangT('我要反馈');?></li>
        </a>

    </ul>
<script type="text/javascript" src="/public/js/jquery.js"></script>
<script type="text/javascript" src="/public/appapi/about/js/about.js?t=<?php echo ($time); ?>"></script>
<script type="text/javascript" src="/public/layer/layer.js"></script>
<script type="text/javascript">
	var lang="<?php echo ($language); ?>";
	var language_type="<?php echo ($language_type); ?>";
</script> 
<script src="/public/js/function.js"></script>
</body>
</html>