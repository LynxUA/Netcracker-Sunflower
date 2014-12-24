package com.sunflower.report;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;


public class Xlscrtr {
    HSSFWorkbook wb;
    HSSFDataFormat format;
    HSSFRow row;
    HSSFCell cell;
    String filename = null;
    File f = null;

    private static Logger logger = Logger.getLogger(Xlscrtr.class);


    public File newor(Connection connection, Date d1, Date d2,ArrayList<String> locations) throws SQLException {

        java.sql.Date sd1 = new java.sql.Date(d1.getTime());//conver java date to sql date
        java.sql.Date sd2 = new java.sql.Date(d2.getTime());
        System.out.println(d1);
        System.out.print(sd1);
        String pst = "SELECT so.ID_ORDER,su.login,su.Name,su.Surname,p.Price_of_service" +//statement that get data for our report
                ",pl.location,so.So_Date From SERVICE_ORDER so INNER JOIN Sun_User su " +
                "On so.login=su.login INNER JOIN PRICE p On so.ID_Price = p.ID_Price" +
                " Inner JOIN PROVIDER_LOCATION pl On p.Id_Prov_location=pl.Id_Prov_location " +
                " WHERE so.ID_Scenario='1' AND so.ID_STATUS='4' AND pl.location IN (?";
        String locationpstmt = ",? ";
        for (int a = 1; a < locations.size(); a++)//we add ? to add location later 
        {
            pst += locationpstmt;
        }
        pst += ") and so.So_Date between ? and ? ORDER by pl.location";
        PreparedStatement pstmt = connection.prepareStatement(pst);
        int a;
        for (a = 1; a <= locations.size(); a++)//we set locations in statement 
        {
            pstmt.setString(a, locations.get(a - 1));
            System.out.println(a);
            System.out.println(locations.get(a - 1));
        }
        System.out.println(pst);

        pstmt.setDate(a, sd1);
        a++;
        System.out.println(a);
        pstmt.setDate(a, sd2);


        try {
            wb = new HSSFWorkbook();
            format = wb.createDataFormat();


            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");//set date format
            Date date = new Date();
            System.out.println(dateFormat.format(date));
            String strdate = dateFormat.format(date);//get current time
            filename = "NewOrderReport" + strdate + ".xls";
            f = new File(filename);
            FileOutputStream fileOut = new FileOutputStream(f);
            HSSFSheet sheet = wb.createSheet("New orders");
            int i = 0;

            Font font = CreateFont(12, "Times New Roman", IndexedColors.WHITE.getIndex());
            Font font1 = CreateFont(12, "Times New Roman", IndexedColors.BLACK.getIndex());
            CellStyle cellStyle1 = CreateCS(IndexedColors.WHITE.getIndex(), font1, false);//create cellstyle
            CellStyle cellStyle2 = CreateCS(IndexedColors.WHITE.getIndex(), font1, true);
            CellStyle cellStyle3 = CreateCS(IndexedColors.ROYAL_BLUE.getIndex(), font, false);
            CrtHEADER(sheet,cellStyle3,0,"ORDER_ID","Login","First Name","Last Name","Order Price","Provider Location","ORDER_DATE");//create header
            String location = null;
            int sum = 0;
            try {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    i++;

                    if (i > 1) {
                        if (!rs.getString(6).equals(location)) {
                            row = sheet.createRow(i);
                            CrtCell(cellStyle3,"New orders for " + location,0);
                            CrtCell(cellStyle3,sum,1);
                            sum = 0;
                            sheet.autoSizeColumn(0);
                            sheet.autoSizeColumn(1);
                            sheet.autoSizeColumn(2);
                            sheet.autoSizeColumn(3);
                            sheet.autoSizeColumn(4);
                            sheet.autoSizeColumn(5);
                            sheet.autoSizeColumn(6);//this function automatically aligns cells
                            sheet=wb.createSheet(rs.getString(6));//we create new sheet with location name
                            i=0;
                            //we set header for new sheet
                            CrtHEADER(sheet,cellStyle3,i,"ORDER_ID","Login","First Name","Last Name","Order Price","Provider Location","ORDER_DATE");
                            i++;
                        }
                    }
                    if(i==1)
                    {
                        wb.setSheetName(wb.getSheetIndex(sheet), rs.getString(6));//change name to location name
                    }
                    row = sheet.createRow(i);
                    CrtCell(cellStyle1,rs.getInt(1),0);
                    CrtCell(cellStyle1,rs.getString(2),1);
                    CrtCell(cellStyle1,rs.getString(3),2);
                    CrtCell(cellStyle1,rs.getString(4),3);
                    CrtCell(cellStyle1,rs.getInt(5),4);
                    CrtCell(cellStyle1,rs.getString(6),5);
                    CrtCell(cellStyle2,rs.getTimestamp(7),6);
                    location = rs.getString(6);
                    sum++;

                }
                i++;

                row = sheet.createRow(i);
                cell = row.createCell(0);
                cell.setCellStyle(cellStyle3);
                cell.setCellValue("New orders for "+location);
                cell = row.createCell(1);
                cell.setCellValue(sum);//print total number of new orders
                cell.setCellStyle(cellStyle3);
                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                wb.write(fileOut);
                fileOut.close();

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }


                return f;
            }


