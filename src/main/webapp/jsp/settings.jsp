<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="custom" uri="/WEB-INF/customTag.tld"%>
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
		<div class="container">
			<div class="row">
				<div class="col col-6"><a href="addNewHome" class="btn btn-primary btn-block"><fmt:message key="key.addNewHome"/></a></div>
				<div class="col col-6"><a href="addNewDevice" class="btn btn-primary btn-block"><fmt:message key="key.addNewDevice"/></a></div>
				<custom:DoubleSpace/>
				<div class="col col-6"><a href="updateHome" class="btn btn-primary btn-block"><fmt:message key="key.updateHome"/></a></div>
				<div class="col col-6"><a href="updateDevice" class="btn btn-primary btn-block"><fmt:message key="key.updateDevice"/></a></div>
				<custom:DoubleSpace/>
				<div class="col col-6"><a href="language" class="btn btn-primary btn-block"><fmt:message key="key.languageProp"/></a></div>
				<c:if test="${sessionScope.user.userRole == 1}">
					<custom:DoubleSpace/>
					<div class="col col-6"><a href="administration" class="btn btn-primary btn-block"><fmt:message key="key.administration"/></a></div>
				</c:if>
			</div>
		</div>
	</main>
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>