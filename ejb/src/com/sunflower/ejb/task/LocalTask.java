package com.sunflower.ejb.task;

import javax.ejb.EJBLocalObject;
import javax.ejb.FinderException;

/**
 * Created by denysburlakov on 03.12.14.
 */
public interface LocalTask extends EJBLocalObject {
    public int getId_group_user();

    public int getId_task();

    public String getDescription();

    public void setDescription(String status);

    public String getStatus();

    public void setStatus(String status);

    public int getId_order();

    public String getLogin();
   // public void changeStatus(Integer key,String status) throws FinderException;
}
