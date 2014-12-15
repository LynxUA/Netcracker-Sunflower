package com.sunflower.ejb.ServiceOrder;

import java.sql.Date;

/**
 * Created by denysburlakov on 12.12.14.
 */
public class SOWrapper {
    
    private int id_order;
    private String status_name;
    private String scenario_name;
    private Date so_date;
    private float longtitude;
    private float latitude;

    public SOWrapper(int id_order, String status_name, String scenario_name, Date so_date, float longtitude, float latitude) {
        this.id_order = id_order;
        this.status_name = status_name;
        this.scenario_name = scenario_name;
        this.so_date = so_date;
        this.longtitude = longtitude;
        this.latitude = latitude;
    }

    public String getStatus_name() {
        return status_name;
    }

    public int getId_order() {
        return id_order;
    }

    public String getScenario_name() {
        return scenario_name;
    }

    public Date getSo_date() {
        return so_date;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public float getLatitude() {
        return latitude;
    }
}
