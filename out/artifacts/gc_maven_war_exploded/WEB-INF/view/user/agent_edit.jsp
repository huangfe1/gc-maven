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
</style>
<div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
        <div class="modal-header bg-primary">
            <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <h4 class="modal-title" id="myModalLabel">代理信息编辑-${action}</h4>
        </div>
        <div class="modal-body">
            <div class="container-fluid">
                <div class="row">
                    <form action="<c:url value='/agent/edit.json'/>" name="editForm"
                          class="form-horizontal" id="editForm" method="post">
                        <ul class="nav nav-tabs" role="tablist">
                            <li role="presentation" class="active"><a href="#basicInfo" aria-controls="basicInfo"
                                                                      role="tab" data-toggle="tab">基本信息</a></li>
                            <%--<li role="presentation"><a href="#authInfo" aria-controls="authInfo" role="tab"--%>
                                                       <%--data-toggle="tab">产品授权</a></li>--%>
                            <%--<li role="presentation"><a href="#authedInfo" aria-controls="authedInfo" role="tab"--%>
                                                       <%--data-toggle="tab">已有授权</a></li>--%>
                        </ul>
                        <div class="tab-content">
                            <div role="tabpanel" class="tab-pane active" id="basicInfo">
                                <div class="col-md-12 col-xs-12">
                                    <div class="form-group">
                                        <input type="hidden" name="id" value="${parameter.entity.id}">
                                        <label class="col-sm-2 control-label">代理编号</label>
                                        <div class="col-sm-2">
                                            <p class="form-control-static">${parameter.entity.agentCode}</p>
                                        </div>
                                        <label for="editName" class="col-sm-1 control-label">姓名</label>
                                        <div class="col-sm-3">
                                            <input type="text" class="form-control" id="editName"
                                                   tabIndex="10" required name="realName"
                                                   value="${parameter.entity.realName}" placeholder="输入代理姓名">
                                        </div>
                                        <div class="col-md-2 col-xs-2 text-error"></div>
                                    </div>

                                        <div class="form-group">
                                            <label for="editName" class="col-sm-2 control-label">上级代理编码</label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" id="editName"
                                                       tabIndex="10" name="parentAgentCode"
                                                <c:if test="${not user.admin}"> readonly='readonly' </c:if>
                                                       value="${ parameter.entity.topAgent ? '' : parameter.entity.parent.agentCode}"
                                                       placeholder="输入上级代理编码">
                                            </div>
                                            <div class="col-md-4 col-xs-4 text-error"></div>
                                        </div>



                                    <div class="form-group">
                                        <label for="editMobile" class="col-sm-2 control-label">手机号码</label>
                                        <div class="col-sm-4">
                                            <input <c:if test="${!user.admin}">readonly</c:if>  type="tel" class="form-control" id="editMobile"
                                                   tabIndex="11" required name="mobile"
                                                   value="${parameter.entity.mobile}" placeholder="输入代理联系电话">
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>

                                    <div class="form-group">
                                        <label for="editMobile" class="col-sm-2 control-label">纳税识别号</label>
                                        <div class="col-sm-4">
                                            <input <c:if test="${!user.admin}">readonly</c:if>  type="tel" class="form-control" id="taxCode"
                                                   tabIndex="11" required name="taxCode"
                                                   value="${parameter.entity.taxCode}" placeholder="纳税识别号">
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>

                                    <div class="form-group">
                                        <label for="registerAddress" class="col-sm-2 control-label">收件地址</label>
                                        <div class="col-sm-4">
                                            <input <c:if test="${!user.admin}">readonly</c:if>  type="tel" class="form-control" id="registerAddress"
                                                   tabIndex="11" required name="registerAddress"
                                                   value="${parameter.entity.registerAddress}" placeholder="收件地址">
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>

                                    <div class="form-group">
                                        <label for="info" class="col-sm-2 control-label">客户性质</label>
                                        <div class="col-sm-4">
                                            <input <c:if test="${!user.admin}">readonly</c:if>  type="tel" class="form-control" id="info"
                                                   tabIndex="11" required name="info"
                                                   value="${parameter.entity.info}" placeholder="客户性质">
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>


                                    <div class="form-group">
                                        <label for="editWeixin" class="col-sm-2 control-label">微信号</label>
                                        <div class="col-sm-4">
                                            <input <c:if test="${!user.admin}">readonly</c:if> type="text" class="form-control" id="editWeixin"
                                                   tabIndex="12" required name="weixin"
                                                   value="${parameter.entity.weixin}" placeholder="输入微信号">
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>



                                    <c:if test="${parameter.entity.id!=null}">
                                        <div class="form-group">
                                            <label for="editWeixin" class="col-sm-2 control-label">密码</label>
                                            <div class="col-sm-4">
                                                <input <c:if test="${!user.admin}">readonly</c:if>  type="text" class="form-control" id="editWeixin"
                                                       tabIndex="12" required name="password"
                                                       value="${parameter.entity.password}" placeholder="输入密码">
                                            </div>
                                            <div class="col-md-4 col-xs-4 text-error"></div>
                                        </div>
                                    </c:if>


                                    <div class="form-group">
                                        <label for="payWay" class="col-sm-2 control-label">支付方式</label>
                                        <div class="col-sm-4">
                                            <select class="form-control" id="payWay" name="payWay">
                                                <c:if test="${parameter.entity.payWay!=null&&parameter.entity.payWay!=''}">
                                                    <option value="${parameter.entity.payWay}">${parameter.entity.payWay}</option>
                                                </c:if>
                                                <option value="微信支付">微信支付</option>
                                                <option value="代付">代付</option>
                                                <option value="货到付款">货到付款</option>
                                            </select>
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>


                                    <div class="form-group" style="display: none">
                                        <label for="editWeixin" class="col-sm-2 control-label">是否需要考核</label>
                                        <div class="col-sm-4">
                                            <select class="form-control" name="needCheck">
                                                <c:choose>
                                                    <c:when test="${!parameter.entity.needCheck}">
                                                        <option value="true">需要</option>
                                                        <option value="false" selected >不需要</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="true">需要</option>
                                                        <option value="false">不需要</option>
                                                    </c:otherwise>
                                                </c:choose>

                                            </select>
                                            <%--<input type="text"  id="check"--%>
                                                   <%--tabIndex="12" required name="check"--%>
                                                   <%--value="${parameter.entity.check}" placeholder="是否需要考核">--%>
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>


                                    <div class="form-group">
                                        <label for="editWeixin" class="col-sm-2 control-label">注册时期</label>
                                        <div class="col-sm-4">
                                            <input <c:if test="${!user.admin}">readonly</c:if>  type="text" class="form-control" id="editWeixin"
                                                   tabIndex="12" required name="joinDate"
                                                   value="${parameter.entity.joinDate}" placeholder="注册时间">
                                        </div>
                                        <div class="col-md-4 col-xs-4 text-error"></div>
                                    </div>
                                    <!--绑定的微信-->
                                    <c:if test="${parameter.entity.wxOpenid!=null||!parameter.entity.wxOpenid eq ''}">
                                        <div class="form-group">
                                            <label for="currentPoint" class="col-sm-2 control-label">绑定的微信</label>
                                            <div class="col-sm-6">
                                                <input <c:if test="${!user.admin}">readonly</c:if>  type="text" class="form-control" id="editIdCard"
                                                       tabIndex="13" required name="wxOpenid"
                                                       value="${parameter.entity.wxOpenid}" placeholder="请勿填写">
                                            </div>
                                            <div class="col-md-4 col-xs-4 text-error"></div>
                                        </div>
                                    </c:if>

                                    <%--<div class="form-group">--%>
                                    <%--<label for="currentPoint" class="col-sm-2 control-label">身份证号</label>--%>
                                    <%--<div class="col-sm-6">--%>
                                    <%--<input type="text" class="form-control" id="editIdCard"--%>
                                    <%--tabIndex="13" required name="idCard"--%>
                                    <%--value="${parameter.entity.idCard}" placeholder="输入代理身份证号">--%>
                                    <%--</div>--%>
                                    <%--<div class="col-md-4 col-xs-4 text-error"></div>--%>
                                    <%--</div>--%>
                                    <!-- 只有管理员显示 -->
                                    <c:if test="${user.admin}">
                                        <div class="form-group">
                                            <label for="currentBalance" class="col-sm-2 control-label">汇款信息</label>
                                            <div class="col-sm-6">
											<textarea <c:if test="${!user.admin}">readonly</c:if>  rows="3" class="form-control" id="editRemittance"
                                                      tabIndex="14" required name="remittance"
                                                      value="${parameter.entity.remittance}"
                                                      placeholder="请输入汇款信息">${parameter.entity.remittance}</textarea>
                                            </div>
                                            <div class="col-md-4 col-xs-4 text-error"></div>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <%--<div role="tabpanel" class="tab-pane" id="authInfo">--%>
                                <%--<div class="col-md-12">--%>
                                    <%--<div class="table-responsive">--%>
                                        <%--<table--%>
                                                <%--class="table table-striped table-bordered table-condensed"--%>
                                                <%--id="mainTable">--%>
                                            <%--<caption>--%>
                                                <%--<label>授权名称</label> <input type="text" value=""--%>
                                                                           <%--name="authName" id="authName"--%>
                                                                           <%--placeholder="授权名称"> <label>产品名称</label><input--%>
                                                    <%--type="text" value="" name="goodsName" id="goodsName"--%>
                                                    <%--placeholder="产品名称">--%>
                                                <%--<button type="button" class="btn btn-primary navbar-btn"--%>
                                                        <%--id="searchDT" name="searchDT">--%>
                                                    <%--<span class="glyphicon glyphicon-search"></span>&nbsp;查询--%>
                                                <%--</button>--%>
                                            <%--</caption>--%>
                                            <%--<thead>--%>
                                            <%--<tr>--%>
                                                <%--<th>授权类型</th>--%>
                                                <%--<th>授权产品</th>--%>
                                                <%--<th>选择</th>--%>
                                            <%--</tr>--%>
                                            <%--</thead>--%>
                                            <%--<tbody>--%>
                                            <%--</tbody>--%>
                                        <%--</table>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div role="tabpanel" class="tab-pane" id="authedInfo">--%>
                                <%--<div class="col-md-12">--%>
                                    <%--<table class="table table-striped table-bordered table-condensed">--%>
                                        <%--<thead>--%>
                                        <%--<tr>--%>
                                            <%--<th>授权类型</th>--%>
                                            <%--<th>授权产品</th>--%>
                                            <%--<th>授权状态</th>--%>
                                            <%--<th>操作</th>--%>
                                        <%--</tr>--%>
                                        <%--</thead>--%>
                                        <%--<tbody>--%>
                                        <%--<c:forEach items="${parameter.entity.authorizations}"--%>
                                                   <%--var="auth">--%>
                                            <%--<tr>--%>
                                                <%--<input type="hidden" value="${auth.id}" name="authedIds">--%>
                                                <%--<td>${auth.authorizationType.name}</td>--%>
                                                <%--<td>${auth.authorizationType.goods.name}</td>--%>
                                                <%--<td>${auth.status.desc}</td>--%>
                                                <%--<td>--%>
                                                    <%--<a class="btn btn-default viewBtn" target="letter"--%>
                                                       <%--href="<c:url value='/auth/letter.html?id=${auth.id}'/>"--%>
                                                       <%--data-id="${l.id}">查看证书</a>--%>
                                                    <%--<a class="btn btn-danger removeAuthBtn ajaxLink"--%>
                                                       <%--href="<c:url value='/agent/auth/remove.json?id=${parameter.entity.id}&authId=${auth.id}'/>">--%>
                                                        <%--<i class="glyphicon glyphicon-trash"></i>取消本授权--%>
                                                    <%--</a></td>--%>
                                            <%--</tr>--%>
                                        <%--</c:forEach>--%>
                                        <%--</tbody>--%>
                                    <%--</table>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        </div>
                    </form>

                </div>
            </div>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <div class="col-md-6 col-xs-12">
                    <button type="button" class="btn btn-default btn-block quitBtn"
                            tabIndex="26" id="quitBtn" data-dismiss="modal" name="quitBtn"
                            value="login" tabindex="4" data-loading-text="正在返回......">
                        <span class="glyphicon glyphicon-remove-sign">&nbsp;</span>关闭
                    </button>
                </div>
                <div class="col-md-6 col-xs-12">
                    <button type="button" class="btn btn-primary btn-block"
                            form="editForm" tabIndex="27" id="saveBtn" name="saveBtn"
                            value="saveBtn" tabindex="4" data-loading-text="正在提交......">
                        <span class="glyphicon glyphicon-save">&nbsp;</span>保存
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>


