package com.sunflower.ejb.task;

import com.sunflower.ejb.DataSource;

import javax.ejb.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by denysburlakov on 03.12.14.
 */

public class TaskBean implements EntityBean {
    private int id_task;
    private String description;
    private String status;
    private int id_group_user;
    private int id_order;

    private EntityContext entityContext;
    public TaskBean() {

    }

    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            try {
                connection = DataSource.getDataSource().getConnection();
            }catch(SQLException e)
            {
                System.out.println(e.getErrorCode());
                System.out.println("something wrong with connection");

            }
            statement = connection.prepareStatement("SELECT ID_TASK FROM TASK WHERE ID_TASK = ?");
            statement.setInt(1, key);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("...");
            }
            return key;
        } catch (SQLException e) {
            throw new EJBException("SELECT exception in ejbFindByPrimaryKey");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeStatus(Integer key,String status) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            try {
                connection = DataSource.getDataSource().getConnection();
            }catch(SQLException e)
            {
                System.out.println(e.getErrorCode());
                System.out.println("something wrong with connection");

            }
            statement = connection.prepareStatement("UPDATE TASK  SET STATUS=? WHERE ID_TASK = ?");
            statement.setString(1,status);
            statement.setInt(2, key);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("...");
            }

        } catch (SQLException e) {
            throw new EJBException("SELECT exception in ejbCompleteTask");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("DELETE FROM TASK WHERE ID_TASK = ?");
            statement.setInt(1, id_task);
            if (statement.executeUpdate() < 1) {
                throw new RemoveException("Exception while deleting");
            }
        } catch (SQLException e) {
            throw new EJBException("DELETE exception");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void ejbActivate() throws EJBException {
        //Do not fill
    }

    public void ejbPassivate() throws EJBException {
        //Do not fill
    }

    public void ejbLoad() throws EJBException {
        id_task = (Integer) entityContext.getPrimaryKey();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT DESCRIPTION, STATUS, ID_GROUP_USER, ID_ORDER FROM TASK WHERE ID_TASK = ?");
            statement.setInt(1, id_task);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("...");
            }
            description = resultSet.getString(1);
            status = resultSet.getString(2);
            id_group_user = resultSet.getInt(3);
            id_order = resultSet.getInt(4);

        } catch (SQLException e) {
            throw new EJBException("Ошибка SELECT");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void ejbStore() throws EJBException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement(
                    "UPDATE TASK SET DESCRIPTION = ?, STATUS = ?, ID_GROUP_USER = ?, ID_ORDER = ? WHERE ID_TASK = ?");
            statement.setString(1, description);
            statement.setString(2, status);
            statement.setInt(3, id_group_user);
            statement.setInt(4, id_order);
            statement.setInt(5, id_task);
            if (statement.executeUpdate() < 1) {
                throw new NoSuchEntityException("...");
            }
        } catch (SQLException e) {
            throw new EJBException("Ошибка UPDATE");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Integer ejbCreate(String description, String status, int id_group_user, int id_order) throws CreateException {
        this.description = description;
        this.status = status;
        this.id_group_user = id_group_user;
        this.id_order = id_order;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            try{
                connection = DataSource.getDataSource().getConnection();
            } catch (SQLException e) {
                throw new EJBException("Ошибка dataSource");
            }
            statement = connection.prepareStatement("INSERT INTO TASK"
                    + "(DESCRIPTION, STATUS, ID_GROUP_USER, ID_ORDER) VALUES(?, ?, ?, ?)", new String[]{"ID_SERV_LOCATION"});
            statement.setString(1, description);
            statement.setString(2, status);
            statement.setInt(3, id_group_user);
            statement.setInt(4, id_order);
            if (statement.executeUpdate() != 1) {
                throw new CreateException("Insert exception");
            }
            id_task = statement.getGeneratedKeys().getInt(1);
            return id_task;
        } catch (SQLException e) {
            //throw new EJBException("Ошибка INSERT");
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void ejbPostCreate(String description, String status, int id_group_user, int id_order) throws CreateException {

    }
    public Integer ejbFindIncompleteTask() throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;
        String status="Incomplete";
        int key=0;
        try {
            try {
                connection = DataSource.getDataSource().getConnection();
            }catch(SQLException e)
            {
                System.out.println(e.getErrorCode());
                System.out.println("something wrong with connection");

            }
            statement = connection.prepareStatement("SELECT MIN(ID_TASK) FROM TASK WHERE STATUS = ?");
            statement.setString(1, status);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("...");
            }
            return resultSet.getInt("MIN(ID_TASK)");
        } catch (SQLException e) {
            throw new EJBException("SELECT exception in ejbFindIncomplete");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getId_group_user() {
        return id_group_user;
    }

    public int getId_task() {
        return id_task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getId_order() {
        return id_order;
    }


}
