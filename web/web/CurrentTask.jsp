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
    <title>Current Task</title>
    <style>
        aside {
            padding: 10px;
            width: 200px;
            float: left;
        }
        p{padding: 1% 7% 1% 7%;
            margin: 1% 7% 1% 7%  ; }
        form{display:inline;}

    </style>
</head>
<body>
<%@include file="header.jsp"%>
<%@ page import="com.sunflower.ejb.EJBFunctions"%>
<%@ page import="com.sunflower.ejb.task.LocalTask"%>

<aside>
    <ul class="nav nav-list bs-docs-sidenav affix">
        <li>
            <a href="assign.jsp">Assign port</a>
        </li>
        <li>
            <a href="modify.jsp">Modify</a>
        </li>
        <li>
            <a href="CurrentTask.jsp">Current Task</a>
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
                LocalTask localTask;
                localTask=EJBFunctions.findIncompleteTask();
                System.out.println(localTask.getDescription());

                if(localTask!=null) {
            %>

                <td align="Center"><%=localTask.getDescription()%></td>
                <% }else {%>
                        <td align="Center">No tasks</td>
                            <% } %>
        </tr>
        </tbody>
    </table>
</div>
<%if (localTask!=null)
{%>
<p>
<form method="get" action="currenttask?action=complete&key=<%=localTask.getId_task()%>">
    <button type="submit" class="btn btn-primary" value="Complete">Complete</button>
</form>
<form method="get" action="currenttask?action=suspend&key=<%=localTask.getId_task()%>">
    <button type="submit" class="btn btn-primary" value="Suspend">Suspend</button>
    </form>
</p>
<%} else{%>
<p>
    <button type="submit" class="btn btn-primary" value="Complete" disabled>Complete</button>

    <button type="submit" class="btn btn-primary" value="Suspend" disabled>Suspend</button>
</p>
<%}%>

<%@include file="footer.jsp"%>
</body>
</html>
