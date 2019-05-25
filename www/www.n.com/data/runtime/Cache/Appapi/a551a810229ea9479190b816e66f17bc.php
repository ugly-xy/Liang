<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html>
<head lang="en">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta content="telephone=no" name="format-detection">
    <title><?php echo LangT('用户举报');?></title>
    <link rel="stylesheet" type="text/css" href="/public/appapi/userreport/css/userreport.css?t=<?php echo ($time); ?>">
    <link type="text/css" rel="stylesheet" href="/public/layer/theme/default/layer.css"/>
    <link type="text/css" rel="stylesheet" href="/public/appapi/userreport/css/reportcon.css?t=<?php echo ($time); ?>"/>
    
</head>
<body>

    <div class="classify_area">
        <div class="woring_title">
            *<?php echo LangT('选择举报的理由');?>
        </div>
        <ul class="report_list">

            <?php if(is_array($classifies)): $i = 0; $__LIST__ = $classifies;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$vo): $mod = ($i % 2 );++$i;?><li class="li"  onclick="male(<?php echo ($vo['id']); ?>)">
                    <p class="report_list_title fl"><?php echo ($vo['title']); ?></p>
                    <p class="report_list_right fr">
                        <input type="radio" name="classify" id="male<?php echo ($vo['id']); ?>" value="<?php echo ($vo['id']); ?>"><label for="male<?php echo ($vo['id']); ?>"></label>
                    </p>
                    <p class="clearboth"></p>
                </li><?php endforeach; endif; else: echo "" ;endif; ?>
        </ul>

        <div class="reportarea">
            <input type="button" value="<?php echo LangT('下一步');?>" >
        </div>  
    </div>

    <div class="report_con">
        
        <div class="report_con_body">
            <div class="report_con_textarea">
                <textarea class="textarea" id="content" placeholder="<?php echo LangT('详细描述举报理由……');?>" maxlength="200"></textarea>
                <div class="tips">
                    <span id="input_nums">0</span>/200
                </div>

                <div class="report_con_img">
                    <img src="/public/appapi/userreport/images/upload.png" class="fl img-sfz" data-index="ipt-file1" onclick="file_click($(this))">
                    <div class="shad shadd" data-select="ipt-file1">
                        <div class="title-upload"><?php echo LangT('正在上传中');?>...</div>
                        <div id="progress1">
                            <div class="progress ipt-file1"></div>
                        </div>
                    </div>
                    <input value="" type="hidden" name="thumb" id="thumb" class="sf1">
                    <div id="upload"></div>
                </div>

            </div>

            
        </div>

        <div class="report_con_body report_con_phone_tips" style="">
            <?php echo LangT('收到举报后，我们会在24小时内进行处理，处理成功后，我们会第一时间告知处理结果');?>
        </div>
        <div class="report_con_body" style="margin-top: 5px;">
            <!-- <div class="report_con_tel">
                
                 <div class="report_con_tel_con">
                     <input type="input" id="contactMsg" placeholder="QQ/微信/邮箱/联系电话">
                 </div>
                 <div class="clearboth"></div>
            </div> -->
        </div>

        <div class="report_con_body" style="margin-top:10px;">
            <div class="report_con_bottom"><?php echo LangT('提交');?></div>    
        </div>

    </div>
    

<script type="text/javascript">
    var uid=<?php echo ($uid); ?>;
    var token='<?php echo ($token); ?>';
    var touid='<?php echo ($touid); ?>';
    
</script>
<script type="text/javascript" src="/public/js/jquery.js"></script>
<script type="text/javascript">
	var lang='<?php echo ($language); ?>';
	var language_type="<?php echo ($language_type); ?>";
	
	
</script> 


<script src="/public/js/function.js"></script>

<script type="text/javascript" src="/public/appapi/userreport/js/userreport.js?t=<?php echo ($time); ?>"></script>
<script type="text/javascript" src="/public/layer/layer.js"></script>
<script src="/public/appapi/js/ajaxfileupload.js"></script>
<script src="/public/appapi/userreport/js/reportcon.js?t=<?php echo ($time); ?>"></script>
<script>
	function male(male){
		$('#male'+male).prop("checked",true)
	}
</script>
</body>
</html>