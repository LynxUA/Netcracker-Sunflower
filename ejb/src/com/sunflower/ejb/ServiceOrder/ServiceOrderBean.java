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
    private String status;
    private String scenario;
    private int group_id;
    private int id;

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
            statement.setInt(1, id);
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
        id = (Integer) entityContext.getPrimaryKey();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT STATUS, SCENARIO,ID_GROUP_USER FROM SERVICE_ORDER WHERE ID_ORDER = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("...");
            }
            status = resultSet.getString(1);
            scenario = resultSet.getString(2);
            group_id = resultSet.getInt(3);

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
                    "UPDATE SERVICE_ORDER SET STATUS = ?, SCENARIO = ?, ID_GROUP_USER = ? WHERE ID_ORDER = ?");
            statement.setString(1, status);
            statement.setString(2, scenario);
            statement.setInt(3, group_id);
            statement.setInt(4, id);
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


    public Integer ejbCreate(String status, String scenario, int group_id, int id) throws CreateException {
        try {
            ejbFindByPrimaryKey(id);
            throw new DuplicateKeyException("...");
        } catch (FinderException e) { /*
        Как это ни странно, именно возникновение исключения
        дает нам повод и возможность выполнять дальнейшие
        действия. Поэтому здесь ничего не происходит.
      */}
        this.id = id;
        this.status = status;
        this.scenario = scenario;
        this.group_id = group_id;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            try{
                connection = dataSource.getConnection();
            } catch (SQLException e) {
                throw new EJBException("Ошибка dataSource");
            }
            statement = connection.prepareStatement("INSERT INTO SERVICE_ORDER"
                    + "(ID_ORDER,STATUS,SCENARIO,ID_GROUP_USER) VALUES(?, ?, ?, ?)");
            statement.setInt(1, id);
            statement.setString(2, status);
            statement.setString(3, scenario);
            statement.setInt(4, group_id);
            if (statement.executeUpdate() != 1) {
                throw new CreateException("Insert exception");
            }
            return id;
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

    public void ejbPostCreate(String status, String scenario, int group_id, int id) throws CreateException {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScenario() {
        return scenario;
    }

    public int getUserGroup() {
        return group_id;
    }

    public int getId() {
        return id;
    }

}
