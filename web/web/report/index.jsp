<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>XLS EXPORT</title>
        
        <link rel="stylesheet" type="text/css" href="tcal.css" />
         
	<script type="text/javascript" src="tcal.js"  ></script> 
     <script type="text/javascript"  src="reports.js" ></script>
        <%@include file="../includes.jsp"%>
        <style>
            aside {
                padding: 10px;
                width: 200px;
                float: left;
            }
            select
            {
                width: 20%;
            }
            pre {
                width:10%
            }
            p {padding: 0.3% 7% 0.3% 7%;
                margin: 0.3% 7% 0.3% 7%  }
        </style>
    </head>
    <body>
    <%@include file="../header.jsp"%>
    <aside>
        <ul class="nav nav-list bs-docs-sidenav affix">
            <li>
                <input type="checkbox" value="borshaga" name="borshaga"  id="borshaga"><strong> Borshaga</strong>
            </li>
            <li>
                <input type="checkbox" value="troya" name="troya" id="troya"/><strong> Troya</strong>
            </li>
            <li>
                <input type="checkbox" value="goloseevo" name="goloseevo" id="goloseevo"/><strong> Goloseevo</strong>
            </li>
        </ul>
    </aside>
        <form name="crtr" action="xmlxprt">



      
               
                
		<!-- add class="tcal" to your input field -->
		<p align="right">From <input  type="text" name="date1" class="tcal" value="" id="date1" />
	
                
      
       
                
		<!-- add class="tcal" to your input field -->
		 To <input  type="text" name="date2" class="tcal" value="" id="date2" />
	
       <select name="Select" id="option" >
            <option >New orders per period</option>
            <option>Disconnects per period</option>
          
        </select>
              
        <input class="btn btn-primary" type="button" value="Get Report per period" name="siper" onclick="doCompletion();"   style="width:20%" /></p>

        <p align="right" > Year <input  type="text" name="year"  value="" id="year" onkeyup="this.value=this.value.replace(/[^0-9]+/g,''); isrighty(this);" />
         Month <input  type="text" name="month" value="" id="month" onkeyup="this.value=this.value.replace(/[^0-9]+/g,''); isrightm(this);"/>
        <input class="btn btn-primary" type="button" value="Get profiatilty per month" name="siper2" onclick="doProf();"  style="width:20%"  /></p>
         <p align="right" ><input class="btn btn-primary" type="button" value="RIreports" name="siper3" onclick="doRI();"  style="width:20%" /></p>
    </form>
    <br>
    <%@include file="../footer.jsp"%>
    </body>
</html>

