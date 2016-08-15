<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tables" uri="/WEB-INF/tables.tld"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><spring:message code="client.label.title" /></title>
		<style>
			<%@ include file="../css/styles.css" %>
		</style>
	</head>
	<body>
		<spring:message code="language.text" /> :
		<a href="?lang=en">English</a> | <a href="?lang=ua">Українська</a>
		<tables:clientTable bills="${bills}"
                            csrf="${_csrf}"
		/>
		<c:if test="${not empty param.msgCard}">
			<div class="error"><spring:message code="client.label.msgCard" /></div>
		</c:if>
		<c:if test="${not empty param.msgBill}">
			<div class="error"><spring:message code="client.label.msgBill" /></div>
		</c:if>
		<c:if test="${not empty param.errMsg}">
			<div class="error"><spring:message code="client.label.errMsg" /></div>
		</c:if>
		<c:if test="${not empty param.msgPass}">
			<div class="error"><spring:message code="client.label.msgPass" /></div>
		</c:if>
		<c:if test="${not empty param.msgMon}">
			<div class="error"><spring:message code="client.label.msgMoney" /></div>
		</c:if>
        <c:if test="${not empty param.msgBillBlocked}">
            <div class="error"><spring:message code="client.label.msgBillBlocked" /></div>
        </c:if>

		<c:url value="/logout" var="logoutUrl" />
		<!-- csrt for log out-->
		<form action="${logoutUrl}" method="post" id="logoutForm">
		  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>
		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<h2>
				<spring:message code="client.label.welcome" /> :
					${sessionScope.get("userName")} | <a
					href="javascript:formSubmit()">
					<spring:message code="client.label.logout" /></a>
			</h2>
		</c:if>
		<a href="/welcome"><spring:message code="label.welcom_page" /></a>
	</body>
</html>