package com.sunflower.ejb;

import com.sunflower.ejb.ServiceOrder.LocalServiceOrder;
import com.sunflower.ejb.ServiceOrder.LocalServiceOrderHome;
import com.sunflower.ejb.task.LocalTask;
import com.sunflower.ejb.task.LocalTaskHome;
import com.sunflower.ejb.user.BadPasswordException;
import com.sunflower.ejb.user.LocalUser;
import com.sunflower.ejb.user.LocalUserHome;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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

    public static LocalServiceOrder createServiceOrder(Integer id, String status, String scenarion, String login){
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
            service_order = home.create(status, scenarion, login, id);
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
}
