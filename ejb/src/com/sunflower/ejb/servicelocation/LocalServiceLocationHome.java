package com.sunflower.ejb.servicelocation;

import com.sunflower.ejb.task.LocalTask;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Created by denysburlakov on 05.12.14.
 */
public interface LocalServiceLocationHome extends EJBLocalHome {
    public LocalServiceLocation create(String location, int id_prov_location, int id_order) throws CreateException;
    com.sunflower.ejb.servicelocation.LocalServiceLocation findByPrimaryKey(Integer key) throws FinderException;
}
