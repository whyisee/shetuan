//$.session.get('funcright');
var aaa='<%=session.getAttribute("funcright")%>'
console.log(aaa)
var example1 = new Vue({
    el: '#menu-list',
    data: {
        items: [
            { menuName: '修改资料'},
            { menuName: '修改密码'}
        ]
    }
})

function upload(obj) {
    var formData = new FormData();//这里需要实例化一个FormData来进行文件上传
    var type = "file";
    formData.append(type,$("#fileName1")[0].files[0]);

    $.ajax({
        type : "POST",
        url : "/file/upload",
        data : formData,
        processData : false,
        contentType : false,
        success : function(data){
            console.log(data);
            $("#img1").attr("src","/upload/"+data)
            //$("input[name='userUrl']").val(data);
    }});
}