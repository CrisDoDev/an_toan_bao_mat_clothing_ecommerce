<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="messages" var="msgs" />

<!DOCTYPE html>
<html lang="${sessionScope.locale.language}">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><fmt:message key="login.title" bundle="${msgs}" /></title>
<link rel="icon" type="image/png" href="images/icons/favicon.png" />

<link rel="stylesheet" type="text/css"
	href="vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" type="text/css" href="css/auth.css">
</head>
<body>
	<div class="auth-container">
		<div class="auth-box">
			<h2 class="auth-title"><fmt:message key="login.title" bundle="${msgs}" /></h2>
			
			<c:if test="${param.status == 'success'}">
				<div class="alert alert-success" role="alert">
					<fmt:message key="register.success" bundle="${msgs}" />
				</div>
			</c:if>

			<c:if test="${not empty errorMessage}">
				<div class="alert alert-danger" role="alert">${errorMessage}</div>
			</c:if>

			<form action="login" method="post">
				<div class="input-group">
					<label><fmt:message key="login.username" bundle="${msgs}" /></label> 
					<input type="text" name="username" class="auth-input" 
						   placeholder="<fmt:message key='register.emailPlaceholder' bundle='${msgs}' />" required>
				</div>

				<div class="input-group">
					<label><fmt:message key="login.password" bundle="${msgs}" /></label> 
					<input type="password" name="password" class="auth-input" 
						   placeholder="<fmt:message key='register.passwordPlaceholder' bundle='${msgs}' />" required>
				</div>

				<button type="submit" class="auth-btn">
					<fmt:message key="login.submit" bundle="${msgs}" />
				</button>
			</form>

			<div class="auth-divider">
				<span><fmt:message key="common.or" bundle="${msgs}" /></span>
			</div>

			<div class="auth-link">
				<fmt:message key="login.noAccount" bundle="${msgs}" /> 
				<a href="register.jsp"><fmt:message key="login.registerNow" bundle="${msgs}" /></a>
			</div>

			<div class="back-home">
				<a href="${pageContext.request.contextPath}/home">
					<i class="fa fa-arrow-left"></i> 
					<fmt:message key="login.backToHome" bundle="${msgs}" />
				</a>
			</div>
		</div>
	</div>
</body>
</html>