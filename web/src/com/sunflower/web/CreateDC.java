package com.sunflower.web;

import com.sunflower.ejb.DataSource;
import com.sunflower.ejb.EJBFunctions;
import com.sunflower.ejb.task.LocalTask;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 * Created by Алексей on 12/16/2014.
 */
@WebServlet(name = "CreateDC")
public class CreateDC extends HttpServlet {
    private ServletContext context;
    private static Logger logger = Logger.getLogger(CreateDC.class);
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action= request.getParameter("action");

        if(action.equals("cable")){
            if(request.getParameter("lenght")=="")
            {
                request.setAttribute("result", "<font color=\"#ff0000\">Set lenght</font>");
                request.getRequestDispatcher("CreateDC.jsp").forward(request, response);
                return;
            }
            float lenght= Float.valueOf(request.getParameter("lenght"));
            String type=request.getParameter("type");


            if(type.equals(""))
            {
                request.setAttribute("result", "<font color=\"#ff0000\">Please write cable type</font>");
                request.getRequestDispatcher("CreateDC.jsp").forward(request, response);
                return;
            }

            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet rs;
            try {
                try{
                    connection = DataSource.getDataSource().getConnection();
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                    throw new EJBException("Ошибка dataSource");
                }
                statement = connection.prepareStatement("SELECT t.ID_TASK FROM TASK t INNER JOIN SERVICE_ORDER so On so.ID_ORDER=t.ID_ORDER where t.login=? and so.Id_Status!=4 and so.Id_Status!=2");
                statement.setString(1,(String)request.getSession().getAttribute("login"));
                rs = statement.executeQuery();
                if(!rs.next())
                {

                    request.setAttribute("result", "<font color=\"#ff0000\">Something wrong</font>");
                    request.getRequestDispatcher("CreateDC.jsp").forward(request, response);
                }
                int Id_Task=rs.getInt(1);
                LocalTask localTask= EJBFunctions.findLocalTaskById(Id_Task);
                String description=localTask.getDescription();
                String[] desc=description.split("Cable id");
                if (desc.length>1)
                {
                    request.setAttribute("result", "<font color=\"#191970\">Cable is already connected</font>");
                    request.getRequestDispatcher("CreateDC.jsp").forward(request, response);
                    return;
                }
                statement = connection.prepareStatement("INSERT INTO CABLE"
                        + "(LENGHT ,TYPE ) VALUES(?, ?)");
                statement.setFloat(1, lenght);
                statement.setString(2, type);
                statement.executeQuery();

                statement = connection.prepareStatement("SELECT MAX(ID_CABLE) FROM CABLE");
                 rs = statement.executeQuery();
                rs.next();
                int id_cable=rs.getInt(1);

                localTask.setDescription(description+" ,Cable id = "+id_cable);
                request.setAttribute("result", "<font color=\"#191970\">Cable created</font>");
                request.getRequestDispatcher("CreateDC.jsp").forward(request, response);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                //throw new EJBException("Ошибка INSERT");

            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if(action.equals("device")){
            Connection connection = null;
            PreparedStatement statement = null;
            try {
                try{
                    connection = DataSource.getDataSource().getConnection();
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                    throw new EJBException("Ошибка dataSource");
                }
                statement = connection.prepareStatement("Select pl.Id_PRov_location From SERVICE_ORDER so INNER  JOIN  PRICE p " +
                        "ON so.ID_PRICE=p.ID_PRICE INNER  join PROVIDER_LOCATION pl " +
                        "on p.ID_PROV_LOCATION=pl.ID_PROV_LOCATION INNER JOIN TASK t" +
                        " On t.ID_order=so.ID_Order where so.Id_Status!=4 and t.login =? and so.Id_Status!=2 " );
                statement.setString(1,(String)request.getSession().getAttribute("login"));
                System.out.println((String)request.getSession().getAttribute("login"));
                ResultSet resultSet=statement.executeQuery();
                resultSet.next();
                int Id_Prov_Loc=resultSet.getInt(1);
                String name= request.getParameter("Devicename");
                statement = connection.prepareStatement("INSERT INTO DEVICE"
                        + "(Name ,ID_Prov_location ) VALUES(?, ?)");

                statement.setString(1, name);
                statement.setInt(2, Id_Prov_Loc);
                resultSet=statement.executeQuery();
               // response.setContentType("text/html;charset=UTF-8");
                //response.getWriter().write("<font color=\"#191970\">Device successfully created</font>");

                request.setAttribute("result", "<font color=\"#191970\">Device successfully created</font>");
                request.getRequestDispatcher("CreateDC.jsp").forward(request, response);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                //throw new EJBException("Ошибка INSERT");

            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);

                }
            }

        }
    }
}
