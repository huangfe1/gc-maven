<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--登录modal-->
<div class="modal fade" id="registerModal" style="overflow-x:hidden;overflow-y:auto;" tabindex="-1" role="dialog"
     aria-labelledby="editModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="editModalLabel">请完善信息</h4>
            </div>
            <div class="modal-body" id="messageBody">
                <form id="registerForm" name="theForm" class="form-signin" action="<c:url value="/agent/completeInfo.json"/>"
                      method="POST">
                    <label>您之前有高臣药业的授权编号吗？</label>
                    <div class="radio" >
                        <label>
                            <input type="radio" checked name="hasCode" id="noCode" value="false">没有 - 我是新客户
                        </label>
                    </div>
                    <div class="radio">
                        <label>
                            <input type="radio" name="hasCode"  id="hasCode" value="true">有 - 我要绑定之前的编号
                        </label>
                    </div>


                    <div id="codeDiv" style="display: none;padding-top: 10px">
                        <input name="agentCode" id="accountName" type="text"
                               style="margin-bottom: -2px;-webkit-appearance:none; " class="form-control"
                               placeholder="用户编号" required="true" autofocus="" tabindex="1">
                        <br>
                        <input id="pwd" name="password" type="password" class="form-control" placeholder="请输入密码"
                               required="true" style="-webkit-appearance:none;" autocomplete="off" tabindex="2">
                    </div>
                    <div id="noCodeDiv" style="padding-top: 10px" >
                        <c:if test="${user.parent==null}">
                            <input type="text" class="form-control" name="parent.agentCode" placeholder="广告源编号">
                            <br>
                        </c:if>
                        <input type="text" class="form-control" name="realName" placeholder="真实姓名">
                        <br>
                        <input type="text" class="form-control" name="mobile" placeholder="手机号码">
                        <br>
                        <input type="text" class="form-control" name="weixin" placeholder="微信号">
                        <%--<br>--%>
                        <%--<input type="text" class="form-control" name="idCard" placeholder="身份证号码">--%>
                    </div>
                    <%--<label class="checkbox">--%>
                        <%--<input checked name="_spring_security_remember_me" type="checkbox" tabindex="4"/> 记住我--%>
                        <%--<a id="reme" href="#" style="float:right">忘记密码</a>--%>
                    <%--</label>--%>

                    <div class="clear"></div>
                </form>
            </div>
            <div class="modal-footer">
                <div class=" col-md-6 col-md-offset-3  col-xs-12 ">
                    <button id="loginBtn" data-loading-text="正在登录系统......" type="button" class="btn btn-info btn-block"
                            tabindex="5">确定提交
                    </button>
                </div>
                <%--<div class="col-xs-5 p0" style="margin-left:10px;"><button type="button" class="btn btn-default btn-block" onclick="window.location.href='/register'"  tabindex="6" >注 册</button></div>--%>
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>--%>
                <%--<button type="button" class="btn btn-primary">登录</button>--%>
            </div>
        </div>
    </div>
</div>
