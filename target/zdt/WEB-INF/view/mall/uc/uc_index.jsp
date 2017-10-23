<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/common/common.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="${keywords}">
    <meta http-equiv="description" content="">
    <%@include file="/WEB-INF/view/common/head_css.jsp" %>
    <%@include file="/WEB-INF/view/common/tc_css.jsp" %>
    <meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1;user-scalable=no;">
    <title>会员中心</title>
    <%--<script type="text/javascript">--%>
    <%--$(document).ready(function () {--%>
    <%--//            var attr = parseInt($(".member_m_pic_1").height());--%>
    <%--//            var attr3 = parseInt($(".member_m_z_1").height());--%>
    <%--//            var h = attr - attr3;--%>
    <%--//            var clientWidth = document.body.clientWidth;--%>
    <%--//            $(".member_mp_t_img img").css("width", clientWidth * 0.3);--%>
    <%--//            $(".member_mp_t_img img").css("height", clientWidth * 0.3);--%>

    <%--//            handleUserPic();--%>
    <%--});--%>

    <%--function handleUserPic() {--%>
    <%--var th = $(".member_m_pic").outerHeight(true);--%>
    <%--if (th < 60) {--%>
    <%--setTimeout("handleUserPic", 500);--%>
    <%--} else {--%>
    <%--$(".member_m_pic .img-circle").css("height", th * 0.9);--%>
    <%--$(".member_m_pic .img-circle").css("width", th * 0.9);--%>
    <%--}--%>
    <%--}--%>
    <%--</script>--%>
