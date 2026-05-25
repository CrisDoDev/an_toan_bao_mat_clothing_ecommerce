<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý khóa</title>
    
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
            <div class="col-md-10 mx-auto">
                <h3 class="mtext-111 cl2 p-b-20">QUẢN LÝ PUBLIC KEY</h3>

                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success">${successMessage}</div>
                </c:if>

                 <div class="alert alert-info mb-4">
                    <div>
                        <strong>Chưa có công cụ tạo khóa?</strong> Hãy 
                        <a href="${pageContext.request.contextPath}/tools/SignatureTool.zip" class="text-primary font-weight-bold" download>
                           Tải Tool Ký Số (Windows/Mac)
                        </a> 
                        để sinh cặp khóa an toàn trên máy tính cá nhân của bạn!
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-7">
                        <div class="bor10 p-t-30 p-b-40 p-l-30 p-r-30 m-b-30 bg0">
                            <h4 class="mtext-109 cl2 p-b-20">Cập nhật Public Key Mới</h4>
                            <form action="user-key" method="post">
                                <input type="hidden" name="action" value="upload">
                                <div class="form-group">
                                    <label class="stext-111 cl6">Dán chuỗi Public Key tạo từ Tool Ký Số:</label>
                                    <textarea name="publicKey" class="form-control" rows="8" required style="font-family: monospace;" placeholder="MIGfMA0GC..."></textarea>
                                </div>
                                <button type="submit" class="flex-c-m stext-101 cl0 size-112 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer">
                                    CẬP NHẬT PUBLIC KEY
                                </button>
                            </form>
                        </div>
                    </div>

                    <div class="col-md-5">
                        <div class="bor10 p-t-30 p-b-40 p-l-30 p-r-30 m-b-30 bg-light">
                            <h4 class="mtext-109 cl2 p-b-20 text-danger">Báo Mất Khóa</h4>
                            <p class="stext-111 cl6 p-b-20">Nếu bạn bị lộ Private Key hoặc mất máy tính, hãy nhấn nút BÁO MẤT KHÓA ngay lập tức để hệ thống vô hiệu hóa tính năng ký trên tất cả đơn hàng mới!</p>
                            
                            <c:if test="${hasActiveKey}">
                                <form action="user-key" method="post">
                                    <input type="hidden" name="action" value="revoke">
                                    <button type="submit" class="flex-c-m stext-101 cl0 size-112 bg-danger bor1 hov-btn3 p-lr-15 trans-04 pointer" onclick="return confirm('Bạn có chắc chắn muốn vô hiệu hóa khóa hiện tại không?')">
                                        BÁO MẤT KHÓA
                                    </button>
                                </form>
                            </c:if>
                            <c:if test="${!hasActiveKey}">
                                <button class="flex-c-m stext-101 cl0 size-112 bg-secondary bor1 p-lr-15 trans-04" disabled>
                                    KHÔNG CÓ KHÓA HOẠT ĐỘNG
                                </button>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="bor10 p-t-30 p-b-40 p-l-30 p-r-30 bg0">
                    <h4 class="mtext-109 cl2 p-b-20">Lịch Sử Public Key</h4>
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <thead class="thead-light">
                                <tr>
                                    <th>ID</th>
                                    <th>Mã Khóa (Rút gọn)</th>
                                    <th>Ngày Tạo</th>
                                    <th>Trạng Thái</th>
                                    <th>Ngày Báo Mất</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="key" items="${userKeys}">
                                    <tr>
                                        <td>#${key.keyId}</td>
                                        <td style="font-family: monospace;" title="${key.publicKeyText}">
                                            ${key.publicKeyText.substring(0, 15)}...
                                        </td>
                                        <td><fmt:formatDate value="${key.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                                        <td>
                                            <c:if test="${key.status == 'ACTIVE'}">
                                                <span class="badge badge-success">HOẠT ĐỘNG</span>
                                            </c:if>
                                            <c:if test="${key.status == 'REVOKED'}">
                                                <span class="badge badge-danger">ĐÃ VÔ HIỆU HÓA</span>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${not empty key.revokedAt}">
                                                <fmt:formatDate value="${key.revokedAt}" pattern="dd/MM/yyyy HH:mm"/>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty userKeys}">
                                    <tr><td colspan="5" class="text-center">Chưa có khóa nào được nạp.</td></tr>
                                </c:if>
                            </tbody>
                        </table>
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

</body>
</html>