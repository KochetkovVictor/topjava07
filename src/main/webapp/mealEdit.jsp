<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal List</title>
</head>
<body>
<h1>Edit Meal</h1>
<form method="post" action="meals">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt>DateTime</dt>
        <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
    </dl>
    <dl>
        <dt>Description</dt>
        <dd><input type="text" value="${meal.description}" name="description"></dd>
    </dl>
    <dl>
        <dt>Calories</dt>
        <dd><input type="number" value="${meal.calories}" name="calories"></dd>
    </dl>
    <button type="submit">
        Ok
    </button>
    <button onclick="window.history.back()">
        Cancel
    </button>
</form>
</body>
</html>