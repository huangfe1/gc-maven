<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/common/common.jsp" %>
<!doctype html>
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
        }

        header {
            background: #f03791;
            height: 3.7em;
            text-align: center;
            position: relative;
        }

        .Return, .Home {
            position: absolute;
            width: 3.7em;
            height: 3.7em;
        }

        .Return {
            left: 0;
        }

        .Home {
            right: 0;
        }

        .Return span, .Home span {
            background: url("${ctx}/resources/mallimages/png.png");
            background-size: 17em;
            position: absolute;
            width: 1.7em;
            height: 1.7em;
        }

        .Return span {
            background-position: -0.17em -5.5em;
            left: 1em;
            top: 1em;
        }

        .Home span {
            background-position: -0.17em -8.1em;
            left: 1em;
            top: 1em;
        }

        .Title {
            color: white;
            font-size: 1.3em;
            line-height: 2.7em;
        }

        .balance{
            background-color: #676d73;
            overflow: hidden;
            padding-top: 0.5em;
        }

        .balance .wxq{
            float: left;
            width: 33%;
        }
        .djq{
            padding-bottom: 0.5em;
        }

        .wxq .img {
            text-align: center;
        }
        .wxq .img img{
            width: 3em;
            padding: 1em 0 0.5em 0;
        }

        .wxq h4,p{
            text-align: center;
            color: white;
        }
        .wxq p{
            color: #a8afb4;
            padding-top: 0.5em;
        }

        .server{
            background-color: #ffffff;
            overflow: hidden;
            border-bottom: 1px solid #e8e8e8;

        }

        .server>h4{
            background-color: #efeef2;
            line-height: 3em;
            color: #848484;
            padding-left: 1em;
        }

        .item{

            float: left;
            width:33%;
            text-align: center;
            color: #848484;
            padding: 1.5em 0;
            border: solid #e8e8e8;
            border-width: 0 0 1px 1px;
            margin-left: -1px;
        }

        .item img{
            width: 2em;
            padding-bottom: 0.5em;
        }

        .wxq{
            cursor: pointer;
        }


    </style>
    <title>钱包</title>
</head>
<body style="background: rgb(232, 232, 232);">
<div class="con">
    <%--<header>--%>
    <%--<a href="" class="Return"><span></span></a>--%>
    <%--<span class="Title">我的钱包</span>--%>
    <%--<a href="" class="Home"><span></span></a>--%>
    <%--</header>--%>
    <div class="balance">
        <div class="yck wxq">
            <div class="img">
                <img src="${ctx}/resources/mallimages/wxq.png" alt="">
            </div>
            <h4>预存款</h4>
            <p>￥${advance}</p>
        </div>

        <div class="djq wxq">
            <div class="img" >
                <img src="${ctx}/resources/mallimages/wxdj.png" alt="">
            </div>
            <h4>奖金</h4>
            <p>￥${voucher}</p>
            <%--<p>今:￥${todayVoucher}</p>--%>
            <!--<p>月:￥2339</p>-->

        </div>
        <div class="jhe wxq">
            <div class="img">
                <img src="${ctx}/resources/mallimages/wxq.png" alt="">
            </div>
            <h4>总业绩</h4>
            <p>￥${benefit}</p>
            <%--<p>直属:￥${sumB}</p>--%>
        </div>
    </div>



    <div class="server">
        <h4>高臣药业服务</h4>
        <div class="items">
            <div class="item" id="dlsc">
                <img src="${ctx}/resources/mallimages/fx.png" alt="">
                <h4> 商城</h4>
            </div>

            <%--<div class="item" id="yhsc">--%>
                <%--<img src="${ctx}/resources/mallimages/yhsc.png" alt="">--%>
                <%--<h4> 优惠商城</h4>--%>
            <%--</div>   --%>

            <div class="item" id="tx">
                <img src="${ctx}/resources/mallimages/tx.png" alt="">
                <h4> 提现</h4>
            </div>

            <div class="item" id="zhcz">
                <img src="${ctx}/resources/mallimages/djqcz.png" alt="">
                <h4>充值</h4>
            </div>

            <%--<div class="item">--%>
            <%--<img src="${ctx}/resources/mallimages/mry.png" alt="">--%>
            <%--<h4> 美容院</h4>--%>
            <%--</div>--%>
            <%--<div class="item">--%>
            <%--<img src="${ctx}/resources/mallimages/qygs.png" alt="">--%>
            <%--<h4> 区域公司</h4>--%>
            <%--</div>--%>
            <%--<div class="item">--%>
            <%--<img src="${ctx}/resources/mallimages/hzyd.png" alt="">--%>
            <%--<h4> 合作药店</h4>--%>
            <%--</div>--%>
        </div>
    </div>



    <div class="server">
        <h4>第三方服务</h4>
        <div class="items">
            <div class="item">
                <img src="${ctx}/resources/mallimages/chel.png" alt="">
                <h4>吃喝玩乐</h4>
            </div>

            <div class="item">
                <img src="${ctx}/resources/mallimages/ty.png" alt="">
                <h4> 线下体验店</h4>
            </div>
            <div class="item">
                <img src="${ctx}/resources/mallimages/hzjd.png" alt="">
                <h4> 合作酒店</h4>
            </div>
            <div class="item">
                <img src="${ctx}/resources/mallimages/mry.png" alt="">
                <h4> 美容院</h4>
            </div>
            <div class="item">
                <img src="${ctx}/resources/mallimages/qygs.png" alt="">
                <h4> 区域公司</h4>
            </div>
            <div class="item">
                <img src="${ctx}/resources/mallimages/hzyd.png" alt="">
                <h4> 合作药店</h4>
            </div>
        </div>
    </div>


</div>
</div>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script>
    $(function () {

        var url = "<c:url value="/mobile/accounts/records.html"/>"

        $(".yck").click(function () {
            url+="?stateType=1";
            window.location.href=url;
        });

        $(".djq").click(function () {
            url+="?stateType=0";
            window.location.href=url;
        });

        $(".jhe").click(function () {
            url+="?stateType=2";
            window.location.href=url;
        });

        $("#tx").click(function () {
            url = "<c:url value='/mobile/withdraw.html'/>";
            window.location.href=url;
        })

        //商城
        $("#dlsc").click(function () {
            <c:if test="${user.agentCode ==null || user.agentCode eq ''}">
            alert("请先完善信息！");
            </c:if>
            <c:if test="${user.agentCode !=null && user.agentCode ne ''}">
            window.location.href="<c:url value="/dmz/mobile/index.html"/>";
            </c:if>

        });

        $("#zhcz").click(function () {
            <c:if test="${user.agentCode ==null || user.agentCode eq ''}">
            alert("请先完善信息！");
            </c:if>
            <c:if test="${user.agentCode !=null && user.agentCode ne ''}">
            window.location.href="<c:url value="/mobile/accounts/recharge.html"/>";
            </c:if>
        })

        //优惠商城
        $("#yhsc").click(function () {
            <%--window.location.href="<c:url value="/dmz/pmall/show.html"/>";--%>
        })

    })
</script>

</body>
</html>