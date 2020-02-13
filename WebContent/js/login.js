function check(form) {
	if (document.forms.myform.username.value == "") {
		alert("请填写用户名");
		document.forms.myform.username.focus();
		return false;
	}
	if (document.forms.myform.userpass.value == "") {
		alert("请输入密码 ");
		document.forms.myform.userpass.focus();
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

