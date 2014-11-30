package com.sunflower.web;

import com.sunflower.ejb.HelloWorld;
import com.sunflower.ejb.HelloWorldHome;

import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by denysburlakov on 16.11.14.
 */

public class HelloWorldServlet extends HttpServlet {
    private HelloWorld helloWorldBean;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
            System.out.println(ic.list(""));
        } catch (NamingException e) {
            e.printStackTrace();
        }

        try {
            helloWorldBean = ((HelloWorldHome) ic.lookup("java:global/ear_ear/ejb/HelloWorldEJB!com.sunflower.ejb.HelloWorldHome")).create();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (CreateException e) {
            e.printStackTrace();
        }
        String hello=helloWorldBean.sayHello();
        request.setAttribute("hello",hello);
        request.getRequestDispatcher("hello.jsp").forward(request,response);
    }
}
