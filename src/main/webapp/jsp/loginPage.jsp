<%--
  Created by IntelliJ IDEA.
  User: NeoDCS
  Date: 09/09/2019
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
<head>
  <title>IOT Service</title>
  <jsp:include page="cssIntegration.jsp"></jsp:include>
</head>
<body>
  <main>
    <jsp:include page="navbarUnlogined.jsp"></jsp:include>
    <div class="container">
      <form action = 'login' method='post' style="padding: 10% 30% 0% 30%" class="form-horisontal">
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
