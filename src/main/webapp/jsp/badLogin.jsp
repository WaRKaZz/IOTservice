<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<fmt:setBundle basename="${lang.languageLocale}" />

<!DOCTYPE html>
<html>
<head>
    <title>IOT Service</title>
    <jsp:include page="cssIntegration.jsp"></jsp:include>
<body>
    <main>
        <header>
            <h2><fmt:message key="key.badLogin"/></h2>
        </header>
        <section>
            <b><fmt:message key="key.badLoginMessage"/></b>
        </section>
    </main>        
    <jsp:include page="javascriptIntegration.jsp"></jsp:include>
</body>
</html>
