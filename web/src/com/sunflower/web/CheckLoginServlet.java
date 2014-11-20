package com.sunflower.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Andriy on 11/18/2014.
 */
@WebServlet(name = "CheckLoginServlet")
public class CheckLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", "");
        String login = request.getParameter("login").toLowerCase();
        String password = request.getParameter("password");
        if(login == null || login.equals("") || password == null || password.equals("")){
            request.setAttribute("login", login);
            request.setAttribute("error", "Login or password can`t be empty");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        if(!StaticFunctions.isValidEmail(login) || !StaticFunctions.isValidPassword(password)){
            request.setAttribute("error", "Bad login or password format");
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }
        if(validateUser(login, StaticFunctions.getHashCode(password))){
            //code for loggining in
            //request.setAttribute("error", "Hallo, user");
            //request.getRequestDispatcher("login.jsp").forward(request,response);
        }
        else
        {
            request.setAttribute("login", login);
            request.setAttribute("error", "Not valid user");
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
