<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 3/20/2023
  Time: 10:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>
<body>
    <h1>Welcome to FAI Bank</h1>

    <h3>Sysstem Login</h3>
    <form action="bank" method="post">
        <input type="text" name="loginAccountNo" placeholder="Account No">
        <input type="password" name="pinCode" placeholder="Enter Pin Code...">
        <input type="submit" value="Login" name="action">
    </form>
</body>
</html>
