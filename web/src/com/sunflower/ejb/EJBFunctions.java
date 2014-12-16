package com.sunflower.ejb;

import com.sunflower.UserGroup;
import com.sunflower.constants.SIStatuses;
import com.sunflower.constants.SOStatuses;
import com.sunflower.constants.Scenarios;
import com.sunflower.constants.UserGroups;
import com.sunflower.ejb.ProviderLocation.LocalProviderLocation;
import com.sunflower.ejb.ProviderLocation.LocalProviderLocationHome;
import com.sunflower.ejb.ProviderLocation.ProviderLocWrapper;
import com.sunflower.ejb.ServiceOrder.LocalServiceOrder;
import com.sunflower.ejb.ServiceOrder.LocalServiceOrderHome;
import com.sunflower.ejb.price.LocalPrice;
import com.sunflower.ejb.price.LocalPriceHome;
import com.sunflower.ejb.price.PriceCatalog;
import com.sunflower.ejb.service.LocalServiceHome;
import com.sunflower.ejb.serviceinstance.LocalServiceInstance;
import com.sunflower.ejb.serviceinstance.LocalServiceInstanceHome;
import com.sunflower.ejb.task.LocalTask;
import com.sunflower.ejb.task.LocalTaskHome;
import com.sunflower.ejb.task.TaskWrapper;
import com.sunflower.ejb.user.BadPasswordException;
import com.sunflower.ejb.user.CustomerWrapper;
import com.sunflower.ejb.user.LocalUser;
import com.sunflower.ejb.user.LocalUserHome;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by denysburlakov on 02.12.14.
 */
