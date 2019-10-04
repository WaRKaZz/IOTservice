<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<fmt:setBundle basename="${lang.languageLocale}"/>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>IOT database</title>
	<jsp:include page="cssIntegration.jsp"></jsp:include>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
	<jsp:include page="navbarLogined.jsp"></jsp:include>
	<header>
		<h3 class="text-center"><fmt:message key="key.newDeviceInfo"/></h3>
	</header>
	<main>
		<c:choose>
			<c:when test="${home == null}">
				<div class="conteiner"style="padding: 0% 30% 0% 30%">
				<p class="text font-weight-bold"><fmt:message key="key.firstlyChosenHome"/></p>
				<a href="/main" class="btn btn-primary btn-block"  role="button"><fmt:message key="key.ok"/></a>
				</div>
			</c:when>
			<c:otherwise>
				<form action="/addNewDevice" class="form-horisontal" style="padding: 0% 30% 0% 30%" method="post">
				<div class="form-group">
					<label class="control-label"><fmt:message key="key.newDeviceName"/></label>
					<input type="text" class="form-control" name="deviceName">
				</div>
				<div class="form-group">
					<label class="control-label"><fmt:message key="key.chooseDeviceType"/></label>
					<select id="inputState" class="form-control custom-select" name="deviceTypeID">
						<c:forEach items="${sessionScope.deviceTypeList}" var="deviceType">
							<option value="${deviceType.deviceTypeID}"><fmt:message key="${deviceType.deviceTypeName}"/></option>
						</c:forEach>
					</select>
				</div>
				<button type ="submit" class="btn btn-success btn-block" name ="apply" value="true"><fmt:message key="key.apply"/></button> 	
				</form>	
			</c:otherwise>
		</c:choose>
		<br>
        <p class="text-center"><fmt:message key="${sessionScope.deviceMessage}"/></p>	
	</main>
	<jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>