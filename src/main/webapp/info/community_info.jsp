<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>社团详情</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Exchange Education a Responsive Web Template, Bootstrap Web Templates, Flat Web Templates, Android Compatible Web Template, Smartphone Compatible Web Template, Free Web Designs for Nokia, Samsung, LG, Sony Ericsson, Motorola Web Design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- css files -->
<link href="/css/font-awesome.min.css" rel="stylesheet" type="text/css" media="all" />
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css" media="all" />
<link href="/css/chromagallery.css" rel="stylesheet" type="text/css" media="all" />
<link href="/css/info.css" rel="stylesheet" type="text/css"   />
<!-- /css files -->
<!-- fonts -->
<link href='//fonts.googleapis.com/css?family=Open+Sans:400,300,600,700,800' rel='stylesheet' type='text/css'>
<link href='//fonts.googleapis.com/css?family=Viga' rel='stylesheet' type='text/css'>
<!-- /fonts -->
<link rel="icon" href="/images/pande.png">
<script src="../js/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue"></script>
</head>
<body id="index.html" data-spy="scroll" data-target=".navbar" data-offset="60">
<!-- About Section -->
<div id="communityInfo">
<section class="about-us">
	<h3 class="text-center slideanim">{{commInfoContent.commName}}</h3>
	<div class="infoInformation">
		<p class="text-center">社团详情</p>
		<div class="container">
			<div class="row">
				<div class="col-lg-6 col-md-6">
					<p><span>理事长：</span>{{commInfoContent.bossName}}</p>
					<p><span>创建时间：</span>{{commInfoContent.createDate}}</p>
					<p><span>特色活动：</span>{{commInfoContent.commSpecialAct}}</p>
					<p><span>报名参加：</span>{{commInfoContent.createPersionId}}</p>
				</div>
				<div class="col-lg-6 col-md-6">
					<p><span>社团简介：</span>{{commInfoContent.commInfo}}</p>
				</div>
			</div>
		</div>
	</div>
	<p class="text-center slideanim">社团管理员信息</p>
	<div class="container">
		<div class="row">
			<div class="col-lg-6 col-md-6" v-for="(p,index) in manageInfo" :key="index">
				<div class="about-details">
					<div class="row">
						<div class="col-sm-2 col-xs-12">
							<img src="/header/about-img1.jpg" class="img-responsive slideanim" alt="about-img">
						</div>
						<div class="col-sm-10 col-xs-12">
							<div class="about-info slideanim">
								<h5>{{p.comm_name}}</h5>
								<p>会长：{{p.comm_remark}}</p>
							</div>
						</div>
					</div>						
				</div>
			</div>
		</div>
	</div>
	<p class="text-center slideanim">社团活动</p>
	<div class="container activity-title" v-for="(activity,index) in activityTitle" :key="index">
		<a :href="'activityDetil.jsp?id='+activity.id" target="_blank">{{activity.title}}</a>
	</div>
</section>
<!-- /About Section -->	
</div>
<!-- js files -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/SmoothScroll.min.js"></script>
<!-- js files for gallery -->
<script src="js/chromagallery.pkgd.min.js"></script>
	<%--<script type="text/javascript">
		$(document).ready(function() 
		{
		    $(".mygallery").chromaGallery();
		});
	</script>--%>
<!-- /js files for gallery -->	
<!-- Back To Top -->
<script src="../js/backtotop.js"></script>
<!-- /Back To Top -->

<script>
    $(document).ready(function() {
            function GetRequest() {
            var url = location.search;
            if (url.indexOf("?") != -1) {
                var str = url.substr(1);
                strs = str.split("=");
                console.log(strs[1]);
                var testdata={commId:strs[1]}
                var testDataSecond={commId:strs[1]}
                /*社团详细信息请求*/
                $.ajax({
                    url:"/community/commInfo", //请求的url地址
                    contentType: 'application/json;charset=UTF-8',
                    dataType:"json", //返回格式为json
                    async:true,//请求是否异步，默认为异步，这也是ajax重要特性
                    data:JSON.stringify(testdata), //参数值
                    type:"POST", //请求方式
                    beforeSend:function(){
                        //请求前的处理
                    },
                    success:function(req){
                        info.commInfoContent=req
                        console.log(info.commInfoContent)
                    },
                    complete:function(){
                        //请求完成的处理
                    },
                    error:function(){
                        //请求出错处理
                    }
                });
                /*社团主要成员请求*/
                $.ajax({
                    url:"/community/findMemberShow", //请求的url地址
                    contentType: 'application/json;charset=UTF-8',
                    dataType:"json", //返回格式为json
                    async:true,//请求是否异步，默认为异步，这也是ajax重要特性
                    data:JSON.stringify(testDataSecond), //参数值
                    type:"POST", //请求方式
                    beforeSend:function(){
                        //请求前的处理
                    },
                    success:function(req){
                        info.manageInfo=req
						console.log(req)
                    },
                    complete:function(){
                        //请求完成的处理
                    },
                    error:function(){
                        //请求出错处理
                    }
                });
                /*社团活动标题请求*/
                $.ajax({
                    url:"/activity/findAll", //请求的url地址
                    contentType: 'application/json;charset=UTF-8',
                    dataType:"json", //返回格式为json
                    async:true,//请求是否异步，默认为异步，这也是ajax重要特性
                    data:JSON.stringify(testDataSecond), //参数值
                    type:"POST", //请求方式
                    beforeSend:function(){
                        //请求前的处理
                    },
                    success:function(req){
                        var commName = info.commInfoContent.commName
                        req.filter((value,index)=>{
                            info.activityTitle.push({
                            'title': value.activityName,
                            'id':value.activityId
                        })
                        })

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
	var info = new Vue({
		el:'#communityInfo',
		data:{
			commInfoContent:{},
			manageInfo:[],
			activityTitle:[],
			activityId:''
		},
		mounted:{

		}
	})

</script>


<script>
$(document).ready(function(){
  // Add smooth scrolling to all links in navbar + footer link
  $(".navbar a, footer a[href='#myPage']").on('click', function(event) {

  // Store hash
  var hash = this.hash;

  // Using jQuery's animate() method to add smooth page scroll
  // The optional number (900) specifies the number of milliseconds it takes to scroll to the specified area
  $('html, body').animate({
    scrollTop: $(hash).offset().top
  }, 900, function(){

    // Add hash (#) to URL when done scrolling (default click behavior)
    window.location.hash = hash;
    });
  });
})
</script>
<script>
$(window).scroll(function() {
  $(".slideanim").each(function(){
    var pos = $(this).offset().top;

    var winTop = $(window).scrollTop();
    if (pos < winTop + 600) {
      $(this).addClass("slide");
    }
  });
});
</script>
<!-- /js files -->
</body>
</html>