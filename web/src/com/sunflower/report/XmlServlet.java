
package com.sunflower.report;

import com.sunflower.ejb.DataSource;

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
    
 @Override
public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
    File f=null;

     String loc="loc1";
     int a=1;
     Connection  connection=null;

     ArrayList<String> locations =new ArrayList<String>();
     while(request.getParameter(loc)!=null)
     {
         System.out.println(request.getParameter(loc));
         if(!request.getParameter(loc).equals("undefined"))
             locations.add(request.getParameter(loc));
         String as=Integer.toString(a);
         a++;
         String as1=Integer.toString(a);
         loc=loc.replace(as,as1);
     }
     if(locations.size()==0)
     {
         request.setAttribute("result", "<font color=\"#ff0000\">Set locations<font>");
         request.getRequestDispatcher("report.jsp").forward(request, response);
         return;
     }

     try{
         String action=request.getParameter("action");

         Xlscrtr xlscrtr=new Xlscrtr();
         Boolean urladded=false;
        
      System.out.println(action);

         try {
            connection = DataSource.getDataSource().getConnection();

         }catch(SQLException e) {
             System.out.println(e.getErrorCode());
             System.out.println("something wrong with connection");
            return;
         }
       if(action.equals("periodic"))
       {System.out.println(action);
           String d1 = request.getParameter("date1");
         String d2 = request.getParameter("date2");
         String select=request.getParameter("option");
           System.out.println(select);
           System.out.println(d1);
           System.out.println(d2);

         System.out.println(select);
         String url = request.getRequestURL().toString();
         System.out.println(d2);System.out.println(url);
           Date date1=null;
           Date date2=null;
           try {
               date1 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(d1);
               date2 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(d2);
           }
           catch (Exception ex)
           {
               request.setAttribute("result", "<font color=\"#ff0000\">Wrong date format<font>");
               request.getRequestDispatcher("report.jsp").forward(request, response);
               return;
           }
     if(date2.after(date1))
     {
          System.out.println(date1);  
             if(select.equals("New orders per period")){

                 f=xlscrtr.newor(connection, date1, date2, locations);

                 if(f.getName()!=null)
                     urladded=true;
             }
             else  if(select.equals("Disconnects per period")){

                 f=xlscrtr.disconperperiod(connection, date1, date2,locations);
                 if(f.getName()!=null)
                     urladded=true;
             }
     }
           else
     {
         request.setAttribute("result", "<font color=\"#ff0000\">Wrong sequence of date<font>");
         request.getRequestDispatcher("report.jsp").forward(request, response);
         return;
     }

     }
       else if(action.equals("prof"))
       {
           String year=request.getParameter("year");
           String month=request.getParameter("month");
           if(year.equals("")||month.equals(""))
           {
               {
                   request.setAttribute("result", "<font color=\"#ff0000\">Set date please<font>");
                   request.getRequestDispatcher("report.jsp").forward(request, response);
                   return;
               }
           }
           Date date=new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(month + "/" + 1 + "/" + year);
           
           urladded=true;
           f=xlscrtr.ProfperMonth(connection, date,locations);
       }
       else if(action.equals("ri"))
       {
           urladded=true;
           f=xlscrtr.rireports(connection,locations);
        
       }
         if (urladded) {
     
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

     finally {
         try {
             if (connection != null) {
                 connection.close();
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
    }


   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
