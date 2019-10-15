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
    <jsp:include page="navbarLogined.jsp"></jsp:include>
	<main>
		<form action="/administration" class="form-horisontal" style="padding: 0% 30% 0% 30%" method="post">
			<select id="inputState" class="form-control custom-select" name="userID">
				<c:forEach items="${sessionScope.users}" var="user">
					<option value="${user.userID}">${user.userLogin}</option>
				</c:forEach>
			</select>
			<div class="radio" style="margin-top: 3%">
				<label><input type="radio" name="action" value="ban"><fmt:message key="key.administrationBan"/></label>
				<label><input type="radio" name="action" value="unBan" checked><fmt:message key="key.administrationUnban"/></label>
			</div>
			<button type="submit" class="btn btn-success btn-block" name="apply" value="true" style="margin-top: 2%"><fmt:message key="key.apply"/></button>
			<br>
			<p class="text-center"><fmt:message key="${administrationMessage}"/></p>
		</form>

	</main>
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>