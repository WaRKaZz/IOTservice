<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
<head>
	<title>IOT database</title>
	<jsp:include page="cssIntegration.jsp"></jsp:include>
</head>
	<jsp:include page="navbarLogined.jsp"></jsp:include>
	<header>
		<h3 class="text-center">Here you can add new home!</h3>
	</header>
	<main>
		<div class="container">
          <form action = 'settings' method='get' style="padding: 5% 30% 0% 30%" class="form-horisontal">
            <div class="form-group">
              <label for="login" class="control-label">Enter home name:</label>
              <input type="text" class="form-control" name="homeName" required>
            </div>
            <div class="form-group">
              <label class="control-label">Enter home address:</label>
              <input type="text" class="form-control" name="homeAddress" required>
            </div>     
            <button type="submit" class="btn btn-success btn-block" name="apply" value="true">Submit</button>
          </form>         
        </div>
        <br>
        <p class="text-center">${sessionScope.homeMessage}</p>	
	</main>
	<jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>