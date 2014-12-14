package com.sunflower.ejb.serviceinstance;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Created by denysburlakov on 13.12.14.
 */
public interface LocalServiceInstanceHome extends EJBLocalHome {
    com.sunflower.ejb.serviceinstance.LocalServiceInstance findByPrimaryKey(Integer key) throws FinderException;
    public LocalServiceInstance create(int status) throws CreateException;
}
