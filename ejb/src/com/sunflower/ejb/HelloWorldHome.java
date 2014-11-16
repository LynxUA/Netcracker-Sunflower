package com.sunflower.ejb;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

/**
 * Created by denysburlakov on 16.11.14.
 */
public interface HelloWorldHome extends EJBHome {
    com.sunflower.ejb.HelloWorld create() throws RemoteException, CreateException;
}
