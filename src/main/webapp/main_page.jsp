<%--
  Created by IntelliJ IDEA.
  User: NeoDCS
  Date: 02-Aug-19
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
</head>
<body>
<b>You are logined as ${sessionScope.user.userLogin}</b>
<form action = 'logout' method='post'>
    <button type="submit">Logout</button>
</form>
<form action = 'ViewHome' method='HomeViewService'>
    <button type=""
</form>
</body>
</html>
