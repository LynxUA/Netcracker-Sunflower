package com.sunflower.ejb.user;



import com.sunflower.ejb.DataSource;
import com.sunflower.ejb.ServiceOrder.SOWrapper;
import oracle.jdbc.pool.OracleDataSource;

import javax.ejb.*;
import javax.naming.Context;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Created by denysburlakov on 30.11.14.
 */

public class UserBean implements EntityBean {
    private String login;
    private String email;
    private String name;
    private String surname;
    private String password;

    private int group;

    private EntityContext entityContext;
    public UserBean() {
    }

    public String ejbFindByPrimaryKey(String key) throws FinderException {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT LOGIN FROM SUN_USER WHERE LOGIN = ?");
            statement.setString(1, key);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("...");
            }
            return key;
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
            statement = connection.prepareStatement("DELETE FROM SUN_USER WHERE LOGIN = ?");
            statement.setString(1, login);
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
        login = (String) entityContext.getPrimaryKey();
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT EMAIL, NAME, SURNAME, PASSWORD, ID_GROUP_USER FROM SUN_USER WHERE LOGIN = ?");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("...");
            }
            email = resultSet.getString(1);
            name = resultSet.getString(2);
            surname = resultSet.getString(3);
            password = resultSet.getString(4);
            group = resultSet.getInt(5);


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
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();

            statement = connection.prepareStatement("UPDATE SUN_USER SET EMAIL = ?, NAME = ?, SURNAME = ?, PASSWORD = ?, ID_GROUP_USER = ? WHERE LOGIN = ?");

            statement.setString(1, email);
            statement.setString(2, name);
            statement.setString(3, surname);
            statement.setString(4, password);
            statement.setInt(5, group);
            statement.setString(6, login);

            if (statement.executeUpdate() < 1) {
                throw new NoSuchEntityException("No such entity");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public String ejbCreate(String login, String email, String name, String surname, String password, int id) throws CreateException{
        try {
            ejbFindByPrimaryKey(login);
            throw new DuplicateKeyException("...");
        } catch (FinderException e) {
            //Everything is ok
        }
        this.login = login;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.group = id;
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();

            statement = connection.prepareStatement("INSERT INTO SUN_USER"
                    + "(LOGIN, EMAIL, NAME, SURNAME, PASSWORD, ID_GROUP_USER) VALUES(?, ?, ?, ?, ?, ?)");

            statement.setString(1, login);
            statement.setString(2, email);
            statement.setString(3, name);
            statement.setString(4, surname);
            statement.setString(5, password);
            statement.setInt(6, id);

            if (statement.executeUpdate() != 1) {
                throw new CreateException("Insert exception");
            }
            return login;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public void ejbPostCreate(String login, String email, String name, String surname, String password, int id) throws CreateException {

    }

    public String ejbFindUser(String login, String password) throws FinderException, BadPasswordException {
        ejbFindByPrimaryKey(login);
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();

            statement = connection.prepareStatement("SELECT LOGIN FROM SUN_USER WHERE (LOGIN = ?) AND (PASSWORD = ?)");

            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                throw new BadPasswordException();
            }

            return login;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public String ejbFindUser(String login) throws FinderException, BadPasswordException {
        ejbFindByPrimaryKey(login);
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();

            statement = connection.prepareStatement("SELECT LOGIN FROM SUN_USER WHERE (LOGIN = ?)");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new FinderException();
            }
            return login;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }


    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGroup() {
        return group;
    }

    public Collection ejbFindСustomers() throws FinderException {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT LOGIN FROM SUN_USER WHERE ID_GROUP_USER = 1");
            ResultSet resultSet = statement.executeQuery();
            ArrayList keys = new ArrayList();
            while (resultSet.next()) {
                String id_order = resultSet.getString(1);
                keys.add(id_order);
            }
            return keys;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
//
//    }
    }

    public int ejbHomeGetNumberOfCustomers() throws FinderException {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT COUNT(LOGIN)\n" +
                    "FROM SUN_USER\n" +
                    "WHERE ID_GROUP_USER = 1");
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

    public Collection ejbHomeGetCustomers(int from, int to) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT A,B,C,D\n" +
                    "FROM (SELECT LOGIN AS A, EMAIL AS B, NAME AS C, SURNAME AS D, ROWNUM R FROM SUN_USER WHERE ID_GROUP_USER = 1 )\n" +
                    "WHERE R >= ? AND R <=?");
            statement.setInt(1, from);
            statement.setInt(2, to);
            ResultSet resultSet = statement.executeQuery();
            Vector<CustomerWrapper> customers = new Vector<CustomerWrapper>();
            while (resultSet.next()) {
                customers.addElement(new CustomerWrapper(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),resultSet.getString(4)));
            }
            return customers;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }
}
