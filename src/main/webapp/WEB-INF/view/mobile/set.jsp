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
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <style>

        .con {
            background-color: #ebebeb;
            position: relative;
            height: auto;
        }

        .list{
            background-color: #FEFFFE;
            margin-top: 1.2em;
            font-size: 1.1em;
        }

        .list ul{
            padding: 0em 0.8em;
        }


        .list ul li{
            padding: 0.9em 0.5em;
            border-bottom: 1px solid #E7E8E7;
        }

        .list ul li:last-child{
            border: none;
        }


    </style>
    <title>设置</title>
</head>
<body style="background: rgb(232, 232, 232);">
<div class="con">

    <div class="list">
        <ul>
            <li id="cp">修改密码</li>
            <!--<li>注销登陆</li>-->
        </ul>
    </div>

    <div class="list">
        <ul>
            <li>关于我们</li>
            <li>意见反馈</li>
        </ul>
    </div>

    <div class="list">
        <ul>
            <!--<li>修改密码</li>-->
            <li class="zx">注销登陆</li>
        </ul>
    </div>


</div>

<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="http://apps.bdimg.com/libs/jquery-qrcode/1.0.0/jquery.qrcode.min.js"></script>
<script type="text/javascript">

    $(function () {

        $("#cp").click(function () {
            var url = "<c:url value='/mobile/changePaw.html'/>";
                window.location.href = url;
        });

        //注销按钮
        $(".zx").click(function () {
            if (confirm("将取消当前账号与微信的绑定，是否继续！")) {
                window.location.href = "<c:url value='/mobile/out.html'/>";
            }
        })


    })


</script>


</body>
</html>