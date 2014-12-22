package com.sunflower.system;

import com.sunflower.constants.Scenarios;
import com.sunflower.ejb.EJBFunctions;
import com.sunflower.ejb.ServiceOrder.LocalServiceOrder;
import com.sunflower.ejb.task.UserWasAssignedException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by denysburlakov on 21.12.14.
 */
@WebServlet(name = "DisconnectInstanceServlet")
public class DisconnectInstanceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_service_inst = Integer.valueOf(request.getParameter("id_service_inst"));
        String login = (String)(request.getSession().getAttribute("login"));
        LocalServiceOrder order = EJBFunctions.findSOBySI(id_service_inst);
        EJBFunctions.createServiceOrder(id_service_inst, Scenarios.DISCONNECT, login, order.getId_price(), order.getLongtitude(), order.getLatitude());
        response.sendRedirect("user_si");
    }
}
