<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/common/common.jsp"%>
<style>
    .footer {
        padding: 0 0.9em 0.9em;
        background: #e8e8e8;
    }

    .footer ul {
        overflow: hidden;
        overflow: auto;
        padding: 0.5em 0;
        border-bottom: #cdcdcd 1px solid;
    }

    .footer ul li:first-child {
        width: 25%;
        text-align: left;
    }

    .footer ul li {
        text-align: center;
        line-height: 2em;
        font-size: 1.17em;
        width: 25%;
        float: left;
    }

    .footer ul li:last-child {
        margin: 0;
        width: 25%;
        text-align: right;
    }

    .footer ul li a {
        color: #36C;
    }

    .footer p {
        padding: 0.5em 0;
        line-height: 1.5;
        text-align: center;
    }

</style>


<div class="footer">
    <ul>
        <li><a href="<c:url value='/dmz/mobile/index.html'/>">首页</a></li>
        <li><a href="<c:url value='/mobile/shopcart/index.html'/>">购物车</a></li>
        <li><a href="<c:url value='/mobile/contacts.html'/>">团队</a></li>
        <li><a href="<c:url value='/mobile/my.html'/>">个人中心</a></li>
        <%--<li><a href="<c:url value="/dmz/pmall/show.html"/>">优惠</a></li>--%>
    </ul>
    <p>© 2015-2017 高臣药业 版权所有，并保留所有权利。</p>
    <!--<div align="center">a</div>-->
</div>
