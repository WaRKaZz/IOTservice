<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<fmt:setBundle basename="${lang.languageLocale}"/>

<!DOCTYPE html>
<html>
<head>
  <title>IOT Service</title>
  <jsp:include page="cssIntegration.jsp"></jsp:include>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
  <jsp:include page="navbarUnlogined.jsp"></jsp:include>
  <main>
    <div class="container">
      <form action = 'login' method='post' style="padding: 10% 30% 0% 30%" class="form-horisontal">
        <div class="form-group">
          <label for="login" class="control-label"><fmt:message key="key.enterLogin"/></label>
          <input type="text" class="form-control" name="login" required>
        </div>
        <div class="form-group">
          <label class="control-label"><fmt:message key="key.enterPassword"/></label>
          <input type="password" class="form-control" name="password" required>
        </div>
        <button type="submit" class="btn btn-success btn-block"><fmt:message key="key.submitButton"/></button>
      </form>           
    </div>>
  </main>
  <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>
