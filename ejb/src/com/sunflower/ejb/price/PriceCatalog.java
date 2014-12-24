package com.sunflower.ejb.price;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Den on 13.12.2014.
 */
public class PriceCatalog {

    private String nameOfService;
    private double priceOfService;
    private double priceOfLocation;
    private static Logger logger = Logger.getLogger(PriceCatalog.class);

    public PriceCatalog(){}

    /**
     * Initializes a newly created PriceCatalog object
     * that represents prices of ISP services
     * @param nameOfService
     * @param priceOfService
     * @param priceOfLocation
     * */
    public PriceCatalog(String nameOfService, double priceOfService, double priceOfLocation){

        this.nameOfService = nameOfService;
        this.priceOfService = priceOfService;
        this.priceOfLocation = priceOfLocation;
    }

    /**
     * Initializes a newly created PriceCatalog object
     * with ResultSet that represents prices of ISP services
     * gotten from database
     * @param rs java.sql.ResultSet
     *
     * */
    public PriceCatalog(ResultSet rs){

        try {

            nameOfService = rs.getString(1);
            priceOfService = rs.getDouble(2);
            priceOfLocation = rs.getDouble(3);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
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
