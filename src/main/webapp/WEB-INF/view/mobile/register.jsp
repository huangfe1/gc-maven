<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/common/common.jsp" %>
<!doctype html>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="${ctx}/resources/mallcss/initcss.css" rel="stylesheet">
    <link href="${ctx}/resources/mallcss/common.css" rel="stylesheet">
    <style>

        .con {
            background-color: #e8e8e8;
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

        .login section {
            padding: 0.8em 0.8em;
        }

        .input {
            background: #ffffff;
            border-radius: 4px;
            border: #dadada 1px solid;
            -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .1) inset;
        }

        .name {
            padding: 0.3em 0.5em;
            /*overflow: hidden;*/
            position: relative;
            line-height: 2.3em;
            font-size: 1.1em;
            border-bottom: 1px solid #e3e3e3;
        }

        .name b {
            color: #85858d;
            text-align: center;
            font-size: 1.2em;
            line-height: 1.8em;
            position: absolute;
            left: 0.5em;
            top: 0.5em;
        }

        .name span {
            display: block;
            /*position: static;*/
            padding-left: 4em;

        }

        .name span input {
            border: none;
            width: 100%;
            padding: 0;
            height: 2.3em;
            font-size: 1.2em;
            outline: none;
        }

        .loginBtn {
            display: block;
            background: #f02387;
            width: 100%;
            border-radius: 1em;
            color: white;
            text-align: center;
            font-size: 1.5em;
            margin-top: 1.5em;
            padding: 0.7em 0;
            margin-bottom: 0.5em;
        }

        .hz {
            border-top: 1px white solid;
            text-align: center;
            padding: 1em;
            margin-top: 1em;
        }

        .hz h2 {
            margin-top: -1.3em;
        }

        .hz h2 span {
            background: #e8e8e8;
            color: #5e5e5e;
            font-size: 0.8em;
        }

        .hz p {
            padding: 1em;
            text-align: center;
        }

        .hz p img {
            margin-right: 0.3em;
            width: 2.1em;
        }

        /*.file {*/
        /*position: relative;*/
        /*display: inline-block;*/
        /*!*background: #D0EEFF;*!*/
        /*!*border: 1px solid #99D3F5;*!*/
        /*border-radius: 4px;*/
        /*padding: 4px 12px;*/
        /*overflow: hidden;*/
        /*color: #1E88C7;*/
        /*text-decoration: none;*/
        /*text-indent: 0;*/
        /*line-height: 20px;*/
        /*}*/
        .file input {
            /*position: absolute;*/
            /*font-size: 100px;*/
            /*right: 0;*/
            /*top: 0;*/
            opacity: 0;
        }

        /*.file:hover {*/
        /*background: #AADFFD;*/
        /*border-color: #78C3F3;*/
        /*color: #004974;*/
        /*text-decoration: none;*/
        /*}*/


    </style>
    <title>完善信息</title>
</head>
<body style="background: rgb(232, 232, 232);">
<div class="con">
    <%--<header>--%>
    <%--<a href="" class="Return"><span></span></a>--%>
    <%--<span class="Title">完善信息</span>--%>
    <%--&lt;%&ndash;<a href="" class="Home"><span></span></a>&ndash;%&gt;--%>
    <%--</header>--%>
    <form id="loginForm" method="post" action="<c:url value='/mobile/register.json' />">
        <div class="login">
            <section>
                <div class="input">
                    <div class="name">
                        <c:if test="${isF}">
                            <b>姓名：</b>
                        </c:if>
                        <c:if test="${isY}">
                            <b>店名：</b>
                        </c:if>
                        <span><input name="realName" type="text"></span>
                    </div>
                    <div class="name">
                        <b>手机：</b><span><input name="mobile" type="text"></span>
                    </div>
                    <div class="name">
                        <b>微信：</b><span><input name="weixin" type="text"></span>
                    </div>
                    <div class="name">
                        <b>住址：</b><span><input name="registerAddress" type="text"></span>
                    </div>

                    <c:if test="${isY}">
                        <div class="name file">
                            <b>证件：</b><span><input id="file"  name="img" type="file"></span>
                        </div>
                    </c:if>

                    <div class="name">
                        <b>密码：</b><span><input id="password" name="password" placeholder="" type="password"></span>
                    </div>
                    <div class="name">
                        <b>重复：</b><span><input id="rePaw" name="rePaw" placeholder="" type="password"></span>
                    </div>
                    <input type="hidden" value="${s_nickname}" name="nickName">
                    <input type="hidden" value="${s_headimgurl}" name="headimgurl">
                    <input type="hidden" value="${s_unionid}" name="wxUnionID">
                    <input type="hidden" value="${s_openid}" name="wxOpenid">
                    <input type="hidden" value="${refCode}" name="refCode">
                </div>
                <p style="color: red" id="error"></p>
                <p>
                    <label class="loginBtn" id="loginBtn">立即提交</label>
                </p>
                <%--<p><a style="color: blueviolet" href="<c:url value="/vmall/uc//dmz/login.html"/>" class="register">有账号,去登陆</a>--%>
                </p>
            </section>
        </div>
    </form>
    <div class="hz">
        <%--<h2><a href=""><span style="color:blueviolet;">我有账号,点我登录</span></a></h2>--%>
        <%--<p>--%>
        <%--<img src="${ctx}/resources/mallimages/qq.png" alt="">--%>
        <%--<img src="${ctx}/resources/mallimages/weibo.png" alt="">--%>
        <%--</p>--%>
    </div>
