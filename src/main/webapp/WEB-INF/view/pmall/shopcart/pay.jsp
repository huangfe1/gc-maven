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
    <%--<link href="${ctx}/resources/mallcss/initcss.css" rel="stylesheet">--%>
    <%--<link href="${ctx}/resources/mallcss/common.css" rel="stylesheet">--%>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <style rel="stylesheet">

        body {
            background-color: #e8e8e8;
        }

        .con {
            background-color: #e8e8e8;
            overflow: hidden;
        }

        header {
            height: 3.7em;
            background-color: #f03792;
            position: relative;
            text-align: left;
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
            padding-left: 4em;

        }

        .content {
            padding: 0.8em 0.8em 0;
        }

        .panel {
            margin-bottom: 0.8em;
        }

        .panel-heading {
            background-color: #f5f5f5;
            padding: 0.5em;
        }

        .panel-heading i {
            color: #eba601;
            padding: 0 0.5em;
        }

        .list-group {
            color: #9b9999;
        }

        .list-group select {
            color: #9b9999;
        }

        .panel .form-control {
            -webkit-appearance: none;
            height: 3.5em;
            border-radius: 0;
            border: none;
            -webkit-box-shadow: none;
            box-shadow: none;
        }

        .panel .form-control {
            border-bottom: 1px solid #e8e8e8;
        }

        .panel select:focus {
            /*outline: none;*/
            -webkit-box-shadow: none;
            box-shadow: none;
        }

        .fh, .addressDiv {
            position: relative;
        }

        .fh i, .addressDiv i {
            position: absolute;
            top: 1.2em;
            right: 1.5em;
            color: #888888;
        }

        .list-group-item i {
            float: right;
            padding-right: 0.5em;
        }

        .radio {
            margin: 0.3em 0;
        }

        .radio label {
            width: 100%;
        }

        a.edit {
            background-color: #f43793;
            display: inline-block;
            border-radius: 0.5em;
            padding: 0 0.2em;
            color: white;
            float: right;
            margin-right: 0.5em;
            text-decoration: none;
        }

        .result {
            background-color: white;
            padding: 1.5em 1em;
        }

        .pay {
            margin-left: 1em;
            color: white;
            text-align: center;
            float: right;
            background-color: #e0217e;
            width: 5em;
            height: 2em;
            /*font-size: 1.5em;*/
            line-height: 2em;
            border-radius: 1em;
        }

        .payName{
            position: absolute;
            left: 4em;
            top: 1.2em;
            z-index: 50;
        }
        .payWay{
            height: 100%;

        }
        input[type=radio] {
            position: absolute;
            margin: 0 0 0;
            /*margin-left: -20px;*/
        }




    </style>
    <title></title>
</head>
<body style="margin: 0 auto;">
<div class="con">
    <%--<header>--%>
    <%--<a href="javascript:history.go(-1)" class="Return">--%>
    <%--<span></span>--%>
    <%--</a>--%>
    <%--<span class="Title">支付确认</span>--%>
    <%--<a href="<c:url value="/dmz/vmall/index.html?ref=01"/>" class="Home"><span></span></a>--%>
    <%--</header>--%>
    <div class="content">
        <form method="post" action="">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="fa fa-credit-card" aria-hidden="true"></i>芝德堂收银台</div>
                <ul class="list-group">
                    <li class="list-group-item">
                        总金额:<span style="font-size: 1.2em;color: red"> ${order.amount}￥</span>
                    </li>
                </ul>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading"><i class="fa fa-credit-card" aria-hidden="true"></i>支付方式</div>


                <ul class="list-group">

                    <li class="list-group-item">
                        <div class="radio ">
                            <label>
                                <input aria-selected="true" type="radio" name="payWay" class="payWay"
                                       value="0" checked>
                                微信支付
                            </label>
                        </div>

                    </li>

                    <%--<li class="list-group-item">--%>
                        <%--<div class="radio ">--%>
                            <%--<label>--%>
                                <%--<input aria-selected="true" type="radio" name="payWay" class="payWay" value="1">--%>
                                <%--代金券支付(${agent.accounts.voucherBalance})--%>
                            <%--</label>--%>
                        <%--</div>--%>
                    <%--</li>--%>





                    <!--是否可以预存款-->
                    <c:if test="${order.canAdvance}">
                        <li class="list-group-item">
                            <div class="radio ">
                                <label>
                                    <input aria-selected="true" type="radio" name="payWay" class="payWay"
                                           value="4">
                                </label>
                            </div>
                            <span class="payName">  预存款支付(${agent.accounts.advanceBalance})</span>
                        </li>
                    </c:if>

                </ul>
            </div>

            <button id="pay" type="button" data-loading-text="正在支付..." class="form-control btn-danger">立即支付</button>
        </form>
    </div>


</div>


<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script type="text/javascript">

    $(function () {


        $("#pay").click(function () {
            $("#pay").button('loading');
            var payWay = $('input:radio:checked').val();
            if (payWay == 0) {//微信支付
                callPay();
            } else if (payWay == 1) {//代金券支付
                payByAccounts(0);
            } else if (payWay == 4) {//预存款支付
                payByAccounts(2);
            }
        })

        function payByAccounts(accountsType) {
            var params = {
                "id":${order.id},
                "accountsType": accountsType
            };
            $.post("<c:url value='/pmall/shopcart/accountsPay.json'/>",
                params,
                function (data, status, jqXHR) {
                    var m = data;
                    if (m.flag == "0") {
//                        alert("订单提交成功，前往支付页面!");
                        window.location.href = "<c:url value='/pm/order/myOrder.html'/>";
                    } else {
                        alert("操作失败" + m.message);
                        $("#pay").button('reset');
//                        window.location.reload();
                    }
                }).fail(function (xhr) {
                if (xhr.status == 401) {
                    window.location = xhr.getResponseHeader("Location");
                } else {
                    alert("未知错误请联系管理员" + xhr.status);
//                    window.location.reload();
                }
            });
        }


    });


    var jp = ${jsApiParam};

    function onBridgeReady() {
        WeixinJSBridge.invoke(
            'getBrandWCPayRequest', {
                "appId": jp.appId,     //公众号名称，由商户传入
                "timeStamp": jp.timeStamp,         //时间戳，自1970年以来的秒数
                "nonceStr": jp.nonceStr, //随机串
                "package": jp.package,
                "signType": jp.signType,         //微信签名方式：
                "paySign": jp.paySign //微信签名
            },
            function (res) {
                if (res.err_msg == "get_brand_wcpay_request:ok") {
                    var str = "<c:url value="/pm/order/myOrder.html"/>";
                    window.location.href = str;
                } else {
                    $("#pay").button('reset');
                }
            }
        );
    }

    function callPay() {
        if (typeof WeixinJSBridge == "undefined") {
            if (document.addEventListener) {
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            } else if (document.attachEvent) {
                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            }
        } else {
            onBridgeReady();
        }

    }


</script>

</body>
</html>