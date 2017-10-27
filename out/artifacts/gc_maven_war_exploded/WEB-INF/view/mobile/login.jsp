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

		.con{
			background-color: #e8e8e8;
		}

		header{
			background: #f03791;
			height: 3.7em;
			text-align: center;
			position: relative;
		}

		.Return , .Home{
			position: absolute;
			width: 3.7em;
			height: 3.7em;
		}

		.Return{
			left: 0;
		}
		.Home{
			right: 0;
		}

		.Return span, .Home span{
			background: url("${ctx}/resources/mallimages/png.png");
			background-size: 17em;
			position: absolute;
			width: 1.7em;
			height: 1.7em;
		}
		.Return span{
			background-position:  -0.17em -5.5em;
			left: 1em;
			top: 1em;
		}

		.Home span{
			background-position:  -0.17em -8.1em;
			left: 1em;
			top: 1em;
		}
		.Title{
			color: white;
			font-size: 1.3em;
			line-height: 2.7em;
		}

		.login section{
			padding: 0.8em 0.8em;
		}

		.input{
			background: #ffffff;
			border-radius: 4px;
			border: #dadada 1px solid;
			-webkit-box-shadow: 0 1px 2px rgba(0,0,0,.1)inset;
		}
		.name{
			padding: 0.3em 0.5em;
			/*overflow: hidden;*/
			position: relative;
			line-height: 2.3em;
			font-size: 1.1em;
			border-bottom: 1px solid #e3e3e3;
		}
		.name b{
			color: #85858d;
			text-align: center;
			font-size: 1.2em;
			line-height: 1.8em;
			position: absolute;
			left: 0.5em;
			top: 0.5em;
		}
		.name span{
			display: block;
			/*position: static;*/
			padding-left: 4em;

		}

		.name span input{
			border: none;
			width: 100%;
			padding: 0;
			height: 2.3em;
			font-size: 1.2em;
			outline: none;
		}

		.loginBtn{
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
            cursor: pointer;
		}
		.hz{
			border-top: 1px white solid;
			text-align: center;
			padding: 1em;
			margin-top: 1em;
		}

		.hz h2{
			margin-top: -1.3em;
		}

		.hz h2 span{
			background: #e8e8e8;
			color: red;
			font-size: 1em;
		}
		.hz p{
			padding: 1em;
			text-align: center;
		}

		.hz p img{
			margin-right: 0.3em;
			width: 2.1em;
		}








	</style>
	<title>用户登录</title>
</head>
<body style="background: rgb(232, 232, 232);">
<div class="con">
	<%--<header>--%>
		<%--<a href="" class="Return"><span></span></a>--%>
		<%--<span class="Title">登录</span>--%>
		<%--<a href="" class="Home"><span></span></a>--%>
	<%--</header>--%>
	<form id="loginForm" method="post" action="<c:url value="/mobile/login.json"/>">
		<div class="login">
			<section>
				<div class="input">
					<div class="name">
						<b>账号：</b><span><input name="accountName" placeholder="手机号码/编号" type="text"></span>
					</div>
					<div class="name">
						<b>密码：</b><span><input name="password" placeholder="密码" type="password"></span>
					</div>
					<!--绑定用户需要的信息-->
					<%--<input type="hidden" value="${s_openid}" name="s_openid">--%>
					<%--<input type="hidden" value="${s_unionid}" name="s_unionid">--%>
				</div>
				<p><label class="loginBtn"  id="loginBtn">立即登录</label></p>
				<%--<c:if test="${refCode!=null}">--%>
					<%--<p ><a style="color: blueviolet" href="<c:url value="/mobile/register.html"/>" class="register">免费注册</a> </p>--%>
				<%--</c:if>--%>
			</section>
		</div>
	</form>
	<c:if test="${refCode!=null}">
	<div class="hz">
		<h2><span>新用户免注册一键登录</span></h2>
		<p>
			<img src="${ctx}/resources/mallimages/wx.png" alt="">
			<%--<img src="${ctx}/resources/mallimages/weibo.png" alt="">--%>
		</p>
	</div>
	</c:if>
</div>

<jsp:include page="footer.jsp"/>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/jquery.form/4.2.1/jquery.form.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-validate/1.16.0/jquery.validate.min.js"></script>
<script>

    //直接注册，无需点击
    <c:if test="${refCode!=null}">
    var url ="<c:url value="/dmz/mobile/oAuthLogin.html"/>";
    window.location.href=url;
    </c:if>

    $(function () {


        $(".hz").click(function () {
			var url ="<c:url value="/dmz/mobile/oAuthLogin.html"/>";
			window.location.href=url;
        });


        var btn = null;
        $("#loginForm")
            .validate(
                {
                    submitHandler: function (form) {
                        $(form)
                            .ajaxSubmit(
                                {
                                    beforeSubmit: function () {
                                        btn.html("loading");
                                    },
                                    success: function (responseText, statusText, xhr, $form) {
                                        var m = $.parseJSON(xhr.responseText);
                                        if(m.flag==0){
                                            var l = xhr.getResponseHeader("Location");
                                            window.location = l;
										}else {
                                            alert(m.message);
//                                            window.location.reload();
										}
                                    },
                                    error: function (xhr,textStatus,errorThrown) {
                                     alert("未知错误请联系管理员textStatus"+textStatus);
                                    }
                                });
                    },
                    rules: {
                        accountName: {
                            required: true
                        },
                        password: {
                            required: true
                        },
                        captcha: {
                            required: true
                        }
                    },
                    onkeyup: false,
                    messages: {
                        accountName: {
                            required: "帐户名必须填写"
                        },
                        password: {
                            required: "登录密码必须填写"
                        },
                        captcha: {
                            required: "请输入左侧验证码"
                        }
                    },
                    focusInvalid: true,
                    errorClass: "text-danger",
                    validClass: "valid",
                    errorElement: "small",
                    errorPlacement: function (error, element) {
                        error.appendTo(element.closest(
                            "div.row").children(
                            "div.text-error"));
                    }
                });
        $("#loginBtn").click(function () {
            btn = $(this);
            $("#loginForm").submit();
        });
    })
</script>
</body>
</html>