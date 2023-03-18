<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 3/18/2023
  Time: 5:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Create Person</h1>
    <div class="row border">
        <div class="col-xl-4 col-lg-4 col-md-4">
            <form action="person" method="post">
                <div class="form-group mb-2">
                    <input class="form-control" name="name" type="text" placeholder="Name">
                </div>
                <div class="form-group mb-2">
                    <input class="form-control" name="number1" type="text" placeholder="Number 1">
                </div>
                <div class="form-group mb-2">
                    <input class="form-control" name="number2" type="text" placeholder="Number 2">
                </div>
                <input class="btn btn-primary mb-2" name="action" value="Add" type="submit">
            </form>
        </div>
    </div>
    <div class="row mb-4">
        <a href="person?action=search">Search person</a>
    </div>
</div>
</body>
</html>
