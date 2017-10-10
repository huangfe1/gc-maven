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

        .panel {
            margin: 1.5em 1em 0;
            text-align: center;
            border: 1px solid;
            -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .1);
        }

        .panel .userInfo {
            background-color: #FAFBFA;
        }

        .panel .userInfo img {
            padding: 1.5em 0 0.5em 0;
            width: 4em;
        }

        .panel .userInfo h3 {
            color: #a5a5a5;
            font-size: 0.9em;
            padding: 0.5em 0 1em;
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

        .panel .transfer .inputs i {
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
            color: #65687C;
            margin: 1em 0 1.5em;
            display: inline-block;
            font-size: 1.1em;
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
    <title>${type.stateInfo}转账</title>
</head>
<body>
<div class="con">
    <%--<header>--%>
    <%--<a href="" class="Return"><span></span></a>--%>
    <%--<span class="Title">${type.stateInfo}转账</span>--%>
    <%--<a href="" class="Home"><span></span></a>--%>
    <%--</header>--%>

    <form method="post" action="<c:url value="/mobile/accounts/transfer.json"/>">
        <div class="panel">
            <div class="userInfo">
                <img src="${toAgent.headimgurl}" alt="">
                <h3>${toAgent.realName}</h3>
            </div>
            <div class="transfer">
                <h3>转账金额</h3>
                <div class="inputs">
                    <i class="fa fa-rmb" aria-hridden="true"></i>
                    <input type="hidden" value="${toAgent.id}" name="uid">
                    <input type="hidden" value="${type.state}" name="state">
                    <input type="hidden" value="" name="remark" id="remark">
                    <input name="amount" id="amount" type="text" value="">
                </div>
                <a href="">添加转账备注</a>
                <div class="btn"><span>转账</span></div>
            </div>
        </div>
    </form>


</div>
</div>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/jquery.form/4.2.1/jquery.form.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-validate/1.16.0/jquery.validate.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-validate/1.16.0/additional-methods.min.js"></script>
<script>
    $(function () {
        $(".transfer a").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            var remark = prompt("填写备注！");
            $("#remark").val(remark);
            $(this).html(remark);
        })
    })
    var canClick = true;

    function changeCanClick() {

        if ($(".btn span").html() == "转账") {
            $(".btn span").html("正在提交..");
        } else {
            $(".btn span").html("转账");
        }
        canClick = !canClick;
    }

    $("form").validate({
            submitHandler: function (form) {
                $(form).ajaxSubmit({
                    beforeSubmit: function () {
                        changeCanClick();
                    },
                    success: function (responseText, statusText, xhr, $form) {
                        var m = $.parseJSON(xhr.responseText);
                        if (m.flag == "0") {
                            alert("转账成功!");
                            var l = "<c:url value='/mobile/accounts/records.html?stateType='/>"+${type.state};
                            window.location = l;
                        } else {
                            alert(m.message);
                        }
                        changeCanClick();
                    },
                    error: function (xhr, textStatus, errorThrown) {
//                        var m = $.parseJSON(xhr.responseText);
                        console.log(xhr);
                        alert("未知错误，请联系管理员");
                        changeCanClick();
                    }
                })
            },
            rules: {
                amount: {
                    required: true

                }
            },
            onkeyup: false,
            message: {
                amount: {
                    required: "请填写金额"
                }
            }
        }
    )

    $(".btn").click(function () {
        if (!canClick)return;
        $("form").submit();
    })


    //验证金额
    $("#amount").keyup(function () {
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

    //    $("#amount").on("keyup", function(){
    //        var valid = /^\d{0,8}(\.\d{0,2})?$/.test(this.value),
    //            val = this.value;
    //
    //        if(!valid){
    //            console.log("Invalid input!");
    //            this.value = val.substring(0, val.length - 1);
    //        }
    //    });


</script>

</body>
</html>