<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Welcome</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a>

        You logged in as administrator

    </c:if>

</div>
<a href="${contextPath}/edit/${user.id}">Edit Profile</a>
${user.firstName} ${user.lastName}

room ${room.name}

Administrator menu
<ul>
    <li><a href="${contextPath}/OfficeProject/showAll">All users</a></li>
    <li><a href="${contextPath}/OfficeProject/projects/all">Projects</a></li>
    <li><a href="${contextPath}/OfficeProject/rooms/all">Rooms</a></li>
    <li><a href="${contextPath}/OfficeProject/dates">Dates</a></li>

</ul>



My menu
<ul>
    <li><a href="${contextPath}/OfficeProject/projects?userId=${user.id}">My projects</a></li>
    <li><a href="${contextPath}/OfficeProject/dates?userId=${user.id}">My working days</a></li>
</ul>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>