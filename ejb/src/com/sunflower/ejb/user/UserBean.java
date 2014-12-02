package com.sunflower.ejb.user;



import oracle.jdbc.pool.OracleDataSource;

import javax.ejb.*;
import javax.naming.Context;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Created by denysburlakov on 30.11.14.
 */

public class UserBean implements EntityBean {
    public String login;
    public String email;
    public String name;
    public String surname;
    public String password;
    public int group;

    private EntityContext entityContext;
    private OracleDataSource dataSource;
    public UserBean() {
    }

    public String ejbFindByPrimaryKey(String key) throws FinderException {
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
            statement = connection.prepareStatement("SELECT LOGIN FROM SUN_USER WHERE LOGIN = ?");
            statement.setString(1, key);
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
            statement = connection.prepareStatement("DELETE FROM SUN_USER WHERE LOGIN = ?");
            statement.setString(1, login);
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
        login = (String) entityContext.getPrimaryKey();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT EMAIL, NAME, SURNAME, PASSWORD FROM SUN_USER WHERE LOGIN = ?");
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
                    "UPDATE SUN_USER SET EMAIL = ?, NAME = ?, SURNAME = ?, PASSWORD = ?, ID_GROUP_USER = ? WHERE LOGIN = ?");
            statement.setString(1, email);
            statement.setString(2, name);
            statement.setString(3, surname);
            statement.setString(4, password);
            statement.setInt(5, group);
            statement.setString(6, login);
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

    public String ejbCreate(String login, String email, String name, String surname, String password, int id) throws CreateException{
        try {
            ejbFindByPrimaryKey(login);
            throw new DuplicateKeyException("...");
        } catch (FinderException e) { /*
        Как это ни странно, именно возникновение исключения
        дает нам повод и возможность выполнять дальнейшие
        действия. Поэтому здесь ничего не происходит.
      */}
        this.login = login;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.group = id;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            try{
                connection = dataSource.getConnection();
            } catch (SQLException e) {
                throw new EJBException("Ошибка dataSource");
            }
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

    public void ejbPostCreate(String login, String email, String name, String surname, String password, int id) throws CreateException {

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
}
