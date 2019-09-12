<%--
  Created by IntelliJ IDEA.
  User: NeoDCS
  Date: 02-Aug-19
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>


<html>
<head>
    <title>IOT Service/main</title>
    <jsp:include page="cssIntegration.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="navbarLogined.jsp"></jsp:include>
    <main style="padding:5% 10% 0% 10%;">
      <div class="container">
        <p style="text-align: center;">You are logined as ${sessionScope.user.userLogin}</p>
      </div>

    </main>
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>
