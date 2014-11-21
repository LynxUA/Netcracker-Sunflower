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
    <title>Reset password page</title>
</head>
<body>

  <h1>Reset password</h1>

  <form method="post" action="reset">
    <p>
      <input type="text" name="login" value="${requestScope.login}" placeholder="email">
      <font color="red"> ${requestScope.login_error}</font>
    </p>
    <p class="submit"><input type="submit" name="commit" value="Reset"></p>
  </form>

</body>
</html>
