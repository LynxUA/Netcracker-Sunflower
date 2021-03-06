package com.sunflower.ejb.service;

import com.sunflower.ejb.DataSource;
import org.apache.log4j.Logger;

import javax.ejb.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by denysburlakov on 08.12.14.
 */
public class ServiceBean implements EntityBean {
    private int id_service;
    private String name;
    private final static Logger logger = Logger.getLogger(ServiceBean.class);
    private EntityContext entityContext;
    public ServiceBean() {
    }

    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            try {
                connection = DataSource.getDataSource().getConnection();
            }catch(SQLException e)
            {
                logger.error(e.getMessage(), e);
                throw new UnknownError();

            }
            statement = connection.prepareStatement("SELECT ID_SERVICE FROM SERVICE WHERE ID_SERVICE = ?");
            statement.setInt(1, key);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("...");
            }
            return key;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new UnknownError();
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
            statement = connection.prepareStatement("DELETE FROM SERVICE WHERE ID_SERVICE =?");
            statement.setInt(1, id_service);
            if (statement.executeUpdate() < 1) {
                throw new RemoveException("Exception while deleting");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);;
            throw new UnknownError();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new UnknownError();
            }
        }
    }

    public void ejbActivate() throws EJBException {
    }

    public void ejbPassivate() throws EJBException {
    }

    public void ejbLoad() throws EJBException {
        id_service = (Integer) entityContext.getPrimaryKey();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT NAME FROM SERVICE WHERE ID_SERVICE = ?");
            statement.setInt(1, id_service);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException();
            }
            name = resultSet.getString(1);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new UnknownError();
            }
        }
    }

    public void ejbStore() throws EJBException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement(
                    "UPDATE SERVICE SET NAME = ? WHERE ID_SERVICE = ?");
            statement.setString(1, name);
            statement.setInt(2, id_service);
            if (statement.executeUpdate() < 1) {
                throw new NoSuchEntityException();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new UnknownError();
            }
        }
    }

    public Collection ejbFindByProviderLocationId(int id) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT ID_SERVICE FROM PRICE WHERE ID_PROV_LOCATION = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Vector keys = new Vector();
            while (resultSet.next()) {
                Integer id_service = resultSet.getInt(1);
                keys.addElement(id_service);
            }
            return keys;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new UnknownError();
            }
        }

    }

    public int getId_service() {
        return id_service;
    }

    public String getName() {
        return name;
    }

}
