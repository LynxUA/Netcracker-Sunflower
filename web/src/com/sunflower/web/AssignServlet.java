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

                request.setAttribute("ports", sb.toString());
                request.getRequestDispatcher("assign.jsp").forward(request, response);

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
        }
        if (action.equals("assign")) {
            Integer Id_Circuit=null;
            Integer Id_ServiceInst=null;
            try {
                try {
                    connection = DataSource.getDataSource().getConnection();
                    System.out.println("zxc1");
                } catch (SQLException e) {
                    System.out.println(e.getErrorCode());
                    System.out.println("something wrong with connection");

                }
                //statement = connection.prepareStatement("SELECT ID_TASK FROM TASK WHERE STATUS = ? Order by ID_TASK");
                statement = connection.prepareStatement("SELECT si.Id_CIRCUIT, si.ID_SERVICE_INST from SERVICE_ORDER so\n" +
                        "  INNER  JOIN PRICE p ON so.ID_PRICE=p.ID_PRICE\n" +
                        "  INNER JOIN Device d on d.ID_PROV_LOCATION=p.ID_PROV_LOCATIOn\n" +
                        "  Inner JOIN  Task t On t.Id_order=so.Id_order\n" +
                        "  inner JOIN SERVICE_INSTANCE si\n" +
                        "    On si.ID_SERVICE_INST=so.ID_SERVICE_INST\n" +
                        "where t.login=? and so.ID_status!='4'\n" +
                        "      and so.ID_status!='2'");


                statement.setString(1, (String) request.getSession().getAttribute("login"));
                System.out.println("zxc2");
                try {
                    ResultSet resultSet = statement.executeQuery();
                    if (!resultSet.next()) {
                        request.setAttribute("result", "someting wrong");
                        request.getRequestDispatcher("assign.jsp").forward(request, response);
                        return;
                    }
                    Id_Circuit = resultSet.getInt(1);
                    Id_ServiceInst = resultSet.getInt(2);
                }
                    catch (Exception ex)
                    {

                    }




                if(request.getParameter("Id_Port").equals(""))
                {
                    request.setAttribute("ports", "select port");
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
                LocalServiceInstance serviceInstance = EJBFunctions.findServiceInstance(Id_ServiceInst);
                serviceInstance.setId_circuit(Id_Circuit);

                request.setAttribute("result", "port assigned");
                request.getRequestDispatcher("assign.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new EJBException("SELECT exception assigner");
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
                    request.setAttribute("result", "port not assigned");
                    request.getRequestDispatcher("assign.jsp").forward(request, response);
                }
                int Id_Circuit = resultSet.getInt(1);
                if (Id_Circuit==0) {
                    request.setAttribute("result", "port not assigned");
                    request.getRequestDispatcher("assign.jsp").forward(request, response);
                }
                String[] Description = resultSet.getString(2).split("Cable ID = ");
                Integer Id_Cable = Integer.parseInt(Description[1]);
                System.out.println("cable "+Id_Cable);
                System.out.println("cable "+Id_Circuit);
                localCircuit = EJBFunctions.findLocalCircuitById(Id_Circuit);
                localCircuit.setId_Cable(Id_Cable);
                request.setAttribute("result", "Circuit created");
                request.getRequestDispatcher("assign.jsp").forward(request, response);
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
            Integer Id_Port = Integer.parseInt(request.getParameter("unports"));
            if (Id_Port.equals("")) {
                request.setAttribute("result", "Please write port id");
                request.getRequestDispatcher("assign.jsp").forward(request, response);
            }
            localPort = EJBFunctions.findLocalPortById(Id_Port);
            localPort.setStatus(0);
            request.setAttribute("result", "port is unassigned");
            request.getRequestDispatcher("assign.jsp").forward(request, response);
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
                statement = connection.prepareStatement("SELECT si.ID_CIRCUIT ,si.ID_SERVICE_INST ,c.ID_PORT  from SERVICE_ORDER so INNER  JOIN PRICE p ON so.ID_PRICE=p.ID_PRICE INNER  join PROVIDER_LOCATION pl on p.ID_PROV_LOCATION=pl.ID_PROV_LOCATION Inner JOIN  Task t On t.Id_order=so.Id_order inner JOIN SERVICE_INSTANCE si On si.ID_SERVICE_INST=so.ID_SERVICE_INST INNER JOIN CIRCUIT c ON so.ID_CIRCUIT=c.ID_CIRCUIT where t.login=? and so.ID_status!='4' and so.ID_status!='2'");
                statement.setString(1, (String) request.getSession().getAttribute("login"));
                System.out.println("zxc2");
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    request.setAttribute("result", "port not assigned");
                    request.getRequestDispatcher("assign.jsp").forward(request, response);
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
                request.setAttribute("result", "Circuit removed");
                request.getRequestDispatcher("assign.jsp").forward(request, response);
            }
            catch (RemoveException ex)
            {
                ex.printStackTrace();
                request.setAttribute("result", "Something wrong");
                request.getRequestDispatcher("assign.jsp").forward(request, response);
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
}
