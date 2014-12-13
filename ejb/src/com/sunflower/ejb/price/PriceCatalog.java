package com.sunflower.ejb.price;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Den on 13.12.2014.
 */
public class PriceCatalog {

    private String nameOfService;
    private double priceOfService;
    private double priceOfLocation;

    public PriceCatalog(){}

    public PriceCatalog(String nameOfService, double priceOfService, double priceOfLocation){

        this.nameOfService = nameOfService;
        this.priceOfService = priceOfService;
        this.priceOfLocation = priceOfLocation;
    }

    public PriceCatalog(ResultSet rs){

        try {

            nameOfService = rs.getString(1);
            priceOfService = rs.getDouble(2);
            priceOfLocation = rs.getDouble(3);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getNameOfService() {
        return nameOfService;
    }

    public void setNameOfService(String nameOfService) {
        this.nameOfService = nameOfService;
    }

    public double getPriceOfService() {
        return priceOfService;
    }

    public void setPriceOfService(double priceOfService) {
        this.priceOfService = priceOfService;
    }

    public double getPriceOfLocation() {
        return priceOfLocation;
    }

    public void setPriceOfLocation(double priceOfLocation) {
        this.priceOfLocation = priceOfLocation;
    }
}
