package com.sunflower.ejb.task;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import java.util.Collection;

/**
 * Created by denysburlakov on 03.12.14.
 */
public interface LocalTaskHome extends EJBLocalHome {
    public LocalTask create(String description, int id_group_user, int id_order, String login) throws CreateException;
    public LocalTask findByPrimaryKey(Integer key) throws FinderException;
    public Collection getTasksByEngineer(int id_group_user);
    //public LocalTask findIncompleteTask() throws FinderException;

 }
