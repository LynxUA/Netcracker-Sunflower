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
        int scenario = 1;
        String login =(String)request.getSession().getAttribute("login");
        int price = Integer.valueOf(request.getParameter("prices"));
        float longtitude = Float.valueOf(request.getParameter("y"));
        float latitude = Float.valueOf(request.getParameter("x"));
        //It hasn't preInstance to send, so we send null
        EJBFunctions.createServiceOrder(null, scenario, login, price, longtitude, latitude);
        response.sendRedirect("success?info=ordered");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
