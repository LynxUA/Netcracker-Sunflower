<%@ page import="com.sunflower.ejb.EJBFunctions" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.sunflower.ejb.task.TaskWrapper" %>
<%--
  Created by IntelliJ IDEA.
  User: Den
  Date: 09.12.2014
  Time: 21:24
  To change this template use File | Settings | File Templates.
--%>
<%
  String order_login = (String) (request.getSession().getAttribute("login"));

  int from = 1;
  int to = 10;
  if(request.getParameter("to")!=null&&request.getParameter("from")!=null){
    to = Integer.parseInt(request.getParameter("to"));
    from = Integer.parseInt(request.getParameter("from"));
  }
  Vector<TaskWrapper> tasks = EJBFunctions.getTasksByEngineer((Integer)(request.getSession().getAttribute("status")), from, to);
  int numberOfRecords = EJBFunctions.getNumberOfTasksByEngineer((Integer)(request.getSession().getAttribute("status")));
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <%@include file="includes.jsp"%>
  <title>List of tasks</title>
</head>

<body>
<%@include file="header.jsp"%>
<div id="body">
  <div class="container" style="padding-bottom: 30px">
    <h3>List of tasks</h3>
    <%if(tasks.size() == 0){%>

    <h1>Sorry, but you don`t have any tasks.</h1>
    <%} else {%>
    <table class="table table-striped table-condensed">
      <thead>
      <tr>
        <th>Order ID</th>
        <th>Scenario</th>
        <th>User's login</th>
        <th>User's coordinates</th>
        <th>Office</th>
        <th>Service</th>
        <th>Description</th>
      </tr>
      </thead>
      <tbody>
      <%for(TaskWrapper task:tasks) {%>
      <tr>
        <td><%=task.getId_order()%></td>
        <td><%=task.getScenarionName()%></td>
        <td><%=task.getLogin()%></td>
        <td><%=task.getLng()%> <%=task.getLat()%></td>
        <td><%=task.getOfficeName()%></td>
        <td><%=task.getServiceName()%></td>
        <td><%=task.getDescription()%></td>
        <td><a href="getTask?id_task=<%=task.getId_task()%>">Get task</a></td>
      </tr>
      <% }%>
      </tbody>
    </table>
    <%}%>
    <%if(numberOfRecords >10){%>
    <div class="pagination pagination-centered">
      <ul class="pagination pagination-centered">
        <li class="<%if(to/10==1){%>disabled<%}else{%>active<%}%>"><a href="users?from=1&to=10">&laquo;</a></li>
        <%if(!(to/10 == 1)){%><li class="active"><a href="users?from=<%=from-10%>&to=<%=to-10%>"><%=(to-10)/10%></a></li><%}%>
        <li class="disabled"><a href="#"><%=to/10%></a></li>
        <%if(!(from+10 > numberOfRecords )){%><li class="active"><a href="users?from=<%=from+10%>&to=<%=to+10%>"><%=(to+10)/10%></a></li><%}%>
        <%if(!(from+20 > numberOfRecords )){%><li class="active"><a href="users?from=<%=numberOfRecords - (numberOfRecords%10)%>&to=<%=numberOfRecords/10%>">&raquo;</a></li><%}%>
      </ul>
    </div>
    <%}%>
  </div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
