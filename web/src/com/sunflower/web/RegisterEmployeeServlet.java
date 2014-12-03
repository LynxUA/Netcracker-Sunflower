package com.sunflower.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by denysburlakov on 30.11.14.
 */
@WebServlet(name = "RegisterEmployeeServlet")
public class RegisterEmployeeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("firstName_error","");
        request.setAttribute("lastName_error","");
        request.setAttribute("login_error", "");
        request.setAttribute("password_error","");
        String check = request.getParameter("check");
        if(check != null && !check.isEmpty()){
            response.sendRedirect("www.google.com");
            return;
        }

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String login = request.getParameter("login");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");


        request.setAttribute("firstName",firstName);
        request.setAttribute("lastName",lastName);
        request.setAttribute("login",login);
        request.setAttribute("password1",password1);
        request.setAttribute("password2",password2);


        if(firstName.isEmpty()) {
            request.setAttribute("firstName_error", "Please, enter the first name");
            request.getRequestDispatcher("register_employee.jsp").forward(request, response);
            return;
        }

        if(lastName.isEmpty()) {
            request.setAttribute("lastName_error", "Please, enter the last name");
            request.getRequestDispatcher("register_employee.jsp").forward(request, response);
            return;
        }

        if(login.isEmpty()){
            request.setAttribute("login_error", "Please, enter the email");
            request.getRequestDispatcher("register_employee.jsp").forward(request, response);
            return;
        }

        if(!StaticFunctions.isValidEmail(login)){
            request.setAttribute("login_error", "Wrong email format");
            request.getRequestDispatcher("register_employee.jsp").forward(request, response);
            return;
        }

        if(StaticFunctions.isEmailExist(login)){
            request.setAttribute("login_error", "Such email is already registered");
            request.getRequestDispatcher("register_employee.jsp").forward(request, response);
            return;
        }

        if(password1.isEmpty() || password2.isEmpty()){
            request.setAttribute("password_error", "Please, enter the passwords");
            request.getRequestDispatcher("register_employee.jsp").forward(request, response);
        }

        if(password1.compareTo(password2) != 0) {
            request.setAttribute("password_error", "Passwords should be equal");
            request.getRequestDispatcher("register_employee.jsp").forward(request, response);
            return;
        }

        if(!StaticFunctions.isValidPassword(password1))
        {
            request.setAttribute("password_error", "Wrong password format");
            request.getRequestDispatcher("register_employee.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("registered.jsp");



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("firstName_error","");
        request.setAttribute("lastName_error","");
        request.setAttribute("login_error", "");
        request.setAttribute("password_error","");
        request.getRequestDispatcher("register_employee.jsp").forward(request, response);
    }
}
