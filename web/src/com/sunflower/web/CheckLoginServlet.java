package com.sunflower.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Andriy on 11/18/2014.
 */
@WebServlet(name = "CheckLoginServlet")
public class CheckLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", "");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if(validateUser(login, password)){
            //code for loggining in
            //request.setAttribute("error", "Hallo, user");
            //request.getRequestDispatcher("login.jsp").forward(request,response);
        }
        else
        {
            request.setAttribute("login", login);
            request.setAttribute("error", "Not valid OMG");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", "");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }


    boolean validateUser(String login, String password) {
        return false;
    }

}
