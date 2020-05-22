var example1 = new Vue({
    el: '#comm-list',
    data: {
        items: [
            { commClassName: '科技创新' },
        ]
    }
})


// 定义一个名为 comm-info 的新组件
Vue.component('comm-info', {
    props: ['item','communitys'],
    template: '<div><div><h2>{{ item.commClassName }}<h2></div>'+
        '    <div v-for="item in communitys">' +
        '        <h3>{{ item.commName }} <a >申请加入</a></h3>' +
        '        <p>理事长: {{ item.bossName }}</p>' +
        '        <p>简介: {{ item.commInfo }}</p>' +
        '        <p>特色活动: {{ item.commSpecialAct }}</p>' +
        '    </div> <p>000</p>' +
        '<div>'
    //  template: '<button v-on:click="count++">You clicked me {{ count }} times.</button>'

})

var example2= new Vue({
    el: '#comm-info',
    data: {
        items: [
            {   commClassId: 0,
                commClassName: '科技创新',
                communitys:[
                    {
                    commName: '网络协会',
                    bossName: '张三',
                    commInfo:'123',
                    commSpecialAct:'特色活动'
                }
            ]
            },
        ]
    }
})

//init();

function init(){

$.ajax({
    url:"community/index", //请求的url地址
    dataType:"json", //返回格式为json
    async:true,//请求是否异步，默认为异步，这也是ajax重要特性
    data:{"id":"value"}, //参数值
    type:"GET", //请求方式
    beforeSend:function(){
        //请求前的处理
    },
    success:function(req){
        //req=JSON.parse(req.value)
        example1.items=req;
        example2.items=req;
    },
    complete:function(){
        //请求完成的处理
    },
    error:function(){
        //请求出错处理
    }
});
}

function testinterface(){
//loginName:"333",
    var testdata={loginName:"xxx",userName:"test",studentId:"123123",
        sex:"男",inDate:"2019",college:"计算机",specially:"软件",
        phone:"123456",address:"1#23",email:"9070@qq.com",
        passwdOld:"333",passwdNew:"123",commId:"100000",activityId:"100001",
        commName:"网络协会2",commInfo:"test",commClassId:"100000",approInfo:"测试",
        approType:"22",activityName:"测试活动",activityDate:"20200417",activityAddr:"402"
        ,approId:"100003",approStatus:"1",
        page:{pageSize:"10",pageCurrent:"1"}

    }
    var testdata2=JSON.stringify(testdata);
    console.log(testdata2)
    $.ajax({
        url:"/appro/memberAdd", //请求的url地址
        contentType: 'application/json;charset=UTF-8',
        dataType:"json", //返回格式为json
        async:true,//请求是否异步，默认为异步，这也是ajax重要特性
        data:JSON.stringify(testdata), //参数值
        type:"POST", //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(req){
            console.log(req);
        },
        complete:function(){
            //请求完成的处理
        },
        error:function(){
            //请求出错处理
        }
    });

}

function singleReturn(){
    var img_id="";
    if (event.target==undefined){
        img_id=event.srcElement;
    }else{
        img_id=event.target;
    }

    var commid=img_id.getAttribute("commid");
    //console.log(commid)
}

testinterface()
