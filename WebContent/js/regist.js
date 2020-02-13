function registCheck(form) {
	if (document.forms.myfor.username.value == "") {
		alert("请填写用户名");
		document.forms.myfor.username.focus();
		return false;
	} else if (document.forms.myfor.userpass.value== "") {
		alert("请填写密码");
		document.forms.myfor.userpass.focus();
		return false;
	} else if (document.forms.myfor.userpass.value != document.forms.myfor.repass.value) {
		alert("两次输入的密码不一致。");
		document.forms.myfor.repass.focus();
		return false;
	} else if (document.forms.myfor.email.value== "") {
		alert("邮箱不能为空。");
		document.forms.myfor.email.focus();
		return false;
	} else if (document.forms.myfor.phone.value== "") {
		alert("手机号不能为空。");
		document.forms.myfor.phone.focus();
		return false;
	}
    if (document.forms.myform.validateCode.value == "") {
        alert("请输入验证码 ");
        document.forms.myform.validateCode.focus();
        return false;
    }
    //验证码校验
    var validateCode=	$("#validateCode").val();
    $.get("/servlet/validateCodeServlet",{validateCode:validateCode},function(data){
        data=$.trim(data);
        //console.log(data)
        if(data=="false"){
            alert("验证码错误 ");
            changeImg();
            return false;
        } else{
            var username=	$("#username").val();
            var userpass=	$("#userpass").val();
            var user={loginName:username,loginPass:userpass};

            $.ajax({
                    url:"Login",
                    contentType: 'application/json;charset=UTF-8',
                    type:"post",
                    data:JSON.stringify(user),
                    //返回数据的格式
                    datatype: "json",
                    success:function(result){
                        //alert(result);
                        //console.log(result)
                        if(null!=result) {
                            changeImg();
                            if (result.loginId == "0" && result.managerId == "1") {
                                window.location.href = "admin";
                            }
                        }
                    },
                    error:function(){
                    }
                }
            )
        }
    });
}
function changeImg(){
    document.getElementById("validateCodeImg").src="/servlet/validateCodeServlet?"+Math.random();
}
