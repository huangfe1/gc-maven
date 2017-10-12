<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <style>

        .con {
            position: relative;
            background-color: #EDEEED;
        }

        body{
            background-color: #EDEEED;
        }

        h4{
            color: #A6A7A6;
            margin-top: 1.5em;
            margin-left: 2em;
            margin-bottom: 0.5em;
            font-size: 1em;
            font-family: "Heiti SC";
        }

        ul{
            background-color: #FEFFFE;
            padding: 0 1em;
            font-size: 1.2em;
        }

        li{
            padding: 0.7em 0;
            border-bottom: 1px solid #EDEEED;
        }

        li span{
            /*float: left;*/
            text-align: left;
            /*padding-top: 0.5em;*/
        }

        li h5{
            /*padding: 0;*/
            margin-top: 0.3em;
            margin-bottom: 0;
            color: #B5B6B5;
            font-family: "Microsoft Sans Serif";
        }


    </style>
    <title>选择银行卡</title>
</head>
<body >
<div class="con">
    <h4>到账银行!</h4>

    <div class="cardInfo">
        <ul>

            <c:forEach items="${cards}" var="card">
                <li class="card" data-id="${card.id}">
                    <span>${card.name}(${fn:substring(card.number,card.number.length()-4 ,card.number.length() )})</span>
                    <h5>提现手续费率0.6%</h5>
                </li>
            </c:forEach>



            <li class="newCard">
                <span>使用新卡</span>
                <h5>点击绑定新卡</h5>
            </li>
        </ul>
    </div>

    <!--<div class="btndiv">-->
    <!--<button class="btn btn-danger">确定绑定</button>-->
    <!--</div>-->


</div>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>

    $(function () {

        $(".newCard").click(function () {
            window.location.href="<c:url value="/mobile/addCard.html"/>";
        })

        $(".card").click(function () {
            var cid = $(this).attr("data-id");
            var url = "<c:url value="/mobile/withdraw.html"/>?cid="+cid;
            window.location.href = url;
        })
    })


</script>
</body>
</html>