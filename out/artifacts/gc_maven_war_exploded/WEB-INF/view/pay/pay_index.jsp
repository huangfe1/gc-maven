<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/common/common.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="${keywords}">
<meta http-equiv="description" content="">
<%@include file="/WEB-INF/view/common/head_css.jsp"%>
<%@include file="/WEB-INF/view/common/head_css_typeahead.jsp"%>
<%@include file="/WEB-INF/view/common/head_css_fav.jsp"%>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js">
</script>
<style type="text/css">
html {
	background-color: transparent !important;
}

.carousel-inner>.item img {
	width: 100%;
	height: 115px;
	max-height: 240px;
}

.thumbnail {
	border: none !important;
	box-shadow: none;
	-webkit-box-shadow: none;
	background-color: transparent !important;
}

nav .thumbnail {
	margin-bottom: 2px !important;
}

#nav {
	border-bottom: 1px solid #e0e0e0;
	background-color: #fff;
}

#nav .thumbnail {
	margin-bottom: 0px;
	color: black;
	font-family: Tahoma, Helvetica, Arial, "Microsoft Yahei", STXihei,
		sans-serif;
}

#nav .thumbnail .caption {
	padding: 0px;
}

#nav .thumbnail>img {
	width: 60% !important;
	max-width: 72px !important;
}

.product_img {
	width: 100%;
	max-width: 150px !important;
}

a:hover {
	text-decoration: none !important;
}

.goods-title {
	white-space: nowrap;
}
.panel-red{
border-color: #d9534f;
}
.panel-red .panel-heading {
    border-color: #d9534f;
    color: #fff;
    background-color: #d9534f;
}
</style>
<title>结算</title>
</head>
<script type="text/javascript">
    try {

    var jp=${jsapiParamJson}

        wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: jp.appId, // 必填，公众号的唯一标识
            timestamp: jp.timeStamp, // 必填，生成签名的时间戳  这里是大写与其他地方不一样
            nonceStr: jp.nonceStr, // 必填，生成签名的随机串
            signature: jp.signature,// 必填，签名，见附录1
            jsApiList: [
                'chooseWXPay'
            ]
        });

        wx.error(function(res){
            alert(res.message);
            // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。

        });


    function callpay() {
            wx.chooseWXPay({
                timestamp: jp.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                nonceStr: jp.nonceStr, // 支付签名随机串，不长于 32 位
                package: jp.package, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
                signType: jp.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'Encrypt'
                paySign: jp.paySign, // 支付签名
                success: function (res) {
                    // 支付成功后的回调函数
                    window.location.replace("<c:url value='/pm/order/myOrder.html'/>");
                }
            });
        }

    } catch (err) {
        alert(err.message);
    }
               
     </script>
<body>
<div class="jumbotron">
		<div class="container">
		<div class="row">
			<div class="col-xs-12">
			<h4 class="text-warning">你正在与高臣药业进行微信支付,请核对订单金额完成支付</h4>
			<h6>订单编号：${order.orderNo}</h6>
			<h3>付款金额：${order.actualPayment}元</h3>
			</div>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-xs-12">
				<button class="btn btn-lg btn-success btn-block" onclick="callpay();"><span class="fa fa-weixin"></span>去支付</button>
			</div>
		</div>
		<span>若不能支付,请联系管理员!</span>
	</div>

	<div class="modal fade" id="winModal" tabindex="-1" role="dialog"
		aria-labelledby="winModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="winModalLabel">高臣药业优惠商城</h4>
				</div>
				<div class="modal-body" id="winMessageBody"></div>
			</div>
		</div>
	</div>
<jsp:include page="/WEB-INF/view/common/head.jsp"></jsp:include>
<jsp:include page="/WEB-INF/view/common/form.jsp"></jsp:include>
<jsp:include page="/WEB-INF/view/common/script_common.jsp"></jsp:include>
	<script type="text/javascript">
		$(function() {
			
		});
	</script>
</body>
</html>
