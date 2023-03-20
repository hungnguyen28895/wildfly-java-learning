<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 3/20/2023
  Time: 10:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Transfer</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>
<body>
    <h2>Transfer within FAI Bank</h2>
    <br/>
    <h2>Hello, ${account.accountName}</h2>
    <span>$${account.balance}</span>

    <form action="bank" method="post">
        <input type="hidden" name="accountNoFrom" value="${account.accountNo}">
      <input type="text" name="accountNoTo" placeholder="Enter Account No">
      <input type="text" name="amount" placeholder="Enter amount...">
      <textarea name="comment" placeholder="Enter description..."></textarea>
      <input type="submit" value="Perform" name="action">
    </form>
</body>
</html>
