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
@WebServlet(name = "SignUpServlet")
public class SignUpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        clearError(request);
        request.setAttribute("capcha_error", "");

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

        String capcha = request.getParameter("capcha");
        if(capcha == null || capcha.isEmpty()){
            request.setAttribute("capcha_error", "Enter the result");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        String md5 = request.getParameter("md5");

        if(StaticFunctions.getHashCode(capcha).compareTo(md5) != 0)
        {
            request.setAttribute("capcha_error", "Wrong result");
            request.setAttribute("password_error", StaticFunctions.getHashCode(capcha));
            request.setAttribute("login_error", md5);
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }


        request.getRequestDispatcher("signup.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        clearError(request);
        request.getRequestDispatcher("signup.jsp").forward(request, response);
    }
    private void clearError(HttpServletRequest request) {
        request.setAttribute("login_error", "");
        request.setAttribute("name_error","");
        request.setAttribute("password_error","");
        request.setAttribute("capcha_error","");
    }
}
