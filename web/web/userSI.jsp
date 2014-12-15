<%@ page import="com.sunflower.ejb.serviceinstance.SIWrapper" %>
<%@ page import="com.sunflower.ejb.EJBFunctions" %>
<%@ page import="java.util.Vector" %>
<%--
  Created by IntelliJ IDEA.
  User: denysburlakov
  Date: 15.12.14
  Time: 20:10
  To change this template use File | Settings | File Templates.
--%>
<%
  int user_status = (Integer)(request.getSession().getAttribute("status"));
  String order_login;
  if(user_status == 1) {
    order_login = (String) (request.getSession().getAttribute("login"));
  }else if (user_status == 3){
    order_login = request.getParameter("login");
  }else{
    response.sendRedirect("/webWeb/");
    return;
  }
  int from = 1;
  int to = 10;
  if(request.getParameter("to")!=null&&request.getParameter("from")!=null){
    to = Integer.parseInt(request.getParameter("to"));
    from = Integer.parseInt(request.getParameter("from"));
  }
  Vector<SIWrapper> serviceInstances = (Vector<SIWrapper>)
          (EJBFunctions.findOrderByLogin(order_login, from, to));
  int numberOfRecords = EJBFunctions.getNumberOfInstancesByLogin(order_login);
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
  <title>User service instances</title>
</head>
<body>


<%@include file="header.jsp"%>


<div class="container" style="padding-bottom: 30px">
  <h3>Service orders</h3>
  <%if(serviceInstances.size() == 0){%>

  <h1>Sorry, but you don`t have any service instances.</h1>
  <%} else {%>
  <div class="row">
    <div class="span5">
      <table class="table table-striped table-condensed">
        <thead>
        <tr>
          <th>SI ID</th>
          <th>Status</th>
          <th>Service</th>
          <th>Service Location</th>
          <th>Provider office</th>
        </tr>
        </thead>
        <tbody>
        <%for(SIWrapper instance:serviceInstances){%>
        <td><%=instance.getId_service_inst()%></td>
        <td><span
                <%
                if(instance.getStatusName().compareTo("Planned")==0){
                %>
                class="label label-warning"
                <%
                } else if(instance.getStatusName().equals("Active")){
                %>
                class="label label-success"
                <%
                }else if(instance.getStatusName().equals("Disconnected")){
                %>
                class="label label-danger"
                <%
                  }
                %>
                >
              <%=instance.getStatusName()%></span>
        </td>
        <td><%=instance.getServiceName()%></td>
        </tr>
        </td>
        <td><%=instance.getLongtitude()%> <%=instance.getLatitude()%></td>
        </td>
        <td><%=instance.getLocation()%></td>
        </tr>
        </tr>
        <%}%>
        </tbody>
      </table>
    </div>
  </div>
  <%}%>
  <%if(numberOfRecords >10){%>
  <div class="pagination pagination-centered">
    <ul class="pagination pagination-centered">
      <li class="<%if(to/10==1){%>disabled<%}else{%>active<%}%>"><a href="user_so?from=1&to=10">&laquo;</a></li>
      <%if(!(to/10 == 1)){%><li class="active"><a href="user_so?from=<%=from-10%>&to=<%=to-10%>"><%=(to-10)/10%></a></li><%}%>
      <li class="disabled"><a href="#"><%=to/10%></a></li>
      <%if(!(from+10 > numberOfRecords )){%><li class="active"><a href="user_so?from=<%=from+10%>&to=<%=to+10%>"><%=(to+10)/10%></a></li><%}%>
      <%if(!(from+20 > numberOfRecords )){%><li class="active"><a href="user_so?from=<%=numberOfRecords - (numberOfRecords%10)%>&to=<%=numberOfRecords/10%>">&raquo;</a></li><%}%>
    </ul>
  </div>
  <%}%>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
