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
	<main style="padding: 0% 30% 0% 30%">
		<form action="/updateHome" class="form-horisontal" method="get">
		<div class="form-group">
			<label class="control-label">Choose home which you want to change:</label>
			<select id="inputState" class="form-control custom-select" name="homeID">
				<c:forEach items="${homeAdminList}" var="homeAdmin">
					<option value="${homeAdmin.homeID}">${homeAdmin.homeName} ${homeAdmin.homeAddress}</option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group">
			<label class="control-label">Enter new home name:</label>
			<input type="text" class="form-control" name="homeName">
		</div>
		<div class="form-group">
			<label class="control-label">Enter new home address:</label>
			<input type="text" class="form-control" name="homeAddress">
		</div>
		<div class="custom-control custom-switch">
			<input type="checkbox" class="custom-control-input" id="customSwitch1" name="delete" value="true">
			<label class="custom-control-label" for="customSwitch1">Delete home</label>
		</div>
		<br>
		<button type ="submit" class="btn btn-success btn-block" name ="apply" value="true">Apply</button> 	
		</form>	
		<br>
		<p class="text-center">${sessionScope.homeMessage}</p>
	</main>
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>