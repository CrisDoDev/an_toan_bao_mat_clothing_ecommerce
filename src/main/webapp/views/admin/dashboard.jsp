<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="layout-admin.jsp" %>

    <div class="row">
        <div class="col-md-4">
            <div class="card text-white bg-primary mb-3">
                <div class="card-header">Sản phẩm</div>
                <div class="card-body">
                    <h5 class="card-title">Quản lý kho hàng</h5>
                    <p class="card-text">Xem, thêm, sửa, xóa sản phẩm.</p>
                    <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-light btn-sm">Chi tiết</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card text-white bg-success mb-3">
                <div class="card-header">Đơn hàng</div>
                <div class="card-body">
                    <h5 class="card-title">Đơn hàng mới</h5>
                    <p class="card-text">Duyệt và xử lý đơn đặt hàng.</p>
                    <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-light btn-sm">Chi tiết</a>
                </div>
            </div>
        </div>
    </div>

</div> </div> </div> <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>