package com.sunflower.ejb.ServiceOrder;

import javax.ejb.EJBLocalObject;

/**
 * Created by Andriy on 12/3/2014.
 */
public interface LocalServiceOrder extends EJBLocalObject {
    public String getStatus();
    public void setStatus(String status);
    public String getScenario();
    public int getUserGroup();
    int getId();
}
