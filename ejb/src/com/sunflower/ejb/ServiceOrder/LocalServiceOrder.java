package com.sunflower.ejb.ServiceOrder;

import javax.ejb.EJBLocalObject;

/**
 * Created by Andriy on 12/3/2014.
 */
public interface LocalServiceOrder extends EJBLocalObject {
    public int getId_status();

    public void setId_status(int id_status);

    public int getId_scenario();

    public void setId_scenario(int id_scenario);
    public String getUserLogin();
    public int getId_order();
    public int getId_price();
    public float getLatitude();
    public float getLongtitude();
}
