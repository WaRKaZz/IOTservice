<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="${lang.languageLocale}"/>
<nav class="navbar navbar-expand-md navbar-light bg-light sticky-top">
    <a href="#" class="navbar-brand"><fmt:message key="key.navBrand"/></a>
    <ul class="nav navbar-nav">
        <li class="nav-item">
            <a class="nav-link" href="/"><fmt:message key="key.navMyhome"/></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/devices"><fmt:message key="key.navDevices"/></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/settings"><fmt:message key="key.navSettings"/></a>
        </li>
    </ul>
    <ul class="nav navbar-nav ml-auto">
        <li class="nav-item">
            <a class="nav-link" href="/profile">${sessionScope.user.userLogin}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/logout"><fmt:message key="key.logout"/></a>
        </li>
    </ul>
</nav>