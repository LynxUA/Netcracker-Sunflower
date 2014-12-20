<%@ page import="com.sunflower.ejb.user.LocalUser" %>
<%@ page import="com.sunflower.ejb.EJBFunctions" %>
<%--
  Created by IntelliJ IDEA.
  User: denysburlakov
  Date: 19.12.14
  Time: 23:50
  To change this template use File | Settings | File Templates.
--%>
<%
  LocalUser customer = EJBFunctions.findUser((String)(request.getSession().getAttribute("login")));
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
  <title>Change user info</title>
</head>
<body>

<%@include file="header.jsp"%>
<div class="container">
  <div class="row voffset-25">
    <div class="col-md-8 col-md-offset-2">
      <div class="panel panel-default">
        <div class="panel-heading">
          <img src="http://s11.postimg.org/7kzgji28v/logo_sm_2_mr_1.png" class="img-responsive center-block" alt="Sunflower"/>
        </div>
        <div class="panel-body">

          <form method="post" action="change_info">
            <p>Name:</p>
            <%if(request.getAttribute("name_error") != null && !((String) request.getAttribute("name_error")).isEmpty()){%>
            <div class="alert alert-danger" role="alert">${requestScope.name_error}</div>
            <%}%>
            <div class="form-group">
              <input type="text" name="name" id="name" class="form-control input-lg" placeholder="Name" value="<%=customer.getName()%>" tabindex="4">
            </div>
            <p>Surname:</p>
            <%if(request.getAttribute("surname_error") != null && !((String) request.getAttribute("surname_error")).isEmpty()){%>
            <div class="alert alert-danger" role="alert">${requestScope.surname_error}</div>
            <%}%>
            <div class="form-group">
              <input type="text" name="surname" id="surname" class="form-control input-lg" placeholder="Surname" value="<%=customer.getSurname()%>" tabindex="4">
            </div>
            <p>Password:</p>
            <%if(request.getAttribute("password_error") != null && !((String) request.getAttribute("password_error")).isEmpty()){%>
            <div class="alert alert-danger" role="alert">${requestScope.password_error}</div>
            <%}%>
            <div class="form-group">
              <input type="password" name="password" id="password" class="form-control input-lg" placeholder="Password" value="<%=customer.getPassword()%>" tabindex="4">
            </div>
            <p>Password check:</p>
            <div class="form-group">
              <input type="password" name="repeat_password" id="repeat_password" class="form-control input-lg" placeholder="Repeat password" value="<%=customer.getPassword()%>" tabindex="4">
            </div>
            <input  type="hidden" name="check" value="">
            <div class="col-xs-6 col-sm-6 col-md-offset-3">
              <input type="submit" class="btn btn-lg btn-success btn-block" value="Change info">
            </div>
          </form>

        </div>
      </div>
    </div>
  </div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
