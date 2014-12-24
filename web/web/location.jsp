<%@ page import="com.sunflower.ejb.ProviderLocation.ProviderLocWrapper" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.sunflower.ejb.EJBFunctions" %>
<%--
  Created by IntelliJ IDEA.
  User: denysburlakov
  Date: 29.11.14
  Time: 16:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  float x;
  float y;
  String location = (String) request.getSession().getAttribute("location");
  if(location == null){
    x = 50.402f;
    y = 30.532f;
  }else{
    String xy[] = location.split("\\s+");
    x = Float.valueOf(xy[0]);
    y = Float.valueOf(xy[1]);
  }
  ArrayList<ProviderLocWrapper> providerLocWrapper = EJBFunctions.getAllLocations();
%>
<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
  <meta charset="utf-8">
  <title>Order</title>
  <%@include file="includes.jsp"%>
  <script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
  <script>
    var geocoder = new google.maps.Geocoder();
    var map;
    var latlng;
    var marker_main;
    var image = 'img/chart.png';
    var locations = [
              <% int i = 1;
              for(ProviderLocWrapper location1 : providerLocWrapper){ %>
              ['<%=location1.getLocation()%>', <%=location1.getLatitude()%>, <%=location1.getLongtitude()%>, <%=i%>]
            <%
            if(providerLocWrapper.iterator().hasNext()){
            i++;
            %>,
            <%
            }
            }%>
            ];

    function geocodePosition(pos) {
      geocoder.geocode({
        latLng: pos
      }, function(responses) {
        if (responses && responses.length > 0) {
          updateMarkerAddress(responses[0].formatted_address);
          latlng = marker_main.position;
          document.getElementById('x').value = latlng.lat();
          document.getElementById('y').value = latlng.lng();
        } else {
          document.getElementById('address').value = 'Cannot determine address at this location.';
        }
      });
    }

    function updateMarkerAddress(str) {
      document.getElementById('address').value = str;
    }

    function updateMarkerPosition(latLng) {
      document.getElementById('x').value = latLng.lat();
      document.getElementById('y').value = latLng.lng();
    }

    function initialize() {
      latlng = new google.maps.LatLng(<%=x%>,<%=y%>);
      var mapOptions = {
        zoom: 11,
        center: latlng
      };
      map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
      marker_main = new google.maps.Marker({
        position: latlng,
        map: map,
        draggable: true,
        title: 'Your location'
      });

      updateMarkerPosition(latlng);
      geocodePosition(latlng);

      var marker, i;
      for (i = 0; i < locations.length; i++) {
        marker = new google.maps.Marker({
          position: new google.maps.LatLng(locations[i][1], locations[i][2]),
          map: map,
          icon: image

        });
      }

      google.maps.event.addListener(marker_main,'drag',function() {
        updateMarkerPosition(marker_main.getPosition());
      });

      google.maps.event.addListener(marker_main, 'dragend', function() {
        geocodePosition(marker_main.getPosition());
      });

      // Try HTML5 geolocation
      if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
          latlng = new google.maps.LatLng(position.coords.latitude,
                  position.coords.longitude);
          marker_main.setPosition(latlng);
          document.getElementById('x').value = latlng.lat();
          document.getElementById('y').value = latlng.lng();
          map.setCenter(latlng);
          geocodePosition(latlng);
        }, function() {
          handleNoGeolocation(true);
        });
      } else {
        // Browser doesn't support Geolocation
        handleNoGeolocation(false);
      }
    }

    function handleNoGeolocation(errorFlag) {
      if (errorFlag) {
        var content = 'Error: The Geolocation service failed.';
      } else {
        var content = 'Error: Your browser doesn\'t support geolocation.';
      }
      var options = {
        map: map,
        position: latlng,
        content: content
      };
      var infowindow = new google.maps.InfoWindow(options);
      map.setCenter(options.position);
    }
    function codeAddress() {
      var address = document.getElementById('address').value;
      geocoder.geocode( { 'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
          map.setCenter(results[0].geometry.location);
          marker_main.setPosition(results[0].geometry.location);
          latlng = marker_main.position;
          document.getElementById('x').value = latlng.lat();
          document.getElementById('y').value = latlng.lng();
        } else {
          alert('Error: Invalid location');
        }
      });
    }
    google.maps.event.addDomListener(window, 'load', initialize);
  </script>
  <script src="http://code.jquery.com/jquery-latest.js">
  </script>
  <script>
    $(document).ready(function() {
      $('#submit').click(function(event) {
        var x=$('#x').val();
        var y=$('#y').val();
        $.post('savelocation',{x:x,y:y});
      });
    });
  </script>
</head>
<body>
<%@include file="header.jsp"%>

<div class="container">
  <div>
    <form>
      <input type="hidden" id="x" name="x" value="50.402">
      <input type="hidden" id="y" name="y" value="30.532">
      <input id="address" class="form-control input-lg form-group" type="textbox" name="address" value="Kyiv">
      <input type="button" class="btn btn-success btn-block" value="Find location" onclick="codeAddress()">
      <input type="button" id="submit" class="btn btn-success btn-block" value="Save" style="margin-top: 10px">
    </form>
  </div>
  <div id="map-canvas"></div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>