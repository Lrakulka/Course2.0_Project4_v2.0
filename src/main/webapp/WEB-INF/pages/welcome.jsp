<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="welcome.label.title" /></title>
</head>
<body>
<h2><spring:message code="welcome.label.text" /></h2>
<h3><spring:message code="welcome.label.avalible" /></h3>
<p><a href="/Project_4/admin"><spring:message code="welcome.label.admin_room" /></a></p>
<a href="/Project_4/client"><spring:message code="welcome.label.client_room" /></a>
</body>
</html>