<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="messages" var="msgs" />
<c:set var="lang" value="${sessionScope.lang != null ? sessionScope.lang : 'vi'}" />

<!DOCTYPE html>
<html lang="en">
<head>
	<title><fmt:message key="home.title" bundle="${msgs}" /></title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/icons/favicon.png"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/fonts/iconic/css/material-design-iconic-font.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/fonts/linearicons-v1.0.0/icon-font.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/vendor/animate/animate.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/vendor/css-hamburgers/hamburgers.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/vendor/animsition/css/animsition.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/vendor/select2/select2.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/vendor/daterangepicker/daterangepicker.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/vendor/slick/slick.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/vendor/MagnificPopup/magnific-popup.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/vendor/perfect-scrollbar/perfect-scrollbar.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/util.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body class="animsition">

	<jsp:include page="header.jsp"></jsp:include>

	<section class="section-slide">
		<div class="wrap-slick1">
			<div class="slick1">
				<div class="item-slick1" style="background-image: url(${pageContext.request.contextPath}/images/slide-01.jpg);">
					<div class="container h-full">
						<div class="flex-col-l-m h-full p-t-100 p-b-30 respon5">
							<div class="layer-slick1 animated visible-false" data-appear="fadeInDown" data-delay="0">
								<span class="ltext-101 cl2 respon2">
									Women Collection 2024
								</span>
							</div>
								
							<div class="layer-slick1 animated visible-false" data-appear="fadeInUp" data-delay="800">
								<h2 class="ltext-201 cl2 p-t-19 p-b-43 respon1">
									NEW SEASON
								</h2>
							</div>
								
							<div class="layer-slick1 animated visible-false" data-appear="zoomIn" data-delay="1600">
								<a href="product" class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04">
									<fmt:message key="home.shopNow" bundle="${msgs}" />
								</a>
							</div>
						</div>
					</div>
				</div>

				<div class="item-slick1" style="background-image: url(${pageContext.request.contextPath}/images/slide-02.jpg);">
					<div class="container h-full">
						<div class="flex-col-l-m h-full p-t-100 p-b-30 respon5">
							<div class="layer-slick1 animated visible-false" data-appear="rollIn" data-delay="0">
								<span class="ltext-101 cl2 respon2">
									Men New-Season
								</span>
							</div>
								
							<div class="layer-slick1 animated visible-false" data-appear="lightSpeedIn" data-delay="800">
								<h2 class="ltext-201 cl2 p-t-19 p-b-43 respon1">
									Jackets & Coats
								</h2>
							</div>
								
							<div class="layer-slick1 animated visible-false" data-appear="slideInUp" data-delay="1600">
								<a href="product" class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04">
									<fmt:message key="home.shopNow" bundle="${msgs}" />
								</a>
							</div>
						</div>
					</div>
				</div>

				<div class="item-slick1" style="background-image: url(${pageContext.request.contextPath}/images/slide-03.jpg);">
					<div class="container h-full">
						<div class="flex-col-l-m h-full p-t-100 p-b-30 respon5">
							<div class="layer-slick1 animated visible-false" data-appear="rotateInDownLeft" data-delay="0">
								<span class="ltext-101 cl2 respon2">
									Men Collection 2024
								</span>
							</div>
								
							<div class="layer-slick1 animated visible-false" data-appear="rotateInUpRight" data-delay="800">
								<h2 class="ltext-201 cl2 p-t-19 p-b-43 respon1">
									New arrivals
								</h2>
							</div>
								
							<div class="layer-slick1 animated visible-false" data-appear="rotateIn" data-delay="1600">
								<a href="product" class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04">
									<fmt:message key="home.shopNow" bundle="${msgs}" />
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>


	<div class="sec-banner bg0 p-t-80 p-b-50">
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-xl-4 p-b-30 m-lr-auto">
					<div class="block1 wrap-pic-w">
						<img src="${pageContext.request.contextPath}/images/banner-01.jpg" alt="IMG-BANNER">
						<a href="product" class="block1-txt ab-t-l s-full flex-col-l-sb p-lr-38 p-tb-34 trans-03 respon3">
							<div class="block1-txt-child1 flex-col-l">
								<span class="block1-name ltext-102 trans-04 p-b-8"><fmt:message key="category.women" bundle="${msgs}" /></span>
								<span class="block1-info stext-102 trans-04">Spring 2024</span>
							</div>
							<div class="block1-txt-child2 p-b-4 trans-05">
								<div class="block1-link stext-101 cl0 trans-09"><fmt:message key="home.shopNow" bundle="${msgs}" /></div>
							</div>
						</a>
					</div>
				</div>

				<div class="col-md-6 col-xl-4 p-b-30 m-lr-auto">
					<div class="block1 wrap-pic-w">
						<img src="${pageContext.request.contextPath}/images/banner-02.jpg" alt="IMG-BANNER">
						<a href="product" class="block1-txt ab-t-l s-full flex-col-l-sb p-lr-38 p-tb-34 trans-03 respon3">
							<div class="block1-txt-child1 flex-col-l">
								<span class="block1-name ltext-102 trans-04 p-b-8"><fmt:message key="category.men" bundle="${msgs}" /></span>
								<span class="block1-info stext-102 trans-04">Spring 2024</span>
							</div>
							<div class="block1-txt-child2 p-b-4 trans-05">
								<div class="block1-link stext-101 cl0 trans-09"><fmt:message key="home.shopNow" bundle="${msgs}" /></div>
							</div>
						</a>
					</div>
				</div>

				<div class="col-md-6 col-xl-4 p-b-30 m-lr-auto">
					<div class="block1 wrap-pic-w">
						<img src="${pageContext.request.contextPath}/images/banner-03.jpg" alt="IMG-BANNER">
						<a href="product" class="block1-txt ab-t-l s-full flex-col-l-sb p-lr-38 p-tb-34 trans-03 respon3">
							<div class="block1-txt-child1 flex-col-l">
								<span class="block1-name ltext-102 trans-04 p-b-8"><fmt:message key="category.accessories" bundle="${msgs}" /></span>
								<span class="block1-info stext-102 trans-04">New Trend</span>
							</div>
							<div class="block1-txt-child2 p-b-4 trans-05">
								<div class="block1-link stext-101 cl0 trans-09"><fmt:message key="home.shopNow" bundle="${msgs}" /></div>
							</div>
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>


	<section class="bg0 p-t-23 p-b-140">
		<div class="container">
			<div class="p-b-10">
				<h3 class="ltext-103 cl5">
					<c:choose>
						<c:when test="${sessionScope.lang == 'en'}">Product Overview</c:when>
						<c:otherwise>Tổng quan Sản phẩm</c:otherwise>
					</c:choose>
				</h3>
			</div>

			<div class="flex-w flex-sb-m p-b-52">
				<div class="flex-w flex-l-m filter-tope-group m-tb-10">
					<a href="home" class="stext-106 cl6 hov1 bor3 trans-04 m-r-32 m-tb-5 ${tag == null ? 'how-active1' : ''}">
						<fmt:message key="product.allProducts" bundle="${msgs}" />
					</a>

					<c:forEach items="${listCategory}" var="c">
						<a href="category?cid=${c.id}" class="stext-106 cl6 hov1 bor3 trans-04 m-r-32 m-tb-5 ${tag == c.id ? 'how-active1' : ''}">
							${sessionScope.lang == 'en' ? c.nameEn : c.name}
						</a>
					</c:forEach>
				</div>
				
				<div class="flex-w flex-c-m m-tb-10">
					<div class="flex-c-m stext-106 cl6 size-104 bor4 pointer hov-btn3 trans-04 m-r-8 m-tb-4 js-show-filter">
						<i class="icon-filter cl2 m-r-6 fs-15 trans-04 zmdi zmdi-filter-list"></i>
						<i class="icon-close-filter cl2 m-r-6 fs-15 trans-04 zmdi zmdi-close dis-none"></i>
						 <fmt:message key="button.filter" bundle="${msgs}" />
					</div>
					<div class="flex-c-m stext-106 cl6 size-105 bor4 pointer hov-btn3 trans-04 m-tb-4 js-show-search">
						<i class="icon-search cl2 m-r-6 fs-15 trans-04 zmdi zmdi-search"></i>
						<i class="icon-close-search cl2 m-r-6 fs-15 trans-04 zmdi zmdi-close dis-none"></i>
						<fmt:message key="button.search" bundle="${msgs}" />
					</div>
				</div>
				
