<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/common/common.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/resources/mallcss/initcss.css" rel="stylesheet">
    <link href="${ctx}/resources/mallcss/common.css" rel="stylesheet">

    <link href="https://cdn.bootcss.com/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker.min.css" rel="stylesheet">

    <style>

        .con {
            background-color: #ebebeb;
            position: relative;
        }

        header {
            background: #f03791;
            height: 3.7em;
            text-align: center;
            position: relative;
        }

        .Return, .Home {
            position: absolute;
            width: 3.7em;
            height: 3.7em;
        }

        .Return {
            left: 0;
        }

        .Home {
            right: 0;
        }

        .Return span, .Home span {
            background: url("${ctx}/resources/mallimages/png.png");
            background-size: 17em;
            position: absolute;
            width: 1.7em;
            height: 1.7em;
        }

        .Return span {
            background-position: -0.17em -5.5em;
            left: 1em;
            top: 1em;
        }

        .Home span {
            background-position: -0.17em -8.1em;
            left: 1em;
            top: 1em;
        }

        .Title {
            color: white;
            font-size: 1.3em;
            line-height: 2.7em;
        }

        .time {
            background-color: #f7f7f7;
            padding: 1em 0 1em 1em;
            position: relative;
            border-bottom: 1px solid #ebebeb;
        }

        .time h4 {
            color: #a5a5a5;
            padding-top: 0.5em;
            font-size: 1em;
        }

        .time img {
            position: absolute;
            width: 2em;
            right: 1em;
            top: 1.5em;
        }

        .records {
            background-color: #ffffff;
            overflow: hidden;
        }

        .records ul li {
            padding: 1.5em 0 1.5em 5.5em;
            border-bottom: 1px solid #ebebeb;
            position: relative;
        }

        .records ul li img {
            /*float: left;*/
            top: 1em;
            left: 1em;
            position: absolute;
            width: 3.5em;
            /*margin-right: 1em;*/
            border-radius:50%
        }

        .records ul li h3 {
            width: 13em;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            display: inline-block;
        }

        .records ul li h4 {
            padding-top: 0.5em;
            color: #a5a5a5;
            display: inline-block;
        }

        .records ul li span {
            float: right;
            font-size: 1.8em;
            padding-right: 0.5em;
            color: #F5BD2C;
        }

        .records ul li span.reduce {
            color: black;
        }


    </style>
    <title>${accountType.stateInfo}记录</title>
</head>
<body style="background: rgb(232, 232, 232);">
<div class="con">
    <%--<header>--%>
        <%--<a href="" class="Return"><span></span></a>--%>
        <%--<span class="Title">交易记录</span>--%>
        <%--<a href="" class="Home"><span></span></a>--%>
    <%--</header>--%>

    <div class="time">
        <h3>${date}</h3>
        <h4>支出￥${countSub}  收入￥${countAdd}</h4>
        <img class="datepicker" src="${ctx}/resources/mallimages/date.png" alt="">
    </div>

    <div class="records">
        <ul>
            <c:forEach items="${records}" var="record">
                <li>
                    <img src="${record.causedAgent.headimgurl}" alt="">
                    <h3>${record.info}</h3>
                        <c:if test="${record.addSub==0}">

                            <span class="reduce">-${record.amount}</span>

                        </c:if>
                        <c:if test="${record.addSub==1}">
                            <span >+${record.amount}</span>
                        </c:if>

                    <h4>${record.updateTime}</h4>
                </li>
            </c:forEach>
            <%--<li>--%>
            <%--<img src="${ctx}/resources/mallimages/djqcz.png" alt="">--%>
            <%--<h3>奖金充值-来自微信充值啊实打实的撒大撒所多</h3>--%>
            <%--<span>+500</span>--%>
            <%--<h4>2017-02-03</h4>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<img src="${ctx}/resources/mallimages/zz.png" alt="">--%>
            <%--<h3>奖金充值-来自微信充值啊实打实的撒大撒所多</h3>--%>
            <%--<span class="reduce">-500</span>--%>
            <%--<h4>2017-02-03</h4>--%>
            <%--</li>--%>
        </ul>
    </div>

</div>
</div>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-datepicker/1.7.1/js/bootstrap-datepicker.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-datepicker/1.7.1/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script>
    $(function () {
        $('.datepicker').datepicker({
            format: 'yyyy-mm-dd',
//            startDate: '-3d',
            autoclose: 'true',
            language : "zh-CN",
            todayHighlight : true,
            todayBtn : "linked",
            endDate:new Date()
        }).on('changeDate',function(ev){
            var date = ev.date.getFullYear().toString() + "-"+ (ev.date.getMonth()+1).toString()+ "-"+ ev.date.getDate().toString();
            freshDate(date);
        });
        
        function freshDate (date) {
            var url = "<c:url value="/mobile/accounts/records.html"/>"+"?date="+date+"&stateType="+${accountType.state};
            window.location.href=url;
        }
        
    })
</script>
</body>
</html>