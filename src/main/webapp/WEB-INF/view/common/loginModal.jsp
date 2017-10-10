<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--登录modal-->
<div class="modal fade" id="loginModal" style="overflow-x:hidden;overflow-y:auto;" tabindex="-1" role="dialog"
	 aria-labelledby="editModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="editModalLabel">请登陆</h4>
			</div>
			<div class="modal-body" id="messageBody">
                <form  id="loginForm"  name="theForm" class="form-signin"  action="<c:url value="/agent/login.json"/>" method="POST" >
                    <input type="hidden" id="returnUrl" name="returnUrl" value="http://m.legendshop.cn/p/orderDetails"/>

                    <input name="accountName"   id="accountName" type="text" style="margin-bottom: -2px;-webkit-appearance:none; " class="form-control" placeholder="帐户名/手机号码"  required="true" autofocus=""  tabindex="1" >
                    <br>
                    <input id="pwd" name="password" type="password" class="form-control" placeholder="请输入密码"  required="true" style="-webkit-appearance:none;" autocomplete="off"  tabindex="2" >
                    <label class="checkbox">
                        <input checked  name="_spring_security_remember_me" type="checkbox"   tabindex="4" /> 记住我
                        <a  id="reme" href="#" style="float:right">忘记密码</a>
                    </label>
                    <div class="clear"></div>

                </form>
            </div>
			<div class="modal-footer">
                <div  class=" col-md-6 col-md-offset-3  col-xs-12 "><button id="loginBtn" data-loading-text="正在登录系统......" type="button" class="btn btn-info btn-block"  tabindex="5" >登  陆</button></div>
                <%--<div class="col-xs-5 p0" style="margin-left:10px;"><button type="button" class="btn btn-default btn-block" onclick="window.location.href='/register'"  tabindex="6" >注 册</button></div>--%>
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>--%>
                <%--<button type="button" class="btn btn-primary">登录</button>--%>
            </div>
		</div>
	</div>
</div>
