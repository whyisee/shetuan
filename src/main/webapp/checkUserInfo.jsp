<%--
  Created by IntelliJ IDEA.
  User: 邹慧琴
  Date: 2020/5/24
  Time: 23:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <script src="js/jquery.min.js"></script>
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" media="all" />
    <script src="js/backtotop.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/vue.min.js"></script>
    <script src="js/axios.min.js"></script>
    <title>查看个人信息</title>
    <style type="text/css">
        body{
            background-color: #F8F8FF;
        }
        h1{
            height: 80px;
            width: 100%;
            margin-top: 60px;
            margin-left: 45%;
        }
        .list-group{
            width: 50%;
            margin: 0 auto;
        }
        .form-group{
            display: flex;
            flex-direction: row;
        }
        .title{
            width: 100px;
        }
        .input{
            width: 400px;
        }
    </style>
</head>
<body>
<h1>个人信息</h1>
<div class="list-group" id="userInfo">
    <button type="button" class="list-group-item">
        <div class="form-group">
            <label class="control-label title">用户名：</label>
            <div>
                <input type="text" class="form-control input" v-model="infoList.loginName" placeholder="请输入用户名">
            </div>
        </div>
    </button>
    <button type="button" class="list-group-item">
        <div class="form-group">
            <label class="title control-label">电话号码：</label>
            <div>
                <input type="text" class="form-control input" v-model="infoList.phone" placeholder="请输入电话号码">
            </div>
        </div>
    </button>
    <button type="button" class="list-group-item">
        <div class="form-group">
            <label class="title control-label">电子邮箱：</label>
            <div>
                <input type="text" class="form-control input" v-model="infoList.email" placeholder="请输入电子邮箱">
            </div>
        </div>
    </button>
    <button type="button" class="list-group-item">
        <div class="form-group">
            <label class="control-label title">家庭住址：</label>
            <div>
                <input type="textarea" class="form-control input" v-model="infoList.address" placeholder="请输入家庭住址">
            </div>
        </div>
    </button>
    <button type="button" class="list-group-item">
        <div class="form-group">
            <label class="control-label title">自我介绍：</label>
            <div>
                <textarea class="form-control input" v-model="infoList.specially" placeholder="请输入自我介绍" row="4"></textarea>
            </div>
        </div>
    </button>
    <button type="button" class="list-group-item" @click="save">
        <label style="margin-left: 300px">保存</label>
    </button>
</div>
</body>
<script>
    var info = new Vue({
        el:'#userInfo',
        data:{
            infoList:{},
            loginName:''
        },
        mounted:function () {
            var url = location.search;
            if (url.indexOf("?") != -1) {
                var str = url.substr(1);
                strs = str.split('=');
                this.loginName=strs[1]
                console.log(strs)

                axios.post('/member/getInfo', {loginName: this.loginName})
                    .then((response) => {
                    console.log('查询个人信息成功')
                    console.log(response)
                    this.infoList=response.data
            }).
                catch(function (error) {
                    console.log(error);
                });
            }
        },
        methods:{
            save(){
                let params = {
                    loginName:this.infoList.loginName,
                    phone:this.infoList.phone,
                    email:this.infoList.email,
                    address:this.infoList.address,
                    specially:this.infoList.specially
                }
                axios.post('/member/update', params)
                    .then((response) => {
                    console.log('修改个人信息成功')
                    swal("修改个人信息成功");
            }).
                catch(function (error) {
                    console.log(error);
                });
            }
        },
    })
</script>
</html>
