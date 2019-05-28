//验证文本框只填数字
function checkNumber(e,strid,msg)
{
    var key = window.event ? e.keyCode : e.which;
    var keychar = String.fromCharCode(key);
    var el = document.getElementById(strid);
    var msg = document.getElementById(msg);
    reg = /\d/;
    var result = reg.test(keychar);
    if(!result)
    {
    el.className = "warn";
    msg.innerHTML = "只能输入数字";
    return false;
    }
    else
    {
    el.className = "";
    msg.innerHTML = "";
    return true;
    }
    
} 