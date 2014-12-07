package com.sunflower.ejb.ProviderLocation;

/**
 * Created by Алексей on 12/6/2014.
 */



import oracle.jdbc.pool.OracleDataSource;

import javax.ejb.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProviderLocationBean implements EntityBean {
    private int Id_Prov_Location;
    private String Location;
    private int Num_of_services;
    private int Id_order;

    private EntityContext entityContext;
    private OracleDataSource dataSource;
    public ProviderLocationBean() {
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
            statement = connection.prepareStatement("SELECT ID_PROV_LOCATON FROM PROVIDER_LOCATION WHERE ID_PROV_LOCATON =?");
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
            statement = connection.prepareStatement("DELETE FROM SUN_USER WHERE ID_PROV_LOCATON =?");
            statement.setInt(1, Id_Prov_Location);
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
        Id_Prov_Location = (Integer) entityContext.getPrimaryKey();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT LOCATION, NUM_OF_SERVICES, ID_ORDER FROM PROVIDER_LOCATION WHERE ID_PROV_LOCATION = ?");
            statement.setInt(1, Id_Prov_Location);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("...");
            }
            Location = resultSet.getString(1);
            Num_of_services = resultSet.getInt(2);
            Id_order = resultSet.getInt(3);

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
            statement = connection.prepareStatement("UPDATE PROVIDER_LOCATION SET LOCATION  = ?, NUM_OF_SERVICES = ?, ID_ORDER = ? WHERE ID_PROV_LOCATION=?");

            statement.setString(1, Location);

            statement.setInt(2, Num_of_services);

            statement.setInt(3, Id_order);

            statement.setInt(4, Id_Prov_Location);

           
            if (statement.executeUpdate() < 1) {
                System.out.println("bad statement");
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

    public Integer ejbCreate( String Location, int Num_of_services, int Id_order) throws CreateException{
        try {
            ejbFindByPrimaryKey(Id_Prov_Location);
            throw new DuplicateKeyException("...");
        } catch (FinderException e) { /*
        Как это ни странно, именно возникновение исключения
        дает нам повод и возможность выполнять дальнейшие
        действия. Поэтому здесь ничего не происходит.
      */}

        this.Location = Location;
        this.Num_of_services = Num_of_services;
        this.Id_order = Id_order;

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            try{
                connection = dataSource.getConnection();
            } catch (SQLException e) {
                throw new EJBException("Ошибка dataSource");
            }
            statement = connection.prepareStatement("INSERT INTO PROVIDER_LOCATION"
                    + "( LOCATION, NUM_OF_SERVICES, ID_ORDER) VALUES(?, ?, ?)");

            statement.setString(1, Location);
            statement.setInt(2, Num_of_services);
            statement.setInt(3, Id_order);

            if (statement.executeUpdate() != 1) {
                throw new CreateException("Insert exception");
            }
            Id_Prov_Location=statement.getGeneratedKeys().getInt(1);
            return Id_Prov_Location;
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

    public void ejbPostCreate(String Location, int Num_of_services, int Id_order) throws CreateException {

    }


    public int getId_Prov_Location() {
        return Id_Prov_Location;
    }

    public String getLocation() {
        return Location;
    }
    public void setLocation(){this.Location=Location;}

    public int getNum_of_services() {
        return Num_of_services;
    }
    public void setNum_of_services(){
        this.Num_of_services=Num_of_services;
    }
    public int getId_order(){return  Id_order;}


}
