<%--
  Created by IntelliJ IDEA.
  User: Andriy
  Date: 11/18/2014
  Time: 5:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
    <title>Sign up</title>
</head>
<body>

  <%@include file="header.jsp"%>

  <div id="body">
    <div class="container">
      <div class="row">
        <div class="col-md-8 col-md-offset-2">
          <div class="panel panel-default">
            <div class="panel-heading">
              <img src="http://s11.postimg.org/7kzgji28v/logo_sm_2_mr_1.png" class="img-responsive center-block" alt="Sunflower"/>
            </div>
            <div class="panel-body">

              <form method="post" action="signup">

                <%if(request.getAttribute("login_error") != null && !((String) request.getAttribute("login_error")).isEmpty()){%>
                <div class="alert alert-danger" role="alert">${requestScope.login_error}</div>
                <%}%>
                <div class="form-group">
                  <input type="email" name="login" id="login" class="form-control input-lg" placeholder="Email Address" value="${requestScope.login}" tabindex="4">
                </div>

                <%if(request.getAttribute("name_error") != null && !((String) request.getAttribute("name_error")).isEmpty()){%>
                <div class="alert alert-danger" role="alert">${requestScope.name_error}</div>
                <%}%>
                <div class="form-group">
                  <input type="text" name="name" id="name" class="form-control input-lg" placeholder="Name" value="${requestScope.name}" tabindex="4">
                </div>

                <%if(request.getAttribute("password_error") != null && !((String) request.getAttribute("password_error")).isEmpty()){%>
                <div class="alert alert-danger" role="alert">${requestScope.password_error}</div>
                <%}%>
                <div class="form-group">
                  <input type="password" name="password" id="password" class="form-control input-lg" placeholder="Password" tabindex="4">
                </div>

                <div class="form-group">
                  <input type="password" name="repeat_password" id="repeat_password" class="form-control input-lg" placeholder="Repeat password" tabindex="4">
                </div>

                <input  type="hidden" name="check" value="">

                <div class="col-xs-6 col-sm-6 col-md-offset-3">
                  <input type="submit" class="btn btn-lg btn-success btn-block" value="Sign Up">
                </div>
              </form>

            </div>
          </div>
        </div>
      </div>
    </div>
  </div>


  <%@include file="footer.jsp"%>

</body>
</html>
