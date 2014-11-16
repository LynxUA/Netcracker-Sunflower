package com.sunflower.ejb;

import javax.ejb.EJBLocalObject;

/**
 * Created by denysburlakov on 16.11.14.
 */
public interface LocalHelloWorld extends EJBLocalObject {
    public String sayHello();
}
