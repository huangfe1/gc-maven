<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/common/common.jsp" %>
<div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <h4 class="modal-title" id="myModalLabel">编辑地址</h4>
        </div>
        <div class="modal-body">
            <div class="container-fluid">
                <div class="row">
                    <form action="<c:url value='/pmall/shopcart/payByOther.json'/>" name="editForm"
                          class="form-horizontal" id="editForm" method="post">
                        <input id="isAdd" name="isAdd" type="hidden" value="true"/>
                        <div id="selectDiv" style="display: none">

                            <c:forEach var="address" items="${addressList}" varStatus="sta">
                                <!--有地址才显示-->
                                <div class="panel panel-primary selectPanel">
                                    <div class="panel-heading">
                                            <span>
                                            &nbsp;
                                            </span>
                                        <a style="color: azure;float: left;"> <span
                                                class="glyphicon glyphicon-ok"></span>&nbsp;点击选择地址${sta.index+1}</a>
                                    </div>

                                        <%--<div class="panel-heading">--%>
                                        <%--<span>--%>
                                        <%--&nbsp;--%>
                                        <%--</span>--%>
                                        <%--<a style="color: azure;float: left;" id="editBtn" > <span class="glyphicon glyphicon-edit"></span>&nbsp;点击修改</a>--%>
                                        <%--<a style="color: azure;float: right"  id="addAddress"> <span class="glyphicon glyphicon-plus"></span>&nbsp;新增地址</a>--%>
                                        <%--</div>--%>

                                    <div class="panel-body">
                                        <div class="row ">
                                            <div class="col-md-3 col-xs-6">收货人:&nbsp&nbsp<span
                                                    class="name">${address.consignee}</span><span
                                                    class="code">${address.consigneeCode}</span></div>
                                            <div class="col-md-9 col-xs-6" style="text-align: right"><span
                                                    class="mobile">${address.mobile}</span></div>
                                        </div>
                                        <div class="row ">
                                            <div class="col-md-12 col-xs-12">收货地址:&nbsp&nbsp<span
                                                    class="province">${address.province}</span><span
                                                    class="city">${address.city}</span><span
                                                    class="country">${address.county}</span><span
                                                    class="address">${address.address}</span></div>
                                        </div>
                                    </div>
                                </div>

                            </c:forEach>


                        </div>

                        <!--地址修改层-->
                        <c:if test="${!empty addressList}">
                            <div id="editPanel" class="panel panel-primary">
                                <div class="panel-heading">
                                    <span>
                                    &nbsp;
                                    </span>
                                    <a style="color: azure;float: left;" id="editBtn"> <span
                                            class="glyphicon glyphicon-edit"></span>&nbsp;点击选择地址</a>
                                    <a style="color: azure;float: right" id="addAddress"> <span
                                            class="glyphicon glyphicon-plus"></span>&nbsp;新增地址</a>
                                </div>
                                <div class="panel-body">
                                    <div class="row ">
                                        <div class="col-md-3 col-xs-6">收货人:&nbsp&nbsp<span class="name">XX</span><span
                                                class="code">XX</span></div>
                                        <div class="col-md-9 col-xs-6" style="text-align: right"><span
                                                class="mobile">XX</span></div>
                                    </div>
                                    <div class="row ">
                                        <div class="col-md-12 col-xs-12">收货地址:&nbsp&nbsp<span class="province">XX</span><span
                                                class="city">XX</span><span class="country">XX</span><span
                                                class="address">XX</span></div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <!--新增地址层-->
                        <div id="addressPanel" class="panel panel-primary"
                                <c:if test="${!empty addressList}">
                                    style="display: none"
                                </c:if>
                        >
                            <div class="panel-heading">填写收货地址</div>
                            <div class="panel-body">
                                </di class="col-md-12 col-xs-12">
                                <input type="hidden" name="id" value="${address.id}">
                                <div class="form-group">
                                    <label for="editAgentCode" class="col-sm-2 control-label">
                                        <%--<c:if test="${user.agentCode==null}">--%>
                                        收货人姓名
                                        <%--</c:if>--%>
                                        <%--<c:if test="${user.agentCode!=null}">--%>
                                        <%--收货人编号--%>
                                        <%--</c:if>--%>
                                    </label>
                                    <div class="col-sm-4">
                                        <input type="text" class="form-control" id="editAgentCode"
                                               tabIndex="10" name="consignee"
                                        <%--value="${address.consigneeCode}"--%>
                                        <%--<c:if test="${user.agentCode==null}">--%>
                                               value=""
                                               placeholder="请使用实名方便收货">
                                        <%--</c:if>--%>
                                        <%--<c:if test="${user.agentCode!=null}">--%>
                                        <%--value="${user.agentCode}"--%>
                                        <%--placeholder="代理编号">--%>
                                        <%--</c:if>--%>
                                        <%--<span class="help-block">防伪码将录入给您填写的代理</span>--%>
                                    </div>
                                    <%--<label for="editLoginName" class="col-sm-2 control-label">收货人姓名</label>--%>
                                    <div class="col-sm-4" style="display: none">
                                        <input type="text" class="form-control" id="editConsigneeName"
                                               tabIndex="10" name="consignee" readonly="readonly"
                                               value="${address.consigneeName}" placeholder="输入收货人姓名">
                                    </div>
                                    <%--<div class="col-md-4 col-xs-4 text-error"></div> --%>
                                </div>

                                <div class="form-group">
                                    <label for="editMobile" class="col-sm-2 control-label">手机号码</label>
                                    <div class="col-sm-4">
                                        <input type="tel" class="form-control" id="editMobile"
                                               tabIndex="11" required name="mobile"
                                               value="${address.mobile}" placeholder="输入收货人联系电话">
                                    </div>
                                    <div class="col-md-4 col-xs-4 text-error"></div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">地址省份</label>
                                    <div class="col-sm-3"><label class="col-sm-2 control-label">省</label><select
                                            val="${address.province}" type="text" class="form-control" id="s_province"
                                            name="province"></select></div>
                                    <div class="col-sm-3"><label class="col-sm-2 control-label">市</label><select
                                            val="${address.city}" type="text" class="form-control" id="s_city"
                                            name="city"></select></div>
                                    <div class="col-sm-3"><label class="col-sm-2 control-label">县</label><select
                                            val="${address.country}" type="text" class="form-control" id="s_county"
                                            name="country"></select></div>
                                    <div class="col-md-4 col-xs-4 text-error"></div>
                                </div>


                                <div class="form-group">
                                    <label for="editWeixin" class="col-sm-2 control-label">详细地址</label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" id="editAddress"
                                               tabIndex="12" required name="address"
                                               value="${address.address}" placeholder="输入收货地址">
                                    </div>
                                    <div class="col-md-4 col-xs-4 text-error"></div>
                                </div>

                            </div>
                        </div>

                        <!--选择支付方式-->
                        <%--<div class="panel panel-primary">--%>
                            <%--<div class="panel-heading">选择支付方式</div>--%>
                            <%--<div class="panel-body">--%>
                                <%--<div class="form-group">--%>
                                    <%--<label for="payWay" class="col-sm-2 control-label">支付方式</label>--%>
                                    <%--<div class="col-sm-6">--%>
                                        <%--<select  class="form-control" id="payWay" name="payWay" value="${address.address}">--%>
                                        <%--<option value="0">微信支付</option>--%>
                                        <%--<option value="1">代金券支付</option>--%>
                                        <%--</select>--%>
                                    <%--</div>--%>
                                    <%--<div class="col-md-4 col-xs-4 text-error"></div>--%>
                                <%--</div>--%>

                            <%--</div>--%>
                        <%--</div>--%>

                        <div class="panel panel-primary">
                            <div class="panel-heading">备注信息</div>
                            <div class="panel-body">

                                <div class="form-group">
                                    <div class="col-sm-12">
											<textarea rows="3" class="form-control" id="editRemark"
                                                      tabIndex="14" name="remark"
                                                      value="${parameter.entity.remark}"
                                                      placeholder="请输入发货备注信息">${address.remark}</textarea>
                                        <span class="help-block">发货注意事项可以在此说明</span>
                                    </div>
                                </div>

                            </div>
                        </div>
                        <!--选择货物层-->
                    </form>

                </div>
            </div>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <div class="col-md-6 col-xs-6">
                    <button type="button" class="btn btn-default btn-block quitBtn"
                            tabIndex="26" id="quitBtn" data-dismiss="modal" name="quitBtn"
                            value="login" tabindex="4" data-loading-text="正在返回......">
                        <span class="glyphicon glyphicon-remove-sign">&nbsp;</span>关闭
                    </button>
                </div>
                <c:if test="${address.status==null }">
                    <div class="col-md-6 col-xs-6">
                        <button type="button" class="btn btn-danger btn-block"
                                form="editForm" tabIndex="27" id="saveBtn" name="saveBtn"
                                value="saveBtn" tabindex="4" data-loading-text="正在提交......">
                            <span class="glyphicon glyphicon-save">&nbsp;</span>保存
                        </button>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value='/resources/js/area.js'/> "/>
