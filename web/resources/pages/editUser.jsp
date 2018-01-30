<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css">
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<html>
<head>
    <title>Edit user</title>
</head>
<body>
<form method="POST" action='${user.id}' name="Update User">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    First Name : <input
        type="text" name="firstName"
        value="${user.firstName}"> <br/>
    Last Name : <input
        type="text" name="lastName"
        value="${user.lastName}"> <br/>
    Room : <input type="text" name="email"
                   value="${room.name}"> <br/> <input
        type="submit" value="Submit"/>
</form>
</body>

</html>