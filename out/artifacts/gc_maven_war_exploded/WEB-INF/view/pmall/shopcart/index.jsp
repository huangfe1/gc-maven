<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/common/common.jsp" %>
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
    <%--<link href="${ctx}/resources/mallcss/initcss.css" rel="stylesheet">--%>
    <%@include file="/WEB-INF/view/common/tc_css.jsp" %>
    <%@include file="/WEB-INF/view/common/head_css.jsp"%>
    <title>购物车</title>
</head>
<style type="text/css">
    .totalPrice::before, .productTotalPrice::before, .price::before {
        font-weight: 400;
        content: "\A5";
        font-size: 0.699em;
        display: inline-block;
    }

    .p-info .p-title{
        height: 20px;
    }
    p {
        margin: 0;
    }

</style>
<body>
<%--<div class="fanhui_cou">--%>
    <%--<div class="fanhui_1"></div>--%>
    <%--<div class="fanhui_ding">顶部</div>--%>
<%--</div>--%>
<%--<header class="header header_1">--%>
    <%--<div class="fix_nav">--%>
        <%--<div class="nav_inner">--%>
            <%--<a class="nav-left back-icon" href="javascript:history.back();">返回</a>--%>
            <%--<div class="tit">购物车</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</header>--%>

<div id="cartItems" class="container ">
    <c:if test="${ empty pmshopcart or pmshopcart.quantity<1}">
        <div class="row" style="padding-top: 100px;">
            <div class="col-md-12 col-xs-12">
                <a href="<c:url value="/dmz/pmall/view.html"/> "><p style="font-size: 20px"
                                                                    class="text-primary  text-center">
                    购物车里什么也没有,点击继续购物</p></a>
            </div>
        </div>
    </c:if>
    <c:forEach items="${pmshopcart.items}" var="item">
        <div class="row rowcar">
            <ul class="list-group">
                <li class="list-group-item text-primary">
                    <div class="icheck pull-left mr5">
                        <input type="checkbox" checked="checked" class="ids" prodStatus="1" itemkey=""/>
                        <%--<label class="checkLabel">--%>
                            <%--<span></span>--%>
                        <%--</label>--%>
                    </div>
                        ${item.value.goods.name}</li>
                <li class="list-group-item hproduct clearfix">
                    <div class="p-pic"><a href="#"><img class="img-responsive"
                                                        src="${dmzImgPath}${item.value.goods.imgFile}"></a></div>
                    <div class="p-info">
                        <a href="#"><p class="p-title"> ${item.value.goods.shareName}</p></a>
                        <p class="p-attr">
                            <span>规格：${item.value.goods.spec}</span></p>
                            <span>分类：
                            <c:forEach items="${item.value.goodsStandards}" var="stand">
                                <i class="stan" vPrice="${stand.price}" vName="${stand.name}" vId="${stand.id}">${stand.name}</i>
                            </c:forEach>
                            </span></p>
                        <p class="p-origin">
                            <a class="close p-close" data-id="${item.value.goods.id}">×</a>
                            <em data-price="${item.value.goods.retailPrice}" class="price">${item.value.goods.retailPrice}</em>
                        </p>
                    </div>
                </li>
                <li class="list-group-item clearfix">
                    <div class="pull-left mt5">
                        <span class="gary">小计：</span>
                        <em class="red productTotalPrice">${item.value.amount}</em>
                    </div>
                    <div class="btn-group btn-group-sm control-num">
                        <button class="btn btn-default minsBtn" type="button"><span class="gary">-</span></button>
                        <input lastQ="${item.value.quantity}" data-id="${item.value.goods.id}" type="tel"
                               class="btn gary2 Amount quantity" value="${item.value.quantity}" maxlength="4"
                               skuId="1358">
                            <%--<button class="btn btn-default addBtn"  type="button" >+</button>--%>
                        <button class="btn btn-default addBtn" type="button"><span class="gary">+</span></button>
                    </div>
                </li>
            </ul>
        </div>
    </c:forEach>
</div>

<div class="fixed-foot">
    <div class="fixed_inner">
        <div class="pay-point">
            <div class="icheck pull-left mr10">
                <input type="checkbox" checked="checked" id="buy-sele-all" value="1">
                <label for="buy-sele-all">
                    <%--<span class="mt10"></span>全选--%>
                </label>
            </div>
            <p>合计：<em><span class="totalPrice">179.00</span></em></p>
        </div>
        <div class="buy-btn-fix">
            <a href='<c:url value="/dmz/pmall/show.html"/>' class="btn btn-primary" data-loading-text="正在提交">继续购物
            </a>
            <button
                    data-url=<c:url value="/pmall/shopcart/commit.html"/>
                     href="#"
                    <c:if test="${pmshopcart.items==null||pmshopcart.items.size()<1}">disabled="disable" </c:if>
                    class="btn btn-danger btn-buy" data-loading-text="正在提交">提交订单</button>
        </div>
    </div>
</div>
<div class="clear"></div>

<div class="modal fade" id="editModal" style="overflow-x:hidden;overflow-y:auto;" tabindex="-1" role="dialog"
     aria-labelledby="editModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="editModalLabel"></h4>
            </div>
            <div class="modal-body" id="messageBody"></div>
            <div class="modal-footer"></div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/view/common/loginModal.jsp"/>
