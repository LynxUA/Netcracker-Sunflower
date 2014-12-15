package com.sunflower.ejb.serviceinstance;

/**
 * Created by denysburlakov on 15.12.14.
 */
public class SIWrapper {
    private int id_service_inst;
    private String statusName;
    private String serviceName;
    private float longtitude;
    private float latitude;
    private String location;

    public SIWrapper(int id_service_inst, String statusName, String serviceName, float longtitude, float latitude, String location) {
        this.id_service_inst = id_service_inst;
        this.statusName = statusName;
        this.serviceName = serviceName;
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.location = location;
    }

    public int getId_service_inst() {
        return id_service_inst;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getLocation() {
        return location;
    }
}
