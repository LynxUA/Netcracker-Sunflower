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
    var geocoder;
    var map;
    var latlng;
    var marker;
    function initialize() {
      geocoder = new google.maps.Geocoder();
      latlng = new google.maps.LatLng(<%=x%>,<%=y%>);
      var mapOptions = {
        zoom: 16,
        center: latlng
      }
      map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
      marker = new google.maps.Marker({
        position: latlng,
        map: map,
        draggable: true,
        title: 'Your location'
      });
      google.maps.event.addListener(marker,'drag',function() {
        geocodePosition(marker.getPosition());
        $('#services').html('');
        $('#price').html('');
      });
//      google.maps.event.addListener(marker,'dragend',function() {
//        geocodePosition(marker.getPosition());
//      });
      // Try HTML5 geolocation
      if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
          latlng = new google.maps.LatLng(position.coords.latitude,
                  position.coords.longitude);
          marker.setPosition(latlng);
          map.setCenter(latlng);
          document.getElementById('x').value = latlng.lat();
          document.getElementById('y').value = latlng.lng();
          geocodePosition(latlng);
          $('#services').html('');
          $('#price').html('');
        }, function() {
          //handleNoGeolocation(true);
        });
      } else {
        // Browser doesn't support Geolocation
        //handleNoGeolocation(false);
      }
    }
    function geocodePosition(pos) {
      geocoder.geocode({
        latLng: pos
      }, function(responses) {
        if (responses && responses.length > 0) {
          document.getElementById('address').value = responses[0].formatted_address;
          latlng = marker.position;
          document.getElementById('x').value = latlng.lat();
          document.getElementById('y').value = latlng.lng();
          $('#services').html('');
          $('#price').html('');
        } else {
          document.getElementById('address').value = 'Cannot determine address at this location.';
        }
      });
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
          marker.setPosition(results[0].geometry.location);
          latlng = marker.position;
          document.getElementById('x').value = latlng.lat();
          document.getElementById('y').value = latlng.lng();
          $('#services').html('');
          $('#price').html('');
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
//      $('#services').on('click', '#servicecheck', function (){
//        var x=document.getElementById('x').value;
//        var y=document.getElementById('y').value;
//        var service = $("input[name='services']:checked").val();
//        $.post('generateprices',{x:x, y:y, service:service},function(responseText) {
//          $('#price').html(responseText);
//        });
//      });
      $('#submit').click(function(event) {
        var x=$('#x').val();
        var y=$('#y').val();
        $.post('savelocation',{x:x,y:y});
        $.post('generateservices',{x:x, y:y},function(responseText) {
          $('#services').html(responseText);
        });
      });
    });
  </script>
</head>
<body>
<%@include file="header.jsp"%>

<div class="row">
  <div class="col-md-4" style="left: 20px">
    <form action="proceedorder" method="post">
      <input type="hidden" id="x" name="x" value="50.402">
      <input type="hidden" id="y" name="y" value="30.532">
      <input id="address" class="form-control input-lg form-group" type="textbox" name="address" value="Kyiv">
      <input type="button" class="btn btn-success btn-block" value="Find location" onclick="codeAddress()">
      <input type="button" id="submit" class="btn btn-success btn-block" value="Save" style="margin-top: 20px">
      <div id="services"></div>
      <%--<div class="price" id="price"></div>--%>

    </form>
  </div>
  <div class="col-md-8" align="right">
    <div id="map-canvas"/>
  </div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>