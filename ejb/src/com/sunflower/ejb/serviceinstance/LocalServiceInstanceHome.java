package com.sunflower.ejb.serviceinstance;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import java.util.Collection;

/**
 * Created by denysburlakov on 13.12.14.
 */
public interface LocalServiceInstanceHome extends EJBLocalHome {
    com.sunflower.ejb.serviceinstance.LocalServiceInstance findByPrimaryKey(Integer key) throws FinderException;
    public LocalServiceInstance create(int status) throws CreateException;
    public Collection getServiceInstances(String login, int from, int to);
    public int getNumberOfInstancesByLogin(String login) throws FinderException;
    public Collection getSLByLogin(String login) throws FinderException;
}
