<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/common/common.jsp" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="${ctx}/resources/mallcss/initcss.css" rel="stylesheet">
    <link href="${ctx}/resources/mallcss/common.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <style>

        .con {
            background-color: #FEFFFE;
            position: relative;
            height: auto;
        }


        .info{
            margin: 2em 0 0 1em;
            color: #969796;
        }

        .form{
            margin-top: 2em;
        }

        .code{
            margin: 0 1em 1em 1em;
            font-size: 1.4em;
            color: #969796;
            border-bottom: 1px solid #E5E6E5;
            padding-bottom: 0.8em;

        }

        .code span{
            margin-right: 2em;

        }

        .password{
            margin: 0 1em 1em 1em;
            font-size: 1.3em;
        }
        .password div{
            /*margin-left: 3em;*/
            /*margin-right: 1em;*/
            float: right;
            width: 60%;
            display: inline-block;
            padding-left: 0.2em;
            padding-bottom: 0.1em;
            border-bottom: 1px solid #E5E6E5;
        }

        .password .old,.password .new,.password .confirm{
            outline: none;
            display: inline-block;
            font-size: 1em;

            width: 6em;
            /*border-bottom: 1px solid red;*/
        }


        .btndiv{
            text-align: center;
            margin-top: 2.5em;
        }


        .btn{
            width: 80%;
            margin: 0 auto;
        }




    </style>
    <title>修改密码</title>
</head>
<body style="background: #FEFFFE">
<div class="con">

    <div class="info">
        请修改密码,以保护账户安全！您可以使用编号+密码登陆或者手机号+密码登陆！
    </div>

    <div class="form">
        <div class="code">
            <span>编号</span>
            <span>zmz123445</span>
        </div>

        <div class="password">
            <span>原来密码</span>
            <div>
                <input type="password" class="old" name="password" placeholder="" autofocus>
            </div>
        </div>

        <div class="password">
            <span>新的密码</span>
            <div>
                <input type="password" class="new" name="new" placeholder="" autofocus>
            </div>
        </div>

        <div class="password">
            <span>确认密码</span>
            <div>
                <input type="password" class="confirm" name="confirm" placeholder="" autofocus>
            </div>
        </div>

        <div class="btndiv">
            <button  data-loading-text="正在提交...." class="btn btn-danger">完成</button>
        </div>

    </div>


</div>

<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">

    $(function () {


        $(".btn").click(function () {
            var th = $(this);
            th.button("loading");
            var oldP =  $(".old").val();
            var newP =  $(".new").val();
            var conP =  $(".confirm").val();
            if(oldP==""){
                alert("原始密码不能为空!");
                th.button("reset");
                return;
            }

            if(newP==""){
                alert("新密码不能为空!");
                th.button("reset");
                return;
            }else if(newP.length<6){
                alert("新密码至少六位!");
                th.button("reset");
                return;
            }


            if(conP==""){
                alert("确认密码不能为空!");
                th.button("reset");
                return;
            }else if(newP!=conP){
                alert("两次密码不匹配!");
                th.button("reset");
                return;
            }

            var param={
                "newP":newP,
                "oldP":oldP,
                "conP":conP
            };

            var url = "<c:url value='/mobile/changePaw.json'/>";

            $.post(url,param,function (data) {
                if(data.flag==0){
                    alert("修改成功");
                    window.location.href = "<c:url value="/mobile/out.html"/>";
                }else {
                    alert("操作失败："+data.message);
                    th.button("reset");
                }
            }).fail( function () {
                alert("未知错误");
                th.button("reset");
            });



        });



    })


</script>


</body>
</html>