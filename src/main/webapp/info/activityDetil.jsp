<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动详情</title>
<link href="../../../../../shetuan@20200506/src/main/webapp/css/bootstrap.min.css" rel="stylesheet" type="text/css" media="all" />
<script src="../js/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue"></script>
<link href="/css/activityDetil.css" type="text/css" rel="stylesheet" />
 <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<div id="acInfo">
   <h1 class="infoTitle">{{activityInformation.activityName}}</h1>
   <div class="container">
      <p>举办社团：<span>{{activityInformation.commName}}</span></p>
      <p>活动时间：<span>{{activityInformation.activityDate}}9：00</span></p>
      <p>活动地点：<span>{{activityInformation.activityAddr}}</span></p>
      <p>活动上限人数：<span>{{activityInformation.activityPersionNum}}人</span></p>
      <p>当前报名人数：<span>{{activityInformation.activityPersionNow}}人</span></p>
      <p>活动详情：<span>{{activityInformation.activityInfo}}</span></p>
   </div>
   <button type="button" class="btn btn-primary" style="background-color: #2e6da4;margin-left: 50%">申请参加活动</button>
</div>
</body>
<script>
    $(document).ready(function() {
        function GetRequest() {
            var url = location.search;
            if (url.indexOf("?") != -1) {
                var str = url.substr(1);
                strs = str.split("=");
                var testdata={activityId:strs[1]}
                $.ajax({
                    url:"/activity/info", //请求的url地址
                    contentType: 'application/json;charset=UTF-8',
                    dataType:"json", //返回格式为json
                    async:true,//请求是否异步，默认为异步，这也是ajax重要特性
                    data:JSON.stringify(testdata), //参数值
                    type:"POST", //请求方式
                    beforeSend:function(){
                        //请求前的处理
                    },
                    success:function(req){
                        acInfo.activityInformation = req
                        console.log(req)
                    },
                    complete:function(){
                        //请求完成的处理
                    },
                    error:function(){
                        //请求出错处理
                    }
                });
            }
        }
        GetRequest()
    })
    var acInfo = new Vue({
        el:'#acInfo',
        data:{
         activityInformation:{}
        },

    })
</script>
</html>