package com.sunflower.ejb.usergroup;

import javax.ejb.EJBLocalObject;
import java.util.List;

/**
 * Created by Den on 02.12.2014.
 */
//Component interface

public interface LocalUserGroup extends EJBLocalObject {

    public String getPosition();
    public void setPosition(String position);
    public String getGroupName();
    public void setGroupName();
    public List getColumnName();
}
