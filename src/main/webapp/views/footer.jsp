<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="messages" var="msgs" />

<footer class="bg3 p-t-75 p-b-32">
		<div class="container">
			<div class="row">
				<div class="col-sm-6 col-lg-3 p-b-50">
					<h4 class="stext-301 cl0 p-b-30"><fmt:message key="footer.categories" bundle="${msgs}" /></h4>
					<ul>
						<li class="p-b-10"><a href="#" class="stext-107 cl7 hov-cl1 trans-04"><fmt:message key="footer.women" bundle="${msgs}" /></a></li>
						<li class="p-b-10"><a href="#" class="stext-107 cl7 hov-cl1 trans-04"><fmt:message key="footer.men" bundle="${msgs}" /></a></li>
						<li class="p-b-10"><a href="#" class="stext-107 cl7 hov-cl1 trans-04"><fmt:message key="footer.shoes" bundle="${msgs}" /></a></li>
						<li class="p-b-10"><a href="#" class="stext-107 cl7 hov-cl1 trans-04"><fmt:message key="footer.watches" bundle="${msgs}" /></a></li>
					</ul>
				</div>
				<div class="col-sm-6 col-lg-3 p-b-50">
					<h4 class="stext-301 cl0 p-b-30"><fmt:message key="footer.help" bundle="${msgs}" /></h4>
					<ul>
						<li class="p-b-10"><a href="#" class="stext-107 cl7 hov-cl1 trans-04"><fmt:message key="footer.trackOrder" bundle="${msgs}" /></a></li>
						<li class="p-b-10"><a href="#" class="stext-107 cl7 hov-cl1 trans-04"><fmt:message key="footer.returns" bundle="${msgs}" /></a></li>
						<li class="p-b-10"><a href="#" class="stext-107 cl7 hov-cl1 trans-04"><fmt:message key="footer.shipping" bundle="${msgs}" /></a></li>
						<li class="p-b-10"><a href="#" class="stext-107 cl7 hov-cl1 trans-04"><fmt:message key="footer.faqs" bundle="${msgs}" /></a></li>
					</ul>
				</div>
				<div class="col-sm-6 col-lg-3 p-b-50">
					<h4 class="stext-301 cl0 p-b-30"><fmt:message key="footer.contact" bundle="${msgs}" /></h4>
					<p class="stext-107 cl7 size-201">
						<fmt:message key="footer.address" bundle="${msgs}" />
					</p>
					<div class="p-t-27">
						<a href="#" class="fs-18 cl7 hov-cl1 trans-04 m-r-16"><i class="fa fa-facebook"></i></a>
						<a href="#" class="fs-18 cl7 hov-cl1 trans-04 m-r-16"><i class="fa fa-instagram"></i></a>
						<a href="#" class="fs-18 cl7 hov-cl1 trans-04 m-r-16"><i class="fa fa-pinterest-p"></i></a>
					</div>
				</div>
				<div class="col-sm-6 col-lg-3 p-b-50">
					<h4 class="stext-301 cl0 p-b-30"><fmt:message key="footer.newsletter" bundle="${msgs}" /></h4>
					<form>
						<div class="wrap-input1 w-full p-b-4">
							<input class="input1 bg-none plh1 stext-107 cl7" type="text" name="email" placeholder="<fmt:message key='footer.emailPlaceholder' bundle='${msgs}' />">
							<div class="focus-input1 trans-04"></div>
						</div>
						<div class="p-t-18">
							<button class="flex-c-m stext-101 cl0 size-103 bg1 bor1 hov-btn2 p-lr-15 trans-04">
								<fmt:message key="footer.subscribe" bundle="${msgs}" />
							</button>
						</div>
					</form>
				</div>
			</div>
			<div class="p-t-40">
				<p class="stext-107 cl6 txt-center">
					Copyright &copy;<script>document.write(new Date().getFullYear());</script> <fmt:message key="footer.copyright" bundle="${msgs}" /> | <fmt:message key="footer.madeWith" bundle="${msgs}" /> <i class="fa fa-heart-o" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
				</p>
			</div>
		</div>
	</footer>
<div class="btn-back-to-top" id="myBtn">
    <span class="symbol-btn-back-to-top"><i class="zmdi zmdi-chevron-up"></i></span>
</div>