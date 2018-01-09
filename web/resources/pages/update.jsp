<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css">
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<html>
<head>
    <title>Edit user</title>
</head>
<body>
<form method="POST" action='edit' name="Update User">
    User ID : <input type="text" readonly="readonly" name="id"
                     value="${user.id}"> <br />
    First Name : <input
        type="text" name="firstName"
        value="${user.firstName}"> <br/>
    Last Name : <input
        type="text" name="lastName"
        value="${user.lastName}"> <br/>
    Speciality : <input
        type="text" name="speciality"
        value="${user.speciality}"> <br/>
    Email : <input type="text" name="email"
                   value="${user.email}"> <br/> <input
        type="submit" value="Submit"/>
</form>
</body>

</html>
