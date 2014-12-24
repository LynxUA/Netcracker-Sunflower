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
  String info = request.getParameter("info");%>
  <h1 align="center">
<%if(info==null){%>
  You are successful person!
<%}else if(info.equals("password_changed")){%>
  Password was successfully changed
<%}else if(info.equals("info_changed")){%>
  Your information was successfully changed!
<%}else if(info.equals("user_registered")){%>
  Thank you for registration in our system! Your user info was sent to your email.
<%}else if(info.equals("ordered")){%>
  Thank you for using our service! Order is being processed.
<%}else if(info.equals("order_canceled")){%>
  Order was successfully canceled!
<%}else if(info.equals("user_created")){%>
    User was successfully created!
    <%}%></h1>

<%@include file="footer.jsp"%>
</body>
</html>