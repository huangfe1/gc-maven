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
    <%@include file="/WEB-INF/view/common/head_css.jsp" %>
    <%@include file="/WEB-INF/view/common/head_css_startbootstrap.jsp" %>
    <%@include file="/WEB-INF/view/common/head_css_morris.jsp" %>
    <%@include file="/WEB-INF/view/common/head_css_treeview.jsp" %>
    <%@include file="/WEB-INF/view/common/head_css_datatables.jsp" %>
    <%@include file="/WEB-INF/view/common/datepicker_css.jsp" %>
    <%@include file="/WEB-INF/view/common/head_css_fav.jsp" %>
    <style>
        .input-daterange {
            width: inherit !important;
        }
    </style>
    <title>奖金转让</title>
</head>
<body>
<div id="wrapper">
    <jsp:include page="/menu.html"></jsp:include>
    <div id="page-wrapper">
        <nav class="navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <div class="collapse navbar-collapse" id="top-navbar-collapse">
                    <div class="col-md-12 col-xs-12">
                        <form class="navbar-form form-horizontal navbar-left" id="searchForm"
                              role="search" action="<c:url value='/delivery/count.html'/>"
                              method="GET">
                            <div class="form-group">

                                <!--获利人姓名-->
                                <label class="">代理编号</label> <input type="text"
                                                                    value="${agentCode}" name="agentCode" id="realName"
                                                                    autofocus class="typeahead form-control"
                                                                    placeholder="代理编号">


                                <label>时间</label>
                                <div class="input-daterange input-group" id="datepicker">
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                    <input type="text" class="form-control pointer"
                                           value="${startTime}" id="startDate" data-date-format="yyyy-mm-dd"
                                           name="startTime" placeholder="开始日期" />
                                    <span class="input-group-addon">到</span> <input type="text"
                                                                                    class="form-control pointer"
                                                                                    value="${endTime }"
                                                                                    name="endTime" placeholder="截止日期" />
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>


                                <%--<label class="">电子币种类</label>--%>
                                <%--<select class="form-control" name="typeState">--%>
                                <%--<c:forEach items="${accountsTypes}" var="type">--%>
                                <%--<option--%>
                                <%--<c:if test="${parameter.entity.accountsType.state eq type.state}">selected</c:if>--%>
                                <%--value="${type.state}">${type.stateInfo}</option>--%>
                                <%--</c:forEach>--%>
                                <%--</select>--%>

                                <button type="submit" class="btn btn-primary" id="search"
                                        name="search">
                                    <span class="glyphicon glyphicon-search searchBtn"></span>&nbsp;查询
                                </button>
                                <button type="button" class="btn btn-danger  btn-re" id="download"
                                        name="search">
                                    <span class="glyphicon glyphicon-search searchBtn"></span>&nbsp;下载
                                </button>

                                <label class="">	<c:forEach items="${levels}" var="level">
                                    ${level[0]} : ${level[1]}
                                </c:forEach></label>
                                <c:forEach items="${gMap}" var="g">
                                    <label class="">   ${g[0]} : ${g[1]}</label>
                                </c:forEach>
                                <%--<label class="">   直接代理业绩合计 : ${mySum}</label>--%>
                                <%--<label class="">   系统总业绩合计 : ${allSum}</label>--%>

                            </div>
                        </form>
                        <!-- 	<ul class="nav navbar-nav navbar-right">
                                <li><button type="button"
                                        class="btn btn-primary navbar-btn transBtn">
                                        <li class="fa fa-exchange fa-fw"></li>转让奖金
                                    </button></li>
                            </ul> -->

                    </div>
                </div>

            </div>
        </nav>
        <div class="row">
            <div class="col-lg-12 col-md-12">
                <div class="table-responsive">
                    <table
                            class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>单号</th>
                            <th>分公司</th>
                            <th>业务员</th>
                            <th>时间</th>
                            <th>产品</th>
                            <th>金额</th>
                            <th>状态</th>
                        </tr>
                        </thead>
                        <tbody id="dataList">
                        <c:forEach items="${notes}" var="l">
                            <tr data-row="${l.id}">
                                <td>${l.logisticsCode}</td>
                                <td>${l.applyAgent.parent.parent.realName}</td>
                                <td>${l.applyAgent.parent.realName}(${l.applyAgent.parent.agentCode})</td>
                                <td>${l.applyTime}</td>
                                <td>
                                <c:forEach items="${l.deliveryItems}" var="item">
                                    ${item.goods.name}X${item.quantity}
                                </c:forEach>
                                </td>
                                <td>${l.amount}</td>
                                <td>${l.status.desc}</td>
                                <%--<td>${l.agent.realName}(${l.agent.agentCode})</td>--%>
                                <%--<c:if test="${l.addSub==0}">--%>
                                    <%--<td style="color: red">支出</td>--%>
                                <%--</c:if>--%>
                                <%--<c:if test="${l.addSub==1}">--%>
                                    <%--<td>进账</td>--%>
                                <%--</c:if>--%>
                                <%--<td>${l.info}</td>--%>
<%----%>
                                <%--<td>${l.amount}</td>--%>
                                <%--<td>${l.nowAmount}</td>--%>
                                <%--<td>${l.updateTime}</td>--%>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <%--<div class="row">--%>
        <%--<div class="col-md-12 col-sm-12 col-xs-12">--%>
        <%--<jsp:include page="/WEB-INF/view/common/pager.jsp"></jsp:include>--%>
        <%--</div>--%>
        <%--</div>--%>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel"></h4>
            </div>
            <div class="modal-body" id="messageBody"></div>
            <div class="modal-footer"></div>
        </div>
    </div>
</div>
<div class="modal fade" id="myAlertModal" tabindex="-1" role="dialog" style="z-index:999999;"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel"></h4>
            </div>
            <div class="modal-body" id="alertMessageBody"></div>
            <div class="modal-footer"></div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/view/common/head.jsp"></jsp:include>
<jsp:include page="/WEB-INF/view/common/datatables.jsp"></jsp:include>
<jsp:include page="/WEB-INF/view/common/startbootstrap.jsp"></jsp:include>
<jsp:include page="/WEB-INF/view/common/datepicker_js.jsp"></jsp:include>
<jsp:include page="/WEB-INF/view/common/script_common.jsp"></jsp:include>
<script type="text/javascript">
    $(function () {
        $("#download").click(function () {
            $("#searchForm").attr("action", "<c:url value='/delivery/count/download.html'/>")
            $("#searchForm").submit();
        });

        $("#datepicker.input-daterange").datepicker({
            autoclose: true,
            language: "zh-CN",
            todayHighlight: true,
            todayBtn: "linked",
            format: "yyyy-mm-dd",
            endDate: new Date()
        });
        $(".transBtn").click(
            function (e) {
                e.preventDefault();
                e.stopPropagation();
                $("#myModal").load(
                    "<c:url value='/voucher/transfer.html'/>",
                    function (e) {
                        $('#myModal').modal({
                            backdrop: "static"
                        });
                    });
            });
        $("tbody tr[data-row]").each(function (index, row) {
            $(row).click(function (event) {
                if (event.target.nodeName != "INPUT") {
                    rowSelect($(this));
                }
                switchCss($(this));
            });
            $(row).bind("contextmenu", function () {
                return false;
            });
        });
    });
</script>
</body>
</html>
