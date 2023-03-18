<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 3/18/2023
  Time: 5:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Person</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Search Person</h1>
    <a href="person?action=create">Create person</a>
    <div class="row">
        <form class="form-inline" action="person" method="post">
            <div class="form-group mx-sm-3 mb-2">
                <label for="searchName" class="sr-only">Name</label>
                <input type="text" class="form-control" id="searchName" name="name" placeholder="Name">
            </div>
            <input class="btn btn-primary mb-2" type="submit" value="Search" name="action">
        </form>
    </div>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Phone 1</th>
            <th scope="col">Phone 2</th>
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${persons}" var="person">
            <tr scope="row">
                <td>${person.name}</td>
                <td>${person.phones.size() > 0 ? person.phones.get(0).number : ''}</td>
                <td>${person.phones.size() > 1 ? person.phones.get(1).number : ''}</td>
                <td>
                    <a href="person?action=update&id=${person.id}">Edit</a>
                </td>
                <td>
                    <form action="person" method="post" onsubmit="return confirm('Are you sure you want to delete this record?');">
                        <input type="hidden" name="id" value="${person.id}"/>
                        <input class="btn btn-danger mb-2" type="submit" name="action" value="Delete"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
