<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="layout-admin.jsp"%>

<div class="container-fluid">
    <h3 class="mt-4 mb-3">Quản lý Sản phẩm</h3>

    <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#addProductModal">
        <i class="fas fa-plus"></i> Thêm sản phẩm mới
    </button>
    
    <c:if test="${not empty param.message}">
        <c:if test="${param.message == 'cannot_delete'}">
            <div class="alert alert-danger">
                <i class="fas fa-exclamation-triangle"></i> 
                Không thể xóa vĩnh viễn! Sản phẩm này đang có trong lịch sử đơn hàng.
            </div>
        </c:if>
        <c:if test="${param.message != 'cannot_delete'}">
            <div class="alert alert-success">
                <c:choose>
                    <c:when test="${param.message == 'success'}">Thêm thành công!</c:when>
                    <c:when test="${param.message == 'updated'}">Cập nhật thành công!</c:when>
                    <c:when test="${param.message == 'soft_deleted'}">Đã chuyển vào thùng rác!</c:when>
                    <c:when test="${param.message == 'destroyed'}">Đã xóa vĩnh viễn!</c:when>
                    <c:when test="${param.message == 'restored'}">Đã khôi phục sản phẩm!</c:when>
                </c:choose>
            </div>
        </c:if>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <table class="table table-bordered table-hover align-middle">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Hình ảnh</th>
                <th>Tên sản phẩm</th>
                <th>Kho hàng (Size - SL)</th>
                <th>Giá</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${listProduct}" var="p">
                <tr class="${p.deleted ? 'table-danger' : ''}">
                    <td>${p.id}</td>
                    <td>
                        <img src="${pageContext.request.contextPath}/images/${p.imageUrl}" 
                             alt="Img" style="width: 60px; height: 60px; object-fit: cover; border-radius: 5px; ${p.deleted ? 'opacity: 0.5' : ''}">
                    </td>
                    <td>
                        <c:if test="${p.deleted}"><del class="text-muted">${p.name}</del> <span class="badge bg-danger">Đã xóa</span></c:if>
                        <c:if test="${!p.deleted}">${p.name}</c:if>
                    </td>
                    <td>
                        <div class="d-flex flex-wrap gap-1">
                            <c:forEach items="${p.listSizes}" var="s">
                                <span class="badge bg-secondary border">
                                    ${s.size}: <span class="${s.stockQuantity > 0 ? 'text-white' : 'text-danger fw-bold'}">${s.stockQuantity}</span>
                                </span>
                            </c:forEach>
                        </div>
                        <div id="data-sizes-${p.id}" style="display:none;">
                            <c:forEach items="${p.listSizes}" var="s">
                                <div data-id="${s.id}" data-name="${s.size}" data-qty="${s.stockQuantity}"></div>
                            </c:forEach>
                        </div>
                        <textarea id="desc-${p.id}" style="display:none;">${p.description}</textarea>
                    </td>
                    <td><fmt:formatNumber value="${p.price}" type="currency" currencySymbol="₫"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${!p.deleted}">
                                <button type="button" class="btn btn-warning btn-sm"
                                        onclick="openEditModal(this)" 
                                        data-id="${p.id}" data-name="${p.name}" data-price="${p.price}"
                                        data-cate="${p.categoryId}" data-img="${p.imageUrl}">
                                    <i class="fas fa-edit"></i>
                                </button>
                                <a href="products?action=delete&id=${p.id}" class="btn btn-danger btn-sm" onclick="return confirm('Bạn muốn chuyển vào thùng rác?');"><i class="fas fa-trash"></i></a>
                            </c:when>
                            <c:otherwise>
                                <a href="products?action=restore&id=${p.id}" class="btn btn-success btn-sm"><i class="fas fa-undo"></i></a>
                                <a href="products?action=destroy&id=${p.id}" class="btn btn-dark btn-sm" onclick="return confirm('Xóa vĩnh viễn?');"><i class="fas fa-times-circle"></i></a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<div class="modal fade" id="addProductModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
             <div class="modal-header bg-primary text-white">
                <h5 class="modal-title">Thêm sản phẩm mới</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form action="products" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="add">
                     <div class="mb-3">
                        <label class="form-label">Tên sản phẩm</label>
                        <input type="text" class="form-control" name="name" required>
                    </div>
                     <div class="mb-3">
                        <label class="form-label">Danh mục</label>
                        <select class="form-select" name="categoryId" id="add_category" onchange="resetSizeContainer('add')">
                            <c:forEach items="${listCategory}" var="c">
                                <option value="${c.id}">${c.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Giá (VNĐ)</label>
                        <input type="number" class="form-control" name="price" required min="0">
                    </div>
                     <div class="mb-3">
                        <label class="form-label">Mô tả</label>
                        <textarea class="form-control" name="description" rows="3"></textarea>
                    </div>
                     <div class="mb-3">
                        <label class="form-label">Hình ảnh</label>
                        <input type="file" class="form-control" name="image" required accept="image/*">
                    </div>

                    <hr>
                    <div class="mb-3">
                        <label class="fw-bold">Size & Tồn kho</label>
                        <div id="size-container"></div>
                        <button type="button" class="btn btn-outline-primary btn-sm mt-2" onclick="addSizeRow()">
                            <i class="fas fa-plus"></i> Thêm Size
                        </button>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="submit" class="btn btn-primary">Lưu</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editProductModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-warning text-dark">
                <h5 class="modal-title">Cập nhật sản phẩm</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
           </div>
            <div class="modal-body">
                <form action="products" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" id="edit_id">
                    <input type="hidden" name="old_image" id="edit_old_image">

                    <div class="mb-3">
                        <label class="form-label">Tên sản phẩm</label>
                        <input type="text" class="form-control" name="name" id="edit_name" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Danh mục</label>
                        <select class="form-select" name="categoryId" id="edit_category" onchange="resetSizeContainer('edit')">
                           <c:forEach items="${listCategory}" var="c">
                                <option value="${c.id}">${c.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Giá</label>
                        <input type="number" class="form-control" name="price" id="edit_price" required min="0">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Mô tả</label>
                        <textarea class="form-control" name="description" id="edit_description" rows="3"></textarea>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Ảnh (Chọn nếu muốn đổi)</label>
                        <input type="file" class="form-control" name="image" accept="image/*">
                        <small class="text-muted">Hiện tại: <span id="current_img_name"></span></small>
                    </div>
                    
                    <hr>
                    <div class="mb-3">
                        <label class="fw-bold">Cập nhật Size & Tồn kho</label>
                        <div id="edit-size-container"></div>
                        <button type="button" class="btn btn-outline-warning btn-sm mt-2" onclick="addEditSizeRow()">
                            <i class="fas fa-plus"></i> Thêm Size Khác
                        </button>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="submit" class="btn btn-warning">Cập nhật</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    // --- CẤU HÌNH LOGIC RIÊNG CỦA BẠN (GIỮ NGUYÊN) ---
    const SIZES_CLOTHES = ['XS', 'S', 'M', 'L', 'XL', 'XXL', '3XL'];
    const SIZES_SHOES = ['35', '36', '37', '38', '39', '40', '41', '42', '43', '44', '45'];
    const ALL_SIZES_ORDER = SIZES_CLOTHES.concat(SIZES_SHOES); 
    const SHOES_CATEGORY_ID = 4; 

    // Hàm lấy danh sách size theo danh mục
    function getSizeListByCategoryId(catId) {
        if (parseInt(catId) === SHOES_CATEGORY_ID) {
            return SIZES_SHOES;
        } else {
            return SIZES_CLOTHES;
        }
    }

    // Hàm lấy điểm số (rank) của size để sắp xếp
    function getSizeRank(sizeName) {
        if (!sizeName) return 999; 
        var index = ALL_SIZES_ORDER.indexOf(sizeName);
        return index === -1 ? 998 : index; 
    }

    //CẬP NHẬT GIAO DIỆN (Sắp xếp & Ẩn size đã chọn)
    function updateAvailableSizes(containerId) {
        var container = document.getElementById(containerId);
        if (!container) return;

        try {
            var rows = Array.from(container.querySelectorAll('.size-row'));
            rows.sort(function(rowA, rowB) {
                var selectA = rowA.querySelector('select[name="size_names"]');
                var selectB = rowB.querySelector('select[name="size_names"]');
                var valA = selectA ? selectA.value : '';
                var valB = selectB ? selectB.value : '';
                return getSizeRank(valA) - getSizeRank(valB);
            });
            for (var i = 0; i < rows.length; i++) {
                container.appendChild(rows[i]);
            }
            var allSelects = container.querySelectorAll('select[name="size_names"]');
            var selectedValues = [];
            allSelects.forEach(function(s) {
                if (s.value) selectedValues.push(s.value);
            });
            allSelects.forEach(function(select) {
                var currentVal = select.value;
                var options = select.querySelectorAll('option');                
                options.forEach(function(option) {
                    if (selectedValues.indexOf(option.value) !== -1 && option.value !== currentVal) {
                        option.hidden = true;
                        option.style.display = 'none';
                    } else {
                        option.hidden = false;
                        option.style.display = '';
                    }
                });
            });
        } catch (e) {
            console.error("Lỗi trong hàm updateAvailableSizes:", e);
        }
    }

    function createSizeSelectHTML(selectedValue, currentCatId, containerId) {
        if(!selectedValue) selectedValue = '';
        var sizeList = getSizeListByCategoryId(currentCatId);
        var html = '<select class="form-select" name="size_names" onchange="updateAvailableSizes(\'' + containerId + '\')">';
        html += '<option value="">-- Chọn --</option>';

        for(var i=0; i<sizeList.length; i++) {
            var s = sizeList[i];
            var selected = (s === selectedValue) ? 'selected' : '';
            html += '<option value="' + s + '" ' + selected + '>Size ' + s + '</option>';
        }
        if (selectedValue && sizeList.indexOf(selectedValue) === -1) {
             html += '<option value="' + selectedValue + '" selected>' + selectedValue + ' (Cũ)</option>';
        }
        html += '</select>';
        return html;
    }

    //CÁC HÀM XỬ LÝ SỰ KIỆN 
    function resetSizeContainer(mode) {
        if (mode === 'add') {
            var c = document.getElementById('size-container');
            if(c) { 
                c.innerHTML = ''; 
                addSizeRow(); 
            }
        } 
    }

    function openEditModal(btn) {
        try {
            var id = btn.getAttribute("data-id");
            document.getElementById('edit_id').value = id;
            document.getElementById('edit_name').value = btn.getAttribute("data-name");
            document.getElementById('edit_price').value = parseInt(btn.getAttribute("data-price"));
            document.getElementById('edit_category').value = btn.getAttribute("data-cate");
            document.getElementById('edit_old_image').value = btn.getAttribute("data-img");
            document.getElementById('current_img_name').innerText = btn.getAttribute("data-img");
            
            var descEl = document.getElementById("desc-" + id);
            document.getElementById('edit_description').value = descEl ? descEl.value : "";
            var container = document.getElementById('edit-size-container');
            var catId = btn.getAttribute("data-cate");
            if (container) {
                container.innerHTML = ''; 
                var dataDiv = document.getElementById('data-sizes-' + id);
                var sizes = dataDiv ? dataDiv.querySelectorAll('div') : [];
                
                if (sizes.length === 0) {
                    addEditSizeRow(0, '', 0, catId); 
                } else {
                    sizes.forEach(function(div) {
                        addEditSizeRow(
                            div.getAttribute('data-id'), 
                            div.getAttribute('data-name'), 
                            div.getAttribute('data-qty'), 
                            catId
                        );
                    });
                }
            }
            setTimeout(function() { updateAvailableSizes('edit-size-container'); }, 200);
            var myModal = new bootstrap.Modal(document.getElementById('editProductModal'));
            myModal.show();
        } catch (e) { 
            console.error(e);
            alert("Có lỗi xảy ra: " + e.message);
        }
    }
    
    function appendRow(containerId, id, name, qty, catId) {
        var container = document.getElementById(containerId);
        if (!container) return;
        var safeId = id ? id : 0;
        var safeQty = (qty !== undefined && qty !== null) ? qty : 0;
        var div = document.createElement('div');
        div.className = 'row mb-2 size-row';       
        div.innerHTML = 
            '<input type="hidden" name="size_ids" value="' + safeId + '">' +
            
            '<div class="col-5">' +
                createSizeSelectHTML(name, catId, containerId) +
            '</div>' +
            
            '<div class="col-5">' +
                '<input type="number" class="form-control" name="size_quantities" value="' + safeQty + '" placeholder="SL" min="0" required>' +
            '</div>' +
            
            '<div class="col-2">' +
                '<button type="button" class="btn btn-danger btn-sm w-100" onclick="removeRow(this, \'' + containerId + '\')">X</button>' +
            '</div>';
        
        container.appendChild(div);
        updateAvailableSizes(containerId);
    }
    
    function addSizeRow() {
        var catEl = document.getElementById('add_category');
        var catId = catEl ? catEl.value : 1;
        appendRow('size-container', 0, '', '', catId);
    }

    function addEditSizeRow(id, name, qty, explicitCatId) {
        if(!id) id=0;
        if(!name) name='';
        if(!qty) qty=0;
        var catEl = document.getElementById('edit_category');
        var catId = explicitCatId || (catEl ? catEl.value : 1);
        appendRow('edit-size-container', id, name, qty, catId);
    }

    function removeRow(btn, containerId) {
        btn.closest('.size-row').remove();
        updateAvailableSizes(containerId);
    }

    document.addEventListener("DOMContentLoaded", function() {
        var c = document.getElementById('size-container');
        if (c && c.innerHTML.trim() === "") addSizeRow();
    });
</script>

</div></div></div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>