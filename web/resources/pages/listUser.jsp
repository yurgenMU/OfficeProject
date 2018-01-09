<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css">
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>Show All Users</title>
</head>
<body>

<div class="container-fluid">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>User Id</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Speciality</th>
            <th>Email</th>
            <th colspan=2>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.speciality}</td>
                <td>${user.email}</td>
                <td><a href="users/edit/${user.id}" class="btn btn-info" role="button">Update</a></td>
                <td><a href="users/remove/${user.id}" class="btn btn-info" role="button">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="users/add" class="btn btn-info" role="button">Add User</a>
    <%--<button type="button" href="/add">Add User</button>--%>
    <%--<p><a href=/add>Add User</a></p>--%>
</div>
</body>
</html>