<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="messages" var="msgs" />
<c:set var="lang" value="${sessionScope.lang != null ? sessionScope.lang : 'vi'}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="order.history" bundle="${msgs}" /></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <jsp:include page="header.jsp" />
    
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
    
    <style>
        .order-status {
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 13px;
            font-weight: 600;
        }
        /* Logic màu sắc dựa trên text tiếng Việt từ DB */
        .status-pending { background-color: #ffeaa7; color: #d35400; } 
        .status-shipping { background-color: #81ecec; color: #00cec9; } 
        .status-success { background-color: #55efc4; color: #00b894; }  
        .status-cancel { background-color: #fab1a0; color: #d63031; }  
    </style>
</head>
<body class="animsition">

    <div class="container">
        <div class="bread-crumb flex-w p-l-25 p-r-15 p-t-30 p-lr-0-lg">
            <a href="home" class="stext-109 cl8 hov-cl1 trans-04">
                <fmt:message key="breadcrumb.home" bundle="${msgs}" />
                <i class="fa fa-angle-right m-l-9 m-r-10" aria-hidden="true"></i>
            </a>
            <span class="stext-109 cl4">
                <fmt:message key="order.history" bundle="${msgs}" />
            </span>
        </div>
    </div>

    <div class="bg0 p-t-75 p-b-85">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 m-lr-auto m-b-50">
                    <div class="m-l-25 m-r--38 m-lr-0-xl">
                        <h4 class="mtext-109 cl2 p-b-30">
                            <fmt:message key="order.history" bundle="${msgs}" />
                        </h4>
                        
                        <c:if test="${empty listOrders}">
                            <div class="alert alert-info text-center">
                                <fmt:message key="order.noOrders" bundle="${msgs}" />. 
                                <a href="product">
                                    <fmt:message key="cart.continueShopping" bundle="${msgs}" />!
                                </a>
                            </div>
                        </c:if>

                        <c:if test="${not empty listOrders}">
                            <div class="wrap-table-shopping-cart">
                                <table class="table-shopping-cart" style="min-width: 100%;">
                                    <tr class="table_head">
                                        <th class="column-1 p-l-30"><fmt:message key="order.orderID" bundle="${msgs}" /></th>
                                        <th class="column-2"><fmt:message key="order.date" bundle="${msgs}" /></th>
                                        <th class="column-3"><fmt:message key="checkout.address" bundle="${msgs}" /></th>
                                        <th class="column-4"><fmt:message key="order.total" bundle="${msgs}" /></th>
                                        <th class="column-5"><fmt:message key="order.status" bundle="${msgs}" /></th>
                                        <th class="column-5 text-center"><fmt:message key="admin.details" bundle="${msgs}" /></th>
                                    </tr>

                                    <c:forEach items="${listOrders}" var="o">
                                        <tr class="table_row">
                                            <td class="column-1 p-l-30">
                                                <strong>#${o.id}</strong>
                                            </td>
                                            <td class="column-2">
                                                <fmt:formatDate value="${o.orderDate}" pattern="dd-MM-yyyy HH:mm"/>
                                            </td>
                                            <td class="column-3" style="max-width: 300px;">
                                                ${o.shippingAddress}
                                            </td>
                                            <td class="column-4" style="color: #c0392b; font-weight: bold;">
                                                <fmt:formatNumber value="${o.totalMoney}" type="number" maxFractionDigits="0"/> 
                                                ${lang == 'en' ? '$' : 'đ'}
                                            </td>
                                            <td class="column-5">
                                                <span class="order-status 
                                                    ${o.status == 'Chờ xử lý' ? 'status-pending' : ''}
                                                    ${o.status == 'Đang giao' ? 'status-shipping' : ''}
                                                    ${o.status == 'Đã giao' ? 'status-success' : ''}
                                                    ${o.status == 'Đã hủy' ? 'status-cancel' : ''}">
                                                    ${o.status}
                                                </span>
                                            </td>
                                             <td class="column-5 text-center">
                                                <button class="stext-104 cl2 hov-cl1 trans-04 js-show-detail" data-id="${o.id}">
                                                    <i class="zmdi zmdi-eye" style="font-size: 20px;"></i>
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="wrap-modal1 js-modal-detail p-t-60 p-b-20">
        <div class="overlay-modal1 js-hide-detail"></div>
        <div class="container">
            <div class="bg0 p-t-60 p-b-30 p-lr-15-lg how-pos3-parent" style="max-width: 900px; margin: auto;">
                <button class="how-pos3 hov3 trans-04 js-hide-detail">
                    <img src="images/icons/icon-close.png" alt="CLOSE">
                </button>

                <div class="p-l-25 p-r-30 p-lr-0-lg">
                    <h4 class="mtext-105 cl2 p-b-14">
                        <fmt:message key="order.viewDetails" bundle="${msgs}" /> #<span id="modal-order-id"></span>
                    </h4>
                    
                    <div class="wrap-table-shopping-cart">
                        <table class="table-shopping-cart">
                            <tr class="table_head">
                                <th class="column-1"><fmt:message key="cart.product" bundle="${msgs}" /></th>
                                <th class="column-2"><fmt:message key="admin.details" bundle="${msgs}" /></th>
                                <th class="column-3"><fmt:message key="cart.price" bundle="${msgs}" /></th>
                                <th class="column-4 text-center"><fmt:message key="cart.quantity" bundle="${msgs}" /></th>
                                <th class="column-5"><fmt:message key="cart.total" bundle="${msgs}" /></th>
                            </tr>
                            <tbody id="order-detail-content"></tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />

    <script src="vendor/jquery/jquery-3.2.1.min.js"></script>
    <script src="vendor/animsition/js/animsition.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="js/main.js"></script>
    
    <script>
        // Khi nhấn nút mắt 
        $('.js-show-detail').on('click', function(e){
            e.preventDefault();
            var orderId = $(this).data('id');
            
            // Set ID lên tiêu đề
            $('#modal-order-id').text(orderId);
            // Dịch text loading
            var loadingText = '${lang == "en" ? "Loading data..." : "Đang tải dữ liệu..."}';
            $('#order-detail-content').html('<tr><td colspan="5" class="text-center p-3">' + loadingText + '</td></tr>');

            $.ajax({
                url: "order-details",
                type: "GET",
                data: { id: orderId },
                success: function(response) {
                    $('#order-detail-content').html(response);
                },
                error: function() {
                    var errorText = '${lang == "en" ? "Error loading data!" : "Lỗi tải dữ liệu!"}';
                    $('#order-detail-content').html('<tr><td colspan="5" class="text-center text-danger">' + errorText + '</td></tr>');
                }
            });

            // Hiện Modal
            $('.js-modal-detail').addClass('show-modal1');
        });

        // Ẩn Modal
        $('.js-hide-detail').on('click', function(){
            $('.js-modal-detail').removeClass('show-modal1');
        });
    </script>

</body>
</html>