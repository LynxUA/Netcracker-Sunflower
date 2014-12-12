package com.sunflower.ejb.ServiceOrder;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import java.util.Collection;

/**
 * Created by Andriy on 12/3/2014.
 */
public interface LocalServiceOrderHome extends EJBLocalHome {
    public LocalServiceOrder create(int id_status, int id_scenario, String login, int id_price) throws CreateException;
    public LocalServiceOrder findByPrimaryKey(Integer key) throws FinderException;
    public Collection getOrdersByLogin(String login) throws FinderException;
}
