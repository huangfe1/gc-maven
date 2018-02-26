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
    <style rel="stylesheet">
        header {
            width: 100%;
            height: 3.7em;
            background: #f03791;
            border-bottom: 1px solid #ddd;
        }

        header.fixed {
            max-width: 640px;
            position: fixed;
            /*left: 0;*/
            top: 0;
            z-index: 99;
        }

        header .Title {
            color: white;
            font-size: 1.3em;
            line-height: 2.7em;
        }

        .header {
            /*margin:0 20px;*/
            text-align: center;
            color: #4e4a49;
            font-size: 1em;
            height: 45px;
            line-height: 45px;
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

        #letter {
            width: 100px;
            height: 100px;
            border-radius: 5px;
            font-size: 75px;
            color: #555;
            text-align: center;
            line-height: 100px;
            background: rgba(145, 145, 145, 0.6);
            position: fixed;
            left: 50%;
            top: 50%;
            margin: -50px 0px 0px -50px;
            z-index: 99;
            display: none;
        }

        #letter img {
            width: 50px;
            height: 50px;
            float: left;
            margin: 25px 0px 0px 25px;
        }

        .sort_box {
            width: 100%;
            /*padding-top: 1em;*/
            overflow: hidden;
            padding-bottom: 4em;
        }

        .sort_list {
            padding: 0.5em 0 0.5em 5em;
            position: relative;
            /*height: 40px;*/
            /*line-height: 40px;*/
            border-bottom: 1px solid #ddd;
            cursor: pointer;
        }

        .sort_list .num_logo {

            /*border-radius: 10px;*/
            overflow: hidden;
            position: absolute;

            top: 0.5em;
            left: 1em;
            /*background-color: #2782D2;*/
            width: 3em;
        }

        .sort_list .num_logo img {
            width: 100%;
            /*height: 50px;*/
        }

        .sort_list .num_name {
            /*color: #000;*/
            line-height: 2.5em;
            font-size: 1.2em;
        }

        .sort_letter {
            /*height: 30px;*/
            /*line-height: 30px;*/
            /*padding-left: 1em;*/
            /*color: #787878;*/
            /*font-size: 14px;*/
            /*border-bottom: 1px solid #ddd;*/
            padding: 0.4em 0 0.4em 1em;
            background-color: #EAEBEA;
            color: #a5a5a5;
            font-size: 0.9em;
        }

        .initials {
            position: fixed;
            top: 2em;
            right: 0px;
            height: 100%;
            width: 15px;
            padding-right: 10px;
            text-align: center;
            font-size: 12px;
            z-index: 99;
            background: rgba(145, 145, 145, 0);
        }

        .initials li img {
            width: 14px;
        }

        .company ul li {
            margin-top: -1px;
            border-top: 1px solid #ddd;;
            position: relative;
            overflow: hidden;
            /*height: 4em;*/
            padding: 0.5em 0 0.5em 5em;
            cursor: pointer;
        }

        .company ul li .img {
            position: absolute;
            top: 0.5em;
            left: 1em;
            /*background-color: #2782D2;*/
            width: 3em;
            /*padding: 0.5em;*/
        }

        .company ul li .img.sq {
            /*background-color: #43BF18;*/
        }

        .company ul li .img img {
            display: inline-block;
            width: 100%;
        }

        .company ul li span {
            line-height: 2.5em;
            font-size: 1.2em;
        }

        div.Title {
            padding: 0.4em 0 0.4em 1em;
            background-color: #EAEBEA;
            color: #a5a5a5;
            font-size: 0.9em;
        }

        .name input {
            font-size: 1.2em;
            height: 2.2em;
            width: 8em;
            outline: none;
            background-color: #F6F7F6;
            border: none;

            box-shadow: none;
            -webkit-box-shadow: none;
            -moz-box-shadow: none;
            text-shadow: none;
        }

    </style>
    <title>通讯录</title>
</head>
<body>

<%--<header class="fixed">--%>
<%--<div class="header">--%>
<%--<a href="" class="Return"><span></span></a>--%>
<%--<span class="Title">通讯录</span>--%>
<%--<a href="" class="Home"><span></span></a>--%>
<%--</div>--%>
<%--</header>--%>


