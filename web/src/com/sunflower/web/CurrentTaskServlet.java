package com.sunflower.web;

import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
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

import com.sunflower.ejb.DataSource;
import com.sunflower.ejb.EJBFunctions;
import com.sunflower.ejb.ServiceOrder.LocalServiceOrder;
import com.sunflower.ejb.port.LocalPort;
import com.sunflower.ejb.serviceinstance.LocalServiceInstance;
import com.sunflower.ejb.task.LocalTask;
import org.apache.log4j.Logger;

/**
 * Created by Алексей on 12/9/2014.
 */
@WebServlet(name = "CurrentTaskServlet")
public class CurrentTaskServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(AssignServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      String action =  request.getParameter("action");
        System.out.println(action);

        int key = Integer.parseInt(request.getParameter("key"));
        System.out.println(key);

        LocalTask localTask= EJBFunctions.findLocalTaskById(key);

        System.out.println(localTask);

            if (action.equals("complete"))
            {
                int Id_Si=Integer.parseInt(request.getParameter("Id_Si"));
                int Id_Order=Integer.parseInt(request.getParameter("Id_Order"));
                int Id_Scenario=Integer.parseInt(request.getParameter("Id_Scenario"));
                //localTask.setStatus("completed");
                LocalServiceInstance localServiceInstance=EJBFunctions.findServiceInstance(Id_Si);
                LocalServiceOrder localServiceOrder=EJBFunctions.findServiceOrder(Id_Order);
                if(Id_Scenario==1) {
                    localServiceInstance.setStatus(2);
                    localServiceOrder.setId_status(4);
                    localTask.setLogin(null);
                    try {
                        localTask.remove();
                    }
                    catch (RemoveException re)
                    {

                    }
                    request.setAttribute("result", "<font color=\"#191970\">Task is completed</font>");
                    request.getRequestDispatcher("CurrentTaskPE.jsp").forward(request, response);
                }
                if(Id_Scenario==3)
                {
                    localServiceInstance.setStatus(3);
                    localServiceOrder.setId_status(4);
                    localTask.setLogin(null);
                    request.setAttribute("result", "<font color=\"#191970\">Task is completed</font>");
                    request.getRequestDispatcher("CurrentTaskPE.jsp").forward(request, response);
                }
            }
        if (action.equals("completeIE"))
        {
          String[] description=request.getParameter("Description").split("Cable id = ");
            String[] devicetest=request.getParameter("Description").split("new router");
            if(devicetest.length>1)
            {
                Connection connection = null;
                PreparedStatement statement = null;
                ResultSet rs;
                try{
                try{
                    connection = DataSource.getDataSource().getConnection();
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                    throw new EJBException("Ошибка dataSource");
                }
                    System.out.println("zdes");
                    Boolean free=false;
                statement = connection.prepareStatement("SELECT d.FREE_PORTS from SERVICE_ORDER so" +
                        "                        INNER  JOIN PRICE p ON so.ID_PRICE=p.ID_PRICE" +
                        "    INNER JOIN Device d on d.ID_PROV_LOCATION=p.ID_PROV_LOCATIOn INNER JOIN Task t \n" +
                        "ON t.ID_ORDER=so.ID_ORDER where t.login=? and so.ID_status!='4'\n" +
                        "      and so.ID_status!='2'");
                statement.setString(1,(String)request.getSession().getAttribute("login"));
                    try{
                rs = statement.executeQuery();
                    System.out.println("zdes1");
                while (rs.next())
                {
                    System.out.println("zdes2");
                    if(rs.getInt(1)>1)
                        free=true;

                }}
                    catch (Exception ex)
                    {

                    }
                    System.out.println("zdes3"+free);
                    if(!free)
                         {
                            request.setAttribute("result", "<font color=\"#ff0000\">Create device please</font>");
                            request.getRequestDispatcher("CurrentTaskIE.jsp").forward(request, response);
                            return;
                        }


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


            if(description.length <= 1) {
                request.setAttribute("result", "<font color=\"#191970\">Create cable please</font>");
                request.getRequestDispatcher("CurrentTaskIE.jsp").forward(request, response);
                return;
            }
            int Cable_id=Integer.parseInt(description[1]);
            String lgn = null;
            String[] lgn1= description[0].split("for");
            String[] lgn2=lgn1[1].split("inst");
            lgn=lgn2[0];
            localTask.setId_group_user(4);
            localTask.setLogin(null);
            localTask.setDescription("Assign port for"+lgn+"instance "+",Cable Id = "+Cable_id);

            request.setAttribute("result", "<font color=\"#191970\">Task is completed</font>");
            request.getRequestDispatcher("CurrentTaskIE.jsp").forward(request, response);
            return;
        }
            if (action.equals("suspendIE")) {
                LocalServiceOrder localServiceOrder=EJBFunctions.findServiceOrder((int)Integer.parseInt(request.getParameter("Id_Order")));
                localServiceOrder.setId_status(2);
                localTask.setLogin(null);
                try{
                    localTask.remove();
                }
                catch (RemoveException e)
                { e.printStackTrace();

                }
                request.setAttribute("result", "<font color=\"#191970\">Task is suspended</font>");
                request.getRequestDispatcher("CurrentTaskIE.jsp").forward(request, response);
            }
        if (action.equals("suspendPE")) {
            LocalServiceOrder localServiceOrder=EJBFunctions.findServiceOrder((int)Integer.parseInt(request.getParameter("Id_Order")));
            localServiceOrder.setId_status(2);
            localTask.setLogin(null);
            try{
                localTask.remove();
            }
            catch (RemoveException e)
            { e.printStackTrace();

            }
            request.setAttribute("result", "<font color=\"#191970\">Task is suspended</font>");
            request.getRequestDispatcher("CurrentTaskPE.jsp").forward(request, response);
        }
        if (action.equals("unassignPE")) {
            localTask.setLogin(null);
            request.setAttribute("result", "<font color=\"#191970\">Task is unassigned</font>");
            request.getRequestDispatcher("CurrentTaskPE.jsp").forward(request, response);
        }

        if (action.equals("unassignIE")) {
            localTask.setLogin(null);
            request.setAttribute("result", "<font color=\"#191970\">Task is unassigned</font>");
            request.getRequestDispatcher("CurrentTaskIE.jsp").forward(request, response);
        }

    }
}
