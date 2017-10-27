<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/common/common.jsp" %>
<!doctype html>
<html>
<head>
    <meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1,user-scalable=no">
    <title>
        ${goods.name}
    </title>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <%@include file="/WEB-INF/view/common/head_css.jsp" %>
    <%@include file="/WEB-INF/view/common/tc_css.jsp" %>
    <%@include file="/WEB-INF/view/common/script_tc.jsp" %>
    <style type="text/css">
        .details_con li .tb-out-of-stock {
            border: 1px dashed #bbb;
            color: #bbb;
            cursor: not-allowed;
        }

        .no-selected {
            background: #ffe8e8 none repeat scroll 0 0;
            border: 2px solid #be0106;
            margin: -1px;
        }

        .item-name {
            max-height: 60px;
        }

        .price::before {
            font-weight: 400;
            content: "\A5";
            font-size: 1em;
            display: inline-block;
        }

        .share {
            display: inline-block;
            position: absolute;
            right: 20px;
        }

        .i-share {
            display: block;
            background: url(<c:url value='/resources/tcimg/share.png'/>);
            width: 32px;
            height: 32px;
        }
    </style>
</head>

<body>

<div class="fanhui_cou">
    <div class="fanhui_1"></div>
    <div class="fanhui_ding">顶部</div>
</div>

<%--<header class="header">--%>
    <%--<div class="fix_nav">--%>
        <%--<div style="max-width:768px;margin:0 auto;background:#000;position: relative;">--%>
            <%--<a class="nav-left back-icon" href="javascript:history.back();">返回</a>--%>
            <%--<div class="tit">商品详细</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</header>--%>
