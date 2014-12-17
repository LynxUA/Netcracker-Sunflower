package com.sunflower.ejb.circuit;

import com.sunflower.ejb.DataSource;

import javax.ejb.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Алексей on 12/14/2014.
 */
public class CircuitBean implements EntityBean {
    private int Id_Circuit;
    private int Id_Port;
    private  int Id_Cable;

    private EntityContext entityContext;
    public CircuitBean() {
    }

    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT ID_CIRCUIT FROM CIRCUIT WHERE ID_CIRCUIT = ?");
            statement.setInt(1, key);
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
            statement = connection.prepareStatement("DELETE FROM CIRCUIT WHERE ID_CIRCUIT =?");
            statement.setInt(1, Id_Circuit);
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
        Id_Port = (Integer) entityContext.getPrimaryKey();
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT ID_PORT,ID_CABLE" +
                    " FROM CIRCUIT WHERE ID_CIRCUIT = ?");
            statement.setInt(1, Id_Circuit);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("...");
            }
            Id_Port = resultSet.getInt(1);
            Id_Cable = resultSet.getInt(2);
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
            statement = connection.prepareStatement("UPDATE CIRCUIT SET ID_PORT = ?, ID_CABLE = ? WHERE ID_CIRCUIT=?");
            statement.setInt(1, Id_Port);
            statement.setInt(2, Id_Circuit);
            statement.setInt(3, Id_Port);
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

    public Integer ejbCreate(int Id_Port, int Id_Cable) throws CreateException {

        this.Id_Port = Id_Port;
        this.Id_Cable = Id_Cable;


        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();

            statement = connection.prepareStatement("INSERT INTO CIRCUIT"
                    + "( ID_PORT,ID_CABLE) VALUES(?, ?)", new String[]{"ID_CIRCUIT"});

            statement.setInt(1, Id_Port);
            statement.setInt(2, Id_Cable);


            if (statement.executeUpdate() != 1) {
                throw new CreateException("Insert exception");
            }
            Id_Circuit=statement.getGeneratedKeys().getInt(1);
            return Id_Circuit;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public void ejbPostCreate(int Id_Port, int Id_Cable) throws CreateException {

    }

    public int getId_Circuit(){return Id_Circuit;}
    public int getId_Port(){return Id_Port;}
    public void setId_Port(int Id_Port){this.Id_Port=Id_Port;}
    public int getId_Cable(){return  Id_Cable;}
    public void setId_Cable(int Id_Cable){this.Id_Cable=Id_Cable;}



}
