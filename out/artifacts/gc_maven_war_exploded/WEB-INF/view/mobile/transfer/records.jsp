<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
    <style rel="stylesheet">

        .con {
            background-color: #e8e8e8;
            overflow: hidden;
        }

        header {
            height: 3.7em;
            background-color: #f03792;
            position: relative;
            text-align: center;
        }

        .Return, .Home {
            position: absolute;
            top: 0;
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
            width: 1.7em;
            height: 1.7em;
            position: absolute;
            top: 1em;
            left: 1em;
        }

        .Return span {
            background-position: -0.17em -5.5em;
        }

        .Home span {
            background-position: -0.17em -8.1em;
        }

        .Title {
            font-size: 1.3em;
            line-height: 2.8em;
            color: white;

        }

        .itemsTitle {
            background-color: white;
            margin-top: 0.5em;
            height: 2.5em;
            border-bottom: 1px dashed #dedede;
        }

        .itemsTitle ul {
            overflow: hidden;
        }

        .itemsTitle ul li {
            width: 33%;
            float: left;
            text-align: center;
            font-size: 1em;
            line-height: 2.5em;
            white-space: nowrap;
            overflow: hidden;
        }







        .result {
            background-color: white;
            padding: 1em 1em 0.5em;
        }

        .result p{
            padding-top: 0.5em;
        }



        .filter {
            background: white;
            height: 4em;
            position: relative;
        }

        .filter ul {
            position: absolute;
            bottom: 0em;
            width: 100%;
        }

        .filter ul li {
            font-size: 0.9em;
            width: 25%;
            float: left;
            text-align: center;
        }

        .filter ul li.activity {
            border-bottom: 1px solid;
            padding-bottom: 1em;
        }

        .filter ul li.activity a {
            color: red;

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
            padding-top: 0.6em;
        }

        .accounts .accountsInfo h4 {
            color: #a5a5a5;
            padding-top: 0.8em;

        }

        .accounts .accountsInfo h3 {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            max-width: 15em;

        }


    </style>
    <title>转货记录</title>
</head>
<body>
<div class="con">
    <%--<header>--%>
    <%--<a href="javascript:history.go(-1)" class="Return">--%>
    <%--<span></span>--%>
    <%--</a>--%>
    <%--<span class="Title">发货记录</span>--%>
    <%--<a href="<c:url value="/dmz/mall/index.html"/>" class="Home"><span></span></a>--%>
    <%--</header>--%>

    <div class="filter">
        <ul>
            <li class="activity"><a href="">全部</a></li>
            <li><a href="">入库</a></li>
            <li><a href="">出库</a></li>
            <li><a href="">失败</a></li>
        </ul>
    </div>
    <div class="carts">

        <c:forEach items="${items}" var="cart">
            <div class="cart">
                <div class="itemsTitle">
                    <ul>
                        <li><span class="from">${cart.fromAgent.realName}</span></li>
                        <li><span class="time"><fmt:formatDate value="${cart.updateTime}" pattern="yyyy-M-d H:m" /></span></li>
                        <li><span class="to">${cart.toAgent.realName}</span></li>
                    </ul>
                </div>

                <div class="accounts">
                    <ul>

                        <c:forEach items="${cart.items}" var="item">
                            <li class="item" data-id="${item.goods.id}">
                                <img src="${dmzImgPath}${item.goods.imgFile}" alt="">
                                <div class="accountsInfo">
                                    <h3><span>${item.goods.name}</span></h3>
                                    <h4 >数量:X${item.quantity}&nbsp;</h4>
                                </div>
                                <div class="price accountsInfo" style="float: right;margin-left: 0">
                                    <h3><span>${item.price}￥</span></h3>
                                    <%--<h4><i><del>${item.goods.retailPrice}￥</del></i></h4>--%>
                                </div>
                                    <%--<div class="operate" style="display: none">--%>
                                    <%--<div class="inputs">--%>
                                    <%--<span class="reduce">-</span>--%>
                                    <%--<input class="amount" type="text" value="0">--%>
                                    <%--<span class="add">+</span>--%>
                                    <%--</div>--%>
                                    <%--<div class="delete">--%>
                                    <%--<span>删除</span>--%>
                                    <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<i  class="fa fa-check-circle" aria-hidden="true"></i>--%>
                            </li>
                        </c:forEach>


                    </ul>
                </div>


                <div class="result">
                    <c:if test="${cart.voucher==0}">
                        <%--<span>总价值:</span>  <span>${cart.amount}￥</span>--%>
                        <span>备注:</span>  <span>${cart.remittance} ${cart.remark}</span>
                    </c:if>
                    <c:if test="${cart.voucher!=0}">
                        <span>消费:</span>  <span>V:${cart.voucher}+P:${cart.purchase}</span>
                    </c:if>

                    <span style="float: right;color: red">${cart.status.desc} </span>
                    <p>
                            <%--${cart.logistics}:${cart.logisticsCode}--%>

                    </p>
                </div>
            </div>
        </c:forEach>


    </div>
</div>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script>
</script>

</body>
</html>