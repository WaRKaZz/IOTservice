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
	<main style="padding: 0% 30% 0% 30%">
		<form action="/updateHome" class="form-horisontal" method="post">
		<div class="form-group">
			<label class="control-label"><fmt:message key="key.updateHomeChooseType"/></label>
			<select id="inputState" class="form-control custom-select" name="homeID">
				<c:forEach items="${homeAdminList}" var="homeAdmin">
					<option value="${homeAdmin.homeID}">${homeAdmin.homeName} ${homeAdmin.homeAddress}</option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group">
			<label class="control-label"><fmt:message key="key.updateHomeNewName"/></label>
			<input type="text" class="form-control" name="homeName">
		</div>
		<div class="form-group">
			<label class="control-label"><fmt:message key="key.updateHomeNewAdreess"/>:</label>
			<input type="text" class="form-control" name="homeAddress">
		</div>
		<div class="custom-control custom-switch">
			<input type="checkbox" class="custom-control-input" id="customSwitch1" name="delete" value="true">
			<label class="custom-control-label" for="customSwitch1"><fmt:message key="key.updateHomeDelete"/></label>
		</div>
		<br>
		<button type ="submit" class="btn btn-success btn-block" name ="apply" value="true"><fmt:message key="key.apply"/></button> 	
		</form>	
		<br>
		<p class="text-center"><fmt:message key="${homeMessage}"/></p>
	</main>
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>