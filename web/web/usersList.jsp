<%--
  Created by IntelliJ IDEA.
  User: Den
  Date: 09.12.2014
  Time: 21:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
  <title>List of users</title>
</head>

<body>
<%@include file="header.jsp"%>
<div id="body">
  <div class="container">
    <h3>List of users</h3>
    <table class="table table-striped table-condensed">
      <thead>
      <tr>
        <th>Login</th>
        <th>E-mail</th>
        <th>Name</th>
        <th>Surname</th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td>admin</td>
        <td>admin@gmail.com</td>
        <td>admin</td>
        <td>surname</td>
      </tr>
      <tr>
        <td>User2</td>
        <td>user2@user2.com</td>
        <td>Denis</td>
        <td>Surnamovich</td>
      </tr>

      </tbody>
    </table>
  </div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
