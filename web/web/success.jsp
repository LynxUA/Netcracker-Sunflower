<%--
  Created by IntelliJ IDEA.
  User: denysburlakov
  Date: 20.12.14
  Time: 01:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
  <title>Success</title>
</head>
<body>
<%@include file="header.jsp"%>

<div id="body">
</div>

<%
  String info = request.getParameter("info");
  if(info==null){%>
  <h1 align="center">You are successful person!</h1>
<%}else if(info.contains("password_changed")){%>
  <h1 align="center">Password was successfully changed!</h1>
<%}else if(info.contains("info_changed")){%>
  <h1 align="center">Your information was successfully changed!</h1>
<%}%>

<%@include file="footer.jsp"%>
</body>
</html>