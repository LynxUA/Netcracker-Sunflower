package com.sunflower.ejb.servicelocation;

import com.sunflower.ejb.DataSource;

import javax.ejb.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by denysburlakov on 05.12.14.
 */
public class ServiceLocationBean implements EntityBean {
    private int id_serv_location;
    private String location;
    private int id_prov_location;
    private int id_order;
    private EntityContext entityContext;

    public ServiceLocationBean() {
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
            statement = connection.prepareStatement("SELECT ID_SERV_LOCATION FROM SERVICE_LOCATION WHERE ID_SERV_LOCATION = ?");
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
            statement = connection.prepareStatement("DELETE FROM SERVICE_LOCATION WHERE ID_SERV_LOCATION = ?");
            statement.setInt(1, id_serv_location);
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
        id_serv_location = (Integer) entityContext.getPrimaryKey();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT LOCATION, ID_PROV_LOCATION, ID_ORDER FROM SERVICE_LOCATION WHERE ID_SERV_LOCATION = ?");
            statement.setInt(1, id_serv_location);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("...");
            }
            location = resultSet.getString(1);
            id_prov_location = resultSet.getInt(2);
            id_order = resultSet.getInt(3);

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
                    "UPDATE SERVICE_LOCATION SET LOCATION = ?, ID_PROV_LOCATION = ?, ID_ORDER = ? WHERE ID_SERV_LOCATION = ?");
            statement.setString(1, location);
            statement.setInt(2, id_prov_location);
            statement.setInt(3, id_order);
            statement.setInt(4, id_serv_location);
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

    public Integer ejbCreate(String location, int id_prov_location, int id_order) throws CreateException {
        this.location = location;
        this.id_prov_location = id_prov_location;
        this.id_order = id_order;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            try{
                connection = DataSource.getDataSource().getConnection();
            } catch (SQLException e) {
                throw new EJBException("Ошибка dataSource");
            }
            statement = connection.prepareStatement("INSERT INTO SERVICE_LOCATION"
                    + "(LOCATION, ID_PROV_LOCATION, ID_ORDER) VALUES(?, ?, ?)");
            statement.setString(1, location);
            statement.setInt(2, id_prov_location);
            statement.setInt(3, id_order);
            if (statement.executeUpdate() != 1) {
                throw new CreateException("Insert exception");
            }
            id_serv_location = statement.getGeneratedKeys().getInt(1);
            return id_serv_location;
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

    public void ejbPostCreate(String location, int id_prov_location, int id_order) throws CreateException {

    }

    public int getId_prov_location() {
        return id_prov_location;
    }

    public int getId_order() {
        return id_order;
    }

    public String getLocation() {
        return location;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setId_prov_location(int id_prov_location) {
        this.id_prov_location = id_prov_location;
    }
}