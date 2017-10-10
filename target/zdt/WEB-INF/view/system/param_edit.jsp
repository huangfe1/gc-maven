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
	<form action="<c:url value='/system/param/edit.json'/>"
		  class="form-horizontal" name="editForm" id="editForm" method="post">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title text-primary" id="myModalLabel">系统参数编辑</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-12 col-xs-12">

							<input type="hidden" name="id" value="${parameter.entity.code}">
							<div class="form-group" for="editCode">
								<label class="col-sm-2 control-label">参数代码</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" id="editCode" required
										   name="code" value="${parameter.entity.code}"
									<c:if test="${parameter.entity.code != null }">
										   readonly="readonly"
									</c:if>
										   placeholder="输入参数代码">
								</div>
								<div class="col-md-4 col-xs-4 text-error"></div>
							</div>

							<div class="form-group">
								<label for="editName" class="col-sm-2 control-label">参数名称</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" id="editName" required
										   name="name" value="${parameter.entity.name}"
										   placeholder="输入参数名称">
								</div>
								<div class="col-md-4 col-xs-4 text-error"></div>
							</div>
							<div class="form-group">
								<label for="editParamValue" class="col-sm-2 control-label">参数值</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" id="editParamValue" required
										   name="paramValue" value="${parameter.entity.paramValue}"
										   placeholder="输入系统参数值">
								</div>
								<div class="col-md-4 col-xs-4 text-error"></div>
							</div>
							<div class="form-group">
								<label for="editRemark" class="col-sm-2 control-label">参数说明</label>
								<div class="col-sm-6">
									<textarea rows="4" class="form-control" id="editRemark"
											  name="remark" value="${parameter.entity.remark}"
											  placeholder="输入系统参数说明">${parameter.entity.remark}</textarea>
								</div>
								<div class="col-md-4 col-xs-4 text-error"></div>
							</div>

							<c:if test="${parameter.entity.code==16||parameter.entity.code==17}">
								<div class="form-group">
									<label for="detailImg" class="col-sm-2 control-label">产品详情</label>
									<div class="col-sm-4">
										<div id="demo">
											<c:set value="${fn:split(parameter.entity.paramValue,'+')}" var="imgs"/>
											<c:forEach items="${imgs}" var="val" varStatus="i">
												<c:if test="${val!=''}">
													<div id="uploadedList_${i.index}" class="upload_append_list">
														<div class="file_bar">
															<div style="padding:5px;"><p class="file_name">${val}</p>
																<span class="ufile_del" data-index="${i.index}"
																	  title="删除"></span>
															</div>
														</div>
														<a style="height:100px;width:120px;" href="#" class="imgBox">
															<div class="uploadImg" style="width:105px"><img
																	id="uploadImage_0" class="upload_image"
																	src="<%=request.getContextPath()%>/dmz/img/goods/${val}"
																	style="width:expression(this.width > 105 ? 105px : this.width)">
															</div>
														</a>
														<p id="uploadedProgress_0" class="file_progress"></p>
														<p id="uploadedFailure_0" class="file_failure">上传失败，请重试</p>
														<p id="uploadedSuccess_0" class="file_success"></p>
													</div>
												</c:if>
											</c:forEach>
										</div>
										<span class="help-block">图片尺寸:200*200px 体积小于50kb</span>
									</div>
									<div class="col-md-4 col-xs-4 text-error"></div>
								</div>
							</c:if>

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
							<span class="glyphicon glyphicon-log-out">&nbsp;</span>关闭
						</button>
					</div>
					<div class="col-md-6 col-xs-12">
						<button type="button" class="btn btn-success btn-block"
								form="editForm" id="saveBtn" name="saveBtn" value="saveBtn"
								tabindex="4" data-loading-text="正在提交......">
							<span class="glyphicon glyphicon-save">&nbsp;</span>保存
						</button>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>

<%@include file="/WEB-INF/view/common/script_hf_upload.jsp" %>
<script type="text/javascript">
    $(function () {
        init();
    });
    function init() {
        $("#editCode").focus().select();
        var btn = null;
        $("#editForm").validate(
            {
                submitHandler: function (form) {
                    $(form).ajaxSubmit(
                        {
                            beforeSubmit: function () {
                                btn.button("loading");
                            },
                            dataType: "json",
                            success: function (responseText,
                                               statusText, xhr, $form) {
                                var m = $.parseJSON(xhr.responseText);
                                btn.button("reset");
                                if (m.flag == "0") {
                                    alert("保存成功");
                                    $(".quitBtn").click();
//                                    $("#search").click();
                                    window.location.reload();
                                } else {
                                    alert("保存失败," + m.message);
                                }

                            },
                            error: function (xhr, textStatus,
                                             errorThrown) {
                                var m = $.parseJSON(xhr.responseText);
                                $("#alert").empty().html(m.message)
                                    .removeClass("invisible");
                                btn.button("reset");
                            }
                        });
                },
                rules: {
                    name: {
                        required: true,
                        maxlength: 64
                    },
                    code: {
                        required: true,
                        maxlength: 8
                    },
                    paramValue: {
                        required: true
                    },
                    remark: {
                        maxlength: 128
                    }
                },
                onkeyup: false,
                messages: {
                    name: {
                        required: "请输入参数名称"
                    },
                    code: {
                        required: "请输入参数编码",
                        maxlength: "参数编码长度不能超过8个字符"
                    },
                    paramValue: {
                        required: "请输入参数值",
                        maxlength: "参数值不能超过64字符"
                    },
                    remark: {
                        maxlength: "参数说明不能超过128字符"
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
            <c:choose>
            <c:when test="${parameter.entity.code==16||parameter.entity.code==17}">
            ZYFILE.submit();
            </c:when>
            <c:otherwise>
            $("#editForm").submit();
            </c:otherwise>
            </c:choose>
        });
    }
</script>
