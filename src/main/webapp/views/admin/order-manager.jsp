<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="layout-admin.jsp"%>

<div class="container-fluid">
	<h3 class="mt-4 mb-3">Quản lý Đơn hàng</h3>

	<div class="card mb-4">
		<div class="card-body">
			<form action="orders" method="get" class="row g-3 align-items-center">
				<div class="col-auto">
					<label class="col-form-label">Trạng thái:</label>
				</div>
				<div class="col-auto">
					<select name="status" class="form-select">
						<option value="all">Tất cả</option>
						<option value="Đã xác nhận"
							${param.status == 'Đã xác nhận' ? 'selected' : ''}>Đã
							xác nhận</option>
						<option value="Đã hủy"
							${param.status == 'Đã hủy' ? 'selected' : ''}>Đã hủy</option>
					</select>
				</div>
				<div class="col-auto">
					<button type="submit" class="btn btn-primary">Lọc</button>
				</div>
			</form>
		</div>
	</div>

	<div class="table-responsive">
		<table class="table table-bordered table-hover align-middle">
			<thead class="table-dark">
				<tr>
					<th>Mã ĐH</th>
					<th>Ngày đặt</th>
					<th>Người nhận</th>
					<th>Tổng tiền</th>
					<th>Trạng thái</th>
					<th>Hành động</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listOrder}" var="o">
					<tr>
						<td><strong>#${o.id}</strong></td>
						<td><fmt:formatDate value="${o.orderDate}"
								pattern="dd/MM/yyyy HH:mm" /></td>
						<td>${o.shippingAddress}<br> <small class="text-muted">(User
								ID: ${o.userId})</small>
						</td>
						<td class="text-danger fw-bold"><fmt:formatNumber
								value="${o.totalMoney}" type="currency" currencySymbol="₫" /></td>
						<td><span
							class="badge ${o.status == 'Đã hủy' ? 'bg-danger' : 'bg-success'}">
								${o.status} </span></td>
						<td>
							<button class="btn btn-info btn-sm text-white"
								onclick="viewOrderDetails('${o.id}')" title="Xem chi tiết">
								<i class="fas fa-eye"></i>
							</button> <c:choose>
								<c:when test="${o.status == 'Đã hủy'}">
									<button class="btn btn-secondary btn-sm" disabled
										title="Đơn hàng đã hủy không thể cập nhật">
										<i class="fas fa-ban"></i>
									</button>
								</c:when>
								<c:otherwise>
									<button class="btn btn-primary btn-sm" data-bs-toggle="modal"
										data-bs-target="#updateStatusModal"
										onclick="setUpdateModal('${o.id}', '${o.status}')"
										title="Cập nhật trạng thái">
										<i class="fas fa-edit"></i>
									</button>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="modal fade" id="updateStatusModal" tabindex="-1"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form action="orders" method="post">
				<div class="modal-header">
					<h5 class="modal-title">Cập nhật trạng thái đơn hàng</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<input type="hidden" name="action" value="updateStatus"> <input
						type="hidden" name="orderId" id="update_order_id">

					<div class="mb-3">
						<label class="form-label">Trạng thái mới</label> <select
							name="status" id="update_status" class="form-select">
							<option value="Đã xác nhận">Đã xác nhận</option>
							<option value="Đã hủy">Đã hủy</option>
						</select>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Đóng</button>
					<button type="submit" class="btn btn-primary">Lưu thay đổi</button>
				</div>
			</form>
		</div>
	</div>
</div>

<div class="modal fade" id="orderDetailModal" tabindex="-1"
	aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header bg-info text-white">
				<h5 class="modal-title">
					Chi tiết đơn hàng #<span id="detail_order_id"></span>
				</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<div id="loading-spinner" class="text-center py-3">
					<div class="spinner-border text-primary" role="status">
						<span class="visually-hidden">Loading...</span>
					</div>
					<p>Đang tải dữ liệu...</p>
				</div>

				<div class="table-responsive" id="detail-content"
					style="display: none;">
					<table class="table table-bordered align-middle">
						<thead>
							<tr>
								<th>Sản phẩm</th>
								<th>Phân loại</th>
								<th>Đơn giá</th>
								<th>Số lượng</th>
								<th>Thành tiền</th>
							</tr>
						</thead>
						<tbody id="order-details-body">
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">Đóng</button>
			</div>
		</div>
	</div>
</div>

<script>
    // 1. Khai báo contextPath dùng chung
    var contextPath = '${pageContext.request.contextPath}';

    // Hàm mở Modal cập nhật trạng thái
    function setUpdateModal(id, status) {
        document.getElementById('update_order_id').value = id;
        document.getElementById('update_status').value = status;
    }

    // Hàm mở Modal xem chi tiết (AJAX)
    function viewOrderDetails(orderId) {
        var myModal = new bootstrap.Modal(document.getElementById('orderDetailModal'));
        myModal.show();
        
        document.getElementById('detail_order_id').innerText = orderId;
        document.getElementById('loading-spinner').style.display = 'block';
        document.getElementById('detail-content').style.display = 'none';
        document.getElementById('order-details-body').innerHTML = '';

        fetch(contextPath + '/admin/orders?action=view_detail&id=' + orderId)
            .then(response => {
                if (!response.ok) throw new Error("Lỗi kết nối server");
                return response.json(); 
            })
            .then(data => {
                document.getElementById('loading-spinner').style.display = 'none';
                document.getElementById('detail-content').style.display = 'block';

                const tbody = document.getElementById('order-details-body');
                let html = '';
                
                data.forEach(item => {
                    let subtotal = item.price * item.quantity;
                    let priceStr = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(item.price);
                    let subtotalStr = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(subtotal);

                    // Sử dụng Template Literals (Backticks) để viết HTML clean hơn
                    html += `
                        <tr>
                            <td>
                                <div class="d-flex align-items-center">
                                    <img src="\${contextPath}/images/\${item.productImage}" 
                                         style="width: 50px; height: 50px; object-fit: cover; border-radius: 5px; margin-right: 10px;"
                                         onerror="this.src='\${contextPath}/images/no-image.png'"> 
                                    <span>\${item.productName || ''}</span>
                                </div>
                            </td>
                            <td>\${item.sizeName || 'N/A'}</td>
                            <td>\${priceStr}</td>
                            <td class="text-center">\${item.quantity}</td>
                            <td class="text-danger fw-bold">\${subtotalStr}</td>
                        </tr>
                    `;
                });
                
                tbody.innerHTML = html;
            })
            .catch(error => {
                console.error('Error:', error);
                document.getElementById('loading-spinner').innerHTML = '<p class="text-danger">Lỗi: ' + error.message + '</p>';
            });
    }
</script>

</div>
</div>
</div>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>