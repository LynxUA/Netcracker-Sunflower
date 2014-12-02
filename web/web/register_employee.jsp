<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="includes.jsp"%>
    <title>Register new employee</title>
</head>

<body>


<%@include file="header.jsp"%>

<div id="body">
    <div class="row voffset-25">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <img src="http://s11.postimg.org/7kzgji28v/logo_sm_2_mr_1.png" class="img-responsive center-block" alt="Sunflower"/>
                </div>
                <div class="panel-body">
                    <form accept-charset="UTF-8" role="form" class="form-register" action="register_employee" method="post">

                        <fieldset>

                            <%if(request.getAttribute("firstName_error") != null && !((String) request.getAttribute("firstName_error")).isEmpty()){%>
                            <div class="alert alert-danger" role="alert">${requestScope.firstName_error}</div>
                            <%}%>
                            <input class="form-control"  placeholder="First Name" value="${requestScope.firstName}" name="firstName" type="text">
                            <br/>

                            <%if(request.getAttribute("lastName_error") != null && !((String) request.getAttribute("lastName_error")).isEmpty()){%>
                            <div class="alert alert-danger" role="alert">${requestScope.lastName_error}</div>
                            <%}%>
                            <input class="form-control" placeholder="Last Name" value="${requestScope.lastName}" name="lastName" type="text">
                            <br/>

                            <%if(request.getAttribute("login_error") != null && !((String) request.getAttribute("login_error")).isEmpty()){%>
                            <div class="alert alert-danger" role="alert">${requestScope.login_error}</div>
                            <%}%>
                            <input class="form-control" placeholder="name@example.com" value="${requestScope.email}" name="login" type="email">
                            <br/>

                            <%if(request.getAttribute("password_error") != null && !((String) request.getAttribute("password_error")).isEmpty()){%>
                            <div class="alert alert-danger" role="alert">${requestScope.password_error}</div>
                            <%}%>
                            <input class="form-control" placeholder="Password" name="password1" type="password">
                            <br/>
                            <input class="form-control" placeholder="Confirm Password" name="password2" type="password">
                            <br/>



                            <div class="center-block">
                                <input type="submit" class="btn btn-lg btn-success btn-block btn-center" value="Register">
                            </div>

                            <input  type="hidden" name="check" value="">
                        </fieldset>


                    </form>
                </div>
            </div>
        </div>
    </div>

    <%@include file="footer.jsp"%>
</div>
</body>
</html>