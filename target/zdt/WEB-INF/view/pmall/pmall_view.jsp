<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/common/common.jsp" %>
<html>
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
    .cartControl-wrapper {
        position: absolute;
        right: 10px;
        bottom: 12px;
    }

    .cartControl .cart-add, .cartControl .cart-decrease .inner {
        /*padding: 4px 6px 6px;*/
        padding: 10px;
        color: #00a0dc;
        font-size: 22px;
    }

    .cartControl .cart-add, .cartControl .cart-count, .cartControl .cart-decrease {
        display: inline-block;
        line-height: 22px;
        vertical-align: top;
    }

    .cartControl .cart-count {
        visibility: hidden;
        font-size: 10px;
        width: 12px;
        padding-top: 10px;
        text-align: center;
        color: #93999f;
    }

    .cart-decrease {
        visibility: hidden;
        opacity: 0;
        transform: translate3d(24px, 0, 0);
        transition: all 0.4s linear;
    }

    .cart-decrease .inner {
        display: inline-block;
        transform: rotate(0);
        transition: all 0.4s linear;
    }

    .move-transition {
        visibility: visible;
        opacity: 1;
        transform: translate3d(0, 0, 0);
    }

    .move-transition .inner {
        transform: rotate(-180deg);
    }

    .move-leave {
        visibility: hidden;
        opacity: 0;
        transform: translate3d(24px, 0, 0);

    }

    .move-leave .inner {
        transform: rotate(180deg);
    }

    /*购物球*/
    .ball {
        width: 16px;
        height: 16px;
        border-radius: 50%;
        background: rgb(0, 160, 220);
        z-index: 200;
        /*transition: all 0.4s linear;*/
    }

    /*星星等级*/
    .score {
        display: inline-block;
        font-family: Wingdings;
        font-size: 18px;
        color: #ccc;
        position: relative;
    }

    .score::before,
    .score span::before {
        content: "\2605\2605\2605\2605\2605";
        display: inline;
    }

    .score span {
        color: gold;
        position: absolute;
        top: 0;
        left: 0;
        overflow: hidden;
    }

    .price::before {
        font-weight: 400;
        content: "\A5";
        font-size: 0.699em;
        display: inline-block;
    }


</style>
<body>
<div class="fanhui_cou">
    <div class="fanhui_1"></div>
    <div class="fanhui_ding">顶部</div>
</div>
<header class="header">
    <div class="fix_nav">
        <div class="nav_inner">
            <a class="nav-left back-icon" href="javascript:history.back();">返回</a>
            <div class="tit">产品列表</div>
        </div>
    </div>
</header>

