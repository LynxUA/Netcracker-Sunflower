package com.sunflower.ejb.device;

import com.sunflower.ejb.DataSource;
import org.apache.log4j.Logger;

import javax.ejb.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by denysburlakov on 08.12.14.
 */
public class DeviceBean implements EntityBean {
    private int id_device;
    private String name;
    private int number_of_ports;
    private int id_prov_location;
    private final static Logger logger = Logger.getLogger(DeviceBean.class);
    private EntityContext entityContext;
    public DeviceBean() {
    }

    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT ID_DEVICE FROM DEVICE WHERE ID_DEVICE = ?");
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
            DataSource.closeConnection(connection);
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
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("DELETE FROM DEVICE WHERE ID_DEVICE = ?");
            statement.setInt(1, id_device);
            if (statement.executeUpdate() < 1) {
                throw new RemoveException("Exception while deleting");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public void ejbActivate() throws EJBException {

    }

    public void ejbPassivate() throws EJBException {

    }

    public void ejbLoad() throws EJBException {
        id_device = (Integer) entityContext.getPrimaryKey();
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT ID_DEVICE,NAME,NUMBER_OF_PORTS, ID_PROV_LOCATION FROM DEVICE WHERE ID_DEVICE = ?");
            statement.setInt(1, id_device);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("...");
            }
            name = resultSet.getString("NAME");
            number_of_ports = resultSet.getInt("NUMBER_OF_PORTS");
            id_prov_location = resultSet.getInt(" ID_PROV_LOCATION");

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new EJBException("Ошибка SELECT");
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public void ejbStore() throws EJBException {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("UPDATE DEVICE SET NAME  = ?, NUMBER_OF_PORTS = ?, ID_PROV_LOCATION = ? WHERE ID_DEVICE=?");

            statement.setString(1, name);

            statement.setInt(2, number_of_ports);


            statement.setInt(4, id_prov_location);
            statement.setInt(4, id_device);



            if (statement.executeUpdate() < 1) {
                System.out.println("bad statement");
                throw new NoSuchEntityException("...");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new EJBException("Ошибка UPDATE");
        } finally {
            DataSource.closeConnection(connection);
        }
    }



}
