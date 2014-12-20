package com.sunflower.ejb.user;

import javax.ejb.EJBLocalObject;

/**
 * Created by denysburlakov on 30.11.14.
 */
public interface LocalUser extends EJBLocalObject {
    public String getLogin();
    public String getEmail();
    public String getName();
    public String getSurname();
    public String getPassword();
    public void setPassword(String password);
    public void setName(String name);
    public void setSurname(String surname);
    public int getGroup();
}