<div class="container" id="container2">
    <div class="row">
        <ul class="mod-filter clearfix">
            <div class="white-bg_2 bb1">

                <li id="default" class="active"><a
                        title="默认排序" href="javascript:void(0);">默认</a></li>
                <li id="buys"><a title="点击按销量从高到低排序"
                                 href="javascript:void(0);">销量
                    <i class='icon_sort'></i>
                </a></li>
                <li id="comments"><a title="点击按评论数从高到低排序"
                                     href="javascript:void(0);">评论数
                    <i class='icon_sort'></i>
                </a></li>
                <li id="cash"><a title="点击按价格从高到低排序"
                                 href="javascript:void(0);">价格
                    <i class='icon_sort'></i>
                </a></li>
            </div>
        </ul>
        <div class="item-list" id="container" rel="2" status="0"><input type="hidden" id="ListTotal" value="1">
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
        <div id="ajax_loading" style="width:300px;margin: 10px  auto 15px;text-align:center;">
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
                "useDatatables": true,
                "entity.name": "${parameter.entity.name}",
                "entity.goodsType.id": "${parameter.entity.goodsType.id}"
            };
            var url = "<c:url value="/dmz/pmall/goods/query.json"/>";
            $.getJSON(url, param, function (goodses) {
                if (goodses.length == 0) {
                    $("#ajax_loading").html("----抱歉到底了,即将上传更多新品----").height("5px").css(" margin: 5px auto 5px;");
                } else {
                    goodses.forEach(function (goods, i) {
                        var sel = parseInt(goods.sel);
                        //初始化数据
                        var detail_a = $("<div>").addClass("itema");
                        detail_a.appendTo(".item-list");
                        var hproduct = $("<div>");
                        hproduct.addClass("hproduct clearfix").attr("style", "background:#fff;border-top:0px;");
                        hproduct.appendTo(detail_a);
                        var pic = $("<div>").addClass("p-pic");
                        pic.appendTo(hproduct);
                        var imgresponsive = $("<img>").attr("style", "max-height:100px;margin:auto;").addClass("img-responsive");
                        imgresponsive.appendTo(pic);
                        var info = $("<div>").addClass("p-info");
                        info.appendTo(hproduct);
                        var title = $("<p>").addClass("p-title");
                        title.appendTo(info);
                        var starRating = $("<span class='score'><span style='width: 88%'></span></span>");
                        starRating.appendTo(info);
                        var origin = $("<p>").addClass("p-origin");
                        origin.appendTo(info);
                        var price = $("<em>").addClass("price");
                        price.appendTo(origin);
                        var spec = $("<span class='spec'></span>");
                        spec.appendTo(origin);
                        var mb0 = $("<p>").addClass("mb0");
                        mb0.appendTo(info);
                        var oldprice = $("<del>").addClass("old-price");
                        oldprice.appendTo(mb0);
                        var cartAdd = "<span data-id=" + goods.id + " class='iconfont icon-jia-copy cart-add'></span>";
                        if (sel != 0) {
                            cartAdd = "";

                        }
                        var cartControl_wrapper =
                            "<div class='cartControl-wrapper'>" +
                            "<div class='cartControl'>" +
                            "<div data-id=" + goods.id + " class='cart-decrease'>" +
                            "<span class='inner iconfont icon-jian1'></span>" +
                            "</div>" +
                            "<span   class='cart-count'>0</span>" + cartAdd +
                            "</div></div>"
                        $(cartControl_wrapper).appendTo(hproduct);
                        //赋值
                        detail_a.attr("data-url", "<c:url value='/dmz/pmall/detail.html?'/>id=" + goods.id);
                        imgresponsive.attr("src","${dmzImgPath}"+goods.imgUrl);
                        title.html(goods.shareDetail);
                        price.html(goods.moneyPrice);
                        spec.html("规格:" + goods.spec);
                        oldprice.html(goods.price);

                    });
                    //翻页
                    start += length;
                }
            });
        }


        //进入详情页事件
        $(".item-list").delegate(".itema", "click", function (e) {
            var url = $(this).attr("data-url");
            window.location = url;
        });


        //购物车增加事件
        $(".item-list").delegate(".cart-add", "click", function (e) {
            e.preventDefault();
            e.stopPropagation();
            var carAdd = $(this);
            //执行添加购物车
            var goodsId = $(this).attr("data-id");
            $.ajax("<c:url value='/dmz/pmall/shopcart/add.json'/>", {
                "type": "POST",
                "data": {
                    "goodsId": goodsId
                },
                "complete": function (xhr, ts) {
                    if (xhr.status == 200) {//正确响应
                        var m = $.parseJSON(xhr.responseText);
                        if (m.flag == 0) {//加入购车成功
                            vCount(carAdd, true);
                            carAdd.siblings(".cart-decrease").removeClass("move-leave");
                            carAdd.siblings(".cart-decrease").addClass("move-transition");
                            flyAnimate(e.pageX, e.pageY, m.data);
                        } else {
                            alert(m.message);
                        }
                    } else {
//                        $.parseJSON(xhr.responseText)
                        alert("未知错误，请联系管理员！");
                    }

                }
            });


        });
        //购物车减少事件
        $(".item-list").delegate(".cart-decrease", "click", function (e) {
            var decrease = $(this);
            var goodsId = decrease.attr("data-id");
            e.preventDefault();
            e.stopPropagation();
            $.ajax("<c:url value="/dmz/pmall/shopcart/mins.json"/>", {
                "type": "post",
                "data": {
                    "goodsId": goodsId
                },
                "complete": function (xhr) {
                    if (xhr.status == 200) {
                        var m = $.parseJSON(xhr.responseText);
                        if (m.flag == 0) {//加入购车成功
                            vCount(decrease, false);
                            $("#shopcartQuantity").html(m.data);
                        } else {
                            alert(m.message);
                        }
                    } else {
                        alert("未知错误，请联系管理员！");
                    }
                }
            });
        });

        //飞入购物车动画
        var flyAnimate = function (startX, startY, data) {
            var st = $(window).scrollTop();
            var data = data;
            var shopCart = $(".shop");
            var flyer = $("<div class='ball'></div>");
            flyer.fly({
                start: {
                    left: startX,
                    top: startY - st
                },
                end: {
                    left: shopCart.offset().left,
                    top: shopCart.offset().top - st,
                    width: 20, height: 20
                }, autoPlay: true, //是否直接运动,默认true
                speed: 1.5, //越大越快，默认1.2
                vertex_Rtop: 50, //运动轨迹最高点top值，默认20
                onEnd: function () {
                    flyer.remove();//删除
                    //购物车增加
                    $("#shopcartQuantity").html(data);
                } //结束回调
            });
        }

        /**
         * 计算数量
         * @param ob
         * @param isAdd
         */
        var vCount = function (ob, isAdd) {
            var count_ob;
            var count;
            if (isAdd) {
                count_ob = ob.prev(".cart-count");
                count = count_ob.html();
                count++;
            } else {
                count_ob = ob.next(".cart-count");
                count = count_ob.html();
                count--;
                $("#")
            }
            if (count > 0) {
                count_ob.html(count);
                count_ob.css("visibility", "visible");
            } else if (count == 0) {
                count_ob.html(count);
                ob.addClass("move-leave");
                count_ob.css("visibility", "hidden");
            }
            return count;
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

