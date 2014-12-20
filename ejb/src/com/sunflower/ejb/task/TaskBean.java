package com.sunflower.ejb.task;

import com.sunflower.ejb.DataSource;
import org.apache.log4j.Logger;

import javax.ejb.*;
import java.sql.*;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by denysburlakov on 03.12.14.
 */


public class TaskBean implements EntityBean {
    private int id_task;
    private String description;
    private int id_group_user;
    private int id_order;
    private String login;
    private final static Logger logger = Logger.getLogger(TaskBean.class);
    private EntityContext entityContext;
    public TaskBean() {

    }

    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT ID_TASK FROM TASK WHERE ID_TASK = ?");
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
            statement = connection.prepareStatement("DELETE FROM TASK WHERE ID_TASK = ?");
            statement.setInt(1, id_task);
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
        id_task = (Integer) entityContext.getPrimaryKey();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT DESCRIPTION, ID_GROUP_USER, ID_ORDER, LOGIN FROM TASK WHERE ID_TASK = ?");
            statement.setInt(1, id_task);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("");

            }
            description = resultSet.getString(1);
            id_group_user = resultSet.getInt(2);
            id_order = resultSet.getInt(3);
            login = resultSet.getString(4);

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
            statement = connection.prepareStatement(
                    "UPDATE TASK SET DESCRIPTION = ?, ID_GROUP_USER = ?, ID_ORDER = ?, LOGIN = ? WHERE ID_TASK = ?");
            statement.setString(1, description);
            statement.setInt(2, id_group_user);
            statement.setInt(3, id_order);
            statement.setString(4, login);
            statement.setInt(5, id_task);

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

    public Integer ejbCreate(String description, int id_group_user, int id_order, String login) throws CreateException {
        this.description = description;
        this.id_group_user = id_group_user;
        this.id_order = id_order;
        this.login = login;
        Connection connection = null;
        PreparedStatement statement;
        try {
            try{
                connection = DataSource.getDataSource().getConnection();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                logger.error(e.getMessage(), e);
                throw new UnknownError();
            }
            statement = connection.prepareStatement("INSERT INTO TASK"
                    + "(DESCRIPTION, ID_GROUP_USER, ID_ORDER, LOGIN) VALUES(?, ?, ?, ?)");
            statement.setString(1, description);
            statement.setInt(2, id_group_user);
            statement.setInt(3, id_order);
            statement.setString(4, login);
            if (statement.executeUpdate() != 1) {
                throw new CreateException("Insert exception");
            }
            statement = connection.prepareStatement("SELECT MAX(ID_TASK) FROM TASK");
            ResultSet rs = statement.executeQuery();
            rs.next();
            id_task=rs.getInt(1);
            return id_task;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public void ejbPostCreate(String description, int id_group_user, int id_order, String login) throws CreateException {

    }

   public Collection ejbHomeGetTasksByEngineer(int id_group_user, int from, int to) {
       Connection connection = null;
       PreparedStatement statement;
       try {
           connection = DataSource.getDataSource().getConnection();
           statement = connection.prepareStatement("SELECT M, A,B, C, D, E, F, I, J \n" +
                   "FROM (SELECT TASK.ID_TASK AS M, SERVICE_ORDER.ID_ORDER AS A, SCENARIO.NAME AS B, SERVICE_ORDER.LOGIN AS C, SERVICE_ORDER.LATITUDE AS D, SERVICE_ORDER.LONGTITUDE AS E, SERVICE.NAME AS F, PROVIDER_LOCATION.LOCATION AS I, TASK.DESCRIPTION AS J, ROWNUM R\n" +
                   "FROM ((((TASK JOIN SERVICE_ORDER ON TASK.ID_ORDER = SERVICE_ORDER.ID_ORDER) JOIN PRICE ON SERVICE_ORDER.ID_PRICE=PRICE.ID_PRICE) JOIN PROVIDER_LOCATION ON PRICE.ID_PROV_LOCATION = PROVIDER_LOCATION.ID_PROV_LOCATION) JOIN SERVICE ON PRICE.ID_SERVICE = SERVICE.ID_SERVICE) JOIN SCENARIO ON SERVICE_ORDER.ID_SCENARIO = SCENARIO.ID_SCENARIO\n" +
                   "WHERE ID_GROUP_USER = ? AND TASK.LOGIN IS NULL) \n" +
                   "WHERE R >= ? AND R <=?");
           statement.setInt(1, id_group_user);
           statement.setInt(2, from);
           statement.setInt(3, to);
           ResultSet resultSet = statement.executeQuery();
           Vector<TaskWrapper> tasks = new Vector<TaskWrapper>();
           while (resultSet.next()) {
               tasks.addElement(new TaskWrapper(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4),resultSet.getFloat(5), resultSet.getFloat(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9)));
           }
           return tasks;
       } catch (SQLException e) {
           logger.error(e.getMessage(), e);
           throw new UnknownError();
       } finally {
           DataSource.closeConnection(connection);
       }
   }




    public int getId_group_user() {
        return id_group_user;
    }
    public void setId_group_user(int id_group_user)
    {
        this.id_group_user=id_group_user;
    }

    public int getId_task() {
        return id_task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getId_order() {
        return id_order;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login)
    {
        this.login=login;
    }

    public int ejbHomeGetNumberOfTasksByEngineer(int id_group_user) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT COUNT(ID_TASK)\n" +
                    "FROM TASK\n" +
                    "WHERE ID_GROUP_USER = ? AND LOGIN IS NULL");
            statement.setInt(1, id_group_user);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public void ejbHomeAssignTask(int id_task, String login) throws UserWasAssignedException, UserHaveAssignedTaskException {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT LOGIN FROM TASK WHERE ID_TASK = ?");
            statement.setInt(1, id_task);
            ResultSet result = statement.executeQuery();
            result.next();
            result.getString(1);
            if(!result.wasNull()){
                throw new UserWasAssignedException();
            }

            /** Fix later!!! (checks whether someone has assigned this task before you*/
            /*
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT ID_TASK FROM TASK WHERE LOGIN LIKE ?");
            statement.setString(1, login);
            ResultSet result2 = statement.executeQuery();
            if (result2.next()){
                throw new UserHaveAssignedTaskException();
            }*/

            statement = connection.prepareStatement("UPDATE SERVICE_ORDER SET ID_STATUS = 3 WHERE ID_ORDER = (SELECT ID_ORDER FROM TASK WHERE ID_TASK = ?)");
            statement.setInt(1,id_task);
            statement.executeUpdate();
            statement = connection.prepareStatement(
                    "UPDATE TASK SET LOGIN = ? WHERE ID_TASK = ?");
            statement.setString(1, login);
            statement.setInt(2, id_task);

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

    public Integer ejbFindIncompleteTask(String name) throws FinderException {
        Connection connection = null;
        PreparedStatement statement;
        try {
            try {
                connection = DataSource.getDataSource().getConnection();
            }catch(SQLException e)
            {
                System.out.println(e.getErrorCode());
                System.out.println("something wrong with connection");

            }
            statement = connection.prepareStatement("SELECT TASK.ID_TASK FROM (TASK JOIN SERVICE_ORDER ON TASK.ID_ORDER = SERVICE_ORDER.ID_ORDER) WHERE TASK.LOGIN = ? AND SERVICE_ORDER.ID_STATUS NOT IN (4)");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("...");
            }
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }
}