<input type="hidden" id="goodsId" value="${goods.id}"/>
<input id="currSkuId" value="" type="hidden"/>
<div class="container">
    <div class="row white-bg">
        <div id="slide">
            <div class="hd">
                <ul>
                    <li>1</li>
                </ul>
            </div>
            <div class="bd">
                <div class="tempWrap" style="overflow:hidden; position:relative;">
                    <ul style="width: 3072px; position: relative; overflow: hidden; padding: 0px; margin: 0px; transition-duration: 200ms; transform: translateX(-768px);">
                        <li style="display: table-cell; vertical-align: middle; max-width: 768px;">
                        <a href="#"><img style="max-width:100vw;margin:auto;" src="<c:url value="${dmzImgPath}${goods.imgUrl}"/>"></a>
                        </li>
                        <li style="display: table-cell; vertical-align: middle; max-width: 768px;">
                        <a href="#"><img style="max-width:100vw;margin:auto;" src="<c:url value="${dmzImgPath}${goods.imgUrl}"/>"></a>
                        </li>
                        <li style="display: table-cell; vertical-align: middle; max-width: 768px;">
                        <a href="#"><img style="max-width:100vw;margin:auto;" src="<c:url value="${dmzImgPath}${goods.imgUrl}"/>"></a>
                        </li>
                        <%--<c:forEach begin="0" end="2"  items="${goods.detailImgUrls}" var="xqimg">--%>
                            <%--<c:if test="${xqimg ne ''}">--%>
                                <%--<li style="display: table-cell; vertical-align: middle; max-width: 768px;">--%>
                                    <%--<a href="#"><img style="max-width:100vw;max-height:80vw;margin:auto;"--%>
                                                     <%--src="${xqimg}"></a>--%>
                                <%--</li>--%>
                            <%--</c:if></c:forEach>--%>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="row gary-bg">
        <div class="white-bg p10 details_con">
            <h1 class="item-name" id="prodName">${goods.shareDetail}</h1>
            <ul>
                <li>
                    <label>优惠价格：</label>
                    <span class="price" id="prodCash">${goods.retailPrice}</span>
                </li>
                <li>
                    <label>零售价格：</label>
                    <span style="text-decoration:line-through; color:red;"><em>¥${goods.price}</em></span>
                </li>
                <!--规格选择区-->
               <c:if test="${goods.goodsStandard.size()>0}">
                   <li id="choose_0" index="0" >
                       <label id="propName" propname="规格">分类：</label>
                       <dl>
                           <c:forEach items="${goods.goodsStandard}" var="standard">
                               <c:if test="${standard.stock>0}">
                                   <dd count="${goods.sel}" pri="${standard.price}" valId="${standard.id}" na="${standard.name}">${standard.name}<span></span></dd>
                               </c:if>
                           </c:forEach>
                               <%--<dd key="208:635" valId="635" >黑色<span></span></dd>--%>
                               <%--<dd class="check" key="208:636" valId="636" >红色<span></span></dd>--%>
                               <%--<dd key="208:661" valId="661" >黄色<span></span></dd>--%>
                       </dl>
                   </li>
               </c:if>


                <li id="choose_0" index="0">
                    <label id="propName" propname="规格">规格：</label>
                    <span>${goods.spec}</span>
                </li>
                <%--<li>--%>
                    <%--<label>分享奖励：</label>--%>
                    <%--<span>${goods.vouchers}¥</span>--%>
                    <%--&lt;%&ndash;<div class="share">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<i class="i-share"></i><span>分享</span>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<div class="count_div" style="height: 30px; width: 130px;">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<a href="javascript:void(0);" class="minus" ></a>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<input type="text" class="count" value="1" id="prodCount" readonly="readonly"/>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<a href="javascript:void(0);" class="add" ></a>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                <%--</li>--%>
            </ul>
        </div>
        <div id="goodsContent" class="goods-content white-bg">

            <div class="hd hd_fav">
                <ul>
                    <li class="on">图文详情</li>
                    <li class="">规格参数</li>
                    <li class="">评价(0)</li>
                </ul>
            </div>

            <div class="tempWrap" style="overflow:hidden; position:relative;">
                <div style="width: 2304px; position: relative; overflow: hidden; padding: 0px; margin: 0px; transition-duration: 200ms; transform: translateX(0px);"
                     class="bd">
                    <ul style="display: table-cell; vertical-align: top; max-width: 768px;width: 100%;"
                        class="property">
                        <div class="prop-area" style="min-height:300px;overflow: hidden;">
                            <c:forEach items="${goods.detailImgUrls}" var="xqimg">
                                <c:if test="${xqimg ne ''}">
                                    <img src="<c:url value="${dmzImgPath}${xqimg}"/>" alt=""/>
                                </c:if></c:forEach>
                        </div>
                    </ul>
                    <ul class="txt-imgs"
                        style="display: table-cell; vertical-align: top; max-width: 768px;width: 100%;">
                        <div class="desc-area" style="padding: 0px 10px 0 10px;">
                            <li style="height:30px;">
                                <div id="ajax_loading" style="margin: 10px  auto 15px;text-align:center;"><img
                                        src="<c:url value="/resources/tcimages/loading.gif"/>"
                                        style="width: auto; display: block;  margin: auto;">
                                </div>
                            </li>
                        </div>
                    </ul>
                    <ul style="display: table-cell; vertical-align: top; max-width: 768px;width: 100%;" class="appraise"
                        rel="1" status="1">
                        <div style="height:30px;">
                            <div id="ajax_loading" style="margin: 10px  auto 15px;text-align:center;"><img
                                    src="<c:url value="/resources/tcimages/loading.gif"/>"
                                    style="width: auto; display: block;  margin: auto;">
                            </div>
                        </div>
                        <div class="wap_page" style="display: none;" onclick="next_comments(this)"><span>下一页</span>
                        </div>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="fixed-foot">
    <div class="fixed_inner">
        <a class="btn-fav" href="<c:url value="/dmz/pmall/show.html"/>" onclick="addInterest(this,'663');">
            <i class="i-fav" style="background-position: 0px -38px;"></i><span>首页</span>
        </a>
        <a class="cart-wrap" href="<c:url value="/dmz/pmall/shopcart/index.html"/>">
            <i class="i-cart"></i>
            <span>购物车</span>
            <span class="add-num" id="totalNum">${pmshopcart.quantity==null?0:pmshopcart.quantity}</span>
        </a>
        <div class="buy-btn-fix">
            <c:if test="${goods.shelf}">
            <a class="btn btn-info btn-cart" href="javascript:void(0);">加入购物车</a>
            </c:if>
            <c:if test="${!goods.shelf&&goods.stockQuantity<=0}">
                <a class="btn btn-info " href="javascript:void(0);">下架/无货</a>
            </c:if>
            <%--<a class="btn btn-danger btn-buy" onclick="buyNow();" href="javascript:void(0);">立即购买</a>--%>
        </div>
    </div>
</div>

<div class="clear"></div>
<jsp:include page="/WEB-INF/view/common/footer.jsp"/>

