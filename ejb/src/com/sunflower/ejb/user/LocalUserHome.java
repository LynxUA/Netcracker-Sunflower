package com.sunflower.ejb.user;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Created by denysburlakov on 30.11.14.
 */
public interface LocalUserHome extends EJBLocalHome {
    public LocalUser create(String login, String email, String name, String surname, String password, int id) throws CreateException;
    public LocalUser findByPrimaryKey(String key) throws FinderException;
}
