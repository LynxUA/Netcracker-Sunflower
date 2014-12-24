<%--
  Created by IntelliJ IDEA.
  User: denysburlakov
  Date: 20.12.14
  Time: 02:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
  <title>Error</title>
</head>
<body>
<%@include file="header.jsp"%>

<div id="body">
</div>

<h1 align="center">
  <%if(request.getParameter("info")==null){%>Sorry, an error has occured!<%}%>
  <%if(request.getParameter("info")=="user_assigned"){%>Error. Task has assigned user!<%}%>
  <%if(request.getParameter("info")=="has_task"){%>Error. You already have a task!<%}%>
</h1>

<%@include file="footer.jsp"%>
</body>
</html>