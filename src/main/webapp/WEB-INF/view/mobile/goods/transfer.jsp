<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/common/common.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/resources/mallcss/initcss.css" rel="stylesheet">
    <link href="${ctx}/resources/mallcss/common.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="${ctx}/resources/mallcss/sliderBar.css" rel="stylesheet">
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
            background: url("${ctx}/resources/mallimages/png.png");
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

        .bar {
            overflow: hidden;
            border-bottom: 1px solid #ebebeb;
            background-color: #FEFFFE;
            padding: 1em 1.5em;
            font-size: 1.2em;
        }

        .bar .info {
            float: left;
        }

        .bar .info i {
            margin-left: 1em;
            font-size: 1em;
            color: #a5a5a5;
        }

        .bar .barBtn {
            float: right;
        }

        .bar .barBtn .reSet {
            padding-right: 1em;
        }

        .bar .barBtn .edit {
            padding-left: 1em;
        }

        .bar .barBtn i {
            color: #a5a5a5;
        }

        .accounts ul li {
            background-color: #FEFFFE;
            border-bottom: 1px solid #ebebeb;
            padding: 0.5em 0.5em;
            overflow: hidden;
            position: relative;

        }

        .accounts ul li img {
            width: 4em;
            border-radius: 4em;
            float: left;
        }

        .accounts .accountsInfo {
            margin-left: 1em;
            display: inline-block;
            padding-top: 0.6em;
        }

        .accounts .accountsInfo h4 {
            color: #a5a5a5;
            padding-top: 0.8em;

        }

        .accounts .accountsInfo h3.hidden {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            max-width: 5em;

        }

        .operate {
            float: right;

        }

        .operate .inputs {

            /*float: left;*/
            display: inline-block;
            margin-right: 5em;
            line-height: 4em;

        }

        .inputs span {
            display: inline-block;
            width: 28px;
            height: 28px;
            line-height: 28px;
            text-align: center;
            font-size: 18px;
            color: #999;
            cursor: pointer;
            border: 1px solid #e6e6e6;
        }

        .inputs input {
            border: none;
            outline: none;
            font-size: 1em;
            width: 3em;
            text-align: center;
        }

        .delete {
            position: absolute;
            top: 0;
            right: 0;
            /*display: inline-block;*/
            background-color: red;
            text-align: center;
            color: white;
            width: 3.5em;
            height: 100%;
            line-height: 5em;

        }

        .btns {
            background-color: #fefffe;
            overflow: hidden;
        }

        .transfer, .backTransfer {

            text-align: center;
            width: 50%;
            text-align: center;
            display: inline-block;
            font-size: 1.5em;
            padding: 0.5em 0;
            color: black;
            cursor: pointer;
            /*-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .1);*/
        }

        .backTransfer {
            width: 100%;
        }

        .delivery, .mydelivery {
            width: 50%;
            text-align: center;
            color: #26ac26;
            float: right;
            display: inline-block;
            font-size: 1.5em;
            padding: 0.5em 0;
            cursor: pointer;
            border-left: 1px solid #e6e6e6;
            margin-left: -1px;
            /*-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .1);*/
        }

        .mydelivery {
            width: 100%;
        }

        .warn {
            padding: 1em 1em;
            color: #a5a5a5;
            font-size: 1.1em;
        }

        .warn ul li {
            padding: 0.25em 0;
        }

        .item i {
            float: right;
            color: red;
        }

        /**地质层**/
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

        .info p {
            margin-top: 0.5em;
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
            display: none;
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

        nav {
            overflow: scroll;

        }

        #selectAddress {
            margin-bottom: 5em;
        }


    </style>
    <title>拨货</title>
