package com.sunflower.ejb.serviceinstance;

/**
 * Created by denysburlakov on 17.12.14.
 */
public class ServiceLocationWrapper {
    float longtitude;
    float latitude;
    String service;

    public ServiceLocationWrapper(float longtitude, float latitude, String service) {
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.service = service;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getService() {
        return service;
    }
}
