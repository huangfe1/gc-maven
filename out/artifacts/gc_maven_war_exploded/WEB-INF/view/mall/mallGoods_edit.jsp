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
            <h4 class="modal-title " id="myModalLabel"><span class="fa fa-edit fa-fw"></span>优惠商城信息编辑</h4>
        </div>
        <div class="modal-body">
            <div class="container-fluid">
                <form action="<c:url value='/mall/goods/edit.json'/>" name="editForm" enctype="multipart/form-data"
                      class="form-horizontal" id="editForm" method="post">
                    <div class="row">
                        <div class="col-md-12 col-xs-12">
                            <input type="hidden" name="id" value="${parameter.entity.id}">
                            <div class="form-group">
                                <label for="name" class="col-sm-2 control-label">商品名称</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="editName" tabIndex="10"
                                           name="name" value="${parameter.entity.name}"
                                           placeholder="输入商品名称">
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>

                            <!-- 产品类型 -->
                            <div class="form-group">
                                <label for="spec" class="col-sm-2 control-label">产品类型</label>
                                <div class="col-sm-6">
                                    <select name="mType" class="form-control">
                                        <option>
                                            选择分类
                                        </option>
                                        <c:forEach items="${types}" var="type">

                                            <option value="${type.id}"
                                                    <c:if test="${type.id eq parameter.entity.goodsType.id}">selected</c:if>>
                                                    ${type.name}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>

                            <!--产品的描述白哦题-->
                            <div class="form-group">
                                <label for="shareName" class="col-sm-2 control-label">分享标题</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="shareName" tabIndex="10"
                                           name="shareName" value="${parameter.entity.shareName}"
                                           placeholder="分享标题">
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>

                            <!--产品的描述-->
                            <div class="form-group">
                                <label for="shareDetail" class="col-sm-2 control-label">分享描述</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="shareDetail" tabIndex="10"
                                           name="shareDetail" value="${parameter.entity.shareDetail}"
                                           placeholder="分享描述">
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>

                            <!--产品的利润-->
                            <div class="form-group">
                                <label for="profit" class="col-sm-2 control-label">毛利润</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="profit" tabIndex="10"
                                           name="profit" value="${parameter.entity.profit}"
                                           placeholder="纯利润">
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>
                            <div class="form-group">
                                <label for="pointFactor" class="col-sm-2 control-label">商品规格</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="editSpec" tabIndex="11"
                                           name="spec" value="${parameter.entity.spec}"
                                           placeholder="输入商品规格">
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>
                            <div class="form-group">
                                <label for="img" class="col-sm-2 control-label">产品图片</label>
                                <div class="col-sm-4">
                                    <input type="file" class="form-control" id="img" tabIndex="12"
                                           name="img" accept="image/png,image/jpeg,image/gif" placeholder="产品图片">
                                    <span class="help-block">图片尺寸:200*200px 体积小于50kb</span>
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>

                            <div class="form-group">
                                <label for="img" class="col-sm-2 control-label">产品墙图片</label>
                                <div class="col-sm-4">
                                    <input type="file" class="form-control" id="wallImg" tabIndex="12"
                                           name="wallImg" accept="image/png,image/jpeg,image/gif" placeholder="产品墙图片">
                                    <span class="help-block">高度158-400随意</span>
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>

                         =


                            <!--产品详情页-->
                            <div class="form-group">
                                <label for="detailImg" class="col-sm-2 control-label">产品详情</label>
                                <div class="col-sm-4">
                                    <div id="demo">
                                        <c:set value="${fn:split(parameter.entity.detailImg,'+')}" var="imgs"/>
                                        <c:forEach items="${imgs}" var="val" varStatus="i">
                                            <c:if test="${val!=''}">
                                                <div id="uploadedList_${i.index}" class="upload_append_list">
                                                    <div class="file_bar">
                                                        <div style="padding:5px;"><p class="file_name">${val}</p><span
                                                                class="ufile_del" data-index="${i.index}"
                                                                title="删除"></span>
                                                        </div>
                                                    </div>
                                                    <a style="height:100px;width:120px;" href="#" class="imgBox">
                                                        <div class="uploadImg" style="width:105px"><img
                                                                id="uploadImage_0" class="upload_image"
                                                                src="${dmzImgPath}${val}"
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

                            <%--<div class="form-group">--%>
                            <%--<label for="currentStock" class="col-sm-2 control-label">原价</label>--%>
                            <%--<div class="col-sm-4">--%>
                            <%--<input type="number" class="form-control" id="editPrice" tabIndex="12" required--%>
                            <%--name="price" value="${parameter.entity.price}"--%>
                            <%--placeholder="输入商品价格">--%>
                            <%--<span class="help-block">非积分+现金消费时的价格</span>--%>
                            <%--</div>--%>
                            <%--<div class="col-md-4 col-xs-4 text-error"></div>--%>
                            <%--</div>--%>



                            <div class="form-group">
                                <label for="price" class="col-sm-2 control-label">原价格</label>
                                <div class="col-sm-4">
                                    <input type="number" class="form-control" id="price" tabIndex="14"
                                           name="price" value="${parameter.entity.price}"
                                           placeholder="原价格">
                                    <span class="help-block">原价格</span>
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>


                            <div class="form-group">
                                <label for="retailPrice" class="col-sm-2 control-label">优惠价格</label>
                                <div class="col-sm-4">
                                    <input type="number" class="form-control" id="retailPrice" tabIndex="14"
                                           name="retailPrice" value="${parameter.entity.retailPrice}"
                                           placeholder="优惠价格">
                                    <span class="help-block">优惠价格</span>
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>


                            <div class="form-group">
                                <label for="editVouchers" class="col-sm-2 control-label">模式</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="editVouchers" tabIndex="15"
                                           name="vouchers" value="${parameter.entity.vouchers}" required
                                           placeholder="输入商品返券模式">
                                    <span class="help-block">返利制度</span>
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>

                            <div class="form-group">
                                <label for="editStockQuantity" class="col-sm-2 control-label">当前库存</label>
                                <div class="col-sm-4">
                                    <input type="number" class="form-control" id="editStockQuantity" tabIndex="17"
                                           name="stockQuantity" value="${parameter.entity.stockQuantity}"
                                           placeholder="输入商品当前库存">
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>



                            <!--是否上架-->
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-6">
                                    <div class="shelf">
                                        <label> <input type="checkbox" id="activity"
                                        <c:if test="${parameter.entity.shelf == true }"> checked="checked"</c:if> name="shelf">是否上架
                                            <input type="hidden" name="shelf" value="${parameter.entity.shelf ? 1 : 0}">
                                        </label>
                                    </div>
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>


                            <!--是否活动-->
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-6">
                                    <div class="activity">
                                        <label> <input type="checkbox" id="activity"
                                        <c:if test="${parameter.entity.activity == true }"> checked="checked"</c:if> name="activity">是否活动
                                            <input type="hidden" name="activity" value="${parameter.entity.activity ? 1 : 0}">
                                        </label>
                                    </div>
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>



                            <!--是否预存款能购买-->
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-6">
                                    <div class="activity">
                                        <label> <input type="checkbox" id="activity"
                                        <c:if test="${parameter.entity.canAdvance == true }"> checked="checked"</c:if> name="activity">是否置换
                                            <input type="hidden" name="activity" value="${parameter.entity.activity ? 1 : 0}">
                                        </label>
                                    </div>
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>



                            <div class="form-group">
                                <label for="limitNumer" class="col-sm-2 control-label">活动限制数量</label>
                                <div class="col-sm-4">
                                    <input type="number" class="form-control" id="limitNumer"
                                           name="limitNumer" value="${parameter.entity.limitNumer}" tabIndex="17"
                                           placeholder="活动限制数量">
                                </div>
                                <div class="col-md-4 col-xs-4 text-error">上次活动时间${parameter.entity.startTime}</div>
                            </div>

                            <div class="form-group">
                                <label for="editOrder" class="col-sm-2 control-label">排列顺序</label>
                                <div class="col-sm-4">
                                    <input type="number" class="form-control" id="editSequence"
                                           name="sequence" value="${parameter.entity.sequence}" tabIndex="17"
                                           placeholder="输入显示顺序">
                                </div>
                                <div class="col-md-4 col-xs-4 text-error"></div>
                            </div>
                            <!-- </form>-->
                        </div>
                        <div class="col-md-12 col-xs-12">
                            <div class="panel panel-primary ">
                                <div class="panel-heading form-inline">
                                    <span>#</span>
                                    <div class="form-group">
                                        <label for="exampleInputName2"><span>规格：需要选择&nbsp</span></label>
                                        <input style="width: 4em;text-align: center" type="text" class="form-control input-sm"  maxlength="1" value="${parameter.entity.sel}" id="exampleInputName2" name="sel" >
                                    </div>
                                </div>
                                <div class="table-responsive">
                                    <table id="standardTable" class="table table-condensed">
                                        <thead>
                                        <tr>
                                            <th>分类名称</th>
                                            <th>单价</th>
                                            <th>库存</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:if test="${parameter.entity.goodsStandards.size()>0}">
                                            <c:forEach items="${parameter.entity.goodsStandards}" var="standard" varStatus="sta">
                                                <input type="hidden" name="ids" value="${standard.id}">
                                                   <tr>
                                                       <td><input type="text" class="form-control" value="${standard.name}" placeholder="分类名称"
                                                                  name="standardNames"></td>
                                                       <td><input type="number" class="form-control" value="${standard.price}" placeholder="单价"
                                                                  name="standardPrices"></td>
                                                       <td><input type="number" class="form-control" value="${standard.stock}" placeholder="库存"
                                                                  name="standardStocks"></td>
                                                       <td>
                                                           <a class="btn btn-danger default deleteBtn" href="#"><li class="glyphicon glyphicon-trash"></li>删除</a>
                                                           <c:if test="${sta.index==parameter.entity.goodsStandards.size()-1}">
                                                               <a class="btn btn-primary default addBtn" href="#"><li class="fa fa-plus-square fa-fw"></li>新增</a>
                                                           </c:if>
                                                       </td>
                                                   </tr>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${parameter.entity.goodsStandards.size()==0}">
                                            <input type="hidden" name="ids" value="0">
                                            <tr>
                                                <td><input type="text" class="form-control" placeholder="分类名称"
                                                           name="standardNames"></td>
                                                <td><input type="number" class="form-control" placeholder="单价"
                                                           name="standardPrices"></td>
                                                <td><input type="number" class="form-control" placeholder="库存"
                                                           name="standardStocks"></td>
                                                <td>
                                                    <a class="btn btn-danger default deleteBtn" href="#"><li class="glyphicon glyphicon-trash"></li>删除</a>
                                                    <a class="btn btn-primary default addBtn" href="#"><li class="fa fa-plus-square fa-fw"></li>新增</a>
                                                </td>
                                            </tr>
                                        </c:if>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
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
<%@include file="/WEB-INF/view/common/script_hf_upload.jsp" %>
<script type="text/javascript">
    $(function () {
        init();
        standard();
        $('#mySwitch').on('switch-change', function (e, data) {
            var $el = $(data.el)
                , value = data.value;
            console.log(e, $el, value);
        });
    });
    function standard(){
        $("#standardTable").delegate(".addBtn","click",function (e) {
            //隐藏新增按钮
            $(this).hide();
            var parentTd = $(this).closest("td");
            //显示删除按钮
            parentTd.children(".deleteBtn").show();
            var td = "<tr>\n" +
                "<input type=\"hidden\" name=\"ids\"  value=\"0\">\n" +
                " <td><input type=\"text\" class=\"form-control\" placeholder=\"分类名称\"\n" +
                "                                                       name=\"standardNames\"></td>\n" +
                " <td><input type=\"number\" class=\"form-control\" placeholder=\"单价\"\n" +
                "                                                       name=\"standardPrices\"></td>\n" +
                " <td><input type=\"number\" class=\"form-control\" placeholder=\"库存\"\n" +
                "                                                       name=\"standardStocks\"></td>\n" +
                " <td>\n" +
                "     <a class=\"btn btn-success default deleteBtn\"  href=\"#\"><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span>删除</a>\n" +
                "\n" +
                "     <a class=\"btn btn-primary default addBtn\" href=\"#\"><li class=\"fa fa-plus-square fa-fw\"></li>新增</a>\n" +
                " </td>\n" +
                "</tr>";
            $(td).appendTo($("#standardTable"));
        });

        $("#standardTable").delegate(".deleteBtn","click",function (e) {
            var  tr = $(this).closest("tr");
            var countTr = tr.next().length;
            if(countTr<=0){
                tr.prev().find(".addBtn").show();//上一个tr显示增加
            }
            tr.remove();//移除当前tr
        });
    }
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
                            $(".quitBtn").click();
                            $("#searchDT").click();
                        } else {
                            alert("保存失败" + m.message);
                        }

                    },
                    error: function (xhr, textStatus,
                                     errorThrown) {
                        btn.button("reset");
                        alert("积分商品保存失败");
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
                },
                sel:{
                    number:true,
                    required:true
                },
                standardNames:{
                    required:true
                },
                standardPrices:{
                    required:true
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
                },
                sel:{
                    number:"填写数字",
                    required:"请填写选择的数量"
                },
                standardNames:{
                    required:"请填写规格名字"
                },
                standardPrices:{
                    required:"请填写规格价格"
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
//			$(document.forms["editForm"]).submit();
            ZYFILE.submit();
        });
    }
</script>
