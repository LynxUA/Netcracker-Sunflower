package com.sunflower.ejb.ProviderLocation;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Den on 13.12.2014.
 */
public class ProviderLocWrapper {

    private String location;
    private float longtitude;
    private float latitude;

    public ProviderLocWrapper(ResultSet rs){
        try {
            location = rs.getString(1);
            longtitude = rs.getFloat(2);
            latitude = rs.getFloat(3);
        } catch (SQLException e) {
            e.printStackTrace();

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
