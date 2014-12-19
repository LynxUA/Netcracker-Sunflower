package com.sunflower.ejb.circuit;

import com.sunflower.ejb.port.LocalPort;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Created by Алексей on 12/14/2014.
 */
public interface LocalCircuitHome extends EJBLocalHome {
    public LocalCircuit findByPrimaryKey(Integer key) throws FinderException;
    public LocalCircuit create(int Id_Port) throws CreateException;
}
