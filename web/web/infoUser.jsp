<%--
  Created by IntelliJ IDEA.
  User: Dima
  Date: 02.12.2014
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>User info</title>
  <%@include file="includes.jsp"%>
  <style>

  </style>
</head>
<body>

<%@include file="header.jsp"%>

<table cellpadding="50" width="50%">
  <tbody>
  <tr>
    <th>Name</th>
    <th>Surname</th>
    <th>Telephone</th>
    <th>Location</th>
    <th>Service</th>
    <th>Status</th>
    <th>Balance</th>

  </tr>

  <tr>
    <td><h4>Petro</h4></td>
    <td><h4>Petrov</h4></td>
    <td><h4>0109090909</h4> </td>
    <td><h4>Poznichki</h4></td>
    <td><h4>Unlimited</h4></td>
    <td><h3 style="color:green">Online</h3></td>
    <td><h4>50</h4></td>
  </tr>
  </tbody>
</table>
  <p>
    <button class="btn btn-large btn-primary" type="button">Block</button>
  </p>


<%@include file="footer.jsp"%>

</html>
