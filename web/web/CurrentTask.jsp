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
            div {padding: 1% 10% 1% 10%;
                margin: 1% 10% 1% 10%  }

        </style>
    </head>
    <body>
    <%@include file="header.jsp"%>
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
        <div>
        <table class="table  table-bordered table-striped" height="90">
            <thead>
                <tr>
                    <th>USER_ID</th>
                    <th>Order</th>
                    <th>Provider_Location</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </tbody>
        </table>
        </div>
        <DIV>
        <button class="btn btn-primary">Complete</button>
        <button class="btn btn-primary">Suspend</button>
        </div>
        
    </body>
</html>
