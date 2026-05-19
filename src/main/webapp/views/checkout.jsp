<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="messages" var="msgs" />
<c:set var="lang"
	value="${sessionScope.lang != null ? sessionScope.lang : 'vi'}" />

<!DOCTYPE html>
<html lang="en">
<head>
<title><fmt:message key="checkout.title" bundle="${msgs}" /></title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/images/icons/favicon.png" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/fonts/iconic/css/material-design-iconic-font.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/fonts/linearicons-v1.0.0/icon-font.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/vendor/animate/animate.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/vendor/css-hamburgers/hamburgers.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/vendor/animsition/css/animsition.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/vendor/select2/select2.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/vendor/daterangepicker/daterangepicker.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/vendor/slick/slick.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/vendor/MagnificPopup/magnific-popup.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/vendor/perfect-scrollbar/perfect-scrollbar.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/util.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/main.css">

<jsp:include page="header.jsp" />
</head>

<body class="animsition">

	<div class="container">
		<div class="bread-crumb flex-w p-l-25 p-r-15 p-t-30 p-lr-0-lg">
			<a href="home" class="stext-109 cl8 hov-cl1 trans-04"> <fmt:message
					key="breadcrumb.home" bundle="${msgs}" />
			</a> <i class="fa fa-angle-right m-l-9 m-r-10"></i> <span
				class="stext-109 cl4"> <fmt:message key="breadcrumb.checkout"
					bundle="${msgs}" />
			</span>
		</div>
	</div>

	<form action="checkout" method="post" class="bg0 p-t-75 p-b-85">
		<div class="container">
			<div class="row">
				<div class="col-lg-10 col-xl-7 m-lr-auto m-b-50">
					<div
						class="bor10 p-lr-40 p-t-30 p-b-40 m-l-63 m-r-40 m-lr-0-xl p-lr-15-sm">
						<h4 class="mtext-109 cl2 p-b-30">
							<fmt:message key="checkout.billingDetails" bundle="${msgs}" />
						</h4>

						<c:if test="${not empty errorMessage}">
							<div class="alert alert-danger">${errorMessage}</div>
						</c:if>

						<div class="bor8 bg0 m-b-12">
							<input class="stext-111 cl8 plh3 size-111 p-lr-15" type="text"
								value="${user.fullName}" readonly
								style="background-color: #f2f2f2;">
						</div>

						<div class="bor8 bg0 m-b-12">
							<input class="stext-111 cl8 plh3 size-111 p-lr-15" type="text"
								value="${user.email}" readonly
								style="background-color: #f2f2f2;">
						</div>

						<div class="bor8 bg0 m-b-12">
							<input class="stext-111 cl8 plh3 size-111 p-lr-15" type="text"
								name="phone"
								placeholder="<fmt:message key='checkout.phone' bundle='${msgs}' />"
								value="${user.phone}" required>
						</div>

						<div class="bor8 bg0 m-b-22">
							<textarea class="stext-111 cl8 plh3 size-111 p-lr-15 p-t-15"
								name="address"
								placeholder="<fmt:message key='checkout.address' bundle='${msgs}' />"
								required rows="3">${user.address}</textarea>
						</div>
					</div>
				</div>

				<div class="col-sm-10 col-lg-7 col-xl-5 m-lr-auto m-b-50">
					<div
						class="bor10 p-lr-40 p-t-30 p-b-40 m-l-63 m-r-40 m-lr-0-xl p-lr-15-sm">
						<h4 class="mtext-109 cl2 p-b-30">
							<fmt:message key="checkout.orderSummary" bundle="${msgs}" />
						</h4>

						<ul class="header-cart-wrapitem w-full m-b-20">
							<c:forEach items="${sessionScope.cart.items}" var="item">
								<li class="header-cart-item flex-w flex-t m-b-12">
									<div class="header-cart-item-img">
										<img src="images/${item.product.imageUrl}" alt="IMG">
									</div>
									<div class="header-cart-item-txt p-t-8">
										<a href="#"
											class="header-cart-item-name m-b-18 hov-cl1 trans-04">
											${sessionScope.lang == 'en' ? item.product.nameEn : item.product.name}
											(Size: ${item.size}) </a> <span class="header-cart-item-info">
											${item.quantity} x <fmt:formatNumber
												value="${item.product.price}" type="number" /> đ
										</span>
									</div>
								</li>
							</c:forEach>
						</ul>

						<div class="flex-w flex-t p-t-27 p-b-33">
							<div class="size-208">
								<span class="mtext-101 cl2"> <fmt:message
										key="checkout.total" bundle="${msgs}" />:
								</span>
							</div>
							<div class="size-209 p-t-1">
								<span class="mtext-110 cl2" style="color: #c0392b;"> <fmt:formatNumber
										value="${sessionScope.cart.totalMoney}" type="number" /> VND
								</span>
							</div>
						</div>

						<button
							class="flex-c-m stext-101 cl0 size-116 bg3 bor14 hov-btn3 p-lr-15 trans-04 pointer">
							<fmt:message key="checkout.confirmOrder" bundle="${msgs}" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</form>

	<jsp:include page="footer.jsp" />
	<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
	<script src="vendor/animsition/js/animsition.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="js/main.js"></script>
</body>
</html>