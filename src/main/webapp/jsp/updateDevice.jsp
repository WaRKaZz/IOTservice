<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<fmt:setBundle basename="${lang.languageLocale}"/>

<!DOCTYPE html>
<html>
<head>
	<title>IOT Service</title>
	<meta charset="UTF-8">
    <jsp:include page="cssIntegration.jsp"></jsp:include>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
    <jsp:include page="navbarLogined.jsp"></jsp:include>
	<main style="padding: 0% 30% 0% 30%">
		<c:choose>
			<c:when test="${home == null}">
				<div class="conteiner">
				<p class="text font-weight-bold"><fmt:message key="key.firstlyChosenHome"/></p>
				<a href="/main" class="btn btn-primary btn-block"	role="button">OK</a>
				</div>
			</c:when>
			<c:when test="${home.homeInstalledDevices == null}">
				<label style="padding: 0% 30% 0% 30%" class="control-label"><fmt:message key="key.dontHaveInstalledDeivces"/></label>
				<a href="/addNewDevice" class="btn btn-block btn-success"><fmt:message key="key.installDevices"/></a>
			</c:when>
			<c:otherwise>
				<form action="/updateDevice" class="form-horisontal" method="post">
					<div class="form-group">
						<label class="control-label"><fmt:message key="key.updateDeviceChooseDeviceType"/></label>
						<select id="inputState" class="form-control custom-select" name="deviceID">
							<c:forEach items="${home.homeInstalledDevices}" var="device">
								<option value="${device.deviceID}">${device.deviceName}, <fmt:message key="${device.deviceDefinitionName}"/></option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label class="control-label"><fmt:message key="key.updateDeviceNewDeviceName"/></label>
						<input type="text" class="form-control" name="deviceName">
					</div>
					<div class="form-group">
						<label class="control-label"><fmt:message key="key.updateDeviceWhereToReplace"/></label>
						<select id="inputState" class="form-control custom-select" name="homeID">
							<c:forEach items="${homeAdminList}" var="homeAdmin">
								<option value="${homeAdmin.homeID}">${homeAdmin.homeName} ${homeAdmin.homeAddress}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label class="control-label"><fmt:message key="key.updateDeviceNewDeviceType"/></label>
						<select id="inputState" class="form-control custom-select" name="deviceTypeID">
							<c:forEach items="${deviceTypeList}" var="deviceType">
								<option value="${deviceType.deviceTypeID}"><fmt:message key="${deviceType.deviceTypeName}"/></option>
							</c:forEach>
						</select>
					</div>
					<div class="custom-control custom-switch">
						<input type="checkbox" class="custom-control-input" id="customSwitch1" name="delete" value="true">
						<label class="custom-control-label" for="customSwitch1"><fmt:message key="key.updateDeviceDeleteDevice"/></label>
					</div>
					<br>
					<button type ="submit" class="btn btn-success btn-block" name ="apply" value="true"><fmt:message key="key.apply"/></button> 	
				</form>	
			</c:otherwise>
		</c:choose>
		<br>
        <p class="text-center"><fmt:message key="${deviceMessage}"/></p>
	</main>
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>