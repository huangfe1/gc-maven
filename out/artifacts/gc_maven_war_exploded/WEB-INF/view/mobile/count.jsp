<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/common/common.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="${ctx}/resources/mallcss/initcss.css" rel="stylesheet">
    <link href="${ctx}/resources/mallcss/common.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker3.min.css" rel="stylesheet">
    <title>统计</title>
</head>
<body>

<form action="<c:url value="/dmz/count.html"/>">
    <br>

    <label for="">选择分公司:</label>
    <select name="agentCode" class="form-control">
        <option>全部分公司</option>
        <c:forEach items="${vips}" var="a">
            <option <c:if test="${a.id==agent.id}">selected</c:if> value="${a.agentCode}">${a.realName}</option>
        </c:forEach>
    </select>
    <br>

    <label for="">选择产品:</label>
    <select name="gid" class="form-control">
        <option value="-1">全部产品</option>
        <c:forEach items="${goodses}" var="g">
            <option
                    <c:if test="${g.id==goods.id}">selected</c:if> value="${g.id}">${g.name}</option>
        </c:forEach>
    </select>
    <br>
    <label for="">选择时间段:</label>

    <div class="input-group input-daterange">
        <input name="startTime" type="text" class="form-control" value="${startTime}" >
        <div class="input-group-addon">to</div>
        <input name="endTime" type="text" class="form-control" value="${endTime}">
    </div>

    <br>
    <input class="btn btn-primary" type="submit" value="查询" style="width: 100%"></input>



</form>


<h5 style="text-align: center">所选产品总销量：${sum}元</h5>
<br>
    <table class="table  table-bordered">
        <caption>所选产品总销量明细</caption>
        <thead>
        <tr>
            <th>名称</th>
            <th>销量</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${sMap.keySet()}" var="s">
        <tr>
            <td>${s}</td>
            <td>${sMap.get(s)}</td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>

<br>
<c:forEach items="${aMap.keySet()}" var="name">
    <table class="table  table-bordered">
        <caption>${name}的销量明细</caption>
        <thead>
        <tr>
            <th>名称</th>
            <th>销量</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${aMap.get(name).keySet()}" var="gn">
            <tr>
                <td>${gn}</td>
                <td>${aMap.get(name).get(gn)}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <br>
</c:forEach>
<script src="https://cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-datepicker/1.7.0/js/bootstrap-datepicker.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-datepicker/1.7.0/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script>
    $('.input-daterange input').each(function() {
        $(this).datepicker({
            autoclose: true,
            language: "zh-CN",
            todayHighlight: true,
            todayBtn: "linked",
            format: "yyyy-mm-dd",
            endDate: new Date()
        });
    });
</script>
</body>
</html>
