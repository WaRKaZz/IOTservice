<%--
  Created by IntelliJ IDEA.
  User: NeoDCS
  Date: 09/09/2019
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
<head>
  <title>IOT Service</title>
  <jsp:include page="cssIntegration.jsp"></jsp:include>
</head>
<body>
  <nav class="navbar navbar-expand-md navbar-light bg-light fixed-top">
    <a href="#" class="navbar-brand">IOTService</a>
    <ul class="nav navbar-nav mr-auto">
      <li class="nav-item">
        <a class="nav-link" href=#>Sign in</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/registration">Sign up</a>
      </li>
    </ul>
  </nav>
  <main>
    <div class="container">
      <form action = 'login' method='post' style="padding: 15% 30% 0% 30%" class="form-horisontal">
        <div class="form-group">
          <label for="login" class="control-label">Enter login:</label>
          <input type="text" class="form-control" placeholder="Login" name="login" required>
        </div>
        <div class="form-group">
          <label class="control-label">Password</label>
          <input type="password" class="form-control" placeholder="Password" name="password" required>
        </div>
        <button type="submit" class="btn btn-success btn-block">Submit</button>
      </form>           
    </div>>
  </main>
  <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>
