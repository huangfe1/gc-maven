<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/common/common.jsp"%>
<html>
<head>
    <%--<script charset="utf-8" src="tcjs/jquery.min.js?v=01291"></script>--%>
    <%--<script charset="utf-8" src="tcjs/global.js?v=01291"></script>--%>
    <%--<script charset="utf-8" src="tcjs/bootstrap.min.js?v=01291"></script>--%>
    <%--<script charset="utf-8" src="tcjs/template.js?v=01291"></script>--%>

    <%--<link rel="stylesheet" href="tccss/bootstrap.css?v=01291">--%>
    <%--<link rel="stylesheet" href="tccss/style.css?v=1?v=01291">--%>
    <%--<link rel="stylesheet" href="tccss/member.css?v=01291">--%>
    <%--<link rel="stylesheet" href="tccss/order3.css?v=01291">--%>
    <%@include file="/WEB-INF/view/common/script_tc.jsp"%>
    <%@include file="/WEB-INF/view/common/head_css.jsp"%>
    <%@include file="/WEB-INF/view/common/tc_css.jsp"%>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta content="telephone=no" name="format-detection">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1;user-scalable=no;">
    <title>首页</title>

</head>
<body>
<header class="header">
    <div class="fix_nav">
        <div style="max-width:768px;margin:0 auto;height: 44px;position: relative;background:#000000;">
            <form action="<c:url value="/dmz/pmall/view.html"/> " method="get" id="searchform" name="searchform">
                <div class="navbar-search left-search">
                    <input type="text" id="keyword"  name="entity.name" value="${parameter.entity.name}" placeholder="搜索商品" class="form-control">
                </div>
                <div class="nav-right">
                    <input type="submit" value="搜索"  class="img-responsive" style="text-align:center;background:#ccc;border-radius: 5px;border:none;height:34px;vertical-align:middle;clear:both;padding:0px;width:42px;"/>
                </div>
            </form>
        </div>
    </div>
</header>

<div class="container">
    <div class="row">
        <div id="slide">
            <div class="hd">
                <ul><li class="on">1</li><li class="on">2</li><li class="on">3</li></ul>
            </div>
            <div class="bd">
                <div class="tempWrap" style="overflow:hidden; position:relative;">
                    <ul style="width: 3840px; position: relative; overflow: hidden; padding: 0px; margin: 0px; transition-duration: 200ms; transform: translateX(-768px);">
                        <li style="display: table-cell; vertical-align: top; width: 768px;">
                            <%--<a href="http://m.legendshop.cn/m_search/list?categoryId=36" target="_blank">--%>
                            <a href="<c:url value='#'/> " >
                                <img src="<c:url value='/resources/images/tch1.jpg?es=5'/>" alt="女包" ppsrc="img/0da8eb94-0159-4700-a6a5-bc007da5a853.jpg">
                            </a>
                        </li>
                        <li style="display: table-cell; vertical-align: top; width: 768px;">
                            <a href="<c:url value='#'/> " >
                                <img src="<c:url value='/resources/images/tch2.jpg?es=6'/> " alt="女鞋" ppsrc="img/61647775-f5d0-4cfe-b84f-96060eb8e2e3.jpg">
                            </a>
                        </li>
                        <li style="display: table-cell; vertical-align: top; width: 768px;">
                            <a href="<c:url value='#'/> " >
                                <img src="<c:url value='/resources/images/tch3.jpg?es=7'/> " alt="服装" ppsrc="img/efa1dc7b-1375-4876-acae-e344cae7d55c.jpg">
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <%--<script charset="utf-8" src="tcjs/TouchSlide.js"></script>--%>

    <script type="text/javascript">

        TouchSlide({
            slideCell:"#slide",
            titCell:".hd ul", //开启自动分页 autoPage:true ，此时设置 titCell 为导航元素包裹层
            mainCell:".bd ul",
            effect:"left",
            autoPlay:true,//自动播放
            autoPage:true, //自动分页
            switchLoad:"_src" //切换加载，真实图片路径为"_src"
        });
    </script>
    <div class="row category">
        <a href="<c:url value="/pm/order/myOrder.html"/> " class="col-xs-3">
            <img class="img-responsive" src="<c:url value='/resources/tcimg/icon_rm.png'/>">
            <h4>我的订单</h4>
        </a>
        <%--<a href="/m_search/list?param.commend='Y'" class="col-xs-3">--%>
        <a href="<c:url value='/dmz/pmall/view.html'/> " class="col-xs-3">
            <img class="img-responsive" src="<c:url value='/resources/tcimg/icon_tm.png'/>">
            <h4>所有产品</h4>
        </a>
        <a href="#" id="tq" class="col-xs-3">
            <img class="img-responsive" src="<c:url value='/resources/tcimg/theme.png'/>">
            <h4>特权商城</h4>
        </a>
        <a href="<c:url value='/dmz/pmall/view.html?entity.goodsType.id=17'/> " class="col-xs-3">
            <img class="img-responsive" src="<c:url value='/resources/tcimg/icon_pp.png'/> ">
            <h4>公益助农</h4>
        </a>
    </div>

    <div id="products" class="row">

        <!--产品块-->

    </div>
    <div id="ajax_loading" style="width:300px;margin: 10px  auto 15px;text-align:center;">
        <img src="<c:url value='/resources/tcimages/loading.gif'/>">
    </div>
    <%--<div class="foot_index">--%>
        <%--<div class="foot_index_tit">Aim Beauty Limited(HongKong)</div>--%>
        <%--<div class="foot_index_rx">服务热线：020-87774389</div>--%>
    <%--</div>--%>