<jsp:include page="/WEB-INF/view/common/form.jsp"></jsp:include>
<jsp:include page="/WEB-INF/view/common/script_common.jsp"></jsp:include>


<script type="text/javascript">
    $(function () {
        init();
    });
    function init() {
        $("#editName").focus().select();
        $("#editTeqName").focus().select();
        var btn = null;
        var langUrl = "<c:url value='/resources/js/datatables-plugins/i18n/Chinese.json'/>";
        var dt = $("#mainTable").DataTable({
            language: {
                url: langUrl
            },
            "processing": true,
            "serverSide": true,
            "searching": false,
            "lengthChange": false,
            "ordering": false,
            "pageLength": 10,
            "ajax": {
                "url": "<c:url value='/authType/query.json'/>",
                "data": function (d) {
                    return $.extend({}, d, {
                        "entity.name": $('#authName').val(), "entity.goods.name": $('#goodsName').val(),
                        "useDatatables": "true"
                    });
                }
            },
            "columns": [{
                "data": "name"
            }, {
                "data": "goods.name"
            }, {
                "data": "id"
            }],
            "columnDefs": [{
                "render": function (data, type, row) {
                    return "<input type='checkbox' name='ids' value='" + data + "'/>";
                },
                "targets": 2
            }]
        });
        $('#mainTable tbody').on('click', 'tr', function () {
            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected');
                $(this).find("input[type='checkbox']").prop("checked", false);
            } else {
                dt.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
                $(this).find("input[type='checkbox']").prop("checked", true);
            }
        });
        $("#searchDT").click(function (e) {
            dt.draw();
        });
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
                                            alert("保存代理信息成功");
                                            $(".quitBtn")
                                                .click();
                                            location.reload();
                                        } else {
                                            alert("保存失败" + m.message);
                                        }

                                    },
                                    error: function (xhr,
                                                     textStatus,
                                                     errorThrown) {
                                        btn.button("reset");
                                        alert("保存失败");
                                        var m = $
                                            .parseJSON(xhr.responseText);

                                    }
                                });
                    },
                    rules: {
                        realName: {
                            required: true
                        },
                        loginName: {
                            required: false
                        },
                        mobile: {
                            required: true,
                            mobile: true
                        },
                        weixin: {
                            required: true
                        },
                        idCard: {
                            required: true
                        },
                        remittance: {
                            required: true
                        }

                    },
                    onkeyup: false,
                    messages: {
                        realName: {
                            required: "请输入代理姓名"
                        },
                        loginName: {
                            required: "请输入代理登陆系统账户名"
                        },
                        mobile: {
                            required: "请输入手机号码",
                            mobile: "手机号码格式不正确"
                        },
                        weixin: {
                            required: "请输入微信号"
                        },
                        idCard: {
                            required: "请输入身份证号"
                        },
                        remittance: {
                            required: "请输入汇款信息"
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
            alert(netx.val());
            $t.prop("checked") ? next.val(1) : next.val(0);
        });
        $("#saveBtn").click(function (e) {
            btn = $(this).button();
            $(document.forms["editForm"]).submit();
        });
        $("#removeAuthBtn").click(function (e) {
            $(this).closest("tr").remove();
        });
        $("a.ajaxLink.removeAuthBtn").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            var method = "DELETE";
            if (!window.confirm("确定取消本授权?")) {
                return false;
            }
            var dataRow = $(this).parents("TR");
            $.ajax({
                url: $(e.target).attr("href"), method: method, complete: function (xhr, ts) {
                    if (xhr.status >= 200 && xhr.status < 300) {
                        $("#alertMessageBody").empty().html("取消产品授权操作成功");
                        $("#myAlertModal").modal({backdrop: "static", show: true});
                        dataRow.remove();
                    } else {
                        $("#alertMessageBody").empty().html("取消产品授权操作失败").addClass("text-danger");
                        $("#myAlertModal").modal({backdrop: "static", show: true});
                    }
                }
            });
        });
        $('a[data-toggle="tab"]').on("show.bs.tab", function (e) {
        });
    }
</script>
