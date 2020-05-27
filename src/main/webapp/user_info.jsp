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

<
<form>
    <input type="file" id="fileName1">
    <img id="img1" src="">
    <input type="button" onclick="upload(this)" value="shangchuang">
</form>
${login.loginName }
${funcright}
<script src="/js/user_info.js"></script>
</body>
</html>
