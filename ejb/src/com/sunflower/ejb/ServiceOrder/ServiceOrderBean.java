package com.sunflower.ejb.ServiceOrder;

import com.sunflower.ejb.DataSource;
import com.sunflower.ejb.task.UserWasAssignedException;
import oracle.jdbc.pool.OracleDataSource;

import javax.ejb.*;
import java.sql.*;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by Andriy on 12/3/2014.
 */



public class ServiceOrderBean implements EntityBean {
    private int id_order;
    private int id_status;
    private int id_scenario;
    private String login;
    private int id_price;
    private int id_service_inst;
    private Date so_date;
    float longtitude;
    float latitude;

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
                System.out.println(e.getMessage());
                e.printStackTrace();
                throw new UnknownError();
            }
            statement = connection.prepareStatement("SELECT ID_ORDER FROM SERVICE_ORDER WHERE ID_ORDER = ?");
            statement.setInt(1, key);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("...");
            }
            return Integer.valueOf(key);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
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
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
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
            statement = connection.prepareStatement("SELECT ID_STATUS, ID_SCENARIO,LOGIN, ID_PRICE, ID_SERVICE_INST, SO_DATE, LONGTITUDE, LATITUDE FROM SERVICE_ORDER WHERE ID_ORDER = ?");
            statement.setInt(1, id_order);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("...");
            }
            id_status = resultSet.getInt(1);
            id_scenario = resultSet.getInt(2);
            login = resultSet.getString(3);
            id_price = resultSet.getInt(4);
            id_service_inst = resultSet.getInt(5);
            so_date = resultSet.getDate(6);
            longtitude = resultSet.getFloat(7);
            latitude = resultSet.getFloat(8);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public void ejbStore() throws EJBException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();

            statement = connection.prepareStatement(
                    "UPDATE SERVICE_ORDER SET ID_STATUS = ?, ID_SCENARIO = ?, LOGIN = ?, ID_PRICE = ?, ID_SERVICE_INST = ?, SO_DATE = ?, LONGTITUDE = ?, LATITUDE = ? WHERE ID_ORDER = ?");
            statement.setInt(1, id_status);
            statement.setInt(2, id_scenario);
            statement.setString(3, login);
            statement.setInt(4, id_price);
            statement.setInt(5, id_service_inst);
            statement.setDate(6, so_date);
            statement.setFloat(7, longtitude);
            statement.setFloat(8, latitude);
            statement.setInt(9, id_order);
            if (statement.executeUpdate() < 1) {
                throw new NoSuchEntityException("...");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
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

    public Integer ejbCreate(int id_scenario, String login, int id_price, int id_service_inst, float longtitude, float latitude) throws CreateException {
        this.id_status = 1; //SOStatuses.Entering
        this.id_scenario = id_scenario;
        this.login = login;
        this.id_price = id_price;
        this.id_service_inst = id_service_inst;
        this.so_date = new Date((new java.util.Date()).getTime());
        this.longtitude = longtitude;
        this.latitude = latitude;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            try{
                connection = dataSource.getConnection();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                throw new UnknownError();
            }
            statement = connection.prepareStatement("INSERT INTO SERVICE_ORDER"
                    + "(ID_STATUS, ID_SCENARIO,LOGIN, ID_PRICE, ID_SERVICE_INST, SO_DATE, LONGTITUDE, LATITUDE) VALUES(?, ?, ?, ?, ?, ?, ?, ?)", new String[]{"ID_ORDER"});
            statement.setInt(1, id_status);
            statement.setInt(2, id_scenario);
            statement.setString(3, login);
            statement.setInt(4, id_price);
            statement.setInt(5, id_service_inst);
            statement.setDate(6, so_date);
            statement.setFloat(7, longtitude);
            statement.setFloat(8, latitude);

            if (statement.executeUpdate() != 1) {
                throw new CreateException("Insert exception");
            }
            rs = statement.getGeneratedKeys();
            while(rs.next()){
                id_order = rs.getInt(1);
            }
            return id_order;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public void ejbPostCreate(int id_scenario, String login, int id_order, int id_service_inst, float longtitude, float latitude) throws CreateException {

    }



    public Collection ejbHomeGetOrdersByLogin(String login, int from, int to) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT A,B,C, D, E, F\n" +
                    "FROM (SELECT ID_ORDER AS A, SO_STATUS.NAME AS B, SCENARIO.NAME AS C, SO_DATE AS D, LONGTITUDE AS E, LATITUDE AS F, ROWNUM R\n" +
                    "FROM (SERVICE_ORDER JOIN SO_STATUS ON SERVICE_ORDER.ID_STATUS = SO_STATUS.ID_STATUS) JOIN SCENARIO ON SERVICE_ORDER.ID_SCENARIO = SCENARIO.ID_SCENARIO\n" +
                    "WHERE LOGIN = ? )\n" +
                    "WHERE R >= ? AND R <=?");
            statement.setString(1, login);
            statement.setInt(2, from);
            statement.setInt(3, to);
            ResultSet resultSet = statement.executeQuery();
            Vector<SOWrapper> orders = new Vector<SOWrapper>();
            while (resultSet.next()) {
                orders.addElement(new SOWrapper(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4), resultSet.getFloat(5), resultSet.getFloat(6)));
            }
            return orders;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public int ejbHomeGetNumberOfOrdersByLogin(String login) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT COUNT(ID_ORDER)\n" +
                    "FROM SERVICE_ORDER\n" +
                    "WHERE LOGIN = ?");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new FinderException();
            }
            return resultSet.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public void ejbHomeCancelOrder(int id_order) throws UserWasAssignedException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT LOGIN FROM TASK WHERE ID_ORDER = ?");
            statement.setInt(1, id_order);
            ResultSet rs = statement.executeQuery();
            rs.next();
            rs.getString(1);
            if (!rs.wasNull()) {
                throw new UserWasAssignedException();
            }

            statement = connection.prepareStatement("DELETE FROM TASK WHERE ID_ORDER = ?");
            statement.setInt(1, id_order);
            if (statement.executeUpdate() < 1) {
                throw new NoSuchEntityException("...");
            }
            statement = connection.prepareStatement(
                    "UPDATE SERVICE_ORDER SET ID_STATUS = 2 WHERE ID_ORDER = ?");
            statement.setInt(1, id_order);
            if (statement.executeUpdate() < 1) {
                throw new NoSuchEntityException("...");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }
}
