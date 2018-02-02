<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css">
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>Show All Projects</title>
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
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Days when ${user.firstName} ${user.lastName} was in office</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${myDates}" var="mydate">
            <tr>
                <td>${mydate.date}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <form method="POST" action="dates?userId=${user.id}" name="frmAddDate">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="userId" value="${user.id}"/>
        Date : <input
            type="text" name="duedate"
            id="datepicker"> <br/>
        <input
            type="submit" value="Add new date"/>
        <a href="/OfficeProject" class="btn btn-info" role="button">Main page</a>
    </form>
    <%--<a href="projects/add" class="btn btn-info" role="button">Add new</a>--%>

</div>
</body>
</html>