</head>
<body>
<div class="maincontainer" style="padding-bottom: 20px">
    <div class="container" style="max-width:768px;margin:0 auto;">
        <div class="row">
            <a href="#">
                <div class="member_top member_top_1">
                    <div class="member_top_bg"><img src="<c:url value="/resources/tcimages/member_bg.png"/> "></div>
                    <div class="member_m_pic member_m_pic_1">
                        <%--<img class="img-circle img-responsive" src="<c:url value="/resources/tcimages/user.jpg"/>">--%>
                        <img class="img-circle img-responsive" src="${user.headimgurl}">
                    </div>
                    <div class="member_m_z member_m_z_1" style="width: 100%">
                        <div class="member_m_x">昵称:${user.realName}</div>
                    </div>
                    <%--<div class="member_m_z member_m_z_1" style="top: 30%;" style="width: 100%">--%>
                    <%--<div class="member_m_x">推荐人:${user.parent==null?'请完善信息':user.parent.realName}</div>--%>
                    <%--</div>--%>
                    <%--<div class="member_m_r">账户管理、收货地址&gt;--%>
                    <%--</div>--%>
                </div>
            </a>
            <%--<div class="member_mp_img" data-toggle="modal" data-target="#myModalmin" data-title="我的名片" data-tpl="mp">--%>
            <div class="member_mp_img" data-title="我的名片" data-tpl="mp">
                <img src="<c:url value="/resources/tcimages/member_mp_img.png"/>"></div>
            <%--<div class="list-group mb10">--%>
            <%--<a href="/p/userOrder" class="list-group-item tip">--%>
            <%--<div class="list_group_img">--%>
            <%--<img src="<c:url value="/resources/tcimages/member_img16.png"/>"></div>--%>
            <%--特权商城订单--%>
            <%--<span class="gary pull-right">查看全部</span>--%>
            <%--</a>--%>
            <%--<div class="list-group-item p0 clearfix">--%>
            <%--<div class="col-xs-3 p0">--%>
            <%--<a class="order_tab_link" href="/p/userOrder?state_type=1">--%>
            <%--<em class="order_img">--%>
            <%--<img src="<c:url value="/resources/tcimages/order_bg_3.png"/>"></em>待付款--%>

            <%--</a>--%>
            <%--</div>--%>
            <%--<div class="col-xs-3 p0">--%>
            <%--<a class="order_tab_link" href="/p/userOrder?state_type=2">--%>
            <%--<em class="order_img">--%>
            <%--<img src="<c:url value="/resources/tcimages/order_bg_2.png"/>"></em>待发货--%>
            <%--</a>--%>
            <%--</div>--%>
            <%--<div class="col-xs-3 p0">--%>
            <%--<a class="order_tab_link" href="/p/userOrder?state_type=3">--%>
            <%--<em class="order_img">--%>
            <%--<img src="<c:url value="/resources/tcimages/order_bg_1.png"/>"></em>待收货--%>
            <%--</a>--%>
            <%--</div>--%>
            <%--<div class="col-xs-3 p0">--%>
            <%--<a class="order_tab_link" href="/p/userOrder?state_type=4">--%>
            <%--<em class="order_img">--%>
            <%--<img src="<c:url value="/resources/tcimages/order_bg.png"/>"></em>已完成--%>
            <%--</a>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>

            <div class="list-group mb10">
                <a href="<c:url value="/pm/order/myOrder.html"/>" class="list-group-item tip">
                    <div class="list_group_img">
                        <img src="<c:url value="/resources/tcimages/member_img16.png"/>"></div>
                    优惠商城订单
                    <span class="gary pull-right">查看全部</span>
                </a>
                <div class="list-group-item p0 clearfix">
                    <div class="col-xs-3 p0">
                        <a class="order_tab_link" href="#">
                            <em class="order_img">
                                <img src="<c:url value="/resources/tcimages/order_bg_3.png"/>"></em>待付款
                        </a>
                    </div>
                    <div class="col-xs-3 p0">
                        <a class="order_tab_link" href="#">
                            <em class="order_img">
                                <img src="<c:url value="/resources/tcimages/order_bg_2.png"/>"></em>待发货
                        </a>
                    </div>
                    <div class="col-xs-3 p0">
                        <a class="order_tab_link" href="#">
                            <em class="order_img">
                                <img src="<c:url value="/resources/tcimages/order_bg_1.png"/>"></em>待收货
                        </a>
                    </div>
                    <div class="col-xs-3 p0">
                        <a class="order_tab_link" href="#">
                            <em class="order_img">
                                <img src="<c:url value="/resources/tcimages/order_bg.png"/>"></em>已完成
                        </a>
                    </div>
                </div>
            </div>
            <div class="list-group mb10 member_list_group clearfix">
                <a href="#" class="list-group-item col-xs-3">
                    <div class="m_img"><img src="<c:url value="/resources/tcimages/order_bg_5.png"/>"></div>
                    <p class="m_name">我的收藏</p>
                    <span class="red">0</span>
                </a>
                <a href="#" class="list-group-item col-xs-3">
                    <div class="m_img"><img src="<c:url value="/resources/tcimages/order_bg_8.png"/>"></div>
                    <p class="m_name">我的评论</p>
                    <span class="red">&nbsp;&nbsp;</span>
                </a>
                <a href="#" class="list-group-item col-xs-3">
                    <div class="m_img"><img src="<c:url value="/resources/tcimages/order_bg_4.png"/>"></div>
                    <p class="m_name">收件箱</p>
                    <span class="red">&nbsp;&nbsp;</span>
                </a>

                <a href="#" class="list-group-item col-xs-3">
                    <div class="m_img"><img src="<c:url value="/resources/tcimages/order_bg_7.png"/>"></div>
                    <p class="m_name">系统消息</p>
                    <span class="red">&nbsp;&nbsp;</span>
                </a>

            </div>

            <div class="list-group mb10">
                <c:if test="${user.agentCode==null}">
                    <a href="#" id="wsxx" class="list-group-item tip">
                        <div class="list_group_img"><img src="<c:url value="/resources/tcimages/wsxx.png"/>"></div>
                        完善信息
                    </a>
                </c:if>
                <%--<c:if test="${user.agentCode!=null}">--%>
                    <%--<a href="#" id="editPaw" class="list-group-item tip">--%>
                        <%--<div class="list_group_img"><img src="<c:url value="/resources/tcimages/wsxx.png"/>"></div>--%>
                        <%--修改密码(默认密码888888或者身份证后六位)--%>
                    <%--</a>--%>
                <%--</c:if>--%>
                <!--我的账户-->
                <a href="<c:url value="/agent/myAuth.html?from=${user.id}"/>" class="list-group-item tip">
                    <div class="list_group_img"><img src="<c:url value="/resources/tcimages/account.png"/>"></div>
                    我的账户
                </a>
                <a href="<c:url value="/agent/children.html?from=${user.id}"/>" class="list-group-item tip">
                    <div class="list_group_img"><img src="<c:url value="/resources/tcimages/users.png"/>"></div>
                    我的客户
                </a>


                <a href="<c:url value="/voucher/my.html"/>" class="list-group-item tip">
                    <div class="list_group_img"><img src="<c:url value="/resources/tcimages/zdjq.png"/>"></div>
                    转让奖金
                </a>

                <a href="<c:url value="/voucher/record.html"/> " class="list-group-item tip">
                    <div class="list_group_img"><img src="<c:url value="/resources/tcimages/mx.png"/>"></div>
                    奖金详情
                </a>

                <a href="<c:url value="/purchase/my.html"/>" class="list-group-item tip">
                    <div class="list_group_img"><img src="<c:url value="/resources/tcimages/zdjq.png"/>"></div>
                    转让进货券
                </a>

                <a href="<c:url value="/purchase/record.html"/> " class="list-group-item tip">
                    <div class="list_group_img"><img src="<c:url value="/resources/tcimages/mx.png"/>"></div>
                    进货券详情
                </a>

                <a href="<c:url value="/transfer/my.html"/>" class="list-group-item tip">
                    <div class="list_group_img"><img src="<c:url value="/resources/tcimages/th.png"/>"></div>
                    转货退货
                </a>

                <%--<a href="<c:url value="/delivery/mobile.html?from=${user.id}'/>"/>" class="list-group-item tip">--%>
                <a href="<c:url value="/delivery/index.html"/> " class="list-group-item tip">
                    <div class="list_group_img"><img src="<c:url value="/resources/tcimages/fh.png"/>"></div>
                    我要发货
                </a>

                <a href="<c:url value="/transfer/records.html?from=${user.id}"/>" class="list-group-item tip">
                    <div class="list_group_img"><img src="<c:url value="/resources/tcimages/dd.png"/>"></div>
                    转货请求与记录
                </a>

                <a href="<c:url value="/delivery/agent/confirm.html"/>" class="list-group-item tip">
                    <div class="list_group_img"><img src="<c:url value="/resources/tcimages/dd.png"/>"></div>
                    客户发货请求
                </a>

                <a href="<c:url value="/securityCode/index.html"/>" class="list-group-item tip">
                    <div class="list_group_img"><img src="<c:url value="/resources/tcimages/fw.png"/>"></div>
                    防伪录入
                </a>

                <a href="<c:url value="/agent/logout"/>" class="list-group-item tip">
                    <div class="list_group_img"><img src="<c:url value="/resources/tcimages/tc.png"/>"></div>
                    退出系统
                </a>

                <a href="#" class="list-group-item tip">
                    <div class="list_group_img"><img src="<c:url value="/resources/tcimages/order_bg_10.png"/>"></div>
                    常见问题
                </a>
                <a href="#" class="list-group-item tip">
                    <div class="list_group_img"><img src="<c:url value="/resources/tcimages/order_bg_9.png"/>"></div>
                    意见反馈
                </a>
            </div>

        </div>
    </div>
    <div class="modal fade" id="myModalmin" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form class="form-horizontal" role="form" data-method="formAjax">
                    <div class="modal-header member_tc_top">
                        <button type="button" class="close member_tc_xx" data-dismiss="modal" aria-hidden="true">×
                        </button>
                        <h4 class="modal-title" id="myModalLabel">&nbsp;&nbsp;</h4>
                    </div>
                    <div style="overflow:hidden;width: 100%;padding-top: 20px;">
                        <div style="">
                            <div class="member_mp_t_img">
                                <%--<img src="<c:url value="/resources/tcimages/user.jpg"/>">--%>
                                <img src="<c:url value="${user.headimgurl}"/>">
                            </div>
                            <div class="member_mp_t_m">${user.realName}&nbsp;&nbsp;${user.agentCode}</div>
                            <div class="member_mp_t_m_m">
                                <img src="<c:url value="/dmz/agent/domain.html?id=${user.id}"/>">
                            </div>
                            <div class="member_mp_t_tit">用微信扫一扫，让更多的人用上优惠的好产品！</div>
                        </div>
                    </div>
                    <div style="height:60px;"></div>
                </form>
            </div>
        </div>
    </div>
    <jsp:include page="/WEB-INF/view/common/registerModal.jsp"/>
    <jsp:include page="/WEB-INF/view/common/footer.jsp"/>
</div>
<%@include file="/WEB-INF/view/common/head.jsp" %>
<%@include file="/WEB-INF/view/common/form.jsp" %>
<%@include file="/WEB-INF/view/common/script_common.jsp" %>
<%@include file="/WEB-INF/view/common/registerModal_js.jsp" %>

<%--<%@include file="/WEB-INF/view/common/script_tc.jsp" %>--%>
<script type="text/javascript">
    $(function () {
        $(".member_mp_img").click(function () {
            <c:if test="${user.agentCode!=null}">
            $("#myModalmin").modal();
            </c:if>
            <c:if test="${user.agentCode==null}">
            $("#registerModal").modal().css(
                {"margin-top": "10px"}
            );
            </c:if>

        });
        $("#wsxx").click(function () {
            $("#registerModal").modal().css(
                {"margin-top": "10px"}
            );
        })
//        $(".member_mp_img").click(function () {
//            $("#myModalmin").modal();
//        });

    })
</script>
</body>
</html>
