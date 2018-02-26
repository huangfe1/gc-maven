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

<div action="<c:url value="/dmz/count.html"/>">
    <br>
    <select name="dq" id="dq" class="form-control ld" data-lv="0">
        <option value="-1">选择大区${sm[0]}</option>
        <c:forEach items="${map[0]}" var="agent">
            <option value="${agent.id}">${agent.realName}</option>
        </c:forEach>
    </select>
    <br>
    <select name="sd" id="sd" class="form-control ld" data-lv="1">
        <option value="-1">选择省代</option>
        <c:forEach items="${map.get(1)}" var="agent">
            <option value="${agent.id}">${agent.realName}</option>
        </c:forEach>
    </select>
    <br>
    <select name="ds" id="ds" class="form-control ld" data-lv="2">
        <option value="-1">选择地市</option>
        <c:forEach items="${map.get(2)}" var="agent">
            <option value="${agent.id}">${agent.realName}</option>
        </c:forEach>
    </select>
    <br>
    <select name="qx" id="qx" class="form-control ld" data-lv="3">
        <option value="-1">选择区县</option>
        <c:forEach items="${map.get(3)}" var="agent">
            <option value="${agent.id}">${agent.realName}</option>
        </c:forEach>
    </select>
    <br>
    <select name="yw" id="yw" class="form-control ld" data-lv="4">
        <option value="-1">选择业务</option>
        <c:forEach items="${map.get(4)}" var="agent">
            <option value="${agent.id}">${agent.realName}</option>
        </c:forEach>
    </select>
    <br>
    <select name="yj" id="days" class="form-control">
        <option value="-11111111">查询项目</option>
        <option value="-11111111">总客户</option>
        <option value="90">预警户</option>
        <option value="150">流失户</option>
    </select>
    <br>


    <label for="">选择时间段:</label>

    <div class="input-group input-daterange">
        <input name="startTime" id="st" type="text" class="form-control" value="${startTime}">
        <div class="input-group-addon">to</div>
        <input name="endTime" id="et" type="text" class="form-control" value="${endTime}">
    </div>

    <br>
    <input class="sb btn btn-primary" type="button" value="查询" style="width: 100%"></input>


</div>


<br>
<table class="table  table-bordered">
    <thead id="dataTable">
    <tr>
        <th>名称</th>
        <th>数量</th>
    </tr>
    </thead>
    <tbody>

    </tbody>
</table>
<br>

<br>
<script src="https://cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-datepicker/1.7.0/js/bootstrap-datepicker.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-datepicker/1.7.0/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="http://ajax.microsoft.com/ajax/jquery.templates/beta1/jquery.tmpl.min.js"></script>




<script>
    $('.input-daterange input').each(function () {
        $(this).datepicker({
            autoclose: true,
            language: "zh-CN",
            todayHighlight: true,
            todayBtn: "linked",
            format: "yyyy-mm-dd",
            endDate: new Date()
        });
    });

    $(function () {
        //点击大区
        $(".ld").change(function () {
            var vl = $(this).val();
            var lv = $(this).attr("data-lv");
            //清空所有级别比他低的
            $(".ld").each(function () {
                var tlv = $(this).attr("data-lv");
                if (tlv > lv) {
                    $(this).find("option").each(function () {
                        if ($(this).val() > -1) $(this).remove();
                    })
                }
            })
            //如果选择了有效值,后台加载
            if (vl > -1) {
                uid = vl;
                getDatas(vl,$(this));
            }

        })

        var uid;

        $(".sb").click(function () {
            var startTime = $("#st").val();
            var endTime = $("#et").val();
            var days = $("#days").val();
            search(uid,startTime,endTime,days);
        })

        function getDatas(uid,ob) {
            var url = "<c:url value="/mobile/select/children.json"/>";
            var param = {
                "uid": uid
            }
            $.post(url, param, function (datas) {
                datas.forEach(function (k,v) {
                    //靠近的最后一个select联动
                    var data = JSON.stringify(k);
                    ob.nextAll(".ld").first().append($("#option").tmpl($.parseJSON(data)));
                })
            }).fail(function (xhr) {
                alert("未知错误,查询错误");
            })
        }

        function search(uid,startTime,endTime,days) {
            //首先清除上次的
            $(".btr").remove();
            var url = "<c:url value="/mobile/agent/count.json"/>";
            var param = {
                "uid":uid,
                "startTime":startTime,
                "endTime":endTime,
                "days":days
            }
            $.post(url,param,function (datas) {
                console.log(datas);
                //装载数据
                datas.forEach(function (v,k) {
                    var data = $.parseJSON(JSON.stringify(v));
                    $("#dataTable").append($("#bt").tmpl(data));
                })
            }).fail(function (xhr) {
                alert("未知错误,请联系管理员!");
            })
        }
    })


</script>

<script id="option" type="text/x-jquery-tmpl">
    <option value="{{= uid}}">{{= name}}</option>
</script>

<script id="bt" type="text/x-jquery-tmpl">
    <tr class="btr">
    <td class="btd">{{= name}}</td>
    <td class="btd">{{= number}}</td>
    </tr>
</script>
</body>
</html>
