<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="messages" var="msgs" />
<c:set var="lang"
	value="${sessionScope.lang != null ? sessionScope.lang : 'vi'}" />

<head>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<style>
.language-switcher {
	display: flex;
	gap: 5px;
}

.language-switcher a {
	padding: 5px 10px;
	border-radius: 4px;
	text-decoration: none;
	color: #888;
	font-size: 13px;
	transition: all 0.3s;
}

.language-switcher a:hover {
	color: #333;
	background: rgba(0, 0, 0, 0.05);
}

.language-switcher a.active-lang {
	color: #00ad5f;
	font-weight: bold;
	background: rgba(0, 173, 95, 0.1);
}
</style>
</head>

<header class="header-v4">
	<div class="container-menu-desktop">
		<div class="top-bar">
			<div class="content-topbar flex-sb-m h-full container">
				<div class="left-top-bar">
					<fmt:message key="topbar.freeship" bundle="${msgs}" />
				</div>

				<div class="right-top-bar flex-w h-full">
					<a href="#" class="flex-c-m trans-04 p-lr-25"> <fmt:message
							key="footer.help" bundle="${msgs}" /> & <fmt:message
							key="footer.faqs" bundle="${msgs}" />
					</a>

					<%-- Language Switcher --%>					
					<div class="language-switcher flex-c-m trans-04 p-lr-15">
						<c:set var="currentLang"
							value="${sessionScope.lang != null ? sessionScope.lang : 'vi'}" />
						<a href="javascript:void(0)" onclick="changeLanguage('vi')"
							class="trans-04 ${currentLang == 'vi' ? 'active-lang' : ''}"
							title="<fmt:message key='lang.vietnamese' bundle='${msgs}' />">🇻🇳
							VI</a> <a href="javascript:void(0)" onclick="changeLanguage('en')"
							class="trans-04 ${currentLang == 'en' ? 'active-lang' : ''}"
							title="<fmt:message key='lang.english' bundle='${msgs}' />">🇬🇧
							EN</a>
					</div>

					<c:choose>
						<c:when test="${not empty sessionScope.user}">
							<a href="profile.jsp" class="flex-c-m trans-04 p-lr-25"
								style="color: #00ad5f; font-weight: bold;"> <i
								class="fa fa-user m-r-5"></i> <fmt:message key="admin.welcome"
									bundle="${msgs}" /> ${sessionScope.user.fullName}
							</a>

							<a href="${pageContext.request.contextPath}/order-history"
								class="flex-c-m trans-04 p-lr-25"> <fmt:message
									key="menu.myOrders" bundle="${msgs}" />
							</a>

							<c:if test="${sessionScope.user.roleId == 1}">
								<a href="${pageContext.request.contextPath}/admin/dashboard"
									class="flex-c-m trans-04 p-lr-25" style="color: red;"> <fmt:message
										key="admin.dashboard" bundle="${msgs}" />
								</a>
							</c:if>

							<a href="${pageContext.request.contextPath}/logout"
								class="flex-c-m trans-04 p-lr-25"> <fmt:message
									key="menu.logout" bundle="${msgs}" />
							</a>
						</c:when>
						<c:otherwise>
							<a href="${pageContext.request.contextPath}/register.jsp"
								class="flex-c-m trans-04 p-lr-25"> <fmt:message
									key="menu.register" bundle="${msgs}" />
							</a>
							<a href="${pageContext.request.contextPath}/login.jsp"
								class="flex-c-m trans-04 p-lr-25"> <fmt:message
									key="menu.login" bundle="${msgs}" />
							</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>

		<div class="wrap-menu-desktop">
			<nav class="limiter-menu-desktop container">
				<a href="home" class="logo">
					<h1 style="font-size: 24px; font-weight: bold; color: #333;">
						<fmt:message key="header.shopName" bundle="${msgs}" />
					</h1>
				</a>

				<div class="menu-desktop">
					<ul class="main-menu">
						<li
							class="${pageContext.request.servletPath == '/home.jsp' ? 'active-menu' : ''}">
							<a href="home"><fmt:message key="menu.home" bundle="${msgs}" /></a>
						</li>
						<li><a href="product"><fmt:message key="menu.shop"
									bundle="${msgs}" /></a></li>
						<li><a href="cart"><fmt:message key="menu.cart"
									bundle="${msgs}" /></a></li>
						<li><a href="about.jsp"><fmt:message key="menu.about"
									bundle="${msgs}" /></a></li>
						<li><a href="contact.jsp"><fmt:message key="menu.contact"
									bundle="${msgs}" /></a></li>
					</ul>
				</div>

				<div class="wrap-icon-header flex-w flex-r-m">
					<div
						class="icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11 js-show-modal-search">
						<i class="zmdi zmdi-search"></i>
					</div>
					<div
						class="icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11 icon-header-noti js-show-cart"
						data-notify="${empty sessionScope.cart ? 0 : sessionScope.cart.totalQuantity}">
						<i class="zmdi zmdi-shopping-cart"></i>
					</div>
				</div>
			</nav>
		</div>
	</div>

	<%-- Modal Tìm kiếm --%>
	<div class="modal-search-header flex-c-m trans-04 js-hide-modal-search">
		<div class="container-search-header">
			<button
				class="flex-c-m btn-hide-modal-search trans-04 js-hide-modal-search">
				<img
					src="${pageContext.request.contextPath}/images/icons/icon-close2.png"
					alt="CLOSE">
			</button>
			<form class="wrap-search-header flex-w p-l-15">
				<button class="flex-c-m trans-04">
					<i class="zmdi zmdi-search"></i>
				</button>
				<input class="plh3" type="text" name="search"
					placeholder="<fmt:message key='header.searchPlaceholder' bundle='${msgs}' />">
			</form>
		</div>
	</div>
