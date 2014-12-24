package com.sunflower.ejb.ProviderLocation;

/**
 * Created by Алексей on 12/6/2014.
 */



import com.sunflower.ejb.DataSource;
import com.sunflower.ejb.price.PriceCatalog;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.log4j.Logger;

import javax.ejb.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProviderLocationBean implements EntityBean {
    private int Id_Prov_Location;
    private String Location;
    private int Num_of_services;
    private float longtitude;
    private float latitude;
    private final static Logger logger = Logger.getLogger(ProviderLocationBean.class);
    private EntityContext entityContext;
    public ProviderLocationBean() {
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
            statement = connection.prepareStatement("SELECT ID_PROV_LOCATION FROM PROVIDER_LOCATION WHERE ID_PROV_LOCATION = ?");
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

    public Integer ejbFindClosest(float longtitude, float latitude) throws FinderException {
        Connection connection = null;
        Statement statement = null;
        try {
            try {
                connection = DataSource.getDataSource().getConnection();
            }catch(SQLException e)
            {
                logger.error(e.getMessage(), e);
                throw new UnknownError();

            }
            //statement.setFloat(1, latitude);
            //statement.setFloat(2, latitude);
            //statement.setFloat(3, longtitude);
            statement = connection.createStatement();
            ResultSet resultSet = null;
            if (statement != null) {
                resultSet = statement.executeQuery("SELECT ID_PROV_LOCATION FROM" +
                        " (SELECT ID_PROV_LOCATION FROM PROVIDER_LOCATION ORDER BY ACOS(SIN(" + latitude +
                        "*3.1415926/180)* SIN(LATITUDE*3.1415926/180) + COS(" +latitude +
                        " * 3.1415926/180)* COS(LATITUDE*3.1415926/180)*COS(LONGTITUDE*3.1415926/180-" + longtitude +
                        "*3.1415926/180)) * 6371 ASC) WHERE ROWNUM = 1");
            }
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
            statement = connection.prepareStatement("DELETE FROM PROVIDER_LOCATION WHERE ID_PROV_LOCATION =?");
            statement.setInt(1, Id_Prov_Location);
            if (statement.executeUpdate() < 1) {
                throw new RemoveException("Exception while deleting");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError("Error occured when DELETE FROM PROVIDER_LOCATION");
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
        Id_Prov_Location = (Integer) entityContext.getPrimaryKey();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT LOCATION, NUM_OF_SERVICES, LONGTITUDE, LATITUDE" +
                    " FROM PROVIDER_LOCATION WHERE ID_PROV_LOCATION = ?");
            statement.setInt(1, Id_Prov_Location);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("...");
            }
            Location = resultSet.getString(1);
            Num_of_services = resultSet.getInt(2);
            longtitude = resultSet.getFloat(3);
            latitude = resultSet.getFloat(4);


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
            statement = connection.prepareStatement("UPDATE PROVIDER_LOCATION SET LOCATION  = ?, NUM_OF_SERVICES = ?, LONGTITUDE = ?, LATITUDE = ? WHERE ID_PROV_LOCATION=?");

            statement.setString(1, Location);
            statement.setInt(2, Num_of_services);

            statement.setFloat(3, longtitude);
            statement.setFloat(4, latitude);

            statement.setInt(5, Id_Prov_Location);


           
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

    public Integer ejbCreate(String location, int id_prov_location, int num_of_services, float longtitude, float latitude) throws CreateException {
        try {
            ejbFindByPrimaryKey(Id_Prov_Location);
            throw new DuplicateKeyException("Duplicated keys");
        } catch (FinderException e) { /*
        Everything is fine
      */}

        this.Location = location;
        this.Num_of_services = num_of_services;
        this.Id_Prov_Location = id_prov_location;
        this.latitude = latitude;
        this.longtitude = longtitude;

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            try{
                connection = DataSource.getDataSource().getConnection();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new UnknownError();
            }
            statement = connection.prepareStatement("INSERT INTO PROVIDER_LOCATION"
                    + "( LOCATION, NUM_OF_SERVICES, LONGTITUDE, LATITUDE) VALUES(?, ?, ?, ?)", new String[]{"ID_SERV_LOCATION"});

            statement.setString(1, Location);
            statement.setInt(2, Num_of_services);
            statement.setFloat(3, longtitude);
            statement.setFloat(4, latitude);


            if (statement.executeUpdate() != 1) {
                throw new CreateException("Insert exception");
            }
            Id_Prov_Location=statement.getGeneratedKeys().getInt(1);
            return Id_Prov_Location;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public void ejbPostCreate(String Location, int Num_of_services, int num_of_services, float longtitude, float latitude) throws CreateException {

    }


    public int getId_Prov_Location() {
        return Id_Prov_Location;
    }

    public String getLocation() {
        return Location;
    }
    public void setLocation(String location){this.Location=Location;}

    public int getNum_of_services() {
        return Num_of_services;
    }
    public void setNum_of_services(int Num_of_services){
        this.Num_of_services=Num_of_services;
    }

    public float ejbHomeGetDistanceToProvider(float longtitude, float latitude) throws ObjectNotFoundException {
        Connection connection = null;
        Statement statement = null;
        try {
            try {
                connection = DataSource.getDataSource().getConnection();
            }catch(SQLException e)
            {
                logger.error(e.getMessage(), e);
                throw new UnknownError();

            }
            statement = connection.createStatement();
            ResultSet resultSet = null;
            if (statement != null) {
                resultSet = statement.executeQuery("SELECT LOC FROM (SELECT (ACOS(SIN("+latitude
                        +"*3.1415926/180)* SIN(LATITUDE*3.1415926/180) + COS(" + latitude
                        +" * 3.1415926/180)* COS(LATITUDE*3.1415926/180)*COS(LONGTITUDE*3.1415926/180-" + longtitude
                        +"*3.1415926/180)) * 6371) AS LOC FROM PROVIDER_LOCATION ORDER BY LOC ASC) WHERE ROWNUM = 1");
            }
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("...");
            }
            return resultSet.getFloat(1);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }

    }

    public boolean ejbHomeIsLocationHasFreePorts(int id_prov_location) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DataSource.getDataSource().getConnection();
            statement = connection.prepareStatement("SELECT SUM(FREE_PORTS) FROM DEVICE WHERE ID_PROV_LOCATION = ?");
            statement.setInt(1, id_prov_location);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("...");
            }
            if(resultSet.getInt(1)!=0)
                return true;
            else
                return false;

        }catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public Collection ejbHomeGetAllLocations() {
        Connection connection = null;
        PreparedStatement statement = null;
        List<ProviderLocWrapper> locationList = new ArrayList<ProviderLocWrapper>();
        try {
            try {
                connection = DataSource.getDataSource().getConnection();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new UnknownError();

            }

            statement = connection.prepareStatement("SELECT LOCATION, LONGTITUDE, LATITUDE FROM PROVIDER_LOCATION");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                ProviderLocWrapper pl = new ProviderLocWrapper(rs);
                locationList.add(pl);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UnknownError();

        } finally {
            DataSource.closeConnection(connection);
        }

        return locationList;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public float getLatitude() {
        return latitude;
    }
}
