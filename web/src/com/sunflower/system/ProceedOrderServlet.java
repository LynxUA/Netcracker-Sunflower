package com.sunflower.system;

import com.sunflower.ejb.EJBFunctions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by denysburlakov on 10.12.14.
 */
@WebServlet(name = "ProceedOrderServlet")
public class ProceedOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int status = 1;
        int scenario = 1;
        String login =(String)request.getSession().getAttribute("login");
        int price = Integer.valueOf(request.getParameter("prices"));

        EJBFunctions.createServiceOrder(status, scenario, login, price);
        response.sendRedirect("/webWeb");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
