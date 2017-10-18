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
    <title>代金券转让</title>
</head>
<body>
<div id="wrapper">
    <jsp:include page="/menu.html"></jsp:include>
    <div id="page-wrapper">
        <nav class="navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <%--<div class="navbar-header">--%>
                <%--<button type="button" class="navbar-toggle animated flip pull-left"--%>
                <%--data-toggle="collapse" data-target="#top-navbar-collapse">--%>
                <%--<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>--%>
                <%--<span class="icon-bar"></span> <span class="icon-bar"></span>--%>
                <%--</button>--%>
                <%--</div>--%>
                <%--<div class="collapse navbar-collapse" id="top-navbar-collapse">--%>
                <div class="col-sm-12 col-md-12 col-xs-12">
                    <form class="navbar-form form-horizontal navbar-left" id="searchForm"
                          role="search" action="<c:url value='/accounts/index.html'/>"
                          method="GET">
                        <div class="form-group">
                            <label>转让时间</label>
                            <div class="input-daterange input-group" id="datepicker">
                                <span class="input-group-addon"><span
                                        class="glyphicon glyphicon-calendar"></span></span>
                                <input type="text" class="form-control pointer"
                                       value="${parameter.startTime }" id="startDate" data-date-format="yyyy-mm-dd"
                                       name="startTime" placeholder="开始日期"/>
                                <span class="input-group-addon">到</span> <input type="text"
                                                                                class="form-control pointer"
                                                                                value="${parameter.endTime }"
                                                                                name="endTime" placeholder="截止日期"/>
                                <span class="input-group-addon"><span
                                        class="glyphicon glyphicon-calendar"></span></span>
                            </div>
                            <%--<label>接受人编号</label>--%>
                            <%--<input type="text" name="entity.toAgent.agentCode"--%>
                                   <%--value="${parameter.entity.toAgent.agentCode}" class="form-control" id="toAgent">--%>

                            <label>接受人姓名</label>
                            <input type="text" name="entity.toAgent.realName"
                                   value="${parameter.entity.toAgent.realName}" class="form-control" id="toAgent">

                            <label>转让人姓名</label>
                            <input type="text" name="entity.fromAgent.realName"
                                   value="${parameter.entity.fromAgent.realName}" class="form-control" id="toAgent">

                            <label class="">电子币种类</label>
                            <select class="form-control" name="typeState">
                                <c:forEach items="${accountsTypes}" var="typeState">
                                    <option
                                            <c:if test="${parameter.entity.type.state eq typeState.state}">selected</c:if>
                                            value="${typeState.state}">${typeState.stateInfo}</option>
                                </c:forEach>
                            </select>

                            <button type="submit" class="btn btn-primary" id="search" name="search">
                                <span class="glyphicon glyphicon-search searchBtn"></span>&nbsp;查询
                            </button>
                        </div>
                    </form>
                    <ul class="nav navbar-nav navbar-right">
                        <li><button type="button"
                        class="btn btn-primary navbar-btn download">
                        <li class="fa fa-caret-square-o-down"></li>下载
                        </button></li>
                    </ul>
                </div>
            </div>

            <%--</div>--%>
        </nav>
        <div class="row">
            <div class="col-lg-12 col-md-12">
                <div class="table-responsive">
                    <table
                            class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>转让人</th>
                            <th>转让人代理编码</th>
                            <th>受让人</th>
                            <th>受让人代理编码</th>
                            <th>数量</th>
                            <th>备注</th>
                            <th>时间</th>
                            <%--<th>转让时间</th>--%>
                            <th>状态</th>
                            <%--<c:if test="${user.admin}">--%>
                            <%--<th>操作</th>--%>
                            <%--</c:if>--%>
                        </tr>
                        </thead>
                        <tbody id="dataList">
                        <c:forEach items="${transfers}" var="l">
                            <tr data-row="${l.id}">
                                <td>${l.fromAgent.realName}</td>
                                <td>${l.fromAgent.agentCode}</td>
                                <td>${l.toAgent.realName}</td>
                                <td>${l.toAgent.agentCode}</td>
                                <td>${l.amount}</td>
                                <td>(${l.type.stateInfo})${l.remark}</td>
                                <td>${l.updateTime}</td>
                                <%--<td>${l.transferTime}</td>--%>
                                <c:if test="${l.status.state==4}">
                                    <td><a class="btn btn-success default ajaxLink editBtn"
                                           href="<c:url value='/mobile/withdraw/confirm.json?aid=${l.id}' /> "><span class="glyphicon glyphicon-ok" aria-hidden="true"></span>同意打款</a>
                                        <a class="ajaxLink btn btn-danger default"
                                           href="<c:url value='/mobile/withdraw/refuse.json?aid=${l.id}' /> "><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>拒绝打款</a>
                                    </td>
                                </c:if>

                                <c:if test="${l.status.state!=4}">
                                    <td>${l.status.stateInfo}</td>
                                </c:if>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <jsp:include page="/WEB-INF/view/common/pager.jsp"></jsp:include>
            </div>
        </div>
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


        $(".download").click(function(){
            $("#searchForm").attr("action", "<c:url value='/accounts/download.html'/>")
            $("#searchForm").submit();
        });

        $("#dataList a.ajaxLink").click(function (e) {

            e.preventDefault();
            e.stopPropagation();
            var method = "GET";
            if ($(this).attr("data-role") == "delete") {
                if (!window.confirm("代理数据删除将不能恢复,是否继续?")) {
                    return false;
                }
                method = "DELETE";
            }
            if (!window.confirm("是否确定打款/拒绝,是否继续?")) {
                return false;
            }
            $.ajax({
                url: $(e.target).attr("href"), method: method, complete: function (xhr, ts) {
                    if (xhr.status >= 200 && xhr.status < 300) {
//                        var m=$.parseJSON(xhr.responseText);
                        var m = xhr.responseText;
                        console.log(m);
                        if (m.indexOf("error") != -1) {
                            $("#alertMessageBody").empty().html("操作失败" + m).addClass("text-danger");
                            $("#myAlertModal").modal({backdrop: "static", show: true});
                        } else {
                            $("#alertMessageBody").empty().html("操作成功");
//                            alert( $("#messageBody").html());
                            $("#myAlertModal").modal({backdrop: "static", show: true});
                            setTimeout(function () {
                                $("#search").click();
                            },1000);
                            }

                    } else {
                        $("#messageBody").empty().html("操作失败").addClass("text-danger");
                        $("#myModal").modal({backdrop: "static", show: true});
                    }
                }
            });
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
                    "<c:url value='/accounts/transfer.html'/>",
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
