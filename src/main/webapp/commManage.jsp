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
    <title>社团管理</title>
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/vue.min.js"></script>
    <script src="js/axios.min.js"></script>
    <script src="js/sweetalert.min.js"></script>
    <style type="text/css">
        .commGroup{
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .box{
            width: 450px !important;
        }
    </style>
</head>
<body>
<div id="manage">
<div class="headContent">
    <h1>社团管理员管理系统</h1>
</div>
<div class="manageModule row" role="navigation">
    <div class="leftSide col-md-4">
        <ul class="nav nav-pills nav-stacked" id="myTab">
            <li role="presentation" class="active"><a href="#home" data-toggle="tab">社团信息管理</a></li>
            <li role="presentation"><a href="#profile" data-toggle="tab">社团成员管理</a></li>
            <li role="presentation"><a href="#messages" data-toggle="tab">社团活动管理</a></li>
            <li role="presentation"><a href="#settings" data-toggle="tab">审核</a></li>
        </ul>
    </div>
    <div class="rightSide col-md-8">
        <div id="myTabContent" class="tab-content">
            <div id="home" class="tab-pane fade in active">
                <h3>社团信息管理</h3>
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">社团名称：</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" v-model="commInfo.commName" placeholder="请输入社团名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">理事长：</label>
                        <div class="col-sm-9">
                            <input type="textarea" class="form-control" v-model="commInfo.bossName" placeholder="请输入社团理事长">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">社团简介：</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" rows="5" v-model="commInfo.commInfo" placeholder="请输入社团简介"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">社团特色活动：</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" rows="5" v-model="commInfo.commSpecialAct" placeholder="请输入社团特色活动"></textarea>
                        </div>
                    </div>
                </form>
                <button type="button" class="btn btn-primary" @click="editCommInfo()" style="margin-left: 50%;transform: translateX(-50%)">确认修改</button>
            </div>
            <div id="profile" class="tab-pane fade">
                <h3>社团成员信息</h3>
                <ul class="list-group" v-for="(item,index) in memberInfo" :key="index">
                    <li class="list-group-item">
                        <span>姓名：{{item.user_name}}</span>
                        <button type="button" class="btn btn-info" style="width: 60px;margin: 0px 30px 0 460px;height: 35px" @click="editMember(item.comm_id,item.login_id,index)" type="button" class="btn btn-primary btn-lg btn btn-link" data-toggle="modal" data-target="#myModal">编辑</button>
                        <button type="button" class="btn btn-danger" style="width: 60px;margin:0px;height: 35px" @click="deleteMember(item.comm_id,item.login_id)">删除</button>
                    </li>
                    <li class="list-group-item">职位：{{item.comm_worker}}</li>
                    <li class="list-group-item">加入社团时间：{{item.create_date}}</li>
                    <li class="list-group-item">个人简介：{{item.comm_remark}}</li>
                </ul>
            </div>
            <%--弹框--%>
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="margin-bottom: 0px"><span aria-hidden="true">&times;</span></button>
                            <h3 class="modal-title">编辑成员信息</h3>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">姓名：</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" v-model="editInfo.user_name" placeholder="请输入社团成员姓名">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">职位：</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" v-model="editInfo.comm_worker" placeholder="请输入社团成员职位">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">加入社团时间：</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" v-model="editInfo.create_date" placeholder="请输入社员加入社团时间">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">个人简介：</label>
                                    <div class="col-sm-9">
                                        <input type="textarea" class="form-control" v-model="editInfo.comm_remark" placeholder="请输入社团成员简介">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal" style="margin:0px">取消</button>
                            <button type="button" class="btn btn-primary" data-dismiss="modal" style="margin-top: 0px" @click="checkInfo">确认</button>
                        </div>
                    </div>
                </div>
            </div>
            <div id="messages" class="tab-pane fade">
                <h3>活动信息管理</h3>
                <div class="form-group commGroup">
                    <label class="control-label">社团活动：</label>
                    <select class="form-control box" @change="changeProduct($event)">
                        <option value="1">请选择</option>
                        <option v-for="(p,index) in activityList" :key="index" :value="p.activityId">{{p.activityName}}</option>
                    </select>
                    <button @click="search" style="margin-left: 20px;margin-bottom: 10px;width: 50px;height: 30px;background-color: #528fc6">查询</button>
                </div>
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">活动标题：</label>
                        <div class="col-sm-9">
                            <input type="textarea" class="form-control" v-model="activityInfo.activityName" placeholder="请输入社团活动标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">举办社团：</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" v-model="activityInfo.commName" placeholder="请输入举办此活动社团名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">活动时间：</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" v-model="activityInfo.activityDate" placeholder="请输入社团活动时间">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">活动地点：</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" v-model="activityInfo.activityAddr" placeholder="请输入社团活动时间">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">活动上限人数：</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" v-model="activityInfo.activityPersionNum" placeholder="请输入社团活动上限人数">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">活动详情：</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" rows="8" v-model="activityInfo.activityInfo" placeholder="请输入社团活动简介"></textarea>
                        </div>
                    </div>
                </form>
                <button type="button" class="btn btn-primary" @click="changeActivity" style="margin-left: 50%;transform: translateX(-50%)">确认修改</button>
            </div>
            <div id="settings" class="tab-pane fade">
                <h3>信息审核</h3>
                <div class="panel panel-default">
                    <div class="panel-heading">审核申请</div>
                    <ul class="list-group" v-for="(p,index) in auditList" :key="index">
                        <li class="list-group-item">姓名：{{p.create_login_name}}</li>
                        <li class="list-group-item">申请类型：{{p.appro_name}}</li>
                        <li class="list-group-item">申请时间：{{p.create_date}}</li>
                        <li class="list-group-item" v-if="p.appro_info">申请理由：{{p.appro_info}}</li>
                        <li class="list-group-item">
                            <div style="display: flex;flex-direction: row-reverse">
                                <div>
                                    <button type="button" class="btn btn-primary" style="width: 70px;margin-bottom: 0px;margin-top: 0px;margin-left: 20px" @click="refuseAppro(p.appro_id)" type="button" class="btn btn-primary btn-lg btn btn-link" data-toggle="modal" data-target="#refuse">拒绝</button>
                                </div>
                                <div>
                                    <button type="button" class="btn btn-primary" style="width: 70px;margin-bottom: 0px;margin-top: 0px" @click="agreeAppro(p.appro_id)" type="button" class="btn btn-primary btn-lg btn btn-link" data-toggle="modal" data-target="#agree">同意</button>
                                </div>
                            </div>
                        </li>
                    </ul>
                    <%--同意理由--%>
                    <div class="modal fade" id="agree" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="margin-bottom: 0px"><span aria-hidden="true">&times;</span></button>
                                    <h3 class="modal-title">提示</h3>
                                </div>
                                <div class="modal-body">
                                    <form>
                                        <label control-label style="display: inline-block">请输入同意申请理由：</label>
                                        <textarea class="form-control" rows="2" v-model="agreeReasion" style="width: 400px;margin-left: 130px"></textarea>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal" style="margin:0px">取消</button>
                                    <button type="button" class="btn btn-primary" data-dismiss="modal" style="margin-top:0px" @click="agree">确认</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%--拒绝理由--%>
                    <div class="modal fade" id="refuse" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="margin-bottom: 0px"><span aria-hidden="true">&times;</span></button>
                                    <h3 class="modal-title">提示</h3>
                                </div>
                                <div class="modal-body">
                                    <form>
                                        <label control-label style="display: inline-block">请输入拒绝申请理由：</label>
                                        <textarea class="form-control" rows="2" v-model="refuseReasion" style="width: 400px;margin-left: 130px"></textarea>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal" style="margin:0px">取消</button>
                                    <button type="button" class="btn btn-primary" data-dismiss="modal" style="margin-top:0px" @click="refuse">确认</button>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
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
    var manage=new Vue({
        el: '#manage',
        data: {
            Id: '',  //社团id
            activityId: '', //社团活动Id
            commClassId: '', //社团分类ID
            commInfo: {
                commName: '',
                bossName: '',
                commInfo: '',
                commSpecialAct: ''
            },
            activityInfo: {
                commName: '',
                activityName: '',
                activityDate: '',
                activityAddr: '',
                activityPersionNum: '',
                activityInfo: '',
                activitySignDate: ''
            },
            activityList: [], //活动列表
            memberInfo: [], //社团成员信息
            editorId: '', //编辑成员信息ID
            editInfo: {},
            auditList: [], //审核信息列表
            agreeReasion: '', //同意申请理由
            refuseReasion: '', //拒绝申请理由
            agreeId: '',
            refuseId: ''
        },
        mounted: function () {

            var url = location.search;
            if (url.indexOf("?") != -1) {
                var str = url.substr(1);
                strs = str.split(/[=&]/);
                console.log(strs)
                this.Id = strs[1]
                this.commClassId = strs[3]

                axios.post('/community/commInfo', {commId: this.Id})
                    .then((response) => {
                    this.commInfo = response.data

            })
            .
                catch(function (error) {
                    console.log(error);
                });
            }

            axios.post('/activity/findAll', {commId: this.Id})
                .then((response) => {
                this.activityList = response.data
                console.log('修改社团信息成功')
            console.log(response.data)

        })
        .
            catch(function (error) {
                console.log(error);
            });
            /*查询审核信息*/
            axios.post('/appro/list', {approStatus: '0'})
                .then((response) => {
                console.log('审核信息')
            console.log(response.data)
            this.auditList = response.data
        })
        .
            catch(function (error) {
                console.log(error);
            });

            /*查询社团成员信息*/
            axios.post('/community/findMemberAll', {commId: this.Id})
                .then((response) => {
                console.log('社团成员信息')
            console.log(response.data)
            this.memberInfo = response.data
        })
        .
            catch(function (error) {
                console.log(error);
            });

        },
        methods: {
            editCommInfo() {
                let params = {
                    commName: this.commInfo.commName,
                    bossName: this.commInfo.bossName,
                    commInfo: this.commInfo.commInfo,
                    commSpecialAct: this.commInfo.commSpecialAct,
                    commId: this.Id,
                    commClassId: this.commClassId
                }
                swal('修改社团信息成功')
                /*发送修改社团信息请求*/
                axios.post('/community/commUpdate', params)
                    .then((response) => {
                    console.log('修改社团信息成功')
            })
            .
                catch(function (error) {
                    console.log(error);
                });
            },
            changeProduct(event) {
                this.activityId = event.target.value
            },
            /*确认修改社团活动信息*/
            changeActivity() {
                let params = {
                    commId: this.Id,
                    activityId: this.activityId,
                    commName: this.commInfo.commName,
                    activityName: this.activityInfo.activityName,
                    activityDate: this.activityInfo.activityDate,
                    activityAddr: this.activityInfo.activityAddr,
                    activitySignDate: this.activityInfo.activitySignDate,
                    activityInfo: this.activityInfo.activityInfo,
                    activityPersionNum: this.activityInfo.activityPersionNum
                }
                axios.post('/activity/update', params)
                    .then((response) => {
                    console.log('修改社团活动信息成功')
                    swal('修改社团活动信息成功')
            })
            .
                catch(function (error) {
                    console.log(error);
                });
            },
            /*查询社团详细信息*/
            search() {
                let params = {
                    activityId: this.activityId
                }
                axios.post('/activity/info', params)
                    .then((response) => {
                    this.activityInfo = response.data
            })
            .
                catch(function (error) {
                    console.log(error);
                });
            },
            /*删除社团成员*/
            deleteMember(value, name) {
                axios.post('/community/memberDel', {commId: value, dealLoginId: name})
                    .then((response) => {
                    console.log('删除成员信息')
                    swal('删除成员信息')
            })
            .
                catch(function (error) {
                    console.log(error);
                });
            },
            /*编辑社团成员信息*/
            editMember(id, name, index) {
                this.editorId = name
                this.editInfo = this.memberInfo[index]
            },
            /*编辑确认按钮*/
            checkInfo() {
                console.log('确认编辑')
                let params = {
                    commId: this.Id,
                    dealLoginId: this.editorId,
                    user_name: this.editInfo.user_name,
                    comm_worker: this.editInfo.comm_worker,
                    create_date: this.editInfo.create_date,
                    comm_remark: this.editInfo.comm_remark
                }
                axios.post('/community/memberUpdate', params)
                    .then((response) => {
                    console.log('编辑成员信息')
            })
            .
                catch(function (error) {
                    console.log(error);
                });
            },
            /*同意申请*/
            agreeAppro(id) {
                this.agreeId = id
            },
            agree() {
                let params = {
                    approId: this.agreeId,
                    approStatus: 1,
                    approRemark: this.agreeReasion
                }
                axios.post('/appro/deal', params)
                    .then((response) => {
                    console.log('同意申请')
                    swal('已同意该申请')
            })
            .
                catch(function (error) {
                    console.log(error);
                });
            },
        /*拒绝申请*/
        refuseAppro(id) {
            this.refuseId = id
        },
        refuse() {
            let params = {
                approId:this.refuseId,
                approStatus:2,
                approRemark:this.refuseReasion
            }
            axios.post('/appro/deal',params)
                .then((response)=>{
                console.log('拒绝申请')
                swal('已拒绝该申请')
            })
            .catch(function (error) {
                    console.log(error);
                });
            },
        }
    })
</script>
</html>
