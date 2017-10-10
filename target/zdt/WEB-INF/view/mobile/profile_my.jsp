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
    <link href="${ctx}resources/mallcss/common.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <style>

        .con {
            background-color: #ebebeb;
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

        .info {
            background-color: #ffffff;
            overflow: hidden;
            margin-top: 1.5em;
        }

        .info div {
            float: left;
        }

        .avatar {
            height: 4.7em;
            width: 4.7em;
            padding: 0.8em 0.8em;
        }

        .avatar img {
            width: 100%;
            height: auto;
        }

        .profile {
            padding: 0.8em 0.8em;
        }

        .profile p {
            color: #a5a5a5;
        }

        .profile i {
            color: #e37c6e;
        }

        .buttons {
            padding: 0em 0.8em;
        }

        .buttons div {
            padding: 0.8em 0;
            border-radius: 4px;
            text-align: center;
            background-color: #f8f8f8;
            margin-top: 1em;
            -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .1);
        }

        .buttons div span {
            font-size: 1.1em;
        }

        .buttons div.click {

            background-color: #a1a1a1;
        }

        .buttons .chat {
            width: 100%;
            background-color: #27ab26;
            color: white;
        }

        .buttons .chat.click {
            background-color: #04be02;
        }

        .label {
            padding: 0.8em 0.8em;
            margin-top: 1em;
            background-color: #ffffff;
            overflow: hidden;
            height: 2em;
            line-height: 2em;
        }

        .label h3 {
            float: left;
            width: 3.7em;

        }

        .label span {
            margin-left: 1.6em;
            color: #a5a5a5;
            /*line-height: 4em;*/
        }

        .moreInfo {
            margin-top: 1em;
        }

        .moreInfo .label {
            margin-top: 0;
            border-bottom: 1px solid #ebebeb;
        }

        .accounts {
            margin-top: 1em;
        }

        .accounts ul li {
            background-color: #FEFFFE;
            border-bottom: 1px solid #ebebeb;
            padding: 0.5em 0.5em;
            overflow: hidden;
            position: relative;

        }

        .accounts ul li img {
            width: 4em;
            border-radius: 4em;
            float: left;
        }

        .accounts .accountsInfo {
            margin-left: 1em;
            display: inline-block;
        }

        .accounts .accountsInfo h4 {
            color: #a5a5a5;
            padding-top: 0.8em;

        }

        .accounts .accountsInfo h3 {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            max-width: 5em;

        }

        .operate {
            float: right;

        }

        .operate .inputs {

            /*float: left;*/
            display: inline-block;
            margin-right: 5em;
            line-height: 4em;

        }

        .inputs span {
            display: inline-block;
            width: 28px;
            height: 28px;
            line-height: 28px;
            text-align: center;
            font-size: 18px;
            color: #999;
            cursor: pointer;
            border: 1px solid #e6e6e6;
        }

        .inputs input {
            border: none;
            outline: none;
            font-size: 1em;
            width: 3em;
            text-align: center;
        }


    </style>
    <title>我的资料</title>
</head>
<body style="background: rgb(232, 232, 232);">
<div class="con">
    <%--<header>--%>
    <%--<a href="" class="Return"><span></span></a>--%>
    <%--<span class="Title">详细资料</span>--%>
    <%--<a href="" class="Home"><span></span></a>--%>
    <%--</header>--%>

    <div class="info">
        <div class="avatar">
            <img src="${agent.headimgurl}" alt="">
        </div>
        <div class="profile">
            <h3>黄飞 &nbsp; <i class="fa fa-user" aria-hidden="true"></i></h3>
            <p class="code">编号:zmz123456</p>
            <p class="phone">等级:${agent.levelName}</p>
        </div>
    </div>

    <div class="label">
        <h3>标签</h3>
        <span>我的账户</span>
    </div>

        <div class="moreInfo">
            <c:forEach items="${aTypes}" var="type">
                <div data-uid="${agent.id}" data-state="${type.state}" class="account label">
                    <span class="h3">${type.stateInfo}</span>
                    <span class="content">${agent.accounts.getAccount(type)}</span>
                    <i class="fa fa-angle-right" aria-hidden="true"></i>
                </div>
            </c:forEach>
        </div>

    <%--<div class="moreInfo">--%>
        <%--<div class="address label">--%>
            <%--<h3>代金券</h3>--%>
            <%--<span>${voucher}</span>--%>
        <%--</div>--%>
        <%--<div class="address label">--%>
            <%--<h3>进货券</h3>--%>
            <%--<span>${purchase}</span>--%>
        <%--</div>--%>
        <%--<div class="address label">--%>
            <%--<h3>预存款</h3>--%>
            <%--<span>${advance}</span>--%>
        <%--</div>--%>
    <%--</div>--%>

    <div class="accounts">
        <ul>

            <c:forEach items="${goodsAccount}" var="gac">
                <li>
                    <img src="${gac.goods.imgFile}" alt="">
                    <div class="accountsInfo">
                        <h3>${gac.goods.name}</h3>
                        <h4>库存:${gac.currentBalance}</h4>
                    </div>
                </li>
            </c:forEach>

            <%--<li>--%>
            <%--<img src="http://www.zmz365.com:8081/dreamer/dmz/img/goods/2e50bb48461343269a7377c5f9766302.jpg" alt="">--%>
            <%--<div class="accountsInfo">--%>
            <%--<h3>芝德堂雪莲</h3>--%>
            <%--<h4>库存:30</h4>--%>
            <%--</div>--%>
            <%--</li>--%>


            <%--<li>--%>
            <%--<img src="http://www.zmz365.com:8081/dreamer/dmz/img/goods/9b2089f4604e4522a975dd3a78490be0.jpg" alt="">--%>
            <%--<div class="accountsInfo">--%>
            <%--<h3>酷宝胸膜二代</h3>--%>
            <%--<h4>库存:30</h4>--%>
            <%--</div>--%>
            <%--</li>--%>
        </ul>
    </div>

    <!--<div class="buttons">-->

    <!--<div class="chat"><span>发消息</span></div>-->

    <!--<div class="transferGoods"><span>给他拨货</span></div>-->
    <!--<div class="transferGoods"><span>给他发货</span></div>-->
    <!--<div class="transferVoucher"><span>转代金券</span></div>-->
    <!--<div class="transferPay"><span>转进货券</span></div>-->
    <!--<div class="transferPay"><span>录防伪码</span></div>-->

    <!--</div>-->
</div>


</body>
</html>