<div class="dis-none panel-search w-full p-t-10 p-b-15">
    <div class="bor8 dis-flex p-l-15">
        <button class="size-113 flex-c-m fs-16 cl2 hov-cl1 trans-04">
            <i class="zmdi zmdi-search"></i>
        </button>

        <%-- THÊM id="search-input" VÀO ĐÂY --%>
        <input class="mtext-107 cl2 size-114 plh2 p-r-15" type="text" name="search-product" placeholder="<fmt:message key='header.searchPlaceholder' bundle='${msgs}' />" id="search-input" autocomplete="off">
    </div>

    <%-- THÊM KHUNG HIỂN THỊ KẾT QUẢ VÀO ĐÂY --%>
    <div class="search-result-container" id="search-results" style="display: none;">
        <ul class="header-cart-wrapitem w-full" id="search-result-list">
        </ul>
    </div>
</div>
			</div>
			<%-- Bắt đầu vùng Filter --%>
				<div class="dis-none panel-filter w-full p-t-10">
					<div class="wrap-filter flex-w bg6 w-full p-lr-40 p-t-27 p-lr-15-sm">

						<%-- Cột 1: Sắp xếp --%>
						<div class="filter-col1 p-r-15 p-b-27">
							<div class="mtext-102 cl2 p-b-15"><fmt:message key="product.sortBy" bundle="${msgs}" /></div>
							<ul>
								<li class="p-b-6"><a
									href="product?cid=${tag}&price=${price}&sort=default"
									class="filter-link stext-106 trans-04 ${sort == 'default' || sort == null ? 'filter-link-active' : ''}">
										<fmt:message key="product.default" bundle="${msgs}" /> </a></li>
								<li class="p-b-6"><a
									href="product?cid=${tag}&price=${price}&sort=newest"
									class="filter-link stext-106 trans-04 ${sort == 'newest' ? 'filter-link-active' : ''}">
										<c:choose><c:when test="${sessionScope.lang == 'en'}">Newest</c:when><c:otherwise>Mới nhất</c:otherwise></c:choose> </a></li>
								<li class="p-b-6"><a
									href="product?cid=${tag}&price=${price}&sort=price_asc"
									class="filter-link stext-106 trans-04 ${sort == 'price_asc' ? 'filter-link-active' : ''}">
										<fmt:message key="product.priceAsc" bundle="${msgs}" /> </a></li>
								<li class="p-b-6"><a
									href="product?cid=${tag}&price=${price}&sort=price_desc"
									class="filter-link stext-106 trans-04 ${sort == 'price_desc' ? 'filter-link-active' : ''}">
										<fmt:message key="product.priceDesc" bundle="${msgs}" /> </a></li>
							</ul>
						</div>

						<%-- Cột 2: Lọc Giá --%>
						<div class="filter-col2 p-r-15 p-b-27">
							<div class="mtext-102 cl2 p-b-15"><fmt:message key="product.price" bundle="${msgs}" /></div>
							<ul>
								<li class="p-b-6"><a
									href="product?cid=${tag}&sort=${sort}&price=all"
									class="filter-link stext-106 trans-04 ${price == 'all' || price == null ? 'filter-link-active' : ''}">
										<fmt:message key="product.allPrices" bundle="${msgs}" /> </a></li>
								<li class="p-b-6"><a
									href="product?cid=${tag}&sort=${sort}&price=0-200000"
									class="filter-link stext-106 trans-04 ${price == '0-200000' ? 'filter-link-active' : ''}">
										0 - 200.000 VND </a></li>
								<li class="p-b-6"><a
									href="product?cid=${tag}&sort=${sort}&price=200000-500000"
									class="filter-link stext-106 trans-04 ${price == '200000-500000' ? 'filter-link-active' : ''}">
										200.000 - 500.000 VND </a></li>
								<li class="p-b-6"><a
									href="product?cid=${tag}&sort=${sort}&price=500000-1000000"
									class="filter-link stext-106 trans-04 ${price == '500000-1000000' ? 'filter-link-active' : ''}">
										500.000 - 1.000.000 VND </a></li>
								<li class="p-b-6"><a
									href="product?cid=${tag}&sort=${sort}&price=1000000-2000000"
									class="filter-link stext-106 trans-04 ${price == '1000000-2000000' ? 'filter-link-active' : ''}">
										1.000.000 - 2.000.000 VND </a></li>
								<li class="p-b-6"><a
									href="product?cid=${tag}&sort=${sort}&price=2000000-"
									class="filter-link stext-106 trans-04 ${price == '2000000-' ? 'filter-link-active' : ''}">
										<c:choose><c:when test="${sessionScope.lang == 'en'}">Over 2,000,000 VND</c:when><c:otherwise>Trên 2.000.000 VND</c:otherwise></c:choose> </a></li>
							</ul>
						</div>
					</div>
				</div>
				<%-- Kết thúc vùng Filter --%>			

			<div class="row isotope-grid">
				<c:forEach items="${listProduct}" var="p">
					<div class="col-sm-6 col-md-4 col-lg-3 p-b-35 isotope-item women">
						<div class="block2">
							<div class="block2-pic hov-img0">
								<img src="${pageContext.request.contextPath}/images/${p.imageUrl}" class="card-img-top product-img-fixed" alt="${lang == 'en' ? p.nameEn : p.name}">
										
								<a href="#" 
								   class="block2-btn flex-c-m stext-103 cl2 size-102 bg0 bor2 hov-btn1 p-lr-15 trans-04 js-show-modal1"
								   data-id="${p.id}"
								   data-name="${p.name}"
   data-name-en="${p.nameEn}"
								   data-price="${p.price}"
								   data-desc="${p.description}"
   data-desc-en="${p.descriptionEn}"
								   data-img="${pageContext.request.contextPath}/images/${p.imageUrl}">
									<fmt:message key="home.quickView" bundle="${msgs}" />
								</a>
							</div>

							<div class="block2-txt flex-w flex-t p-t-14">
								<div class="block2-txt-child1 flex-col-l ">
									<a href="detail?pid=${p.id}" class="stext-104 cl4 hov-cl1 trans-04 js-name-b2 p-b-6">
										${lang == 'en' ? p.nameEn : p.name}
									</a>

									<span class="stext-105 cl3">
										<fmt:formatNumber value="${p.price}" type="number" maxFractionDigits="0"/> VND
									</span>
								</div>

								<div class="block2-txt-child2 flex-r p-t-3">
									<a href="#" class="btn-addwish-b2 dis-block pos-relative js-addwish-b2">
										<img class="icon-heart1 dis-block trans-04" src="${pageContext.request.contextPath}/images/icons/icon-heart-01.png" alt="ICON">
										<img class="icon-heart2 dis-block trans-04 ab-t-l" src="${pageContext.request.contextPath}/images/icons/icon-heart-02.png" alt="ICON">
									</a>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>

