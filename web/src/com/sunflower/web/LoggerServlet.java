package com.sunflower.web;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;

/**
 * Created by Den on 19.12.2014.
 */
@WebServlet(name = "LoggerServlet")
public class LoggerServlet extends HttpServlet {

    public void init(){

        Loader.getResource("resources/log4j.properties", Logger.class);
        String prefix =  getServletContext().getRealPath("/");
        String file = getInitParameter("log4j");

        if(file != null){
            PropertyConfigurator.configure(prefix + file);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
