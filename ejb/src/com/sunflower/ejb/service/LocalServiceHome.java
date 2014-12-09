package com.sunflower.ejb.service;

import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import java.util.Collection;

/**
 * Created by denysburlakov on 08.12.14.
 */
public interface LocalServiceHome extends EJBLocalHome {
    com.sunflower.ejb.service.LocalService findByPrimaryKey(Integer key) throws FinderException;
    Collection findByProviderLocationId(int id) throws FinderException;
}
