<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="welcome.label.title" /></title>
        <spring:url value="/resources/images/DatabaseUMLDiagram.png" var="diagram" />
    </head>
    <body>
        <spring:message code="language.text" /> : <a href="?lang=en">English</a> |
        <a href="?lang=ua">Українська</a>
        <h2 align="center"><spring:message code="welcome.label.task" /></h2>
        <h2><spring:message code="welcome.label.text" /></h2>
        <h3><spring:message code="welcome.label.author" /></h3>
        <p>Tomcat9, JSP/JSTL, Custom tag, Spring MVC(Annotation), Spring Security,
            Hibernate, C3P0, MySQL, Log4j, i18n, CSS, Maven,
            Lombok, Git, JUnit, HTML5</p>
        <img src=${diagram}>
        <h3><spring:message code="welcome.label.available" /></h3>
        <p><a href="/admin"><spring:message code="welcome.label.admin_page" /></a></p>
        <p><a href="/client"><spring:message code="welcome.label.client_page" /></a><p>

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
                    ${sessionScope.get("userName")} |
                <a href="javascript:formSubmit()"><spring:message code="admin.label.logout" /></a>
            </h2>
        </c:if>

    </body>
</html>