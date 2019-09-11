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
    <link rel="stylesheet" href="../css/style2.css">
</head>
<body>
    <div class="wrap">
        <header>
                <h2>Welcome!</h2>
        </header>
        <div class="content">
            <p>You are logined as ${sessionScope.user.userLogin}</p>
            <p><form action = 'logout' method='post'>
                <button type="submit">Logout</button>
            </form></p>
        </div>
        <aside>
            <jsp:include page="navigationMain.jsp"></jsp:include>
        </aside>
    </div>
</body>
</html>
