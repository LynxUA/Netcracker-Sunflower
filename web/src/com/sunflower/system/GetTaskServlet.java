package com.sunflower.system;

import com.sunflower.ejb.EJBFunctions;
import com.sunflower.ejb.task.UserHaveAssignedTaskException;
import com.sunflower.ejb.task.UserWasAssignedException;
import org.apache.log4j.Logger;

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
    private static Logger logger = Logger.getLogger(GetTaskServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_task = Integer.valueOf(request.getParameter("id_task"));
        String login = (String)(request.getSession().getAttribute("login"));
        try {
            EJBFunctions.assignTask(id_task, login);
        } catch (UserWasAssignedException e) {
            logger.error(e.getMessage(), e);
        }
        catch (UserHaveAssignedTaskException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        response.sendRedirect("tasks");
    }
}
