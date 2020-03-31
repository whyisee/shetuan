<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <base href="<%=basePath%>">
    <title>社团生活</title>
    <link rel="icon" href="images/pande.png">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="Exchange Education a Responsive Web Template, Bootstrap Web Templates, Flat Web Templates, Android Compatible Web Template, Smartphone Compatible Web Template, Free Web Designs for Nokia, Samsung, LG, Sony Ericsson, Motorola Web Design" />
    <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
    <!-- css files -->
    <link href="css/font-awesome.min.css" rel="stylesheet" type="text/css" media="all" />
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" media="all" />
    <link href="css/chromagallery.css" rel="stylesheet" type="text/css" media="all" />
    <link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
    <style type="text/css">
        #container { width:80%; margin:30px auto; text-align:left; padding:10px; border:1px solid #ccc; height:250px; position:relative; overflow:hidden; font:16px Verdana, Geneva, sans-serif; color:#fff; text-align:left;}
        .noticeList { width:80%; list-style:inside; position:absolute; top:270px; left:10px;}
        .noticeList li { padding-bottom:5px;}
        .noticeList li a { color:#606060; text-decoration:none;}
        .noticeList li a:hover { color:#09F;}
    </style>
    <!-- /css files -->
    <!-- fonts -->
    <link href='//fonts.googleapis.com/css?family=Open+Sans:400,300,600,700,800' rel='stylesheet' type='text/css'>
    <link href='//fonts.googleapis.com/css?family=Viga' rel='stylesheet' type='text/css'>
    <!-- /fonts -->
    <!-- js files -->
    <script src="js/modernizr.custom.js"></script>
    <script src="js/jquery.min.js"></script>
    <script src="js/speech/jQuery.speech.js"></script>
    <script src="js/speech/jQuery.speech.min.js"></script>
    <script src="js/backtotop.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/SmoothScroll.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue"></script>

    <!-- /js files -->
</head>

<body id="index.html" data-spy="scroll" data-target=".navbar" data-offset="60">
<!-- Top Bar -->
<section class="top-bar">
    <div class="container">
        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <a href="#index.html" class="logo"><h1>多彩生活</h1></a>
            </div>

            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <ul class="social-icons1">

                    <c:if test="${login == null}">
                        <li class="y-in"><a href="login.jsp" style="{color:#fffff;}">登陆</a></li>
                        <li ><a href="regist.jsp" style="{color:#fffff;}">注册</a></li>
                    </c:if>
                    <c:if test="${login != null}">
                        <li class="y-in"><a href="MemberInfo?mname=${login.loginName }">欢迎：${login.loginName }</a></li>
                        <li><a href="loginOut"> | 注销</a></li>
                    </c:if>

                </ul>
            </div>

        </div>
    </div>
</section>
<!-- /Top Bar -->
<div id="myCarousel" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="1"></li>
        <li data-target="#myCarousel" data-slide-to="2"></li>
    </ol>
    <div class="carousel-inner" role="listbox">
        <div class="item active">
            <img class="first-slide" src="images/banner1.jpg" alt="First slide">
            <div class="container">
                <div class="carousel-caption">

                </div>
            </div>
        </div>
        <div class="item">
            <img class="second-slide" src="images/banner2.jpg" alt="Second slide">
            <div class="container">
                <div class="carousel-caption">

                </div>
            </div>
        </div>
        <div class="item">
            <img class="third-slide" src="images/banner3.jpg" alt="Third slide">
            <div class="container">
                <div class="carousel-caption">

                </div>
            </div>
        </div>
    </div>
    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>
<!-- Navigation Bar -->
<div class="navbar-wrapper">
    <div class="container">
        <nav class="navbar navbar-inverse navbar-static-top cl-effect-21">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">多彩社团</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div id="navbar" class="navbar-collapse collapse">


                    <ul class="nav navbar-nav" id="comm-list">
                        <li class="active"><a href="index2.jsp">首页</a></li>
                        <li v-for="item in items">
                            <a v-bind:href="'index2.jsp#comm'+item.commClassId" >{{ item.commClassName }}</a>
                        </li>
                        <li><a href="index2.jsp#notice">活动公告</a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
</div>
<!-- /Navigation Bar -->
<!-- Banner Section -->
<!-- /Banner Section -->
<!-- About Section -->
<p>111</p>
<div id="components-demo">
    <button-counter
            v-for="item in items"
            v-bind:item="item"
            v-bind:key="item.commClassId"
    >
    </button-counter>
</div>



<p>111</p>

<section class="our-services" id="comm100000">
    <h3 class="text-center slideanim">科技创新类社团</h3>
    <div class="container">
        <div class="row">
            <div class="col-lg-6 col-md-6">
                <div class="serv-details">
                    <div class="row">
                        <div class="col-sm-6 col-xs-6">
                            <img src="images/serv- chemistry.jpg" alt="" class="img-responsive slideanim">
                        </div>
                        <div class="col-sm-6 col-xs-6">
                            <div class="serv-img-details slideanim">
                                <h4><a href="info/community_info.jsp" target="_blank">网络协会</a></h4>
                                <p><a href="index.jsp">申请加入</a></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="serv-info slideanim speech">
                    <p>
                        <span>理事长：</span>张一天</br>
                        <span>简介：</span>网络协会于2002年12月成立，学术科技类社团，隶属于井冈山大学学生社团联合会，依托电子信息工程学院，由曾小荟老师担任指导老师。社团由理事长组织管理。理事会由理事长、副理事长、理事长助理构成，下设技术部、组织策划部、外联部、宣传部、网络部等部门，拥有自己的会旗。</br>
                        <span>特色活动：</span>深入的学习三大办公软件；操作系统的安装；文件资源修改； MP5固件美化
                    </p>
                </div>
            </div>
            <div class="col-lg-6 col-md-6">
                <div class="serv-details">
                    <div class="row">
                        <div class="col-sm-6 col-xs-6">
                            <img src="images/serv-geographical.jpg" alt="" class="img-responsive slideanim">
                        </div>
                        <div class="col-sm-6 col-xs-6">
                            <div class="serv-img-details slideanim">
                                <h4><a href="info/community_info.jsp" target="_blank">电子创新协会</a></h4>
                                <p><a href="index.jsp">申请加入</a></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="serv-info slideanim speech">
                    <p>
                        <span>理事长：</span>胡大勇</br>
                        <span>简介：</span>电子创新协会成立于2002年，学术科技类社团，隶属于井冈山大学学生社团联合会，依托电子与信息工程学院，由电子系主任肖开选担任指导老师，同时拥有一个以电子系老师组成的电子系顾问团。社团由理事会管理。理事会由理事长、副理事长构成。下设纪检部、技术部、宣传部、组织策划部、财务部等部门。拥有自己的会旗和实验室———十栋A区405.</br>
                        <span>特色活动：</span>参加各种电子科技竞赛,走出校外与其它协会交流经验；电器维修，旨在丰富大学生课余生活；

                        　　 电子制作，注重培养学生的自主创新能力； 经验交流会，举办交流会讨论电子专业最前沿技术。
                    </p>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-6 col-md-6">
                <div class="serv-details">
                    <div class="row">
                        <div class="col-sm-6 col-xs-6">
                            <img src="images/serv-math.jpg" alt="" class="img-responsive slideanim">
                        </div>
                        <div class="col-sm-6 col-xs-6">
                            <div class="serv-img-details slideanim">
                                <h4><a href="info/community_info.jsp" target="_blank">数学建模协会</a></h4>
                                <p><a href="index.jsp">申请加入</a></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="serv-info slideanim speech">
                    <p>
                        <span>理事长：</span>李思思</br>
                        <span>简介：</span>井冈山大学数学建模协会成立于2004年10月12日，是以“全面推广数学知识，提高学生的数学认识，增强学生的数学应用能力”为宗旨的学术科技类社团。是以数理学院为依托单位，校团委社团联合会直接领导的学生组织，由数理学院邓志云副教授担任指导老师。数学建模协会由理事会组织管理，理事会由理事长、总监、副理事长构成，下设有办公室、活动策划部、数模研究部、宣传部、学习部、信息部、财务部等部门，拥有自己的会旗和办公室（10栋C303）以及活动室10栋B区207、208、209。</br>
                        <span>特色活动：</span>邀请协会指导老师授课； 组织会员在数学实验室上机学习数学软件；组织会员观看数学电影；

                        承办由学校教务处主办的“井冈杯”数学建模竞赛；

                        组织理事成员整理协会历年档案，编辑并出版《数学建模报》；

                        为每年的全国大学生数学建模竞赛培养优秀建模人才，并代表学校参赛。
                    </p>
                </div>
            </div>
            <div class="col-lg-6 col-md-6">
                <div class="serv-details">
                    <div class="row">
                        <div class="col-sm-6 col-xs-6">
                            <img src="images/serv-img4.jpg" alt="" class="img-responsive slideanim">
                        </div>
                        <div class="col-sm-6 col-xs-6">
                            <div class="serv-img-details slideanim">
                                <h4><a href="info/community_info.jsp" target="_blank">软件协会</a></h4>
                                <p><a href="index.jsp">申请加入</a></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="serv-info slideanim speech">
                    <p>
                        <span>理事长：</span>倡导以人为本，以计算机为媒介，加强信息交流</br>
                        <span>简介：</span>软件协会于2012年11月成立，协会性质为兴趣爱好和学术研究类社团，隶属于井冈山大学学生社团联合会，依托电子与信息工程学院，由电信学院团委副书记，学生科副科长何旭梅、电信学院讲师吴兰英、电信学院讲师彭蕾担任指导老师，同时拥有一个由电信学院教师组成的专业指导团队，社团由理事会组织管理。理事会由理事长、副理事长、理事长助理构成，下设办公室、策划部、宣传部、编辑部、技术部等部门，拥有自己的实验室——十栋B509和工作室——十栋创业孵化中心梧桐树下工作室。</br>
                        <span>特色活动：</span>课程教学，由专业老师和有经验的学长学姐进行指导；ＩＴ比赛，社员将参加国家级、省级等各级大赛；
                        技术交流，社员和老师以及学长学姐进行课上和课下交流；
                        校内活动，在全校范围内开展电子竞技等各类友谊赛。
                    </p>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- /Our contact -->
<!--/notice-->
<section class="our-services" id="notice">
    <h3 class="text-center slideanim">活动公告</h3>
    <div id="container">
        <ul class="noticeList">
            <li><a href="info/activityDetil.jsp" title=""  target="_Blank">创业协会2016.5.25创客空间活动室   :针对“大众创新万众创业”活动开展相应培训</a></li>
            <li><a href="info/activityDetil.jsp" title="" target="_blank">计算机协会2016.11.6计算机科研室    :举办大学生“Oracl"甲骨文ACM竞赛</a></li>
            <li><a href="info/activityDetil.jsp" title="" target="_blank">天文地理协会 2016.10.9教学楼103 :"走进天文，探索奥秘"主题活动，讲解天体观测</a></li>
            <li><a href="info/activityDetil.jsp" title="" target="_blank">数学应用协会 2016.5.6  教学楼505 :数学课堂知识模拟大赛</a></li>
            <li><a href="info/activityDetil.jsp" title="" target="_blank">English.net协会 2016.3.20	 成栋教阶三	:“新生杯”英语演讲比赛海选赛</a></li>
            <li><a href="info/activityDetil.jsp" title="" target="_blank">English.net协会  2016.3.25 成栋教阶三	:“新生杯”英语演讲比赛决赛、颁奖仪式</a></li>
            <li><a href="info/activityDetil.jsp" title="" target="_blank">心理健康协会 2016.11.11	研楼436 :“阳光之旅”系列心理健康教育活动宣讲会</a></li>
            <li><a href="info/activityDetil.jsp" title="" target="_blank">阳光公益社	2016.4.28  幸福中心小学  :深入课堂，走进学生身边开展支教活动 </a></li>
            <li><a href="info/activityDetil.jsp" title="" target="_blank">健身协会  2016.5.6  体育馆健身馆	:普及健康知识，进行健身动作讲解</a></li>
            <li><a href="info/activityDetil.jsp" title="" target="_blank">魔术协会 2016.5.20	大学生活动中心  :魔术巅峰展演</a></li>
            <li><a href="info/activityDetil.jsp" title="" target="_blank">排球协会  2016.6.6 北区体育场  :校园排球友谊赛开幕式</a></li>
        </ul>
    </div>

</section>
<!--/notice-->
<!-- Footer Section -->
<section class="footer">
    <h2 class="text-center">THANKS FOR VISITING US</h2>
    <p>
        <a href="http://www.neau.edu.cn/" target="_blank">东北农业大学官网</a>|
        <a href="http://jwc.neau.edu.cn/" target="_blank">东北农业大学教务处</a>|
        <a href="http://nic.neau.edu.cn/" target="_blank">东北农业大学网络中心</a>
    </p><br>
    <p>QQ群号:676521839</p>
    <div id="Result">
    </div>

</section>
<!-- /Footer Section -->
<!-- Back To Top -->
<a href="#0" class="cd-top">Top</a>
<!-- /Back To Top -->
<!-- js files -->

<!-- js files for gallery -->
<script src="js/chromagallery.pkgd.min.js"></script>
<script type="text/javascript">
    $(document).ready(function()
    {
        $(".mygallery").chromaGallery();
    });
</script>
<!-- /js files for gallery -->
<!-- Back To Top -->
<!-- /Back To Top -->
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
    jQuery(function($) {
        function ScrollAction(listObj, speed, isSeries) {	//listObj为需要滚动的列表，  speed为滚动速度
            var pos, top, aniTop, height;
            var id = '';  //记录setInterval的标记id

            pos = listObj.position();
            top = pos.top;			//列表的top
            aniTop = top;				//记录当前运动时的top
            height = listObj.height();	//列表的高度

            var scrollUp = function() {
                aniTop--;
                if(!isSeries) {	//isSeries变量控制是否连续滚动，false不连续，true连续
                    if(aniTop == -height) {	//不连续，滚动玩重新滚动
                        listObj.css({'top': top});
                        aniTop = top;
                    };
                } else {
                    if(aniTop == -listObj.children().eq(0).height()) {	//连续滚动
                        var firstItem = '<li>' + listObj.children().eq(0).html() + '</li>';
                        listObj.children().eq(0).remove();
                        listObj.append(firstItem);
                        aniTop = 4;
                    };
                };
                listObj.css({'top': aniTop + 'px'});
            };

            var hover = function(id) {
                listObj.hover(function() {
                    clearInterval(id);
                }, function() {
                    id = setInterval(scrollUp, speed);
                });
            };

            this.start = function() {
                id = setInterval(scrollUp, speed);
                hover(id);
            };

        };
        var sa = new ScrollAction($('.noticeList'), 30, true);
        sa.start();
    });

    $('.speech>p').speech({
        "speech": true, //通过点击链接播报，还是直接播报
        "lang": "zh", //语言
        "speed": 3, //语速
        "sWidth": 16, //链接按钮的宽度
        "sHeight": 16, //链接按钮的高度
        "https": true, //启用https
        "bg": "./images/speech.jpg", //链接按钮的背景图片
    });
</script>
<!-- /js files -->
<!-- 弹出未登录alert -->
<c:if test="${msg==1}">
    <script type="text/javascript">
        alert("您尚未登录，请登录后查看");
        window.location.replace("login.jsp") ;

    </script>
</c:if>

<script src="/js/index.js"></script>

</body>
</html>