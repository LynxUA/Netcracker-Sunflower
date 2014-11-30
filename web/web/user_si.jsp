<%--
  Created by IntelliJ IDEA.
  User: Den
  Date: 29.11.2014
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <%@include file="includes.jsp"%>
        <title>User's instances</title>
    </head>
    <body>
        <%@include file="header.jsp"%>
        <div id="body">
            <div class="bs-example">
              <h3>User's instances</h3>
              <table class="table table-striped table-bordered table-hover table-condensed">
              <thead style="background-color: #5f5e5d; color: white;">
              <tr>
                <th>Service name</th>
                <th>Status</th>
              </tr>
              </thead>
                <tbody>
                    <tr class="success">
                      <td><a href="location.jsp">Instance1</a></td>
                      <td>Active</td>
                    </tr>

                    <tr>
                      <td><a href="location.jsp">Instance2</a></td>
                      <td>Planned</td>
                    </tr>

                    <tr class="danger">
                      <td><a href="location.jsp">Instance3</a></td>
                      <td>Disconnected</td>
                    </tr>

                    <tr>
                      <td>Instance4</td>
                      <td>Planned</td>
                    </tr>

                    <tr>
                      <td>Instance5</td>
                      <td>Planned</td>
                    </tr>

                    <tr>
                      <td>Instance6</td>
                      <td>Planned</td>
                    </tr>
                </tbody>
              </table>
            </div>
        </div>

    </body>
</html>
