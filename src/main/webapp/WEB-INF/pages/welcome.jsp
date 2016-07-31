<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="welcome.label.title" /></title>
        <style>
            <%@ include file="../css/styles.css" %>
        </style>
    </head>
    <body>
        <h2><spring:message code="welcome.label.text" /></h2>
        <h3><spring:message code="welcome.label.available" /></h3>
        <p><a href="/admin"><spring:message code="welcome.label.admin_room" /></a></p>
        <a href="/client"><spring:message code="welcome.label.client_room" /></a>

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
                    ${pageContext.request.userPrincipal.name} | <a
                    href="javascript:formSubmit()"><spring:message code="admin.label.logout" /></a>
            </h2>
        </c:if>
    </body>
</html>