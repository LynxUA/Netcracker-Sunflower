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

  <%@ page import="com.sunflower.ejb.DataSource"%>
  <%@ page import="java.sql.Connection"%>
  <%@ page import="java.sql.PreparedStatement" %>
  <%@ page import="java.sql.SQLException" %>
  <%@ page import="java.sql.ResultSet" %>
  <%@ page import="javax.ejb.ObjectNotFoundException" %>
  <%@ page import="javax.ejb.EJBException" %>
  <%@ page import="javax.ejb.EJBException" %>
  <%@ page import="javax.ejb.FinderException" %>

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
      width: 15%;
      height: 4%;
    }
    p {padding: 0.3% 7% 0.3% 7%;
      margin: 0.3% 7% 0.3% 7%  }
    pre {
      width:70%
    }
    form{display:inline;
      padding: 0;
      margin: 0}
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
      <a href="assign.jsp">PE panel</a>
    </li>
    <li>
      <a href="CurrentTaskPE.jsp">Current Task</a>
    </li>
  </ul>
</aside>

<p align="right">
  <% Connection connection = null;
    PreparedStatement statement;
    int Scenario=0;

    try {
      try {
        connection = DataSource.getDataSource().getConnection();

      }catch(SQLException e)
      {
        System.out.println(e.getErrorCode());
        System.out.println("something wrong with connection");

      }
      System.out.println("war1");
      //statement = connection.prepareStatement("SELECT ID_TASK FROM TASK WHERE STATUS = ? Order by ID_TASK");
      statement = connection.prepareStatement("SELECT d.ID_DEVICE, d.NAME, d.FREE_PORTS ,so.Id_Scenario,t.ID_TASK from SERVICE_ORDER so\n" +
"  INNER  JOIN PRICE p ON so.ID_PRICE=p.ID_PRICE\n" +
"  INNER JOIN Device d on d.ID_PROV_LOCATION=p.ID_PROV_LOCATIOn\n" +
"  Inner JOIN  Task t On t.Id_order=so.Id_order\n" +
"  inner JOIN SERVICE_INSTANCE si\n" +
"    On si.ID_SERVICE_INST=so.ID_SERVICE_INST\n" +
"where t.login=? and so.ID_status!='4'\n" +
"      and so.ID_status!='2' ");
      System.out.println("war2");

      statement.setString(1,(String)request.getSession().getAttribute("login"));
      System.out.println("war3");



  %>




<form method="get" action="assign">

  <select  onchange="   var router=document.getElementById('router');

  var url = 'assign?action=getports&id_router=' + escape(router.value);



  window.location.href = url;" id="router"  name="router">
  <option selected="true" style="display:none;"  value="">Select router</option>

    <%
      try{
        ResultSet resultSet = statement.executeQuery();
        System.out.println("war4");
      Boolean st=false;
        int ID_task=0;
      while(resultSet.next())
    { System.out.println("war5");
      if(st) {
        if (ID_task != resultSet.getInt(5))
          break;
      }
        ID_task=resultSet.getInt(5);
      st=true;


      Scenario=resultSet.getInt(4);
      System.out.println("war6");
      if(resultSet.getInt(3)>0){  System.out.println("war7");%>

    <option value="<%=resultSet.getInt(1)%>"><%=resultSet.getString(2)%></option>


    <%}}}
      catch (Exception ex)
      {
        Scenario=0;
      }
      System.out.println("war8");%>

  </select>
  </form>
<form method="get" action="assign">
  <input type="hidden" name="action" value="assign" style=" width:0">
  <select name="Id_Port">

    <option selected="true" style="display:none;" >Select Port</option>
    <%if(request.getAttribute("ports") != null && !((String) request.getAttribute("ports")).isEmpty()){%>
    ${requestScope.ports}
    <%}%>
    <!--<option selected="true" style="display:none;" >Select router</option>-->
  </select>

  <button align="right"  class="btn btn-primary" width="15%"  type="submit">Assign port  </button>
  </form>
</p>
<p align="right">

<form method="get" action="assign" align="right">
  <input type="hidden" name="action" value="createCir" style=" width:0">
  <button  align="right"  class="btn btn-primary" width="15%" type="submit" >Create circuit</button>
</form> </p>

<p align="right">
<form method="get" action="assign">
  <input  type="text" name="unports" placeholder="Write port id" onkeyup="this.value=this.value.replace(/[^0-9]+/g,''); isrightm(this);" >
  <input type="hidden" name="action" value="unassign" style=" width:0">
<button  align="right"  class="btn btn-primary" width="15%" type="submit">Unassign port</button>
</form>
</p>
<p align="right">
<%if(Scenario==3){%>
<form method="get" action="assign">
  <input type="hidden" name="action" value="remove" style=" width:0">
<button  align="right" class="btn btn-primary" width="15%" type="submit">Remove the circuit</button>
</form>
<%} else{%>

<button  align="right"  class="btn btn-primary" width="15%" disabled>Remove the circuit</button>
<% }%>
</p>
<%if(request.getAttribute("result") != null && !((String) request.getAttribute("result")).isEmpty()){%>
<p name="result">${requestScope.result}</p>
<%}%>
<%  } catch (SQLException e) {
      throw new EJBException("SELECT exception in assign");
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
