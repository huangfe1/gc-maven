<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/common/common.jsp" %>
<style>
    <!--
    -->
    .none {
        display: none;
    }

    .quantity {
        padding: 3px 0px !important;
        text-align: center !important;
    }
</style>


<div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <h4 class="modal-title text-primary" id="myModalLabel">转出货物</h4>
        </div>
        <div class="modal-body">
            <div class="container-fluid">
                <form action="<c:url value='/transfer/to.json'/>" name="editForm"
                      class="form-horizontal" id="editForm" method="post">
                    <div class="row">
                        <div class="col-md-12 col-xs-12"
                             <c:if test="${!user.admin}">style="display: none" </c:if> >
                            <div class="panel panel-red">
                                <div class="panel-heading">转出方信息</div>
                                <div class="panel-body">
                                    <div class="form-group">
                                        <label for="fromName" class="col-sm-2 control-label">姓名</label>
                                        <div class="col-sm-4">
                                            <input type="text" value="${fromAgent.realName}" name="fromName"
                                                   class="form-control" id="fromName" tabIndex="12" required
                                                   placeholder="输入对方姓名">
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>
                                    <div class="form-group">
                                        <label for="toCode" class="col-sm-2 control-label">编号</label>
                                        <div class="col-sm-4">
                                            <input type="text" value="${fromAgent.agentCode}" class="form-control" required id="fromCode" tabIndex="12" name="fromCode"
                                                   placeholder="输入对方编号">
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>

                                    <%--<div class="form-group">--%>
                                    <%--<label for="remittance" class="col-sm-2 control-label">转货备注</label>--%>
                                    <%--<div class="col-sm-4">--%>
                                    <%--<input type="text" class="form-control" required--%>
                                    <%--id="remittance" tabIndex="12"--%>
                                    <%--name="remittance"--%>
                                    <%--placeholder="转货的备注">--%>
                                    <%--</div>--%>
                                    <%--<div class="col-md-4 col-xs-4 text-error"></div>--%>
                                    <%--</div>--%>
                                    <%-- <div class="form-group">
                                <label for="currentStock" class="col-sm-2 control-label">转出数量</label>
                                <div class="col-sm-4">
                                    <input type="number" class="form-control" required id="editQuantity" tabIndex="12"
                                        name="quantity" value=""
                                        <c:if test="${currentBalance<=0}">
                                        disabled="disabled"
                                        </c:if>
                                        placeholder="输入转出数量">
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div> --%>

                                </div>
                            </div>
                        </div>

                        <div class="col-md-12 col-xs-12">
                            <div class="panel panel-yellow">
                                <div class="panel-heading">接收方信息</div>
                                <div class="panel-body">
                                    <div class="form-group">
                                        <label for="toName" class="col-sm-2 control-label">姓名</label>
                                        <div class="col-sm-4">
                                            <input type="text" value="${toAgent.realName}" name="toName"
                                                   class="form-control" id="toName" tabIndex="12" required
                                                   placeholder="输入对方姓名">
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>
                                    <div class="form-group">
                                        <label for="toCode" class="col-sm-2 control-label">编号</label>
                                        <div class="col-sm-4">
                                            <input type="text" value="${toAgent.agentCode}" class="form-control"
                                                   required id="toCode" tabIndex="12" name="toCode"
                                                   placeholder="输入对方编号">
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>

                                    <div class="form-group">
                                        <label for="remark" class="col-sm-2 control-label">转货备注</label>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control" required
                                                   id="remark" tabIndex="12"
                                                   name="remark"
                                                   placeholder="转货的备注">
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>
                                    <%-- <div class="form-group">
                                <label for="currentStock" class="col-sm-2 control-label">转出数量</label>
                                <div class="col-sm-4">
                                    <input type="number" class="form-control" required id="editQuantity" tabIndex="12"
                                        name="quantity" value=""
                                        <c:if test="${currentBalance<=0}">
                                        disabled="disabled"
                                        </c:if>
                                        placeholder="输入转出数量">
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div> --%>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    转出货物信息
                                    <button type="button" class="btn btn-primary" id="addItemBtn"
                                            name="addItemBtn">
                                        <span class="glyphicon glyphicon-plus"></span>&nbsp;添加货物
                                    </button>
                                </div>
                                <div class="panel-body" style="padding:0px;">

                                    <div class="table-responsive">
                                        <table
                                                class="table table-striped table-bordered table-condensed"
                                                id="mainTable">
                                            <thead>
                                            <tr>
                                                <th>货物名称</th>
                                                <th>数量</th>
                                                <th>操作</th>
                                            </tr>
                                            </thead>
                                            <tbody id="itemsBody">
                                            <c:forEach items="${parameter.entity.items}" var="item">
                                                <tr data-id="${item.key}">
                                                    <td>${item.value.goodsName}<input type="hidden"
                                                                                      name="goodsIds"
                                                                                      value="${item.value.goodsId}">
                                                    </td>
                                                    <td><input type="number" class="form-control quantity"
                                                               value="${item.value.quantity}" required name="quantitys"
                                                               min="0"></td>
                                                    <td><a class="btn btn-danger default removeItem"
                                                           data-role="delete" href="#"><span
                                                            class="fa fa-trash fa-fw" aria-hidden="true"></span>删除</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
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
                <div class="col-md-6 col-xs-6">
                    <button type="button" class="btn btn-primary btn-block"
                            form="editForm" tabIndex="27" id="saveBtn" name="saveBtn"
                            value="saveBtn" tabindex="4" data-loading-text="正在提交......">
                        <span class="glyphicon glyphicon-save">&nbsp;</span>确认转出
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<%--<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>--%>
<%--<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>--%>



<script type="text/javascript">
    $(function () {
        init();
    });
    function init() {
        $("#editName").focus().select();
        $("#addItemBtn").click(
            function (e) {
                e.preventDefault();
                e.stopPropagation();
                $("#selectModal").load(
                    "<c:url value='/goods/authGoods.html'/>",
                    function (e) {
                        $('#selectModal').modal({
                            backdrop: "static"
                        });
                    });
            });
        var btn = null;
        $("#editForm")
            .validate(
                {
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
                                            alert("转货操作成功");
                                            $(".quitBtn").click();
                                            window.location.href="<c:url value="/transfer/records.html"/>";
                                        } else {
                                            alert("转货失败," + m.message);
                                        }

                                    },
                                    error: function (xhr,
                                                     textStatus,
                                                     errorThrown) {
                                        btn.button("reset");
                                        alert("转货操作失败,未知错误请联系管理员");
                                    }
                                });
                    },
                    rules: {
                        quantity: {
                            required: true,
                            digits: true
                        },
                        remittance: {
                            required: true
                        }
                    },
                    onkeyup: false,
                    messages: {
                        quantity: {
                            required: "转货数必须填写",
                            digits: "转货数必须为整数"
                        },
                        remittance: {
                            required: "请填写转账备注"
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
            $(document.forms["editForm"]).submit();
        });
        $("#mainTable").delegate(".removeItem", "click", function (e) {
            $(this).closest("tr").remove();
        });
        $("#editQuantity").change(function (e) {
            var q = $(this).val();
            $(".total").empty().text(q * $("#price").val());
        });
    }
</script>

