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

        span {
            font-family: "Microsoft JhengHei";
        }

        .con {
            position: relative;
            background-color: #EDEEED;
        }

        body {
            background-color: #EDEEED;
        }

        h4 {
            color: #A6A7A6;
            margin-top: 1.5em;
            margin-left: 2em;
            margin-bottom: 0.5em;
            font-size: 1em;
            font-family: "Heiti SC";
        }

        ul {
            background-color: #FEFFFE;
            padding: 0 1em;
            font-size: 1.2em;
        }

        li {
            padding: 0.7em 0;
            border-bottom: 1px solid #EDEEED;
        }

        li span {
            /*float: left;*/
        }

        li input {
            margin-left: 2em;
            /*padding-left: 4e;*/
            font-size: 1em;
            outline: none;

            opacity: 0.5;
            font-family: "Microsoft Sans Serif";
        }

        .btndiv {
            width: 100%;
            text-align: center;
            margin-top: 1em;
            /*padding: 0.8em;*/
        }

        .btn {
            padding: 0.8em;
        }

        button {
            width: 90%;
        }


    </style>
    <title>添加银行卡</title>
</head>
<body>
<div class="con">
    <h4>请绑定本人的银行卡!</h4>

    <div class="cardInfo">
        <ul>
            <li>
                <span>真名</span><input name="name" class="name" readonly type="text" placeholder="黄飞">
            </li>

            <li>
                <span>银行</span><input autofocus name="bank" class="bank" type="text" placeholder="开户银行">
            </li>

            <li>
                <span>支行</span><input type="branch" class="branch" placeholder="开户支行">
            </li>

            <li>
                <span>卡号</span><input type="number" class="number" placeholder="银行卡号">
            </li>
        </ul>
    </div>

    <div class="btndiv">
        <button class="btn btn-danger" data-loading-text="正在提交....">确定绑定</button>
    </div>


</div>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>

    $(function () {

        var can = true;

        var canClick = function () {
            $(".btn").button("reset")
            can = true;
        }

        var noClick = function () {
            $(".btn").button("loading")
            can = false;
        }

        $(".btn").click(function () {
            if (!can) return;
            var bank = $(".bank").val();
            var number = $(".number").val();
            var branch = $(".branch").val();
            if(bank==null||bank==""){
                alert("银行不能为空");
                return;
            }
            if(number==null||number==""){
                alert("卡号不能为空");
                return;
            }
            if(branch==null||branch==""){
                alert("分行不能为空");
                return;
            }
            noClick();
            var url = "<c:url value="/mobile/addCard.json"/>";
            var param = {
                "bank":bank,
                "number":number,
                "branch":branch
            }
            $.post(url,param,function (data) {
                if(data.flag==0){
                    alert("绑定成功");
                    window.location.href="<c:url value="/mobile/cards.html"/>";
                }else {
                    alert("绑定失败"+data.message);
                    canClick();
                }
            }).fail( function (xhr, textStatus, errorThrown) {
                alert("未知错误,请联系管理员");
                canClick();
            })

        })
    })


</script>
</body>
</html>