<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<footer class="footer">
	<div class="foot-con">
		<div class="foot-con_2">
			<a href="<c:url value="/dmz/pmall/show.html"/> " class="active">
				<i class="navIcon home"></i>
				<span class="text">优惠商城</span>
			</a>
			<%--<a href="category.html">--%>
			<a href="<c:url value='/dmz/mobile/index.html'/>">
				<i class="navIcon sort"></i>
				<span class="text">特权商城</span>
			</a>
			<%--<a href="shopcart.html">--%>
			<a href="<c:url value="/dmz/pmall/shopcart/index.html"/>">
				<i class="navIcon shop"></i>
				<span class="text">购物车<span class="badge" id="shopcartQuantity">${pmshopcart.quantity}</span></span>
            </a>
			<%--<a href="userhome.html" >--%>
			<a href="<c:url value='/mobile/my.html'/> " >
				<i class="navIcon member"></i>
				<span class="text">用户中心</span>
			</a>
		</div>
	</div>
</footer>