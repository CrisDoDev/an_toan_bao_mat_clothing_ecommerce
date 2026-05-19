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
<title>${lang == 'en' ? product.nameEn : product.name}</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="icon" type="image/png" href="images/icons/favicon.png" />
<link rel="stylesheet" type="text/css"
	href="vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="fonts/iconic/css/material-design-iconic-font.min.css">
<link rel="stylesheet" type="text/css"
	href="fonts/linearicons-v1.0.0/icon-font.min.css">
<link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
<link rel="stylesheet" type="text/css"
	href="vendor/css-hamburgers/hamburgers.min.css">
<link rel="stylesheet" type="text/css"
	href="vendor/animsition/css/animsition.min.css">
<link rel="stylesheet" type="text/css"
	href="vendor/select2/select2.min.css">
<link rel="stylesheet" type="text/css" href="vendor/slick/slick.css">
<link rel="stylesheet" type="text/css"
	href="vendor/MagnificPopup/magnific-popup.css">
<link rel="stylesheet" type="text/css"
	href="vendor/perfect-scrollbar/perfect-scrollbar.css">
<link rel="stylesheet" type="text/css" href="css/util.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body class="animsition">

	<jsp:include page="header.jsp" />

	<div class="container">
		<div class="bread-crumb flex-w p-l-25 p-r-15 p-t-30 p-lr-0-lg">
			<a href="home" class="stext-109 cl8 hov-cl1 trans-04"> <fmt:message
					key="breadcrumb.home" bundle="${msgs}" /> <i
				class="fa fa-angle-right m-l-9 m-r-10" aria-hidden="true"></i>
			</a> <a href="product" class="stext-109 cl8 hov-cl1 trans-04"> <fmt:message
					key="breadcrumb.product" bundle="${msgs}" /> <i
				class="fa fa-angle-right m-l-9 m-r-10" aria-hidden="true"></i>
			</a> <span class="stext-109 cl4"> ${lang == 'en' ? product.nameEn : product.name}
			</span>
		</div>
	</div>

	<section class="sec-product-detail bg0 p-t-65 p-b-60">
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-lg-7 p-b-30">
					<div class="p-l-25 p-r-30 p-lr-0-lg">
						<div class="wrap-slick3 flex-sb flex-w">
							<div class="wrap-slick3-dots"></div>
							<div class="wrap-slick3-arrows flex-sb-m flex-w"></div>

							<div class="slick3 gallery-lb">
								<div class="item-slick3" data-thumb="images/${product.imageUrl}">
									<div class="wrap-pic-w pos-relative">
										<img src="images/${product.imageUrl}" alt="IMG-PRODUCT">
										<a
											class="flex-c-m size-108 how-pos1 bor0 fs-16 cl10 bg0 hov-btn3 trans-04"
											href="images/${product.imageUrl}"> <i
											class="fa fa-expand"></i>
										</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-6 col-lg-5 p-b-30">
					<div class="p-r-50 p-t-5 p-lr-0-lg">
						<h4 class="mtext-105 cl2 js-name-detail p-b-14">${lang == 'en' ? product.nameEn : product.name}
						</h4>

						<span class="mtext-106 cl2"> <fmt:formatNumber
								value="${product.price}" type="number" maxFractionDigits="0" />
							VND
						</span>

						<p class="stext-102 cl3 p-t-23">${lang == 'en' ? product.descriptionEn : product.description}
						</p>

						<div class="p-t-33">
							<form action="${pageContext.request.contextPath}/cart"
								method="post">
								<input type="hidden" name="action" value="add"> <input
									type="hidden" name="id" value="${product.id}">

								<div class="flex-w flex-r-m p-b-10">
									<div class="size-203 flex-c-m respon6">Size</div>

									<div class="size-204 respon6-next">
										<div class="rs1-select2 bor8 bg0">
											<select class="js-select2" name="size" required>
												<option value="">
													<fmt:message key="product.selectSize" bundle="${msgs}" />
												</option>

												<c:forEach items="${listSizes}" var="s">
													<c:choose>
														<c:when test="${s.stockQuantity > 0}">
															<option value="${s.size}" data-max="${s.stockQuantity}">
																Size ${s.size} (
																<fmt:message key="cart.available" bundle="${msgs}" />:
																${s.stockQuantity})
															</option>
														</c:when>
														<c:otherwise>
															<option disabled>Size ${s.size} (
																<fmt:message key="product.outOfStock" bundle="${msgs}" />)
															</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>

												<c:if test="${empty listSizes}">
													<option value="OneSize">One Size</option>
												</c:if>
											</select>
											<div class="dropDownSelect2"></div>
										</div>
									</div>
								</div>

								<div class="flex-w flex-r-m p-b-10">
									<div class="size-204 flex-w flex-m respon6-next">
										<div class="wrap-num-product flex-w m-r-20 m-tb-10">
											<div
												class="btn-num-product-down cl8 hov-btn3 trans-04 flex-c-m">
												<i class="fs-16 zmdi zmdi-minus"></i>
											</div>

											<input id="num-product-input"
												class="mtext-104 cl3 txt-center num-product" type="number"
												name="quantity" value="1" min="1">

											<div
												class="btn-num-product-up cl8 hov-btn3 trans-04 flex-c-m">
												<i class="fs-16 zmdi zmdi-plus"></i>
											</div>
										</div>

										<button
											class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04 js-addcart-detail">
											  <fmt:message key="product.addToCart" bundle="${msgs}" />
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<jsp:include page="footer.jsp" />

	<div class="btn-back-to-top" id="myBtn">
		<span class="symbol-btn-back-to-top"> <i
			class="zmdi zmdi-chevron-up"></i>
		</span>
	</div>

	<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
	<script src="vendor/animsition/js/animsition.min.js"></script>
	<script src="vendor/bootstrap/js/popper.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="vendor/select2/select2.min.js"></script>
	<script>
		$(".js-select2").each(function() {
			$(this).select2({
				minimumResultsForSearch : 20,
				dropdownParent : $(this).next('.dropDownSelect2')
			});
		})
	</script>
	<script src="vendor/daterangepicker/moment.min.js"></script>
	<script src="vendor/daterangepicker/daterangepicker.js"></script>
	<script src="vendor/slick/slick.min.js"></script>
	<script src="js/slick-custom.js"></script>
	<script src="vendor/parallax100/parallax100.js"></script>
	<script>
		$('.parallax100').parallax100();
	</script>
	<script src="vendor/MagnificPopup/jquery.magnific-popup.min.js"></script>
	<script>
		$('.gallery-lb').each(function() {
			$(this).magnificPopup({
				delegate : 'a',
				type : 'image',
				gallery : {
					enabled : true
				},
				mainClass : 'mfp-fade'
			});
		});
	</script>
	<script src="vendor/isotope/isotope.pkgd.min.js"></script>
	<script src="vendor/sweetalert/sweetalert.min.js"></script>
	<script>
		/* [Lưu ý từ Chuyên gia]
		   Việc hardcode text tiếng Anh trong JS (ví dụ: "is added to cart !") sẽ không đồng bộ với ngôn ngữ trang.
		   Để chuyên nghiệp, bạn nên lấy message từ resource bundle và gán vào biến JS hidden.
		 */
		$('.js-addwish-detail').each(
				function() {
					var nameProduct = $(this).parent().parent().parent().find(
							'.js-name-detail').html();

					$(this).on('click', function() {
						swal(nameProduct, "is added to wishlist !", "success");

						$(this).addClass('js-addedwish-detail');
						$(this).off('click');
					});
				});

		$('.js-addcart-detail').each(
				function() {
					var nameProduct = $(this).parent().parent().parent()
							.parent().find('.js-name-detail').html();
					$(this).on('click', function() {
						// Text này nên được Việt hóa hoặc lấy động
						swal(nameProduct, "is added to cart !", "success");
					});
				});
	</script>
	<script src="vendor/perfect-scrollbar/perfect-scrollbar.min.js"></script>
	<script>
		$('.js-pscroll').each(function() {
			$(this).css('position', 'relative');
			$(this).css('overflow', 'hidden');
			var ps = new PerfectScrollbar(this, {
				wheelSpeed : 1,
				scrollingThreshold : 1000,
				wheelPropagation : false,
			});

			$(window).on('resize', function() {
				ps.update();
			})
		});
	</script>
	<script src="js/main.js"></script>

	<script>
		//Xử lý khi chọn Size
		$('select[name="size"]').on('change', function() {
			var maxStock = $(this).find(':selected').data('max');
			var inputQuantity = $('#num-product-input');

			if (maxStock) {
				inputQuantity.attr('max', maxStock);

				// Nếu số lượng đang chọn lớn hơn tồn kho mới -> reset về max
				if (parseInt(inputQuantity.val()) > maxStock) {
					inputQuantity.val(maxStock);
				}
			} else {
				inputQuantity.removeAttr('max');
			}
		});

		//Xử lý nút Cộng (+) và Trừ (-) 
		$('.btn-num-product-down').off('click');
		$('.btn-num-product-up').off('click');

		$('.btn-num-product-up').on('click', function() {
			var input = $(this).prev();
			var numProduct = Number(input.val());
			var maxStock = parseInt(input.attr('max'));

			// Chỉ tăng nếu không có giới hạn max hoặc chưa đạt max
			if (isNaN(maxStock) || numProduct < maxStock) {
				input.val(numProduct + 1);
			}
		});

		$('.btn-num-product-down').on('click', function() {
			var input = $(this).next();
			var numProduct = Number(input.val());
			if (numProduct > 1) {
				input.val(numProduct - 1);
			}
		});

		// Chặn nhập tay số quá lớn hoặc < 1
		$('#num-product-input').on('change keyup', function() {
			var max = parseInt($(this).attr('max'));
			var val = parseInt($(this).val());

			if (val < 1 || isNaN(val)) {
				$(this).val(1);
			}
			if (max && val > max) {
				$(this).val(max);
			}
		});
	</script>
</body>
</html>