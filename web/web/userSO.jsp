<%@ page import="com.sunflower.ejb.EJBFunctions" %>
<%@ page import="com.sunflower.ejb.ServiceOrder.LocalServiceOrder" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: Andriy
  Date: 12/6/2014
  Time: 11:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
  <title>User service orders</title>
</head>
<body>
--CHange it to getting all user`s SOs
<%ArrayList<LocalServiceOrder> serviceOrders = new ArrayList<LocalServiceOrder>();
  serviceOrders.add((LocalServiceOrder) EJBFunctions.findServiceOrder(1));%>

<%@include file="header.jsp"%>

<div id="body">

  <div class="container">
    <%if(serviceOrders.size() == 0){%>

    Sorry, but you don`t have any service orders.
    <%} else {%>
    <div class="row">
      <div class="span5">
        <table class="table table-striped table-condensed">
          <thead>
          <tr>
            <th>id</th>
            <th>Scenario</th>
            <th>Status</th>
          </tr>
          </thead>
          <tbody>
          <%for(int i = 0; i < serviceOrders.size(); i++){%>
          <td><%=serviceOrders.get(i).getId()%></td>
          <td><%=serviceOrders.get(i).getScenario()%></td>
          entering, processing, completed, cancelled
          <td><span
            <%
                        if(serviceOrders.get(i).getStatus().toLowerCase().compareTo("entering") == 0){
                        %>
                  class="label-primary"
            <%
                        } else if(serviceOrders.get(i).getStatus().toLowerCase().compareTo("processing") == 0){
                        %>
                  class="label label-warning"
            <%
                        } else if(serviceOrders.get(i).getStatus().toLowerCase().compareTo("completed") == 0){
                        %>
                  class="label label-success"
            <%
                        }else {
                        %>
                  class="label label-danger"
            <%
                        }
                        %>

                  >
              <%=serviceOrders.get(i).getStatus()%>
          </td>
          </tr>
          <%}%>
          </tbody>
        </table>
      </div>
    </div>
  </div>
  <%}%>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
