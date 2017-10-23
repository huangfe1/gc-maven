<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/view/common/common.jsp" %>
<!doctype html>
<html lang="en" xmlns:v-on="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/resources/mallcss/initcss.css" rel="stylesheet">
    <link href="${ctx}/resources/mallcss/common.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/scrollup/2.4.1/jquery.scrollUp.js"></script>
    <title>详情页</title>
    <style rel="stylesheet">

        .con {
            background: #ffffff;
            overflow: hidden;
        }

        header {
            background-color: #f03791;
            height: 3.7em;
            position: relative;
        }

        .headerLeft {
            /*position: absolute;*/
            /*top: 0;*/
            /*left: 0;*/
        }

        .headerLeft a {

        }

        .headerLeft a span {
            background: url("${ctx}/resources/mallimages/png.png") -0.17em -5.5em;
            background-size: 17em;
            position: absolute;
            width: 1.7em;
            height: 1.7em;
            top: 1em;
            left: 0.8em;
        }

        header .Title h3{
            font-size: 1.2em;
            color: white;
            text-align: center;
            line-height: 3em;
        }

        #focus a img {
            width: 100%;
            height: auto;
        }

        .info {
            padding: 2em 0.6em 0;
        }

        .info .Title {
            overflow: hidden;
        }

        .info .Title h4 {
            width: 80%;
            font-size: 1.2em;
            font-weight: 800;
            float: left;
            line-height: 1.25em;
            text-indent: 0;
        }

        .info .Title span {
            float: right;
            text-align: center;
            font-size: 1.2em;
            color: #f03791;
            border-left: 1px solid #e3e3e3;
            padding-left: 0.3em;
        }

        .info .Title i {
            width: 100%;
        }

        .detail {
            padding-top: 1em;
        }
        .detail p{
            padding: 0.5em 0;
        }

        .detail p span {
            width: 60%;


        }

        .detail p strong {
            text-indent: 0;
            color: #f02387 !important;
            font-size: 1.25em;
            font-weight: normal;
        }

        .option {
            border-top: #9d9d9d 1px dotted;
            margin-top: 1em;
            padding-top: 1em;
        }

        .option span {
            padding: 0 0 0 0.5em;
            color: #858585;
        }

        .option-con {
            /*width: 100%;*/
            padding-top: 0.5em;
            overflow: hidden;
        }

        .option-con ul li {
            margin: 0 1.6666% .66em;
            float: left;
            width: 30%;
            color: #858585;
        }

        .option-con ul li input {
            display: none;

        }

        .option-con ul li label {
            display: inline-block;
            border: 1px solid;
            width: 100%;
            text-align: center;
            padding: 0.4em 0;

        }

        .option-con ul li .sel {
            background: url(${ctx}/resources/mallimages/selected_new.png) right bottom no-repeat;
        }

        .buy .Title {
            /*padding: 0 0 0 0.3em;*/
            color: #858585;

        }

        .inputs {
            margin-top: 0.5em;
            /*margin-left: 0.5em;*/
            overflow: hidden;
        }

        .buy .sup, .plus {
            float: left;
            text-align: center;
            display: inline-block;
            background-color: #eee;
            width: 2.3em;
            font-size: 1em;
            line-height: 2.3em;
        }

        .inputs input {
            font: inherit;
            height: 2.29em;
            text-align: center;
            float: left;
            width: 6em;
            padding: 0;
            margin: 0;
            outline: 0;
            border: none;
        }

        .buy-cart {
            overflow: hidden;
        }

        .toCart {
            /*margin: 1em 2.5% 0;*/
            display: inline-block;
            margin-top: 1em;
            width: 100%;
            text-align: center;
            background: #f02387;
            border-radius: 2em;
            font-size: 1.5em;
            padding: 0.5em 0;
        }




        .toCart span {
            color: white;
        }

        .tab-info {
            /*margin-top: 1em;*/
            padding: 1em 0.5em;
        }

        .tab-info img {
            width: 100%;
        }

    </style>
