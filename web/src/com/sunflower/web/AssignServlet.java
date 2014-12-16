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
/**
 * Created by Алексей on 12/14/2014.
 */
@WebServlet(name = "AssignServlet")
public class AssignServlet extends HttpServlet {
    private ServletContext context;
    @Override
      public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        Connection connection = null;
        PreparedStatement statement;
        LocalCircuit localCircuit = null;
        LocalPort localPort = null;
        if (action.equals("getports")) {
            StringBuffer sb = new StringBuffer();
            int id_device = Integer.parseInt(request.getParameter("router"));

            try {
                try {
                    connection = DataSource.getDataSource().getConnection();
                    System.out.println("zxc1");
                } catch (SQLException e) {
                    System.out.println(e.getErrorCode());
                    System.out.println("something wrong with connection");

                }
                //statement = connection.prepareStatement("SELECT ID_TASK FROM TASK WHERE STATUS = ? Order by ID_TASK");
                statement = connection.prepareStatement("SELECT ID_PORT from PORT  where ID_DEVICE=? AND  STATUS='0'");
                statement.setInt(1, id_device);
                System.out.println("zxc2");
                ResultSet resultSet = statement.executeQuery();

                sb.append("<option selected=\"true\" style=\"display:none;\" >Select Port</option>");
                while (resultSet.next()) {
                    sb.append("<option value=\"" + resultSet.getInt(1) + " " + "<%=Id_Circuit>" + " " + "<%=Id_Si>" + "\">" + resultSet.getInt(1) + "</option>");

                }
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(sb.toString());

            } catch (SQLException e) {
                throw new EJBException("SELECT exception in ejbFindIncomplete");
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
        if (action.equals("assign")) {
            StringBuffer sb = new StringBuffer();
            String[] parameters = request.getParameter("parameters").split(" ");
            int Id_Port = Integer.parseInt(parameters[0]);
            Integer Id_Circuit = Integer.parseInt(parameters[1]);
            Integer Id_ServiceInst = Integer.parseInt(parameters[2]);
            localPort = EJBFunctions.findLocalPortById(Id_Port);
            localPort.setStatus(1);

            if (Id_Circuit != null) {
                localCircuit = EJBFunctions.findLocalCircuitById(Id_Circuit);
                localCircuit.setId_Port(Id_Port);
            } else {
                localCircuit = EJBFunctions.createCircuit(Id_Port, 0);
                Id_Circuit = localCircuit.getId_Circuit();
            }
            LocalServiceInstance serviceInstance = EJBFunctions.findServiceInstance(Id_ServiceInst);
            serviceInstance.setId_circuit(Id_Circuit);

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<font color=\"#191970\">Port assigned</font>");
        }
        if (action.equals("createCir")) {

            try {
                try {
                    connection = DataSource.getDataSource().getConnection();
                    System.out.println("zxc1");
                } catch (SQLException e) {
                    System.out.println(e.getErrorCode());
                    System.out.println("something wrong with connection");

                }
                //statement = connection.prepareStatement("SELECT ID_TASK FROM TASK WHERE STATUS = ? Order by ID_TASK");
                statement = connection.prepareStatement("SELECT si.ID_CIRCUIT ,t.DESCRIPTION  from SERVICE_ORDER so INNER  JOIN PRICE p ON so.ID_PRICE=p.ID_PRICE INNER  join PROVIDER_LOCATION pl on p.ID_PROV_LOCATION=pl.ID_PROV_LOCATION Inner JOIN  Task t On t.Id_order=so.Id_order inner JOIN SERVICE_INSTANCE si On si.ID_SERVICE_INST=so.ID_SERVICE_INST where t.login=? and so.ID_status!='4'");
                statement.setString(1, (String) request.getSession().getAttribute("login"));
                System.out.println("zxc2");
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().write("<font color=\"#ff0000\">Port not assigned</font>");
                }
                int Id_Circuit = resultSet.getInt(1);
                String[] Description = resultSet.getString(2).split("Cable ID = ");
                int Id_Cable = Integer.parseInt(Description[1]);
                localCircuit = EJBFunctions.findLocalCircuitById(Id_Circuit);
                localCircuit.setId_Cable(Id_Cable);
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("<font color=\"#ff0000\">Circuit created</font>");
            } catch (SQLException e) {
                throw new EJBException("SELECT exception in createCir");
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
        if (action.equals("unassign")) {
            Integer Id_Port = Integer.parseInt(request.getParameter("parameter"));
            if (Id_Port.equals("")) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("<font color=\"#ff0000\">Write Port Id</font>");
            }
            localPort = EJBFunctions.findLocalPortById(Id_Port);
            localPort.setStatus(0);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<font color=\"#ff0000\">Port Is Unnasigned</font>");
        }
        if (action.equals("remove")) {

            try {
                try {
                    connection = DataSource.getDataSource().getConnection();
                    System.out.println("zxc1");
                } catch (SQLException e) {
                    System.out.println(e.getErrorCode());
                    System.out.println("something wrong with connection");

                }
                //statement = connection.prepareStatement("SELECT ID_TASK FROM TASK WHERE STATUS = ? Order by ID_TASK");
                statement = connection.prepareStatement("SELECT si.ID_CIRCUIT ,si.ID_SERVICE_INST ,c.ID_PORT  from SERVICE_ORDER so INNER  JOIN PRICE p ON so.ID_PRICE=p.ID_PRICE INNER  join PROVIDER_LOCATION pl on p.ID_PROV_LOCATION=pl.ID_PROV_LOCATION Inner JOIN  Task t On t.Id_order=so.Id_order inner JOIN SERVICE_INSTANCE si On si.ID_SERVICE_INST=so.ID_SERVICE_INST INNER JOIN CIRCUIT c ON so.ID_CIRCUIT=c.ID_CIRCUIT where t.login=? and so.ID_status!='4'");
                statement.setString(1, (String) request.getSession().getAttribute("login"));
                System.out.println("zxc2");
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().write("<font color=\"#ff0000\">Port not assigned</font>");
                }
                int Id_Circuit = resultSet.getInt(1);
                int Id_Si=resultSet.getInt(2);
                int Id_Port=resultSet.getInt(3);
                localCircuit = EJBFunctions.findLocalCircuitById(Id_Circuit);
                LocalServiceInstance localServiceInstance=EJBFunctions.findServiceInstance(Id_Si);
                localPort=EJBFunctions.findLocalPortById(Id_Port);
                localServiceInstance.setId_circuit(null);
                localCircuit.remove();
                localPort.setStatus(0);
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("<font color=\"#ff0000\">Circuit removed</font>");
            }
            catch (RemoveException ex)
            {
                ex.printStackTrace();
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("<font color=\"#ff0000\">Something wrong</font>");
            }
            catch (SQLException e) {
                throw new EJBException("SELECT exception in createCir");
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
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
