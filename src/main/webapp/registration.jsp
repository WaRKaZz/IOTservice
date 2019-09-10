<%--
  Created by IntelliJ IDEA.
  User: NeoDCS
  Date: 02-Aug-19
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<html>
<head>
    <title>Title</title>
</head>
<body>
<br />
    <br />
    <b>${sessionScope.registrationMessage}</b>
    <br />
<br />
<form action = 'submitRegistration' method='post'>
    <b>Username: </b>
    <input type="text" placeholder="Login" name="login" required>
    <br />
    <b>Password: </b>
    <input type="password" placeholder="Enter Password" name="password" required>
    <br />
    <b>Repeat password </b>
    <input type="password" placeholder="Repeat Password" name="repeatedPassword" required>
    <br />
    <b> Your Fist Name: </b>
    <input type="text" placeholder="Enter your first name" name="firstName">
    <br />
    <b> Your Last Name: </b>
    <input type="text" placeholder="Enter your last name" name="lastName">
    <br />
    <button type='submit'>Sign up!</button>
</form>
</body>
</html>
