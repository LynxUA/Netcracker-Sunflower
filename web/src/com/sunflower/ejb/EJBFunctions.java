package com.sunflower.ejb;

import com.sunflower.constants.SIStatuses;
import com.sunflower.constants.SOStatuses;
import com.sunflower.constants.Scenarios;
import com.sunflower.constants.UserGroups;
import com.sunflower.ejb.ProviderLocation.LocalProviderLocation;
import com.sunflower.ejb.ProviderLocation.LocalProviderLocationHome;
import com.sunflower.ejb.ProviderLocation.ProviderLocWrapper;
import com.sunflower.ejb.ServiceOrder.LocalServiceOrder;
import com.sunflower.ejb.ServiceOrder.LocalServiceOrderHome;
import com.sunflower.ejb.circuit.LocalCircuit;
import com.sunflower.ejb.circuit.LocalCircuitHome;
import com.sunflower.ejb.port.LocalPort;
import com.sunflower.ejb.port.LocalPortHome;
import com.sunflower.ejb.price.LocalPrice;
import com.sunflower.ejb.price.LocalPriceHome;
import com.sunflower.ejb.price.PriceCatalog;
import com.sunflower.ejb.service.LocalServiceHome;
import com.sunflower.ejb.serviceinstance.LocalServiceInstance;
import com.sunflower.ejb.serviceinstance.LocalServiceInstanceHome;
import com.sunflower.ejb.task.LocalTask;
import com.sunflower.ejb.task.LocalTaskHome;
import com.sunflower.ejb.task.TaskWrapper;
import com.sunflower.ejb.task.UserWasAssignedException;
import com.sunflower.ejb.user.*;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import org.apache.log4j.*;

/**
 * Created by denysburlakov on 02.12.14.
 */
public class EJBFunctions {

    private static Logger logger = Logger.getLogger(EJBFunctions.class);

    private EJBFunctions(){}

