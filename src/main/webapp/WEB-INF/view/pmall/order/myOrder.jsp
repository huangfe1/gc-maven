<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/common/common.jsp" %>
<html style="font-size: 14px;">
<head>
    <%@include file="/WEB-INF/view/common/script_tc.jsp" %>
    <%@include file="/WEB-INF/view/common/tc_css.jsp" %>
    <%@include file="/WEB-INF/view/common/head_css.jsp" %>
    <link href="//at.alicdn.com/t/font_z5notrk2fbznnrk9.css" rel="stylesheet" type="text/css">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta content="telephone=no" name="format-detection">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1,user-scalable=no">
    <title>首页</title>
</head>
<style>

    /*订单*/

    #container2 {
        background: #eee;
    }

    .item-list > ul > li {
        padding-top: 1rem;
    }

    .mod-filter {
        margin-bottom: 0px;
    }

    .o-t-title-shop {
        height: 3.067rem;
        padding: 0 .99rem;
        box-sizing: border-box;
        -webkit-box-pack: center;
        -moz-box-pack: center;
        -ms-box-pack: center;
        -o-box-pack: center;
        box-pack: center;
        -webkit-box-align: center;
        -moz-box-align: center;
        -ms-box-align: center;
        -o-box-align: center;
        box-align: center;
        display: -webkit-box;
        display: -moz-box;
        display: -ms-box;
        display: -o-box;
        display: box;
        background: #fff
    }

    .tcont {
        width: 100%;
        display: -webkit-box;
        display: -moz-box;
        display: -ms-box;
        display: -o-box;
        display: box
    }

    .tcont > div {
        -webkit-box-pack: center;
        -moz-box-pack: center;
        -ms-box-pack: center;
        -o-box-pack: center;
        box-pack: center;
        -webkit-box-align: center;
        -moz-box-align: center;
        -ms-box-align: center;
        -o-box-align: center;
        box-align: center;
        display: -webkit-box;
        display: -moz-box;
        display: -ms-box;
        display: -o-box;
        display: box
    }

    .ico {
        margin-right: 1rem;
        line-height: 0;
        width: 2rem;
    }

    .ico img {
        width: 100%;
    }

    .contact {
        -webkit-box-flex: 1;
        -moz-box-flex: 1;
        -ms-box-flex: 1;
        -o-box-flex: 1;
        box-flex: 1
    }

    .item-list ul, ol, li, p, div, h1, h2, h3, h4, h5 {
        list-style: none;
        margin: 0;
        padding: 0;
        font-weight: 400;
    }

    .contact a {
        width: 100%;
        display: -webkit-box;
        display: -moz-box;
        display: -ms-box;
        display: -o-box;
        display: box;
        padding-right: 2.667rem;
        box-sizing: border-box
    }

    .contact .title {
        color: #051b28;
        line-height: 2rem;
        text-align: left;
        overflow: hidden;
    }

    .state {
        /*margin-left: 1rem;*/
        text-align: right
    }

    .state-cont {
        padding: 0.5rem 0
    }

    .icon-right {
        line-height: 0;
        width: 2rem;
    }

    .icon-right img {
        width: 100%;
    }

    .h {
        color: #ff5000;
        line-height: 2rem;
    }

    .gray {
        color: #999;
    }

    .module1 {
        background: white;
    }

    .o-t-item {
        display: -webkit-box;
        padding: .5rem 1rem;
        background: #f5f5f5;
    }

    .item-img {
        width: 8rem;
        /*height: 6.6rem;*/
        margin-right: 1rem;
    }

    .item-img img {
        width: 100%;
    }

    .item-info {
        -webkit-box-flex: 1; /*设置相对布局*/
        -moz-box-flex: 1;
        -ms-box-flex: 1;
        -o-box-flex: 1;
        box-flex: 1;
    }

    .item-info .title { /* -webkit-line-clamp   -moz-box-orient display: -moz-box; 才能出现缩略*/
        overflow: hidden;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        -moz-box-orient: vertical;
        -ms-box-orient: vertical;
        -o-box-orient: vertical;
        box-orient: vertical;
        display: -webkit-box;
        display: -moz-box;
        display: -ms-box;
        display: -o-box;
        display: box;

        font-size: 12px;
        line-height: 1.3em;
        font-weight: 500;
    }

    .item-info .sku {
        color: #999;
    }

    .item-info p {
        margin-top: 1rem;
        overflow: hidden;
        -webkit-line-clamp: 3;
        -moz-line-clamp: 3;
        -ms-line-clamp: 3;
        -o-line-clamp: 3;
        line-clamp: 3;
        -webkit-box-orient: vertical;
        -moz-box-orient: vertical;
        -ms-box-orient: vertical;
        -o-box-orient: vertical;
        box-orient: vertical;
        display: -webkit-box;
        display: -moz-box;
        display: -ms-box;
        display: -o-box;
        display: box;
        word-break: break-all;
        font-size: .8em;
    }

    .item-pay {
        width: 5rem;
        text-align: right;
        display: -webkit-box;
        display: -moz-box;
        display: -ms-box;
        display: -o-box;
        display: box;
        -webkit-box-orient: vertical;
        font-size: 13px;
    }

    .item-pay .price {
        line-height: 1rem;
        font-weight: 700;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
        padding: 0;
        margin: 0;
        font-size: 1.1em;
        color: black;

    }

    .item-pay .price span {
        font-size: 11px
    }

    .o-total-price {
        line-height: 3rem;
        text-align: right;
        background: white;
        display: -webkit-box;
        display: -moz-box;
        display: -ms-box;
        display: -o-box;
        display: box;
        border-bottom: 1px solid #e7e7e7;
        padding-right: 2em;

    }

    .o-total-price .cont {
        width: 100%;
    }

    .o-total-price .cont span {
        margin-left: .96rem;
    }

    .o-tab-btn {
        height: 3.3rem;
        background: white;
        padding: .2rem .96rem .21rem 0;
    }

    .o-tab-btn > ul {
        position: relative;
        float: right
    }

    .o-tab-btn > ul li {
        float: right;
        height: 2.1rem;
        line-height: 2.1rem;
        text-align: center;
        border: 1px solid #999;
        border-radius: 0.15rem;
        padding: 0 .267rem;
        margin: .399rem 0 .399rem .399rem;
    }

    .o-tab-btn > ul li.more {
        width: 2.7rem;
        margin-left: 0;
    }

    .p-info .p-title{
        height: 20px;
    }
    p {
        margin: 0;
    }

    /*.item-pay .item-pay-data {*/
    /*-webkit-box-flex: 1;*/
    /*-moz-box-flex: 1;*/
    /*-ms-box-flex: 1;*/
    /*-o-box-flex: 1;*/
    /*box-flex: 1*/
    /*}*/
    /*.item-pay .price {*/
    /*line-height: 3.3rem;*/
    /*font-weight: 700;*/
    /*text-overflow: ellipsis;*/
    /*white-space: nowrap;*/
    /*overflow: hidden*/
    /*}*/


