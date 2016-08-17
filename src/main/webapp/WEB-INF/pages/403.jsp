<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="403.label.title" /></title>
</head>
<body>
	<spring:message code="language.text" /> : <a href="?lang=en">English</a> |
	<a href="?lang=ua">Українська</a>
	<h1><spring:message code="403.label.title" /></h1>

	<c:choose>
		<c:when test="${empty username}">
			<h2><spring:message code="403.label.msg" /></h2>
		</c:when>
		<c:otherwise>
			<h2><spring:message code="403.label.username" /> : ${sessionScope.get("userName")}
				<br/><spring:message code="403.label.msgu" /></h2>
		</c:otherwise>
	</c:choose>

</body>
</html>