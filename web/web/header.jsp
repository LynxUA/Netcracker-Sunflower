<%@ page import="com.sunflower.UserGroup" %>
<%@ page import="com.sunflower.exceptions.UnknownStatusException" %>
<%@ page import="static com.sunflower.UserGroup.*" %>
<%--
  Created by IntelliJ IDEA.
  User: denysburlakov
  Date: 23.11.14
  Time: 04:14
  To change this template use File | Settings | File Templates.
--%>
<%
  /** TODO:
   * /users (link to /profile)
   * /groups (link to /group)
   * /instances (link to /user_si)
   * /orders  (link to /user_so)
   * /tasks (link to /task)
   * */

  String login = (String) request.getSession().getAttribute("login");
  UserGroup status = null;
  if(login!=null){
    try {
      status = fromInt((Integer) (request.getSession().getAttribute("status")));
    }catch (UnknownStatusException e){
      e.printStackTrace();
    }
  }else{
    login = "Guest";
    status = GUEST;
  }
%>
<div id="wrap">
<div id="header">
  <div class="bs-example">
    <nav id="myNavbar" class="navbar navbar-default" role="navigation">
      <!-- Brand and toggle get grouped for better mobile display -->
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/webWeb/">
             OSS Sunflower
          </a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
            <%
              switch (status){
                case GUEST:
            %>
                  <li><a href="/webWeb/">Home</a></li>
                  <li><a href="catalog.jsp">Services</a></li>
            <%
                break;
              case CUSTOMER:
            %>
                  <li><a href="/webWeb/">Home</a></li>
                  <li><a href="catalog.jsp">Services</a></li>
                  <li><a href="order">Make an order</a></li>
            <%
                break;
              case ADMIN:

            %>
                  <li><a href="/webWeb/">Home</a></li>
                  <%--<li><a href="users">Users</a></li>--%>
            <%
                break;
              case CSE:

            %>
                  <li><a href="/webWeb/">Home</a></li>
                  <li><a href="users">Users</a></li>
            <%
                break;
              case PE:
            %>
                <li><a href="/webWeb/">Home</a></li>
                <li><a href="tasks">Tasks</a></li>
            <%
                break;
              case IE:
            %>
                <li><a href="/webWeb/">Home</a></li>
                <li><a href="tasks">Tasks</a></li>
                <%--<li><a href="/groups">Groups</a></li>--%>
            <%
                  break;
              }
            %>

          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
              <a href="<%
              switch (status){
              case GUEST:
              %>
              #
              <%
              default:
              %>
              profile
              <%
              }

              %>" data-toggle="dropdown" class="dropdown-toggle"><%=login%><b class="caret"></b></a>
              <ul class="dropdown-menu">
                <%
                  switch (status){
                    case GUEST:
                %>
                    <li><a href="login">Login</a></li>
                    <li><a href="signup">Signup</a></li>
                    <li><a href="location">Your location</a></li>
                <%
                    break;
                    case CUSTOMER:
                %>
                    <li><a href="user_si">My instances</a></li>
                    <li><a href="user_so">My orders</a></li>
                    <%--<li><a href="location">My location</a></li>--%>
                    <li class="divider"></li>
                    <li><a href="exit">Exit</a></li>
                <%
                    break;
                    case ADMIN:
                %>
                    <li><a href="reg_emp">Register employee</a></li>
                    <%--<li><a href="location">My location</a></li>--%>
                    <li class="divider"></li>
                    <li><a href="exit">Exit</a></li>

                <%
                    break;
                  case CSE:
                %>
                    <%--<li><a href="reg_emp">Register employee</a></li>--%>
                    <%--<li><a href="location">My location</a></li>--%>
                    <%--<li class="divider"></li>--%>
                    <li><a href="exit">Exit</a></li>
                <%
                    break;
                    case PE:
                %>
                    <%--<li><a href="reg_emp">Register employee</a></li>--%>
                    <%--<li><a href="location">My location</a></li>--%>
                    <%--<li class="divider"></li>--%>
                    <li><a href="exit">Exit</a></li>
                <%
                    break;
                    case IE:
                %>
                    <%--<li><a href="reg_emp">Register employee</a></li>--%>
                    <%--<li><a href="location">My location</a></li>--%>
                    <li class="divider"></li>
                    <li><a href="exit">Exit</a></li>
                <%
                    break;
                  }
                %>
              </ul>
            </li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div>
    </nav>
  </div>
</div>