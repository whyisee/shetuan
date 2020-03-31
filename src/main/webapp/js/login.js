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
            $("#myform").submit();
            return false;
            /*    var username=	$("#username").val();
                var userpass=	$("#userpass").val();
                var user={loginName:username,loginPass:userpass};
                //$('#myform').serialize(),
                $.ajax({
                        url:"/member/login",
                        contentType: 'application/json;charset=UTF-8',
                        type:"post",
                        data:JSON.stringify(user),
                        //返回数据的格式
                        datatype: "json",
                        success:function(result){
                            //alert(result);
                            console.log(result)
                            result=JSON.parse(result)
                            if(null!=result) {
                                console.log(result.status+result.managerId)

                                changeImg();
                                if (result.status == "1" && result.managerId == "1") {
                                    window.location.href = "admin";
                                }
                                if (result.status == "1" && result.managerId == "0") {
                                    window.location.href = "/index.jsp";
                                }
                            }
                        },
                        error:function(){
                        }
                    }
                )*/
        }
    });


}

function changeImg(){
 document.getElementById("validateCodeImg").src="/servlet/validateCodeServlet?"+Math.random();
}

