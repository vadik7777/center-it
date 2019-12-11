
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
<link href="<c:url value="/resources/css/home.css"/>" rel="stylesheet">
<title>Главная</title>
</head>
<body>
	<p>Время сервера ${serverTime}.</p>
	<h2>Информация о погоде</h2>
	<c:if test="${not empty weatherData}">
		<dl>
			<c:forEach var="listValue" items="${weatherData}">
					<dt>Температура:</dt>
					<dd>${listValue.temp}</dd>
					<dt>Осадки:</dt>
					<dd>${listValue.rain}</dd>
					<dt>Влажность:</dt>
					<dd>${listValue.damp}</dd>
			</c:forEach>
		</dl>
	</c:if>
</body>
</html>