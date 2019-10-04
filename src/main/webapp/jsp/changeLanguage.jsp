<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="${lang.languageLocale}" />

<!DOCTYPE html>
<html>
<head>
	<title>IOT Service</title>
    <jsp:include page="cssIntegration.jsp"></jsp:include>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<c:choose>
		<c:when test="${sessionScope.user == null}">
    		<jsp:include page="navbarUnlogined.jsp"></jsp:include>
		</c:when>
		<c:when test="${sessionScope.user.userLogin == 'guest'}">
    		<jsp:include page="navbarUnlogined.jsp"></jsp:include>
		</c:when>
		<c:otherwise>
    		<jsp:include page="navbarLogined.jsp"></jsp:include>
		</c:otherwise>
	</c:choose>
	<main>
		<form action="/language" class="form-horisontal" style="padding: 0% 30% 0% 30%" method="post">
			<select id="inputState" class="form-control custom-select" name="languageID">
				<c:forEach items="${languages}" var="language">
					<option value="${language.languageID}">${language.languageName}</option>
				</c:forEach>
			</select>
			<button type="submit" class="btn btn-success btn-block" name="apply" value="true" style="margin-top: 2%">
				<fmt:message key="key.submitButton"/></button>
			<br>
			<p class="text-center">${sessionScope.administrationMessage}</p>
		</form>
	</main>
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>