<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<h2>${sessionScope.user.userFirstName} ${sessionScope.user.userLastName}</h2>
<ul>
	<li><a href="">Главная</a></li>
	<li><a href="">Мой дом</a></li>
	<li><a href="">Администрирование</a></li>
	<li><a href="">Контакты</a></li>
	<li><a href="/logout">Выйти</a></li>
</ul>