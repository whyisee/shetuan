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
	} else if (document.forms.myfor.userName.value== "") {
		alert("姓名不能为空。");
		document.forms.myfor.userName.focus();
		return false;
	} else if (document.forms.myfor.studentId.value== "") {
		alert("学号号不能为空。");
		document.forms.myfor.studentId.focus();
		return false;
	}
    if (document.forms.myfor.validateCode.value == "") {
        alert("请输入验证码 ");
        document.forms.myfor.validateCode.focus();
        return false;
    }
    //验证码校验
    var validateCode=$("#validateCode").val();
    $.get("/servlet/validateCodeServlet",{validateCode:validateCode},function(data){
        data=$.trim(data);
        //console.log(data)
        if(data=="false"){
            alert("验证码错误 ");
            changeImg();
            return false;
        }else{
          //  $("#md5password").attr("value",hex_md5($("#password").val()));
            $("#myfor").submit();

            return false;
        }
    });
}
function changeImg(){
    document.getElementById("validateCodeImg").src="/servlet/validateCodeServlet?"+Math.random();
}
