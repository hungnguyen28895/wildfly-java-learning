<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 3/18/2023
  Time: 5:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Update Person</h1>
    <div class="row border">
        <div class="col-xl-4 col-lg-4 col-md-4">
            <form action="person" method="post">
                <div class="form-group mb-2">
                    <input class="form-control" name="id" type="hidden" value="${person.id}">
                </div>
                <div class="form-group mb-2">
                    <input class="form-control" name="name" type="text" value="${person.name}" placeholder="Name">
                </div>
                <div class="form-group mb-2">
                    <input
                            class="form-control"
                            name="number1"
                            type="text"
                            value="${person.phones.size() > 0 ? person.phones.get(0).number : ''}"
                            placeholder="Number 1">
                </div>
                <div class="form-group mb-2">
                    <input
                            class="form-control"
                            name="number2"
                            type="text"
                            value="${person.phones.size() > 1 ? person.phones.get(1).number : ''}"
                            placeholder="Number 2">
                </div>
                <%--    <c:forEach items="${person.phones}" var="phone" varStatus="loop">--%>
                <%--        <p>--%>
                <%--            <input name="number${loop.index+1}" type="text" value="${phone.number}" placeholder="Number ${loop.index+1}" >--%>
                <%--        </p>--%>
                <%--    </c:forEach>--%>
                <input class="btn btn-primary" type="submit" value="Update" name="action">
            </form>
        </div>
    </div>
    <div class="row mb-4">
        <a href="person?action=search">Search person</a>
    </div>
</div>
</body>
</html>