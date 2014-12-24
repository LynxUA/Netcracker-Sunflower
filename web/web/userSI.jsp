<%@ page import="com.sunflower.ejb.serviceinstance.SIWrapper" %>
<%@ page import="com.sunflower.ejb.EJBFunctions" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.sunflower.ejb.serviceinstance.ServiceLocationWrapper" %>
<%--
  Created by IntelliJ IDEA.
  User: denysburlakov
  Date: 15.12.14
  Time: 20:10
  To change this template use File | Settings | File Templates.
--%>
<%
  int user_status = (Integer)(request.getSession().getAttribute("status"));
  String order_login;
  if(user_status == UserGroups.CUSTOMER) {
    order_login = (String) (request.getSession().getAttribute("login"));
  }else if (user_status == UserGroups.CSE){
    order_login = request.getParameter("login");
  }else{
    response.sendRedirect("/webWeb/");
    return;
  }
  int from = 1;
  int to = 10;
  if(request.getParameter("to")!=null&&request.getParameter("from")!=null){
    to = Integer.parseInt(request.getParameter("to"));
    from = Integer.parseInt(request.getParameter("from"));
  }
  Vector<SIWrapper> serviceInstances = (Vector<SIWrapper>)
          (EJBFunctions.getServiceInstances(order_login, from, to));
  int numberOfRecords = EJBFunctions.getNumberOfInstancesByLogin(order_login);

  Vector<ServiceLocationWrapper> SLWrapper = (Vector<ServiceLocationWrapper>) EJBFunctions.getSLByLogin(order_login);

%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
  <script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
  <script>
    var map;
    var image = 'img/chart.png';
    var locations = [
      <% int i = 1;
      for(ServiceLocationWrapper location1 : SLWrapper){ %>
      ['<%=location1.getService()%>', <%=location1.getLatitude()%>, <%=location1.getLongtitude()%>, <%=i%>]
      <%
      if(SLWrapper.iterator().hasNext()){
      i++;
      %>,
      <%
      }
      }%>
    ];
    function initialize() {
      var mapOptions = {
        zoom: 11,
        center: new google.maps.LatLng(50.402,30.532)
      };
      map = new google.maps.Map(document.getElementById('map-canvas'),
              mapOptions);
      var market, i;
      for (i = 0; i < locations.length; i++) {
        marker = new google.maps.Marker({
          position: new google.maps.LatLng(locations[i][1], locations[i][2]),
          map: map,
          icon: image

        });
      }
    }

    google.maps.event.addDomListener(window, 'load', initialize);

  </script>
  <title>User service instances</title>
</head>
<body>


<%@include file="header.jsp"%>


<div class="container" style="padding-bottom: 30px">
  <h3>Service instances</h3>
  <%if(serviceInstances.size() == 0){%>

  <h1>Sorry, but you don`t have any service instances.</h1>
  <%} else {%>
  <div class="row">
    <div class="span5">
      <table class="table table-striped table-condensed">
        <thead>
        <tr>
          <th>SI ID</th>
          <th>Status</th>
          <th>Service</th>
          <th>Service Location</th>
          <th>Provider office</th>
          <%if(status == UserGroups.CUSTOMER){%><th>Disconnect</th><%}%>
        </tr>
        </thead>
        <tbody>
        <%for(SIWrapper instance:serviceInstances){%>
        <tr>
        <td><%=instance.getId_service_inst()%></td>
        <td><span
                <%
                if(instance.getStatusName().contains("Planned")){
                %>
                class="label label-warning"
                <%
                } else if(instance.getStatusName().contains("Active")){
                %>
                class="label label-success"
                <%
                }else if(instance.getStatusName().contains("Disconnected")){
                %>
                class="label label-danger"
                <%
                  }
                %>
                >
              <%=instance.getStatusName()%></span>
        </td>
        <td><%=instance.getServiceName()%></td>
        <td><%=instance.getLongtitude()%> <%=instance.getLatitude()%></td>
        <td><%=instance.getLocation()%></td><td><%if(status == UserGroups.CUSTOMER && instance.getStatusName().contains("Active"))
        {%><a href="disconnect?id_service_inst=<%=instance.getId_service_inst()%>">Disconnect</a><%}%></td>
        </tr>
            <%}%>
        </tbody>
      </table>
    </div>
  </div>
  <%}%>
  <%if(numberOfRecords >10){%>
  <div class="pagination pagination-centered">
    <ul class="pagination pagination-centered">
      <li class="<%if(to/10==1){%>disabled<%}else{%>active<%}%>"><a href="user_si?from=1&to=10">&laquo;</a></li>
      <%if(!(to/10 == 1)){%><li class="active"><a href="user_si?from=<%=from-10%>&to=<%=to-10%>"><%=(to-10)/10%></a></li><%}%>
      <li class="disabled"><a href="#"><%=to/10%></a></li>
      <%if(!(from+10 > numberOfRecords )){%><li class="active"><a href="user_si?from=<%=from+10%>&to=<%=to+10%>"><%=(to+10)/10%></a></li><%}%>
      <%if(!(from+20 > numberOfRecords )){%><li class="active"><a href="user_si?from=<%=numberOfRecords - (numberOfRecords%10)%>&to=<%=(numberOfRecords+10) - (numberOfRecords%10)%>">&raquo;</a></li><%}%>
    </ul>
  </div>
  <%}%>
  <div id="map-canvas"></div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
