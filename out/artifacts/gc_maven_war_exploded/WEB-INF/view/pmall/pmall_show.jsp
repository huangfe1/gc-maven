<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>高臣药业优惠商城</title>
    <%@include file="/WEB-INF/view/common/tc_css.jsp" %>
    <!-- Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://www.zmz365.com:8081/data/button.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>

    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<style>
    header p {
        text-align: center;
        font-size: 14px;
        color: #666666;
        margin: 0;
        font-weight: 200;
    }

    header h1 {
        text-align: center;
        font-size: 30px;
        font-weight: 200;
        margin: 30px 0 10px 0;
    }

    .demo_line_02 {
        margin: 8px 0 8px;
        height: 1px;
        border-top: 1px solid #ddd;
        text-align: center;
    }

    .demo_line_02 span {
        text-align: center;
        font-size: 14px;
        color: #666666;
        margin: 0;
        font-weight: 200;

        position: relative;
        top: -9px;
        background: #fff;
        padding: 0 20px;
    }

    .col{
        text-align: center;
    }



    /*#tagsList {position:relative; width:96%; height:250px; margin: 0px auto 0;  }*/
    /*#tagsList a {position:absolute; top:0px; left:0px; font-family: Microsoft YaHei; color:#fff; font-weight:bold; text-decoration:none; padding: 3px 6px; }*/
    /*#tagsList a:hover { color:#FF0000; letter-spacing:2px;}*/
</style>
<body style="background-color:#fbfbfb;">
<div class="container">
    <header>
        <%--<h1>优惠商城</h1>--%>
        <%--<p>让更多的人用上优惠的好产品</p>--%>
            <br>
            <form action="<c:url value="/dmz/pmall/view.html"/> " method="get" id="searchform" name="searchform">
                <div class="input-group">
                    <input type="text" class="form-control" name="entity.name" placeholder="输入你要查找的产品...">
                    <span class="input-group-btn">
        <button class="btn btn-default" type="submit">搜索</button>
      </span>
                </div>
            </form>
        <%--<p>&gt;&gt;产品分类点击这里&lt;&lt;</p>--%>
    </header>

    <%--<div class="demo_line_02"><span>商城入口区</span></div>--%>
    <%--<div class="row">--%>
        <%--<div class="col-xs-4">--%>
            <%--<button data-url="<c:url value="/dmz/pmall/view.html"/>" type="button" style="margin:10px 0;width: 100px;line-height: 30px"--%>
                    <%--class="rk button blue">所有产品</button>--%>
        <%--</div>--%>
        <%--<div class="col-xs-4"><button data-url="#" id="tq" type="button" style="margin:10px 0;width: 100px;line-height: 30px"--%>
                                      <%--class="button blue">特权商城</button></div>--%>

        <%--<div class="col-xs-4"><button  data-url="<c:url value="/dmz/pmall/show.html?pType=17" />" type="button" style="margin:10px 0;width: 100px;line-height: 30px"--%>
                                      <%--class="rk button blue">公益助农</button></div>--%>
    <%--</div>--%>
    <%--<div class="demo_line_02"><span>搜索好产品</span></div>--%>
    <div class="row category">
        <a href="<c:url value="/pm/order/myOrder.html"/> " class="col-xs-3">
            <img class="img-responsive" src="<c:url value='/resources/tcimg/icon_rm.png'/>">
            <h4>我的订单</h4>
        </a>
        <%--<a href="/m_search/list?param.commend='Y'" class="col-xs-3">--%>
        <a href="<c:url value='/dmz/pmall/show.html'/> " class="col-xs-3">
            <img class="img-responsive" src="<c:url value='/resources/tcimg/icon_tm.png'/>">
            <h4>优惠商城</h4>
        </a>
        <a href="#" id="tq" class="col-xs-3">
            <img class="img-responsive" src="<c:url value='/resources/tcimg/theme.png'/>">
            <h4>特权商城</h4>
        </a>
        <a href="<c:url value='/dmz/pmall/view.html?entity.goodsType.id=17'/> " class="col-xs-3">
            <img class="img-responsive" src="<c:url value='/resources/tcimg/icon_pp.png'/> ">
            <h4>公益商城</h4>
        </a>
    </div>

    <%--<div class="demo_line_02"><span>产品分类区</span></div>--%>
    <div class="row">
        <div class="col-xs-12">
            <div id="tagsList">
                <c:forEach items="${pTypes}" var="type" varStatus="sta">
                <c:if test="${sta.index%3==0}">
                <c:if test="${sta.index!=0}">
            </div>
            </c:if>
            <div class="row">
                </c:if>
                <div  class="col-xs-4 col" >
                    <button  data-id="${type.id}" type="button" style="margin:5px 0;width: 100px;line-height: 30px"
                            class="button blue">${type.name}</button>
                </div>
                <c:if test="${sta.index+1==pTypes.size()}">
            </div>
            </c:if>
                <%--<span class="label label-default">Default</span>--%>
                <%--<span class="label label-primary">Primary</span>--%>
                <%--<span class="label label-success">Success</span>--%>
                <%--<span class="label label-info">Info</span>--%>
                <%--<span class="label label-warning">Warning</span>--%>
                <%--<span class="label label-danger">Danger</span>--%>

            </c:forEach>
        </div>
    </div>
