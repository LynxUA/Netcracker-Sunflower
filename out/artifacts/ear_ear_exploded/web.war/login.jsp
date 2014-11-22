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
    <title>Login page</title>
</head>
<body>

  <h1>Login page</h1>

  <font color="red"><h2> ${requestScope.error}</h2></font>
  <form method="post" action="login">
    <p><input type="text" name="login" value="${requestScope.login}" placeholder="E-mail"></p>
    <p><input type="password" name="password" value="" placeholder="Password"></p>
    <p class="remember_me">
      <label>
        <input type="checkbox" name="remember_me" id="remember_me">
        Remember me on this computer
      </label>
    </p>

    <p class="submit">
      <input type="submit" name="commit" value="Login">
      <a href="signup">
        <input type="button" value="Sign up" onclick="signup">
      </a>
    </p>
    <input  type="hidden" name="check" value="">
  </form>

  <div>
    <p>Forgot your password? <a href="reset">Click here to reset it</a>.</p>
  </div>
</body>
</html>
