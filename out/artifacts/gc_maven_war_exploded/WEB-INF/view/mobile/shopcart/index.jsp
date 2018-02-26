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
    <link href="${ctx}/resources/mallcss/sliderBar.css" rel="stylesheet">
    <style rel="stylesheet">

        .con {
            background-color: #e8e8e8;
            overflow: scroll;
        }

        header {
            height: 3.7em;
            background-color: #f03792;
            position: relative;
            text-align: center;
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

        }

        .cartItems {
            background-color: #e8e8e8;
            padding: 1em 0.5em 0.5em;
        }

        .cartItems ul li {
            background-color: #ffffff;
            border-radius: 0.3em;
            border-bottom: dashed 1px #dedede;
        }

        .item {
            padding: 0.5em 0.3em;
            overflow: hidden;
            position: relative;
            height: 6.5em;
        }

        .item .imgDiv {
            float: left;
            width: 30%;
            height: 100%;
        }

        .imgDiv img {
            width: 100%;
            height: 100%;
        }

        .item .info {
            margin-left: 0.5em;
            float: left;
            width: 50%;

        }

        .info h3 {
            font-size: 1em;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .buy {
            position: absolute;
            bottom: 0.5em;
        }

        .inputs {
            /*margin-left: 0.5em;*/
            overflow: hidden;
        }

        .buy .sup, .plus {
            float: left;
            text-align: center;
            display: inline-block;
            background-color: #eee;
            width: 2em;
            font-size: 1em;
            line-height: 2em;
        }

        .inputs input {
            font: inherit;
            height: 2em;
            text-align: center;
            float: left;
            width: 5em;
            padding: 0;
            margin: 0;
            outline: 0;
            border: none;
        }

        .price {
            text-align: right;
            padding: 0 0.5em 0 0;
            overflow: hidden;
            /*position: relative;*/
            /*float: right;*/
        }

        .price .remove {
            background-color: #eee;
            width: 2em;
            font-size: 1em;
            line-height: 2em;
            display: inline-block;
            text-align: center;
            position: absolute;
            bottom: 0.5em;
            right: 0.5em;
        }

        .result {
            background-color: white;
            padding: 1.5em 1em;
        }

        .toTransfer, .toDelivery, .toHome {
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

        .address {
            /*margin-top: 1em;*/
            padding: 0.5em 1em 0.5em 0.5em;
            background-color: #ffffff;
            height: auto;
            position: relative;
            border-bottom: dashed 1px #dedede;
        }

        .address:first-child {
            border-top: dashed 1px #dedede;
        }

        .address .info {
            position: relative;
            overflow: hidden;
        }

        .info .addrImg {
            width: 3.7em;
            height: 3.7em;
            float: left;
            position: relative;
        }

        .addrImg span {
            width: 1.7em;
            height: 1.7em;
            background: url("${ctx}/resources/mallimages/png.png") -15.65em 0 no-repeat;
            background-size: 17em;
            font-size: 1em;
            line-height: 2em;
            position: absolute;
            top: 1.2em;
            left: 1em;
        }

        .info .adrPhone {
            position: absolute;
            right: 1em;
        }

        .info .adrDetail {
            margin-top: 1em;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            /*position: absolute;*/
            /*bottom: 0;*/
        }

        .address a {
            position: absolute;
            top: 1em;
            right: 0;
            height: 3.7em;
            width: 3.7em;
        }

        .address a span {
            width: 1.7em;
            height: 1.7em;
            background: url("${ctx}/resources/mallimages/png.png") -6.5em -5.2em no-repeat;
            background-size: 17em;
            /*position: absolute;*/
            float: right;
            margin-right: 0.5em;
            margin-top: 0.68em;
            /*top: 0.5em;*/
            /*left: 1em;*/
        }

        /*地址层*/

        .st-menu {
            width: 100%;
            margin-top: 3.7em;
            background-color: white;
        }


        nav{
            overflow: scroll;
        }

        #selectAddress{
            margin-bottom:5em;
        }

    </style>
    <title>购物车</title>
</head>
<body>
<div id="st-container" class="st-container con">
    <%--<header>--%>
        <%--<a href="javascript:history.go(-1)" class="Return">--%>
            <%--<span></span>--%>
        <%--</a>--%>
        <%--<span class="Title">购物车</span>--%>
        <%--<a href="<c:url value="/dmz/mall/index.html"/>" class="Home"><span></span></a>--%>
    <%--</header>--%>

    <div class="cartItems">
        <ul>
            <c:forEach items="${shopcart.items}" var="item">
                <li>
                    <div class="item">
                        <div class="imgDiv">
                            <img src="${dmzImgPath}${item.value.goods.imgFile}" alt="">
                        </div>
                        <div class="info">
                            <h3>${item.value.goods.name}</h3>
                            <p>规格：${item.value.goods.spec}</p>
                            <div class="buy">
                                <div class="inputs">
                                    <span class="sup">-</span>
                                    <input class="inp" readonly type="text" value="${item.value.quantity}"
                                           data-id="${item.value.goods.id}">
                                    <span class="plus">+</span>
                                </div>
                            </div>
                        </div>
                        <div class="price">
                            <p>￥<strong class="nums">${item.value.price}</strong></p>
                            <p>￥
                                <del>${item.value.goods.retailPrice}</del>
                            </p>
                            <p><span class="remove" data-id="${item.value.goods.id}">x</span></p>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div class="result">
        <c:if test="${shopcart!=null&&not empty shopcart&&shopcart.quantity>0}">
        <span>会员价:</span>
        <span class="totalPrice">￥${shopcart.amount}</span>
        <c:if test="${isVip}">
            <span class="toDelivery st-trigger-effects" data-effect="st-effect-1">提交订单</span>
            <%--<span class="toTransfer">转入库存</span>--%>
        </c:if>
        </c:if>
        <c:if test="${shopcart==null or  empty shopcart or shopcart.quantity<1}">
            <span>购物车为空</span>
            <a href="<c:url value="/dmz/mobile/index.html"/>"><span class="toHome">继续购物</span></a>
        </c:if>
    </div>

    <nav class="st-menu st-effect-1" id="menu-1">
        <p style="text-align: center;padding: 1em 0">选择或者新增收货地址</p>
        <div id="addAddress" style="display: none">
            <div class="form-group">
                <input type="text" class="form-control" value="" id="addrName" placeholder="收货人">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" value="" id="addrCode" placeholder="收货人编号,没有可不填">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" id="addrPhone" placeholder="收货人手机号">
            </div>
            <div class="form-inline">
                <div data-toggle="distpicker">
                    <div class="form-group">
                        <select id="addrProvince" class="form-control"></select>
                    </div>
                    <div class="form-group">
                        <select id="addrCity" class="form-control"></select>
                    </div>
                    <div class="form-group">
                        <select id="addrCounty" class="form-control"></select>
                    </div>
                </div>
            </div>
            <div class="form-group">
                    <%--<label for="exampleInputPassword1">Password</label>--%>
                <textarea class="form-control" id="address" placeholder="详细地址"></textarea>
            </div>

            <div class="form-group">
                    <%--<label for="exampleInputPassword1">Password</label>--%>
                <button id="submitBtn" type="button" class="btn btn-danger form-control">确定发货</button>
            </div>
        </div>
        <div id="selectAddress">
            <c:forEach var="address" items="${addresses}" varStatus="sta">
                <div class="address ">
                    <input type="hidden" class="aid" value="${address.id}">
                    <div class="info">
                        <div class="addrImg"><span></span></div>
                        <p>
                            <span class="adrName">收件人:${address.consignee} &nbsp; &nbsp;${address.consigneeCode}</span>
                            <span class="adrPhone">${address.mobile}</span>
                        </p>
                        <p class="adrDetail">地址：${address.mobile}</p>
                    </div>
                    <a href=""><span class="toEdit"></span></a>
                </div>
            </c:forEach>

            <div class="form-group" style="margin-top: 1em">
                    <%--<label for="exampleInputPassword1">Password</label>--%>
                <button id="addBtn" type="button" class="btn btn-danger form-control">新增地址</button>
            </div>


        </div>

    </nav>

</div>


<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="${ctx}/resources/malljs/classie.js"></script>
<script src="${ctx}/resources/malljs/sidebarEffects.js"></script>
<script src="https://cdn.bootcss.com/distpicker/2.0.0-rc/distpicker.min.js"></script>
<script>
    $(function () {
        $(".sup").click(function (e) {
            e.stopPropagation();
            e.preventDefault();
            var quantity = $(this).closest("div").find(".inp");
            var v = parseInt(quantity.val());
            if (isNaN(v)) {
                v = 1;
            }
            quantity.val(v - 1).change();
        });

        $(".plus").click(function (e) {
            e.stopPropagation();
            e.preventDefault();
            var quantity = $(this).closest("div").find(".inp");
            var v = parseInt(quantity.val());
            if (isNaN(v)) {
                v = 1;
            }
            quantity.val(v + 1).change();
        });

        $(".remove").click(function () {
            var id = $(this).attr("data-id");
            $.post("<c:url value='/vmall/shopcart/remove.json'/>",
                {
                    "goodsId": id
                }
                ,
                function (data, status, jqXHR) {
                    var m = data;
                    if (m.flag == "0") {
//                        calcTotal();
                        window.location.reload();
                    } else {
                        alert("操作失败" + m.message);
                    }
                }).fail(function (xhr) {
                if (xhr.status == 401) {
                    window.location = xhr.getResponseHeader("Location");
                } else {
                    alert("未知错误请联系管理员" + xhr.status);
                }
            });
        });
        
        
        $(".inp").click(function () {
            var value = prompt("请输出数量");
            if(!isNaN(value)){
                $(this).val(value);
                $(this).change();
            }else{
                alert("请输入数字");
            }

        })


        $(".inp").change(function (e) {
            var $this = $(this);
            if (!parseInt($this.val())) {
                $this.val(1);
            }
            if (parseInt($this.val()) < 1) {
                $this.val(1);
            }
            var inp = $(this);

            var id = $(this).attr("data-id");
            $.post("<c:url value='/vmall/shopcart/put.json'/>",
                {
                    "goodsId": id,
                    "quantity": inp.val()
                }
                ,
                function (data, status, jqXHR) {
                    var m = data;
                    if (m.flag == "0") {
                        calcTotal();
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


        var canClick = true;
        $(".toTransfer").click(function () {
            if (!canClick)return;
            $(this).html("提交....");
            canClick = false;
            commitOrPay();
        });

        //新增地址
        $("#addBtn").click(function () {
            $("#addAddress").show();
            $("#selectAddress").hide();
        });

        //选择地址
        $(".address").click(function () {
            var aid = $(this).children(".aid").val();
            if (confirm("确定发给这个地址？")) {
                var remark = prompt("如果需要备注，请填写");
                if (remark == "") remark = "未备注";
                var params = {"id": aid, "remark": remark};
                applyDelivery(params);
            }
        });

        //确定发货
        $("#submitBtn").click(function () {
            var remark = prompt("如果需要备注，请填写");
            if (remark == "") remark = "未备注";
            var addrName = $("#addrName").val();
            if (addrName == "") {
                alert("名字不能为空");
                return;
            }
            var addrCode = $("#addrCode").val();
            var addrPhone = $("#addrPhone").val();
            if (addrPhone == "") {
                alert("电话不能为空");
                return;
            }
            var addrProvince = $("#addrProvince").val();
            if (addrProvince == "") {
                alert("省份不能为空");
                return;
            }
            var addrCity = $("#addrCity").val();
            if (addrCity == "") {
                alert("市区不能为空");
                return;
            }
            var addrCounty = $("#addrCounty").val();
//            if (addrCounty == "") {
//                alert("县不能为空");
//                return;
//            }
            var address = $("#address").val();
            if (address == "") {
                alert("详细地址不能为空");
                return;
            }
            var params = {
                "remark": remark,
                "consignee": addrName,
                "consigneeCode": addrCode,
                "mobile": addrPhone,
                "province": addrProvince,
                "city": addrCity,
                "county": addrCounty,
                "address": address
            };
            applyDelivery(params);
        });

        //直接发货
        function applyDelivery(params) {

            $.post("<c:url value='/transfer/delivery.json'/>",
                params,
                function (data, status, jqXHR) {
                    var m = data;
                    if (m.flag == "0") {
                        alert("提交成功,请及时支付!");
                        window.location.href = "<c:url value='/mobile/delivery/records.html'/>";
                    } else {
                        alert("操作失败" + m.message);
//                        window.location.reload();
                    }
                }).fail(function (xhr) {
                if (xhr.status == 401) {
                    window.location.href = xhr.getResponseHeader("Location");
                } else {
                    alert("未知错误请联系管理员" + xhr.status);
                    console.log(xhr);
//                    window.location.reload();
                }
            });
        }

        function commitOrPay() {
            var remark = prompt("如果需要备注，请填写");
            if (remark == "") remark = "未备注";
            $.post("<c:url value='/transfer/autoConfirm.json'/>",
                {
                    "remark": remark
                },
                function (data, status, jqXHR) {
                    var m = data;
                    if (m.flag == "0") {
                        alert("提交成功!");
                        window.location.href = "<c:url value='/mobile/transfer/records.html'/>";
                    } else {
                        alert("操作失败" + m.message);
                        window.location.reload();
                    }
                }).fail(function (xhr) {
                if (xhr.status == 401) {
                    window.location = xhr.getResponseHeader("Location");
                } else {
                    alert("未知错误请联系管理员" + xhr.status);
                    window.location.reload();
                }
            });
        }


        function calcTotal() {
            var totalPrice = 0;
            var qs = $(".inp"), as = $(".nums"), totalQuantity = 0, totalAmount = 0.0;
            qs.each(function (i, d) {
                totalQuantity = parseInt($(d).val());
                totalAmount = parseFloat($(as.get(i)).text());
                totalPrice += totalQuantity * totalAmount;
            });
//            as.each(function(i,d){
//                totalAmount+=parseFloat($(d).text());
//            });
            $(".totalPrice").text("￥" + totalPrice.toFixed(2));
        }
    })


</script>

</body>
</html>