<script type="text/javascript">
    $(function () {

        var sel = parseInt(${goods.sel});
        //如果绑定了上级，就会弹出来绑定了上级
        var parent = "${parentName}";
        if (parent != "" && typeof(parent) != "undefined" && parent != null) {
            alert("您关注的小店主人是" + parent);
        }
        var jsapiParamJson =${jsapiParamJson};
        var url = "";
        var shareName = "";
        var realName = "${user.realName}的小店";
        var shareDetail = "";
        var img = "${dmzImgPath}${goods.imgUrl}";
        <%--url=location.href.split('#')[0]+"&&myCode=${myCode}";//分享链接地址--%>
        url = location.href.split('#')[0] + "&&myCode=${user.id}";//通过id分享
        shareName = "${goods.shareName}";
        shareDetail = "${goods.shareDetail}";
        wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: jsapiParamJson.appId, // 必填，公众号的唯一标识
            timestamp: jsapiParamJson.timestamp, // 必填，生成签名的时间戳
            nonceStr: jsapiParamJson.nonceStr, // 必填，生成签名的随机串
            signature: jsapiParamJson.signature,// 必填，签名，见附录1
            jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });

        wx.ready(function () {

            //朋友圈分享
            wx.onMenuShareTimeline({
                title: realName+shareName, // 分享标题
                link: url, // 分享链接
                imgUrl: img, // 分享图标
                success: function () {
                    // 用户确认分享后执行的回调函数
                    alert("小店分享成功了");
                },
                cancel: function () {
                    // 用户取消分享后执行的回调函数
                }
            });
            //用户分享
            wx.onMenuShareAppMessage({
                title: realName+shareName, // 分享标题
                desc: shareDetail, // 分享描述
                link: url, // 分享链接
                imgUrl: img, // 分享图标
                type: '', // 分享类型,music、video或link，不填默认为link
                dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                success: function () {
                    // 用户确认分享后执行的回调函数
                    alert("小店分享成功");
                },
                cancel: function () {
                    // 用户取消分享后执行的回调函数
                }
            });
        });

        $(".btn-cart").click(function () {
            //执行添加购物车
            var goodsId = $("#goodsId").val();
            var standardIds = [];
            var standardPrices = [];
            var standardNames = [];
            var  indexCount = 0;
            //详情属性选择
            $('.details_con ul li dd').each(function(e) {
                if($(this).hasClass("check")){
                    indexCount++;
                    standardIds.push($(this).attr("valId"));
                    standardPrices.push($(this).attr("pri"));
                    standardNames.push($(this).attr("na"));
                }
            });
            if(indexCount==0&&sel!=0){
                alert("请选择分类");
            } else {
                if(indexCount!=sel) {
                    var tmp = sel - indexCount;
                    tmp++;
                    alert("选择了" + tmp + "个" + standardNames[standardNames.length - 1]);
                    for (var i = 0; i < tmp-1; i++) {
                        standardNames.push(standardNames[standardNames.length - 1]);
                        standardIds.push(standardIds[standardIds.length - 1]);
                        standardPrices.push(standardPrices.length-1);
                    }
                }
                $.ajax("<c:url value='/dmz/pmall/shopcart/add.json'/>", {
                    "type": "POST",
                    "data": {
                        "goodsId": goodsId,
                        "standardIds":standardIds,
                        "standardPrices":standardPrices,
                        "standardNames":standardNames
                    },
                    "complete": function (xhr, ts) {
                        if (xhr.status == 200) {//正确响应
                            var m = $.parseJSON(xhr.responseText);
                            if (m.flag == 0) {//加入购车成功
                                location.href = "<c:url value="/dmz/pmall/shopcart/index.html"/>";
                            } else {
                                alert(m.message);
                            }
                        } else {
//                        $.parseJSON(xhr.responseText)
                            console.log(xhr);
                            alert("未知错误，请联系管理员！");
                        }
                    }
                });
            }
        });
    });
    //插件：图片轮播
    TouchSlide({
        slideCell: "#slide",
        titCell: ".hd ul", //开启自动分页 autoPage:true ，此时设置 titCell 为导航元素包裹层
        mainCell: ".bd ul",
        effect: "left",
        autoPlay: false,//自动播放
        autoPage: true, //自动分页
        switchLoad: "_src" //切换加载，真实图片路径为"_src"
    });

    var scrollTop = 0;
    TouchSlide({
        slideCell: "#goodsContent",
        startFun: function (i, c) {
            var prodId = $("#goodsId").val();
            if (i == 1) {//规格参数
                var th = jQuery("#goodsContent .bd ul").eq(i);
//                if(!th.hasClass('hadGoodsContent')){
//                    queryParameter(th,prodId);
//                }
//
//                if($(window).scrollTop() > scrollTop){
//                    $(window).scrollTop(scrollTop);
//                }
                th.html("暂未开放");
            } else if (i == 2) {//评价
                var th = jQuery("#goodsContent .bd ul").eq(i);
//
//                if(!th.hasClass('hadConments')){
//                    queryProdComment(th,prodId);
//                }
//
//                if($(window).scrollTop() > scrollTop){
//                    $(window).scrollTop(scrollTop);
//                }
                th.html("暂未开放");
            } else {
                if (scrollTop == 0) {
                    $(window).scrollTop(scrollTop);
                    var hd_fav = $('.hd_fav');
                    var position = hd_fav.position();

                    scrollTop = position.top;
                }
            }
        },
    });

</script>
</body>
</html>
