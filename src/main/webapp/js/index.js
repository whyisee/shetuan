

var example1 = new Vue({
    el: '#comm-list',
    data: {
        items: [
            { commClassName: '科技创新' },
        ]
    }
})
// 定义一个名为 button-counter 的新组件
Vue.component('button-counter', {
    props: ['item'],
    template: '<div><div><h3>{{ item.commClassName }}<h3></div>'+
        '<div>' +
        '理事长:' +'{{ item.commClassName }}'+
        '<div>'+
        '<div>'
    //  template: '<button v-on:click="count++">You clicked me {{ count }} times.</button>'

})

var example2= new Vue({
    el: '#components-demo',
    data: {
        items: [
            { commClassId: 0, commClassName: '科技创新' },
        ]
    }
})





$.ajax({
    url:"/community/index", //请求的url地址
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



