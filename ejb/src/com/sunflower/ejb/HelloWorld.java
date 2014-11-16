package com.sunflower.ejb;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Created by denysburlakov on 16.11.14.
 */
public interface HelloWorld extends EJBObject {
    public String sayHello() throws RemoteException;
}