<jsp:include page="/WEB-INF/view/common/footer.jsp"/>

    <script type="text/javascript">
        $(function () {
            var start = 0;
            var length = 3;
            var query = function () {
                var param = {
                    "start": start,
                    "length": length,
                    "useDatatables" : true
                };
                var url = "<c:url value='/dmz/pmall/types/goods/query.json'/>";
                var tb_box = null;
                var tab_tit = null;
                var ha = null;
                $.getJSON(url, param, function (types) {
                    if(types.length==0){
                        $("#ajax_loading").html("----抱歉到底了,即将上传更多新品----").height("5px").css(" margin: 5px auto 5px;");
                    }else{
                        types.forEach(function (type, i) {
                            tb_box = $("<div>").addClass("tb_box");
                            tab_tit = $("<h2>").addClass("tab_tit");
                            ha = $("<a class='more' href='<c:url value='/dmz/pmall/view.html?entity.goodsType.id="+type.typeId+"'/>'>");
                            ha.html('更多');
                            tb_box.appendTo($("#products"));
                            tab_tit.appendTo(tb_box);
                            tab_tit.html(type.typeName);
                            ha.appendTo(tab_tit);
                            var tb_type = $("<div class='tb_type tb_type_even clearfix'>");
                            tb_type.appendTo(tb_box);
                            type.goodsDtos.forEach(function (goods, i) {
                                var imgPath = "${dmzImgPath}"+goods.imgPath;
                                var img = $("<img class='tb_pic' src='" +imgPath  + "' style='width:100%;'>");
                                var a = $("<a class='th_link' style='border-right: 1px solid #dadada' href='<c:url value='/dmz/pmall/detail.html?id="+goods.goodsId+"'/> '>");
                                img.appendTo(a);
                                a.appendTo(tb_type);
                            });
                        });
                        start+=length;
                    };

                });
            }
            query();
            $(window).scroll(
                function(e) {
                    var mayLoadContent = $(window).scrollTop() >= $(
                            document).height()
                        - $(window).height();
                    if (mayLoadContent) {
                        query();
                    }
                });

            $("#tq").click(function (e) {
                e.preventDefault();
                <c:if test="${user.agentCode==null}">
                alert("请先去用户中心完善信息！");
                location.href="<c:url value="/mobile/my.html"/>";
                </c:if>
                <c:if test="${user.agentCode!=null}">
                location.href="<c:url value="/dmz/mobile/index.html"/>";
                </c:if>

            })
        });


//        $(document).ready(function(){
//            $("#slide img").each(function(){
//                var img_src=$(this).attr("_src");
//                $(this).attr("src",img_src);
//            });
//        });
//
//        function searchproduct(){
//            var keyword = document.getElementById("keyword").value;
//            if(keyword == undefined || keyword==null ||keyword ==""){
//                alert("请输入搜索关键字！");
//                return false;
//            }
//            document.getElementById("searchform").submit();
//        }
    </script>
</body></html>