</header>

<%-- Mini Cart Panel --%>
<div class="wrap-header-cart js-panel-cart">
	<div class="s-full js-hide-cart"></div>
	<div class="header-cart flex-col-l p-l-65 p-r-25">
		<div class="header-cart-title flex-w flex-sb-m p-b-8">
			<span class="mtext-103 cl2"><fmt:message key="cart.title"
					bundle="${msgs}" /></span>
			<div
				class="fs-35 lh-10 cl2 p-lr-5 pointer hov-cl1 trans-04 js-hide-cart">
				<i class="zmdi zmdi-close"></i>
			</div>
		</div>

		<div class="header-cart-content flex-w js-pscroll">
			<c:if test="${empty sessionScope.cart.items}">
				<div class="w-full text-center p-t-50">
					<img
						src="${pageContext.request.contextPath}/images/icons/empty-cart.png"
						alt="Empty" style="width: 60px; opacity: 0.5;">
					<p class="stext-111 cl6 p-t-20">
						<fmt:message key="cart.empty" bundle="${msgs}" />
					</p>
				</div>
			</c:if>

			<c:if test="${not empty sessionScope.cart.items}">
				<ul class="header-cart-wrapitem w-full">
					<c:forEach items="${sessionScope.cart.items}" var="item">
						<li class="header-cart-item flex-w flex-t m-b-12">
							<div class="header-cart-item-img">
								<img
									src="${pageContext.request.contextPath}/images/${item.product.imageUrl}"
									alt="IMG">
							</div>
							<div class="header-cart-item-txt p-t-8">
								<a href="product-detail?id=${item.product.id}"
									class="header-cart-item-name m-b-18 hov-cl1 trans-04">${item.product.name}</a>
								<span class="header-cart-item-info"> ${item.quantity} x <fmt:formatNumber
										value="${item.product.price}" type="number" />
									${sessionScope.lang == 'en' ? '$' : 'đ'}
								</span>
							</div>
						</li>
					</c:forEach>
				</ul>

				<div class="w-full">
					<div class="header-cart-total w-full p-tb-40">
						<fmt:message key="cart.total" bundle="${msgs}" />
						: <span style="color: #c0392b; font-weight: bold;"> <fmt:formatNumber
								value="${sessionScope.cart.totalMoney}" type="number" />
							${sessionScope.lang == 'en' ? '$' : 'đ'}
						</span>
					</div>
					<div class="header-cart-buttons flex-w w-full">
						<a href="cart"
							class="flex-c-m stext-101 cl0 size-107 bg3 bor2 hov-btn3 p-lr-15 trans-04 m-r-8 m-b-10">
							<fmt:message key="menu.cart" bundle="${msgs}" />
						</a> <a href="checkout"
							class="flex-c-m stext-101 cl0 size-107 bg3 bor2 hov-btn3 p-lr-15 trans-04 m-b-10">
							<fmt:message key="cart.checkout" bundle="${msgs}" />
						</a>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</div>

<%-- Thông báo SweetAlert2 --%>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        // Thông báo Đăng xuất
        <c:if test="${param.status == 'logged_out'}">
            Swal.fire({
                icon: 'success',         
                title: '<fmt:message key="message.success" bundle="${msgs}" />',
                text: '${sessionScope.lang == "en" ? "Successfully logged out! See you next time." : "Đăng xuất thành công! Hẹn gặp lại bạn lần sau."}',
                timer: 2000, timerProgressBar: true, showConfirmButton: false
            });
        </c:if>

        // Thông báo Đặt hàng thành công
        <c:if test="${param.status == 'order_success'}">
            Swal.fire({
                icon: 'success',
                title: '<fmt:message key="message.orderSuccess" bundle="${msgs}" />',
                text: '${sessionScope.lang == "en" ? "Thank you for shopping with us." : "Cảm ơn bạn đã mua sắm tại cửa hàng."}',
                confirmButtonText: '<fmt:message key="order.viewDetails" bundle="${msgs}" />',
                showCancelButton: true,
                cancelButtonText: '<fmt:message key="button.close" bundle="${msgs}" />'
            }).then((result) => { if (result.isConfirmed) window.location.href = "order-history"; });
        </c:if>

        // Thông báo Giỏ hàng trống
        <c:if test="${param.message == 'cart_empty'}">
            Swal.fire({
                icon: 'info',
                title: '<fmt:message key="cart.empty" bundle="${msgs}" />',
                text: '${sessionScope.lang == "en" ? "You have no items in your cart." : "Giỏ hàng của bạn đang trống."}'
            });
        </c:if>

        // Xóa param URL
        if (window.history.replaceState) {
            const url = new URL(window.location.href);
            url.searchParams.delete('status');
            url.searchParams.delete('message');
            window.history.replaceState(null, '', url.href);
        }
    });
</script>
<script>
    function changeLanguage(langCode) {
        // Lấy URL hiện tại (ví dụ: product-detail?id=5&size=M)
        let currentUrl = new URL(window.location.href);

        // Cập nhật hoặc Thêm tham số 'lang' (thành: product-detail?id=5&size=M&lang=en)
        currentUrl.searchParams.set('lang', langCode);

        // Chuyển hướng sang URL mới
        window.location.href = currentUrl.toString();
    }
</script>