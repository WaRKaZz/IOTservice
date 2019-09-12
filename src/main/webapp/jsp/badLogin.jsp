<%--
  Created by IntelliJ IDEA.
  User: NeoDCS
  Date: 02-Aug-19
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
<head>
    <title>IOT Service</title>
    <jsp:include page="cssIntegration.jsp"></jsp:include>
<body>
    <main>
        <header>
            <h2>Bad login</h2>
        </header>
        <section>
            <b>Oopps, wrong login or password, you will be redirected</b>
        </section>
    </main>        
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>
