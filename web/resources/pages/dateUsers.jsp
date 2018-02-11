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
    <title>Users in selected day</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        $( function() {
            $( "#datepicker" ).datepicker();
        } );
        var date = $('#datepicker').datepicker({ dateFormat: 'dd-mm-yy' }).val();
    </script>
</head>
<body>



<div class="container-fluid">




    <form method="GET" action="${contextPath}/OfficeProject/users?adate=${datepicker}" name="frmAddDate">
        <h1>Users who have been in the office at ${month} ${day}th </h1>
        Date : <input
            type="text" name="adate"
            id="datepicker" value="${showDate}"> <br/>
        <input
                type="submit" value="Show users"/>
        <a href="/${contextPath}" class="btn btn-info" role="button">to main page</a>

    <table class="table table-striped">

        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.firstName} ${user.lastName}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

</div>
</body>
</html>
