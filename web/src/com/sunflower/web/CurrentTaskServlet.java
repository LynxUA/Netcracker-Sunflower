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

        int key = Integer.parseInt(request.getParameter("key"));

        LocalTask localTask= EJBFunctions.findLocalTaskById(key);


            if (action == "complete")
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
                }
                if(Id_Scenario==3)
                {
                    localServiceInstance.setStatus(3);
                    localServiceOrder.setId_status(4);
                }
            }
        if (action == "completeIE")
        {
            localTask.setId_group_user(4);
            localTask.setLogin(null);
        }
            if (action == "suspend") {
                LocalServiceOrder localServiceOrder=EJBFunctions.findServiceOrder(Integer.parseInt(request.getParameter("Id_Order")));
                localServiceOrder.setId_status(2);
            }
        if (action == "unassign") localTask.setLogin(null);




    }
}
