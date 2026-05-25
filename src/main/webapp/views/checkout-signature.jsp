<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ký xác nhận đơn hàng</title>
    
    <link rel="icon" type="image/png" href="images/icons/favicon.png" />
    <link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="fonts/iconic/css/material-design-iconic-font.min.css">
    <link rel="stylesheet" type="text/css" href="fonts/linearicons-v1.0.0/icon-font.min.css">
    <link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
    <link rel="stylesheet" type="text/css" href="vendor/css-hamburgers/hamburgers.min.css">
    <link rel="stylesheet" type="text/css" href="vendor/animsition/css/animsition.min.css">
    <link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
    <link rel="stylesheet" type="text/css" href="vendor/perfect-scrollbar/perfect-scrollbar.css">
    <link rel="stylesheet" type="text/css" href="css/util.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    </head>
<body class="animsition">

    <jsp:include page="header.jsp" />

    <div class="container m-t-140 m-b-100">
        <div class="row">
            <div class="col-md-8 mx-auto">
                <div class="p-t-30 p-b-40 p-l-40 p-r-40 bor10 shadow-sm bg0" style="border: 2px solid #28a745;">
                    <h4 class="mtext-111 cl2 p-b-20 text-center" style="color: #28a745;">
                        ĐẶT HÀNG THÀNH CÔNG - CHỜ KÝ XÁC NHẬN
                    </h4>
                    
                    <p class="stext-111 cl6 p-b-20 text-center">
                        Mã đơn hàng: <strong>#${orderId}</strong> <br/>
                        Tổng tiền: <strong><fmt:formatNumber value="${totalMoney}" type="currency" currencySymbol="₫" /></strong>
                    </p>

                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger" role="alert">
                            ${errorMessage}
                        </div>
                    </c:if>

                    <div class="bg0 p-t-20 p-b-20">
                        <h5 class="mtext-111 cl2 p-b-10">BƯỚC 1: LẤY MÃ BĂM</h5>
                        <p class="stext-111 cl6 p-b-10">Mã băm dưới đây đại diện duy nhất cho đơn hàng này. Không thể thay đổi.</p>
                        
                        <div class="input-group m-b-20">
                            <input type="text" id="orderHash" class="form-control" value="${orderHash}" readonly style="font-family: monospace;">
                            <div class="input-group-append">
                                <button class="btn btn-outline-secondary" type="button" onclick="copyHash()">Copy Mã Băm</button>
                            </div>
                        </div>

                        <h5 class="mtext-111 cl2 p-t-20 p-b-10">BƯỚC 2: KÝ XÁC NHẬN</h5>
                        <p class="stext-111 cl6 p-b-10">
                            Hãy mở ứng dụng <strong>Tool Ký Số</strong> ở máy tính của bạn. Dán mã băm trên vào ô nhập, chọn file Private Key và bấm <strong>KÝ ĐƠN HÀNG</strong>.
                            <br/><br/>
                            <span class="text-danger">Bạn chưa có Tool Ký Số cho máy tính?</span> 
                            <a href="${pageContext.request.contextPath}/tools/SignatureTool.zip" class="cl2 font-weight-bold text-underline" download>
                                Tải Công Cụ Tại Đây
                            </a>
                        </p>

                        <hr/>

                        <h5 class="mtext-111 cl2 p-t-10 p-b-10">BƯỚC 3: NỘP CHỮ KÝ SỐ</h5>
                        <form action="submit-signature" method="post">
                            <input type="hidden" name="orderId" value="${orderId}">
                            <input type="hidden" name="orderHash" value="${orderHash}">
                            
                            <div class="form-group">
                                <label class="stext-111 cl2">Dán chữ ký số thu được từ Tool vào đây:</label>
                                <textarea name="signature" class="form-control" rows="5" required style="font-family: monospace;" placeholder="Vd: Xj1s8R/9..."></textarea>
                            </div>
                            
                            <button type="submit" class="flex-c-m stext-101 cl0 size-116 bg3 bor14 hov-btn3 p-lr-15 trans-04 pointer mt-4">
                                GỬI CHỮ KÝ
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
    
    <script src="vendor/jquery/jquery-3.2.1.min.js"></script>
    <script src="vendor/animsition/js/animsition.min.js"></script>
    <script src="vendor/bootstrap/js/popper.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="vendor/select2/select2.min.js"></script>
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
        function copyHash() {
            var copyText = document.getElementById("orderHash");
            copyText.select();
            copyText.setSelectionRange(0, 99999);
            document.execCommand("copy");
            alert("Đã copy mã băm: " + copyText.value);
        }
    </script>
</body>
</html>