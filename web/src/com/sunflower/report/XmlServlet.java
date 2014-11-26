/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sunflower.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import javax.servlet.ServletOutputStream;

/**
 *
 * @author Алексей
 */
public class XmlServlet extends HttpServlet {
private ServletContext context;
  @Override
public void init(ServletConfig config) throws ServletException {
    this.context = config.getServletContext();
}

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try  {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet XmlServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet XmlServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        finally {
        out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    
 @Override
public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
    File f=null;

     try{
         String action=request.getParameter("action");
         
         
         EcxelExp excelexp=new EcxelExp();
         Xlscrtr xlscrtr=new Xlscrtr();
         Boolean urladded=false;
        
      
         
        JDBC jdbc=new JDBC();
        Connection connection=jdbc.connect();
        // Date date1=new Date(114,10,10);
          //Date date2=new Date(114,10,20);
         //xlscrtr.ProfperMonth(connection, date1);
                 
       if(action.equals("periodic"))
       { String d1 = request.getParameter("date1");
         String d2 = request.getParameter("date2");
         String select=request.getParameter("option");
         System.out.println(select);
         String url = request.getRequestURL().toString();
         System.out.println(d2);System.out.println(url);
        Date date1 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(d1);
     Date date2 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(d2);
     if(date2.after(date1))
     {
          System.out.println(date1);  
             if(select.equals("New orders per period")){
                 urladded=true;
                 f=xlscrtr.newor(connection, date1, date2);
             }
             else  if(select.equals("Disconnects per period")){
                 urladded=true;
                 f=xlscrtr.disconperperiod(connection, date1, date2);
             }
     }
   
           
         
        
         
       
        
             System.out.println(f.getName());
     }
       else if(action.equals("prof"))
       {
           String year=request.getParameter("year");
           String month=request.getParameter("month");
           
           Date date=new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(month+"/"+1+"/"+year);
           
           urladded=true;
           f=xlscrtr.ProfperMonth(connection, date);
       }
         
       else if(action.equals("ri"))
       {
           urladded=true;
           ArrayList inf1= excelexp.Inf1();
           ArrayList inf2= excelexp.Inf1();
          f=excelexp.Test(inf1, inf2);
       }
         
         if (urladded) {
       //      response.setContentType("text/xml");
         //    response.setHeader("Cache-Control", "no-cache");
           //  response.getWriter().write("<composers>" + sb.toString() + "</composers>");
             response.setContentType("application/octet-stream");
             response.setHeader("Content-Disposition",
                "attachment;filename="+f.getName());
             
FileInputStream fileIn = new FileInputStream(f);
ServletOutputStream out = response.getOutputStream();
 
byte[] outputByte = new byte[4096];
//copy binary contect to output stream
while(fileIn.read(outputByte, 0, 4096) != -1)
{
	out.write(outputByte, 0, 4096);
}
fileIn.close();
out.flush();
out.close();
             f.delete();
         } else {
             //nothing to show
             response.setStatus(HttpServletResponse.SC_NO_CONTENT);
         }
         
     }
   
     catch(ParseException ex)
    {
         ex.printStackTrace();
     }
      catch(SQLException ex)
         {
             ex.printStackTrace();
         }
  
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
