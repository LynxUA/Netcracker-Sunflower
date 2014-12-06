package com.sunflower.ejb.servicelocation;

import javax.ejb.EJBLocalObject;

/**
 * Created by denysburlakov on 05.12.14.
 */
public interface LocalServiceLocation extends EJBLocalObject {
    public String getLocation();
    public void setLocation(String location);

    public int getId_prov_location();
    public void setId_prov_location(int id_prov_location);

    public int getId_order();
    public void setId_order(int id_order);
}
