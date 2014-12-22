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


  <%--<% Connection connection = null;--%>
    <%--PreparedStatement statement;--%>

    <%--try {--%>
      <%--try {--%>
        <%--connection = DataSource.getDataSource().getConnection();--%>

      <%--}catch(SQLException e)--%>
      <%--{--%>
        <%--System.out.println(e.getErrorCode());--%>
        <%--System.out.println("something wrong with connection");--%>

      <%--}--%>
      <%--//statement = connection.prepareStatement("SELECT ID_TASK FROM TASK WHERE STATUS = ? Order by ID_TASK");--%>
      <%--statement = connection.prepareStatement("SELECT  DEVICE.Free_ports ,si.ID_CIRCUIT ,si.ID_Service_INST ,so.ID_SCENARIO from SERVICE_ORDER so INNER  JOIN PRICE p ON so.ID_PRICE=p.ID_PRICE INNER  join PROVIDER_LOCATION pl on p.ID_PROV_LOCATION=pl.ID_PROV_LOCATION Inner JOIN  Task t On t.Id_order=so.Id_order inner JOIN SERVICE_INSTANCE si On si.ID_SERVICE_INST=so.ID_SERVICE_INST JOIN CIRCUIT ON SERVICE_INSTANCE.ID_CIRCUIT = CIRCUIT.ID_CIRCUIT JOIN PORT ON CIRCUIT.ID_PORT = PORT.ID_PORT JOIN DEVICE ON PORT.ID_DEVICE = DEVICE.ID_DEVICE  where t.login like ? and so.ID_status NOT IN (4) ");--%>

      <%--statement.setString(1,(String)request.getSession().getAttribute("login"));--%>
      <%--ResultSet resultSet = statement.executeQuery();--%>
      <%--int Id_Si;--%>
      <%--int Id_Circuit;--%>


  <%--%>--%>


<div style="padding: 1% 7% 1% 7%;
     margin: 1% 7% 1% 7%" align="right">
<form accept-charset="UTF-8" role="form"  action="CreateDC?action=device&option=device" method="get">
  <input  type="text" name="lenght" placeholder="Lenght of cable in KM" onkeyup="this.value=this.value.replace(/[^0-9]+/g,'');" >
  <input  type="text" name="type" placeholder="Type of Cable"  >
  <input type="hidden" name="action" value="cable" style=" width:0">
  <input align="right"  class="btn btn-primary" type="submit"  style="  width:15%"    value="Create Cable">
  </form>
</div>
<div style="padding: 1% 7% 1% 7%;
margin: 1% 7% 1% 7%" align="right">
<form accept-charset="UTF-8" role="form"  action="CreateDC?action=device&option=device" method="get">
  <input  type="text" name="Devicename" placeholder="Device Name" id="devicename" >
 <input type="hidden" name="action" value="device" style=" width:0">
  <input   class="btn btn-primary" type="submit"  style=" width:15%"   value="Create Device">
</form>
</div>
<%if(request.getAttribute("result") != null && !((String) request.getAttribute("result")).isEmpty()){%>
<div style="padding: 1% 7% 1% 7%;
     margin: 1% 7% 1% 7%"  align="right" name="result">${requestScope.result}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
<%}%>


<%--<%  } catch (SQLException e) {--%>
  <%--throw new EJBException("SELECT exception in CreateDC");--%>
<%--} finally {--%>
  <%--try {--%>
    <%--if (connection != null) {--%>
      <%--connection.close();--%>
    <%--}--%>
  <%--} catch (SQLException e) {--%>
    <%--e.printStackTrace();--%>
  <%--}--%>
<%--} %>--%>

<%@include file="footer.jsp"%>

</body>
</html>