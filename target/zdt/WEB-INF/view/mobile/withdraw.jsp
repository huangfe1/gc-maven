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
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <style>

        .con {
            position: relative;
        }

        body {
            color: #EDEEED;
        }

        header {
            background: #f03791;
            height: 3.7em;
            text-align: center;
            position: relative;
        }

        .panel {
            margin: 1.5em 1em 0;
            text-align: center;
            border: 1px solid;
            -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .1);
        }

        .panel .cardInfo {
            background-color: #FAFBFA;
            padding: 2.2em 0 1em 2.2em;
        }

        .cardInfo span {
            color: black;
            font-size: 1.2em;
            font-family: "Microsoft Sans Serif";
            float: left;
        }

        .cardInfo div {
            padding-left: 8em;
            text-align: left;
        }

        .cardInfo a {
            color: #6A748A;
            font-size: 1.2em;
            font-family: "Microsoft Sans Serif";
            /*float: left;*/
            /*padding-left: 2.4em;*/
        }

        .cardInfo h3 {
            /*color: #6A748A;*/
            /*font-size: 1.2em;*/
            /*float: right;*/
            font-family: "Microsoft Sans Serif";

            /*padding-left: 3em;*/
            padding-top: 0.5em;
        }

        .panel .cardInfo img {
            padding: 1.5em 0 0.5em 0;
            width: 4em;
        }

        .panel .cardInfo h3 {
            color: #a5a5a5;
            padding-bottom: 1em;
        }

        .panel .transfer {
            text-align: left;
            padding: 1.5em 2em;
            background-color: #FEFFFE;
        }

        .panel .transfer h3 {
            color: #292A29;
            padding-bottom: 1.5em;
        }

        .panel .transfer .inputs {
            position: relative;
            overflow: hidden;
            border-bottom: 2px solid;

        }

        .panel .transfer .inputs strong {
            color: black;
            font-size: 2em;
            position: absolute;
            left: 0;
            top: 0.13em;
            /*font-weight: 800;*/
        }

        .panel .transfer .inputs input {
            font-size: 3.5em;
            width: 80%;
            border: none;
            padding-left: 0.6em;
            outline: none;
            font-family: "Microsoft Sans Serif";
            /*float: right;*/
        }

        .panel .transfer a {
            color: #8D8E8D;
            letter-spacing: 0.1em;
            margin: 1em 0 1.5em;
            display: inline-block;
            font-size: 1.1em;
            font-family: Kai;
        }

        .panel .transfer .btn {
            color: white;
            padding: 0.8em 0;
            border-radius: 4px;
            text-align: center;
            background-color: #19AD15;
            margin: 1em 0;
            -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .1);
            font-size: 1.35em;

        }
    </style>
    <title>奖金提现</title>
</head>
<body>
<div class="con">
    <!--<header>-->
    <!--<a href="" class="Return"><span></span></a>-->
    <!--<span class="Title">代金券转账</span>-->
    <!--<a href="" class="Home"><span></span></a>-->
    <!--</header>-->

    <div class="panel">
        <div class="cardInfo">

            <!--<img src="resources/imagestem/ava.jpeg" alt="">-->
            <span>到账银行卡</span>
            <input type="hidden" class="cid" value="${card.id}">

            <div>
                <c:if test="${card==null}">
                    <a id="addCard">点击绑定银行卡</a>
                </c:if>
                <c:if test="${card != null}">
                    <a id="card">${card.bank}(${fn:substring(card.number, card.number.length()-4, card.number.length())})</a>
                </c:if>

                <h3>提现手续费0.6%</h3>
            </div>

        </div>
        <div class="transfer">
            <h3>提现金额</h3>
            <div class="inputs">
                <strong>￥</strong>
                <input class="amount" value="" autofocus>
            </div>

            <a href="">
                当前余额<span class="balance">${balance}</span>
                ,
                <span id="all" style="color: #5D6286">全部提现</span>
            </a>

            <div>   <input   type="checkbox"  id="isAdvance"> <span style="color: #5D6286"> 转成预存款</span></div>



            <div class="btn" id="btn" <c:if test="${card==null}">style="background:#A3DFA2;"</c:if>  ><span>提现</span></div>
        </div>

    </div>


</div>
</div>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script>

    $("#all").click(function (e) {
        e.preventDefault();
        e.stopPropagation();
        var bal = $(".balance").html();
        $(".amount").val(bal);
    })

    //验证金额
    $(".amount").keyup(function () {
        var reg = $(this).val().match(/\d+\.?\d{0,2}/);
        var txt = '';
        if (reg != null) {
            txt = reg[0];
        }
        $(this).val(txt);

    }).change(function () {
        $(this).keypress();
        var v = $(this).val();
        if (/\.$/.test(v)) {
            $(this).val(v.substr(0, v.length - 1));
        }
    });

    //新增银行卡
    $("#addCard").click(function () {
        var url = "<c:url value="/mobile/addCard.html"/>";
        window.location.href = url;
    });

    //选择银行卡
    $("#card").click(function () {
        var url = "<c:url value="/mobile/cards.html"/>";
        window.location.href = url;
    })


    var canClick = function(){
        $("#btn.span").html("提现");
    }

    var noClick = function () {
        $("#btn.span").html("正在提交..");
    }

    //提交提现
    $("#btn").click(function () {

        var remark = prompt("如果需要备注,请填写");

        if( $("#btn span").html()=="正在提交..")return;
        noClick();
        var url = "<c:url value="/mobile/withdraw.json"/>";
        var amount = $(".amount").val();
        if(amount<=0){
            alert("请填写金额");
        }

        var isAdvance = false;

       if( $("#isAdvance").is(':checked')){
           isAdvance = true;
       }

        var cid = $(".cid").val();
        if(cid==null||cid=="")return;
        var param = {
            "cid":cid,
            "amount":amount,
            "remark":remark,
            "isAdvance":isAdvance
        }

        $.post(url,param,function (data) {
            if(data.flag==0){
                alert("提现申请成功");
                window.location.href = "<c:url value="/mobile/wallet.html"/>";
            }else {
                alert("提现申请失败"+data.message);
                canClick();
            }
        }).fail(function (xhr) {
            alert("未知错误,请联系管路员！");
        })
    })


</script>
</body>
</html>