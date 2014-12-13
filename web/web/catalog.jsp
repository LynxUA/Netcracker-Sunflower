<%@ page import="java.util.ArrayList" %>
<%@ page import="com.sunflower.ejb.ProviderLocation.ProviderLocWrapper" %>
<%@ page import="com.sunflower.ejb.EJBFunctions" %>
<%@ page import="com.sunflower.ejb.price.PriceCatalog" %>
<%
  String location = request.getParameter("location");
  ArrayList<ProviderLocWrapper> locationList = EJBFunctions.getAllLocations();
  ArrayList<PriceCatalog> catalogList = null;
  if(location != null) {
    catalogList = EJBFunctions.getServicePriceByLoc(location);
  }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
  <title>Catalog</title>
</head>
<body>

<%@include file="header.jsp"%>

<div id="body">
  <div class="container">
    <ul class="nav nav-tabs">
      <% for(ProviderLocWrapper pl : locationList) {%>
        <li role="presentation"><a href="catalog?location=<%=pl.getLocation()%>"><%=pl.getLocation()%></a></li>
      <%}%>
    </ul>

    <table class="table table-striped table-condensed">
      <thead>
      <tr>
        <th>Name</th>
        <th>Price of service</th>
        <th>Price of location</th>
      </tr>
      </thead>
      <tbody>
      <%if(location != null){
        for(PriceCatalog pc : catalogList) {%>
      <tr>
        <td><%=pc.getNameOfService()%></td>
        <td><%=pc.getPriceOfService()%></td>
        <td><%=pc.getPriceOfLocation()%></td>
      </tr>
      <%}}%>
      </tbody>
    </table>
    </div>
</div>

<%@include file="footer.jsp"%>
</body>
</html>
