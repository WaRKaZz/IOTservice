<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>IOT database</title>
	<jsp:include page="cssIntegration.jsp"></jsp:include>
</head>
	<jsp:include page="navbarUnlogined.jsp"></jsp:include>
	<header>
		<h3 class="text-center">Here you can add new device to your home!</h3>
	</header>
	<main>
		<c:choose>
			<c:when test="${home eq null}">
				<p class="text"> Please, choose home before</p>
				<a href="/main" class="btn btn-primary"	role="button">OK</a>
			</c:when>
			<c:otherwise>
				<form action="/addNewDevice" class="form-horisontal" style="padding: 5% 30% 0% 30%" method="get">
				<div class="form-group">
					<label class="control-label"> Enter device name:
					<input type="text" class="form-control" name="homeName"></label>
				</div>
				<div class="form-group">
					<label class="control-label"> Choose device type:
					<input type="text" class="form-control" name="homeAddress"></label>
				</div>
				<button type ="submit" class="btn btn-success btn-block">Apply</button> 
			</form>	
			</c:otherwise>
		</c:choose>
	</main>
	<jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>