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
		<c:if test="${home.homeInstalledDevices == null}">
			<div class="conteiner"style="padding: 0% 30% 0% 30%">
			<p class="text font-weight-bold"><fmt:message key="key.firstlyChosenHome"/></p>
			<a href="/addNewDevice" class="btn btn-primary btn-block"	role="button"><fmt:message key="key.ok"/></a>
		</div>
		</c:if>
		<div class="row">
				<c:set var="increment" value="1" scope="page" />
				<c:forEach items="${home.homeInstalledDevices}" var="device">
					<div class="col col-6 align-items-start">
						<hr>
						<p>${device.deviceName}, <fmt:message key="${device.deviceDefinitionName}"/></p>
						<hr>
						<c:forEach items="${device.functions}" var="function">
							<form action="/changeDevice" class="form-horizontal" method="post" style="padding-left: 5%">
								<c:choose>
									<c:when test="${function.functionInput}">
										<label class="control-labely"><fmt:message key="${function.functionName}"/></label>
										<c:if test="${function.functionType eq 'BOOL'}">
											 <div class="radio">
												<label><input type="radio" name="${function.functionId}" value="true"><fmt:message key="key.enable"/></label>
												<label><input type="radio" name="${function.functionId}" value="false"><fmt:message key="key.disable"/></label>
											</div>
											<div class="col col-6"><hr></div>
										</c:if>
										<c:if test="${function.functionType eq 'INTEGER'}">
											<div class="form-group">
												<label for="customRange1"><fmt:message key="key.from1to100"/></label>
												<input type="range" class="custom-range" id="customRange1" min="0" max="100" name="${function.functionId}">
											</div>
											<div class="col col-6"><hr></div>
										</c:if>
										<c:if test="${function.functionType eq 'TEXT'}">
											<div class="form-group">
								              <label for="1" class="control-label"><fmt:message key="key.enterFunction"/></label>
								              <input type="text" class="form-control" name="${function.functionId}">
								            </div>
								            <div class="col col-6"><hr></div>
										</c:if>
										<button type="submit" class="btn btn-success btn-block" name"apply" value="true"><fmt:message key="key.submitButton"/></button>
									</c:when>
									<c:otherwise>
										<c:if test="${function.functionType eq 'BOOL'}">
											<c:choose>
												<c:when test="${function.functionTrue}">
													<label class="control-label bg-success"><fmt:message key="${function.functionName}"/></label>
												</c:when>
												<c:otherwise>
													<label class="control-labely bg-dark text-white"><fmt:message key="${function.functionName}"/></label>
												</c:otherwise>
											</c:choose>
											<div class="col col-6"><hr></div>
										</c:if>
										<c:if test="${function.functionType eq 'INTEGER'}">
											<label class="control-labely"><fmt:message key="${function.functionName}"/></label>
											<input type="text" class="form-control" name="1" placeholder="${function.functionInteger}" readonly>
											<div class="col col-6"><hr></div>
										</c:if>
										<c:if test="${function.functionType eq 'TEXT'}">
											<label class="control-labely"><fmt:message key="${function.functionName}"/></label>
											<input type="text" class="form-control" name="1" placeholder="${function.functionText}" readonly>
											<div class="col col-6"><hr></div>
										</c:if>
									</c:otherwise>
								</c:choose>
							</form>
							<c:set var="increment" value="${increment + 1}" scope="page"/>
						</c:forEach>
					</div>	
				</c:forEach>
		</div>
	</main>
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>