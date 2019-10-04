<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<fmt:setBundle basename="${lang.languageLocale}"/>

<html>
<head>
    <title>IOT Service/main</title>
    <jsp:include page="cssIntegration.jsp"></jsp:include>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
    <jsp:include page="navbarLogined.jsp"></jsp:include>
    <header>
      <h2 class="text-center"><fmt:message key="key.welcome"/></h2>
    </header>
    <main>
      <form action="chooseHome" method="post" class="form-horisontal">
        <div class="row">
          <div class="col-12">
            <h5>
              <c:choose>
                <c:when test="${sessionScope.home eq null}">
                  <fmt:message key="key.notChosenHome"/>
                </c:when>
                <c:otherwise>
                  <fmt:message key="key.chosenHome"/>  ${sessionScope.home.homeName} ${sessionScope.home.homeAddress}
                </c:otherwise>
              </c:choose>
            </h5>
          </div>

          <div class = "col-6">
            <p class="text"><fmt:message key="key.youAdmin"/></p>
            <c:forEach items="${homeAdminList}" var = "homeChosen">
              <div class="radio">
                  <label><input type="radio" name="homeID" value="${homeChosen.homeID}"> ${homeChosen.homeName} ${homeChosen.homeAddress}</label>
              </div>
            </c:forEach>
          </div>  
          <div class = "col-6">
            <div class="col-12">
              <p class="text"><fmt:message key="key.youUser"/></p>
            </div>
            <c:forEach items="${homeUserList}" var = "homeChosen">
              <div class="radio">
                  <label><input type="radio" name="homeID" value="${homeChosen.homeID}"> ${homeChosen.homeName} ${homeChosen.homeAddress}</label>
              </div>
            </c:forEach>
            </div>  
          <button type="submit" class="btn btn-success btn-block"><fmt:message key="key.submitButton"/></button>
        </div>
      </form>
    </main>
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>
