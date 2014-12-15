package com.sunflower.web;

import javax.ejb.FinderException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.sunflower.ejb.EJBFunctions;
import com.sunflower.ejb.task.LocalTask;

/**
 * Created by Алексей on 12/9/2014.
 */
@WebServlet(name = "CurrentTaskServlet")
public class CurrentTaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String action =  request.getParameter("action");

        int key = Integer.parseInt(request.getParameter("key"));
        LocalTask localTask= EJBFunctions.findLocalTaskById(key);

            //if (action == "complete") localTask.setStatus("completed");
            //if (action == "suspend") localTask.setStatus("suspended");





    }
}