</div>

<jsp:include page="footer.jsp"/>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/jquery.form/4.2.1/jquery.form.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-validate/1.16.0/jquery.validate.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-validate/1.16.0/additional-methods.min.js"></script>
<script>
    $(function () {

        !function (w) {
            if (w.utils) return;
            var Utils = function () {
            }
            Utils.prototype = {
                Constructor: Utils,
                phoneValidate: function (e) {
                    var sr = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/g;
                    return this.mobilePhone(e) || sr.test(e);
                },
                mobilePhone: function (e) {
                    var mr = /^((\(\d{3}\))|(\d{3}\-))?1[3-8][0-9]\d{8}$/g;
                    return mr.test(e);
                },
                pawValidate: function (e) {
                    var paw = $("#password").val();
                    return e == paw;
                }
            }
            w["utils"] = new Utils();
        }(window);

        if (jQuery.validator) {
            jQuery.validator.addMethod("telephone", function (value, element) {
                return this.optional(element) || window.utils.phoneValidate(value);
            }, "请正确填写电话号码");
            jQuery.validator.addMethod("mobile", function (value, element) {
                return this.optional(element) || window.utils.mobilePhone(value);
            }, "请正确填写手机号码");
            jQuery.validator.addMethod("rePaw", function (value, element) {
                return this.optional(element) || window.utils.pawValidate(value);
            }, "两次密码不同");
        }

        var btn = null;
        $("#loginForm").validate(
            {
                submitHandler: function (form) {
                    $(form)
                        .ajaxSubmit(
                            {
                                beforeSubmit: function () {
                                    btn.html("loading");
                                },
                                success: function (responseText, statusText, xhr, $form) {//btn.button("reset");
                                    var m = $.parseJSON(xhr.responseText);
                                    if (m.flag == "0") {
                                        var l = xhr.getResponseHeader("Location");
                                        window.location = l;
                                    } else {
                                        alert(m.message);
                                        btn.html("提交注册");
                                    }

                                },
                                error: function (xhr, textStatus, errorThrown) {
                                    var m = $.parseJSON(xhr.responseText);
//                                        $("#alert").empty().html(m.message).removeClass("invisible");
//                                        btn.button("reset");
//                                        $(".captchaImg").click();
//                                        $("#accountName").focus();
                                    alert(m.message);
                                    btn.html("提交注册");
//                                        window.location.reload();
                                }
                            });
                },
                rules: {
                    realName: {
                        required: true
                    },
                    mobile: {
                        required: true,
                        mobile: true
                    },
                    weixin: {
                        required: true
                    },
                    password: {
                        required: true
                    },
                    rePaw: {
                        required: true,
                        rePaw: true
                    }
                },
                onkeyup: false,
                messages: {
                    realName: {
                        required: "请输入代理姓名"
                    },
                    mobile: {
                        required: "请输入手机号码",
                        mobile: "手机号码格式不正确"
                    },
                    weixin: {
                        required: "请输入微信号"
                    },
                    password: {
                        required: "请输入密码"
                    },
                    rePaw: {
                        required: "请输入密码",
                        rePaw: "两次密码不一样"
                    }
                },
                focusInvalid: true,
                errorClass: "text-danger",
                validClass: "valid",
                errorElement: "small",
                errorPlacement: function (error, element) {
//                    error.appendTo(element.closest(
//                        "div.row").children(
//                        "div.text-error"));
                    $("#error").html(error);

                }
            });
        $("#loginBtn").click(function () {
            btn = $(this);
            $("#loginForm").submit();
        });

        $("#file").change(function () {
            var index = $(this).val().lastIndexOf("\\") + 1;
            var val = $(this).val().substr(index, $(this).val().length);
            if(val.length>25){
                val = val.substr(val.length-20,val.length);
            }
            $(this).closest(".file").children("b").html("证件: " + val.trim());
        })

    })
</script>
</body>
</html>