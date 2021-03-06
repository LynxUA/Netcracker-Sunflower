package com.sunflower.ejb.usergroup;

import com.sunflower.ejb.DataSource;
import org.apache.log4j.Logger;

import javax.ejb.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Den on 02.12.2014.
 */
//Entity bean
public class UserGroupBean implements EntityBean {

    private Connection connection;
    private Integer primaryKey;
    private String position;
    private String groupName;
    private EntityContext entityContext;
    private final static Logger logger = Logger.getLogger(UserGroupBean.class);

    public UserGroupBean() {
    }

    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {

        PreparedStatement st = null;
        ResultSet rsKey = null;

        try {
            st = connection.prepareStatement("SELECT ID_GROUP_USER FROM USER_GROUP WHERE ID_GROUP_USER = ?");
            st.setInt(1,key);
            rsKey = st.executeQuery();

            while (rsKey.next()){
                primaryKey = rsKey.getInt(1);
                if(primaryKey != rsKey.getInt(1)) throw new FinderException("WRONG PK!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        }

        return primaryKey;
    }

    public void setEntityContext(EntityContext entityContext) throws EJBException {
        this.entityContext = entityContext;
        if(DataSource.getDataSource()==null){
            DataSource.setDataSource();
        }
    }

    public void unsetEntityContext() throws EJBException {
        this.entityContext = null;
    }

    public void ejbRemove() throws RemoveException, EJBException {

        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("DELETE FROM user_group WHERE id_group_user = ?");
            ps.setInt(1, primaryKey);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public void ejbActivate() throws EJBException {
        //Empty
    }

    public void ejbPassivate() throws EJBException {
        //Empty
    }

    //Loads stored data to bean from DB
    public void ejbLoad() throws EJBException {
        primaryKey = (Integer)entityContext.getPrimaryKey();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT position, group_name FROM user_group WHERE id_group_user = ?");
            ps.setInt(1, primaryKey);
            rs = ps.executeQuery();

            while (rs.next()){
                position = rs.getString(1);
                groupName = rs.getString(2);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }

    }

    //Saves current data to DB
    public void ejbStore() throws EJBException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("UPDATE user_group SET position = ?, group_name = ? WHERE id_group_user=?");
            ps.setString(1, position);
            ps.setString(2, groupName);
            ps.setInt(3,primaryKey);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }

    }


    public String getPosition() {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT position FROM user_group WHERE id_group_user = ?");
            ps.setInt(1, primaryKey);
            rs = ps.executeQuery();

            while (rs.next()){
                position = rs.getString(1);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }


        return position;
    }


    public void setPosition(String position) {

    }


    public String getGroupName() {
        return null;
    }


    public void setGroupName() {

    }


    public List getColumnName() {

        ResultSet rsColumns = null;
        ArrayList<String> colNames = new ArrayList<String>();
        try {
            DatabaseMetaData meta = connection.getMetaData();
            rsColumns = meta.getColumns(null, null, "USER_GROUP", null);
            ResultSetMetaData rsmd = rsColumns.getMetaData();
            rsColumns.next();

            while(rsColumns.next()){
                String columnName = rsColumns.getString("COLUMN_NAME");
                colNames.add(columnName);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            ;
            throw new UnknownError();
        }finally {
            DataSource.closeConnection(connection);
        }

        return colNames;
    }


    public Integer ejbCreate(String position, String groupName) throws CreateException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        this.position = position;
        this.groupName = groupName;

        try {

            String query = "INSERT INTO user_group (position, group_name) VALUES (?,?)";

            ps = connection.prepareStatement(query, new String[]{"ID_GROUP_USER"});
            ps.setString(1,position);
            ps.setString(2,groupName);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();

            while(rs.next()){
                primaryKey = rs.getInt(1);
            }


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);

        }

        return primaryKey;
    }

    public void ejbPostCreate(String position, String groupName) throws CreateException {

    }
}
