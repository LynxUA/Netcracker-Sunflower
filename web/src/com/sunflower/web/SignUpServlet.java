package com.sunflower.web;

import javafx.util.Pair;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Andriy on 11/20/2014.
 */
@WebServlet(name = "SignUpServlet")
public class SignUpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        clearError(request);
        String check = request.getParameter("check");
        if(check != null && !check.equals("")){
            response.sendRedirect("www.google.com");
            return;
        }

        String login = request.getParameter("login");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String repeat_password = request.getParameter("repeat_password");

        request.setAttribute("login",login);
        request.setAttribute("name",name);
        request.setAttribute("password",password);
        request.setAttribute("repeat_password",repeat_password);

        if(!StaticFunctions.isValidEmail(login)){
            request.setAttribute("login_error", "Wrong email format");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        if(StaticFunctions.isEmailExist(login)){
            request.setAttribute("login_error", "Such email is already registered");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        if(name == null || name.equals("")) {
            request.setAttribute("name_error", "Type your name");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        if(password == null || repeat_password == null ||
                (password.compareTo("") == 0) || (repeat_password.compareTo("") == 0)) {
            request.setAttribute("password_error", "Fill the passwords");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        if(password.compareTo(repeat_password) != 0) {
            request.setAttribute("password_error", "Passwords should be equal");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        if(!StaticFunctions.isValidPassword(password))
        {
            request.setAttribute("password_error", "Wrong password format");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        addNewUser(login,name,StaticFunctions.getHashCode(password));
        response.sendRedirect("welcome");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        clearError(request);
        request.getRequestDispatcher("signup.jsp").forward(request, response);
    }
    private void addNewUser(String login, String name, String password){
        StaticFunctions.users.put(login,password);
        return;
    }
    private void clearError(HttpServletRequest request) {
        request.setAttribute("login_error", "");
        request.setAttribute("name_error","");
        request.setAttribute("password_error","");
        request.setAttribute("capcha_error","");
    }
}
