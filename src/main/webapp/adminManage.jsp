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
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/vue.min.js"></script>
    <script src="js/axios.min.js"></script>
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
            </ul>
        </div>
        <div class="rightSide col-md-8">
            <div id="myTabContent" class="tab-content">
                <div id="home" class="tab-pane fade in active">
                    <h3>账号权限管理</h3>
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">社团名称：</label>
                            <select class="form-control" @change="changeComm($event)">
                                <option value="1">请选择</option>
                                <option v-for="(p,index) in activityList" :key="index" :value="p.commId">{{p.commName}}</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">社团成员：</label>
                            <select class="form-control" @change="changeMemeber($event)">
                                <option value="1">请选择</option>
                                <option v-for="(p,index) in memberList" :key="index" :value="p.login_name">{{p.login_name}}</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">成员角色：</label>
                            <select class="form-control" @change="changePlayer($event)">
                                <option value="1">请选择</option>
                                <option value="2">社团管理员</option>
                                <option value="3">普通成员</option>
                            </select>
                        </div>
                    </form>
                    <button type="button" class="btn btn-primary" @click="exitChange">确认修改</button>
                </div>
                <div id="profile" class="tab-pane fade">
                    <h3>创建社团管理</h3>
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label for="communityName" class="col-sm-3 control-label">社团名称：</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="communityName" placeholder="请输入社团名称" v-model="commName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="communityBoss" class="col-sm-3 control-label">理事长：</label>
                            <div class="col-sm-9">
                                <input type="textarea" class="form-control" id="communityBoss" placeholder="请输入社团理事长" v-model="commBoss">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="communityClass" class="col-sm-3 control-label">社团分类：</label>
                            <div class="col-sm-9">
                                <input type="textarea" class="form-control" id="communityClass" placeholder="请输入社团分类" v-model="commClassId">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="communityInfo" class="col-sm-3 control-label">社团简介：</label>
                            <div class="col-sm-9">
                                <textarea class="form-control" rows="5" id="communityInfo" placeholder="请输入社团简介" v-model="commInfo"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="communityAct" class="col-sm-3 control-label">社团特色活动：</label>
                            <div class="col-sm-9">
                                <textarea class="form-control" rows="5" id="communityAct" placeholder="请输入社团特色活动" v-model="commActivity"></textarea>
                            </div>
                        </div>
                    </form>
                    <button type="button" class="btn btn-primary" @click="createComm">确认创建</button>
                </div>
                <div id="messages" class="tab-pane fade">
                    <h3>解散社团管理</h3>
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">社团名称：</label>
                            <select class="form-control" @change="getDeleteId($event)">
                                <option value="1">请选择</option>
                                <option v-for="(p,index) in activityList" :key="index" :value="p.commId">{{p.commName}}</option>
                            </select>
                        </div>
                    </form>
                    <button type="button" class="btn btn-primary" @click="deleteComm">确认解散</button>
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
    var adminManage = new Vue({
        el:'#manage',
        data:{
            commId: '',
            activityList:[],
            memberList:[],
            loginName:'',
            player: '',
            commName: '',
            commBoss:'',
            commInfo:'',
            commActivity:'',
            commClassId:'',
            deleteCommId: ''
        },
        mounted: function () {
                /*查询所有社团名称*/
                axios.post('/community/index')
                    .then((response) => {
                    console.log('查询所有社团信息')
                    response.data.filter(value => {
                        let list = value.communitys
                        list.map(item => {
                            this.activityList.push(item)
                    })
                    })
                console.log(this.activityList)
                }).catch(function (error) {
                    console.log(error);
                });


        },
        methods:{
            changeComm(event){
                this.commId = event.target.value
                /*查询社团成员*/
                axios.post('/community/findMemberAll', {commId: this.commId})
                    .then((response) => {
                    console.log('查询社团成员姓名')
                this.memberList=response.data
                console.log(response)
            }).catch(function (error) {
                    console.log(error);
                });
            },
            changeMemeber(event){
                this.loginName=event.target.value
            },
            changePlayer(event){
                console.log(event.target.value)
                if (event.target.value == '2'){
                    this.player = '社团管理员'
                } else {
                    this.player= '普通成员'
                }
            },
            /*发送修改社团账号权限申请*/
            exitChange(){
                let params = {
                    loginName:this.loginName,
                    commId:this.commId,
                    newRole:this.player
                }
                axios.post('/community/roleChange',params)
                    .then((response) => {
                    console.log('社团账号权限修改成功')
                 }).catch(function (error) {
                    console.log(error);
                });
            },
            /*创建社团*/
            createComm(){
                let params = {
                    commName:this.commName,
                    loginName:this.commBoss,
                    commInfo:this.commInfo,
                    commSpecialAct:this.commActivity,
                    commClassId:this.commClassId
                }
                axios.post('/community/commAdd',params)
                    .then((response) => {
                    console.log('社团创建成功')
            }).catch(function (error) {
                    console.log(error);
                });
            },
            getDeleteId(event){
                console.log('进入结算社团了')
                console.log(event.target.value)
                this.deleteCommId=event.target.value
            },
            /*解散社团*/
            deleteComm(){
                axios.post('/community/commDel',{commId:this.deleteCommId})
                    .then((response) => {
                    console.log('删除社团成功')
            }).catch(function (error) {
                    console.log(error);
                });
            },

        }
    })
</script>
</html>
