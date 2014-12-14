package com.sunflower.ejb.price;

import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Created by denysburlakov on 08.12.14.
 */
public interface LocalPriceHome extends EJBLocalHome {
    com.sunflower.ejb.price.LocalPrice findByPrimaryKey(Integer key) throws FinderException;
    LocalPrice findByLocationAndService(int id_service, int id_prov_location) throws FinderException;
    int getLocationByPrice(int id_price) throws FinderException;
}
