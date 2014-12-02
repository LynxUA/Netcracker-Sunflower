package com.sunflower.ejb;

import com.sunflower.ejb.user.LocalUser;
import com.sunflower.ejb.user.LocalUserHome;

import javax.ejb.CreateException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by denysburlakov on 02.12.14.
 */
public class EJBFunctions {
    private EJBFunctions(){

    }
    public static String createUser(String login, String email, String name, String surname, String password){
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
        try {
            if (home != null) {
                user = home.create(login, email, name, surname, password, 1);
                return user.getLogin();
            }
        } catch (CreateException e) {
            return null;
        }
        return null;
    }
    public static void findUser(String login){

    }

}
