


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
  <title>Netcracker Sunflower</title>
</head>
<body>

<%@include file="header.jsp"%>

<div id="body">
  <h1>Input location </h1>

  <form action="catalogResponce.jsp" method="POST">
    <input type="text" name="location" value="" />
    <input type="submit" value="Submit">


<%@include file="footer.jsp"%>
</body>
</html>
