<%@ page import="com.dreamer.util.TokenInfo" %>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/common/common.jsp" %>
<div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
        <div class="modal-header bg-primary">
            <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <h4 class="modal-title " id="myModalLabel"><span class="fa fa-edit fa-fw"></span>积分商城信息编辑</h4>
        </div>
        <div class="modal-body">
            <div class="container-fluid">
                <form action="<c:url value='/goods/category/edit.json'/>" name="editForm" enctype="multipart/form-data"
                      class="form-horizontal" id="editForm" method="post">
                    <div class="row">
                        <div class="col-md-12 col-xs-12">
                            <input type="hidden" name="id" value="${parameter.entity.id}">
                            <div class="form-group">
                                <label for="name" class="col-sm-2 control-label">上级类型</label>
                                <div class="col-sm-6">
                                    <p class="form-control-static">${parameter.entity.parentType==null?'无':parameter.entity.parentType.name}</p>
                                    <c:if test="${parameter.entity.parentType.id!=null}">
                                        <input type="hidden" name="parentType.id"
                                               value="${parameter.entity.parentType.id}">
                                    </c:if>

                                    <input type="hidden" name="type"
                                           value="${parameter.entity.parentType==null?0:1}">
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-sm-2 control-label">类型名称</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="editName" tabIndex="10"
                                           name="name" value="${parameter.entity.name}"
                                           placeholder="输入商品名称">
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>


                            <div class="form-group">
                                <label for="img" class="col-sm-2 control-label">产品图片</label>
                                <div class="col-sm-4">
                                    <input type="file" class="form-control" id="img" tabIndex="12"
                                           name="imgFile" accept="image/png,image/jpeg,image/gif" placeholder="产品图片">
                                    <span class="help-block">图片尺寸:200*105px 体积小于50kb</span>
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>

                            <div class="form-group">
                                <label for="orderIndex" class="col-sm-2 control-label">排序</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="orderIndex" tabIndex="10"
                                           name="orderIndex" value="${parameter.entity.orderIndex}"
                                           placeholder="排列顺序">
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>
                            <!-- </form>-->
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <div class="col-md-6 col-xs-12">
                    <button type="button" class="btn btn-default btn-block quitBtn" tabIndex="26"
                            id="quitBtn" data-dismiss="modal" name="quitBtn" value="login"
                            tabindex="4" data-loading-text="正在返回......">
                        <span class="glyphicon glyphicon-remove-sign">&nbsp;</span>关闭
                    </button>
                </div>
                <div class="col-md-6 col-xs-12">
                    <button type="button" class="btn btn-success btn-block" form="editForm" tabIndex="27"
                            id="saveBtn" name="saveBtn" value="saveBtn" tabindex="4"
                            data-loading-text="正在提交......">
                        <span class="glyphicon glyphicon-floppy-save">&nbsp;</span>保存
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>


<jsp:include page="/WEB-INF/view/common/form.jsp"></jsp:include>
<%--<%@include file="/WEB-INF/view/common/script_hf_upload.jsp" %>--%>
<script type="text/javascript">
    $(function () {
        init();
        $('#mySwitch').on('switch-change', function (e, data) {
            var $el = $(data.el)
                    , value = data.value;
            console.log(e, $el, value);
        });
    });
    function init() {
        $("#editName").focus().select();
        var btn = null;
        $("#editForm").validate({
            submitHandler: function (form) {
                $(form).ajaxSubmit({
                    beforeSubmit: function (arr, $form, options) {
                        btn.button("loading");
                    },
                    success: function (responseText,
                                       statusText, xhr, $form) {
                        var m = $.parseJSON(xhr.responseText);
                        btn.button("reset");
                        if (m.flag == "0") {
                            alert("保存成功");
//                            $(".quitBtn").click();
//                            $("#searchDT").click();
                            location.reload();
                        } else {
                            alert("保存失败" + m.message);
                        }

                    },
                    error: function (xhr, textStatus,
                                     errorThrown) {
                        btn.button("reset");
                        console.log(xhr);
                        alert("保存失败，未知错误");
                    }
                });
            },
            rules: {
                name: {
                    required: true
                },
                spec: {
                    required: true
                },
                price: {
                    number: true,
                    min: true,
                    required: true
                },
                pointPrice: {
                    required: true,
                    number: true
                },
                moneyPrice: {
                    required: true,
                    number: true
                },
                voucher: {
                    required: true,
                    number: true,
                    min: true
                },
                vouchers: {
                    required: true
                },
                benefitPoints: {
                    required: true,
                    number: true,
                    min: true
                },
                stockQuantity: {
                    number: true
                }
            },
            onkeyup: false,
            messages: {
                name: {
                    required: "商品名必须填写"
                },
                vouchers: {
                    required: "填写返利制度"
                },
                spec: {
                    required: "商品规格必须填写"
                },
                price: {
                    number: "价格必须为数字",
                    min: "价格不能为负数",
                    required: "价格需填写"
                },
                pointPrice: {
                    number: "积分必须为数字",
                    min: "积分不能为负数",
                    required: "所需积分不能为空"
                },
                moneyPrice: {
                    number: "积分价格必须为数字",
                    min: "积分价格不能为负数",
                    required: "积分价格必须填写"
                },
                voucher: {
                    required: "返券额度需填写",
                    min: "返券额度不能为负数",
                    number: "返券额度必须为数字"
                },
                benefitPoints: {
                    required: "返福利积分数需填写",
                    number: "福利积分必须为数字",
                    min: "福利积分数不能为负"
                },
                stockQuantity: {
                    number: "当前库存必须为数字"
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
        $("#editForm").find("input[type='checkbox']").change(function (e) {
            var $t = $(this);
            var next = $t.next("input[type='hidden']");
            $t.prop("checked") ? next.val(1) : next.val(0);
        });
        $("#saveBtn").click(function (e) {
            btn = $(this).button();
			$(document.forms["editForm"]).submit();
//            ZYFILE.submit();
        });
    }
</script>
