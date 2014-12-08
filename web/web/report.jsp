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
        <ul class="nav nav-list bs-docs-sidenav affix">

            <li>

                <input  type="checkbox" value="borshaga" name="borshaga"  id="borshaga"><font color="#296293">Borshaga</font>

            </li>

            <li>

                <input  type="checkbox" value="troya" name="troya" id="troya"/><font color="#296293">Troya</font>

            </li>

            <li>

                <input  type="checkbox" value="goloseevo" name="goloseevo" id="goloseevo"/><font color="#296293">Goloseevo</font>

            </li>
        </ul>
    </aside>
    <form accept-charset="UTF-8" role="form"  action="xmlxprt" method="get">
      <!--  <form name="crtr" action="xmlxprt">-->

		<!-- add class="tcal" to your input field -->
		<p align="right"> <input  type="text" name="date1" class=" input-medium tcal " value="" id="date1" placeholder="From" style="  padding:0.5% 2% 0.5% 2% ;
                margin: 0.5% 2% 0.5% 2%;
                width: 15%;
                height: 4%;" >

		<!-- add class="tcal" to your input field -->
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
        <input class="btn btn-primary" type="button" value="Get profiatilty per month" name="siper2" onclick="doProf();"  style="width:20%"  /></p>
         <p align="right" ><input class="btn btn-primary" type="button" value="RIreports" name="siper3" onclick="doRI();"  style="width:20%" /></p>
    </form>


    <%@include file="footer.jsp"%>
    </body>
</html>