<jsp:include page="/WEB-INF/view/common/footer.jsp"/>
<jsp:include page="/WEB-INF/view/common/head.jsp"></jsp:include>
<jsp:include page="/WEB-INF/view/common/form.jsp"></jsp:include>
<jsp:include page="/WEB-INF/view/common/script_common.jsp"></jsp:include>
<jsp:include page="/WEB-INF/view/common/loginModal_js.jsp"></jsp:include>
<script type="text/javascript">
    $(function () {
        calcTotal();//计算总金额
        /*增加、减少*/
        $("#cartItems").delegate(".addBtn,.minsBtn", "click", function (e) {
            var quantity = $(this).closest("div").find(".quantity");
            var v = parseInt(quantity.val());
            if (isNaN(v)) {
                v = 1;
            }
            if ($(this).hasClass("addBtn")) {
                quantity.val(v + 1).change();
            } else {
                quantity.val(v - 1).change();
            }
        });
        /*
         改变
         */
        $("#cartItems").delegate(".quantity", "change", function (e) {
            var $this = $(this), val = $this.val(), goodsId = $this.attr("data-id");
            var lastQ = parseInt($this.attr("lastQ"));
            if (isNaN(val) || val <= 0) {
                removeItem($this, goodsId);
                return false;
            }
            var standardIds = [];
            var standardNames = [];
            var standardPrices = [];
            $(this).closest("ul").find(".stan").each(function (e) {
                standardIds.push($(this).attr("vId"));
                standardPrices.push($(this).attr("vPrice"));
                standardNames.push($(this).attr("vName"));
            });
            $.ajax("<c:url value="/dmz/pmall/shopcart/add.json"/>", {
                "type": "post",
                "data": {
                    "goodsId": goodsId,
                    "quantity": val,
                    "standardIds":standardIds,
                    "standardNames":standardNames,
                    "standardPrices":standardPrices
                },
                "complete": function (xhr) {
                    if (xhr.status == 200) {
                        var m = $.parseJSON(xhr.responseText);
                        if (m.flag != 0) {//加入购车失败
                            $this.val(lastQ);
                            alert(m.message);
                        } else {
                            calcRow($this);
                            $this.attr("lastQ", m.data);//更新上次的值
                        }
                    } else {
                        $this.val(lastQ);
                        alert("未知错误，请联系管理员！");
                    }
                }
            });
        });

        /*移除购物车*/
        $("#cartItems").delegate(".p-close", "click", function (e) {
            if (confirm("将从购物车删除此产品")) {
                removeItem($(this), $(this).attr("data-id"));
            }
        });

        $(".btn-buy").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            var url = $(this).attr("data-url");
            if (url != null) {
                window.location.href = url;
            }
        });


//        //购买
//        $(".btn-buy").click(function (e) {
//            e.preventDefault();
//            e.stopPropagation();
//            $(this).button('loading');
//            var url = $(this).attr("data-url");
//            if (url == null) {
//                $("#loginModal").modal().css({
//                    "margin-top": "100px"
//                });
//            } else {
//                $("#editModal").load(
//                    url,
//                    function (e) {
//                        $('#editModal').modal({
//                            backdrop: "static"
//                        });
//                    });
//            }

//            if(){
//
//            }
//            $("#editModal").load(
//                url,
//                function(e) {
//                    $('#editModal').modal({
//                        backdrop : "static"
//                    });
//                });
//        });

        /**
         * 移除
         */
        function removeItem($this, goodsId) {
            var standardIds = [];
            var standardNames = [];
            var standardPrices = [];
            $this.closest("ul").find(".stan").each(function (e) {
                standardIds.push($(this).attr("vId"));
                standardPrices.push($(this).attr("vPrice"));
                standardNames.push($(this).attr("vName"));
            });
            $.ajax("<c:url value='/dmz/pmall/shopcart/remove.json'/>",
                {
                    "type": "POST",
                    "data": {
                        "goodsId": goodsId,
                        "standardIds":standardIds,
                        "standardNames":standardNames,
                        "standardPrices":standardPrices
                    },
                    "complete": function (xhr) {
                        if (xhr.status == 200) {
                            var m = $.parseJSON(xhr.responseText);
                            if (m.flag == 0) {
                                if ($this.closest("#cartItems").find(".rowcar").size <= 1) {
                                    $(".btn-buy").attr("disabled", "disabled");
                                }
                                $this.closest(".rowcar").remove();
                            } else {
                                alert(m.message);
                            }
                        } else {
                            alert("未知错误，请联系管理员！");
                        }
                    }
                });
        }

        function decimal(num,v){
            var vv = Math.pow(10,v);
            return Math.round(num*vv)/vv;
        }

        /**
         * 计算一列的产品
         * @param tr
         */
        function calcRow($this) {
            var rowcar = $this.closest(".rowcar");
            var price = parseFloat(rowcar.find(".price").attr("data-price"));
            var number = parseInt(rowcar.find(".quantity").val());
            rowcar.find(".productTotalPrice").html(decimal(price * 100 * number / 100,2));
            //汇总
            calcTotal();
        }

        /**
         * 计算所有产品
         * @param tr
         */
        function calcTotal() {
            var productTotalPrice = 0;
            $(".productTotalPrice").each(function (i, d) {
                productTotalPrice += parseFloat($(d).text());
            });
            $(".totalPrice").html(productTotalPrice);
        }
    });
</script>
</body>
</html>