</div>
<div class="demo_line_02"><span>今日主推区</span></div>
<div class="row products">


    <c:forEach items="${ztGoods}" var="zt">
        <div class="col-xs-12 col-md-6 col">
            <a href="<c:url value='/pmall/detail.html?id='/>${zt.id}">
                <img class="img-responsive" src="${dmzImgPath}${zt.wallImgUrl}" alt="${zt.name}">
            </a>
        </div>
    </c:forEach>

    <div class="col-xs-12">
        <div class="demo_line_02"><span>产品分类区</span></div>
    </div>


    <%--<div class="col-xs-12 col-md-6">--%>
    <%--<a href="#">--%>
    <%--<img class="img-responsive" src="http://www.zmz365.com:8081/bb.jpeg" alt="产批名">--%>
    <%--</a>--%>
    <%--</div>--%>
    <%--<div class="col-xs-12 col-md-6">--%>
    <%--<a href="#">--%>
    <%--<img class="img-responsive" src="http://www.zmz365.com:8081/bb1.jpeg" alt="产批名">--%>
    <%--</a>--%>
    <%--</div>--%>

    <%--<div class="col-xs-12 col-md-6">--%>
    <%--<a href="#">--%>
    <%--<img class="img-responsive" src="http://www.zmz365.com:8081/bb2.jpeg" alt="产批名">--%>
    <%--</a>--%>
    <%--</div>--%>
</div>
<div id="ajax_loading" style="width:300px;margin: 10px  auto 15px;text-align:center;">
    <img src="http://www.zmz365.com:8081/loading.gif">
</div>
<jsp:include page="/WEB-INF/view/common/footer.jsp"/>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<%--<script type="text/javascript" src="http://www.zmz365.com:8081/data/tags.js"></script>--%>
<script type="text/javascript">

    $(function () {
        $.ajaxSettings.async = false;
        $(".col>button").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            var url = "<c:url value='/dmz/pmall/show.html?pType='/>" + $(this).attr("data-id");
            window.location.href = url;
        });

        $(".rk").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            window.location.href = $(this).attr("data-url");
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

        var imgShare = "";

        //如果绑定了上级，就会弹出来绑定了上级
        var parent = "${parentName}";
        if (parent != "" && typeof(parent) != "undefined" && parent != null) {
            alert("您关注的小店主人是" + parent);
        }
        var jsapiParamJson =${jsapiParamJson};

        var urlShare = "";
        var shareName = "";
        var realName = "${user.realName}的小店";
        var shareDetail = "";
        <%--url=location.href.split('#')[0]+"&&myCode=${myCode}";//分享链接地址--%>
        urlShare = location.href.split('#')[0];//通过id分享
        if(urlShare.indexOf("?")>-1){
            urlShare+="&&myCode=${user.id}";
        }else {
            urlShare+="?myCode=${user.id}";
        }
        shareName = "${goods.shareName}";
        shareDetail = "${goods.shareDetail}";
        wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: jsapiParamJson.appId, // 必填，公众号的唯一标识
            timestamp: jsapiParamJson.timestamp, // 必填，生成签名的时间戳
            nonceStr: jsapiParamJson.nonceStr, // 必填，生成签名的随机串
            signature: jsapiParamJson.signature,// 必填，签名，见附录1
            jsApiList: jsapiParamJson.jsApiList // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });

        wx.ready(function () {
            //朋友圈分享
            wx.onMenuShareTimeline({
                title: realName + ":让更多的人用上优惠的好产品", // 分享标题
                link: urlShare, // 分享链接
                imgUrl: imgShare, // 分享图标
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
                title: realName + ":让更多的人用上优惠的好产品", // 分享标题
                desc: shareDetail, // 分享描述
                link: urlShare, // 分享链接
                imgUrl: imgShare, // 分享图标
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

        //    var url = "http://www.zmz365.com/dreamer/dmz/pmall/goods/query.json";
        var url = "<c:url value="/dmz/pmall/goods/query.json"/>";

        var start = 0;
        var length = 8;

        function query() {
            var type = "${param.pType}";
            if(type==""){
                type="${pType}";
            }
            var param = {
                "start": start,
                "length": length,
                "useDatatables": true,
                "entity.goodsType.id": type
                <%--// "entity.name": "${parameter.entity.name}",--%>
                <%--// "entity.goodsType.id": "${parameter.entity.goodsType.id}"--%>
            };
            $.getJSON(url, param, function (goodses) {
                if (goodses.length == 0) {
                    $("#ajax_loading").html("----抱歉到底了,即将上传更多新品----").height("5px").css(" margin: 5px auto 5px;");
                } else {
                    goodses.forEach(function (goods, i) {
                        var url = "<c:url value='/dmz/pmall/detail.html?id='/>" + goods.id;
//                        需要登陆
                        <%--var url = "<c:url value='/pmall/detail.html?id='/>" + goods.id;--%>
                        var img = "${dmzImgPath}"+goods.wallImgUrl;
//                    if (img.indexOf("null")>-1){
//                        continue;
//                        img=goods.imgUrl;
//                    }
                        var item = "<div class=\"col-xs-12 col col-md-6\">\n" +
                            "<a href=\"" + url + "\">\n" +
                            "<img style='max-height: 300px' class=\"img-responsive\" src=\"" + img + "\" alt=\"" + goods.name + "\">\n" +
                            "</a>\n" +
                            "</div>";
                        if (img.indexOf("null") > -1) {

                        } else {
                            imgShare = img;
                            $(".products").append($(item));
                        }

                    });
                    start += goodses.length;
                }
            });

        }

        function onScroll() {
            // Check if we're within 100 pixels of the bottom edge of the broser window.
            var winHeight = window.innerHeight ? window.innerHeight : $(window).height(), // iphone fix
                closeToBottom = ($(window).scrollTop() + winHeight > $(document).height() - 100);

            if (closeToBottom) {
                // Get the first then items from the grid, clone them, and add them to the bottom of the grid
                // var $items = $('li', $container),
                //     $firstTen = $items.slice(0, 10).clone().css('opacity', 0);
                // $container.append($firstTen);
                query();

            }
        }

        query();
        $(window).bind('scroll', onScroll);

    });


</script>

</body>
</html>