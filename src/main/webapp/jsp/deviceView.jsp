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
		<div class="row">
				<c:forEach items="${home.homeInstalledDevices}" var="device">
					<div class="col col-6 align-items-start">
						<p>${device.deviceName}</p>
						<c:forEach items="${device.functions}" var="function">
							<form action="/changeDevice" class="form-horizontal" method="get">
								<label class="control-label">${function.functionName}</label>
								<c:choose>
									<c:when test="${function.functionInput}">
										<c:if test="${function.functionType eq 'BOOL'}">
											<div class="custom-control custom-switch">
												<input type="checkbox" class="custom-control-input" id="customSwitch1" name="1" value="true">
												<label class="custom-control-label" for="customSwitch1">True/False</label>
											</div>
											<br>
										</c:if>
										<c:if test="${function.functionType eq 'INTEGER'}">
											<div class="form-group">
												<label for="customRange1">From 1 to 100</label>
												<input type="range" class="custom-range" id="customRange1" min="0" max="100" name="1">
											</div>
											<br>
										</c:if>
										<c:if test="${function.functionType eq 'TEXT'}">
											<div class="form-group">
								              <label for="1" class="control-label">Enter function:</label>
								              <input type="text" class="form-control" name="1">
								            </div>
								            <br>
										</c:if>
										<button type="submit" class="btn btn-success btn-block" name"apply" value="true">Submit</button>
									</c:when>
									<c:otherwise>
										<c:if test="${function.functionType eq 'BOOL'}">
											<c:choose>
												<c:when test="${function.functionTrue}">
													true
												</c:when>
												<c:otherwise>
													false
												</c:otherwise>
											</c:choose>
											<br>
										</c:if>
										<c:if test="${function.functionType eq 'INTEGER'}">
											${function.functionInteger}
											<br>
										</c:if>
										<c:if test="${function.functionType eq 'TEXT'}">
											${function.functionText}
											<br>
										</c:if>
									</c:otherwise>
								</c:choose>
							</form>
						</c:forEach>
						<hr>
					</div>	
				</c:forEach>

		</div>
	</main>
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>