    public static LocalUser createUser(String login, String email, String name, String surname, String password, int group) throws CreateException {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalUserHome home = null;
        try {
            home = (LocalUserHome) ic.lookup("java:comp/env/ejb/User");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalUser user = null;
        user = home.create(login, email, name, surname, password, group);
        return user;
    }
    public static LocalUser findUser(String login) throws FinderException{
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalUserHome home = null;
        try {
            home = (LocalUserHome) ic.lookup("java:comp/env/ejb/User");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalUser user = null;
        user = home.findUser(login);
        return user;

    }
    public static LocalUser login(String login, String password) throws Exception, BadPasswordException, FinderException {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalUserHome home = null;
        try{
            home = (LocalUserHome) ic.lookup("java:comp/env/ejb/User");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }

        return home.findUser(login, password);

    }

    public static void setPassword(String login, String password) throws NoSuchUserException {
        InitialContext ic;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        }
        LocalUserHome home;
        try{
            home = (LocalUserHome) ic.lookup("java:comp/env/ejb/User");
        } catch (NamingException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        }

        home.setPassword(login, password);

    }

    public static Vector<CustomerWrapper> getCustomers(int from, int to) throws Exception {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalUserHome home = null;
        try {
            home = (LocalUserHome) ic.lookup("java:comp/env/ejb/User");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        return (Vector<CustomerWrapper>)home.getCustomers(from, to);

    }

    public static int getNumberOfCustomers() throws FinderException {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalUserHome home = null;
        try {
            home = (LocalUserHome) ic.lookup("java:comp/env/ejb/User");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        return home.getNumberOfCustomers();

    }

    public static LocalTask createTask(String description, int id_group_user, int id_order) throws Exception {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalTaskHome home = null;
        try {
            home = (LocalTaskHome) ic.lookup("java:comp/env/ejb/Task");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }

        try {
            return home.create(description, id_group_user, id_order, null);
        } catch (CreateException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
    }

    public static LocalServiceOrder createServiceOrder(Integer id_service_inst, int id_scenario, String login, int id_price, float longtitude, float latitude){
        LocalServiceOrder order;
        if(id_service_inst == null && id_scenario == Scenarios.NEW){

            LocalServiceInstance instance = createServiceInstance(SIStatuses.PLANNED);
            int inner_id_service_inst = instance.getId_service_inst();
            order= plainCreateServiceOrder(inner_id_service_inst, id_scenario, login, id_price, longtitude, latitude);
            if(isLocationHasFreePorts(getProviderLocationByPrice(order.getId_price()))){
                try {
                    createTask("Connect cable for "+login+"'s instance", UserGroups.IE, order.getId_order());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new UnknownError();
                }
            }else{
                try {
                    createTask("Add new router for new instances", UserGroups.IE, order.getId_order());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new UnknownError();
                }


            }

        }else if(id_service_inst != null && id_scenario == Scenarios.MODIFY){
            order = plainCreateServiceOrder(id_service_inst, id_scenario, login, id_price, longtitude, latitude);
            if(isLocationHasFreePorts(getProviderLocationByPrice(order.getId_price()))){
                try {
                    createTask("Connect ports for "+login+"'s instance", UserGroups.PE, order.getId_order());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new UnknownError();
                }
            }else{
                try {
                    createTask("Add new router for new instances", UserGroups.IE, order.getId_order());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new UnknownError();
                }


            }
            //createTask(order.getId_order());
        }else if(id_service_inst !=null && id_scenario == Scenarios.DISCONNECT){
            order = plainCreateServiceOrder(id_service_inst, id_scenario, login, id_price, longtitude, latitude);
            int id_port = getPortByInstance(id_service_inst);
            try {
                createTask("Disconnect ports for "+login+"'s instance, Port="+id_port, UserGroups.PE, order.getId_order());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new UnknownError();
            }

        }else{
            throw new UnsupportedOperationException();
        }
        return order;
    }
    
     public static LocalCircuit createCircuit(int Id_Port){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalCircuitHome home = null;
        try {
            home = (LocalCircuitHome) ic.lookup("java:comp/env/ejb/Circuit");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalCircuit localCircuit;
        try {
            localCircuit = home.create(Id_Port);
            return localCircuit;
        } catch (CreateException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        }
    }
    private static int getPortByInstance(int id_service_inst){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalPortHome home;
        try {
            home = (LocalPortHome)ic.lookup("java:comp/env/ejb/Port");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        return home.getPortIdByInstance(id_service_inst);
    }

    private static boolean isLocationHasFreePorts(int id_prov_location) {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalProviderLocationHome home;
        try {
            home = (LocalProviderLocationHome)ic.lookup("java:comp/env/ejb/ProviderLocation");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        return home.isLocationHasFreePorts(id_prov_location);

    }
    private static int getProviderLocationByPrice(int id_price){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
        }
        LocalPriceHome home = null;
        try {
            home = (LocalPriceHome) ic.lookup("java:comp/env/ejb/Price");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        try {
            return home.getLocationByPrice(id_price);
        } catch (FinderException e) {
            //Critical exception
            logger.error(e.getMessage(), e);
            return 0;
        }
    }

    private static LocalServiceOrder plainCreateServiceOrder(Integer id_service_inst, int id_scenario, String login, int id_price, float longtitude, float latitude){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalServiceOrderHome home = null;
        try {
            home = (LocalServiceOrderHome) ic.lookup("java:comp/env/ejb/ServiceOrder");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalServiceOrder service_order;
        try {
            service_order = home.create(id_scenario, login, id_price, id_service_inst, longtitude, latitude);
            return service_order;
        } catch (CreateException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        }
    }

    public static LocalServiceOrder findServiceOrder(int id) {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalServiceOrderHome home = null;
        try {
            home = (LocalServiceOrderHome) ic.lookup("java:comp/env/ejb/ServiceOrder");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalServiceOrder service_order;
        try {
            service_order = home.findByPrimaryKey(id);
            return service_order;
        } catch (FinderException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        }

    }

    public static Collection findOrderByLogin(String login, int from , int to) {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalServiceOrderHome home = null;
        try {
            home = (LocalServiceOrderHome) ic.lookup("java:comp/env/ejb/ServiceOrder");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        Collection service_order;
        try {
            service_order = home.getOrdersByLogin(login, from, to);
            return service_order;
        } catch (FinderException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        }

    }

    public static int getNumberOfOrdersByLogin(String login) {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
        }
        LocalServiceOrderHome home = null;
        try {
            home = (LocalServiceOrderHome) ic.lookup("java:comp/env/ejb/ServiceOrder");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        try {
            return home.getNumberOfOrdersByLogin(login);
        } catch (FinderException e) {
            return 0;
        }

    }

    public static LocalProviderLocation findProviderLocation(float longtitude, float latitude){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalProviderLocationHome home;
        try {
            home = (LocalProviderLocationHome)ic.lookup("java:comp/env/ejb/ProviderLocation");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalProviderLocation location;
        try {
            location = home.findClosest(longtitude, latitude);
            return location;
        } catch (FinderException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        }
    }

    public static LocalProviderLocation findProviderLocationById(int id){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalProviderLocationHome home;
        try {
            home = (LocalProviderLocationHome)ic.lookup("java:comp/env/ejb/ProviderLocation");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalProviderLocation location;
        try {
            location = home.findByPrimaryKey(id);
            return location;
        } catch (FinderException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        }
    }

    public static Collection findServiceByProviderLocationId(int id){
        InitialContext ic;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalServiceHome home;
        try {
            home = (LocalServiceHome)ic.lookup("java:comp/env/ejb/Service");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        Collection services;
        try {
            services = home.findByProviderLocationId(id);
            return services;
        } catch (FinderException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
    }
    
    public static LocalTask findLocalTaskById(int id){
        InitialContext ic;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalTaskHome home;
        try {
            home = (LocalTaskHome)ic.lookup("java:comp/env/ejb/Task");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalTask task;
        try {
            task = home.findByPrimaryKey(id);
            return task;
        } catch (FinderException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        }
    }

    public static float getDestinationToProvider(float longtitude, float latitude){
        InitialContext ic;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalProviderLocationHome home;
        try {
            home = (LocalProviderLocationHome)ic.lookup("java:comp/env/ejb/ProviderLocation");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        float distance;
        try {
            distance = home.getDistanceToProvider(longtitude, latitude);
            return distance;
        } catch (FinderException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        }
    }

    public static LocalPrice findPrice(int service, int providerLocation){
        InitialContext ic;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalPriceHome home;
        try {
            home = (LocalPriceHome)ic.lookup("java:comp/env/ejb/Price");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        try {
            return home.findByLocationAndService(service, providerLocation);
        } catch (FinderException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        }
    }


    public static LocalServiceInstance createServiceInstance(int status){
        InitialContext ic;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalServiceInstanceHome home;
        try {
            home = (LocalServiceInstanceHome) ic.lookup("java:comp/env/ejb/ServiceInstance");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalServiceInstance service_order;

        try {
            service_order = home.create(status);
            return service_order;
        } catch (CreateException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        }
    }

    public static ArrayList<ProviderLocWrapper> getAllLocations(){
        InitialContext ic;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalProviderLocationHome home;
        try {
            home = (LocalProviderLocationHome) ic.lookup("java:comp/env/ejb/ProviderLocation");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        ArrayList<ProviderLocWrapper> locations = null;

        if(home != null) {
            locations = (ArrayList<ProviderLocWrapper>) home.getAllLocations();
        }

        return locations;
    }

    public static ArrayList<PriceCatalog> getServicePriceByLoc(String location){

        InitialContext ic = null;
        LocalPriceHome home = null;

        try {

            ic = new InitialContext();
            home = (LocalPriceHome) ic.lookup("java:comp/env/ejb/Price");

        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();

        }

        ArrayList<PriceCatalog> catalogs = null;

        try {
            catalogs = (ArrayList<PriceCatalog>) home.getServicePriceByLoc(location);
        } catch (FinderException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        return catalogs;
    }

    public static Vector<TaskWrapper> getTasksByEngineer(int id_group_user, int from, int to) throws Exception {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalTaskHome home = null;
        try {
            home = (LocalTaskHome) ic.lookup("java:comp/env/ejb/Task");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }

        return (Vector<TaskWrapper>)home.getTasksByEngineer(id_group_user, from, to);

    }

    public static int getNumberOfTasksByEngineer(int id_group_user) throws Exception {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
        }
        LocalTaskHome home = null;
        try {
            home = (LocalTaskHome) ic.lookup("java:comp/env/ejb/Task");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
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
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalTaskHome home = null;
        try {
            home = (LocalTaskHome) ic.lookup("java:comp/env/ejb/Task");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }

        home.assignTask(id_task, login);

    }
    
    public static LocalServiceInstance findServiceInstance(int id) {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalServiceInstanceHome home = null;
        try {
            home = (LocalServiceInstanceHome) ic.lookup("java:comp/env/ejb/ServiceInstance");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalServiceInstance service_instance = null;
        try {
            service_instance = home.findByPrimaryKey(id);
            return service_instance;
        } catch (FinderException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        }

    }
    
    public static LocalPort findLocalPortById(int id){
        InitialContext ic;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalPortHome home;
        try {
            home = (LocalPortHome)ic.lookup("java:comp/env/ejb/Port");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalPort port;
        try {
            port = home.findByPrimaryKey(id);
            return port;
        } catch (FinderException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        }
    }
    public static LocalCircuit findLocalCircuitById(int id){
        InitialContext ic;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalCircuitHome home;
        try {
            home = (LocalCircuitHome)ic.lookup("java:comp/env/ejb/Circuit");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalCircuit circuit;
        try {
            circuit = home.findByPrimaryKey(id);
            return circuit;
        } catch (FinderException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        }
    }

    public static Collection getServiceInstances(String login, int from, int to) throws Exception {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalServiceInstanceHome home = null;
        try {
            home = (LocalServiceInstanceHome) ic.lookup("java:comp/env/ejb/ServiceInstance");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }

        return home.getServiceInstances(login, from, to);

    }

    public static int getNumberOfInstancesByLogin(String login) throws Exception {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalServiceInstanceHome home = null;
        try {
            home = (LocalServiceInstanceHome) ic.lookup("java:comp/env/ejb/ServiceInstance");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        return home.getNumberOfInstancesByLogin(login);

    }

    public static LocalTask findIncompleteTask(String name) throws FinderException {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalTaskHome home = null;
        try {
            home = (LocalTaskHome) ic.lookup("java:comp/env/ejb/Task");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }

        return home.findIncompleteTask(name);

    }
    public static void cancelOrder(String login, int id_order) throws UserWasAssignedException {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalServiceOrderHome home = null;
        try {
            home = (LocalServiceOrderHome) ic.lookup("java:comp/env/ejb/ServiceOrder");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        try {
            if (home != null&&login.contains(home.findByPrimaryKey(id_order).getUserLogin())) {
                home.cancelOrder(id_order);
            }
        } catch (FinderException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
    }

    public static Collection getSLByLogin(String login) throws Exception {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalServiceInstanceHome home = null;
        try {
            home = (LocalServiceInstanceHome) ic.lookup("java:comp/env/ejb/ServiceInstance");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }

        return home.getSLByLogin(login);
    }
    public static LocalServiceOrder findSOBySI(int id_service_inst){
        InitialContext ic = null;
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalServiceOrderHome home = null;
        try {
            home = (LocalServiceOrderHome) ic.lookup("java:comp/env/ejb/ServiceOrder");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
        LocalServiceOrder service_order;
        try {
            service_order = home.findByInstanceId(id_service_inst);
            return service_order;
        } catch (FinderException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }
    }


}