        public File ProfperMonth(Connection connection,Date d,ArrayList<String> locations) throws SQLException {
            File f=null;
            String pst = "SELECT so.ID_ORDER,p.Price_of_service" +
                    ",pl.location,so.So_Date From SERVICE_ORDER so INNER JOIN Sun_User su " +
                    "On so.login=su.login INNER JOIN PRICE p On so.ID_Price = p.ID_Price" +
                    " Inner JOIN PROVIDER_LOCATION pl On p.Id_Prov_location=pl.Id_Prov_location " +
                    " WHERE so.ID_Scenario='1' AND so.ID_STATUS='4' AND pl.location IN (?";
            String locationpstmt = ",? ";
            for (int a = 1; a < locations.size(); a++) {
                pst += locationpstmt;
            }
            pst += ") and so.So_Date BETWEEN ? AND ADD_MONTHS(?,1) ORDER by pl.location";
            PreparedStatement pstmt = connection.prepareStatement(pst);
            int a;
            for (a = 1; a <= locations.size(); a++) {
                pstmt.setString(a, locations.get(a - 1));
                System.out.println(a);
                System.out.println(locations.get(a - 1));
            }
            System.out.println(pst);
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");

            java.sql.Date sd = new java.sql.Date(d.getTime());

            System.out.println(dateFormat.format(date));
            String strdate = dateFormat.format(date);
            pstmt.setDate(a, sd);
            a++;
            System.out.println(a);
            pstmt.setDate(a, sd);

                try {
                    wb = new HSSFWorkbook();
                    format = wb.createDataFormat();
                    filename = "ProfitabilityPerMonthReport" + strdate + ".xls";
                    f = new File(filename);
                    FileOutputStream fileOut = new FileOutputStream(f);
                    HSSFSheet sheet = wb.createSheet("ProfitabilityPerMonth");
                    int i = 0;
                    double sum = 0;
                    Font font = CreateFont(12, "Times New Roman", IndexedColors.WHITE.getIndex());
                    Font font1 = CreateFont(12, "Times New Roman", IndexedColors.BLACK.getIndex());
                    CellStyle cellStyle1 = CreateCS(IndexedColors.WHITE.getIndex(), font1, false);
                    CellStyle cellStyle2 = CreateCS(IndexedColors.WHITE.getIndex(), font1, true);
                    CellStyle cellStyle3 = CreateCS(IndexedColors.ROYAL_BLUE.getIndex(), font, false);
                    CrtHEADER(sheet,cellStyle3,0,"ORDER_ID","Order Price","Location","Disconnection Date");

                    try {
                        ResultSet rs = pstmt.executeQuery();
                    String location=null;
                    while (rs.next()) {
                        i++;


                        if(i>1)
                        {
                            if(!rs.getString(3).equals(location))
                            {
                                row = sheet.createRow(i);
                                CrtCell(cellStyle3,"Profitability per month for "+location,0);
                                CrtCell(cellStyle3,sum,1);
                                sheet.autoSizeColumn(0);
                                sheet.autoSizeColumn(1);
                                sheet.autoSizeColumn(2);
                                sheet.autoSizeColumn(3);

                                sum=0;
                                sheet=wb.createSheet(rs.getString(3));
                                i=0;
                                CrtHEADER(sheet,cellStyle3,i,"ORDER_ID","Order Price","Location","Disconnection Date");
                                i++;
                            }
                        }
                        if(i==1)
                        {
                            wb.setSheetName(wb.getSheetIndex(sheet), rs.getString(3));
                        }
                        row = sheet.createRow(i);
                        CrtCell(cellStyle1,rs.getInt(1),0);
                        CrtCell(cellStyle1,rs.getInt(2),1);
                        sum += rs.getInt(2);
                        CrtCell(cellStyle1,rs.getString(3),2);
                        location=rs.getString(3);
                        CrtCell(cellStyle2,rs.getTimestamp(4),3);
                    }
                    i++;
                    row = sheet.createRow(i);
                        CrtCell(cellStyle3,"profitability per month for "+location,0);
                        CrtCell(cellStyle3,sum,1);
                        sheet.autoSizeColumn(0);
                    sheet.autoSizeColumn(1);
                    sheet.autoSizeColumn(2);
                    sheet.autoSizeColumn(3);

                    wb.write(fileOut);
                    fileOut.close();

                } catch (FileNotFoundException e) {
                    logger.error(e.getMessage(), e);
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
                catch (FileNotFoundException e) {
                    logger.error(e.getMessage(), e);
                }

            return f;

        }



    public File disconperperiod(Connection connection, Date d1, Date d2, ArrayList<String> locations) throws SQLException {



        java.sql.Date sd1 = new java.sql.Date(d1.getTime());
        java.sql.Date sd2 = new java.sql.Date(d2.getTime());
        System.out.println(d1);
        System.out.print(sd1);

        String pst = "SELECT so.ID_ORDER,su.login" +
                ",pl.location,so.So_Date From SERVICE_ORDER so INNER JOIN Sun_User su " +
                "On so.login=su.login INNER JOIN PRICE p On so.ID_Price = p.ID_Price" +
                " Inner JOIN PROVIDER_LOCATION pl On p.Id_Prov_location=pl.Id_Prov_location " +
                " WHERE so.ID_Scenario='3' AND so.ID_STATUS='4' AND pl.location IN (?";
        String locationpstmt = ",? ";
        for (int a = 1; a < locations.size(); a++) {
            pst += locationpstmt;
        }
        pst += ") and so.So_Date between ? and ? ORDER by pl.location";
        PreparedStatement pstmt = connection.prepareStatement(pst);
        int a;
        for (a = 1; a <= locations.size(); a++) {
            pstmt.setString(a, locations.get(a - 1));
            System.out.println(a);
            System.out.println(locations.get(a - 1));
        }
        System.out.println(pst);

        pstmt.setDate(a, sd1);
        a++;
        System.out.println(a);
        pstmt.setDate(a, sd2);


            File f = null;

            try {
                wb = new HSSFWorkbook();
                format = wb.createDataFormat();


                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
                Date date = new Date();
                System.out.println(dateFormat.format(date));
                String strdate = dateFormat.format(date);
                filename = "DisconnectOrdersReport" + strdate + ".xls";
                f = new File(filename);
                FileOutputStream fileOut = new FileOutputStream(f);
                HSSFSheet sheet = wb.createSheet("New orders");
                int i = 0;

                Font font = CreateFont(12, "Times New Roman", IndexedColors.WHITE.getIndex());
                Font font1 = CreateFont(12, "Times New Roman", IndexedColors.BLACK.getIndex());
                CellStyle cellStyle1 = CreateCS(IndexedColors.WHITE.getIndex(), font1, false);
                CellStyle cellStyle2 = CreateCS(IndexedColors.WHITE.getIndex(), font1, true);
                CellStyle cellStyle3 = CreateCS(IndexedColors.ROYAL_BLUE.getIndex(), font, false);

                CrtHEADER(sheet,cellStyle3,0,"ORDER_ID","Login","Location","Order date");

                int sum = 0;
                String location = null;
                try {
                    ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    i++;

                    if (i > 1) {
                        if (!rs.getString(3).equals(location)) {
                            row = sheet.createRow(i);
                            CrtCell(cellStyle3,"Disconnects for " + location,0);
                            CrtCell(cellStyle3,sum,1);
                            sheet.autoSizeColumn(0);
                            sheet.autoSizeColumn(1);
                            sheet.autoSizeColumn(2);
                            sheet.autoSizeColumn(3);
                            sheet.autoSizeColumn(4);
                            sum = 0;
                            sheet=wb.createSheet(rs.getString(3));
                            i=0;
                            CrtHEADER(sheet,cellStyle3,i,"ORDER_ID","Login","Location","Order date");
                            i++;
                        }
                    }
                    if(i==1)
                    {
                        wb.setSheetName(wb.getSheetIndex(sheet), rs.getString(3));
                    }

                    row = sheet.createRow(i);
                    CrtCell(cellStyle1,rs.getInt(1),0);
                    CrtCell(cellStyle1,rs.getString(2),1);
                    CrtCell(cellStyle1,rs.getString(3),2);
                    CrtCell(cellStyle2,rs.getTimestamp(4),3);
                    location = rs.getString(3);
                    sum++;

                }
                i++;
                row = sheet.createRow(i);
                cell = row.createCell(0);
                cell.setCellStyle(cellStyle3);
                cell.setCellValue("Disconnects for " + location);
                cell = row.createCell(1);
                cell.setCellValue(sum);
                cell.setCellStyle(cellStyle3);
                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                wb.write(fileOut);
                fileOut.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
            catch (FileNotFoundException e) {
                logger.error(e.getMessage(), e);
            }

            return f;

    }

    public File rireports(Connection connection, ArrayList<String> locations) throws SQLException {





        String pst = "select d.ID_DEVICE, d.name,d.Number_of_ports,d.free_ports," +//this statement get all values that we need and
                "p.id_port,p.status,pl.location,pr.Price_Of_service\n" +//sort it by location, device, and port
                "from Device d Inner Join PORT p on p.ID_Device=d.ID_DEVICE\n" +
                "  inner join Provider_location pl On pl.id_prov_location=d.id_prov_location\n" +
                "  Left join Circuit c On c.ID_Port=p.ID_port\n" +
                "  Left join Service_Instance si on c.ID_Circuit=si.ID_Circuit\n" +
                "  Left join Service_order so On so.ID_Service_inst=si.ID_Service_inst\n" +
                "  left join Price pr On pr.Id_price=so.Id_price  where pl.location IN (?";
        String locationpstmt = ",? ";
        for (int a = 1; a < locations.size(); a++) // we add ? symbol to statement
        {
            pst += locationpstmt;
        }
        pst += ")  ORDER by pl.location ,d.ID_Device, p.ID_Port ";
        PreparedStatement pstmt = connection.prepareStatement(pst);
        int a;
        for (a = 1; a <= locations.size(); a++) //we set locations instead of ? symbol
        {
            pstmt.setString(a, locations.get(a - 1));
            System.out.println(a);
            System.out.println(locations.get(a - 1));
        }


        File f = null;

        try {
            wb = new HSSFWorkbook();//we create our workbook
            format = wb.createDataFormat();


            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
            Date date = new Date();
            System.out.println(dateFormat.format(date));//get current date
            String strdate = dateFormat.format(date);
            filename = "RIreport" + strdate + ".xls";
            f = new File(filename);
            FileOutputStream fileOut = new FileOutputStream(f);
            HSSFSheet sheet = wb.createSheet("Ri reports");
            int i = 0;

            Font font = CreateFont(12, "Times New Roman",IndexedColors.BLACK.getIndex());
            Font font1 = CreateFont(12, "Times New Roman",IndexedColors.WHITE.getIndex());
            CellStyle cellStyle1 = CreateCS(IndexedColors.WHITE.getIndex(), font, false);
            CellStyle cellStyle2 = CreateCS(IndexedColors.WHITE.getIndex(), font, true);
            CellStyle cellStyle3 = CreateCS(IndexedColors.ROYAL_BLUE.getIndex(), font1, false);

            CrtHEADER(sheet,cellStyle3,0,"Device Id","Device name","Device location","Port","Status","SI price");

            double sum = 0;//profitability parametr
            String location = null;//parameter thaw we will compare to current location
            int capacity=0;//capacity of router parameter
            int device_id=0;//parameter thaw we will compare to current device id
            int utilization=0;//utilization of router
       
            Boolean newdev=true;// that parametr have true value when we in the first line of new device 
            int portmin=0;
            ;
            String status;//status of port
            double price;
            try {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    i++;
                  

                    if (i > 1) {
                        if (rs.getInt(1)!=device_id) {
                            row = sheet.createRow(i);
                            CrtCell(cellStyle3,"Router capacity",0);
                            CrtCell(cellStyle3, capacity,1);
                            CrtCell(cellStyle3,"Router utilization",2);
                            CrtCell(cellStyle3,"     "+utilization+"%   ",3);
                            CrtCell(cellStyle3,"Router profitability",4);
                            CrtCell(cellStyle3,"   "+sum+"grn ",5);
                            sheet.autoSizeColumn(0);
                            sheet.autoSizeColumn(1);
                            sheet.autoSizeColumn(2);
                            sheet.autoSizeColumn(3);
                            sheet.autoSizeColumn(4);
                            sheet.autoSizeColumn(5);
                            sheet.autoSizeColumn(6);
                            sheet.autoSizeColumn(7);//auto align cell
                            newdev=true;
                            sum = 0;
                            i+=2;
                            if(rs.getString(7)!=location)//create new sheet if location changed
                            {
                                sheet=wb.createSheet(rs.getString(7));
                                i=0;
                                CrtHEADER(sheet,cellStyle3,i,"Device Id","Device name","Device location","Port","Status","SI price");
                            }
                            else{
                                CrtHEADER(sheet,cellStyle3,i,"Device Id","Device name","Device location","Port","Status","SI price");
                            }
                            i++;
                        }
                    }
                      if(i==1)
                    {
                        wb.setSheetName(wb.getSheetIndex(sheet), rs.getString(7));
                    }

                    row = sheet.createRow(i);
                    if(newdev) //set values if we in the first row of new device
                    {
                        portmin=rs.getInt(5)-1;//value that we will subtract from port id value
                        CrtCell(cellStyle1,rs.getInt(1),0);
                        CrtCell(cellStyle1, rs.getString(2),1);
                        CrtCell(cellStyle1, rs.getString(7),2);
                        CrtCell(cellStyle1, rs.getString(2),4);
                        newdev=false;
                    }
                    else{
                        CrtCell(cellStyle1," ",0);
                        CrtCell(cellStyle1, " ",1);
                        CrtCell(cellStyle1, " ",2);
                        CrtCell(cellStyle1, " ",4);
                    }
                    CrtCell(cellStyle1,rs.getInt(5)-portmin,3);
                    if(rs.getInt(6)==0)
                        status="inactive";
                    else status="active";
                    CrtCell(cellStyle1, status,4);
                    price=rs.getDouble(8);
                    System.out.println("price=" +price);
                    if(price==0)
                    CrtCell(cellStyle1," - ",5);
                    else  if(rs.getInt(6)==1) CrtCell(cellStyle1,price,5);
                    else {
                        price=0;
                        CrtCell(cellStyle1, " - ", 5);
                    }
                    location = rs.getString(7);
                    device_id =rs.getInt(1);
                    

                    capacity=rs.getInt(3);
                    utilization= (int)(100.0-((rs.getInt(4)*100.0)/rs.getInt(3)));
                    sum+=price;


                }
                i++;
                row = sheet.createRow(i);
                CrtCell(cellStyle3,"Router capacity",0);
                CrtCell(cellStyle3,capacity,1);
                CrtCell(cellStyle3, "Router utilization ", 2);
                CrtCell(cellStyle3,"     "+utilization+"%   ", 3);
                CrtCell(cellStyle3, "Router profitability", 4);
                CrtCell(cellStyle3,"   "+sum+"grn ", 5);
                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                sheet.autoSizeColumn(7);
                wb.write(fileOut);
                fileOut.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }

        return f;

    }



        public CellStyle CreateCS(short clrindx, Font f,boolean datab)//function that create our cellstyle
        {
            CellStyle cellStyle1  = wb.createCellStyle();
            cellStyle1.setFont(f);
            short hAlignment=CellStyle.ALIGN_CENTER;
            cellStyle1.setAlignment(hAlignment);
            cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle1.setFillForegroundColor(clrindx);
            cellStyle1.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle1.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle1.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle1.setBorderTop(CellStyle.BORDER_THIN);
            if(datab)
                cellStyle1.setDataFormat(format.getFormat("m/d/yy"));
            return  cellStyle1;

        }
        public Font CreateFont(int size,String fontname,short clrindx)
        {
            Font font  = wb.createFont();
            font.setFontHeightInPoints((short)size);
            font.setColor(clrindx);
            System.out.println(clrindx);
            font.setFontName(fontname);
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            return font;
        }
        public void CrtCell(    CellStyle cellStyle, String value,int num)//different cell creation function for different value
        {
            cell = row.createCell(num);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(value);
        }
    public void CrtCell(    CellStyle cellStyle, int value,int num){
        cell = row.createCell(num);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
    }
    public void CrtCell(   CellStyle cellStyle, Timestamp value,int num){
        cell = row.createCell(num);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
    }
    public void CrtCell( CellStyle cellStyle, double value,int num){
        cell = row.createCell(num);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
    }
    public void CrtHEADER(HSSFSheet sheet,CellStyle cellStyle,int rownum, String... values)
    {

        row=sheet.createRow(rownum);
        int i=0;
        for(String value:values)
        {
            CrtCell(cellStyle,value,i);
            i++;
        }
    }
}

