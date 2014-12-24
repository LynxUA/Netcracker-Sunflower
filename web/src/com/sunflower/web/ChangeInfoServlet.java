package com.sunflower.web;

import com.sunflower.ejb.EJBFunctions;
import com.sunflower.ejb.user.LocalUser;

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
 * Created by denysburlakov on 19.12.14.
 */
@WebServlet(name = "ChangeInfoServlet")
public class ChangeInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        clearError(request);
        String check = request.getParameter("check");
        if(check == null && !check.equals("")){
            response.sendRedirect("http://google.com");
            return;
        }

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String password = request.getParameter("password");
        String repeat_password = request.getParameter("repeat_password");

        request.setAttribute("name",name);
        request.setAttribute("surname", surname);
        request.setAttribute("password",password);
        request.setAttribute("repeat_password",repeat_password);

        if(name == null || name.equals("")) {
            request.setAttribute("name_error", "Type your name");
            request.getRequestDispatcher("changeInfo.jsp").forward(request, response);
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
            request.getRequestDispatcher("changeInfo.jsp").forward(request, response);
            return;
        }

        if(password.compareTo(repeat_password) != 0) {
            request.setAttribute("password_error", "Passwords should be equal");
            request.getRequestDispatcher("changeInfo.jsp").forward(request, response);
            return;
        }

        if(!StaticFunctions.isValidPassword(password))
        {
            request.setAttribute("password_error", "Wrong password format");
            request.getRequestDispatcher("changeInfo.jsp").forward(request, response);
            return;
        }

        LocalUser user = null;
        try {
            user = EJBFunctions.findUser((String)(request.getSession().getAttribute("login")));
        } catch (FinderException e) {
            response.sendRedirect("error");
            return;
        }
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(password);
        //MailServer.messageAfterRegistration(name,password,email,login);
        response.sendRedirect("success?info=info_changed");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        clearError(request);
        request.getRequestDispatcher("changeInfo.jsp").forward(request, response);
    }

    private void clearError(HttpServletRequest request) {
        request.setAttribute("login_error", "");
        request.setAttribute("name_error","");
        request.setAttribute("surname_error","");
        request.setAttribute("password_error","");
        request.setAttribute("capcha_error", "");
    }
}
