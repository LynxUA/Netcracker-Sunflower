<%@ page import="com.sunflower.ejb.EJBFunctions" %>
<%@ page import="com.sunflower.ejb.ServiceOrder.LocalServiceOrder" %>
<%@ page import="java.util.Collection" %>
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
<%----CHange it to getting all user`s SOs--%>
<%Collection serviceOrders = EJBFunctions.findOrderByLogin((String)request.getSession().getAttribute("login"));%>

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
          Entering == 1, Cancelled == 2, Processing == 3, Completed ==4 fix later
          <%for(Object order:serviceOrders){%>
          <td><%=((LocalServiceOrder)order).getId_order()%></td>
          <td><%=((LocalServiceOrder)order).getId_scenario()%></td>
          <td><span
            <%
                        if(((LocalServiceOrder)order).getId_status() == 1){
                        %>
                  class="label-primary"
            <%
                        } else if(((LocalServiceOrder)order).getId_status() == 3){
                        %>
                  class="label label-warning"
            <%
                        } else if(((LocalServiceOrder)order).getId_status() == 4){
                        %>
                  class="label label-success"
            <%
                        }else if(((LocalServiceOrder)order).getId_status() == 2){
                        %>
                  class="label label-danger"
            <%
                        }
                        %>

                  >
              <%=((LocalServiceOrder)order).getId_status()%>
          </td>
          </tr>
          <%}%>
          New == 1, Modify == 2, Disconnect == 3
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
