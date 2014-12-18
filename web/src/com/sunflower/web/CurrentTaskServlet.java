package com.sunflower.web;

import javax.ejb.FinderException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.sunflower.ejb.EJBFunctions;
import com.sunflower.ejb.ServiceOrder.LocalServiceOrder;
import com.sunflower.ejb.port.LocalPort;
import com.sunflower.ejb.serviceinstance.LocalServiceInstance;
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
        System.out.println(action);

        int key = Integer.parseInt(request.getParameter("key"));
        System.out.println(key);

        LocalTask localTask= EJBFunctions.findLocalTaskById(key);

        System.out.println(localTask);

            if (action.equals("complete"))
            {
                int Id_Si=Integer.parseInt(request.getParameter("Id_Si"));
                int Id_Order=Integer.parseInt(request.getParameter("Id_Order"));
                int Id_Scenario=Integer.parseInt(request.getParameter("Id_Scenario"));
                //localTask.setStatus("completed");
                LocalServiceInstance localServiceInstance=EJBFunctions.findServiceInstance(Id_Si);
                LocalServiceOrder localServiceOrder=EJBFunctions.findServiceOrder(Id_Order);
                if(Id_Scenario==1) {
                    localServiceInstance.setStatus(2);
                    localServiceOrder.setId_status(4);
                    request.setAttribute("result", "<font color=\"#191970\">Task is completed</font>");
                    request.getRequestDispatcher("CurrentTaskIE.jsp").forward(request, response);
                }
                if(Id_Scenario==3)
                {
                    localServiceInstance.setStatus(3);
                    localServiceOrder.setId_status(4);
                    request.setAttribute("result", "<font color=\"#191970\">Task is completed</font>");
                    request.getRequestDispatcher("CurrentTaskIE.jsp").forward(request, response);
                }
            }
        if (action.equals("completeIE"))
        {
            System.out.println("hello");
            localTask.setId_group_user(4);
            localTask.setLogin(null);
            System.out.println("hey");
            request.setAttribute("result", "<font color=\"#191970\">Task is completed</font>");
            request.getRequestDispatcher("CurrentTaskIE.jsp").forward(request, response);
            return;
        }
            if (action.equals("suspend")) {
                LocalServiceOrder localServiceOrder=EJBFunctions.findServiceOrder(Integer.parseInt(request.getParameter("Id_Order")));
                localServiceOrder.setId_status(2);
                request.setAttribute("result", "<font color=\"#191970\">Task is suspended</font>");
                request.getRequestDispatcher("CurrentTaskIE.jsp").forward(request, response);
            }
        if (action.equals("unassign")) {
            localTask.setLogin(null);
            request.setAttribute("result", "<font color=\"#191970\">Task is unassigned</font>");
            request.getRequestDispatcher("CurrentTaskIE.jsp").forward(request, response);
        }



    }
}
