<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>

        <title>XLS EXPORT</title>

       <link rel="stylesheet" type="text/css" href="report/tcal.css" />

     <script type="text/javascript" src="report/tcal.js"  ></script>
      <script type="text/javascript"  src="report/reports.js" ></script>
        <%@include file="includes.jsp"%>

        <style>
            aside {
                padding: 2%;
                width: 200px;
                float: left;
            }


            p {padding: 0.3% 4% 0.3% 4%;
                margin: 0.3% 4% 0.3% 4%  }
        </style>
    </head>
    <body>

    <%@include file="header.jsp"%>
    <aside>
        <%@ page import="com.sunflower.ejb.EJBFunctions"%>
        <%@ page import="com.sunflower.ejb.ProviderLocation.LocalProviderLocation"%>
        <%@ page import="java.sql.SQLException" %>
        <%@ page import="com.sunflower.ejb.DataSource" %>
        <%@ page import="java.sql.PreparedStatement" %>
        <%@ page import="java.sql.Connection" %>
        <%@ page import="java.sql.ResultSet" %>
        <%@ page import="javax.ejb.EJBException" %>
        <ul class="nav nav-list bs-docs-sidenav affix">
            <%
                Connection connection = null;
                PreparedStatement statement;
                ResultSet rs;
                try {
                    try {
                        connection = DataSource.getDataSource().getConnection();

                    }catch(SQLException e)
                    {
                        System.out.println(e.getErrorCode());
                        System.out.println("something wrong with connection");

                    }
                    System.out.println("war1");
                    statement = connection.prepareStatement("Select Id_Prov_Location" +
                            ", Location from PROVIDER_LOCATION ");
                    try{
                         rs = statement.executeQuery();
            while (rs.next())
                        {

            %>

            <li>

                <input  type="checkbox" value="<%=rs.getString(2)%>" name="<%=rs.getString(2)%>"  id="loc<%=rs.getInt(1)%>"><font color="#296293"><%=rs.getString(2)%></font>

            </li>
            <%
                }} catch (Exception ex)
                {
                   ex.printStackTrace();
                }
            %>

        </ul>
    </aside>
    <form accept-charset="UTF-8" role="form"  action="xmlxprt" method="get">
   

	
		<p align="right"> <input  type="text" name="date1" class=" input-medium tcal " value="" id="date1" placeholder="From" style="  padding:0.5% 2% 0.5% 2% ;
                margin: 0.5% 2% 0.5% 2%;
                width: 15%;
                height: 4%;" >

	
		  <input  type="text" name="date2" class="input-medium tcal " value="" id="date2" placeholder="To" style="  padding:0.5% 2% 0.5% 2% ;
                margin: 0.5% 2% 0.5% 2%;
                width: 15%;
                height: 4%;" />

       <select name="Select" id="option" style="  padding:0.5% 2% 0.5% 2% ;
                margin: 0.5% 2% 0.5% 2%;
                width: 20%;
                height: 4%;" >
            <option>New orders per period</option>
            <option>Disconnects per period</option>

        </select>

        <input class="btn btn-primary" type="button" value="Get Report per period" name="siper" onclick="doCompletion();"   style="width:20%" /></p>

        <p align="right" >  <input style="  padding:0.5% 2% 0.5% 2% ;
                margin: 0.5% 2% 0.5% 2%;
                width: 15%;
                height: 4%;"  placeholder="Year" class="input-medium" type="text" name="year"  value="" id="year" onkeyup="this.value=this.value.replace(/[^0-9]+/g,''); isrighty(this);" />
       <input placeholder="Month" style="  padding:0.5% 2% 0.5% 2% ;
                margin: 0.5% 2% 0.5% 2%;
                width: 20%;
                height: 4%;"  class="input-medium" type="text" name="month" value="" id="month" onkeyup="this.value=this.value.replace(/[^0-9]+/g,''); isrightm(this);"/>
        <input class="btn btn-primary" type="button" value="Get profitabilty per month" name="siper2" onclick="doProf();"  style="width:20%"  /></p>
         <p align="right" ><input class="btn btn-primary" type="button" value="RI report" name="siper3" onclick="doRI();"  style="width:20%" /></p>
    </form>
    <%if(request.getAttribute("result") != null && !((String) request.getAttribute("result")).isEmpty()){%>
    <div style="padding: 1% 7% 1% 7%;
     margin: 1% 7% 1% 7%"  align="right" name="result">${requestScope.result}</div>
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

