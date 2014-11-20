package com.sunflower.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Andriy on 11/20/2014.
 */
@WebServlet(name = "ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("login_error", "");
        String login = request.getParameter("login").toLowerCase();
        if(StaticFunctions.isValidEmail(login)){
            if(!StaticFunctions.isEmailExist(login)){
                request.setAttribute("login_error", "Sorry, but there is no user with such email");
                request.getRequestDispatcher("reset.jsp").forward(request,response);
            } else{
                response.sendRedirect("complete");
            }
        } else{
            request.setAttribute("login", login);
            request.setAttribute("login_error", "Wrong email format");
            request.getRequestDispatcher("reset.jsp").forward(request,response);
        }
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("login_error", "");
        request.getRequestDispatcher("reset.jsp").forward(request, response);
    }
}
