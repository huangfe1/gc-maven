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
    <link href="https://cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">
    <style rel="stylesheet">

        .con {
            background-color: #ffffff;
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
            text-overflow: ellipsis;

        }

        .cartItems {
            background-color: #e8e8e8;
        }

        .cartItems ul li {
            background-color: #ffffff;
            /*border-radius: 0.3em;*/
            border-bottom: dashed 1px #dedede;
        }

        .item {
            padding: 0.5em 0.3em;
            overflow: hidden;
            position: relative;
            height: 6em;
        }

        .item .imgDiv {
            float: left;
            width: 30%;
            height: 100%;
        }

        .imgDiv img {
            width: 100%;
            height: 100%;
        }

        .item .info {
            margin-left: 0.5em;
            float: left;
            width: 50%;

        }

        .info p {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .info h3 {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .inputs input {
            font: inherit;
            height: 2em;
            text-align: center;
            float: left;
            width: 5em;
            padding: 0;
            margin: 0;
            outline: 0;
            border: none;
        }

        .price {
            text-align: right;
            padding: 0 0.5em 0 0;
            overflow: hidden;
            /*position: relative;*/
            /*float: right;*/
        }

        .price .status {
            color: red;
            font-size: 1em;
            line-height: 2em;
            display: inline-block;
            text-align: center;
            position: absolute;
            bottom: 0.5em;
            right: 0.5em;
        }

        .result {
            background-color: white;
            padding: 1em 1em;
        }

        .result .toBuy {
            color: white;
            text-align: center;
            float: right;
            background-color: #e0217e;
            width: 6em;
            height: 2em;
            /*font-size: 1.5em;*/
            line-height: 2em;
            border-radius: 1em;
        }

        .filter {
            background: white;
            /*height: 4em;*/
            position: relative;
            overflow: hidden;
        }

        .filter ul {
            /*position: absolute;*/
            /*bottom: 0em;*/
            width: 100%;
        }

        .filter ul li {
            font-size: 0.9em;
            width: 25%;
            float: left;
            text-align: center;
            margin-left: -1px;

        }

        .filter ul li div {
            border: solid #e8e8e8; /*e8e8e8*/
            border-width: 0 0 1px 1px;
        }

        /*.filter ul li.activity{*/
        /*border-bottom: 1px solid;*/
        /*padding-bottom: 1em;*/
        /*}*/

        .filter ul li a {
            /*color: red;*/
            padding: 1.3em 0;
            display: inline-block;
            /*border: 1px grey solid;*/
            /*width: 100%;*/

        }

        .items {
            overflow: hidden;
            padding-left: 0.5em;
        }

        .items ul li {

            text-align: center;
            float: left;
            width: 50%;

        }

        .items div {
            padding-right: 0.5em;
            padding-top: 0.5em;

        }

        .items img {

            width: 100%;
        }

        .items p {
            color: #A5A6A5;
            padding-top: 0.8em;
        }

        .items .info {
            float: left;
        }

        .items span {

            color: #EE1D81;
        }

        .items del {
            color: #EE1D81;
            font-size: 0.8em;
            padding-left: 0.5em;
        }

        .pinfo {
            text-align: left;
            border: solid #e8e8e8;
            border-width: 0px 1px 1px;
            padding-bottom: 0.5em;
            padding-left: 0.5em;
        }


    </style>
    <title>商品列表</title>
</head>
<body>
<div class="con">
    <%--<header>--%>
    <%--<a href="javascript:history.go(-1)" class="Return">--%>
    <%--<span></span>--%>
    <%--</a>--%>
    <%--<span class="Title">商品列表</span>--%>
    <%--<a href="<c:url value="/dmz/vmall/index.html?ref=01"/>" class="Home"><span></span></a>--%>
    <%--</header>--%>

    <div class="filter">
        <ul>
            <li class="activity">
                <div><a href="<c:url value="/dmz/goods/list.html?cid=${param.cid}&orderType=0"/>">默认</a></div>
            </li>
            <li>
                <div><a href="<c:url value="/dmz/goods/list.html?cid=${param.cid}&orderType=1"/>">时间<i
                        class="fa fa-arrow-up" aria-hidden="true"></i></a></div>
            </li>
            <li>
                <div><a href="<c:url value="/dmz/goods/list.html?cid=${param.cid}&orderType=2"/>">人气<i
                        class="fa fa-arrow-up" aria-hidden="true"></i></a></div>
            </li>
            <li>
                <div><a href="<c:url value="/dmz/goods/list.html?cid=${param.cid}&orderType=3"/>">价格<i
                        class="fa fa-arrow-up" aria-hidden="true"></i></a></div>
            </li>
        </ul>
    </div>

    <div class="items">
        <ul>


            <c:forEach items="${goodss}" var="goods">
                <c:if test="${goods.show}">
                    <li>
                        <a href="<c:url value="/dmz/mobile/${goods.id}/detail.html"/>">

                            <div>
                                <img src="${dmzImgPath}${goods.imgFile}" alt="">
                                <div class="pinfo">
                                    <p>${goods.name}</p>
                                    <p><span>￥ ${goods.retailPrice}</span>
                                            <%--<del>￥299</del>--%>
                                    </p>
                                </div>
                            </div>
                        </a>
                    </li>
                </c:if>
            </c:forEach>


        </ul>
    </div>
    <jsp:include page="footer.jsp"/>
</div>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script>
</script>

</body>
</html>