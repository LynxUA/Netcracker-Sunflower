package com.sunflower.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by denysburlakov on 29.11.14.
 */
@WebServlet(name = "LocationServlet")
public class LocationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("location",request.getParameter("x")+" "+request.getParameter("y"));
        request.getSession().setAttribute("y", Float.valueOf((String)(request.getParameter("y"))));
        response.sendRedirect("/webWeb/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("location.jsp").forward(request, response);
    }
}