<div class="flex-c-m flex-w w-full p-t-45">
    <c:if test="${endPage > 1}">
        <c:forEach begin="1" end="${endPage}" var="i">
            <c:choose>
                <c:when test="${not empty tag}">
                    <a href="home?cid=${tag}&page=${i}" 
                       class="flex-c-m how-pagination1 trans-04 m-all-7 ${currentPage == i ? 'active-pagination1' : ''}">
                        ${i}
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="home?page=${i}" 
                       class="flex-c-m how-pagination1 trans-04 m-all-7 ${currentPage == i ? 'active-pagination1' : ''}">
                        ${i}
                    </a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:if>
</div>
		</div>
	</section>


	<jsp:include page="footer.jsp" />


	<div class="btn-back-to-top" id="myBtn">
		<span class="symbol-btn-back-to-top">
			<i class="zmdi zmdi-chevron-up"></i>
		</span>
	</div>

<div class="wrap-modal1 js-modal1 p-t-60 p-b-20">
    <div class="overlay-modal1 js-hide-modal1"></div>
    <div class="container">
        <div class="bg0 p-t-60 p-b-30 p-lr-15-lg how-pos3-parent">
            <button class="how-pos3 hov3 trans-04 js-hide-modal1">
                <img src="${pageContext.request.contextPath}/images/icons/icon-close.png" alt="CLOSE">
            </button>
            <div class="row">
                <div class="col-md-6 col-lg-7 p-b-30">
                    <div class="p-l-25 p-r-30 p-lr-0-lg">
                        <div class="wrap-slick3 flex-sb flex-w">
                            <div class="wrap-slick3-dots"></div>
                            <div class="wrap-slick3-arrows flex-sb-m flex-w"></div>
                            <div class="slick3 gallery-lb">
                                <div class="item-slick3" data-thumb="">
                                    <div class="wrap-pic-w pos-relative">
                                        <img class="modal-img" src="" alt="IMG-PRODUCT">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-lg-5 p-b-30">
                    <div class="p-r-50 p-t-5 p-lr-0-lg">
                        <h4 class="mtext-105 cl2 js-name-detail p-b-14 modal-name">Product Name</h4>
                        <span class="mtext-106 cl2 modal-price">0 VND</span>
                        <p class="stext-102 cl3 p-t-23 modal-desc">Description</p>
                        
                        <form action="cart" method="post" class="p-t-33">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="id" class="modal-id" value="">
                            
                            <div class="flex-w flex-r-m p-b-10">
                                <div class="size-203 flex-c-m respon6"><fmt:message key="product.size" bundle="${msgs}" /></div>
                                <div class="size-204 respon6-next">
                                    <div class="rs1-select2 bor8 bg0">
                                        <select class="js-select2" name="size" id="modal-size-select" required>
                                            <option><c:choose><c:when test="${sessionScope.lang == 'en'}">Loading...</c:when><c:otherwise>Đang tải...</c:otherwise></c:choose></option>
                                        </select>
                                        <div class="dropDownSelect2"></div>
                                    </div>
                                </div>
                            </div>

                            <div class="flex-w flex-r-m p-b-10">
                                <div class="size-204 flex-w flex-m respon6-next">
                                    <div class="wrap-num-product flex-w m-r-20 m-tb-10">
                                        <div class="btn-num-product-down cl8 hov-btn3 trans-04 flex-c-m"><i class="fs-16 zmdi zmdi-minus"></i></div>
                                        
                                        <%-- THÊM ID: modal-num-product --%>
                                        <input class="mtext-104 cl3 txt-center num-product" type="number" name="quantity" value="1" id="modal-num-product">
                                        
                                        <div class="btn-num-product-up cl8 hov-btn3 trans-04 flex-c-m"><i class="fs-16 zmdi zmdi-plus"></i></div>
                                    </div>
                                    <button class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04 js-addcart-detail">
                                        <fmt:message key="product.addToCart" bundle="${msgs}" />
                                    </button>
                                </div>
                            </div>  
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

	<script src="${pageContext.request.contextPath}/vendor/jquery/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/vendor/animsition/js/animsition.min.js"></script>
	<script src="${pageContext.request.contextPath}/vendor/bootstrap/js/popper.js"></script>
	<script src="${pageContext.request.contextPath}/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/vendor/select2/select2.min.js"></script>
	<script>
		$(".js-select2").each(function(){
			$(this).select2({
				minimumResultsForSearch: 20,
				dropdownParent: $(this).next('.dropDownSelect2')
			});
		})
	</script>
	<script src="${pageContext.request.contextPath}/vendor/daterangepicker/moment.min.js"></script>
	<script src="${pageContext.request.contextPath}/vendor/daterangepicker/daterangepicker.js"></script>
	<script src="${pageContext.request.contextPath}/vendor/slick/slick.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/slick-custom.js"></script>
	<script src="${pageContext.request.contextPath}/vendor/parallax100/parallax100.js"></script>
	<script>
        $('.parallax100').parallax100();
	</script>
	<script src="${pageContext.request.contextPath}/vendor/MagnificPopup/jquery.magnific-popup.min.js"></script>
	<script>
		$('.gallery-lb').each(function() {
			$(this).magnificPopup({
		        delegate: 'a',
		        type: 'image',
		        gallery: {enabled:true},
		        mainClass: 'mfp-fade'
		    });
		});
	</script>
	<script src="${pageContext.request.contextPath}/vendor/isotope/isotope.pkgd.min.js"></script>
	<script src="${pageContext.request.contextPath}/vendor/sweetalert/sweetalert.min.js"></script>
	<script>
		$('.js-addwish-b2').on('click', function(e){
			e.preventDefault();
		});

		$('.js-addwish-b2').each(function(){
			var nameProduct = $(this).parent().parent().find('.js-name-b2').html();
			$(this).on('click', function(){
				swal(nameProduct, "is added to wishlist !", "success");
				$(this).addClass('js-addedwish-b2');
				$(this).off('click');
			});
		});

		$('.js-addwish-detail').each(function(){
			var nameProduct = $(this).parent().parent().parent().find('.js-name-detail').html();
			$(this).on('click', function(){
				swal(nameProduct, "is added to wishlist !", "success");
				$(this).addClass('js-addedwish-detail');
				$(this).off('click');
			});
		});

		$('.js-addcart-detail').each(function(){
			var nameProduct = $(this).parent().parent().parent().parent().find('.js-name-detail').html();
			$(this).on('click', function(){
				swal(nameProduct, "is added to cart !", "success");
			});
		});
	</script>
	<script src="${pageContext.request.contextPath}/vendor/perfect-scrollbar/perfect-scrollbar.min.js"></script>
	<script>
		$('.js-pscroll').each(function(){
			$(this).css('position','relative');
			$(this).css('overflow','hidden');
			var ps = new PerfectScrollbar(this, {
				wheelSpeed: 1,
				scrollingThreshold: 1000,
				wheelPropagation: false,
			});
			$(window).on('resize', function(){
				ps.update();
			})
		});
	</script>
	<script src="${pageContext.request.contextPath}/js/main.js"></script>

	<%-- SCRIPT XỬ LÝ QUICK VIEW --%>
	<script>
	var lang = '${sessionScope.lang}' || 'vi';
	
    $('.js-show-modal1').on('click', function(e){
        e.preventDefault();
        var id = $(this).data('id');
        var name = $(this).data('name');
        var nameEn = $(this).data('name-en');
        var price = $(this).data('price');
        var desc = $(this).data('desc');
        var descEn = $(this).data('desc-en');
        var img = $(this).data('img'); 
        
        // Display localized name and description
        $('.modal-name').text(lang === 'en' ? nameEn : name);
        $('.modal-desc').text(lang === 'en' ? descEn : desc);
        $('.modal-id').val(id); 
        
        var formattedPrice = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
        $('.modal-price').text(formattedPrice);
        
        $('.modal-img').attr('src', img);
        $('.item-slick3').find('div.wrap-pic-w img').attr('src', img);
        $('.item-slick3').attr('data-thumb', img);

        $.ajax({
            url: "product-sizes", 
            type: "GET",
            data: { id: id },
            success: function(data) {
                $("#modal-size-select").html(data);
                $("#modal-num-product").val(1).removeAttr('max');
            },
            error: function() {
                $("#modal-size-select").html("<option>Lỗi tải size</option>");
            }
        });
    });

    $('.js-modal1').on('shown.bs.modal', function () {
        $('.slick3').slick('setPosition');
    });

    $('#modal-size-select').on('change', function() {
        var maxStock = $(this).find(':selected').data('max');
        var inputQuantity = $('#modal-num-product');

        if (maxStock) {
            inputQuantity.attr('max', maxStock);
            if (parseInt(inputQuantity.val()) > maxStock) {
                inputQuantity.val(maxStock);
            }
        } else {
            inputQuantity.removeAttr('max');
        }
    });

    $('.wrap-modal1 .btn-num-product-up').off('click');
    $('.wrap-modal1 .btn-num-product-down').off('click');

    $('.wrap-modal1 .btn-num-product-up').on('click', function() {
        var input = $(this).prev(); 
        var numProduct = Number(input.val());
        var maxStock = parseInt(input.attr('max'));

        if (isNaN(maxStock) || numProduct < maxStock) {
            input.val(numProduct + 1);
        } 
    });

    $('.wrap-modal1 .btn-num-product-down').on('click', function() {
        var input = $(this).next();
        var numProduct = Number(input.val());
        if(numProduct > 1) {
            input.val(numProduct - 1);
        }
    });
    
    $('#modal-num-product').on('change keyup', function() {
        var max = parseInt($(this).attr('max'));
        var val = parseInt($(this).val());
        
        if (val < 1) $(this).val(1);
        if (max && val > max) {
            $(this).val(max);
        }
    });
