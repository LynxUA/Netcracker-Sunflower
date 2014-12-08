package com.sunflower.ejb.service;

import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Created by denysburlakov on 08.12.14.
 */
public interface LocalServiceHome extends EJBLocalHome {
    com.sunflower.ejb.service.LocalService findByPrimaryKey(Integer key) throws FinderException;
}
