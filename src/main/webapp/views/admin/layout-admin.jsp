<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .sidebar { height: 100vh; background-color: #343a40; color: white; padding-top: 20px; }
        .sidebar a { color: white; text-decoration: none; display: block; padding: 10px 20px; }
        .sidebar a:hover { background-color: #495057; }
        .content { padding: 20px; }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2 sidebar">
                <h4 class="text-center mb-4">Trang ADMIN</h4>
                <a href="${pageContext.request.contextPath}/admin/dashboard"><i class="fas fa-home me-2"></i> Tổng quan</a>
                <a href="${pageContext.request.contextPath}/admin/products"><i class="fas fa-box me-2"></i> Quản lý Sản phẩm</a>
                <a href="${pageContext.request.contextPath}/admin/orders"><i class="fas fa-shopping-cart me-2"></i> Quản lý Đơn hàng</a>
                <hr>                
                <a href="${pageContext.request.contextPath}/logout" class="text-danger"><i class="fas fa-sign-out-alt me-2"></i> Đăng xuất</a>
            </div>

            <div class="col-md-10 content">
                <div class="d-flex justify-content-between align-items-center mb-4 border-bottom pb-2">
                    <h5>Xin chào, ${sessionScope.user.fullName}</h5>
                </div>