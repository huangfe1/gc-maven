<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/common/common.jsp" %>
<style>
</style>
<div class="modal-dialog modal-lg" role="document">
    <form action="<c:url value='/accounts/transfer.json'/>"
          class="form-horizontal" name="editForm" id="editForm" method="post">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title text-primary" id="myModalLabel">
                    <span class="glyphicon glyphicon-retweet"></span>${accountsType.stateInfo}转账</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12 col-xs-12">
                            <input type="hidden" name="typeState" value="${accountsType.state}">

                            <div class="panel panel-red"
                                 <c:if test="${!user.admin}">style="display: none" </c:if>  >
                                <div class="panel-heading">转出方信息</div>
                                <div class="panel-body">
                                    <div class="form-group">
                                        <label for="fromName" class="col-sm-2 control-label">姓名</label>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control" required autofocus="autofocus"
                                                   id="fromName" tabIndex="12" name="fromName"
                                                   value="${fromAgent.realName}" placeholder="">
                                            <span class="help-block text-danger">注册时填写的实名,必须与代理编号对应</span>
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>

                                    <div class="form-group">
                                        <label for="fromCode" class="col-sm-2 control-label">编号</label>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control" required
                                                   id="fromCode" tabIndex="12"
                                                   name="fromCode" value="${fromAgent.agentCode}"
                                                   placeholder="转出人编号">
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>

                                </div>
                            </div>
                        </div>

                        <div class="col-md-12 col-xs-12">

                            <div class="panel panel-primary">

                                <div class="panel-heading">接收人信息</div>
                                <div class="panel-body">

                                    <div class="form-group">
                                        <label for="editName" class="col-sm-2 control-label">姓名</label>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control" required autofocus="autofocus"
                                                   id="toName" tabIndex="12" name="toName"
                                                   value="${toAgent.realName}" placeholder="接收人姓名">
                                            <span class="help-block text-danger">注册时填写的实名,必须与代理编号对应</span>
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>
                                    <div class="form-group">
                                        <label for="editAgentCode" class="col-sm-2 control-label">编号</label>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control" required
                                                   id="toCode" tabIndex="12"
                                                   name="toCode" value="${toAgent.agentCode}"
                                                   placeholder="输入接受人的代理编号">
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>
                                    <div class="form-group">
                                        <label for="amount" class="col-sm-2 control-label">数量</label>
                                        <div class="col-sm-4">
                                            <input type="number" class="form-control input-lg" required
                                                   id="amount" tabIndex="12" name="amount" value="0"
                                                   placeholder="输入转出数量">
                                            <span class="help-block text-danger"></span>
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>
                                    <div class="form-group">
                                        <label for="remark" class="col-sm-2 col-xs-12 control-label">备注说明</label>
                                        <div class="col-sm-6 col-xs-12">
                                            <textarea class="form-control" id="remark" rows="4" name="remark"
                                                      tabindex="13">${fromAgent.realName}转出</textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="form-group">
                        <div class="col-md-6 col-xs-12">
                            <button type="button" class="btn btn-default btn-block quitBtn"
                                    id="quitBtn" data-dismiss="modal" name="quitBtn" value="login"
                                    tabindex="4" data-loading-text="正在返回......">
                                <span class="glyphicon glyphicon-off">&nbsp;</span>关闭
                            </button>
                        </div>
                        <div class="col-md-6 col-xs-12">
                            <button type="submit" class="btn btn-success btn-block"
                                    form="editForm" id="saveBtn" name="saveBtn" value="saveBtn"
                                    tabindex="4" data-loading-text="正在提交......">
                                <span class="glyphicon glyphicon-random">&nbsp;</span>转让
                            </button>
                        </div>
                    </div>
                </div>
            </div>
    </form>
</div>


<jsp:include page="/WEB-INF/view/common/head.jsp"></jsp:include>
<jsp:include page="/WEB-INF/view/common/form.jsp"></jsp:include>
<jsp:include page="/WEB-INF/view/common/startbootstrap.jsp"></jsp:include>
<jsp:include page="/WEB-INF/view/common/script_common.jsp"></jsp:include>
<script type="text/javascript">
    $(function () {
        init();
    });
    function init() {
        var btn = null;
        $("#editName").focus();
        $("#editForm").validate(
            {
                submitHandler: function (form) {
                    $(form).ajaxSubmit(
                        {
                            beforeSubmit: function () {
                                btn.button("loading");
                            },
                            success: function (responseText, statusText, xhr, $form) {
                                var m = $.parseJSON(xhr.responseText);
                                btn.button("reset");
                                if (m.flag == "0") {
                                    alert("转让成功");
                                    var typeState = "${accountsType.state}";
                                    window.location.href = "<c:url value="/accounts/index.html?typeState="/>"+typeState;
//                                    $(".quitBtn").click();
//                                    $("#search").click();
                                } else {
                                    alert("转让失败," + m.message);
                                }

                            },
                            error: function (xhr, textStatus, errorThrown) {
                                var m = $.parseJSON(xhr.responseText);
                                $("#alert").empty().html(m.message).removeClass("invisible");
                                btn.button("reset");
                            }
                        });
                },
                rules: {
                    agentCode: {
                        required: true
                    },
                    realName: {
                        required: true
                    },
                    voucher: {
                        required: true,
                        number: true,
                        min: 1
                    }
                },
                onkeyup: false,
                messages: {
                    agentCode: {
                        required: "请填写代理编码"
                    },
                    realName: {
                        required: "请填写代理人姓名"
                    }
                },
                focusInvalid: true,
                errorClass: "text-danger",
                validClass: "valid",
                errorElement: "small",
                errorPlacement: function (error, element) {
                    error.appendTo(element.closest("div.form-group")
                        .children("div.text-error"));
                }
            });
        $("#saveBtn").click(function () {
            btn = $(this).button();
        });
    }
</script>
