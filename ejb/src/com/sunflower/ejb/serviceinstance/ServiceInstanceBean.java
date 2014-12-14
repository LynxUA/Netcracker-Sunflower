package com.sunflower.ejb.serviceinstance;

import com.sunflower.ejb.DataSource;

import javax.ejb.*;
import java.sql.*;

/**
 * Created by denysburlakov on 13.12.14.
 */
public class ServiceInstanceBean implements EntityBean {
    private int id_service_inst;
    private int status;
    private Integer id_circuit;

    private EntityContext entityContext;

    public ServiceInstanceBean() {

    }

    public Integer ejbCreate(int status) throws CreateException {
        this.status = status;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            try{
                connection = DataSource.getDataSource().getConnection();
            } catch (SQLException e) {
                throw new EJBException("Ошибка dataSource");
            }
            statement = connection.prepareStatement("INSERT INTO SERVICE_INSTANCE"
                    + "(STATUS) VALUES(?)", new String[]{"ID_SERVICE_INST"});
            statement.setInt(1, status);

            if (statement.executeUpdate() != 1) {
                System.out.println("Fail");
                throw new CreateException("Insert exception");
            }
            rs = statement.getGeneratedKeys();
            while(rs.next()){
                id_service_inst = rs.getInt(1);
            }
            return id_service_inst;
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

    public void ejbPostCreate(int status) throws CreateException {

    }

    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
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
            statement = connection.prepareStatement("SELECT ID_SERVICE_INST FROM SERVICE_INSTANCE WHERE ID_SERVICE_INST = ?");
            statement.setInt(1, key);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("...");
            }
            return key;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());
            System.out.println("тут");
            e.printStackTrace();
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
        if(DataSource.getDataSource()==null){
            DataSource.setDataSource();
        }
    }

    public void unsetEntityContext() throws EJBException {
        this.entityContext = null;
    }

    public void ejbRemove() throws RemoveException, EJBException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("DELETE FROM SERVICE_INSTANCE WHERE ID_SERVICE_INST =?");
            statement.setInt(1, id_service_inst);
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

    }

    public void ejbPassivate() throws EJBException {

    }

    public void ejbLoad() throws EJBException {
        id_service_inst = (Integer) entityContext.getPrimaryKey();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT STATUS, ID_CIRCUIT FROM SERVICE_INSTANCE WHERE ID_SERVICE_INST = ?");
            statement.setInt(1, id_service_inst);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("...");
            }
            status = resultSet.getInt(1);

            id_circuit = resultSet.getInt(2);
            if(id_circuit == 0){
                id_circuit = null;
                //System.out.println("id_circuit is null");
            }else{
                System.out.println("id_circuit is " + id_circuit);
            }

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
            connection = DataSource.getDataSource().getConnection();
            if(id_circuit !=null){
                statement = connection.prepareStatement(
                        "UPDATE SERVICE_INSTANCE SET STATUS = ?, ID_CIRCUIT = ? WHERE ID_SERVICE_INST = ?");
                statement.setInt(1, status);
                statement.setInt(2, id_circuit);
                statement.setInt(3, id_service_inst);
                System.out.println("Status is: "+status + ", id_circuit is: "+id_service_inst+", id_service_inst is: "+id_service_inst);
            }
            else {
                statement = connection.prepareStatement(
                        "UPDATE SERVICE_INSTANCE SET STATUS = ? WHERE ID_SERVICE_INST = ?");
                statement.setInt(1, status);
                statement.setInt(2, id_service_inst);
                System.out.println("Status is: "+status + ", id_circuit is: null, id_service_inst is: "+id_service_inst);
            }
            //System.out.println(statement.getParameterMetaData().toString());
            if (statement.executeUpdate() < 1) {
                throw new NoSuchEntityException("...");
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());
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

    public int getId_service_inst() {
        return id_service_inst;
    }

    public int getStatus() {
        return status;
    }

    public int getId_circuit() {
        return id_circuit;
    }
}
