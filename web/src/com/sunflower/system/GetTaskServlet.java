package com.sunflower.system;

import com.sunflower.ejb.EJBFunctions;
import com.sunflower.ejb.task.UserHaveAssignedTaskException;
import com.sunflower.ejb.task.UserWasAssignedException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by denysburlakov on 15.12.14.
 */
@WebServlet(name = "GetTaskServlet")
public class GetTaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_task = Integer.valueOf(request.getParameter("id_task"));
        String login = (String)(request.getSession().getAttribute("login"));
        try {
            EJBFunctions.assignTask(id_task, login);
        } catch (UserWasAssignedException e) {
            e.printStackTrace();
        }
        catch (UserHaveAssignedTaskException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("tasks");
    }
}
