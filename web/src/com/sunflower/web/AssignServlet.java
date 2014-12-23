package com.sunflower.web;

import javax.ejb.EJBException;

import javax.ejb.RemoveException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.sunflower.ejb.DataSource;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sunflower.ejb.EJBFunctions;
import com.sunflower.ejb.port.LocalPort;
import com.sunflower.ejb.circuit.LocalCircuit;
import com.sunflower.ejb.serviceinstance.LocalServiceInstance;
import org.apache.log4j.Logger;
/**
 * Created by Алексей on 12/14/2014.
 */
@WebServlet(name = "AssignServlet")
public class AssignServlet extends HttpServlet {
    private ServletContext context;
    private static Logger logger = Logger.getLogger(AssignServlet.class);
    @Override
      public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      /*  String action = request.getParameter("action");
        Connection connection = null;
        System.out.println("cxz1123");
        PreparedStatement statement;
        if (action.equals("getports")) {
            StringBuffer sb = new StringBuffer();
            System.out.println("cxz1");
            int id_device = Integer.parseInt(request.getParameter("id_router"));
            System.out.println("cxz2");
            try {
                try {
                    System.out.println("cxz3");
                    connection = DataSource.getDataSource().getConnection();
                    System.out.println("zxc1");
                } catch (SQLException e) {
                    System.out.println(e.getErrorCode());
                    System.out.println("something wrong with connection");

                }
                System.out.println("cxz4");
                //statement = connection.prepareStatement("SELECT ID_TASK FROM TASK WHERE STATUS = ? Order by ID_TASK");
                statement = connection.prepareStatement("SELECT ID_PORT from PORT  where ID_DEVICE=? AND  STATUS='0'");
                statement.setInt(1, id_device);
                System.out.println("zxc2");
                ResultSet resultSet = statement.executeQuery();


                while (resultSet.next()) {
                    sb.append("<option value=\"" + resultSet.getInt(1) +"\">" + resultSet.getInt(1) + "</option>");

                }
                response.setContentType("text/html");
                response.getWriter().write(sb.toString());
                //request.setAttribute("ports", sb.toString());
                // request.getRequestDispatcher("assign.jsp").forward(request, response);

            } catch (SQLException e) {
                throw new EJBException("SELECT exception in ejbFindIncomplete");
            } finally {
                try {
                    System.out.println("cxz5");
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Connection connection = null;
        System.out.println("cxz1123");
        PreparedStatement statement;
        LocalCircuit localCircuit = null;
        LocalPort localPort = null;
        if (action.equals("getports")) {
            StringBuffer sb = new StringBuffer();
            System.out.println("cxz1");
            System.out.println(request.getParameter("id_router"));
            int id_device = Integer.parseInt(request.getParameter("id_router"));
            System.out.println("cxz2");
            try {
                try {
                    System.out.println("cxz3");
                    connection = DataSource.getDataSource().getConnection();
                    System.out.println("zxc1");
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);

                }
                System.out.println("cxz4");
                //statement = connection.prepareStatement("SELECT ID_TASK FROM TASK WHERE STATUS = ? Order by ID_TASK");
                statement = connection.prepareStatement("SELECT ID_PORT from PORT  where ID_DEVICE=? AND  STATUS='0'");
                statement.setInt(1, id_device);
                System.out.println("zxc2");
                ResultSet resultSet = statement.executeQuery();


                while (resultSet.next()) {

                    sb.append("<option value=\"" + resultSet.getInt(1) +"\">" + resultSet.getInt(1) + "</option>");

                }
                response.setContentType("text/html");
                response.getWriter().write(sb.toString());
                //request.setAttribute("ports", sb.toString());
              //  request.getRequestDispatcher("assign.jsp").forward(request, response);

            } catch (SQLException e) {
                System.out.println("exc");
                throw new EJBException("SELECT exception in ejbFindIncomplete");
            } finally {
                try {
                    System.out.println("cxz5");
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        if (action.equals("assign")) {
            Integer Id_Circuit=0;
            Integer Id_ServiceInst=0;
            try {
                try {
                    connection = DataSource.getDataSource().getConnection();
                    System.out.println("zxc1");
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);

                }
                //statement = connection.prepareStatement("SELECT ID_TASK FROM TASK WHERE STATUS = ? Order by ID_TASK");
                statement = connection.prepareStatement("SELECT si.Id_CIRCUIT, si.ID_SERVICE_INST,so.ID_Scenario,p.Status from SERVICE_ORDER so\n" +
                        "  INNER  JOIN PRICE p ON so.ID_PRICE=p.ID_PRICE\n" +
                        "  INNER JOIN Device d on d.ID_PROV_LOCATION=p.ID_PROV_LOCATIOn\n" +
                        "  Inner JOIN  Task t On t.Id_order=so.Id_order\n" +
                        "  inner JOIN SERVICE_INSTANCE si\n" +
                        "LEFT  JOIN  CIRCUIT c on c.ID_CIRCUIT=si.ID_CIRCUIT" +
                        " Left join Port p on p.ID_PORT=c.ID_PORT" +
                        "    On si.ID_SERVICE_INST=so.ID_SERVICE_INST\n" +
                        "where t.login=? and so.ID_status!='4'\n" +
                        "      and so.ID_status!='2'");


                statement.setString(1, (String) request.getSession().getAttribute("login"));
                System.out.println("zxc2");
                try {
                    ResultSet resultSet = statement.executeQuery();
                    if (!resultSet.next()) {
                        request.setAttribute("result", "<font color=\"#ff0000\">something wrong<font>");
                        request.getRequestDispatcher("assign.jsp").forward(request, response);
                        return;
                    }
                    int Id_Scenario=resultSet.getInt(3);
                    if((Id_Scenario==1)&(resultSet.getInt(4)!=0))
                    {
                        request.setAttribute("result", "<font color=\"#ff0000\">port is already assigned<font>");
                        request.getRequestDispatcher("assign.jsp").forward(request, response);
                        return;
                    }
                    Id_Circuit = resultSet.getInt(1);
                    Id_ServiceInst = resultSet.getInt(2);
                }
                    catch (Exception ex)
                    {

                    }

                if(request.getParameter("Id_Port").equals("Select Port"))
                {
                    System.out.println("portitorti");
                    request.setAttribute("result", "<font color=\"#ff0000\">Select port<font>");
                    request.getRequestDispatcher("assign.jsp").forward(request, response);
                    return;
                }
                int Id_Port = Integer.parseInt(request.getParameter("Id_Port"));
                localPort = EJBFunctions.findLocalPortById(Id_Port);
                localPort.setStatus(1);
                System.out.println(Id_Circuit);
                if (Id_Circuit != 0) {
                    localCircuit = EJBFunctions.findLocalCircuitById(Id_Circuit);
                    localCircuit.setId_Port(Id_Port);
                } else {
                    localCircuit = EJBFunctions.createCircuit(Id_Port);
                    Id_Circuit = localCircuit.getId_Circuit();
                }
                System.out.println("idserv "+Id_ServiceInst);
                LocalServiceInstance serviceInstance = EJBFunctions.findServiceInstance(Id_ServiceInst);
                serviceInstance.setId_circuit(Id_Circuit);

                request.setAttribute("result", "<font color=\"#191970\">port assigned<font>");
                request.getRequestDispatcher("assign.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new EJBException("SELECT exception assigner");
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
        if (action.equals("createCir")) {

            try {
                try {
                    connection = DataSource.getDataSource().getConnection();
                    System.out.println("zxc1");
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);

                }
                //statement = connection.prepareStatement("SELECT ID_TASK FROM TASK WHERE STATUS = ? Order by ID_TASK");
                statement = connection.prepareStatement("SELECT si.ID_CIRCUIT ,t.DESCRIPTION  " +
                        "from SERVICE_ORDER so INNER  JOIN PRICE p" +
                        " ON so.ID_PRICE=p.ID_PRICE INNER  join PROVIDER_LOCATION pl" +
                        " on p.ID_PROV_LOCATION=pl.ID_PROV_LOCATION Inner JOIN  Task t " +
                        "On t.Id_order=so.Id_order inner JOIN SERVICE_INSTANCE si" +
                        " On si.ID_SERVICE_INST=so.ID_SERVICE_INST where t.login=? " +
                        "and so.ID_status!='4' and so.ID_status!='2'");
                statement.setString(1, (String) request.getSession().getAttribute("login"));
                System.out.println("zxc2");
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    request.setAttribute("result", "<font color=\"#ff0000\">port not assigned<font>");
                    request.getRequestDispatcher("assign.jsp").forward(request, response);
                }
                int Id_Circuit = resultSet.getInt(1);
                if (Id_Circuit==0) {
                    request.setAttribute("result", "<font color=\"#ff0000\">port not assigned<font>");
                    request.getRequestDispatcher("assign.jsp").forward(request, response);
                }
                System.out.println(resultSet.getString(2));
                String[] Description = resultSet.getString(2).split("Cable Id = ");
                Integer Id_Cable = Integer.parseInt(Description[1]);
                System.out.println("cable "+Id_Cable);
                System.out.println("cable "+Id_Circuit);
                localCircuit = EJBFunctions.findLocalCircuitById(Id_Circuit);
                localCircuit.setId_Cable(Id_Cable);
                request.setAttribute("result", "<font color=\"#191970\">Circuit created<font>");
                request.getRequestDispatcher("assign.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new EJBException("SELECT exception in createCir");
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
        if (action.equals("unassign")) {

            if (request.getParameter("unports").equals("")) {
                request.setAttribute("result", "<font color=\"#ff0000\">Please write port id<font>");
                request.getRequestDispatcher("assign.jsp").forward(request, response);
            }
            Integer Id_Port = Integer.parseInt(request.getParameter("unports"));
            try {
                try {
                    connection = DataSource.getDataSource().getConnection();
                    System.out.println("zxc1");
                } catch (SQLException e) {
                    System.out.println(e.getErrorCode());
                    System.out.println("something wrong with connection");

                }
                statement = connection.prepareStatement("Select ID_Port from Port WHERE Id_Port=?");
                statement.setInt(1,Id_Port);

                ResultSet resultSet= statement.executeQuery();
                if(!resultSet.next())
                {
                    request.setAttribute("result", "<font color=\"#ff0000\">Wrong port number<font>");
                    request.getRequestDispatcher("assign.jsp").forward(request, response);
                    return;
                }
                localPort = EJBFunctions.findLocalPortById(Id_Port);
                localPort.setStatus(0);
                request.setAttribute("result", "<font color=\"#191970\">port is unassigned<font>");
                request.getRequestDispatcher("assign.jsp").forward(request, response);
            }
            catch (SQLException ex)
            {
                request.setAttribute("result", "<font color=\"#ff0000\">Something wrong<font>");
                request.getRequestDispatcher("assign.jsp").forward(request, response);
            }
            finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        if (action.equals("remove")) {

            try {
                try {
                    connection = DataSource.getDataSource().getConnection();
                    System.out.println("zxc1");
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);

                }
                //statement = connection.prepareStatement("SELECT ID_TASK FROM TASK WHERE STATUS = ? Order by ID_TASK");
                statement = connection.prepareStatement("SELECT si.ID_CIRCUIT ,si.ID_SERVICE_INST ,c.ID_PORT  from SERVICE_ORDER so INNER  JOIN PRICE p ON so.ID_PRICE=p.ID_PRICE INNER  join PROVIDER_LOCATION pl on p.ID_PROV_LOCATION=pl.ID_PROV_LOCATION Inner JOIN  Task t On t.Id_order=so.Id_order inner JOIN SERVICE_INSTANCE si On si.ID_SERVICE_INST=so.ID_SERVICE_INST INNER JOIN CIRCUIT c ON si.ID_CIRCUIT=c.ID_CIRCUIT where t.login=? and so.ID_status!='4' and so.ID_status!='2'");
                statement.setString(1, (String) request.getSession().getAttribute("login"));
                System.out.println("zxc2");
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    request.setAttribute("result", "<font color=\"#ff0000\">port not assigned<font>");
                    request.getRequestDispatcher("assign.jsp").forward(request, response);
                    return;
                }
                int Id_Circuit = resultSet.getInt(1);
                int Id_Si=resultSet.getInt(2);
                int Id_Port=resultSet.getInt(3);
                localCircuit = EJBFunctions.findLocalCircuitById(Id_Circuit);
                LocalServiceInstance localServiceInstance=EJBFunctions.findServiceInstance(Id_Si);
                localPort=EJBFunctions.findLocalPortById(Id_Port);
                localServiceInstance.setId_circuit(null);

                System.out.println(localCircuit.getId_Circuit());
                localCircuit.remove();

                localPort.setStatus(0);
                request.setAttribute("result", "<font color=\"#191970\">Circuit removed<font>");
                request.getRequestDispatcher("assign.jsp").forward(request, response);
            }
            catch (RemoveException ex)
            {
                ex.printStackTrace();
                request.setAttribute("result", "<font color=\"#ff0000\">Something wrong<font>");
                request.getRequestDispatcher("assign.jsp").forward(request, response);
                return;
            }
            catch (SQLException e) {
                throw new EJBException("SELECT exception in createCir");
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