<script type="text/javascript">
    $(function () {
        init();
        _init_area();//加载地址

    });
    function init() {
        //修改地址
        $("#editBtn").click(function (e) {
            e.preventDefault();
            $("#editPanel").hide();
            $("#selectDiv").show();
            $("#isAdd").val(false);
        });

        //增加地址
        $("#addAddress").click(function (e) {
            e.preventDefault();
            $("#editPanel").hide();
            $("#addressPanel").show();
            $("#isAdd").val(true);

        });

        //点击地址
        $(".selectPanel").click(function (e) {
            //设置编号
            $("#editAgentCode").val($(this).find(".code").html());
            $("#editConsigneeName").val($(this).find(".name").html());
            $("#editPanel").find(".code").html($(this).find(".code").html());
            //设置手机号码
            //设置手机号码
            var mb;
            if ($(this).find(".mobile").find("a").length > 0) {
                mb = $(this).find(".mobile").find("a").html();
            } else {
                mb = $(this).find(".mobile").html();
            }
            $("#editMobile").val(mb);

            $("#editPanel").find(".mobile").html($(this).find(".mobile").html());

            //设置省
            $("#s_province").val($(this).find(".province").html());
            $("#editPanel").find(".province").html($(this).find(".province").html());
            $("#s_province").trigger("change");
            //设置县
            $("#s_city").val($(this).find(".city").html());
            $("#editPanel").find(".city").html($(this).find(".city").html());
            $("#s_city").trigger("change");
            //设置区
            $("#s_county").val($(this).find(".country").html());
            $("#editPanel").find(".country").html($(this).find(".country").html());
            //设置详细地址
            $("#editAddress").val($(this).find(".address").html());
            $("#editPanel").find(".address").html($(this).find(".address").html());
            $("#editPanel").find(".name").html($(this).find(".name").html());
            //设置不用新增地址
            $("#isAdd").val(false);
            $("#selectDiv").hide();
            $("#editPanel").show();
        });


        $("#itAgentCode").focus().select();
        var btn = null;
        $("#addItemBtn").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            $("#selectModal").load("<c:url value='/delivery/authGoods.html?toback=0'/>", function (e) {
                $('#selectModal').modal({
                    backdrop: "static"
                });
            });
        });
        /**
         * 地址验证
         */
        jQuery.validator.addMethod("prequired", function (value, element) {
            if (value == "省份") {
                alert("请点击选择或者新增地址");
                $("#editBtn").focus();
            }
            return value != "省份";
        }, "地址必填");

        $("#editForm")
            .validate(
                {
                    ignore: [],
                    submitHandler: function (form) {
                        $(form)
                            .ajaxSubmit(
                                {
                                    beforeSubmit: function (arr, $form, options) {
                                        btn.button("loading");
                                    },
                                    success: function (responseText,
                                                       statusText, xhr,
                                                       $form) {
                                        var m = $
                                            .parseJSON(xhr.responseText);
                                        btn.button("reset");
                                        if (m.flag == "0") {
                                            $(".quitBtn")
                                                .click();
                                            var url = xhr.getResponseHeader("Location");
                                            if (!url) {//如果对象不存在
                                                if (confirm("下单成功,是否继续购物")) {
                                                    location.href = "<c:url value="/pmall/index.html"/>";
                                                } else {
                                                    location.href = "<c:url value="/dmz/vmall/index.html"/>";
                                                }
                                            } else {
//                                                alert("提交成功，前去支付页面支付！");
                                                window.location = xhr.getResponseHeader("Location");
                                            }
//															$("#search")
//																	.click();

                                        } else {
                                            alert("发货申请失败," + m.message);
                                        }
                                        btn.button("reset");

                                    },
                                    error: function (xhr, textStatus, errorThrown) {
                                        btn.button("reset");
                                        alert("未知错误请联系管理员" + xhr);
                                        console.log(xhr);
                                    }
                                });
                    },
                    rules: {
                        province: {
                            prequired: true
                        },
                        consigneeName: {
                            required: false
                        },
                        mobile: {
                            required: true,
                            mobile: true
                        },
                        address: {
                            required: true
                        }
                    },
                    onkeyup: false,
                    messages: {
                        consigneeName: {
                            required: "请输入收货人姓名"
                        },
                        mobile: {
                            required: "请输入手机号码",
                            mobile: "手机号码格式不正确"
                        },
                        address: {
                            required: "请输入收货地址"
                        }

                    },
                    focusInvalid: true,
                    errorClass: "text-danger",
                    validClass: "valid",
                    errorElement: "small",
                    errorPlacement: function (error, element) {
                        error.appendTo(element
                            .closest("div.form-group").children(
                                "div.text-error"));
                    }
                });
        $("#editForm").find("input[type='checkbox']").change(function (e) {
            var $t = $(this);
            var next = $t.next("input[type='hidden']");
            $t.prop("checked") ? next.val(1) : next.val(0);
        });
        $("#saveBtn").click(function (e) {
            btn = $(this).button();
//			if($("#itemsBody").find("TR").size()<1){
//				alert("还未添加需要发送的货物");
//				return false;
//			}
            $(document.forms["editForm"]).submit();
        });
        $("#mainTable").delegate(".removeItem", "click", function (e) {
            $(this).closest("tr").remove();
        });
        $("#mainTable").delegate("INPUT[name='quantitys']", "change", function (e) {
            if ($(this).val() <= 0) {
                $(this).closest("tr").remove();
            }
        });
        $('a[data-toggle="tab"]').on("show.bs.tab", function (e) {
            alert($(e.target).prop("id"));
        });
    }
</script>
