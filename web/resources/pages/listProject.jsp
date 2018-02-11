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
    <title>Show All Projects</title>
</head>
<body>

<div class="container-fluid">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Project name</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${myProjects}" var="mproject">
            <tr>
                <td><a href="${contextPath}/OfficeProject/projects/edit?projectId=${mproject.id}">${mproject.name}</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="${contextPath}/OfficeProject/projects/add" class="btn btn-info" role="button">Add new</a>
</div>
</body>
</html>
