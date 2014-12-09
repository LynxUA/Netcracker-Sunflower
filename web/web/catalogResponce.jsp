<%@page import="java.sql.PreparedStatement"%>


<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.SQLException"%>


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



    <h1><% if(request.getParameter("location")=="")
        out.println("Your location could not determined");
    else out.println("Your location :  "+request.getParameter("location")); %> </h1>
        <%  out.println("Your location :  "+request.getParameter("location")); %>
    <table border="1" width="50%">
        <tr>
            <th>Service ID</th>
            <th>Location</th>
            <th>Price</th>
        </tr>


            <%

    boolean flag=false;
    if(request.getParameter("location")!="")
    {
//   out.println("Your location :  "+request.getParameter("location"));

String url = "jdbc:oracle:thin:@194.44.143.139:1521/xe";;
Class.forName ("oracle.jdbc.driver.OracleDriver").newInstance ();
Connection conn = DriverManager.getConnection (url, "sunflower", "sunflower14");



String selectTableSQL = "";
selectTableSQL="select *  from PRICE INNER JOIN PROVIDER_LOCATION ON  PRICE.ID_PROV_LOCATION=PROVIDER_LOCATION.ID_PROV_LOCATION "
         + "WHERE PROVIDER_LOCATION.LOCATION LIKE '"+request.getParameter("location")+"%'";
Statement stmt =null;

stmt = conn.createStatement();
ResultSet rs=null;
try{
rs = stmt.executeQuery(selectTableSQL);
}
catch(Exception c)
        {
        out.println( c.getMessage());
        }

while (rs.next()) {

	String userid = rs.getString("ID_PRICE");
	String name = rs.getString("PRICE_OF_SERVICE");
        String price = rs.getString("LOCATION");
        out.println("<tr>");
out.println("<td>");       out.println(userid); out.println("</td>");
        out.println("<td>"); out.println(price);  out.println("</td>");
        out.println("<td>"); out.println(name);  out.println("</td>");
        out.println("</tr>");
        flag=true;

}


if (!flag)
{
     out.println("No available services near your location");
}

   }

 %>



        <%@include file="footer.jsp"%>
</body>
</html>