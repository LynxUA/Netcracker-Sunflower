<%@ page import="java.util.logging.Logger" %>
<%@ page import="java.util.logging.Level" %>
<%@ page import="java.util.logging.Handler" %>
<%@ page import="java.util.logging.FileHandler" %>
<%@ page import="java.io.IOException" %>
<%--
  Created by IntelliJ IDEA.
  User: denysburlakov
  Date: 16.11.14
  Time: 00:19
  To change this template use File | Settings | File Templates.
--%>
<%
  //Parent logger registration
  if(System.getProperty("java.util.logging.config.class") == null
          && System.getProperty("java.util.logging.config.file") ==null ) {
    try {

      Logger.getLogger("").setLevel(Level.ALL);
      final int ROTATION_COUNT = 5;
      Handler handler = new FileHandler("/Developer/Java_EE", 1000000, ROTATION_COUNT);
      Logger.getLogger("").addHandler(handler);

    } catch (IOException e){
      Logger.getLogger("com.sunflower.web.CheckLoginServlet").
              log(Level.SEVERE, "Creating log file handler error",e);
    }
  }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
  <title>Netcracker Sunflower</title>
</head>
<body>

<%@include file="header.jsp"%>

<div id="body">
  <div class="bs-example">
    <%--<div class="carousel fade-carousel slide" data-ride="carousel" data-interval="4000" id="bs-carousel">
      <!-- Overlay -->
      <div class="overlay"></div>

      <!-- Indicators -->
      <ol class="carousel-indicators">
        <li data-target="#bs-carousel" data-slide-to="0" class="active"></li>
        <li data-target="#bs-carousel" data-slide-to="1"></li>
        <li data-target="#bs-carousel" data-slide-to="2"></li>
      </ol>

      <!-- Wrapper for slides -->
      <div class="carousel-inner">
        <div class="item slides active">
          <div class="slide-1"></div>
          <div class="hero">
            <hgroup>
              <h1>We are progressive</h1>
              <h3>We have services for everyone</h3>
            </hgroup>
            <a href="location">
              <button class="btn btn-hero btn-lg" role="button" >
                Start using
              </button>
            </a>
          </div>
        </div>
        <div class="item slides">
          <div class="slide-2"></div>
          <div class="hero">
            <hgroup>
              <h1>We are loyal</h1>
              <h3>We have a lot of loyal offerings.</h3>
            </hgroup>
            <a href="location">
              <button class="btn btn-hero btn-lg" role="button" >
                Start using
              </button>
            </a>
          </div>
        </div>
        <div class="item slides">
          <div class="slide-3"></div>
          <div class="hero">
            <hgroup>
              <h1>You may always ask</h1>
              <h3>Our support is available 24/7</h3>
            </hgroup>
            <a href="location">
              <button class="btn btn-hero btn-lg" role="button" >
                Start using
              </button>
            </a>
          </div>
        </div>
      </div>--%>

      <div align="center">
        <h1>Sunflower the internet provider</h1>
        <img id="logo" src="resources/sunflower_logo.png" />
        <p>
          <form action="signup" method="get">
            <input class="btn btn-lg btn-primary" type="submit" value="&nbsp;&nbsp;Make me feel good&nbsp;&nbsp;">
          </form>
        </p>
      </div>

    </div>
  </div>

  <%@include file="footer.jsp"%>
</body>
</html>
