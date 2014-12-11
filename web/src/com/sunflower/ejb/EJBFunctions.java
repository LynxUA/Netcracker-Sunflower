package com.sunflower.ejb;

import com.sunflower.ejb.ProviderLocation.LocalProviderLocation;
import com.sunflower.ejb.ProviderLocation.LocalProviderLocationHome;
import com.sunflower.ejb.ServiceOrder.LocalServiceOrder;
import com.sunflower.ejb.ServiceOrder.LocalServiceOrderHome;
import com.sunflower.ejb.price.LocalPrice;
import com.sunflower.ejb.price.LocalPriceHome;
import com.sunflower.ejb.service.LocalServiceHome;
import com.sunflower.ejb.task.LocalTask;
import com.sunflower.ejb.task.LocalTaskHome;
import com.sunflower.ejb.user.BadPasswordException;
import com.sunflower.ejb.user.LocalUser;
import com.sunflower.ejb.user.LocalUserHome;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Collection;

/**
 * Created by denysburlakov on 02.12.14.
 */
public class EJBFunctions {
    private EJBFunctions(){

    }
    public static LocalUser createUser(String login, String email, String name, String surname, String password) throws CreateException {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalUserHome home = null;
        try {
            home = (LocalUserHome) ic.lookup("java:comp/env/ejb/User");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalUser user = null;
        if (home != null) {
            user = home.create(login, email, name, surname, password, 1);
            return user;
        }
        return null;
    }
    public static void findUser(String login){

    }
    public static LocalUser login(String login, String password) throws Exception, BadPasswordException, FinderException {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalUserHome home = null;
        try {
            home = (LocalUserHome) ic.lookup("java:comp/env/ejb/User");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        if (home != null) {
            return home.findUser(login, password);
        }else{
            throw new Exception("Error with EJBs");
        }
    }
    public static LocalTask createTask(String description, String status, int id_group_user, int id_order) throws Exception {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalTaskHome home = null;
        try {
            home = (LocalTaskHome) ic.lookup("java:comp/env/ejb/Task");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        if (home != null) {
            try {
                return home.create(description, status, id_group_user, id_order);
            } catch (CreateException e) {
                e.printStackTrace();
            }
        }else{
            throw new Exception("Error with EJBs");
        }
        return null;
    }

    public static LocalServiceOrder createServiceOrder(int id_status, int id_scenario, String login, int id_price){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalServiceOrderHome home = null;
        try {
            home = (LocalServiceOrderHome) ic.lookup("java:comp/env/ejb/ServiceOrder");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalServiceOrder service_order = null;
        try {
            service_order = home.create(id_status, id_scenario, login, id_price);
            return service_order;
        } catch (CreateException e) {
            return null;
        }
    }

    public static LocalServiceOrder findServiceOrder(int id) {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalServiceOrderHome home = null;
        try {
            home = (LocalServiceOrderHome) ic.lookup("java:comp/env/ejb/ServiceOrder");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalServiceOrder service_order = null;
        try {
            service_order = home.findByPrimaryKey(id);
            return service_order;
        } catch (FinderException e) {
            return null;
        }

    }
    public static LocalProviderLocation findProviderLocation(float longtitude, float latitude){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalProviderLocationHome home = null;
        try {
            home = (LocalProviderLocationHome)ic.lookup("java:comp/env/ejb/ProviderLocation");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalProviderLocation location = null;
        try {
            location = home.findClosest(longtitude, latitude);
            return location;
        } catch (FinderException e) {
            return null;
        }
    }

    public static LocalProviderLocation findProviderLocationById(int id){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalProviderLocationHome home = null;
        try {
            home = (LocalProviderLocationHome)ic.lookup("java:comp/env/ejb/ProviderLocation");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalProviderLocation location = null;
        try {
            location = home.findByPrimaryKey(id);
            return location;
        } catch (FinderException e) {
            return null;
        }
    }

    public static Collection findByProviderLocationId(int id){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalServiceHome home = null;
        try {
            home = (LocalServiceHome)ic.lookup("java:comp/env/ejb/Service");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        Collection services = null;
        try {
            services = home.findByProviderLocationId(id);
            return services;
        } catch (FinderException e) {
            return null;
        }
    }
    
      public static LocalTask findIncompleteTask() {
        InitialContext ic = null;
          System.out.println("dwa1");
        try {
            System.out.println("dwa2");
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalTaskHome home = null;
        try {
            System.out.println("dwa3");
            home = (LocalTaskHome) ic.lookup("java:comp/env/ejb/Task");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalTask localTask = null;
        try {
            System.out.println("dwa4");
            localTask = home.findIncompleteTask();
            System.out.println(localTask);
            System.out.println("dwa5");
            return localTask;
        } catch (FinderException e) {
            return null;
        }

    }
    
    public static LocalTask findLocalTaskById(int id){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalTaskHome home = null;
        try {
            home = (LocalTaskHome)ic.lookup("java:comp/env/ejb/Task");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalTask task = null;
        try {
            task = home.findByPrimaryKey(id);
            return task;
        } catch (FinderException e) {
            return null;
        }
    }

    public static float getDestinationToProvider(float longtitude, float latitude){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalProviderLocationHome home = null;
        try {
            home = (LocalProviderLocationHome)ic.lookup("java:comp/env/ejb/ProviderLocation");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        float distance;
        try {
            distance = home.getDistanceToProvider(longtitude, latitude);
            return distance;
        } catch (FinderException e) {
            return -1f;
        }
    }

    public static LocalPrice findPrice(int service, int providerLocation){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalPriceHome home = null;
        try {
            home = (LocalPriceHome)ic.lookup("java:comp/env/ejb/Price");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        try {
            return home.findByLocationAndService(service, providerLocation);
        } catch (FinderException e) {
            return null;
        }
    }
}
