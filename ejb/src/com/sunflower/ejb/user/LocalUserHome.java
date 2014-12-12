package com.sunflower.ejb.user;

import com.sunflower.ejb.task.LocalTask;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Created by denysburlakov on 30.11.14.
 */
public interface LocalUserHome extends EJBLocalHome {
    public LocalUser create(String login, String email, String name, String surname, String password, int id) throws CreateException;
    public LocalUser findByPrimaryKey(String key) throws FinderException;
    public LocalUser findUser(String login, String password) throws FinderException, BadPasswordException;
    public LocalUser findUser(String login) throws FinderException;
}
