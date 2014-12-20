package com.sunflower.ejb.ProviderLocation;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Den on 13.12.2014.
 */
public class ProviderLocWrapper {

    private String location;
    private float longtitude;
    private float latitude;
    private final static Logger logger = Logger.getLogger(ProviderLocWrapper.class);

    public ProviderLocWrapper(ResultSet rs){
        try {
            location = rs.getString(1);
            longtitude = rs.getFloat(2);
            latitude = rs.getFloat(3);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);

        }
    }
    public String getLocation() {
        return location;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public float getLatitude() {
        return latitude;
    }
}
