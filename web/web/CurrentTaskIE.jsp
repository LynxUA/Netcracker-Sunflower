<%-- 
    Document   : current task
    Created on : Nov 29, 2014, 8:08:09 PM
    Author     : Алексей
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="includes.jsp"%>
    <title>Current Task for IE</title>
    <style>
        aside {
            padding: 10px;
            width: 200px;
            float: left;
        }
        p{padding: 1% 7% 1% 7%;
            margin: 1% 7% 1% 7%   }
        form{display:inline;}

    </style>
</head>
<body>
<%@include file="header.jsp"%>
<%@ page import="com.sunflower.ejb.EJBFunctions"%>
<%@ page import="com.sunflower.ejb.task.LocalTask"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="com.sunflower.ejb.DataSource" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="javax.ejb.EJBException" %>
<%@ page import="javax.ejb.CreateException" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="javax.ejb.ObjectNotFoundException" %>
<%@ page import="javax.ejb.FinderException" %>

<aside>
    <ul class="nav nav-list bs-docs-sidenav affix">
        <li>
            <a href="CreateDC.jsp">IE panel</a>
        </li>

        <li>
            <a href="CurrentTaskIE.jsp">Current Task</a>
        </li>
    </ul>
</aside>
<div style="padding: 1% 7% 1% 7%;
     margin: 1% 7% 1% 7%" >
    <table class="table  table-bordered table-striped" height="90">
        <thead>
        <tr >

            <th align=CENTER >Description</th>

        </tr>
        </thead>
        <tbody>
        <tr>

            <ul class="nav nav-list bs-docs-sidenav affix">
                    <%
                    boolean state=true;
                     Connection connection = null;
        PreparedStatement statement = null;
         ResultSet resultSet = null;
        try {
            try{
                connection = DataSource.getDataSource().getConnection();
            } catch (SQLException e) {
                throw new EJBException("Ошибка dataSource");
            }
            statement = connection.prepareStatement("SELECT t.ID_TASK, t.DESCRIPTION, so.ID_Order" +
             " from SERVICE_ORDER so INNER  JOIN PRICE p ON so.ID_PRICE=p.ID_PRICE INNER  join PROVIDER_LOCATION pl on p.ID_PROV_LOCATION=pl.ID_PROV_LOCATION Inner JOIN  Task t On t.Id_order=so.Id_order inner JOIN SERVICE_INSTANCE si On si.ID_SERVICE_INST=so.ID_SERVICE_INST where t.login like ? and so.ID_status!=4 and so.ID_status!=2 ");

            statement.setString(1, (String)request.getSession().getAttribute("login"));
            try{
            resultSet = statement.executeQuery();
           if(!resultSet.next())
            {
             throw new FinderException();
            }
            }catch (FinderException e)
            {
                state = false;
            }

                if(state) {
            %>

                <td align="Center"><%=resultSet.getString(2)%></td>
                <% }else {%>
                        <td align="Center">No tasks</td>
                            <% } %>
        </tr>
        </tbody>
    </table>
</div>
<%if (state)
{%>
<div style="padding: 1% 7% 1% 7%;
     margin: 1% 7% 1% 7%" >
<form method="get" action="currenttask">
    <input type="hidden" name="key" value="<%=resultSet.getInt(1)%>" style=" width:0">
    <input type="hidden" name="action" value="completeIE" style=" width:0">
    <input type="hidden" name="Description" value="<%=resultSet.getString(2)%>" style=" width:0">
    <button type="submit" class="btn btn-primary" value="Complete">Complete</button>
</form>
<form method="get" action="currenttask">
    <input type="hidden" name="key" value="<%=resultSet.getInt(1)%>" style=" width:0">
    <input type="hidden" name="Id_Order" value="<%=resultSet.getInt(3)%>" style=" width:0">
    <input type="hidden" name="action" value="suspendIE" style=" width:0">
    <button type="submit" class="btn btn-primary" value="Suspend">Suspend</button>
    </form>

<form method="get" action="currenttask">
    <input type="hidden" name="key" value="<%=resultSet.getInt(1)%>" style=" width:0">
    <input type="hidden" name="action" value="unassignIE" style=" width:0">
    <button type="submit" class="btn btn-primary" value="unassign">Unassign Task</button>
</form>
</div>
<%} else{%>
<p>
    <button type="submit" class="btn btn-primary" value="Complete" disabled>Complete</button>

    <button type="submit" class="btn btn-primary" value="Suspend" disabled>Suspend</button>
    <button type="submit" class="btn btn-primary" value="unassign" disabled>Unassign Task</button>
</p>
<%}%>
<%if(request.getAttribute("result") != null && !((String) request.getAttribute("result")).isEmpty()){%>
<div  style="padding: 1% 7% 1% 7%;
     margin: 1% 7% 1% 7%"  name="result">${requestScope.result}</div>
<%}%>

<%@include file="footer.jsp"%>
</body>
</html>
<%  } catch (SQLException e) {
    System.out.println(e.getMessage());
    throw new EJBException("SELECT exception in TaskIe");
    } finally {
    try {
    if (connection != null) {
    connection.close();
    }
    } catch (SQLException e) {
    e.printStackTrace();
    }
    } %>