public class EJBFunctions {
    private EJBFunctions(){

    }
    public static LocalUser createUser(String login, String email, String name, String surname, String password, int group) throws CreateException {
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
            user = home.create(login, email, name, surname, password, group);
            return user;
        }
        return null;
    }
    public static LocalUser findUser(String login) throws FinderException{
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
            user = home.findUser(login);
            return user;
        }
        return null;
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

    public static Vector<CustomerWrapper> getCustomers(int from, int to) throws Exception {
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
            return (Vector<CustomerWrapper>)home.getCustomers(from, to);
        }else{
            throw new Exception("Error with EJBs");
        }

    }

    public static int getNumberOfCustomers() {
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
        try {
            return home.getNumberOfCustomers();
        } catch (FinderException e) {
            return 0;
        }

    }

    public static LocalTask createTask(String description, int id_group_user, int id_order) throws Exception {
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
                return home.create(description, id_group_user, id_order, null);
            } catch (CreateException e) {
                e.printStackTrace();
            }
        }else{
            throw new Exception("Error with EJBs");
        }
        return null;
    }

    public static LocalServiceOrder createServiceOrder(Integer id_service_inst, int id_scenario, String login, int id_price, float longtitude, float latitude){
        LocalServiceOrder order;
        if(id_service_inst == null && id_scenario == Scenarios.NEW){

            LocalServiceInstance instance = createServiceInstance(SIStatuses.PLANNED);
            int inner_id_service_inst = instance.getId_service_inst();
            order= plainCreateServiceOrder(inner_id_service_inst, id_scenario, login, id_price, longtitude, latitude);
            if(isLocationHasFreePorts(getProviderLocationByPrice(order.getId_price()))){
                try {
                    createTask("Connect ports for "+login+"'s instance", UserGroups.PE, order.getId_order());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    createTask("Add new router for new instances", UserGroups.IE, order.getId_order());
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        }else if(id_service_inst != null && id_scenario == Scenarios.MODIFY){
            order = plainCreateServiceOrder(id_service_inst, id_scenario, login, id_price, longtitude, latitude);
            if(isLocationHasFreePorts(getProviderLocationByPrice(order.getId_price()))){
                try {
                    createTask("Connect ports for "+login+"'s instance", UserGroups.PE, order.getId_order());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    createTask("Add new router for new instances", UserGroups.IE, order.getId_order());
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            //createTask(order.getId_order());
        }else if(id_service_inst !=null && id_scenario == Scenarios.DISCONNECT){
            order = plainCreateServiceOrder(id_service_inst, id_scenario, login, id_price, longtitude, latitude);
            try {
                createTask("Disconnect ports for "+login+"'s instance", UserGroups.IE, order.getId_order());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            throw new UnsupportedOperationException();
        }
        return order;
    }
    
     public static LocalCircuit createCircuit(int Id_Port, int Id_Cable){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalCircuitHome home = null;
        try {
            home = (LocalCircuitHome) ic.lookup("java:comp/env/ejb/Circuit");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalCircuit service_order;
        try {
            service_order = home.create(Id_Port, Id_Cable);
            return service_order;
        } catch (CreateException e) {
            return null;
        }
    }

    private static boolean isLocationHasFreePorts(int id_prov_location) {
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
        return home.isLocationHasFreePorts(id_prov_location);

    }
    private static int getProviderLocationByPrice(int id_price){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalPriceHome home = null;
        try {
            home = (LocalPriceHome) ic.lookup("java:comp/env/ejb/Price");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        try {
            return home.getLocationByPrice(id_price);
        } catch (FinderException e) {
            //Critical exception
            e.printStackTrace();
            return 0;
        }
    }

    private static LocalServiceOrder plainCreateServiceOrder(Integer id_service_inst, int id_scenario, String login, int id_price, float longtitude, float latitude){
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
            service_order = home.create(id_scenario, login, id_price, id_service_inst, longtitude, latitude);
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

    public static Collection findOrderByLogin(String login, int from , int to) {
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
        Collection service_order = null;
        try {
            service_order = home.getOrdersByLogin(login, from, to);
            return service_order;
        } catch (FinderException e) {
            return null;
        }

    }

    public static int getNumberOfOrdersByLogin(String login) {
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
        int number;
        try {
            number = home.getNumberOfOrdersByLogin(login);
            return number;
        } catch (FinderException e) {
            return 0;
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
    /*
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
    */
    
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


    public static LocalServiceInstance createServiceInstance(int status){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalServiceInstanceHome home = null;
        try {
            home = (LocalServiceInstanceHome) ic.lookup("java:comp/env/ejb/ServiceInstance");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalServiceInstance service_order = null;

        try {
            service_order = home.create(status);
            return service_order;
        } catch (CreateException e) {
            return null;
        }
    }

    public static ArrayList<ProviderLocWrapper> getAllLocations(){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalProviderLocationHome home = null;
        try {
            home = (LocalProviderLocationHome) ic.lookup("java:comp/env/ejb/ProviderLocation");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        ArrayList<ProviderLocWrapper> locations = null;

        if(home != null) {
            locations = (ArrayList<ProviderLocWrapper>) home.getAllLocations();
        }

        return locations;
    }

    public static ArrayList<PriceCatalog> getServicePriceByLoc(String location){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalPriceHome home = null;
        try {
            home = (LocalPriceHome) ic.lookup("java:comp/env/ejb/Price");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        ArrayList<PriceCatalog> catalogs = null;

        if(home != null) {
            try {
                catalogs = (ArrayList<PriceCatalog>) home.getServicePriceByLoc(location);
            } catch (FinderException e) {
                e.printStackTrace();
            }
        }

        return catalogs;
    }

    public static Vector<TaskWrapper> getTasksByEngineer(int id_group_user, int from, int to) throws Exception {
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
            return (Vector<TaskWrapper>)home.getTasksByEngineer(id_group_user, from, to);
        }else{
            throw new Exception("Error with EJBs");
        }
    }

    public static int getNumberOfTasksByEngineer(int id_group_user) throws Exception {
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
            return home.getNumberOfTasksByEngineer(id_group_user);
        }else{
            throw new Exception("Error with EJBs");
        }
    }
    public static void assignTask(int id_task, String login) throws Exception {
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
            home.assignTask(id_task, login);
        }else{
            throw new Exception("Error with EJBs");
        }
    }
    
    public static LocalServiceInstance findServiceInstance(int id) {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalServiceInstanceHome home = null;
        try {
            home = (LocalServiceInstanceHome) ic.lookup("java:comp/env/ejb/ServiceInstance");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalServiceInstance service_instance = null;
        try {
            service_instance = home.findByPrimaryKey(id);
            return service_instance;
        } catch (FinderException e) {
            return null;
        }

    }
    
    public static LocalPort findLocalPortById(int id){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalPortHome home = null;
        try {
            home = (LocalPortHome)ic.lookup("java:comp/env/ejb/Port");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalPort port = null;
        try {
            port = home.findByPrimaryKey(id);
            return port;
        } catch (FinderException e) {
            return null;
        }
    }
    public static LocalCircuit findLocalCircuitById(int id){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalCircuitHome home = null;
        try {
            home = (LocalCircuitHome)ic.lookup("java:comp/env/ejb/Circuit");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalCircuit circuit = null;
        try {
            circuit = home.findByPrimaryKey(id);
            return circuit;
        } catch (FinderException e) {
            return null;
        }
    }

    public Collection getServiceInstances(String login, int from, int to) throws Exception {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalServiceInstanceHome home = null;
        try {
            home = (LocalServiceInstanceHome) ic.lookup("java:comp/env/ejb/ServiceInstance");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        if (home != null) {
            return home.getServiceInstances(login, from, to);
        }else{
            throw new Exception("Error with EJBs");
        }
    }

    public static int getNumberOfInstancesByLogin(String login) throws Exception {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        LocalServiceInstanceHome home = null;
        try {
            home = (LocalServiceInstanceHome) ic.lookup("java:comp/env/ejb/ServiceInstance");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        if (home != null) {
            return home.getNumberOfInstancesByLogin(login);
        }else{
            throw new Exception("Error with EJBs");
        }
    }
}
