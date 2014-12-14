package com.sunflower.ejb.ProviderLocation;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Den on 13.12.2014.
 */
public class ProviderLocWrapper {

    private String location;

    public ProviderLocWrapper(){}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ProviderLocWrapper(ResultSet rs){
        try {
            location = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
