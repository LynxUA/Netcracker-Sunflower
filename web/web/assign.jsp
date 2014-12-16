<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 11/30/2014
  Time: 2:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>assign ports</title>
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

  <script type="text/javascript"  src="js/assign.js" ></script>
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
      <a href="assign.jsp">Assign port</a>
    </li>
    <li>
      <a href="modify.jsp">Modify</a>
    </li>
    <li>
      <a href="CurrentTaskPE.jsp">Current Task</a>
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
      statement = connection.prepareStatement("SELECT d.ID_DEVICE, d.NAME, d.Free_ports ,si.ID_CIRCUIT ,si.ID_Service_INST ,so.ID_SCENARIO" +
              " from SERVICE_ORDER so INNER  JOIN PRICE p ON so.ID_PRICE=p.ID_PRICE INNER  join PROVIDER_LOCATION pl on p.ID_PROV_LOCATION=pl.ID_PROV_LOCATION" +
              "Inner JOIN DEVICE d On d.Id_Prov_location=pl.Id_Prov_location Inner JOIN  Task t On t.Id_order=so.Id_order inner JOIN SERVICE_INSTANCE si On si.ID_SERVICE_INST=so.ID_SERVICE_INST " +
              "where t.login=? and so.ID_status!='4' and so.ID_status!='2' ");

      statement.setString(1,(String)request.getSession().getAttribute("login"));
      ResultSet resultSet = statement.executeQuery();
      int Id_Si;
      int Id_Circuit;


  %>





  <select  onchange="selectRouter()"  name="router">
  <option selected="true" style="display:none;"  value="">Select router</option>

    <%while(resultSet.next())
    {
      Id_Circuit=resultSet.getInt(4);
       Id_Si=resultSet.getInt(5);
      if(resultSet.getInt(3)>0){%>

    <option value="<%=resultSet.getInt(1)%>"><%=resultSet.getString(2)%></option>


    <%}}%>

  </select>

  <select name="ports">

    <option selected="true" style="display:none;" >Select Port</option>
    <!--<option selected="true" style="display:none;" >Select router</option>-->
  </select>
  <button align="right" style="float: right;" class="btn btn-primary" width="15%" onclick="assign()">Assign port  </button>
</p>
<p>

  <button  align="right" style="float: right;" class="btn btn-primary" width="15%" onclick="createCir()">Create circuit</button> </p>
<p>
  <input  type="text" name="unports" placeholder="Write port id" onkeyup="this.value=this.value.replace(/[^0-9]+/g,''); isrightm(this);" >

<button  align="right" style="float: right;" class="btn btn-primary" width="15%" onclick="unassign()">Unassign port</button>
</p>
<%if(resultSet.getInt(5)!=3) {%>
<button  align="right" style="float: right;" class="btn btn-primary" width="15%" disabled>Remove the circuit</button>
<%}else{%>
<button  align="right" style="float: right;" class="btn btn-primary" width="15%" onclick="remove()">Remove the circuit</button>
<%}%>

<p name="result"></p>
<%  } catch (SQLException e) {
      throw new EJBException("SELECT exception in ejbFindIncomplete");
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
