<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<fmt:setBundle basename="${lang.languageLocale}"/>

<!DOCTYPE html>
<html>
<head>
	<title>IOT database</title>
	<jsp:include page="cssIntegration.jsp"></jsp:include>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
	<jsp:include page="navbarLogined.jsp"></jsp:include>
	<header>
		<h3 class="text-center"><fmt:message key="key.newHomeInfo"/></h3>
	</header>
	<main>
		<div class="container">
          <form action = 'addNewHome' method='post' style="padding: 5% 30% 0% 30%" class="form-horisontal">
            <div class="form-group">
              <label for="login" class="control-label"><fmt:message key="key.enterNewHomeName"/></label>
              <input type="text" class="form-control" name="homeName" required>
            </div>
            <div class="form-group">
              <label class="control-label"><fmt:message key="key.enterNewHomeAddress"/></label>
              <input type="text" class="form-control" name="homeAddress" required>
            </div>     
            <button type="submit" class="btn btn-success btn-block" name="apply" value="true"><fmt:message key="key.apply"/></button>
          </form>         
        </div>
        <br>
        <p class="text-center"><fmt:message key="${sessionScope.homeMessage}"/></p>	
	</main>
	<jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>