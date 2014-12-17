package com.sunflower.system;

import com.sunflower.ejb.EJBFunctions;
import com.sunflower.ejb.task.UserWasAssignedException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by denysburlakov on 17.12.14.
 */
@WebServlet(name = "CancelOrderServlet")
public class CancelOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_order = Integer.valueOf(request.getParameter("id_order"));
        String login = (String)(request.getSession().getAttribute("login"));

        try {
            EJBFunctions.cancelOrder(login, id_order);
        } catch (UserWasAssignedException e) {
            response.sendRedirect("orders");
        }
        response.sendRedirect("orders");
    }
}
