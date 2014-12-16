<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 12/16/2014
  Time: 1:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>IE panel</title>
  <%@include file="includes.jsp"%>
  <%@include file="header.jsp"%>

  <%@ page import="com.sunflower.ejb.DataSource"%>
  <%@ page import="java.sql.Connection"%>
  <%@ page import="java.sql.PreparedStatement" %>
  <%@ page import="java.sql.SQLException" %>
  <%@ page import="java.sql.ResultSet" %>
  <%@ page import="javax.ejb.ObjectNotFoundException" %>
  <%@ page import="javax.ejb.EJBException" %>
  <%@ page import="javax.ejb.EJBException" %>

  <script type="text/javascript"  src="js/create.js" ></script>
  <style>
    aside {
      padding: 10px;
      width: 200px;
      float: left;
    }
    select, input {
      padding:0.5% 2% 0.5% 2% ;
      margin: 0.5% 2% 0.5% 2%;
      width: 30%;
      height: 4%;
    }
    p {padding: 0.3% 7% 0.3% 7%;
      margin: 0.3% 7% 0.3% 7%  }
    pre {
      width:70%
    }
    button{
      width:15%;
    }
  </style>
</head>
<body>
<%@include file="header.jsp"%>


<aside>
  <ul class="nav nav-list bs-docs-sidenav affix">
    <li>
      <a href="CreateDC.jsp">IE panel</a>
    </li>
    <li>
      <a href="CurrentTaskIE.jsp">Current Task</a>
    </li>
  </ul>
</aside>
<p><pre>   Port                                  User ID</pre></p>
<p>
  <% Connection connection = null;
    PreparedStatement statement;

    try {
      try {
        connection = DataSource.getDataSource().getConnection();

      }catch(SQLException e)
      {
        System.out.println(e.getErrorCode());
        System.out.println("something wrong with connection");

      }
      //statement = connection.prepareStatement("SELECT ID_TASK FROM TASK WHERE STATUS = ? Order by ID_TASK");
      statement = connection.prepareStatement("SELECT  d.Free_ports ,si.ID_CIRCUIT ,si.ID_Service_INST ,so.ID_SCENARIO from SERVICE_ORDER so INNER  JOIN PRICE p ON so.ID_PRICE=p.ID_PRICE INNER  join PROVIDER_LOCATION pl on p.ID_PROV_LOCATION=pl.ID_PROV_LOCATION Inner JOIN  Task t On t.Id_order=so.Id_order inner JOIN SERVICE_INSTANCE si On si.ID_SERVICE_INST=so.ID_SERVICE_INST where t.login=? and so.ID_status!='4' ");

      statement.setString(1,(String)request.getSession().getAttribute("login"));
      ResultSet resultSet = statement.executeQuery();
      int Id_Si;
      int Id_Circuit;


  %>



<p>
  <input  type="text" name="length" placeholder="Length of cable in KM" onkeyup="this.value=this.value.replace(/[^0-9]+/g,'');" >
  <input  type="text" name="type" placeholder="Type of Cable"  >
  <button  align="right" style="float: right;" class="btn btn-primary" width="15%" onclick="cable()">Create Cable</button>
</p>
<p>
  <input  type="text" name="Devicename" placeholder="Device Name" >

  <button  align="right" style="float: right;" class="btn btn-primary" width="15%" onclick="device()">Create Cable</button>
</p>



<p name="result"></p>
<%  } catch (SQLException e) {
  throw new EJBException("SELECT exception in CreateDC");
} finally {
  try {
    if (connection != null) {
      connection.close();
    }
  } catch (SQLException e) {
    e.printStackTrace();
  }
} %>

<%@include file="footer.jsp"%>

</body>
</html>