</style>
<body>
<%--<div class="fanhui_cou">--%>
    <%--<div class="fanhui_1"></div>--%>
    <%--<div class="fanhui_ding">顶部</div>--%>
<%--</div>--%>
<%--<header class="header">--%>
    <%--<div class="fix_nav">--%>
        <%--<div class="nav_inner">--%>
            <%--<a class="nav-left back-icon" href="javascript:history.back();">返回</a>--%>
            <%--<div class="tit">订单列表</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</header>--%>

<div class="container" id="container2">
    <div class="row">
        <ul class="mod-filter clearfix">
            <div class="white-bg_2 bb1">

                <li id="default" class="active"><a
                        title="默认排序" href="javascript:void(0);">全部</a></li>
                <li id="buys"><a title="点击按销量从高到低排序"
                                 href="javascript:void(0);">待付款
                    <%--<i class='icon_sort'></i>--%>
                </a></li>
                <li id="comments"><a title="点击按评论数从高到低排序"
                                     href="javascript:void(0);">待发货
                    <%--<i class='icon_sort'></i>--%>
                </a></li>
                <li id="cash"><a title="点击按价格从高到低排序"
                                 href="javascript:void(0);">待评价
                    <%--<i class='icon_sort'></i>--%>
                </a></li>
            </div>
        </ul>
        <div class="item-list" id="container" rel="2" status="0"><input type="hidden" id="ListTotal" value="1">
            <ul id="ul">
                <%--<li>--%>
                    <%--<div class="module1">--%>
                        <%--<div class="o-t-title-shop">--%>
                            <%--<div class="tcont">--%>
                                <%--<div class="ico"><img--%>
                                        <%--src="<c:url value="/resources/tcimages/user.jpg"/>"></div>--%>
                                <%--<div class="contact">--%>
                                    <%--<a href="//shop.m.taobao.com/shop/shop_index.htm?user_id=2567130917"><p--%>
                                            <%--class="title">湖南芝德堂</p>--%>
                                        <%--&lt;%&ndash;<p class="icon-right"><img class="icon-right"&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;src="<c:url value="/resources/tcimages/icon-right-2.png"/> ">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;</p>&ndash;%&gt;--%>
                                    <%--</a></div>--%>
                                <%--<div class="state">--%>
                                    <%--<div class="state-cont"><p class="h" class="h">交易成功</p>--%>
                                        <%--<p class="gray"></p></div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="module_item">--%>
                        <%--<div class="o-t-item">--%>
                            <%--<div class="item-img"><p><img class=""--%>
                                                          <%--src="https://gw.alicdn.com/bao/uploaded/i2/TB1QDvOOXXXXXbzXpXXXXXXXXXX_!!0-item_pic.jpg_120x120q50s150.jpg_.webp">--%>
                            <%--</p></div>--%>
                            <%--<div class="item-info"><h3 class="title">晨光文具办公学生签字笔芯0.5子弹头 米家优品2004按动中性笔替芯 </h3>--%>
                                <%--<p class="sku">颜色分类:按动红色20支整盒</p></div>--%>
                            <%--<div class="item-pay">--%>
                                <%--<div class="item-pay-data"><p class="price">￥13.86</p>--%>
                                    <%--<p class="price">--%>
                                        <%--<del class="">￥14.00</del>--%>
                                    <%--</p>--%>
                                    <%--<p class="nums">x1</p></div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="module_item">--%>
                        <%--<div class="o-t-item">--%>
                            <%--<div class="item-img"><p><img class=""--%>
                                                          <%--src="https://gw.alicdn.com/bao/uploaded/i2/TB1QDvOOXXXXXbzXpXXXXXXXXXX_!!0-item_pic.jpg_120x120q50s150.jpg_.webp">--%>
                            <%--</p></div>--%>
                            <%--<div class="item-info"><h3 class="title">晨光文具办公学生签字笔芯0.5子弹头 米家优品2004按动中性笔替芯 </h3>--%>
                                <%--<p class="sku">颜色分类:按动红色20支整盒</p></div>--%>
                            <%--<div class="item-pay">--%>
                                <%--<div class="item-pay-data"><p class="price">￥13.86</p>--%>
                                    <%--<p class="price">--%>
                                        <%--<del class="">￥14.00</del>--%>
                                    <%--</p>--%>
                                    <%--<p class="nums">x1</p></div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="module_pay">--%>
                        <%--<div class="o-total-price">--%>
                            <%--<div class="cont"><span>共<b>2</b>件商品</span> <span>合计:<b>￥27.72</b></span>--%>
                                <%--<span>(含运费<b>￥0.00</b>)</span></div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="module_orderop">--%>
                        <%--<div class="o-tab-btn">--%>
                            <%--<ul>--%>
                                <%--<li class="" name="tmallAppendRate" data-id=""> 追加评价</li>--%>
                                <%--<li class="" name="" data-id=""--%>
                                    <%--data-url="//h5.m.taobao.com/2shou/onekey/orderview/index.html?bizOrderId=2894358116555383&amp;isArchive=0">--%>
                                    <%--卖了换钱--%>
                                <%--</li>--%>
                                <%--<li class="more">更多</li>--%>
                                <%--<ul class="sublist" style="display:none">--%>
                                    <%--<li class="" name="viewLogistic" data-id=""> 查看物流</li>--%>
                                    <%--<li class="" name="delOrder" data-id=""> 删除订单</li>--%>
                                <%--</ul>--%>
                            <%--</ul>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</li>--%>

            </ul>


            <%--<a href="views.html">--%>
            <%--<div class="hproduct clearfix" style="background:#fff;border-top:0px;">--%>
            <%--<div class="p-pic"><img style="max-height:100px;margin:auto;" class="img-responsive" src="tcimg/b0c64749-6129-4069-9965-4ab577d48d6a.jpg"></div>--%>
            <%--<div class="p-info">--%>
            <%--<p class="p-title">丹希路真皮女包手提包中年女士包包牛皮时尚休闲单肩斜跨包大包女</p>--%>
            <%--<p class="p-origin"><em class="price">¥338.00预存款</em>--%>
            <%--</p>--%>
            <%--<p class="mb0"><del class="old-price">¥1058.00</del></p>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</a>--%>
        </div>
        <div id="ajax_loading"  style="width:300px;margin: 10px  auto 15px;text-align:center;">
            <img src="<c:url value='/resources/tcimages/loading.gif'/>">
        </div>
        <form action='/m_search/prodlist' method="post" id="list_form">
            <input type="hidden" id="curPageNO" name="curPageNO" value=""/>
            <input type="hidden" id="categoryId" name="categoryId" value="36"/>
            <input type="hidden" id="orders" name="orders" value=""/>
            <input type="hidden" id="hasProd" name="hasProd" value=""/>
            <input type="hidden" id="keyword" name="keyword" value=""/>
            <input type="hidden" id="prop" name="prop" value=""/>
        </form>
    </div>
