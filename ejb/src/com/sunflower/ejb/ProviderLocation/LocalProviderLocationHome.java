package com.sunflower.ejb.ProviderLocation;


import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;
import java.util.Collection;

/**
 * Created by Алексей on 12/6/2014.
 */
public interface LocalProviderLocationHome extends EJBLocalHome {
    public LocalProviderLocation create(String location, int id_prov_location, int num_of_services, float longtitude, float latitude) throws CreateException;
    public LocalProviderLocation findByPrimaryKey(Integer key) throws FinderException;
    public LocalProviderLocation findClosest(float longtitude, float latitude) throws FinderException;
    public float getDistanceToProvider(float longtitude, float latitude) throws ObjectNotFoundException;
    public boolean isLocationHasFreePorts(int id_prov_location);
    Collection getAllLocations();
}
