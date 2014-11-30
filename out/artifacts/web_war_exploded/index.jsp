<%--
  Created by IntelliJ IDEA.
  User: denysburlakov
  Date: 16.11.14
  Time: 00:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
  <title>Netcracker Sunflower</title>
</head>
<body>

<%@include file="header.jsp"%>

<div id="body">
  <form action="report/"><input type="submit" type="button" value="Reports"></form>
  <form action="helloworld"><input type="submit" type="button" value="Bean test"></form>
</div>


<%@include file="footer.jsp"%>
</body>
</html>
