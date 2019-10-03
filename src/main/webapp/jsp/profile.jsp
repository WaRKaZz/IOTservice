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
		<p class="text-center">Hello!, ${user.userLogin}</p>
		<form action = 'changePassword' method='post' style="padding: 5% 30% 0% 30%" class="form-horisontal">
		<p>Here you can change you'r password</p>
		<div class="form-group">
			<label class="control-label">Old Password</label>
			<input type="password" class="form-control" placeholder="Old Password" name="oldPassword" required>
		</div>
		<div class="form-group">
			<label class="control-label">New Password</label>
			<input type="password" class="form-control" placeholder="New Password" name="newPassword" required>
		</div>
		<div class="form-group">
			<label class="control-label">Repeat password</label>
			<input type="password" class="form-control" placeholder="Repeat password" name="repeatedPassword" required>
		</div>
			<button type="submit" class="btn btn-success btn-block">Submit</button>
		</form>    
	</main>
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>