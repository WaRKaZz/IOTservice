<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>


<html>
<head>
    <title>IOT Service/main</title>
    <jsp:include page="cssIntegration.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="navbarLogined.jsp"></jsp:include>
    <header>
      <h2 class="text-center">Wellcome! Here you can choose you'r Home:</h2>
    </header>
    <main>
      <form action="chooseHome" method="get" class="form-horisontal">
        <div class="row">
          <div class="col-12">
            <h5>${sessionScope.home eq null ? 'You have no chosen home yet, please choose:': 'Chosen home is:'}
              <c:if test="${sessionScope.home != null}">
                ${sessionScope.home.homeName} ${sessionScope.home.homeAddress}
              </c:if>
            </h5>
          </div>

          <div class = "col-6">
            <p class="text">You admin in: </p>
            <c:forEach items="${homeAdminList}" var = "homeChosen">
              <div class="radio">
                  <label><input type="radio" name="homeID" value="${homeChosen.homeID}"> ${homeChosen.homeName} ${homeChosen.homeAddress}</label>
              </div>
            </c:forEach>
          </div>  
          <div class = "col-6">
            <div class="col-12">
              <p class="text">You user in: </p>
            </div>
            <c:forEach items="${homeUserList}" var = "homeChosen">
              <div class="radio">
                  <label><input type="radio" name="homeID" value="${homeChosen.homeID}"> ${homeChosen.homeName} ${homeChosen.homeAddress}</label>
              </div>
            </c:forEach>
            </div>  
          <button type="submit" class="btn btn-success btn-block">Submit</button>
        </div>
      </form>
    </main>
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>