</div>

<div class="clear"></div>
<jsp:include page="/WEB-INF/view/common/footer.jsp"/>
<script type="text/javascript">
    $(function () {
        var start = 0;
        var length = 8;
        var query = function () {
            var param = {
                "start": start,
                "length": length,
                "useDatatables": true
            };
            var url = "<c:url value="/pm/order/query.json"/>";
            $.getJSON(url, param, function (orders) {
                if (orders.length == 0) {
                    $("#ajax_loading").html("----抱歉到底了!----").height("5px").css(" margin: 5px auto 5px;");
                } else {
                    if(orders.length<8){
                        $("#ajax_loading").html("----抱歉到底了!----").height("5px").css(" margin: 5px auto 5px;");
                    }
                    orders.forEach(function (order, i) {
                        //初始化数据
                    var li = $("<li>");
                    li.appendTo($("#ul"));
                    var userjpg = "<c:url value='/resources/mallimages/gs.jpg'/>";
                    var status = order.status;
                    var address = order.address;
                    var logist = order.logist;
                    var logistics = order.logistics != null ?order.logistics: "暂未发货";
                    var logisticsCode = order.logisticsCode!= null ? order.logisticsCode:"暂未发货";
                    var  time = order.time;
                        var module1 ="<div class=\"module1\">\n" +
                            "                        <div class=\"o-t-title-shop\">\n" +
                            "                            <div class=\"tcont\">\n" +
                            "                                <div class=\"ico\"><img\n" +
                            "                                        src=\""+userjpg+"\"></div>\n" +
                            "                                <div class=\"contact\">\n" +
                            "                                    <a href=\"#\"><p\n" +
//                            "                                            class=\"title\">湖南芝德堂("+time+")</p>\n" +
                            "                                            class=\"title\">"+time+"</p>\n" +
                            "                                    </a></div>\n" +
                            "                                <div class=\"state\">\n" +
                            "                                    <div class=\"state-cont\"><p class=\"h\" class=\"h\">"+status+"</p>\n" +
                            "                                        <p class=\"gray\"></p></div>\n" +
                            "                                </div>\n" +
                            "                            </div>\n" +
                            "                        </div>\n" +
                            "                    </div>";
                        $(module1).appendTo(li);
                        var totalQ = 0;
                        var totalP = 0;
                        order.items.forEach(function (item) {
                            var goodsName = item.goodsName;
                            var imgUrl = "${dmzImgPath}"+item.imgUrl;
                            var goodsPrice = item.goodsPrice;
                            var quantity = item.quantity;
                            totalQ+=parseInt(quantity);
                            totalP+=parseFloat(goodsPrice)*100*parseInt(quantity)/100;
                            var module_item = "<div class=\"module_item\">\n" +
                                "                        <div class=\"o-t-item\">\n" +
                                "                            <div class=\"item-img\"><p><img class=\"\"\n" +
                                "                                                          src=\""+imgUrl+"\">\n" +
                                "                            </p></div>\n" +
                                "                            <div class=\"item-info\"><h3 class=\"title\">"+goodsName+"</h3>\n" +
                                "                                <p class=\"sku\">"+address+"</p> <p class=\"sku\">"+logistics+":"+logisticsCode+"</p></div>\n" +
                                "                            <div class=\"item-pay\">\n" +
                                "                                <div class=\"item-pay-data\"><p class=\"price\">"+goodsPrice+"</p>\n" +
                                "                                    <p class=\"price\">\n" +
//                                "                                        <del class=\"\">￥14.00</del>\n" +
                                "                                    </p>\n" +
                                "                                    <p class=\"nums\">x"+quantity+"</p></div>\n" +
                                "                            </div>\n" +
                                "                        </div>\n" +
                                "                    </div>";
                            $(module_item).appendTo(li);
                        });

                        var module_pay ="<div class=\"module_pay\">\n" +
                            "                        <div class=\"o-total-price\">\n" +
                            "                            <div class=\"cont\"><span>共<b>"+totalQ+"</b>件商品</span> <span>合计:<b>￥"+totalP+"</b></span>\n" +
//                            "                                <span>(不含运费<b>￥"+logist+"</b>)</span></div>\n" +
                            "                        </div>\n" +
                            "                    </div>";
                        $(module_pay).appendTo(li);
                    });
                    //翻页
                    start += length;
                }
            });

        }
        query();
        $(window).scroll(
            function (e) {
                var mayLoadContent = $(window).scrollTop() >= $(
                        document).height()
                    - $(window).height();
                if (mayLoadContent) {
                    query();
                }
            });
    });
</script>
</body>
</html>