</script>

<style>				
    .search-result-container {
        background: white;
        border: 1px solid #e6e6e6;
        position: absolute; 
        z-index: 9999;
        width: 100%;
        max-width: 1170px; 
        max-height: 400px;
        overflow-y: auto;
        box-shadow: 0 5px 10px rgba(0,0,0,0.1);
        padding: 15px;
        margin-top: 5px;
    }

    .header-cart-item-img {
        width: 60px;
        margin-right: 20px;
    }
</style>

<script>
    $(document).ready(function(){
        var timeout = null; 

        $("#search-input").on("input", function() {
            var txt = $(this).val().trim();
            
            clearTimeout(timeout);

            timeout = setTimeout(function() {
                
                if (txt === "") {
                    $("#search-results").fadeOut();
                    return;
                }

                $.ajax({
                    url: "search", 
                    type: "GET",
                    data: { txt: txt },
                    success: function(data) {
                        $("#search-result-list").html(data);
                        $("#search-results").fadeIn();
                    },
                    error: function(xhr) {
                        console.log("Lỗi tìm kiếm");
                    }
                });

            }, 500); 
        });

        $(document).on("click", function(e) {
            if (!$(e.target).closest(".panel-search").length) {
                $("#search-results").fadeOut();
            }
        });
    });
</script>
</body>
</html>