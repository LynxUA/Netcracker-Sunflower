package com.sunflower.web;

import com.sunflower.ejb.EJBFunctions;
import com.sunflower.ejb.task.LocalTask;
import com.sunflower.ejb.user.BadPasswordException;
import com.sunflower.ejb.user.LocalUser;

import javax.ejb.FinderException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Andriy on 11/18/2014.
 */

@WebServlet(name = "CheckLoginServlet")
public class CheckLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", "");
        String check = request.getParameter("check");
        if(check != null && !check.isEmpty()){
            response.sendRedirect("www.google.com");
            return;
        }

        String login = request.getParameter("login").toLowerCase();
        String password = request.getParameter("password");
        request.setAttribute("login", login);
        if(login == null || login.isEmpty() || password == null || password.isEmpty()){
            request.setAttribute("error", "Login or password can`t be empty");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        LocalUser user;
        try {
            user = EJBFunctions.login(login, password);
        } catch(FinderException e){
            request.setAttribute("error", "User with this login wasn't found");
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        } catch(BadPasswordException e){
            request.setAttribute("error", "Bad password");
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        } catch (Exception e) {
            //ejb exception
            e.printStackTrace();

            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }
        request.getSession().setAttribute("login", user.getLogin());
        request.getSession().setAttribute("status", user.getGroup());
        response.sendRedirect("/webWeb/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", "");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

}
