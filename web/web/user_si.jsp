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
            <div class="container">
              <h3>User's instances</h3>
              <table class="table table-striped table-condensed">
              <thead>
              <tr>
                <th>Service name</th>
                <th>Status</th>
              </tr>
              </thead>
                <tbody>
                    <tr>
                      <td><a href="location.jsp">instance1</a></td>
                      <td><span class="label label-success">active</span></td>
                    </tr>

                    <tr>
                      <td><a href="location.jsp">instance2</a></td>
                        <td><span class="label label-warning">planned</span></td>
                    </tr>

                    <tr>
                      <td><a href="location.jsp">instance3</a></td>
                        <td><span class="label label-danger">disconnected</span></td>
                    </tr>

                    <tr>
                      <td>instance4</td>
                        <td><span class="label label-warning">planned</span></td>
                    </tr>

                    <tr>
                      <td>instance5</td>
                        <td><span class="label label-success">active</span></td>
                    </tr>

                    <tr>
                      <td>instance6</td>
                        <td><span class="label label-warning">planned</span></td>
                    </tr>
                </tbody>
              </table>
            </div>
        </div>
        <%@include file="footer.jsp"%>
    </body>
</html>
