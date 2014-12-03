package com.sunflower.ejb.task;

import javax.ejb.EJBLocalObject;

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
}
