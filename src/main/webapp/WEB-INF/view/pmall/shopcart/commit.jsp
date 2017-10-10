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
    <link href="${ctx}/resources/mallcss/common.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <style rel="stylesheet">

        /*p{*/
        /*padding: 0.5em 0;*/
        /*}*/

        li{
            list-style: none;
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

        /*.pay {*/
        /*margin-left: 1em;*/
        /*color: white;*/
        /*text-align: center;*/
        /*float: right;*/
        /*background-color: #e0217e;*/
        /*width: 5em;*/
        /*height: 2em;*/
        /*!*font-size: 1.5em;*!*/
        /*line-height: 2em;*/
        /*border-radius: 1em;*/
        /*}*/


    </style>
    <title>支付页面</title>
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
                <!--<div class="panel-heading"><i class="fa fa-user" aria-hidden="true"></i>是否立即发货</div>-->
                <div class="panel-heading"><i class="fa fa-plane" aria-hidden="true"></i>选择收货地址</div>


                <ul class="list-group">

                    <div class="addressDiv">
                        <select class="form-control addressSelect">
                            <option value="-1">选择地址</option>
                            <option value="-1"><span style="background-color: red">++++点我-新增地址-点我++++</span></option>
                            <c:forEach items="${addressMyList}" var="address" varStatus="sta">
                                <option value="${address.id}">${address.consignee}--${address.address}</option>
                            </c:forEach>
                        </select>
                        <i class="fa fa-chevron-right" aria-hidden="true"></i>
                    </div>


                    <%--<li class="list-group-item form-control">--%>
                    <%--新增地址--%>
                    <%--<i class="fa fa-chevron-down" aria-hidden="true"></i>--%>
                    <%--</li>--%>

                </ul>
            </div>


            <!--地址-->
            <div class="panel panel-default addressPanel" style="display: none">
                <div class="panel-heading"><i class="fa fa-map-marker" aria-hidden="true"></i>填写新地址
                </div>

                <ul class="list-group">
                    <input placeholder="收货人" id="consignee" name="consignee" type="text" class="form-control" value=""/>
                    <input placeholder="收货人编号/没有可不填" id="consigneeCode" name="consigneeCode" type="text" class="form-control"
                           value=""/>
                    <input placeholder="收货电话" id="mobile" name="mobile" type="tel" class="form-control" value=""/>

                    <div data-toggle="distpicker">
                        <select id="province" name="province" class="form-control"></select>
                        <select id="city" name="city" class="form-control"></select>
                        <select id="county" name="county" class="form-control"></select>
                    </div>
                    <input id="address" name="address" id="address" class="form-control" placeholder="详细地址，请具体到门牌号"/>
                </ul>
            </div>


            <%--<div class="panel panel-default">--%>
            <%--<div class="panel-heading"><i class="fa fa-credit-card" aria-hidden="true"></i>支付方式</div>--%>


            <%--<ul class="list-group">--%>

            <%--<li class="list-group-item">--%>
            <%--<div class="radio ">--%>
            <%--<label>--%>
            <%--<input aria-selected="true" type="radio" name="payWay" id="optionsRadios1" value="${payWays[0].type}" checked>--%>
            <%--微信支付--%>
            <%--</label>--%>
            <%--</div>--%>
            <%--</li>--%>

            <%--<li class="list-group-item">--%>
            <%--<div class="radio ">--%>
            <%--<label>--%>
            <%--<input aria-selected="true" type="radio" name="payWay" id="optionsRadios1" value="${payWays[1].type}" >--%>
            <%--代金券支付(${agent.accounts.voucherBalance})--%>
            <%--</label>--%>
            <%--</div>--%>
            <%--</li>--%>
            <%--&lt;%&ndash;<li class="list-group-item">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div class="radio">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<label>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<input type="radio" name="payWay" id="optionsRadios2"&ndash;%&gt;--%>
            <%--&lt;%&ndash;value="option2">电子币支付（${user.accounts.voucherBalance}）&ndash;%&gt;--%>
            <%--&lt;%&ndash;</label>&ndash;%&gt;--%>

            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</li>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<li class="list-group-item">&ndash;%&gt;--%>
            <%--&lt;%&ndash;微信支付&ndash;%&gt;--%>
            <%--&lt;%&ndash;<i class="fa fa-chevron-down" aria-hidden="true"></i>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</li>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<li class="list-group-item">&ndash;%&gt;--%>
            <%--&lt;%&ndash;使用积分&ndash;%&gt;--%>
            <%--&lt;%&ndash;<i class="fa fa-chevron-down" aria-hidden="true"></i>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</li>&ndash;%&gt;--%>
            <%--</ul>--%>
            <%--</div>--%>


            <div class="panel panel-default">
                <div class="panel-heading"><i class="fa fa-shopping-bag" aria-hidden="true"></i>商品列表 <a
                        href="<c:url value="/pmall/shopcart/index.html"/>" class="edit">修改</a>
                </div>

                <ul class="list-group">


                    <c:forEach items="${pmshopcart.items}" var="item">
                        <li class="list-group-item " style="text-align: right;">
                            <div class="row">
                                <div class="col-xs-7"><p style="text-align: left;margin: 0">${item.value.goods.name}</p>
                                </div>
                                <div class="col-xs-2">X${item.value.quantity}</div>
                                <div class="col-xs-3">￥${item.value.goods.retailPrice}</div>
                            </div>
                        </li>
                    </c:forEach>
                    <li class="list-group-item " style="text-align: right;">
                        <div class="row">
                            <div class="col-xs-7"><p style="text-align: left;margin: 0">总计:</p>
                            </div>
                            <div class="col-xs-2">X${pmshopcart.quantity}</div>
                            <div class="col-xs-3">￥${pmshopcart.amount}</div>
                        </div>
                    </li>
                    <input type="text" class="form-control remark" placeholder="订单附言"/>

                </ul>
            </div>

        </form>
    </div>

    <div class="result">
        <%--<span>会员价:</span>--%>
        <%--<span class="totalPrice" style="color:red;">￥${pmshopcart.amount}</span>--%>
        <%--<span class="pay" style="background-color: red">订单提交</span>--%>
        <button class="form-control btn-danger pay" data-loading-text="正在提交..">订单提交</button>
    </div>
    <jsp:include page="../../mobile/footer.jsp"/>

</div>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel"></h4>
            </div>
            <div class="modal-body" id="alertMessageBody"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success btn-block quitBtn"
                        tabIndex="26" id="quitBtn" data-dismiss="modal" name="quitBtn"
                        value="login" tabindex="4" data-loading-text="正在跳转......">
                    <span class="glyphicon glyphicon-remove-sign">&nbsp;</span>关闭
                </button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdn.bootcss.com/distpicker/2.0.0-rc/distpicker.min.js"></script>
<script>
    $(function () {

        function validate(mobile) {
            mobile=mobile.trim();
//            var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
//            if (!myreg.test(mobile)) {
//                return false;
//            }
            if(mobile.length<11){
                return false;
            }
            return true;
        }

        function validateStr(str) {
            if (str == "" || str == null) {
                return false;
            }
            return true;
        }


        $(".pay").click(function () {
//            $(this).html("正在提交..");
            var params = {};
            var remark = $(".remark").val();
//            var transferWay = $(".transferWay").val();
            var transferWay = 0;
            if (transferWay == 0) {//直接发货
                var addressVal = $(".addressSelect").val();
                if (addressVal == -1) {
                    var consignee = $("#consignee").val();
                    var consigneeCode = $("#consigneeCode").val();
                    var mobile = $("#mobile").val();
                    var province = $("#province").val();
                    var city = $("#city").val();
                    var county = $("#county").val();
                    var address = $("#address").val();
                    params = {
                        "remark": remark,
                        "consignee": consignee,
                        "consigneeCode": consigneeCode,
                        "mobile": mobile,
                        "province": province,
                        "city": city,
                        "county": county,
                        "address": address
                    };

                    if (!validateStr(consignee)) {
                        $("#alertMessageBody").empty().html("请填写收货人");
                        $("#myModal").modal({backdrop: "static", show: true});
                        return;
                    }

                    if (!validateStr(province)) {
                        $("#alertMessageBody").empty().html("请填写省份");
                        $("#myModal").modal({backdrop: "static", show: true});
                        return;
                    }

                    if (!validateStr(address)) {
                        $("#alertMessageBody").empty().html("请填写地址");
                        $("#myModal").modal({backdrop: "static", show: true});
                        return;
                    }

                    if (!validateStr(mobile)) {
                        $("#alertMessageBody").empty().html("请选择地址");
                        $("#myModal").modal({backdrop: "static", show: true});
                        return;
                    } else {
                        if (!validate(mobile)) {
                            $("#alertMessageBody").empty().html("手机号码格式不对！");
                            $("#myModal").modal({backdrop: "static", show: true});
                            return;
                        }
                    }
                } else {
                    params = {
                        "remark": remark,
                        "id": addressVal
                    };
                }
                applyDelivery(params);
            } else {//转货
//                params = {
//                    "remittance": remark
//                }
//                commitOrPay(params);
            }
            $(".pay").button('loading');
        })


        //直接发货
        function applyDelivery(params) {
            $.post("<c:url value='/pmall/shopcart/commit.json'/>",
                params,
                function (data, status, jqXHR) {
                    var m = data;
                    if (m.flag == "0") {
//                        alert("订单提交成功，前往支付页面!");
                        window.location.href = "<c:url value='/pmall/shopcart/pay.html?id='/>"+m.data;
                    } else {
                        alert("操作失败" + m.message);
                        $(".pay").button('reset');
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


        //提交转货
        <%--function commitOrPay(params) {--%>
        <%--$(this).html("正在提交..");--%>
        <%--$.post("<c:url value='/transfer/hf_applyFrom.json'/>",--%>
        <%--params--%>
        <%--,--%>
        <%--function (data, status, jqXHR) {--%>
        <%--var m = data;--%>
        <%--if (m.flag == "0") {--%>
        <%--$("#alertMessageBody").empty().html("商品订单提交成功,前往订单支付页面完成支付");--%>
        <%--$("#myModal").modal({backdrop: "static", show: true});--%>
        <%--window.setTimeout(function () {--%>
        <%--window.location.href = "<c:url value="/vmall/uc//index.html"/>";--%>
        <%--}, 1000);--%>

        <%--} else {--%>
        <%--alert("操作失败" + m.message);--%>
        <%--window.location.reload();--%>
        <%--}--%>
        <%--}).fail(function (xhr) {--%>
        <%--if (xhr.status == 401) {--%>
        <%--window.location = xhr.getResponseHeader("Location");--%>
        <%--} else {--%>
        <%--alert("未知错误请联系管理员" + xhr.status);--%>
        <%--window.location.reload();--%>
        <%--}--%>
        <%--});--%>
        <%--}--%>


        $(".transferWay").change(function () {
            if ($(this).val() == 1) {
                $(".addressDiv").hide();
            } else {
                $(".addressDiv").show();
            }
        });

        $(".addressSelect").change(function () {
            if ($(this).val() == -1) {
                //先清除数据 再显示
                $(".addressPanel").find(".form-control").val("");
                $(".addressPanel").show();
            } else {
                $(".addressPanel").hide();
            }
        });

    })
</script>

</body>
</html>