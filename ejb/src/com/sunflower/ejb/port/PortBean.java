package com.sunflower.ejb.port;

/**
 * Created by Alexey on 12/11/2014.
 */
import com.sunflower.ejb.DataSource;
import org.apache.log4j.Logger;


import javax.ejb.*;
import java.sql.*;

public class PortBean implements EntityBean {
    private int Id_Port;
    private int status;
    private  int Id_Device;
    private final static Logger logger = Logger.getLogger(PortBean.class);
    private EntityContext entityContext;
    public PortBean() {
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
            statement = connection.prepareStatement("SELECT ID_PORT FROM PORT WHERE ID_PORT = ?");
            statement.setInt(1, key);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("...");
            }
            return key;
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
            statement = connection.prepareStatement("DELETE FROM PORT WHERE ID_PORT =?");
            statement.setInt(1, Id_Port);
            if (statement.executeUpdate() < 1) {
                throw new RemoveException("Exception while deleting");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
            throw new EJBException("DELETE exception");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(),e);
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
        Id_Port = (Integer) entityContext.getPrimaryKey();
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT STATUS,ID_DEVICE" +
                    " FROM PORT WHERE ID_PORT = ?");
            statement.setInt(1, Id_Port);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("...");
            }
            try {
                status = resultSet.getInt(1);
            }catch (SQLException e){
                System.out.println(e.getErrorCode());
                System.out.println(e.getMessage());
                logger.error(e.getMessage(),e);
            }
            try{
                Id_Device= resultSet.getInt(2);
            }catch (SQLException e) {
                logger.error(e.getMessage(),e);
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            throw new EJBException("Ошибка SELECT");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(),e);
            }
        }
    }

    public void ejbStore() throws EJBException {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("UPDATE PORT SET STATUS  = ?, ID_DEVICE = ? WHERE ID_PORTT=?");

            statement.setInt(1, status);

            statement.setInt(2, Id_Device);


            statement.setInt(3, Id_Port);




            if (statement.executeUpdate() < 1) {
                System.out.println("bad statement");
                throw new NoSuchEntityException("...");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
            throw new EJBException("Ошибка UPDATE");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(),e);
            }
        }
    }

    public Integer ejbCreate(int status, int Id_Device) throws CreateException {

        this.status = status;
        this.Id_Device = Id_Device;


        Connection connection = null;
        PreparedStatement statement = null;
        try {
            try{
                connection = DataSource.getDataSource().getConnection();
            } catch (SQLException e) {
                logger.error(e.getMessage(),e);
                throw new EJBException("Ошибка dataSource");
            }
            statement = connection.prepareStatement("INSERT INTO PORT"
                    + "( STATUS,ID_DEVICE) VALUES(?, ?)", new String[]{"ID_PORT"});

            statement.setInt(1, status);
            statement.setInt(2, Id_Device);


            if (statement.executeUpdate() != 1) {
                throw new CreateException("Insert exception");
            }
            Id_Port=statement.getGeneratedKeys().getInt(1);
            return Id_Port;
        } catch (SQLException e) {
            //throw new EJBException("Ошибка INSERT");
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(),e);
            }
        }
        return null;
    }

    public void ejbPostCreate(int status, int Id_Port) throws CreateException {

    }


    public int getId_Port(){return  Id_Port;}
    public int getStatus(){return  status;}
    public void setStatus(int status){this.status=status;}
    public int getId_Device(){return Id_Device;}


}

