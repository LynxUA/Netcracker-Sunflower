
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="includes.jsp"%>
    <title>User's info</title>
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
                    <form accept-charset="UTF-8" role="form" action="change_pas" method="post">
                        <div class="bs-example">
                            <h3>User's info</h3>
                            <table class="table table-striped table-bordered table-hover table-condensed">
                                <thead style="background-color: #5f5e5d; color: white;">
                                <tr>
                                    <th></th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>Name</td>
                                    <td>Ivan</td>
                                </tr>
                                <tr>
                                    <td>Surname</td>
                                    <td>Ivanov</td>
                                </tr>
                                <tr>
                                    <td>Login</td>
                                    <td>lolka93</td>
                                </tr>

                                <tr>
                                    <td>Email</td>
                                    <td>ololo@gmail.com</td>
                                </tr>
                                <tr>
                                    <td>Password</td>
                                    <td>iivanoV_175</td>

                                </tr>
                                <tr>

                                </tbody>
                            </table>
                        </div>
                        <div class="center-block">
                            <input type="submit" class="btn btn-lg btn-success btn-block btn-center" value="Change password">
                        </div>

                        <div class="bs-example">
                            <h3>User's instances</h3>
                            <table class="table table-striped table-bordered table-hover table-condensed">
                                <thead style="background-color: #5f5e5d; color: white;">
                                <tr>
                                    <th>Service name</th>
                                    <th>Status</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr class="success">
                                    <td><a href="location.jsp">Instance1</a></td>
                                    <td>Active</td>
                                </tr>

                                <tr>
                                    <td><a href="location.jsp">Instance2</a></td>
                                    <td>Planned</td>
                                </tr>

                                <tr class="danger">
                                    <td><a href="location.jsp">Instance3</a></td>
                                    <td>Disconnected</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div class="bs-example">
                            <h3>User's orders</h3>
                            <table class="table table-striped table-bordered table-hover table-condensed">
                                <thead style="background-color: #5f5e5d; color: white;">
                                <tr>
                                    <th>Service name</th>
                                    <th>Status</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td><a href="location.jsp">Order1</a></td>
                                    <td>Processing</td>
                                </tr>

                                <tr>
                                    <td><a href="location.jsp">Order2</a></td>
                                    <td>Processing</td>
                                </tr>

                                <tr>
                                    <td><a href="location.jsp">Order3</a></td>
                                    <td>Entering</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <%@include file="footer.jsp"%>
</div>
</body>
</html>