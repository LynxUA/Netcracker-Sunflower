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
    </head>
    <body>
        <form name="crtr" action="xmlxprt">
        

        
                  
		<!-- add class="tcal" to your input field -->
		<div  ALIGN=CENTER> <strong>From </strong><input  type="text" name="date1" class="tcal" value="" id="date1" /></div>
	
                
      
       
                
		<!-- add class="tcal" to your input field -->
		<div  ALIGN=CENTER> <strong>To </strong><input  type="text" name="date2" class="tcal" value="" id="date2" /></div>
	
        <div  ALIGN=CENTER><select name="Select" id="option" >
            <option >New orders per period</option>
            <option>Disconnects per period</option>
          
        </select>  </div>
              
        <div  ALIGN=CENTER><input type="button" value="Get Report per period" name="siper" onclick="doCompletion();"   /></div>     
        
        <div  ALIGN=CENTER> <strong>Year</strong><input  type="text" name="year"  value="" id="year" /></div>
        <div  ALIGN=CENTER> <strong>Month</strong><input  type="text" name="month" value="" id="month" /></div>
        <div  ALIGN=CENTER><input type="button" value="GetProf" name="siper2" onclick="doProf();"   /></div>
         <div  ALIGN=CENTER><input type="button" value="RIreports" name="siper3" onclick="doRI();"   /></div> 
    </form>
        
    </body>
</html>
