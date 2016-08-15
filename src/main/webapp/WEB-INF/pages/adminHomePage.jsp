<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tables" uri="/WEB-INF/tables.tld"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><spring:message code="admin.label.title" /></title>
        <style>
            <%@ include file="../css/styles.css" %>
        </style>
	</head>
	<body>
		<spring:message code="language.text" /> :
        <a href="?lang=en">English</a> | <a href="?lang=ua">Українська</a>
		<tables:clientsTable clients="${clients}"
                             csrf="${_csrf}"
		/>

		<c:url value="/logout" var="logoutUrl" />
		<!-- csrt for log out-->
		<form action="${logoutUrl}" method="post" id="logoutForm">
		  <input type="hidden"
			name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		</form>
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>
		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<h2>
				<spring:message code="admin.label.welcome" /> :
					${sessionScope.get("userName")} | <a
					href="javascript:formSubmit()"><spring:message code="admin.label.logout" /></a>
			</h2>
		</c:if>
        <a href="/welcome"><spring:message code="label.welcom_room" /></a>
	</body>
</html>