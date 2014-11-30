
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="includes.jsp"%>
  <title>Netcracker Sunflower</title>
</head>
<body>

<%@include file="header.jsp"%>

<div id="body">


  <h1>Your catalog!</h1>




    <%  out.println("Your location :  "+request.getParameter("location")); %>
  <table border="1" width="50%">
    <tr>
      <th>Service ID</th>
      <th>Price</th>
      <th>Name</th>
    </tr>
    <tr>
      <td>1</td>
      <td>90 USD</td>
      <td>Volia Premimum </td>
    </tr>
    <tr>
      <td>2</td>
      <td>50 USD</td>
      <td>Triolan </td>
    </tr>

      <%
    boolean flag=false;

 //   out.println("Your location :  "+request.getParameter("location"));
    /*
String url = "jdbc:mysql://127.0.0.1:3306/DB";
Class.forName ("com.mysql.jdbc.Driver").newInstance ();
Connection conn = DriverManager.getConnection (url, "root", "");
conn.createStatement();

String selectTableSQL = "select * from service,location where service.location_id=location.location_id and location.name=? ";

PreparedStatement preparedStatement = conn.prepareStatement(selectTableSQL);
preparedStatement.setString(1,request.getParameter("location"));

ResultSet rs = preparedStatement.executeQuery();

while (rs.next()) {

	String userid = rs.getString("service_id");
	String name = rs.getString("name");
        String price = rs.getString("price");
        out.println("<tr>");
out.println("<td>");       out.println(userid); out.println("</td>");
        out.println("<td>"); out.println(price);  out.println("</td>");
        out.println("<td>"); out.println(name);  out.println("</td>");
        out.println("</tr>");
        flag=true;

}
*/

if (!flag)
{
     out.println("No available services near your location");
}



 %>



  <%@include file="footer.jsp"%>
</body>
</html>