package com.sunflower.ejb.ServiceOrder;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by Andriy on 12/3/2014.
 */
public interface LocalServiceOrderHome extends EJBLocalHome {
    public LocalServiceOrder create(int id_scenario, String login, int id_price, int id_service_inst, float longtitude, float latitude) throws CreateException;
    public LocalServiceOrder findByPrimaryKey(Integer key) throws FinderException;
    public Collection getOrdersByLogin(String login, int from, int to) throws FinderException;
    public int getNumberOfOrdersByLogin(String login) throws FinderException;
}
