<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags/form"
           prefix="springForm"%>

<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css">
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>Add new room</title>
</head>
<body>
<div class="container-fluid">
    <springForm:form name="form1" method="post" action="${contextPath}/OfficeProject/rooms/add" modelAttribute="room" class="form-signin">
        <h1>Room's name</h1>
        <%--<spring:bind path="name">--%>
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input type="text" path="name" class="form-control"
                        placeholder="Enter room's name" autofocus="true"></form:input>
            <form:errors path="name"></form:errors>
        </div>
        <%--</spring:bind>--%>


        <table class="table table-striped">
            <thead>
            <tr>
                <th>Choose users</th>
            </tr>
            <tr>
                <td>User</td>
                <td>Select</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.firstName} ${user.lastName}</td>
                    <td>
                        <input type="checkbox" name="userId" value="${user.id}">
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <table>
            <tr>
                <td>
                    <INPUT id="submit" name="submit" type="submit" value="Submit"/>
                </td>
            </tr>
        </table>
    </springForm:form>
</div>
</body>
</html>