</head>
<body style="background: rgb(232, 232, 232);">
<div id="st-container" class="st-container" style="overflow: scroll;">
    <%--<header>--%>
    <%--<a href="" class="Return"><span></span></a>--%>
    <%--<span class="Title">选择产品</span>--%>
    <%--<a href="" class="Home"><span></span></a>--%>
    <%--</header>--%>

    <div class="bar">
        <div class="info">
            <span>给${toAgent.realName}转货</span>
            <i class="fa fa-angle-right" aria-hidden="true"></i>
        </div>
        <div class="barBtn">
            <span class="reSet">重置</span>
            <i>|</i>
            <span class="edit">编辑</span>
        </div>
    </div>

    <div class="accounts">
        <ul>
            <c:forEach items="${goodsAccounts}" var="accounts">
                <c:if test="${accounts.currentBalance!=0}">
                    <li class="item" data-id="${accounts.goods.id}">
                        <img src="${dmzImgPath}${accounts.goods.imgFile}" alt="">
                        <div class="accountsInfo">
                            <h3>${accounts.goods.name}</h3>
                            <h4>库存:${accounts.currentBalance}</h4>
                        </div>
                        <div class="operate" style="display: none">
                            <div class="inputs">
                                <span class="reduce">-</span>
                                <input class="amount" type="text" value="0">
                                <span class="add">+</span>
                            </div>
                            <div class="delete">
                                <span>删除</span>
                            </div>
                        </div>
                        <i style="display: none" class="fa fa-check-circle" aria-hidden="true"></i>
                    </li>
                </c:if>
            </c:forEach>


        </ul>
    </div>

    <div class="btns" style="display: none">
        <c:if test="${!toAgent.mutedUser}">
            <c:if test="${toAgent.id eq user.id}">
                <div class="delivery mydelivery st-trigger-effects" data-effect="st-effect-1">我要发货</div>
            </c:if>

            <c:if test="${toAgent.id ne user.id}">
                <div class="transfer">转货</div>
                <div class="delivery st-trigger-effects" data-effect="st-effect-1">发货</div>
            </c:if>

        </c:if>
        <c:if test="${toAgent.mutedUser}">
            <div class="backTransfer">申请退货</div>
        </c:if>
    </div>

    <div class="warn">
        <ul>
            <li>温馨提示 :</li>
            <li>
                *转货，将虚拟库存转入客户系统，相当于银行卡转账
            </li>
            <li>
                *发货，填写地址，让公司将货物发到指定地点，相当于银行提现
            </li>
            <li>
                *有问题请联系客服
            </li>
        </ul>
    </div>


    <nav style="width: 100%; position: fixed;top: 0px;background-color: white" class="st-menu st-effect-1" id="menu-1">
        <p style="text-align: center;padding: 1em 0;color: red">点击选择地址或者新增收货地址</p>
        <div id="addAddress" style="display: none">
            <div class="form-group">
                <%--<label for="exampleInputEmail1">Email address</label>--%>

                <input type="text"
                <c:if test="${user.id  ne toAgent.id}">
                       readonly  value="${toAgent.realName}"
                </c:if>

                       class="form-control" id="addrName"
                       placeholder="收货人">
            </div>
            <div class="form-group">
                <%--<label for="exampleInputPassword1">Password</label>--%>
                <input type="text" class="form-control"
                <c:if test="${user.id  ne toAgent.id}">
                       value="${toAgent.agentCode}"
                </c:if>

                       id="addrCode" placeholder="收货人编号/没有可不填">
            </div>
            <div class="form-group">
                <%--<label for="exampleInputPassword1">Password</label>--%>
                <input type="number" class="form-control" id="addrPhone" placeholder="收货人手机号">
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
                <button id="submitBtn" data-loading-text="正在提交..." type="button" class="btn btn-danger form-control">
                    确定发货
                </button>
            </div>
        </div>
        <div id="selectAddress">
            <c:forEach var="address" items="${addressList}" varStatus="sta">
                <div class="address ">
                    <input type="hidden" class="aid" value="${address.id}">
                    <div class="info">
                        <div class="addrImg"><span></span></div>
                        <p>
                            <span class="adrName">收件人:${address.consignee}(${address.consigneeCode})</span>
                            <span class="adrPhone">${address.mobile}</span>
                        </p>
                        <p class="adrDetail">地址：${address.address}</p>
                    </div>
                    <a href=""><span class="toEdit"></span></a>
                </div>
            </c:forEach>

            <div class="form-group" style="margin-top: 1em">
                <%--<label for="exampleInputPassword1">Password</label>--%>
                <button id="addBtn" style="background-color: red" type="button" data-loading-text="正在提交，请稍等..."
                        style="background-color: red" class="btn btn-danger form-control">新增地址
                </button>
            </div>


        </div>

    </nav>

