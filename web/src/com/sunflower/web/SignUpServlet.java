package com.sunflower.web;

import com.sunflower.ejb.EJBFunctions;
import com.sunflower.ejb.user.LocalUser;
import com.sunflower.ejb.user.LocalUserHome;
import com.sunflower.ejb.user.UserBean;

import javax.ejb.CreateException;
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
            response.sendRedirect("www.google.com");
            return;
        }
        if(request.getSession().getAttribute("current_user") != null){
            response.sendRedirect("welcome");
        }
        String login = request.getParameter("login");
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

        if(!StaticFunctions.isValidPassword(password))
        {
            request.setAttribute("password_error", "Wrong password format");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        //addNewUser(login, email,name,StaticFunctions.getHashCode(password));
        //addNewUser(login, email,name, password);
        if(EJBFunctions.createUser(login.toLowerCase(), email, name, surname, password)==null){
            request.setAttribute("login_error", "User with this login is already exist");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }else{
            request.getSession().setAttribute("login", login);
            request.getSession().setAttribute("status", 1);
            /** Прибрати*/
            System.out.println(login);
        }
        //MailServer.messageAfterRegistration(name,password,email,login);

        response.sendRedirect("welcome");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        clearError(request);
        request.getRequestDispatcher("signup.jsp").forward(request, response);
    }
    /*private void addNewUser(String login,String email, String name, String password){
        //StaticFunctions.users.put(login,password);
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalUserHome home = null;
        try {
            home = (LocalUserHome) ic.lookup("java:comp/env/ejb/User");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalUser user = null;
        try {
            if (home != null) {
                user = home.create(login, email, name, "Burlakov", password, 1);
            }
        } catch (CreateException e) {
            e.printStackTrace();
        }
        System.out.println("hurray");

        return;
    }*/
    private void clearError(HttpServletRequest request) {
        request.setAttribute("login_error", "");
        request.setAttribute("name_error","");
        request.setAttribute("password_error","");
        request.setAttribute("capcha_error","");
    }
}
