package com.sunflower.ejb.task;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Created by denysburlakov on 03.12.14.
 */
public interface LocalTaskHome extends EJBLocalHome {
    public LocalTask create(String description, String status, int id_group_user, int id_order) throws CreateException;
    com.sunflower.ejb.task.LocalTask findByPrimaryKey(Integer key) throws FinderException;
}
