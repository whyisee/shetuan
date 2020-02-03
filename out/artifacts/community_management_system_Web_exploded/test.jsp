<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ajax传json值测试</title>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript">
        function check(){
            var name=	$("#name").val();
            var pwd=	$("#pwd").val();
            var user={name:name,pwd:pwd};

            $.ajax({
                    url:"Jsontest",
                    contentType: 'application/json;charset=UTF-8',
                    type:"post",
                    data:JSON.stringify(user),
                    //返回数据的格式
                    datatype: "json",
                    success:function(result){
                        alert(result);
                        console.log(result.pwd)
                        //alert(JSON.parse(result).pwd)
                    },
                    error:function(){
                    }
                }
            )


        }
    </script>
</head>
<body>
用户名：<input type="text" id="name"/> <br/>
密码：<input type="text" id="pwd"/>
<input type="button" value="校验" onclick="check()"/>



</body>
</html>
