<%--
  Created by IntelliJ IDEA.
  User: NeoDCS
  Date: 02-Aug-19
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <jsp:include page="cssIntegration.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="navbarUnlogined.jsp"></jsp:include>
    <main>
        <header>
            <h2><b>${sessionScope.registrationMessage}</b></h2>
        </header>
        <div class="container">
          <form action = 'submitRegistration' method='post' style="padding: 5% 30% 0% 30%" class="form-horisontal">
            <div class="form-group">
              <label for="login" class="control-label">Username</label>
              <input type="text" class="form-control" placeholder="Login" name="login" required>
            </div>
            <div class="form-group">
              <label class="control-label">Password</label>
              <input type="password" class="form-control" placeholder="Password" name="password" required>
            </div>
            <div class="form-group">
              <label class="control-label">Repeat password</label>
              <input type="password" class="form-control" placeholder="Repeated password" name="repeatedPassword" required>
            </div>
            <button type="submit" class="btn btn-success btn-block">Submit</button>
          </form>           
        </div>
    </main>
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>
