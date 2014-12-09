package com.sunflower.system;

import com.sunflower.ejb.EJBFunctions;
import com.sunflower.ejb.ProviderLocation.LocalProviderLocation;
import com.sunflower.ejb.service.LocalService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Created by denysburlakov on 06.12.14.
 */
@WebServlet(name = "GenerateServicesServlet")
public class GenerateServicesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String name=null;
//        name = "Hello "+request.getParameter("user");
//        if(request.getParameter("user").toString().equals("")){
//            name="Hello User";
//        }

        float longtitude = Float.valueOf((String)(request.getParameter("y")));
        float latitude = Float.valueOf((String)(request.getParameter("x")));

        //System.out.println(EJBFunctions.findProviderLocationById(1).getLocation());
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        LocalProviderLocation prov_location = EJBFunctions.findProviderLocation(longtitude, latitude);
        Collection services = EJBFunctions.findByProviderLocationId(prov_location.getId_Prov_Location());

        PrintWriter writer = response.getWriter();
        writer.println("<form method=\"post\">");
        writer.println("Nearest provider location: Netflower " + prov_location.getLocation() + "<br>");
        for(Object local:services)
        {
            writer.println("<div class=\"radio\">\n" +
                    "            <label>\n" +
                    "            <input type=\"radio\" name=\"services\" value=\""+ ((LocalService)local).getId_service()+"\">\n" +
                    ( (LocalService) local).getName()  +
                    "                </label>\n" +
                    "            </div>\n");

            //writer.println(((LocalService) local).getName() + "<br>");
        }
        writer.println("<input id=\"servicecheck\" type=\"button\" name=\"servicecheck\" value=\"Get price\"></input>");
        writer.println("</form>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