</div>


</div>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="${ctx}/resources/malljs/classie.js"></script>
<script src="${ctx}/resources/malljs/sidebarEffects.js"></script>
<script src="https://cdn.bootcss.com/distpicker/2.0.0-rc/distpicker.min.js"></script>
<script>
    $(function () {

        var isEdit = false;

        var canEdit = false;

        //显示所有点击了的
        function showOperate() {

//        $("").each(function (v,i) {
//            $(v).find()
//        })
            $(".item").hide();
            $(".item.click").show();

            $(".item.click i").hide();
            $(".item.click .operate").show();
            $(".item.click .accountsInfo h3").addClass("hidden");
            $(".btns").show();

        }

//    function hideOperate() {
//        $(".operate").hide();
//        $(".accountsInfo h3").removeClass("hidden");
//        $(".btns").hide();
//    }


        //增加
        $(".add").click(function () {
            var inp = $(this).closest("div").children(".amount");
            inp.val(parseInt(inp.val()) + 1);
        });

        //增加
        $(".reduce").click(function () {
            var inp = $(this).closest("div").children(".amount");
            if (parseInt(inp.val() - 1) < 0) {
                $(this).val(0);
                return;
            }
            inp.val(parseInt(inp.val()) - 1);
        });


        //点击
        $(".item").click(function () {
//            $("#addBtn").button("loading");
            canEdit = true;
            if (isEdit)return;
            var gid = $(".item").attr("data-id");
            $(this).addClass("click");
            $(this).find("i").show();
            //重置亮起
            $(".edit").css("color", "red");
        });


        //编辑
        $(".edit").click(function () {
            if (!canEdit)return;
            isEdit = true;
            showOperate();
            $(this).css("color", "black");
            $(".reSet").css("color", "red");
        });

        //重置
        $(".reSet").click(function () {
            window.location.reload();
        });

        //删除
        $(".delete").click(function () {
            $(this).closest("li").hide();
            $(this).closest("li").removeClass("click");//移除click
            $(this).closest("li").find("i").hide();
        });

        //限制只能输入整数
        $(".amount").keyup(function () {
            var tmptxt = $(this).val();
            $(this).val(tmptxt.replace(/\D|^0/g, ''));
        }).bind("paste", function () {
            var tmptxt = $(this).val();
            $(this).val(tmptxt.replace(/\D|^0/g, ''));
        }).css("ime-mode", "disabled");

        //地址编辑

        //新增地址
        $("#addBtn").click(function () {
            $("#addAddress").show();
            $("#selectAddress").hide();
        });

        //产品Id
        var goodsIds = [];

        //产品数量
        var amounts = [];

        function checkGoods() {
            //清空
            goodsIds = [];
            amounts = [];
            $(".item.click").each(function (i, v) {
                var id = $(v).attr("data-id");
                var amount = $(v).find(".amount").val();
                goodsIds.push(id);
                amounts.push(amount);
            });
        }

        //确定转货
        $(".transfer").click(function () {
            checkGoods();
            var remark = prompt("如果需要备注，请填写");
            if (remark == "") remark = "未备注";
            var params = {
                "remark": remark,
                "goodsIds": goodsIds,
                "amounts": amounts,
                "toUid":${toAgent.id}
            };
            applyTransfer(params);
        });

        //拨货
        function applyTransfer(params) {
            params = $.param(params, true);
            $.post("<c:url value='/mobile/goods/transfer.json'/>",
                params,
                function (data, status, jqXHR) {
                    var m = data;
                    if (m.flag == "0") {
                        alert("转货成功!");
                        window.location.href = "<c:url value='/mobile/transfer/records.html'/>";
                    } else {
                        alert("操作失败,请重试" + m.message);
//                        window.location.reload();
                    }
                }).fail(function (xhr) {
                if (xhr.status == 401) {
                    window.location.href = xhr.getResponseHeader("Location");
                } else {
                    console.log(xhr);
                    alert("未知错误请联系管理员" + xhr.status);
//                    window.location.reload();
                }
            });
        }

        //申请退货
        //确定转货
        $(".backTransfer").click(function () {
            checkGoods();
            var remark = prompt("如果需要备注，请填写");
            if (remark == "") remark = "未备注";
            var params = {
                "remark": remark,
                "goodsIds": goodsIds,
                "amounts": amounts
            };
            applyBackTransfer(params);
        });

        //退货
        function applyBackTransfer(params) {
            params = $.param(params, true);
            $.post("<c:url value='/mobile/goods/backTransfer.json'/>",
                params,
                function (data, status, jqXHR) {
                    var m = data;
                    if (m.flag == "0") {
                        alert("申请退货成功，等待管理员处理!");
                        window.location.href = "<c:url value='/mobile/transfer/records.html'/>";
                    } else {
                        alert("操作失败" + m.message);
//                        window.location.reload();
                    }
                }).fail(function (xhr) {
                if (xhr.status == 401) {
                    window.location.href = xhr.getResponseHeader("Location");
                } else {
                    console.log(xhr);
                    alert("未知错误请联系管理员" + xhr.status);
//                    window.location.reload();
                }
            });
        }


        //点击选择了地址
        $(".address").click(function () {
            checkGoods();//核算点击的产品
            var aid = $(this).children(".aid").val();
            if (confirm("确定发给这个地址？")) {
                $("#addBtn").button("loading");
                var remark = prompt("如果需要备注，请填写");
                if (remark == "") remark = "未备注";
                var params = {
                    "id": aid,
                    "remark": remark,
                    "goodsIds": goodsIds,
                    "amounts": amounts,
                    "toUid":${toAgent.id}
                };
                applyDelivery(params);
            }
        });


        //确定发货
        $("#submitBtn").click(function () {
            $(this).button("loading");
            checkGoods();//核算点击的产品
//        console.log(goodsIds);
//        console.log(amounts);

//            var remark = "未备注";
            var remark = prompt("如果需要备注，请填写");
            if (remark == "") remark = "未备注";
            var addrName = $("#addrName").val();
            if (addrName == "") {
                alert("名字不能为空");
                $("#submitBtn").button("reset");
                return;
            }
            var addrCode = $("#addrCode").val();
            var addrPhone = $("#addrPhone").val();
            if (addrPhone == "") {
                alert("电话不能为空");
                $("#submitBtn").button("reset");
                return;
            }
            var addrProvince = $("#addrProvince").val();
            if (addrProvince == "") {
                alert("省份不能为空");
                $("#submitBtn").button("reset");
                return;
            }
            var addrCity = $("#addrCity").val();
            if (addrCity == "") {
                alert("市区不能为空");
                $("#submitBtn").button("reset");
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
                $("#submitBtn").button("reset");
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
                "address": address,
                "goodsIds": goodsIds,
                "amounts": amounts,
                "toUid":${toAgent.id}
            };
            applyDelivery(params);
        });

        //直接发货
        function applyDelivery(params) {
            params = $.param(params, true);
            $.post("<c:url value='/mobile/goods/delivery.json'/>",
                params,
                function (data, status, jqXHR) {
                    var m = data;
                    if (m.flag == "0") {
                        alert("发货成功!");
                        window.location.href = "<c:url value='/mobile/delivery/records.html'/>";
                    } else {
                        alert("操作失败" + m.message);
                        $("#submitBtn").button("reset");
//                        window.location.reload();
                    }
                }).fail(function (xhr) {
                if (xhr.status == 401) {
                    window.location.href = xhr.getResponseHeader("Location");
                } else {
                    console.log(xhr);
                    alert("未知错误请联系管理员" + xhr.status);
//                    window.location.reload();
                }
            });
        }


    })


</script>

</body>
</html>