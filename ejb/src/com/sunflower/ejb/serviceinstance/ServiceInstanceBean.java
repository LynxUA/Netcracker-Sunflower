package com.sunflower.ejb.serviceinstance;

import com.sunflower.ejb.DataSource;
import com.sunflower.ejb.ServiceOrder.SOWrapper;
import com.sunflower.ejb.servicelocation.ServiceLocationBean;
import org.apache.log4j.Logger;

import javax.ejb.*;
import java.sql.*;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by denysburlakov on 13.12.14.
 */
public class ServiceInstanceBean implements EntityBean {
    private int id_service_inst;
    private int status;
    private Integer id_circuit;
    private final static Logger logger = Logger.getLogger(ServiceLocationBean.class);
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
               logger.error(e.getMessage(), e);
                throw new UnknownError();
            }
            statement = connection.prepareStatement("INSERT INTO SERVICE_INSTANCE"
                    + "(STATUS) VALUES(?)", new String[]{"ID_SERVICE_INST"});
            statement.setInt(1, status);

            if (statement.executeUpdate() != 1) {
                throw new CreateException("Insert exception");
            }
            rs = statement.getGeneratedKeys();
            while(rs.next()){
                id_service_inst = rs.getInt(1);
            }
            return id_service_inst;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
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
                logger.error(e.getMessage(), e);
                throw new UnknownError();

            }
            statement = connection.prepareStatement("SELECT ID_SERVICE_INST FROM SERVICE_INSTANCE WHERE ID_SERVICE_INST = ?");
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
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("DELETE FROM SERVICE_INSTANCE WHERE ID_SERVICE_INST =?");
            statement.setInt(1, id_service_inst);
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
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
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
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
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
    public void setStatus(int status)
    {
        this.status=status;
    }

    public void setId_circuit(Integer id_circuit) {
        this.id_circuit = id_circuit;
    }

    public Collection ejbHomeGetServiceInstances(String login, int from, int to) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT A,B,C, D, E, F\n" +
                    "FROM (SELECT SERVICE_ORDER.ID_SERVICE_INST AS A, SI_STATUS.NAME AS B, SERVICE.NAME AS C, SERVICE_ORDER.LONGTITUDE AS D, SERVICE_ORDER.LATITUDE AS E, PROVIDER_LOCATION.LOCATION AS F, ROWNUM R\n" +
                    "FROM ((((SERVICE_ORDER JOIN SERVICE_INSTANCE ON SERVICE_ORDER.ID_SERVICE_INST = SERVICE_INSTANCE.ID_SERVICE_INST) JOIN SI_STATUS ON SERVICE_INSTANCE.STATUS = SI_STATUS.ID_STATUS) JOIN PRICE ON SERVICE_ORDER.ID_PRICE = PRICE.ID_PRICE) JOIN SERVICE ON PRICE.ID_SERVICE = SERVICE.ID_SERVICE) JOIN PROVIDER_LOCATION ON PRICE.ID_PROV_LOCATION = PROVIDER_LOCATION.ID_PROV_LOCATION\n" +
                    "WHERE LOGIN = ? )\n" +
                    "WHERE R >= ? AND R <=?");
            statement.setString(1, login);
            statement.setInt(2, from);
            statement.setInt(3, to);
            ResultSet resultSet = statement.executeQuery();
            Vector<SIWrapper> instances = new Vector<SIWrapper>();
            while (resultSet.next()) {
                instances.addElement(new SIWrapper(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(4), resultSet.getFloat(5), resultSet.getString(6)));
            }
            return instances;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new EJBException("Ошибка SELECT");
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public int ejbHomeGetNumberOfInstancesByLogin(String login) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT COUNT(DISTINCT ID_SERVICE_INST)\n" +
                    "FROM (SERVICE_ORDER)\n" +
                    "WHERE LOGIN = ?");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new FinderException();
            }
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public Collection ejbHomeGetSLByLogin(String login) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            try {
                connection = DataSource.getDataSource().getConnection();
            }catch(SQLException e)
            {
                logger.error(e.getMessage(), e);
                throw new UnknownError();

            }
            statement = connection.prepareStatement("SELECT LONGTITUDE, LATITUDE, NAME FROM (SELECT DISTINCT SERVICE_INSTANCE.ID_SERVICE_INST" +
                    ", LONGTITUDE, LATITUDE, LOGIN, SERVICE.NAME FROM (SERVICE_INSTANCE JOIN SERVICE_ORDER ON SERVICE_INSTANCE.ID_SERVICE_INST = SERVICE_ORDER.ID_SERVICE_INST" +
                    " JOIN PRICE ON SERVICE_ORDER.ID_PRICE = PRICE.ID_PRICE JOIN SERVICE ON PRICE.ID_SERVICE = SERVICE.ID_SERVICE  )) WHERE LOGIN = ?");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            Vector<ServiceLocationWrapper> wrapper = new Vector<ServiceLocationWrapper>();
            while (resultSet.next()) {
                wrapper.add(new ServiceLocationWrapper(resultSet.getFloat(1), resultSet.getFloat(2), resultSet.getString(3)));
            }
            return wrapper;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }
}
