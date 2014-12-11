//deprecated
//package com.sunflower.system;
//
//import com.sunflower.ejb.EJBFunctions;
//import com.sunflower.ejb.ProviderLocation.LocalProviderLocation;
//import com.sunflower.ejb.price.LocalPrice;
//import com.sunflower.ejb.service.LocalService;
//
//import javax.servlet.ServletException;

//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Collection;
//
///**
// * Created by denysburlakov on 09.12.14.
// */
//@WebServlet(name = "GeneratePricesServlet")
//public class GeneratePricesServlet extends HttpServlet {
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        float longtitude = Float.valueOf((String)(request.getParameter("y")));
//        float latitude = Float.valueOf((String)(request.getParameter("x")));
//        int service = Integer.valueOf(request.getParameter("service"));
//
//        //System.out.println(EJBFunctions.findProviderLocationById(1).getLocation());
//        response.setContentType("text/html");
//        response.setCharacterEncoding("UTF-8");
//        LocalProviderLocation prov_location = EJBFunctions.findProviderLocation(longtitude, latitude);
//        LocalPrice price = EJBFunctions.findPrice(service, prov_location.getId_Prov_Location());
//        float distance = EJBFunctions.getDestinationToProvider(longtitude, latitude);
//
//        PrintWriter writer = response.getWriter();
//        writer.println(price.getPrice_of_service()+distance*price.getPrice_of_location());
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
//}
