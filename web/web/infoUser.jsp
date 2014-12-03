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

<div id="body">
  <table class="table table-condensed" width="20" >

    <tr width="20">
      <td >#</td>
      <th >Имя</th>
      <th >Фамилия</th>
      <th >Имя пользователя</th>
    </tr>

    <tbody>
    <tr>
      <td rowspan="3">1</td>
      <td rowspan="2">Mark</td>

    </tr>
    <tr>
      <td>2</td>
      <td>Jacob</td>

    </tr>
    <tr>
      <td>" " </td>
      <td>  </td>
    </tr>
    <tr>
      <td></td>
      <td></td>
    </tr>
    </tbody>
  </table>
</div>


<%@include file="footer.jsp"%>

</html>
