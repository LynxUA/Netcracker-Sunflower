package com.sunflower.ejb.serviceinstance;

import javax.ejb.EJBLocalObject;

/**
 * Created by denysburlakov on 13.12.14.
 */
public interface LocalServiceInstance extends EJBLocalObject {
    public int getId_service_inst();
    public int getStatus();
    public int getId_circuit();
}
