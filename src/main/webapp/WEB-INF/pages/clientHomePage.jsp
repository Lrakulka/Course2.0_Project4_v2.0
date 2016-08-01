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
		<spring:message code="language.text" /> : <a href="?lang=en">English</a> | <a href="?lang=ua">Українська</a>
		<spring:message code="client.button.block" var="block" />
		<spring:message code="client.button.fill" var="fill" />
		<spring:message code="client.button.pay" var="pay" />
		<spring:message code="client.label.blocked" var="blocked" />
		<spring:message code="client.label.unblocked" var="unblocked" />
		<spring:message code="button.unblock" var="unblock" />
		<spring:message code="button.block" var="block" />
		<spring:message code="button.undelete" var="undelete" />
		<spring:message code="button.delete" var="delete" />

		<tables:clientTable bills="${bills}"
							buttonFillInfo="${fill}"
							buttonBlockInfo="${block}"
							buttonMakePaymentInfo="${pay}"
							textBlocked="${blocked}"
							textUnBlocked="${unblocked}"
							deleteButtonInfo="${delete}"
							unDeleteButtonInfo="${undelete}"
							blockButtonInfo="${block}"
							unBlockButtonInfo="${unblock}"
							parameterName="${_csrf.parameterName}"
							token="${_csrf.token}"
		/>
		<c:if test="${not empty msg}">
			<div style="color:red"><spring:message code="client.label.msg" /></div>
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
					${pageContext.request.userPrincipal.name} | <a
					href="javascript:formSubmit()">
					<spring:message code="client.label.logout" /></a>
			</h2>
		</c:if>
		<a href="/welcome"><spring:message code="label.welcom_room" /></a>
	</body>
</html>