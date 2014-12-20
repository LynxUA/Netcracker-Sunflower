<%@ page import="com.sunflower.ejb.EJBFunctions" %>
<%@ page import="com.sunflower.ejb.user.LocalUser" %>
<%--
  Created by IntelliJ IDEA.
  User: denysburlakov
  Date: 12.12.14
  Time: 03:54
  To change this template use File | Settings | File Templates.
--%>
<%
  Integer userStatus = (Integer)(request.getSession().getAttribute("status"));
  String userLogin = request.getParameter("login");

  LocalUser customer = null;
  if(userLogin != null && userStatus == UserGroups.CSE) {
    customer = EJBFunctions.findUser(userLogin);
  }else{
    customer = EJBFunctions.findUser((String)(request.getSession().getAttribute("login")));
  }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <%@include file="includes.jsp"%>
    <title>User info</title>
  </head>
  <body>
    <%@include file="header.jsp"%>
    
    <div id="body">
      <div class="container">
        <h3>User info</h3>
        <table class="table table-striped table-condensed">
          <tr>
            <th scope="row">Login</th>
            <td><%=customer.getLogin()%></td>
          </tr>
          <tr>
            <th scope="row">Email</th>
            <td><%=customer.getEmail()%></td>
          </tr>
          <tr>
            <th scope="row">Name</th>
            <td><%=customer.getName()%></td>
          </tr>
          <tr>
            <th scope="row">Surname</th>
            <td><%=customer.getSurname()%></td>
          </tr>
        </table>
        <%if(userLogin != null && userStatus == UserGroups.CSE){%>
        <div class="col-xs-6 col-md-4">
          <a href="change_password?login=<%=customer.getLogin()%>" class="btn btn-lg btn-primary btn-block">Change password</a>
        </div>
        <div class="col-xs-6 col-md-4">
          <a href="user_so?login=<%=customer.getLogin()%>" class="btn btn-lg btn-primary btn-block">User orders</a>
        </div>
        <div class="col-xs-6 col-md-4">
          <a href="user_si?login=<%=customer.getLogin()%>" class="btn btn-lg btn-primary btn-block">User instances</a>
        </div>
        <%}else{%>
        <div class="col-xs-6 col-md-4">
          <a href="change_info" class="btn btn-lg btn-primary btn-block">Change info</a>
        </div>
        <%}%>
      </div>

    </div>
  <%@include file="footer.jsp"%>
  </body>
</html>
