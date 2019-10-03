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
    <jsp:include page="navbarLogined.jsp"></jsp:include>
	<main>
		<div class="container" style="padding: 0% 30% 0% 30%">
		<label for="login" class="control-label">Hello!, : ${user.userLogin}</label>
			<form action = 'changePassword' method='post' style="padding: 5% 30% 0% 30%" class="form-horisontal">
			<p>Here you can change you'r password</p>
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