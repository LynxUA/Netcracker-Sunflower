package com.sunflower.ejb;

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

}
