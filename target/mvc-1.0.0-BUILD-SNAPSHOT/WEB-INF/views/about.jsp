
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
<link href="<c:url value="/resources/css/home.css"/>" rel="stylesheet">
<title>Главная</title>
</head>
<body>
	<div style="margin: 20px;">
		<h2>Информация о погоде</h2>
		<dl>
			<dt>Сервис:</dt>
			<dd>${weatherData.service}</dd>
			<dt>Дата последнего запроса:</dt>
			<dd>${weatherData.date}</dd>
			<dt>Город:</dt>
			<dd>${weatherData.city}</dd>
			<dt>Температура:</dt>
			<dd>${weatherData.temp}</dd>
			<dt>Влажность:</dt>
			<dd>${weatherData.humidity}</dd>
			<dt>Давление:</dt>
			<dd>${weatherData.pressure}</dd>
		</dl>
	</div>
</body>
</html>