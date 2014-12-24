<%--
  Created by IntelliJ IDEA.
  User: denysburlakov
  Date: 16.11.14
  Time: 00:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
  <title>Sunflower</title>
</head>
<body>

<%@include file="header.jsp"%>

<div id="body">
  <div class="bs-example">
      <div align="center">
        <h1>Sunflower the internet provider</h1>
        <img id="logo" src="resources/sunflower_logo.png" />
        <p>
          <form action="<%if(status == UserGroups.GUEST){
          %>signup<%}else if(status == UserGroups.CUSTOMER){
          %>order<%}else if(status == UserGroups.IE || status == UserGroups.PE){
          %>tasks<%}else if(status == UserGroups.CSE){
          %>users<%}else if(status == UserGroups.ADMIN){
          %>reg_emp<%}%>" method="get">
            <input class="btn btn-lg btn-primary" type="submit" value="&nbsp;&nbsp;Make me feel good&nbsp;&nbsp;">
          </form>
        </p>
      </div>

    </div>
  </div>

  <%@include file="footer.jsp"%>
</body>
</html>
