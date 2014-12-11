package com.sunflower.ejb.ServiceOrder;

import oracle.jdbc.pool.OracleDataSource;

import javax.ejb.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Andriy on 12/3/2014.
 */
public class ServiceOrderBean implements EntityBean {
    private int id_status;
    private int id_scenario;
    private String login;
    private int id_order;
    private int id_price;

    private EntityContext entityContext;
    private OracleDataSource dataSource;


    public ServiceOrderBean() {

    }

    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            try {
                connection = dataSource.getConnection();
            }catch(SQLException e)
            {
                System.out.println(e.getErrorCode());
                System.out.println("something wrong with connection");

            }
            statement = connection.prepareStatement("SELECT ID_ORDER FROM SERVICE_ORDER WHERE ID_ORDER = ?");
            statement.setInt(1, key);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("...");
            }
            return Integer.valueOf(key);
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
        try {
            dataSource = new OracleDataSource();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataSource.setURL("jdbc:oracle:thin:@//194.44.143.139:1521/XE");
        dataSource.setUser("sunflower");
        dataSource.setPassword("sunflower14");
    }

    public void unsetEntityContext() throws EJBException {
        this.entityContext = null;
    }

    public void ejbRemove() throws RemoveException, EJBException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT ID_ORDER FROM SERVICE_ORDER WHERE ID_ORDER = ?");
            statement.setInt(1, id_order);
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
        id_order = (Integer) entityContext.getPrimaryKey();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT ID_STATUS, ID_SCENARIO,LOGIN, ID_PRICE ID_SERV_LOCATION FROM SERVICE_ORDER WHERE ID_ORDER = ?");
            statement.setInt(1, id_order);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("...");
            }
            id_status = resultSet.getInt(1);
            id_scenario = resultSet.getInt(2);
            login = resultSet.getString(3);
            id_price = resultSet.getInt(4);

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
            connection = dataSource.getConnection();

            statement = connection.prepareStatement(
                    "UPDATE SERVICE_ORDER SET ID_STATUS = ?, ID_SCENARIO = ?, LOGIN = ?, ID_PRICE = ? WHERE ID_ORDER = ?");
            statement.setInt(1, id_status);
            statement.setInt(2, id_scenario);
            statement.setString(3, login);
            statement.setInt(4, id_price);
            statement.setInt(5, id_order);
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

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public int getId_scenario() {
        return id_scenario;
    }

    public void setId_scenario(int id_scenario) { this.id_scenario = id_scenario; }

    public String getUserLogin() {
        return login;
    }


    public int getId_order() {
        return id_order;
    }
    public int getId_price() {return  id_price; }

    public Integer ejbCreate(int id_status, int id_scenario, String login, int id_price) throws CreateException {
        this.id_status = id_status;
        this.id_scenario = id_scenario;
        this.login = login;
        this.id_price = id_price;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            try{
                connection = dataSource.getConnection();
            } catch (SQLException e) {
                throw new EJBException("Ошибка dataSource");
            }
            statement = connection.prepareStatement("INSERT INTO SERVICE_ORDER"
                    + "(ID_STATUS, ID_SCENARIO,LOGIN, ID_PRICE) VALUES(?, ?, ?, ?)", new String[]{"ID_ORDER"});
            statement.setInt(1, id_status);
            statement.setInt(2, id_scenario);
            statement.setString(3, login);
            statement.setInt(4, id_price);

            if (statement.executeUpdate() != 1) {
                System.out.println("Fail");
                throw new CreateException("Insert exception");
            }
            rs = statement.getGeneratedKeys();
            while(rs.next()){
                id_order = rs.getInt(1);
            }
            return id_order;
        } catch (SQLException e) {
            //throw new EJBException("Ошибка INSERT");

            System.out.println(e.getMessage());
            e.printStackTrace();
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

    public void ejbPostCreate(int id_status, int id_scenario, String login, int id_order) throws CreateException {

    }
}
