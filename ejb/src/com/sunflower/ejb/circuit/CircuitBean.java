package com.sunflower.ejb.circuit;

import com.sunflower.ejb.DataSource;
import org.apache.log4j.Logger;

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
    private final static Logger logger = Logger.getLogger(CircuitBean.class);
    private  Integer Id_Cable;

    private EntityContext entityContext;
    public CircuitBean() {
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
            }
            statement = connection.prepareStatement("SELECT ID_CIRCUIT FROM CIRCUIT WHERE ID_CIRCUIT = ?");
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
            statement = connection.prepareStatement("DELETE FROM CIRCUIT WHERE ID_CIRCUIT =?");
            statement.setInt(1, Id_Circuit);
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
        //Do not fill
    }

    public void ejbPassivate() throws EJBException {
        //Do not fill
    }

    public void ejbLoad() throws EJBException {
        Id_Circuit = (Integer) entityContext.getPrimaryKey();
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
            if(Id_Cable == 0){
                Id_Cable = null;
                //System.out.println("id_circuit is null");
            }
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

            if(Id_Cable!=null) {
                statement = connection.prepareStatement("UPDATE CIRCUIT SET ID_PORT = ?, ID_CABLE = ? WHERE ID_CIRCUIT=?");
                statement.setInt(1, Id_Port);
                statement.setInt(2, Id_Cable);
                statement.setInt(3, Id_Circuit);
                if (statement.executeUpdate() < 1) {
                    throw new NoSuchEntityException("No such entity");
                }
            }
            else {
                statement = connection.prepareStatement("UPDATE CIRCUIT SET ID_PORT = ? WHERE ID_CIRCUIT=?");
                statement.setInt(1, Id_Port);

                statement.setInt(2, Id_Circuit);
                if (statement.executeUpdate() < 1) {
                    throw new NoSuchEntityException("No such entity");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public Integer ejbCreate(int Id_Port) throws CreateException {

        this.Id_Port = Id_Port;



        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            connection = DataSource.getDataSource().getConnection();

            statement = connection.prepareStatement("INSERT INTO CIRCUIT"
                    + "(ID_PORT) VALUES(?)");

            statement.setInt(1, Id_Port);



            if (statement.executeUpdate() != 1) {
                throw new CreateException("Insert exception");
            }

                    statement=connection.prepareStatement("SELECT Id_Circuit from CIRCUIT where Id_Port = ?");
            statement.setInt(1, Id_Port);
           resultSet=  statement.executeQuery();
            resultSet.next();

            Id_Circuit=resultSet.getInt(1);
            return Id_Circuit;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }    }

    public void ejbPostCreate(int Id_Port) throws CreateException {

    }

    public int getId_Circuit(){return Id_Circuit;}
    public int getId_Port(){return Id_Port;}
    public void setId_Port(int Id_Port){this.Id_Port=Id_Port;}
    public Integer getId_Cable(){return  Id_Cable;}
    public void setId_Cable(Integer Id_Cable){this.Id_Cable=Id_Cable;}



}