</head>
<body>
<div class="con" id="con">
    <%--<header>--%>
        <%--<div class="headerLeft">--%>
            <%--<a href="javascript:history.go(-1)"><span></span></a>--%>
        <%--</div>--%>
        <%--<span class="Title"><h3>详情页</h3></span>--%>
    </header>
    <div id="focus">
        <a href=""><img src="<c:url value="${dmzImgPath}${goods.imgFile}"/>" alt=""></a>
    </div>
    <div class="info">
        <section class="Title">
            <h4>${goods.name}</h4>
            <%--<span><i class="fa fa-heart-o"></i>收藏</span>--%>
        </section>
        <section class="detail">
            <p>
                    <span>当前价格:
                    <strong>￥${goods.retailPrice}</strong>
                    </span>
            </p>
            <p>
                    <span>产品规格:
                    <strong>${goods.spec}</strong>
                    </span>
            </p>

            <p>
                    <span>批准文号:
                    <strong>${goods.license}</strong>
                    </span>
            </p>

            <p>
                    <span>生产公司:
                    <strong>${goods.company}</strong>
                    </span>
            </p>

            <%--<c:if test="${goods.}"></c:if>--%>
            <%--<p>--%>
            <%--<span>活动结束时间:--%>
            <%--<strong>1天</strong>--%>
            <%--</span>--%>
            <%--</p>--%>
        </section>
        <%--<section class="option">--%>
        <%--<span>分类:</span>--%>
        <%--<div class="option-con">--%>
        <%--<ul>--%>
        <%--<li>--%>
        <%--<label :class="{sel:isSel('spec1')}"  v-on:click="specSelect" for="spec1">红色</label>--%>
        <%--<input   type="radio" id="spec1">--%>
        <%--</li>--%>

        <%--<li>--%>
        <%--<label :class="{sel:isSel('spec2')}"  v-on:click="specSelect" for="spec2">红色</label>--%>
        <%--<input  type="radio" id="spec2">--%>
        <%--</li>--%>

        <%--<li>--%>
        <%--<label  v-on:click="specSelect" for="spec3">红色</label>--%>
        <%--<input  type="radio" id="spec3">--%>
        <%--</li>--%>


        <%--<li>--%>
        <%--<label v-on:click="specSelect" for="spec4">红色</label>--%>
        <%--<input  type="radio" id="spec4">--%>
        <%--</li>--%>
        <%--</ul>--%>
        <%--</div>--%>

        <%--</section>--%>
        <section class="buy">
            <span class="Title">购买数量:</span>
            <div class="inputs">
                <span class="sup">-</span>
                <input class="inp" type="text" value="1">
                <span class="plus">+</span>
            </div>
        </section>
        <div class="buy-cart">
            <a href="" data-id="${goods.id}" class="toCart"><span>加入购物车</span></a>
            <%--<a href="<c:url value='/vmall/shopcart/index.html'/> " class="toBuy"><span>立即购买</span></a>--%>
        </div>
        <div class="tab-info">
            <p><img src="${ctx}/resources/mallimages/detail_tit.jpg" alt=""></p>
            <c:set value="${fn:split(goods.detailImg,'+')}" var="imgs"/>
            <c:forEach items="${imgs}" var="xqimg">
                <c:if test="${xqimg ne ''}">
                    <p><img src="${dmzImgPath}${xqimg}" alt=""></p>
                </c:if>
            </c:forEach>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
<jsp:include page="cart.jsp"/>
<%--<script src="https://cdn.bootcss.com/vue/2.3.3/vue.min.js"></script>--%>
<script>
    $(function () {


        $(".sup").click(function (e) {
            e.stopPropagation();
            e.preventDefault();
            var quantity = $(".inp");
            var v = parseInt(quantity.val());
            if (isNaN(v)) {
                v = 1;
            }
            quantity.val(v - 1).change();
        });

        $(".plus").click(function (e) {
            e.stopPropagation();
            e.preventDefault();
            var quantity = $(".inp");
            var v = parseInt(quantity.val());
            if (isNaN(v)) {
                v = 1;
            }
            quantity.val(v + 1).change();
        });


        $(".inp").change(function (e) {
            var $this = $(this);
            if (!parseInt($this.val())) {
                $this.val(1);
            }
            if (parseInt($this.val()) < 1) {
                $this.val(1);
            }
        });


        $(".toCart").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            var id = $(this).attr("data-id");
            $.post("<c:url value='/vmall/shopcart/put.json'/>",
                {
                    "goodsId": id,
                    "quantity": $(".inp").val()
                }
                ,
                function (data, status, jqXHR) {
                    var m = data;
                    if (m.flag == "0") {
                        window.location.href = "<c:url value='/mobile/shopcart/index.html'/>";
                    } else {
                        alert("操作失败"+m.message);
                    }
                }).fail(function(xhr) {
                if(xhr.status==401){
                    window.location=xhr.getResponseHeader("Location");
                }else {
                    alert("未知错误请联系管理员");
                }
            });
        });

        //        $(".flow_cart_num").html("2");
    })
    //    var vm = new Vue({
    //        el : "#con",
    //        data:{
    //            message: 'Hello Vue!',
    //            specName:[]
    //        },
    //        methods:{
    //            specSelect:function (e) {
    ////                this.specName.add(e.target.id);
    //                this.specName=[];
    //                this.specName.push(e.target.getAttribute("for"));
    //                console.log(this.specName);
    //            }, isSel:function (val) {
    //                var tem = false;
    //                for(var t in this.specName){
    //                    console.log(val+"=="+this.specName[t])
    //                    if(this.specName[t]==val){
    //                        tem=true;
    //                    }
    //                }
    //                return tem;
    //            }
    //        },
    //        computed: {
    //
    //        }
    //    })
</script>
</body>
</html>