package com.sunflower.system;

import com.sunflower.ejb.EJBFunctions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by denysburlakov on 06.12.14.
 */
@WebServlet(name = "GeneratePricesServlet")
public class GeneratePricesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String name=null;
//        name = "Hello "+request.getParameter("user");
//        if(request.getParameter("user").toString().equals("")){
//            name="Hello User";
//        }

        float longtitude = Float.valueOf((String)(request.getParameter("y")));
        float latitude = Float.valueOf((String)(request.getParameter("x")));

        //System.out.println(EJBFunctions.findProviderLocationById(1).getLocation());
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("Nearest provider location: Netflower " + EJBFunctions.findProviderLocation(longtitude,latitude).getLocation());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
