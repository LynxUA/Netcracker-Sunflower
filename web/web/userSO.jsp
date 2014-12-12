<%@ page import="com.sunflower.ejb.EJBFunctions" %>
<%@ page import="com.sunflower.ejb.ServiceOrder.LocalServiceOrder" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.sunflower.ejb.ServiceOrder.SOWrapper" %>
<%@ page import="java.util.Vector" %>
<%--
  Created by IntelliJ IDEA.
  User: Andriy
  Date: 12/6/2014
  Time: 11:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%
    String order_login = (String)(request.getSession().getAttribute("login"));
    int from = 1;
    int to = 10;
    if(request.getParameter("to")!=null&&request.getParameter("from")!=null){
        to = Integer.parseInt(request.getParameter("to"));
        from = Integer.parseInt(request.getParameter("from"));
    }
    Vector<SOWrapper> serviceOrders = (Vector<SOWrapper>)
            (EJBFunctions.findOrderByLogin(order_login, from, to));
    int numberOfRecords = EJBFunctions.getNumberOfOrdersByLogin(order_login);
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
  <title>User service orders</title>
</head>
<body>


<%@include file="header.jsp"%>


  <div class="container">
    <%if(serviceOrders.size() == 0){%>

    <h1>Sorry, but you don`t have any service orders.</h1>
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
          <%for(SOWrapper order:serviceOrders){%>
          <td><%=order.getId_order()%></td>
          <td><%=order.getScenario_name()%></td>
          <td><span
              <%
                        if(order.getStatus_name().equals("Entering")){
                        %>
                  class="label-primary"
            <%
                        } else if(order.getStatus_name().equals("Processing")){
                        %>
                  class="label label-warning"
            <%
                        } else if(order.getStatus_name().equals("Completed")){
                        %>
                  class="label label-success"
            <%
                        }else if(order.getStatus_name().equals("Cancelled")){
                        %>
                  class="label label-danger"
            <%
                        }
                        %>

                  >
              <%=order.getStatus_name()%>
          </td>
          </tr>
          <%}%>
          </tbody>
        </table>
      </div>
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
<%@include file="footer.jsp"%>
</body>
</html>
