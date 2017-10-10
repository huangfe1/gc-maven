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
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/resources/mallcss/initcss.css" rel="stylesheet">
    <link href="${ctx}/resources/mallcss/common.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/scrollup/2.4.1/jquery.scrollUp.js"></script>
    <title></title>
    <style>

        .header {
            background: #f03791;
            height: 3.7em;
        }

        .headerLeft {
            float: left;
            padding: 0.7em 0.9em 0;
        }

        .headerLeft img {
            height: 2.5em;
            /*width: auto;*/
        }

        .headerRight a {
            float: right;
            height: 3.7em;
            width: 3.7em;
            position: relative;
            /*line-height: 18px;*/
        }

        .headerRight a span {
            width: 2em;
            height: 1.75em;
            background: url("${ctx}/resources/mallimages/png.png") no-repeat;
            background-size: 17em;
            position: absolute;
            top: 0.975em;
            left: 0.85em;
            /*line-height: 1.75em;*/
        }

        .headerRight a span.h_cart {
            background-position: -0.17em -3em;
        }

        .headerRight a span.h_login {
            background-position: -0.17em -0.42em;
        }

        .headerRight a span.h_search {
            background-position: -2.17em -0.45em;
        }

        .focus {
            width: 100%;
            height: auto;
            margin: 0 auto;
            position: relative;
            overflow: hidden;
        }

        .focus .hd {
            width: 100%;
            position: absolute;
            z-index: 1;
            bottom: 0.5em;
            text-align: center;
        }

        .focus .hd ul {
            display: inline-block;
            height: 5px;
            padding: 3px 5px;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            font-size: 0;
            vertical-align: top;
        }

        .focus .hd ul li {
            display: inline-block;
            width: 10px;
            height: 10px;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            background: #8C8C8C;
            margin: 0 5px;
            vertical-align: top;
            overflow: hidden;
        }

        .focus .hd ul .on {
            background: #FE6C9C;
        }

        .focus .bd {
            position: relative;
            z-index: 0;
        }

        .focus .bd li img {
            width: 100%;
            height: auto;
        }

        .focus .bd li a {
            -webkit-tap-highlight-color: rgba(0, 0, 0, 0); /* 取消链接高亮 */
        }

        .ect-row-nav {
            background: #FFF;
        }

        .ect-row-nav li {
            /*float: left;*/
            width: 25%;
            float: left;
            box-sizing: border-box;
            margin: 0.5em 0;
            line-height: 20px;
            text-align: center;
            padding: 0;
            border: 0;
            overflow: hidden;
        }

        .ect-row-nav li img {
            width: 4.17em;
            height: auto;
            display: block;
            margin: 0 auto;
        }

        .ect-row-nav li p {
            margin: 0;
            padding-top: 0.5em;
            color: gray;
            font-size: 1em;
        }

        .gridRow {
            padding: 10px 0 0 0;
        }

        .gridRow ul {
            height: auto;
            overflow: hidden;
        }

        .gridRow ul li {
            width: 50%;
            float: left;
            border: solid #e8e8e8;
            border-width: 1px 1px 0 0;
            box-sizing: border-box;
            margin-top: -1px;
            background: #fff;
        }

        .gridRow ul li a {
            display: block;
            padding: 0 13px;
        }

        .gridRow ul li a img {
            width: 100%;
            height: auto;
        }

        .indexList {
            padding: 10px 0 0 0;
        }

        .indexListTit h2 {
            border-left: #f02387 0.2em solid;
            color: #999;
            font-size: 1.2em;
            font-weight: 700;
            padding: 0 0.5em;
            line-height: 1.1;
            float: left;
        }

        .indexListTit {
            background: #fff;
            padding: 0.9em 0.9em 0;
            height: auto;
            overflow: hidden;
        }

        .indexListTit span {
            line-height: 1.3;
            font-size: 1em;
            float: left;
            font-family: arial;
        }

        .indexListTit em {
            font-size: 1.1em;
            color: #f02387;
            padding: 0 0.5em;
            line-height: 1.2;
            float: left;
        }

        .channelImg {
            padding: 0 0.9em 0.9em;
            background: #fff;
            border-bottom: 1px solid #ccc;
        }

        .channelImg li {
            padding-top: 0.9em;
        }

        .pic img {
            width: 100%;
        }

        .shop_info {
            background: #f6f6f6;
            position: relative;
            padding: 0 6px;
            height: 2.75em;
            line-height: 2.75em;
        }

        .shop_info p {
            display: block;
            padding-right: 7em;
            font-size: 1.17em;
            overflow: hidden;
            white-space: nowrap;
            word-break: keep-all;
            text-overflow: ellipsis;
            color: #242424;
            padding-top: 0.6em;
        }

        .brand_time {
            position: absolute;
            top: 0;
            right: 6px;
            font-size: 1em;
            line-height: 2.8em;
        }

        .brand_time span {
            width: 1.2em;
            height: 1.2em;
            display: inline-block;
            margin: 0.75em 0.25em 0 0;
            vertical-align: top;
            /*background: url(re/mallimages/png.png) -15.9em -8em no-repeat;*/
            background: url("${ctx}/resources/mallimages/png.png") -15.9em -8em no-repeat;
            background-size: 17em;
        }

        .goodsList {
            overflow: hidden;
        }

        .goodsList ul li {
            background: white;
            float: left;
            width: 50%;
            border: solid #e8e8e8;
            border-width: 1px 1px 0 0;
            margin-top: -1px;
        }

        .goodsList ul a {
            overflow: hidden;
            padding: 0 5px;
            display: block;
        }

        .goodsList ul li img {
            width: 100%;
            height: auto;
        }

        .goodsList ul li .info {
            padding: 1em 1em;
        }

        .cartBtn{
            float: right;
        }


    </style>
