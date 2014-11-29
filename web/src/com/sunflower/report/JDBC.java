package com.sunflower.report;


import java.sql.*;

public class JDBC {
    final private static String driverName = "oracle.jdbc.OracleDriver";
    private static String url;
    final private static String server = "194.44.143.139";
    final private static String port = "1521";
    final private static String sid = "xe";
    final private static String username = "sunflower";
    final private static String password = "sunflower14";
    private static Connection connection;
    private static boolean isConnected = false;

    public  Connection connect() {
        try {

            url = "jdbc:oracle:thin:@194.44.143.139:1521/xe";

            System.out.println(url);
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("connecting: " + url);
            if(connection.equals(null))
                isConnected = false;
            else
                isConnected = true;
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException");
            isConnected = false;
        } catch (SQLException e) {
            System.out.println("SQLException\n" + e.getMessage());
            isConnected = false;
        }
        finally {    System.out.println(isConnected);
                return connection;
        }



                }
   /* public void add(ArrayList<Medicament> mds) throws SQLException {  PreparedStatement pstmt = connection.prepareStatement( "insert into drugstore values (?,?,?,?)");
        for (int i=0;i<mds.size();i++)
        {

            String state=null;
           state= mds.get(i).isPres_need()?"y":"n";
           String name=mds.get(i).getName();
           double price=mds.get(i).getPrice();
           int id=mds.get(i).getid();
               pstmt.setInt(1,id);
            pstmt.setString(2,name);
            pstmt.setDouble(3,price);
            pstmt.setString(4, state);
            ResultSet rs = pstmt.executeQuery();

        }


    }     */







 /*   public static void main(String args[])
    {
        JDBC jdbc=new JDBC();
        jdbc.connect();
        Xlscrtr xlscrtr=new Xlscrtr();
        Date d1=new Date(114,10,19);
        Date d2=new Date(114,10,20);
        Date d3=new Date(114,10,1);
        try {
            xlscrtr.newor(connection, d1, d2);
            xlscrtr.disconperperiod(connection, d1, d2);
            xlscrtr.ProfperMonth(connection,d3);
        }
           catch (SQLException sq)
           {
               sq.printStackTrace();
           }
    }
}*/
}