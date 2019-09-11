<%--
  Created by IntelliJ IDEA.
  User: NeoDCS
  Date: 09/09/2019
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<html>
<head>
    <title>IOT Service</title>
    <link rel="stylesheet" href="../css/style2.css">
</head>
<body>
    <main>
        <header>
            <h2>Login page:</h2>
        </header>
        <section class="loginForm">
            <form action = 'login' method='post'>
                <p><label>Username:
                <input type="text" placeholder="Your login" name="login" required></label></p>
                <p><label>Password:
                <input type="password" placeholder="Enter Password" name="password" required></label></p>
                <button type="submit">Login</button>
            </form>
            <form action = 'registration' method='post'>
                <button type="submit"> sign up! </button>
            </form>            
        </section>
    </main>
</body>
</html>
