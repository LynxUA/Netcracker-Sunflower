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
      height: 3%;

    }
    div {padding: 0.3% 10% 0.3% 10%;
      margin: 0.3% 10% 0.3% 10%  }
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
      <a href="CurrentTask.jsp">Current Task</a>
    </li>
  </ul>
</aside>
<div><pre>   Port                                  User ID</pre></div>
<div>
  <select>
    <option selected="true" style="display:none" >Provider location</option>
    <option></option>
  </select>

  <input  type="text" >

  <button align="right" style="float: right;" class="btn btn-primary" width="15%">Assign port  </button>
</div>
<div>
  <select >
    <option selected="true" style="display:none;" >Select router</option>
    <option></option>
  </select>
  <button  align="right" style="float: right;" class="btn btn-primary" width="15%">Unssign port</button>

</div>
<div>
  <input  type="text" >
</div>

      <%@include file="footer.jsp"%>
    
  ,/body>
</html>