</head>
<body>
<div class="con">
    <header class="header">
        <div class="headerLeft">
            <a href=""><img src="${ctx}/resources/mallimages/logo.png" alt=""></a>
        </div>
        <div class="headerRight">
            <a href=""><span class="h_cart"></span></a>
            <a href=""><span class="h_login"></span></a>
            <a href="<c:url value='/goods/category/show'/>"><span class="h_search"></span></a>
        </div>
    </header>

    <div id="focus" class="focus">
        <div class="hd">
            <ul></ul>
        </div>
        <div class="bd">
            <ul>

                <c:set value="${fn:split(imagses,'+')}" var="imgs"/>
                <c:forEach items="${imgs}" var="val" varStatus="i">
                    <c:if test="${val!=''}">
                        <div class="item <c:if test='${i.index==0}'>active</c:if> ">
                            <li><a href="#"><img src="${dmzImgPath}${val}" alt=""></a></li>
                        </div>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>

    <nav class="container-fluid">
        <ul class="row ect-row-nav">
            <%--<li class="col-xs-3">--%>
                <%--<a href="<c:url value='/goods/category/show'/>">--%>
                    <%--<i><img width="50" height="50" src="${ctx}/resources/mallimages/fl.png" alt=""></i>--%>
                    <%--<p>我的订单</p>--%>
                <%--</a>--%>
            <%--</li>--%>
                <li class="col-xs-3">
                    <a href="<c:url value='/mobile/transfer/records.html'/>">
                        <i><img width="50" height="50" src="${ctx}/resources/mallimages/fl.png" alt=""></i>
                        <p>我的订单</p>
                    </a>
                </li>

                <li class="col-xs-3">
                    <%--<a href="<c:url value="/dmz/pmall/show.html"/>">--%>
                    <a href="<c:url value="#"/>">
                        <i><img width="50" height="50" src="${ctx}/resources/mallimages/jf.png" alt=""></i>
                        <p>优惠商城</p>
                    </a>
                </li>

            <li class="col-xs-3">
                <a href="<c:url value="/dmz/mobile/index.html" />">
                    <i><img width="50" height="50" src="${ctx}/resources/mallimages/user.png" alt=""></i>
                    <p>特权商城</p>
                </a>
            </li>
            <%--<li class="col-xs-3">--%>
                <%--<a href="<c:url value='/mobile/shopcart/index.html'/>">--%>
                    <%--<i><img width="50" height="50" src="${ctx}/resources/mallimages/cart.png" alt=""></i>--%>
                    <%--<p class="text-center">购物车</p>--%>
                <%--</a>--%>
            <%--</li>--%>

                <li class="col-xs-3">
                    <%--<a href="<c:url value='/dmz/pmall/view.html?entity.goodsType.id=17'/>">--%>
                    <a href="<c:url value='#'/>">
                        <i><img width="50" height="50" src="${ctx}/resources/mallimages/cart.png" alt=""></i>
                        <p class="text-center">公益商城</p>
                    </a>
                </li>
        </ul>
    </nav>

    <div class="gridRow">
        <ul>
            <c:forEach items="${categorys}" var="category">
                <li>
                    <a href="<c:url value='/goods/category/show/${category.id}'/>"><img
                            src="${dmzImgPath}${category.img}" alt=""></a>
                </li>
            </c:forEach>
        </ul>
    </div>

    <div class="indexList">
        <div class="indexListTit">
            <h2>精彩推荐</h2>
            <span>|</span>
            <em>不定时更新 产品特卖</em>
        </div>
        <div class="channelImg">
            <ul>
                <c:forEach items="${goodss}" var="goods">
                    <c:if test="${goods.activity&&goods.show}">
                        <li>
                            <div class="pic">
                                <i><a href="<c:url value='/dmz/mobile/${goods.id}/detail.html'/>"><img
                                        src="${dmzImgPath}${goods.actImg}" alt=""></i></a>
                            </div>
                            <div class="shop_info">
                                <p>${goods.name}</p>
                                <div class="brand_time">
                                    <span></span>仅剩 ${goods.deadLine}天
                                </div>
                            </div>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>

    <div class="goodsList">
        <%--<div class="listTitle">--%>
        <%--<h2>所有产品</h2>--%>
        <%--<span>|</span>--%>
        <%--&lt;%&ndash;<em></em>&ndash;%&gt;--%>
        <%--</div>--%>
        <ul>
            <%--<li>--%>
            <%--<a href=""><img src="http://drp.zbw.vc/zbck/dmz/img/goods/3676bbea2f2045bf950ea5d68d935083.jpg" alt=""></a>--%>
            <%--<div class="info">--%>
            <%--<span>￥59.6</span>--%>
            <%--<span data-id="" class="cartBtn"><i class="fa fa-shopping-cart" aria-hidden="true"></i></span>--%>
            <%--</div>--%>
            <%--</li>--%>

            <%--<li>--%>
            <%--<a href=""><img src="http://drp.zbw.vc/zbck/dmz/img/goods/826890d408424a708c7afafe6451b31f.jpg" alt=""></a>--%>
            <%--<div class="info">--%>
            <%--<span>￥59.6</span>--%>
            <%--<span data-id="" class="cartBtn"><i class="fa fa-shopping-cart" aria-hidden="true"></i></span>--%>
            <%--</div>--%>
            <%--</li>--%>


            <c:forEach items="${goodss}" var="goods">
                <c:if test="${goods.show}">
                    <li>
                        <a href="<c:url value="/dmz/mobile/${goods.id}/detail.html"/>"><img src="${dmzImgPath}${goods.imgFile}" alt=""></a>
                        <div class="info" >
                            <span>￥${goods.retailPrice}</span>
                            <span data-id="${goods.id}" class="cartBtn"><i class="fa fa-shopping-cart" aria-hidden="true"></i></span>
                        </div>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </div>

    <jsp:include page="footer.jsp"/>


    <jsp:include page="cart.jsp"/>

