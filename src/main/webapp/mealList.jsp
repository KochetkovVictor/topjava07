<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    <%@include file='/color.css' %>
</style>
<html>
<head>
    <title>Meal List</title>
</head>
<body>
<link href="color.css" rel="stylesheet" type="text/css"  property="class">
<a href="meals?action=add">Add Meal</a>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <c:forEach items="${mealList}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.UserMealWithExceed"/>
        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
            <td>
                    ${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
