package com.sunflower.ejb.port;

import javax.ejb.EJBLocalObject;

/**
 * Created by Alexey on 12/11/2014.
 */

import javax.ejb.EJBLocalObject;

/**
 * Created by Алексей on 12/6/2014.
 */
public interface LocalPort extends EJBLocalObject {

    public int getId_Port();
    public String getStatus();
    public void setStatus(String status);
    public int getId_Device();


}