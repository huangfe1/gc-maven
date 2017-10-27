<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/common/common.jsp"%>
<style>
    .footer {
        position: fixed;
        bottom: 0;
        background-color: #ffffff;
        width: 100%;
        max-width: 35.5em;
        overflow: hidden;
        height: 4em;
    }

    .footer a {
        display: inline-block;
        width: 25%;
        float: left;
        text-align: center;
        padding: 0.5em 0 0.3em;
        cursor: pointer;
    }



    .footer a span{

        display: inline-block;
        width: 1.8em;
        height: 1.8em;
        padding: 0;
        margin: 0;
    }
    .footer a p{
        font-size: 1em;
        margin-top: 0.3em;
        color: #a5a5a5;
    }

    .footer a .xx{

        background: url("${ctx}/resources/mallimages/xx.png") no-repeat;
        background-size: 1.8em;
    }

    .footer a .txl{
        background: url("${ctx}/resources/mallimages/txl.png") no-repeat;
        background-size: 1.8em;
    }

    .footer a .fx{
        background: url("${ctx}/resources/mallimages/dbfx.png") no-repeat;
        background-size: 1.8em;
    }

    .footer a .me{
        background: url("${ctx}/resources/mallimages/me.png") no-repeat;
        background-size: 1.8em;
    }

</style>


<div class="footer">
    <a class="item" >
            <span class="xx" >&nbsp;</span>
            <p>消息</p>

    </a>
    <a class="item" href="<c:url value="/mobile/contacts.html"/>">
    <%--<a class="item" href="<c:url value="#"/>">--%>
            <span class="txl" >&nbsp;</span>
            <p>团队</p>
    </a>
    <%--<a class="item" href="<c:url value="/mobile/find.html"/>">--%>
    <a class="item" href="<c:url value="#"/>">
            <span class="fx" >&nbsp;</span>
            <p>发现</p>
    </a>
    <a class="item" href="<c:url value='/mobile/my.html'/>">
            <span class="me" >&nbsp;</span>
            <p>我</p>
    </a>
</div>
