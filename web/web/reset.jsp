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
  <title>Reset password page</title>
</head>
<body>

  <%@include file="header.jsp"%>

  <div id="body">
    <div class="container">
      <div class="row">
        <div class="col-md-8 col-md-offset-2">
          <div class="panel panel-default">
            <div class="panel-heading">
              <div class="row-fluid user-row">
                <img src="http://s11.postimg.org/7kzgji28v/logo_sm_2_mr_1.png" class="img-responsive center-block" alt="Sunflower"/>
              </div>
            </div>
            <div class="panel-body">

              <form accept-charset="UTF-8" role="form" class="form-signin" action="reset" method="post">
                <h1>Reset password for <%=request.getParameter("login")%></h1>
                <%if(request.getAttribute("password_error") != null && !((String) request.getAttribute("password_error")).isEmpty()){%>
                <div class="alert alert-danger" role="alert">${requestScope.password_error}</div>
                <%}%>
                <div class="form-group">
                  <input type="password" name="password" id="password" class="form-control input-lg" placeholder="Password" tabindex="4">
                </div>

                <div class="form-group">
                  <input type="password" name="repeat_password" id="repeat_password" class="form-control input-lg" placeholder="Repeat password" tabindex="4">
                </div>
                <div class="col-xs-6 col-sm-6 col-md-offset-3">
                  <input type="submit" class="btn btn-lg btn-success btn-block" value="Reset">
                </div>
                <input type="hidden" name="login" value="<%=request.getParameter("login")%>">
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
