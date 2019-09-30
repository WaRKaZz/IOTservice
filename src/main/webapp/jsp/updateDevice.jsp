<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
<head>
	<title>IOT Service</title>
	<meta charset="UTF-8">
    <jsp:include page="cssIntegration.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="navbarLogined.jsp"></jsp:include>
	<main style="padding: 0% 30% 0% 30%">
		<c:choose>
			<c:when test="${home == null}">
				<div class="conteiner">
				<p class="text font-weight-bold">Firstly you have to choose home:</p>
				<a href="/main" class="btn btn-primary btn-block"	role="button">OK</a>
				</div>
			</c:when>
			<c:when test="${sessionScope.home.homeInstalledDevices == null}">
				<label style="padding: 0% 30% 0% 30%" class="control-label">You don't have installed devices in this home yet</label>
				<a href="/addNewDevice" class="btn btn-block btn-success">Install devices</a>
			</c:when>
			<c:otherwise>
				<form action="/updateDevice" class="form-horisontal" method="get">
				<div class="form-group">
					<label class="control-label">Choose device which you want to change:</label>
					<select id="inputState" class="form-control custom-select" name="deviceID">
						<c:forEach items="${sessionScope.home.homeInstalledDevices}" var="device">
							<option value="${device.deviceID}">${device.deviceName}, ${device.deviceDefinitionName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label class="control-label">Enter new device name:</label>
					<input type="text" class="form-control" name="deviceName">
				</div>
				<div class="form-group">
					<label class="control-label">Where you want to replace device?:</label>
					<select id="inputState" class="form-control custom-select" name="homeID">
						<c:forEach items="${homeAdminList}" var="homeAdmin">
							<option value="${homeAdmin.homeID}">${homeAdmin.homeName} ${homeAdmin.homeAddress}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label class="control-label">Select new device type:</label>
					<select id="inputState" class="form-control custom-select" name="deviceTypeID">
						<c:forEach items="${sessionScope.deviceTypeList}" var="deviceType">
							<option value="${deviceType.deviceTypeID}">${deviceType.deviceTypeName}</option>
						</c:forEach>
					</select>
					</select>
				</div>
				<button type ="submit" class="btn btn-success btn-block" name ="apply" value="true">Apply</button> 	
				</form>	
			</c:otherwise>
		</c:choose>
		<br>
        <p class="text-center">${sessionScope.deviceMessage}</p>
	</main>
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>