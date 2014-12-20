package com.sunflower.web;

import com.sunflower.ejb.EJBFunctions;
import com.sunflower.ejb.user.LocalUser;
import com.sunflower.ejb.user.NoSuchUserException;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.FinderException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by denysburlakov on 20.12.14.
 */
@WebServlet(name = "ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        clearError(request);
        String check = request.getParameter("check");
        if(check == null && !check.equals("")){
            response.sendRedirect("http://google.com");
            return;
        }
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String repeat_password = request.getParameter("repeat_password");

        request.setAttribute("password",password);
        request.setAttribute("repeat_password",repeat_password);

         if(password == null || repeat_password == null ||
                (password.compareTo("") == 0) || (repeat_password.compareTo("") == 0)) {
            request.setAttribute("password_error", "Type the passwords");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        if(password.compareTo(repeat_password) != 0) {
            request.setAttribute("password_error", "Passwords should be equal");
            //response.sendRedirect("change_password?login="+login);
            request.getRequestDispatcher("changePassword.jsp?login="+login).forward(request, response);
            return;
        }

        if(!StaticFunctions.isValidPassword(password))
        {
            request.setAttribute("password_error", "Wrong password format");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        try {
            LocalUser user = EJBFunctions.findUser(login);
            user.setPassword(password);
        } catch (FinderException e) {
            response.sendRedirect("access_denied");
        }

//        try {
//            EJBFunctions.setPassword(login, password);
//        } catch (NoSuchUserException e) {
//            response.sendRedirect("access_denied");
//        }


        //MailServer.messageAfterRegistration(name,password,email,login);
        response.sendRedirect("success?info=password_changed");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        clearError(request);
        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
    }

    private void clearError(HttpServletRequest request) {
        request.setAttribute("password_error","");
        request.setAttribute("capcha_error", "");
    }
}
