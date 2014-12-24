package com.sunflower.ejb.price;

import javax.ejb.EJBLocalObject;

/**
 * Created by denysburlakov on 08.12.14.
 */
public interface LocalPrice extends EJBLocalObject {
    public int getId_price();
    public float getPrice_of_service();
    public float getPrice_of_location();
    public int getId_service();
    public int getId_prov_location();

}
