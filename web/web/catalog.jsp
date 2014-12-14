<%@ page import="java.util.ArrayList" %>
<%@ page import="com.sunflower.ejb.ProviderLocation.ProviderLocWrapper" %>
<%@ page import="com.sunflower.ejb.EJBFunctions" %>
<%@ page import="com.sunflower.ejb.price.PriceCatalog" %>
<%
  String locationParam = request.getParameter("location");
  ArrayList<ProviderLocWrapper> locationList = EJBFunctions.getAllLocations();
  ArrayList<PriceCatalog> catalogList = EJBFunctions.getServicePriceByLoc("Troeschina");
  if(locationParam != null) {
    catalogList = EJBFunctions.getServicePriceByLoc(locationParam);
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
      <%int i=0;
        for(ProviderLocWrapper pl : locationList) {
            if(locationParam == null && i==0){
      %>
                <li role="presentation"><a href="catalog?location=<%=pl.getLocation()%>"><b><u><%=pl.getLocation()%></u></b></a></li>
      <% i++;
            } else{
                String location = pl.getLocation();
                if(locationParam !=null && locationParam.equals(location)) location = "<b><u>" + pl.getLocation() + "</u></b>";%>
                    <li role="presentation"><a href="catalog?location=<%=pl.getLocation()%>"><%=location%></a></li>
        <%        }
      }%>
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
      <%for(PriceCatalog pc : catalogList) {%>
      <tr>
        <td><%=pc.getNameOfService()%></td>
        <td><%=pc.getPriceOfService()%></td>
        <td><%=pc.getPriceOfLocation()%></td>
      </tr>
      <%}%>
      </tbody>
    </table>
    </div>
</div>

<%@include file="footer.jsp"%>
</body>
</html>