</div>


<script src="${ctx}/resources/malljs/TouchSlide.1.1.js"></script>

<script src="https://cdn.bootcss.com/vue/2.3.3/vue.min.js"></script>
<script>
    /*banner滚动图片*/
    TouchSlide({
        slideCell: "#focus",
        titCell: ".hd ul", // 开启自动分页 autoPage:true ，此时设置 titCell 为导航元素包裹层
        mainCell: ".bd ul",
        effect: "left",
        autoPlay: true, // 自动播放
        autoPage: true, // 自动分页
        switchLoad: "_src" // 切换加载，真实图片路径为"_src"
    });

    $(function () {
        $(".cartBtn").click(function () {
            var gid = $(this).attr("data-id");
            var id = $(this).attr("data-id");
            $.post("<c:url value='/vmall/shopcart/put.json'/>",
                {
                    "goodsId": gid,
                }
                ,
                function (data, status, jqXHR) {
                    var m = data;
                    if (m.flag == "0") {
//                        calcTotal();
                        var sq = parseInt( $(".flow_cart_num").html());
                        if(isNaN(sq))sq=0;
                        $(".flow_cart_num").html(sq+1);
                    } else {
                        alert("操作失败" + m.message);
                    }
                }).fail(function (xhr) {
                if (xhr.status == 401) {
                    window.location = xhr.getResponseHeader("Location");
                } else {
                    alert("未知错误请联系管理员");
                }
            });
        });
    })


    //首页菜单下特卖广告
    //    $(function(){
    //        var a=$(".gridRow li");
    //        a.each(function(i){
    //            a.eq(i*2-1).addClass("on");
    //        });
    //    });

</script>
</body>

</html>