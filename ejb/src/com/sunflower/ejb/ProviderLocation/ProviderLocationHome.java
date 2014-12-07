package com.sunflower.ejb.ProviderLocation;


import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Created by Алексей on 12/6/2014.
 */
public interface ProviderLocationHome extends EJBLocalHome {
    public LocalProviderLocation create(String location, int id_prov_location, int id_order) throws CreateException;
    LocalProviderLocation findByPrimaryKey(Integer key) throws FinderException;
}
