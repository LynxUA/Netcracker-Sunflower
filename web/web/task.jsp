<%--
  Created by IntelliJ IDEA.
  User: Den
  Date: 09.12.2014
  Time: 20:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
  <title>Tasks</title>
</head>

<body>
<%@include file="header.jsp"%>
<div id="body">
  <div class="container">
    <h3>Tasks</h3>
    <table class="table table-striped table-condensed">
      <thead>
      <tr>
        <th>Description</th>
        <th>Status</th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td>do task1</td>
        <td><span class="label label-success">completed</span></td>
      </tr>

      <tr>
        <td>do task2</td>
        <td><span class="label label-warning">assigned</span></td>
      </tr>

      <tr>
        <td>do task3</td>
        <td><span class="label label-danger">not assigned</span></td>
      </tr>

      </tbody>
    </table>
  </div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
