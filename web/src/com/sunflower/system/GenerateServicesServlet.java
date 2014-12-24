package com.sunflower.system;

import com.sunflower.ejb.EJBFunctions;
import com.sunflower.ejb.ProviderLocation.LocalProviderLocation;
import com.sunflower.ejb.price.LocalPrice;
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
        Collection services = EJBFunctions.findServiceByProviderLocationId(prov_location.getId_Prov_Location());
        request.setAttribute("lng", prov_location.getLongtitude());
        request.setAttribute("lat", prov_location.getLatitude());
        PrintWriter writer = response.getWriter();
        writer.println("<form action=\"proceedorder\" method=\"post\">");
        writer.println("Nearest provider location: " + prov_location.getLocation() + "<br>");
        for(Object local:services)
        {
            LocalService service = (LocalService)local;
            LocalPrice price = EJBFunctions.findPrice(service.getId_service(), prov_location.getId_Prov_Location());
            float distance = EJBFunctions.getDestinationToProvider(longtitude, latitude);

            //writer.println(price.getPrice_of_service()+distance*price.getPrice_of_location());
            writer.println("<div class=\"radio\">\n" +
                    "            <label>\n" +
                    "            <input type=\"radio\" name=\"prices\" value=\""+ price.getId_price()+"\">\n" +
                    ( (LocalService) local).getName() +" for "+ ((double)Math.round((price.getPrice_of_service()+distance*price.getPrice_of_location()) * 100) / 100)+
                    "                </label>\n" +
                    "            </div>\n");

            //writer.println(((LocalService) local).getName() + "<br>");
        }
       // writer.println("<form action=\"proceedorder?price="++"\" method = \"post\"></form>");
        writer.println("<input type=\"submit\" class=\"btn btn-success btn-block\" value=\"Confirm\">");
        //writer.println("<input type=\"button\" id=\"servicecheck\" class=\"btn btn-success btn-block\" value=\"Save\" style=\"margin-top: 20px\">");
        writer.println("</form>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
