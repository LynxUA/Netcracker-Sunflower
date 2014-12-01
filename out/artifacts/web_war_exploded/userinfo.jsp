<%--
  Created by IntelliJ IDEA.
  User: Maxim
  Date: 01.12.2014
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
  <title>User info</title>
</head>
<body>
  <%@include file="header.jsp"%>
  <div class="container">
    <div class="row voffset-25">
      <div class="col-md-8 col-md-offset-2">
        <div class="panel panel-default">
          <div class="panel-heading">
            <div class="panel-body">
              <fieldset>
                <h1>My profile</h1>
                <br/>
                <table class="table table-bordered">
                  <tr>
                    <td><b>Name: </b></td>
                    <td> Ivan </td>
                  </tr>
                  <tr>
                    <td><b>Surname: </b></td>
                    <td> Ivanov </td>
                  </tr>
                  <tr>
                    <td><b>Email: </b></td>
                    <td> ivanivanov@gmail.com </td>
                  </tr>
                </table>
                <br/>
                <br/>
                <a href="password" class="btn btn-lg btn-primary btn-block">Change password</a>
                <br/>
                <div class="col-xs-6 col-sm-6 col-md-6">
                  <a href="instances" class="btn btn-lg btn-primary btn-block">My instances</a>
                </div>
                <div class="col-xs-6 col-sm-6 col-md-6">
                  <a href="orders" class="btn btn-lg btn-primary btn-block">My orders</a>
                </div>
                <input  type="hidden" name="check" value="">
              </fieldset>
              <br/>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <%@include file="footer.jsp"%>
</body>
</html>
