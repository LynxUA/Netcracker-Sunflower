package com.sunflower.ejb.device;

import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Created by denysburlakov on 08.12.14.
 */
public interface LocalDeviceHome extends EJBLocalHome {
    com.sunflower.ejb.price.LocalPrice findByPrimaryKey(Integer key) throws FinderException;
}
