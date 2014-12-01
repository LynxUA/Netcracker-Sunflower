<%--
  Created by IntelliJ IDEA.
  User: Andriy
  Date: 12/1/2014
  Time: 10:01 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
    <title>Contact us</title>
</head>
<body>
<%@include file="header.jsp"%>

<div id="body">
  <div class="container">
    <div class="row">
      <div class="col-md-6 col-md-offset-3">
        <div class="well well-sm">
          <form class="form-horizontal" action="contuctus" method="post">
            <fieldset>
              <legend class="text-center">Contact us</legend>
              <%if(request.getAttribute("name_error") != null && !((String) request.getAttribute("name_error")).isEmpty()){%>
              <div class="alert alert-danger" role="alert">${requestScope.name_error}</div>
              <%}%>
              <!-- Name input-->
              <div class="form-group">
                <label class="col-md-3 control-label" for="name">Name</label>
                <div class="col-md-9">
                  <input id="name" name="name" type="text" placeholder="Your name" class="form-control" value="${requestScope.name}">
                </div>
              </div>

              <%if(request.getAttribute("email_error") != null && !((String) request.getAttribute("email_error")).isEmpty()){%>
              <div class="alert alert-danger" role="alert">${requestScope.email_error}</div>
              <%}%>
              <!-- Email input-->
              <div class="form-group">
                <label class="col-md-3 control-label" for="email">Your E-mail</label>
                <div class="col-md-9">
                  <input id="email" name="email" type="email" placeholder="Your email" class="form-control" value="${requestScope.email}">
                </div>
              </div>

              <%if(request.getAttribute("text_error") != null && !((String) request.getAttribute("text_error")).isEmpty()){%>
              <div class="alert alert-danger" role="alert">${requestScope.text_error}</div>
              <%}%>
              <!-- Message body -->
              <div class="form-group">
                <label class="col-md-3 control-label" for="text">Your message</label>
                <div class="col-md-9">
                  <textarea class="form-control" id="text" name="text" placeholder="Please enter your message here..." rows="5">${requestScope.text}</textarea>
                </div>
              </div>
              <input  type="hidden" name="check" value="">
              <!-- Form actions -->
              <div class="form-group">
                <div class="col-md-12 text-right">
                  <button type="submit" class="btn btn-primary btn-lg">Submit</button>
                </div>
              </div>
            </fieldset>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>


<%@include file="footer.jsp"%>
</body>
</html>
