<%--
  Created by IntelliJ IDEA.
  User: bjp-yxkj-zoukh
  Date: 2020/4/4
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人中心</title>
    <script src="https://cdn.jsdelivr.net/npm/vue"></script>
    <script src="js/jquery.min.js"></script>
    <link href="/css/user_info.css" type="text/css" rel="stylesheet" />

</head>
<body>
<div id="navbar" class="navbar-collapse collapse">


    <ul class="nav navbar-nav" id="menu-list">
        <li class="active"><a href="index2.jsp">首页</a></li>
        <li v-for="item in items">
            <a v-bind:href="'index2.jsp#comm'+item.commClassId" >{{ item.menuName }}</a>
        </li>
        <li><a href="index2.jsp#notice">活动公告</a></li>
    </ul>
</div>


${login.loginName }
<%--${funcright}--%>

<form >
    <label>上传图片</label>
    <input type="file" name="file" id="fileName1">
<%--
    <input type="submit" value="上传">
--%>
    <input type="button" value="上传" onclick="return upload(this);"/>

</form>

<div data-v-ee13a7d2="" data-v-6c79fef7="" class="security-left"><span data-v-ee13a7d2="" class="security-title">个人中心</span>
    <ul  id="ser-ul" class="security-ul">
    <li  class="security-list"> <span  class="security-nav-name">首<b style="width:28px; display:inline-block"></b>页</span></li>
    <li  class="security-list"> <span  class="security-nav-name">我的信息</span></li>
    <li  class="security-list"> <span  class="security-nav-name">我的头像</span></li>
    <li  class="security-list"> <span  class="security-nav-name">账号安全</span></li>
    <li  class="security-list"> <span  class="security-nav-name">我的社团</span></li>
    <li  class="security-list"> <span  class="security-nav-name">我的活动</span></li>
    <li  class="security-list"> <span  class="security-nav-name">我的审核</span></li>
    <li  class="security-list"> <span  class="security-nav-name">我的留言</span></li>
    <li  class="security-list"> <span  class="security-nav-name">学生认证</span></li>
    <li  class="security-list"> <span  class="security-nav-name">邀请注册</span></li>
    </ul>

<div>
    <form>

        <div>头像</div>
        <div><img id="img1" src="/images/head-default.jpeg" class="tool-user-face"/></div>
        <div>昵称</div>
        <div><input type="text" maxlength="16"></div>
    </form>
</div>


<script src="/js/user_info.js"></script>
</body>
</html>
