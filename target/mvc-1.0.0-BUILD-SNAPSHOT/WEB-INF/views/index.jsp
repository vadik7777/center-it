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
	    <form method = "GET" action = "/center_it/find" acc accept-charset="utf-8" enctype="application/x-www-form-urlencoded">
		    <table>
			    <tr>
				    <td>
					    <label for="service">Сервис</label>
				    </td>
				    <td>
					    <select name="service" id="service">
						    <c:if test="${not empty listService}">
							    <c:forEach var="service" items="${listService}">
								    <option value="${service}">${service}</option>
							    </c:forEach>
						    </c:if>
					    </select>
				    </td>
			    </tr>
			    <tr>
				    <td>
					    <label for="cities">Город</label>
				    </td>
				    <td>
					    <select name="city" id="city">
						    <c:if test="${not empty listCities}">
							    <c:forEach var="city" items="${listCities}">
								    <option value="${city}">${city}</option>
							    </c:forEach>
						    </c:if>
					    </select>
				    </td>
			    </tr>
				    <td colspan = "2">
					    <input type = "submit" value = "ОК"/>
				    </td>
			    </tr>
		    </table>
	    </form>
    </div>
</body>
</html>