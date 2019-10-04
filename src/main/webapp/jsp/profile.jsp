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
		<p class="text-center"><fmt:message key="key.profileWelcomeMessage"/> ${sessionScope.user.userLogin}</p>
		<form action = 'profile' method='post' style="padding: 5% 30% 0% 30%" class="form-horisontal">
		<p><fmt:message key="key.profileMessage"/></p>
		<div class="form-group">
			<label class="control-label"><fmt:message key="key.oldPassword"/></label>
			<input type="password" class="form-control" name="oldPassword" required>
		</div>
		<div class="form-group">
			<label class="control-label"><fmt:message key="key.newPassword"/></label>
			<input type="password" class="form-control" name="newPassword" required>
		</div>
		<div class="form-group">
			<label class="control-label"><fmt:message key="key.repeatedPassword"/></label>
			<input type="password" class="form-control" name="repeatedPassword" required>
		</div>
			<button type="submit" class="btn btn-success btn-block" name ="apply" value="true"><fmt:message key="key.apply"/></button>
		<br>
		<p class="text-center"><fmt:message key="${sessionScope.changePasswordMessage}"/></p>
		</form>    
	</main>
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>