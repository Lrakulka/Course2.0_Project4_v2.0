<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="login.label.title" /></title>
		<style>
			<%@ include file="../css/styles.css" %>
		</style>
	</head>

	<body onload='document.loginForm.username.focus();'>
		<spring:message code="language.text" /> : <a href="?lang=en">English</a> | <a href="?lang=ua">Українська</a>

		<div id="login-box">

			<h3><spring:message code="login.label.text" /></h3>

			<c:if test="${not empty error}">
				<div class="error"><spring:message code="login.label.error" /></div>
			</c:if>
			<c:if test="${not empty msg}">
				<div class="msg"><spring:message code="login.label.msg" /></div>
			</c:if>

			<form name='loginForm' action="<c:url value='/login' />" method='POST'>

				<table>
					<tr>
						<td><spring:message code="login.label.username" />:</td>
						<td><input type='text' name='username'></td>
					</tr>
					<tr>
						<td><spring:message code="login.label.password" />:</td>
						<td><input type='password' name='password' /></td>
					</tr>
					<tr>
						<td colspan='2'><input name="submit" type="submit"
							value="<spring:message code="login.button.submit" />" /></td>
					</tr>
				</table>

				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

			</form>
		</div>

	</body>
</html>