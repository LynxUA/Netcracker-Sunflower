package com.sunflower.ejb.ServiceOrder;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Created by Andriy on 12/3/2014.
 */
public interface LocalServiceOrderHome extends EJBLocalHome {
    public LocalServiceOrder create(String status, String scenario, String login, int id) throws CreateException;
    public LocalServiceOrder findByPrimaryKey(Integer key) throws FinderException;
}