<div id="letter"></div>
<div class="sort_box">

    <%--<div class="company">--%>
        <%--<ul>--%>
            <%--<li>--%>
                <%--<div class="img sq"><img src="${ctx}/resources/mallimages/sq.jpeg" alt=""></div>--%>
                <%--<div class="name"><span>授权客服</span></div>--%>
            <%--</li>--%>
            <%--<li>--%>
                <%--<div class="img wl"><img src="${ctx}/resources/mallimages/wl.jpeg" alt=""></div>--%>
                <%--<div class="name"><span>物流客服</span></div>--%>
            <%--</li>--%>
            <%--<li>--%>
                <%--<div class="img wl"><img src="${ctx}/resources/mallimages/cw.jpeg" alt=""></div>--%>
                <%--<div class="name"><span>专用客服</span></div>--%>
            <%--</li>--%>
        <%--</ul>--%>
    <%--</div>--%>

    <%--<div class="Title">相关账号</div>--%>

    <%--<div class="company">--%>
        <%--<ul>--%>

            <%--<c:if test="${isVip}">--%>
                <%--<li data-uid="${mutedUserId}">--%>
                    <%--<div class="img sq"><img src="${ctx}/resources/mallimages/gs.jpg" alt=""></div>--%>
                    <%--<div class="name"><span>公司账号</span></div>--%>
                <%--</li>--%>
            <%--</c:if>--%>
            <%--<c:if test="${!isVip}">--%>
                <%--<li data-uid="${user.parent.id}">--%>
                    <%--<div class="img sq"><img src="${user.parent.headimgurl}" alt=""></div>--%>
                    <%--<div class="name"><span>业务咨询</span></div>--%>
                <%--</li>--%>
            <%--</c:if>--%>
            <%--<li data-uid="${user.id}">--%>
                <%--<div class="img sq"><img src="${user.headimgurl}" alt=""></div>--%>
                <%--<div class="name"><span>自己账号</span></div>--%>
            <%--</li>--%>
        <%--</ul>--%>
    <%--</div>--%>

    <%--<div class="Title">搜索账号</div>--%>

    <%--<div class="company search">--%>
        <%--<ul>--%>
            <%--<li>--%>
                <%--<div class="img sq"><img src="<c:url value="/resources/images/newFr.png"/>" alt=""></div>--%>
                <%--<div class="name"><span>搜索代理</span></div>--%>
                <%--<div class="name sn" style="display: none"><input placeholder="编号/手机号" type="text"></div>--%>
            <%--</li>--%>
        <%--</ul>--%>
    <%--</div>--%>


    <c:forEach items="${agents}" var="agent">
    <div class="sort_list" data-id="${agent.id}">
        <div class="num_logo">
            <img src="${agent.headimgurl}" alt="">
        </div>
        <div class="num_name">
                ${agent.realName}&nbsp;${agent.agentCode}
        <c:if test="${agent.agentStatus.desc eq '未激活'}">
            <span style="color: red">(未激活)</span>
        </c:if>
        </div>
    </div>
    </c:forEach>

    <div class="initials">
        <ul>
            <li><img src="${ctx}/resources/mallimages/068.png"></li>
        </ul>
    </div>
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/malljs/jquery.charfirst.pinyin.js"></script>
    <script type="text/javascript" src="${ctx}/resources/malljs/sort.js"></script>

    <jsp:include page="/WEB-INF/view/mobile/appfooter.jsp"/>
    <script type="text/javascript">
        $(function () {
            //验证手机号
            function validate(mobile) {
                var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
                if (!myreg.test(mobile)) {
                    return false;
                }
                return true;
            }

            //点击自己
            $(".company ul li").click(function () {
                var uid = $(this).attr("data-uid");
                if (isNaN(uid)) {
                    return;
                }
                var url = "<c:url value='/mobile/profile.html?uid='/>" + uid;
                window.location.href = url;
            });

            $(".sort_list").click(function () {
                var uid = $(this).attr("data-id");
                var url = "<c:url value='/mobile/profile.html?uid='/>" + uid;
                window.location.href = url;
            });

            //隐藏搜索名
            function hideName() {
                $(".search .name").hide();//搜索名字隐藏
                $(".search .sn").show();//搜索框展示
                $(".search input").focus();
            }

            //显示搜索名
            function showName(id,img,name,code) {
                $(".search input").val("");//重置搜索框

                //重写搜索名
                $(".search li").attr("data-id",id);
                $(".search img").attr("src",img);
                $(".search span").html(name+"  "+code);
                //显示
                $(".search .name").show();
                $(".search .sn").hide();//隐藏
            }

            $(".search li").click(function () {
                var uid = $(this).attr("data-id");
                if(isNaN(uid)){
                    hideName();
                }else {
                    var url = "<c:url value='/mobile/profile.html?uid='/>" + uid;
                    window.location.href = url;
                }
            });


            $(".search input").keyup(function () {

                var val = $(this).val();
                if (val.indexOf("zdt") > -1 && val.length == 9) {//编号
                    $(this).attr("readonly", "readonly");//只读状态
                    findAgent(val);
                } else if (validate(val)) {//如果是手机号
                    $(this).attr("readonly", "readonly");//只读状态
                    findAgent(val);
                }
            });

            //查找代理
            function findAgent(str) {
                var url = "<c:url value="/mobile/findAgent.json"/>";
                var param = {
                    "info": str
                };
                $.post(url, param, function (agent, status, jqXHR) {
                    if(isNaN(agent.id)){
                        alert("查询失败");
                        window.location.reload();
                    }
                    //成功
                    showName(agent.id,agent.headImgUrl,agent.name,agent.agentCode);
                }).fail(function (xhr) {
                    alert("查询失败！");
                    window.location.reload();
                });
            }


        });
    </script>
</body>
</html>
</html>
