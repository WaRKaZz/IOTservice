<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="${lang.languageLocale}"/>
<nav class="navbar navbar-expand-md navbar-light bg-light sticky-top">
    <a href="#" class="navbar-brand"><fmt:message key="key.navBrand"/></a>
    <ul class="nav navbar-nav">
        <li class="nav-item">
            <a class="nav-link" href="/language"><fmt:message key="key.navLanguage"/></a>
        </li>
    </ul>
    <ul class="nav navbar-nav ml-auto">
        <li class="nav-item">
            <a class="nav-link" href="/"><fmt:message key="key.navSignIn"/></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/registration"><fmt:message key="key.navSignUp"/></a>
        </li>
    </ul>
</nav>