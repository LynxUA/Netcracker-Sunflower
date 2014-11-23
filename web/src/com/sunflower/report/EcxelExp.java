package com.sunflower.report;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

/**
 * Created by Dima on 19.11.2014.
 */
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class EcxelExp {
    HSSFWorkbook ecxelWorkBook;
    HSSFSheet ecxelReport;
    ArrayList<String> tableHead;
    int ROW_NUM;

    EcxelExp() {
        ROW_NUM=0;
        ecxelWorkBook = new HSSFWorkbook();
        //ecxelReport = ecxelWorkBook.createSheet("Report");
        tableHead = new ArrayList<String>();
        tableHead.add("№");
        tableHead.add("Router id");
        tableHead.add("Location");
        tableHead.add("Number of ports");
        tableHead.add("Used ports");
        tableHead.add("Capacity %");
        tableHead.add("Erned");
    }


    private CellStyle getCellStyle()
    {
        Font font  = ecxelWorkBook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setColor(IndexedColors.WHITE.getIndex());
        System.out.println(IndexedColors.WHITE.getIndex());
        CellStyle cellStyle  = ecxelWorkBook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderBottom(CellStyle.BORDER_THICK);
        cellStyle.setBorderLeft(CellStyle.BORDER_THICK);
        cellStyle.setBorderRight(CellStyle.BORDER_THICK);
        cellStyle.setBorderTop(CellStyle.BORDER_THICK);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return cellStyle;
    }

    //RI Report Most Erned
    void createReport2() {
        ecxelReport = ecxelWorkBook.createSheet("ReportRIerned");
        Row row=ecxelReport.createRow(ROW_NUM++);
        CellStyle style=getCellStyle();
        for(int i=0;i<4;i++) {
            Cell cell=row.createCell(i);
            if(i==3) {
                cell.setCellValue(tableHead.get(tableHead.size()-1));
                System.out.println(tableHead.get(tableHead.size()-1));
            }else {
                cell.setCellValue(tableHead.get(i));
                System.out.println(tableHead.get(i));
            }
            cell.setCellStyle(style);

            ecxelReport.autoSizeColumn(i);
            //cell.setCell
        }

    }


    void addInfo2(ArrayList<String> list)
    {
        Row row=ecxelReport.createRow(ROW_NUM++);
        CellStyle style=getCellStyle();
        Cell cell=row.createCell(0);
        cell.setCellValue(ROW_NUM-1);
        cell.setCellStyle(style);
        for(int i=0;i<3;i++)
        {
            Cell cell1=row.createCell(i+1);
            if(i==2)
            {
               /* String s="E" + 1 + "/D"+1+"*100";
                cell.setCellFormula(s);*/
                cell1.setCellValue(list.get(list.size()-1));
            }else cell1.setCellValue(list.get(i));
            cell1.setCellStyle(style);
            ecxelReport.autoSizeColumn(i+1);
        }
    }


    //RI Report Capacity
    void createReport1()
    {
        ecxelReport = ecxelWorkBook.createSheet("ReportRIstat");
        Row row=ecxelReport.createRow(ROW_NUM++);
        CellStyle style=getCellStyle();
        for(int i=0;i<tableHead.size()-1;i++) {
            Cell cell=row.createCell(i);
            cell.setCellValue(tableHead.get(i));
            cell.setCellStyle(style);
            ecxelReport.autoSizeColumn(i);
            //cell.setCell
        }


    }

    void addInfo1(ArrayList<String> list)
    {
        Row row=ecxelReport.createRow(ROW_NUM++);
        CellStyle style=getCellStyle();
        Cell cell=row.createCell(0);
        cell.setCellValue(ROW_NUM-1);
        cell.setCellStyle(style);
        for(int i=0;i<list.size()-1;i++)
        {
            Cell cell1=row.createCell(i+1);
            if(i==list.size()-2)
            {
               /* String s="E" + 1 + "/D"+1+"*100";
                cell.setCellFormula(s);*/
                cell1.setCellValue(list.get(i));
            }else cell1.setCellValue(list.get(i));
            cell1.setCellStyle(style);
            ecxelReport.autoSizeColumn(i+1);
        }

    }





    public ArrayList Inf1() throws IOException {

        ArrayList<String> info=new ArrayList<String>();
        info.add("78");
        info.add("Kontraktova");
        info.add("77");
        info.add("50");
        info.add("65");
        info.add("200$");
        return info;
    }
     public ArrayList Inf2()  {
        ArrayList<String> info2=new ArrayList<String>();
        info2.add("573");
        info2.add("Pozniaki");
        info2.add("127");
        info2.add("120");
        info2.add("94");
        info2.add("500$");
        return info2;
     }

               public File Test(ArrayList in1, ArrayList in2)
                 {File f=null;
                     try{
                         
                     EcxelExp test=new EcxelExp();
        test.createReport1();
        test.createReport2();
                
        test.addInfo1(in1);
        test.addInfo1(in2);
        f=new File("new.xls");
        FileOutputStream out = new FileOutputStream(f);
        test.ecxelWorkBook.write(out);
        out.close();
                     }
                     catch(Exception ex)
                     {
                         ex.printStackTrace();
                     }
                 finally{
                         return f;
                     }    
        //System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
                 }
    }

