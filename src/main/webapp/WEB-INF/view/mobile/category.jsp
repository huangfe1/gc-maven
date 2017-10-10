<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/common/common.jsp"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="${ctx}/resources/mallcss/initcss.css" rel="stylesheet">
    <link href="${ctx}/resources/mallcss/common.css" rel="stylesheet">
    <style>

        header{
            height: 3.7em;
            background: #f03791;
            position: relative;
        }
        .headerLeft a,.headerRight a{
            width: 3.7em;
            height: 3.7em;
            position: absolute;
            top: 0;
        }
        .headerLeft a{
            left: 0;
        }
        .headerRight a{
            right: 0;
        }

        .headerLeft a span,.headerRight a span{
            background: url("${ctx}/resources/mallimages/png.png") no-repeat;
            background-size: 17em;
            width: 2em;
            height: 1.75em;
            position: absolute;
            top: 50%;
            left: 50%;
            margin: -0.89em 0 0 -1em;
        }

        .headerLeft a span{
            background-position: -0.17em -5.5em;
        }

        .headerRight a span{
            background-position: -0.17em -8.1em;
        }
        .Title{
            display: block;
            text-align: center;
            color: white;
            font-size: 1.3em;
            line-height: 2.87em;
        }

        .categorys{
            background: #f1f1f1;;

        }
        .category{
            overflow: hidden;
        }

        .category .title{

            overflow: hidden;
            padding: 0.4em;
            /*height:auto;*/
        }

        .category .title h3{
            float: left;
            font-size: 1.4em;
            line-height: 1.1em;
            font-weight: 800;
            color: #555;
            margin-left: 0.5em;

        }

        .category .title span{
            float: left;
            font-size: 2.1em;
            color: #ed2b87;
            line-height: 0.8em;
        }

        .category .imgs{
            border:solid  #d9d9d9;
            border-width: 1px 0;
            font-size: 1.1em;
            overflow: hidden;
            padding: 0.5em;
            background-color: white;
        }

        .category .imgs a{
            width: 25%;
            float: left;
            text-align: center;
        }
        .category .imgs a img{
            width: 5.34em;
            height: 5.34em;
        }

        .category .imgs a p{
            font-size: 1.1em;
            color: #333;
            line-height: 1.62;

        }




    </style>
    <title>分类</title>
</head>
<body>
<div class="con">
    <%--<header>--%>
        <%--<div class="headerLeft">--%>
            <%--<a href="javascript:history.go(-1)"><span></span></a>--%>
        <%--</div>--%>
        <%--<span class="Title">分类</span>--%>
        <%--<div class="headerRight">--%>
            <%--<a href="<c:url value="/dmz/vmall/index.html?ref=01"/> "><span></span></a>--%>
        <%--</div>--%>
    <%--</header>--%>


    <div class="categorys">
        <c:forEach items="${dtos}" var="dto">
            <div class="category">
                <div class="title"><span>|</span><h3>${dto.parentCategory.name}</h3></div>
                <div class="imgs">
                    <c:forEach items="${dto.goodsCategories}" var="category">
                        <a href="<c:url value="/dmz/goods/list.html?cid=${category.id}"/>"><img src="${dmzImgPath}${category.img}" alt=""><p>${category.name}</p></a>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>
    </div>

    <jsp:include page="footer.jsp"/>

</div>
</body>
</html>