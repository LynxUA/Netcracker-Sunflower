package com.sunflower.web;

import com.sunflower.ejb.EJBFunctions;
import com.sunflower.ejb.user.LocalUser;
import com.sunflower.ejb.user.LocalUserHome;
import com.sunflower.ejb.user.UserBean;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.FinderException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
        if(check == null && !check.equals("")){
            response.sendRedirect("http://www.google.com");
            return;
        }
        if(request.getSession().getAttribute("current_user") != null){
            response.sendRedirect("welcome");
        }
        String login = request.getParameter("login").toLowerCase();
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String password = request.getParameter("password");
        String repeat_password = request.getParameter("repeat_password");

        request.setAttribute("login", login);
        request.setAttribute("email",email);
        request.setAttribute("name",name);
        request.setAttribute("surname", surname);
        request.setAttribute("password",password);
        request.setAttribute("repeat_password",repeat_password);

        if(login.isEmpty()){
            request.setAttribute("login_error", "Login can`t be empty");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }
        if(email.isEmpty()){
            request.setAttribute("email_error", "Email can`t be empty");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        if(!StaticFunctions.isValidEmail(email)){
            request.setAttribute("email_error", "Wrong email format");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        if(StaticFunctions.isEmailExist(email)){
            request.setAttribute("email_error", "Such email is already registered");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        if(name == null || name.equals("")) {
            request.setAttribute("name_error", "Type your name");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }
        if(surname == null || surname.equals("")) {
            request.setAttribute("surname_error", "Type your surname");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        if(password == null || repeat_password == null ||
                (password.compareTo("") == 0) || (repeat_password.compareTo("") == 0)) {
            request.setAttribute("password_error", "Type the passwords");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        if(password.compareTo(repeat_password) != 0) {
            request.setAttribute("password_error", "Passwords should be equal");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }



        try {
            LocalUser user = EJBFunctions.createUser(login, email, name, surname, password, 1);
        }catch(DuplicateKeyException e) {
            request.setAttribute("login_error", "User with this login is already exist");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        } catch (CreateException e1) {
            request.setAttribute("login_error", "Server error");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
        request.getSession().setAttribute("login", login);
        request.getSession().setAttribute("status", 1);

        MailServer.messageAfterRegistration(name,password,email,login);
        response.sendRedirect("success?info=user_registered");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        clearError(request);
        request.getRequestDispatcher("signup.jsp").forward(request, response);
    }

    private void clearError(HttpServletRequest request) {
        request.setAttribute("login_error", "");
        request.setAttribute("name_error","");
        request.setAttribute("surname_error","");
        request.setAttribute("password_error","");
        request.setAttribute("capcha_error","");
    }
}
