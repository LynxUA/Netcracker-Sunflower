package com.sunflower.ejb.port;




import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;


/**
 * Created by Alexey on 12/11/2014.
 */
public interface LocalPortHome extends EJBLocalHome {

    public LocalPort findByPrimaryKey(Integer key) throws FinderException;
    public LocalPort create(int status, int Id_Device) throws CreateException;
    public int getPortIdByInstance(int id_service_inst);
}
