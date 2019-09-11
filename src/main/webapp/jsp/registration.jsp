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

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../css/style2.css">
</head>
<body>
    <main>
        <header>
            <h2><b>${sessionScope.registrationMessage}</b></h2>
        </header>
        <section>
            <form action = 'submitRegistration' method='post'>
                <p><label>Username:
                    <input type="text" placeholder="Login" name="login" required>
                </label></p>
                <p><label>Password:
                    <input type="password" placeholder="Enter Password" name="password" required>
                </label></p>
                <p><label>Repeat password
                    <input type="password" placeholder="Repeat Password" name="repeatedPassword" required>
                </label></p>
                <p><label>Your Fist Name:
                    <input type="text" placeholder="Enter your first name" name="firstName">
                </label></p>
                <p><label>Your Last Name:
                    <input type="text" placeholder="Enter your last name" name="lastName">
                </label></p>
                <p><input type='submit' value="Submit"></p>
            </form>
        </section>
    </main>
</body>
</html>
