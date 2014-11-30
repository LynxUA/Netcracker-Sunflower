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
  <title>Netcracker Sunflower</title>
</head>
<body>

<%@include file="header.jsp"%>

<div id="body">
  <div class="bs-example">
    <div class="carousel fade-carousel slide" data-ride="carousel" data-interval="4000" id="bs-carousel">
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
      </div>
    </div>
  </div>

  <%@include file="footer.jsp"%>
</body>
</html>
