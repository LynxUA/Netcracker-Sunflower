package com.sunflower.ejb;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

/**
 * Created by denysburlakov on 16.11.14.
 */
public interface LocalHelloWorldHome extends EJBLocalHome {
    com.sunflower.ejb.LocalHelloWorld create() throws CreateException;
}
