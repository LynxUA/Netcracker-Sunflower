package com.sunflower.ejb.usergroup;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Created by Den on 02.12.2014.
 */
//Local home
public interface LocalUserGroupHome extends EJBLocalHome {
    com.sunflower.ejb.usergroup.LocalUserGroup findByPrimaryKey(Integer key) throws FinderException;
    public LocalUserGroup create(String position, String groupName) throws CreateException;
}
