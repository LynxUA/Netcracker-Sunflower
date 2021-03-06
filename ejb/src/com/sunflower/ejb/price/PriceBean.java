package com.sunflower.ejb.price;

import com.sunflower.ejb.DataSource;
import org.apache.log4j.Logger;

import javax.ejb.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by denysburlakov on 08.12.14.
 */
public class PriceBean implements EntityBean {
    private int id_price;
    private float price_of_service;
    private float price_of_location;
    private int id_service;
    private int id_prov_location;
    private final static Logger logger = Logger.getLogger(PriceBean.class);
    private EntityContext entityContext;

    public PriceBean() {
    }

    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        Connection connection = null;
        PreparedStatement statement ;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT ID_PRICE FROM PRICE WHERE ID_PRICE = ?");
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
        if (DataSource.getDataSource() == null) {
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
            statement = connection.prepareStatement("DELETE FROM PRICE WHERE ID_PRICE = ?");
            statement.setInt(1, id_price);
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
        id_price = (Integer) entityContext.getPrimaryKey();
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT PRICE_OF_SERVICE, PRICE_OF_LOCATION, ID_SERVICE, ID_PROV_LOCATION FROM PRICE WHERE ID_PRICE = ?");
            statement.setInt(1, id_price);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("...");
            }
            price_of_service = resultSet.getFloat(1);
            price_of_location = resultSet.getFloat(2);
            id_service = resultSet.getInt(3);
            id_prov_location = resultSet.getInt(4);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public void ejbStore() throws EJBException {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("UPDATE PRICE SET PRICE_OF_SERVICE  = ?, PRICE_OF_LOCATION = ?, ID_SERVICE = ?, ID_PROV_LOCATION = ? WHERE ID_PRICE=?");

            statement.setFloat(1, price_of_service);

            statement.setFloat(2, price_of_location);

            statement.setInt(3, id_service);
            statement.setInt(4, id_prov_location);
            statement.setInt(5, id_price);


            if (statement.executeUpdate() < 1) {
                throw new NoSuchEntityException("...");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public Integer ejbFindByLocationAndService(int id_service, int id_prov_location) throws FinderException {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT ID_PRICE FROM PRICE WHERE ID_SERVICE = ? AND ID_PROV_LOCATION = ?");
            statement.setInt(1, id_service);
            statement.setInt(2, id_prov_location);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("...");
            }
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public int getId_price() {
        return id_price;
    }

    public float getPrice_of_service() {
        return price_of_service;
    }


    public float getPrice_of_location() {
        return price_of_location;
    }


    public int getId_service() {
        return id_service;
    }


    public int getId_prov_location() {
        return id_prov_location;
    }

    public int ejbHomeGetLocationByPrice(int id_price) throws FinderException {
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
            statement = connection.prepareStatement("SELECT ID_PROV_LOCATION FROM PRICE WHERE ID_PRICE = ?");
            statement.setInt(1, id_price);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("...");
            }
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new EJBException("SELECT exception in ejbFindByPrimaryKey");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public Collection ejbHomeGetServicePriceByLoc(String location) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<PriceCatalog> catalogList = new ArrayList<PriceCatalog>();

        try {
            try {
                connection = DataSource.getDataSource().getConnection();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);

            }

            statement = connection.prepareStatement("SELECT NAME, PRICE_OF_SERVICE, PRICE_OF_LOCATION " +
                    "FROM PRICE INNER JOIN SERVICE ON PRICE.ID_SERVICE = SERVICE.ID_SERVICE WHERE ID_PROV_LOCATION = " +
                    "(SELECT ID_PROV_LOCATION FROM PROVIDER_LOCATION WHERE LOCATION = ?)");
            statement.setString(1, location);
            rs = statement.executeQuery();

            while(rs.next()){
                PriceCatalog pc = new PriceCatalog(rs);
                catalogList.add(pc);
            }

        } catch (SQLException e) {

            logger.error(e.getMessage(), e);
            throw new EJBException("SELECT exception");

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }

        return catalogList;
    }
}
