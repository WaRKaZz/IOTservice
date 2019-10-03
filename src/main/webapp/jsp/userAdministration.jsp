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
		<form action="/administration" class="form-horisontal" style="padding: 0% 30% 0% 30%" method="get">
			<select id="inputState" class="form-control custom-select" name="userID">
				<c:forEach items="${sessionScope.users}" var="user">
					<option value="${user.userID}">${user.userLogin}</option>
				</c:forEach>
			</select>
			<div class="radio" style="margin-top: 3%">
				<label><input type="radio" name="action" value="ban">Ban!</label>
				<label><input type="radio" name="action" value="unBan" checked>Unban!</label>
			</div>
			<button type="submit" class="btn btn-success btn-block" name="apply" value="true" style="margin-top: 2%">Submit</button>
			<br>
			<p class="text-center">${sessionScope.administrationMessage}</p>
		</form>

	</main>
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>