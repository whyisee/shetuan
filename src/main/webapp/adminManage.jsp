<%--
  Created by IntelliJ IDEA.
  User: 邹慧琴
  Date: 2020/4/11
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" media="all" />
    <link href="css/commManage.css" rel="stylesheet" type="text/css"/>
    <title>系统管理</title>
    <script src="https://cdn.jsdelivr.net/npm/vue"></script>
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<div id="manage">
    <div class="headContent">
        <h1>系统管理员管理系统</h1>
    </div>
    <div class="manageModule row" role="navigation">
        <div class="leftSide col-md-4">
            <ul class="nav nav-pills nav-stacked" id="myTab">
                <li role="presentation" class="active"><a href="#home" data-toggle="tab">账号权限</a></li>
                <li role="presentation"><a href="#profile" data-toggle="tab">创建社团</a></li>
                <li role="presentation"><a href="#messages" data-toggle="tab">结散社团</a></li>
                <li role="presentation"><a href="#settings" data-toggle="tab">留言</a></li>
            </ul>
        </div>
        <div class="rightSide col-md-8">
            <div id="myTabContent" class="tab-content">
                <div id="home" class="tab-pane fade in active">
                    <h3>账号权限管理</h3>
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label for="userName" class="col-sm-3 control-label">姓名：</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="userName" placeholder="请输入姓名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="userNumber" class="col-sm-3 control-label">学号：</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="userNumber" placeholder="请输入学号">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="userNumber" class="col-sm-3 control-label">角色：</label>
                            <div class="col-sm-9">
                                <label>
                                    <input type="radio" value="student"> 学生
                                    <input type="radio" value="commonManage"> 社团管理员
                                    <input type="radio" value="manage"> 系统管理员
                                </label>
                            </div>
                        </div>
                    </form>
                </div>
                <div id="profile" class="tab-pane fade">
                    <h3>创建社团管理</h3>
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label for="communityName" class="col-sm-3 control-label">社团名称：</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="communityName" placeholder="请输入社团名称">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="communityBoss" class="col-sm-3 control-label">理事长：</label>
                            <div class="col-sm-9">
                                <input type="textarea" class="form-control" id="communityBoss" placeholder="请输入社团理事长">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="communityInfo" class="col-sm-3 control-label">社团简介：</label>
                            <div class="col-sm-9">
                                <textarea class="form-control" rows="5" id="communityInfo" placeholder="请输入社团简介"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="communityAct" class="col-sm-3 control-label">社团特色活动：</label>
                            <div class="col-sm-9">
                                <textarea class="form-control" rows="5" id="communityAct" placeholder="请输入社团特色活动"></textarea>
                            </div>
                        </div>
                    </form>
                    <button type="button" class="btn btn-primary">确认创建</button>
                </div>
                <div id="messages" class="tab-pane fade">
                    <h3>解散社团管理</h3>
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label for="commName" class="col-sm-3 control-label">社团名称：</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="commName" placeholder="请输入社团名称">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="commLender" class="col-sm-3 control-label">社团理事长：</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="commLender" placeholder="请输入社团理事长名称">
                            </div>
                        </div>
                    </form>
                    <button type="button" class="btn btn-primary">确认解散</button>
                </div>
                <div id="settings" class="tab-pane fade">
                    <h3>留言管理</h3>
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label for="name" class="col-sm-3 control-label">姓名：</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="name" placeholder="请输入学生姓名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="number" class="col-sm-3 control-label">学号：</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="number" placeholder="请输入学生学号">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="commentContent" class="col-sm-3 control-label">留言内容：</label>
                            <div class="col-sm-9">
                                <textarea class="form-control" rows="5" id="commentContent" placeholder="请输入留言内容"></textarea>
                            </div>
                        </div>
                    </form>
                    <button type="button" class="btn btn-primary">确认留言</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        $(function () {
            $('#myTab li:eq(0) a').tab('show');
        });
    })
</script>
</html>
