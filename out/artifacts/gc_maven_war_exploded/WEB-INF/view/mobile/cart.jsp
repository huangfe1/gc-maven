<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/common/common.jsp"%>
<style>

    #scrollUp, .scrollUp {
        border-radius: 100%;
        background-color: #000;
        color: #eee;
        font-size: 3em;
        line-height: 1;
        text-align: center;
        text-decoration: none;
        bottom: .25em;
        right: .25em;
        overflow: hidden;
        width: 1.15em;
        height: 1.15em;
        border: 1px solid #fff;
        opacity: 0.7;
        display: none;
    }
    .scrollUp {
        display: block;
        background: #f4469b;
        right: auto;
        left: .25em;
        opacity: 1;
        display: block;
    }

    #scrollUp i, .scrollUp i {
        content: "";
        display: inline-block;
        position: absolute;
        top: 50%;
        left: 50%;
        width: 0.375em;
        height: 0.525em;
        margin: -0.25em 0 0 -0.175em;
        background: url(${ctx}/resources/mallimages/png.png) -1.375em -1.625em no-repeat;
        background-size: 5em;

    }

    .scrollUp i {
        width: 0.6em;
        height: 0.525em;
        background: url(${ctx}/resources/mallimages/png.png) -0.075em -0.875em no-repeat;
        background-size: 5em;
        margin-left: -0.275em;
    }
    .scrollUp .flow_cart_num{
        position: relative;
        /*top: 0.3em;*/
        /*left: 1.2em;*/
    }


</style>


<a id="scrollUp" href="#top" style="position: fixed; z-index: 10;">
    <i class="fa fa-angle"></i>
</a>

<a class="scrollUp" href="<c:url value='/mobile/shopcart/index.html'/>" style="position: fixed; z-index: 10; left:.25em;right:auto;">
    <i class="fa fa-angle"></i>
    <span class="flow_cart_num badge">${shopcart.quantity}</span>
</a>
<script>
    $(function () {
        var bodyH=$(window).height();
        var scrollUp=$('#scrollUp');
        $(window).scroll(function () {
            if ($(this).scrollTop() > bodyH/2 ) {
                scrollUp.fadeIn();
            } else {
                scrollUp.fadeOut();
            }
        });
        $(window).resize(function(){
            bodyH=$(window).height();
            $(window).scroll(function () {
                if ($(this).scrollTop() > bodyH/2 ) {
                    scrollUp.fadeIn();
                } else {
                    scrollUp.fadeOut();
                }
            });
        });
        // scroll body to 0px on click
        $('#scrollUp').click(function () {
//            $('#scrollUp').tooltip('hide');
            $('body,html').animate({scrollTop:0}, 200);
            return false;
        });
    })

</script>
