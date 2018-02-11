<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <title>Show All Users</title>
</head>
<body>

<div class="container-fluid">
    <table class="table table-striped">
        <thead>
        <tr>
            <%--<th onclick="sortTable(0)">User Id</th>--%>
            <th onclick="sortTable(0)">First Name</th>
            <th onclick="sortTable(1)">Last Name</th>
            <th onclick="sortTable(2)">Speciality</th>
            <th onclick="sortTable(3)">Email</th>
            <th onclick="sortTable(4)">Room</th>
            <th colspan=1>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.login}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.email}</td>
                <td>${user.room}</td>
                <td><a href="${contextPath}/OfficeProject/projects?userId=${user.id}" class="btn btn-info" role="button">Show
                    projects</a></td>

            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
</body>
</html>