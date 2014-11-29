<%--
  Created by IntelliJ IDEA.
  User: Den
  Date: 29.11.2014
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
  <%@include file="header.jsp"%>
    <title>User's instances</title>
</head>
<body>
<h2>User's instances</h2>

<div class="bs-example">
  <table class="table table-striped table-bordered table-hover table-condensed">
  <thead style="background-color: #5f5e5d; color: white;">
  <tr>
    <th>Service name</th>
    <th>Status</th>
  </tr>
  </thead>
    <tbody>
    <tr class="success">
      <td>Instance1</td>
      <td>Active</td>
    </tr>
    <tr>
      <td>Instance2</td>
      <td>Planned</td>
    </tr>
    <tr class="danger">
      <td>Instance3</td>
      <td>Disconnected</td>
    </tr>
    <tr>
      <td>Instance4</td>
      <td>Planned</td>
    </tr>
    <tr>
      <td>Instance5</td>
      <td>Planned</td>
    </tr>
    <tr>
      <td>Instance6</td>
      <td>Planned</td>
    </tr>
    <tr>
      <td>Instance7</td>
      <td>Planned</td>
    </tr>
    </tbody>
  </table>
</div>
</body>
</html>
