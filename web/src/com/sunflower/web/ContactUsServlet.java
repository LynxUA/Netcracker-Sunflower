package com.sunflower.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Andriy on 12/1/2014.
 */
@WebServlet(name = "ContactUsServlet")
public class ContactUsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        if(check == null && !check.equals("")){
            response.sendRedirect("www.google.com");
            return;
        }
        request.setAttribute("name_error", "");
        request.setAttribute("email_error", "");
        request.setAttribute("text_error", "");

        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String text = request.getParameter("text");

        request.setAttribute("email",email);
        request.setAttribute("name",name);
        request.setAttribute("text",text);

        if(name.isEmpty()){
            request.setAttribute("name_error", "Please, enter your name.");
            request.getRequestDispatcher("contuctus.jsp").forward(request, response);
            return;
        }

        if(email.isEmpty()){
            request.setAttribute("email_error", "Please, enter your email.");
            request.getRequestDispatcher("contuctus.jsp").forward(request, response);
            return;
        }

        if(!StaticFunctions.isValidEmail(email)){
            request.setAttribute("email_error", "Wrong email format.");
            request.getRequestDispatcher("contuctus.jsp").forward(request, response);
            return;
        }

        if(text.isEmpty()){
            request.setAttribute("text_error", "Please, enter your massage.");
            request.getRequestDispatcher("contuctus.jsp").forward(request, response);
            return;
        }
        StaticFunctions.sendSupportEmail(name, email, text);
        request.getRequestDispatcher("complete.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("name_error", "");
        request.setAttribute("email_error", "");
        request.setAttribute("text_error", "");
        request.getRequestDispatcher("contuctus.jsp").forward(request, response);
    }
}
