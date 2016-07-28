<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tables" uri="/WEB-INF/tables.tld"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="admin.label.title" /></title>
</head>
<body>
<spring:message code="admin.button.unblock" var="unblock" />
<spring:message code="admin.label.blocked" var="blocked" />
<spring:message code="admin.label.unblocked" var="unblocked" />
<tables:clientTable clients="${clients}" 
buttonInfo="${unblock}"  
textBlocked="${blocked}"  
textUnBlocked="${unblocked}" 
parameterName="${_csrf.parameterName}" token="${_csrf.token}" />
<c:if test="${not empty msg}">
	<div style="color:red"><spring:message code="admin.label.msg" /></div>
</c:if>
<c:url value="/j_spring_security_logout" var="logoutUrl" />
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
			${pageContext.request.userPrincipal.name} | <a
			href="javascript:formSubmit()"><spring:message code="admin.label.logout" /></a>
	</h2>
</c:if>

</body>
</html>