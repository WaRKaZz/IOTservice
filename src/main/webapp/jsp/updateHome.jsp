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
		<div class="container">
			<div class="row">
				<div class="col col-6"><a href="addNewHome" class="btn btn-primary btn-block">Add new home</a></div>
				<div class="col col-6"><a href="addNewDevice" class="btn btn-primary btn-block">Add new device</a></div>
				<br>
				<br>
				<div class="col col-6"><a href="updateHome" class="btn btn-primary btn-block">Update home</a></div>
				<div class="col col-6"><a href="updateDevice" class="btn btn-primary btn-block ">Update device</a></div>
			</div>
		</div>
	</main>
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>