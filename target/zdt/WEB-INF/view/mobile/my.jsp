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
            background: url("resources/mallimages/png.png");
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
            padding-top: 0.6em;
        }

        .profile i {
            color: #e37c6e;
        }

        .info span {
            font-size: 2em;
            float: right;
            line-height: 3.2em;
            color: darkgray;
            margin-right: 1em;
        }

        .label {
            padding: 1.1em 0.8em;
            margin-top: 1.5em;
            background-color: #ffffff;
            overflow: hidden;
            position: relative;

        }

        .label .img {
            /*display: inline-block;*/
            position: absolute;
            left: 1em;
            top: 0.8em;
            width: 2em;
            height: 2em;
        }

        .label .img img {
            display: table-cell;
            vertical-align: middle;
            text-align: center;
            width: 2em;

        }

        .label span {
            margin-left: 3.5em;
            color: black;
            font-size: 1.1em;
            line-height: 1em;
            padding-top: 0.2em;
            display: inline-block;
            /*float: left;*/
        }

        .moreInfo {
            margin-top: 1.5em;
        }

        .moreInfo .label {
            margin-top: 0;
            border-bottom: 1px solid #ebebeb;
        }

        .qr {
            width: 100%;
            position: absolute;
            top: 9%;
            display: none;
        }

        .qr.aniShow {
            -webkit-animation: qrShow 0.5s;
            -webkit-animation-fill-mode: forwards;
        }

        .qr.aniHide {
            -webkit-animation: qrHide 0.5s;
            -webkit-animation-fill-mode: forwards;
        }

        @-webkit-keyframes qrShow /* Safari and Chrome */
        {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }

        @-webkit-keyframes qrHide /* Safari and Chrome */
        {
            from {
                opacity: 1;
            }
            to {
                opacity: 0;
            }
        }

        .qrDiv {
            /*position: absolute;*/
            width: 80%;
            background: #ffffff;
            margin: 0 auto;
            border-radius: 4px;
            z-index: 1500;
            position: relative;
        }

        .headerinfo {
            padding: 2.5em 0 0 2.5em;
            position: relative;
        }

        .headerinfo .headerImg {
            float: left;
            width: 4.7em;
            padding: 0.8em 0.8em 0 0em;
        }

        .headerImg img {
            width: 100%;
            height: auto;
        }

        #qrCode {
            width: 66%;
            /*margin: 2em auto;*/
            margin: 1.3em auto 2em;

        }

        #qrCode img {
            width: 100%;
        }

        .qrbg {
            position: fixed;
            width: 100%;
            height: 100%;
            top: 0;

            background-color: #000;
            filter: alpha(opacity=50);
            -moz-opacity: 0.5;
            opacity: 0.5;
            z-index: 1000;
        }


    </style>
    <title></title>
</head>
<body style="background: rgb(232, 232, 232);">
<div class="con">
    <%--<header>--%>
    <%--<a href="" class="Return"><span></span></a>--%>
    <%--<span class="Title">详细资料</span>--%>
    <%--<a href="" class="Home"><span></span></a>--%>
    <%--</header>--%>
    <a href="
    <c:if test="${agent.agentCode==null}">
        <c:url value='/mobile/register.html?s_unionid=${agent.wxUnionID}&s_openid=${agent.wxOpenid}'/>
    </c:if>
     <c:if test="${agent.agentCode!=null}">
     #
