<%--
  Created by IntelliJ IDEA.
  User: 邹慧琴
  Date: 2020/5/25
  Time: 0:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="js/jquery.min.js"></script>
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" media="all" />
    <script src="js/backtotop.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/vue.min.js"></script>
    <script src="js/axios.min.js"></script>
    <title>修改密码</title>
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
<h1>修改密码</h1>
<div class="list-group" id="code">
    <button type="button" class="list-group-item">
        <div class="form-group">
            <label class="control-label title">旧密码：</label>
            <div>
                <input type="text" class="form-control input" v-model="code" placeholder="请输入旧密码">
            </div>
        </div>
    </button>
    <button type="button" class="list-group-item">
        <div class="form-group">
            <label class="control-label title">新密码：</label>
            <div>
                <input type="text" class="form-control input" v-model="newCode" placeholder="请输入新密码密码">
            </div>
        </div>
    </button>
    <button type="button" class="list-group-item" @click="save">
        <label style="margin-left: 300px">保存</label>
    </button>
</div>
</body>
<script>
    var code = new Vue({
        el:'#code',
        data:{
            code:'',
            newCode:''
        },
        mounted:function () {

        },
        methods:{
            save(){
                let params = {
                    passwdOld:this.code,
                    passwdNew:this.newCode
                }
                axios.post('/member/updatePWD', params)
                    .then((response) => {
                    console.log('修改密码成功')
                }).
                catch(function (error) {
                    console.log(error);
                });
            }
        }
    })
</script>
</html>
