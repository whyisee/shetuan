<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="./images/pande.png">
    <title>Login</title>
</head>
     <link href="css/login.css" type="text/css" rel="stylesheet" />
	<!-- js files -->
	<script src="js/login.js"></script>
<script src="js/jquery.min.js"></script>

	<!-- /js files -->
</head>
<body>
    <div class="wrap">
        <div class="container">
            <h1>欢迎</h1>
            <%--<c:if test="${param.error =='uname' }"><font color=red >用户名密码错误</font></c:if>--%>
            <form name="myform" action="Login" method="post">
            	<%if(request.getAttribute("return_uri")!=null) {%>
				<input type="hidden" name="return_uri" value="<%=request.getAttribute("return_uri") %>"/>
				<%} %>
                <input name="username" id="username" type="text" placeholder="登录名"/><br>
                <input name="userpass" id="userpass" type="password" placeholder="密码" />
                <div class="">
                    <input style="vertical-align:middle; width: 240px" name="validateCode" id="validateCode" type="text" placeholder="验证码" />
                    <img  style="vertical-align:middle;"  alt="验证码看不清，换一张" src="/servlet/validateCodeServlet" id="validateCodeImg" onclick="changeImg()">

                </div>
                <input type="button" value="登录" onclick="return check(this);"/>
                    <p></p>
                <input name="lru" type="hidden"  value="login"/>
                <input name="log" type="hidden" value="log"/>
            </form>

			<div id="regst">
                如果你还没有账户，请点击&nbsp;&nbsp;<a href="regist.jsp">注册</a>

            </div>
           <%-- <a href="//wpa.qq.com/msgrd?v=3&amp;uin=907022598&amp;site=Discuz! Board&amp;menu=yes&amp;from=discuz" target="_blank" title="QQ">
                <img src="static/image/common/site_qq.jpg" alt="QQ"></a>--%>
        </div>
        <ul>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
        </ul>
    </div>
<script type="">
    function login(){

    }
</script>
</body>
</html>