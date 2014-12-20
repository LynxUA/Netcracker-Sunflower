package com.sunflower.report;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;



public class Xlscrtr {
    HSSFWorkbook wb;
    HSSFDataFormat format;
    HSSFRow row;
    HSSFCell cell;
    String filename=null;
    File f=null;

    private static Logger logger = Logger.getLogger(Xlscrtr.class);

    public File newor(Connection connection, Date d1, Date d2,ArrayList<String> locations) throws SQLException {

        java.sql.Date sd1=new java.sql.Date(d1.getTime());
        java.sql.Date sd2=new java.sql.Date(d2.getTime());
        System.out.println(d1);
        System.out.print(sd1);
        String pst ="SELECT * FROM orders WHERE location=? ";
        String locationpstmt="OR location =? ";
        for (int a=1; a<locations.size();a++)
        {
            pst+=locationpstmt;
        }
        pst+="and Order_date between ? and ?";
        PreparedStatement pstmt = connection.prepareStatement(pst);
        int a;
        for (a=1; a<=locations.size();a++)
        {
            pstmt.setString(a,locations.get(a-1));
            System.out.println(a);
            System.out.println(locations.get(a-1));
        }
        System.out.println(pst);

        pstmt.setDate(a, sd1);
        a++;
        System.out.println(a);
        pstmt.setDate(a,sd2);
        ResultSet rs = pstmt.executeQuery();
        
        try {
            wb = new HSSFWorkbook();
            format = wb.createDataFormat();


            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
            Date date = new Date();
            System.out.println(dateFormat.format(date));
            String strdate= dateFormat.format(date);
            filename="NewOrderReport"+strdate+".xls";
             f=new File(filename);
            FileOutputStream fileOut = new FileOutputStream(f);
            HSSFSheet sheet = wb.createSheet("New orders");
            int i=0;

            Font font=CreateFont(12,"Times New Roman");
            CellStyle cellStyle1= CreateCS(IndexedColors.GREY_50_PERCENT.getIndex(), font,false);
            CellStyle cellStyle2= CreateCS(IndexedColors.GREY_50_PERCENT.getIndex(), font,true);
            CellStyle cellStyle3= CreateCS(IndexedColors.GREEN.getIndex(), font,false);

            while (rs.next()) {

                row = sheet.createRow( i );
                 i++;
                cell = row.createCell( 0);
                cell.setCellStyle(cellStyle1) ;
                cell.setCellValue(rs.getInt("ORDER_ID"));
                    cell = row.createCell( 1);
                cell.setCellStyle(cellStyle1) ;
                cell.setCellValue(rs.getString("FIRSTNAME"));
                cell = row.createCell( 2);
                cell.setCellStyle(cellStyle1) ;
                cell.setCellValue(rs.getString("LASTNAME"));

                    cell = row.createCell( 3);
                cell.setCellStyle(cellStyle1) ;
                cell.setCellValue(rs.getString("ORDERTYPE"));
                cell = row.createCell( 4);
                cell.setCellStyle(cellStyle1) ;
                cell.setCellValue(rs.getString("LOCATION"));
                    cell = row.createCell( 5);
                cell.setCellStyle(cellStyle2);
                cell.setCellValue(rs.getTimestamp("ORDER_DATE"));

                            }
            row = sheet.createRow( i );
            cell = row.createCell(0);
            cell.setCellStyle(cellStyle3) ;
            cell.setCellValue("Number of orders");
            cell = row.createCell( 1);
            cell.setCellValue(i);
            cell.setCellStyle(cellStyle3) ;
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            wb.write(fileOut);
            fileOut.close();
         
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            
        }
            finally{
        return f;
    }
                     }
        public File ProfperMonth(Connection connection,Date d) {
            File f=null;
            try {
                wb = new HSSFWorkbook();
                format = wb.createDataFormat();

                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");

                java.sql.Date sd = new java.sql.Date(d.getTime());

                System.out.println(dateFormat.format(date));
                String strdate = dateFormat.format(date);
                filename = "ProfitabilityPerMonthReport" + strdate + ".xls";
                f=new File(filename);
                PreparedStatement pstmt = connection.prepareStatement("SELECT ORDER_ID, ORDER_PRICE, ORDER_DATE, LOCATION FROM orders WHERE Order_date BETWEEN ? AND ADD_MONTHS(?,1) ");
                pstmt.setDate(1, sd);
                pstmt.setDate(2, sd);
                ResultSet rs = pstmt.executeQuery();
                FileOutputStream fileOut = new FileOutputStream(f);

                HSSFSheet sheet = wb.createSheet("ProfitabilityPerMonth");
                int i = 0;
                double sum=0;

                Font font = CreateFont(12, "Times New Roman");
                CellStyle cellStyle1 = CreateCS(IndexedColors.GREY_50_PERCENT.getIndex(), font, false);
                CellStyle cellStyle2 = CreateCS(IndexedColors.GREY_50_PERCENT.getIndex(), font, true);
                CellStyle cellStyle3 = CreateCS(IndexedColors.GREEN.getIndex(), font, false);
                while (rs.next()) {

                    row = sheet.createRow(i);
                    i++;
                    cell = row.createCell( 0);
                    cell.setCellStyle(cellStyle1) ;
                    cell.setCellValue(rs.getInt("ORDER_ID"));


                    cell = row.createCell(1);
                    cell.setCellStyle(cellStyle1);
                    cell.setCellValue(rs.getDouble("ORDER_PRICE"));
                    sum+=rs.getDouble("ORDER_PRICE");
                    cell = row.createCell(2);
                    cell.setCellStyle(cellStyle1) ;
                    cell.setCellValue(rs.getString("LOCATION"));
                    cell = row.createCell(3);
                    cell.setCellStyle(cellStyle2);
                    cell.setCellValue(rs.getTimestamp("ORDER_DATE"));
                }
                row = sheet.createRow(i);
                cell = row.createCell(0);
                cell.setCellStyle(cellStyle3);
                cell.setCellValue("profitability per month");
                cell = row.createCell(1);
                cell.setCellStyle(cellStyle3);
                cell.setCellValue(sum);
                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                wb.write(fileOut);
                fileOut.close();

            } catch (FileNotFoundException e) {
                logger.error(e.getMessage(), e);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            finally{
                return f;
            }
        }

        public File disconperperiod(Connection connection, Date d1, Date d2, ArrayList<String> locations) throws SQLException {



                java.sql.Date sd1 = new java.sql.Date(d1.getTime());
                java.sql.Date sd2 = new java.sql.Date(d2.getTime());
                System.out.println(d1);
                System.out.print(sd1);

            String pst ="SELECT ORDER_ID ,FIRSTNAME, LASTNAME, Location, ORDER_DATE FROM orders WHERE ORDERTYPE='disconnect' and location in ( ?";
            String locationpstmt=",? ";
            for (int a=1; a<locations.size();a++)
            {

                pst+=locationpstmt;
            }
            pst+=") and Order_date between ? and ?";
            System.out.println(pst);
            PreparedStatement pstmt = connection.prepareStatement(pst);
            int a;
            for (a=1; a<=locations.size();a++)
            {
                pstmt.setString(a,locations.get(a-1));
                System.out.println(a);
                System.out.println(locations.get(a-1));
            }
            System.out.println(pst);

            pstmt.setDate(a, sd1);
            a++;
            System.out.println(a);
            pstmt.setDate(a,sd2);
            ResultSet rs = pstmt.executeQuery();

                File f=null;

                try {
                    wb = new HSSFWorkbook();
                    format = wb.createDataFormat();


                    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
                    Date date = new Date();
                    System.out.println(dateFormat.format(date));
                    String strdate = dateFormat.format(date);
                    filename = "DisconnectOrdersReport" + strdate + ".xls";
                    f=new File(filename);
                    FileOutputStream fileOut = new FileOutputStream(f);
                    HSSFSheet sheet = wb.createSheet("New orders");
                    int i = 0;

                    Font font = CreateFont(12, "Times New Roman");
                    CellStyle cellStyle1 = CreateCS(IndexedColors.GREY_50_PERCENT.getIndex(), font, false);
                    CellStyle cellStyle2 = CreateCS(IndexedColors.GREY_50_PERCENT.getIndex(), font, true);
                    CellStyle cellStyle3 = CreateCS(IndexedColors.GREEN.getIndex(), font, false);

                    while (rs.next()) {

                        row = sheet.createRow(i);
                        i++;
                        cell = row.createCell(0);
                        cell.setCellStyle(cellStyle1);
                        cell.setCellValue(rs.getInt("ORDER_ID"));
                        cell = row.createCell(1);
                        cell.setCellStyle(cellStyle1);
                        cell.setCellValue(rs.getString("FIRSTNAME"));
                        cell = row.createCell(2);
                        cell.setCellStyle(cellStyle1);
                        cell.setCellValue(rs.getString("LASTNAME"));


                        cell = row.createCell(3);
                        cell.setCellStyle(cellStyle1);
                        cell.setCellValue(rs.getString("LOCATION"));
                        cell = row.createCell(4);
                        cell.setCellStyle(cellStyle2);
                        cell.setCellValue(rs.getTimestamp("ORDER_DATE"));

                    }
                    row = sheet.createRow(i);
                    cell = row.createCell(0);
                    cell.setCellStyle(cellStyle3);
                    cell.setCellValue("Number of disconnections");
                    cell = row.createCell(1);
                    cell.setCellValue(i);
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
                       finally{
        return f;
    }
            }



        public CellStyle CreateCS(short clrindx, Font f,boolean datab)
        {
            CellStyle cellStyle1  = wb.createCellStyle();
            cellStyle1.setFont(f);
            cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle1.setFillForegroundColor(clrindx);
            cellStyle1.setBorderBottom(CellStyle.BORDER_THICK);
            cellStyle1.setBorderBottom(CellStyle.BORDER_THICK);
            cellStyle1.setBorderLeft(CellStyle.BORDER_THICK);
            cellStyle1.setBorderRight(CellStyle.BORDER_THICK);
            cellStyle1.setBorderTop(CellStyle.BORDER_THICK);
            if(datab)
                cellStyle1.setDataFormat(format.getFormat("m/d/yy h:mm:s"));
            return  cellStyle1;

        }
        public Font CreateFont(int size,String fontname)
        {
            Font font  = wb.createFont();
            font.setFontHeightInPoints((short)size);
            font.setColor(IndexedColors.WHITE.getIndex());
            System.out.println(IndexedColors.WHITE.getIndex());
            font.setFontName(fontname);
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            return font;
        }
}

