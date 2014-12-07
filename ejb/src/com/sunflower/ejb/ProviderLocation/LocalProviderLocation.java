package com.sunflower.ejb.ProviderLocation;

import javax.ejb.EJBLocalObject;

/**
 * Created by Алексей on 12/6/2014.
 */
public interface LocalProviderLocation extends EJBLocalObject {

    public int getId_Prov_Location();
    public String getLocation();
    public void setLocation(String location);
    public int getNum_of_services();
    public int setNum_of_services();
    public int getId_order();

}