</c:if>

    ">
        <div class="info">
            <div class="avatar">
                <%--<img src="${ctx}/resources/imagestem/ava.jpeg" alt="">--%>
                <img src="${agent.headimgurl}" alt="">
            </div>
            <div class="profile">
                <h3>${agent.realName}</h3>
                <c:if test="${agent.agentCode==null}">
                    <p class="code">编号:点击完善信息</p>
                </c:if>
                <c:if test="${agent.agentCode!=null}">
                    <p class="code">编号:${agent.agentCode}</p>
                </c:if>
                <%--<p class="code">编号:${agent.agentCode}</p>--%>
                <p class="phone">级别:${agent.levelName}</p>
            </div>
            <c:if test="${isVip}">
                <span id="qrBtn"><i class="fa fa-qrcode" aria-hidden="true"></i></span>
            </c:if>

        </div>
    </a>

    <div class="label" id="wallet">
        <div class="img">
            <img src="${ctx}/resources/mallimages/wxqb.png" alt="">
        </div>
        <span>钱包</span>
        <!--钱包-->
    </div>


        <div class="moreInfo">
            <%--<div class="label zq">--%>
                <%--<div class="img">--%>
                    <%--<img src="${ctx}/resources/mallimages/zq.png" alt="">--%>
                <%--</div>--%>
                <%--<span>转券</span>--%>
            <%--</div>--%>
            <div class="label jh">
                <div class="img">
                    <img src="${ctx}/resources/mallimages/jh.png" alt="">
                </div>
                <span>进货</span>
            </div>
            <%--<div class="label fh">--%>
                <%--<div class="img">--%>
                    <%--<img src="${ctx}/resources/mallimages/fh.png" alt="">--%>
                <%--</div>--%>
                <%--<span>发货</span>--%>
            <%--</div>--%>
            <%--<div class="label gw">--%>
                <%--<div class="img">--%>
                    <%--<img src="${ctx}/resources/mallimages/yhsc.png" alt="">--%>
                <%--</div>--%>
                <%--<span>购物</span>--%>
            <%--</div>--%>
            <%--<div class="address label">--%>
            <%--<div class="img">--%>
            <%--<img src="${ctx}/resources/mallimages/wxqb.png" alt="">--%>
            <%--</div>--%>
            <%--<span>扫一扫</span>--%>
            <%--</div>--%>
        </div>


    <div class="moreInfo">
        <%--<div class="label dd">--%>
            <%--<div class="img">--%>
                <%--<img src="${ctx}/resources/mallimages/dd.png" alt="">--%>
            <%--</div>--%>
            <%--<span>订单</span>--%>
        <%--</div>--%>
        <div class="label bg">
            <div class="img">
                <img src="${ctx}/resources/mallimages/bg.png" alt="">
            </div>
            <span>包裹</span>
        </div>
        <%--<div class="address label">--%>
        <%--<div class="img">--%>
        <%--<img src="${ctx}/resources/mallimages/wxqb.png" alt="">--%>
        <%--</div>--%>
        <%--<span>扫一扫</span>--%>
        <%--</div>--%>
    </div>

    <%--<div class="label" id="zx" style="margin-bottom: 6em">--%>
        <%--<div class="img">--%>
            <%--<img src="${ctx}/resources/mallimages/wxsz.png" alt="">--%>
        <%--</div>--%>
        <%--<span>注销</span>--%>
        <%--<!--钱包-->--%>
    <%--</div>--%>

        <div class="label" id="zx" style="margin-bottom: 6em">
            <div class="img">
                <img src="${ctx}/resources/mallimages/wxsz.png" alt="">
            </div>
            <span>设置</span>
            <!--钱包-->
        </div>


    <div class="qr">
        <div class="qrDiv">
            <div class="headerinfo">
                <div class="headerImg">
                    <%--<img src="${ctx}/resources/imagestem/ava.jpeg" alt="">--%>
                    <img src="${agent.headimgurl}" alt="">
                </div>
                <div class="profile">
                    <h3>${agent.realName}</h3>
                    <p>${agent.agentCode}</p>
                    <p>${agent.mobile}</p>
                </div>
            </div>
            <div id="qrCode">
                <img src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=${qrcodeUrl}" alt="">
            </div>
            <h5 style="text-align: center;color: #a5a5a5;">推广二维码，加入高臣!</h5>
            <br>
        </div>
        <div class="qrbg"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/view/mobile/appfooter.jsp"/>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="http://apps.bdimg.com/libs/jquery-qrcode/1.0.0/jquery.qrcode.min.js"></script>
<script type="text/javascript">

    $(function () {

//        var qrWidh = parseInt($(".qrDiv").width() * 0.66);

//        var qrHeight=parseInt($(".qrDiv").height()/2);
//
//        $("#qrCode").qrcode({
//            text: 'http://www.cnblogs.com/jingjing-blog/p/5012923.html',
//            width: qrWidh,
//            height: qrWidh,
//            colorDark: '#000000',
//            colorLight: '#ffffff'
//        });

        $("#wallet").click(function () {
            var url = "<c:url value="/mobile/wallet.html"/>"
            window.location.href=url;
        });

        //注销按钮
        $("#zx").click(function () {
//            if (confirm("将取消当前账号与微信的绑定，是否继续！")) {
                window.location.href = "<c:url value='/mobile/set.html'/>";
//            }
        })


        $("#qrBtn").click(function (e) {
            e.stopPropagation();
            e.preventDefault();
            $(".qr").show();
            $(".qr").removeClass("aniHide");
            $(".qr").addClass("aniShow");

        });

        $(".qr").click(function () {
            $(".qr").removeClass("aniShow");
            $(".qr").addClass("aniHide");
            var setInt_obj = setInterval(function () {
                $(".qr").hide();
                clearInterval(setInt_obj);
            }, 0.5 * 1000);
        });

        //发货
        $(".bg").click(function () {
            window.location.href="<c:url value="/mobile/delivery/records.html"/>";
        })

        //订单
        $(".dd").click(function () {
            window.location.href="<c:url value="/mobile/transfer/records.html"/>";
        })

        //进货
        $(".jh").click(function () {
            <c:if test="${user.agentCode ==null || user.agentCode eq ''}">
            alert("请先完善信息！");
            </c:if>
            <c:if test="${user.agentCode !=null && user.agentCode ne ''}">
            window.location.href="<c:url value="/dmz/mobile/index.html"/>";
            </c:if>
        })

        //发货
        $(".fh").click(function () {
            alert("请选择收货人!");
            window.location.href="<c:url value="/mobile/contacts.html"/>";
        })

        //优惠商城
        $(".gw").click(function () {
            window.location.href="<c:url value="/dmz/pmall/show.html"/>";
        })

        //转券
        $(".zq").click(function () {
            alert("请选择收款人！");
            window.location.href="<c:url value="/mobile/contacts.html"/>";
        })


    })


</script>


</body>
</html>