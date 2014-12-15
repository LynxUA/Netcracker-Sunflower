package com.sunflower.ejb.task;

/**
 * Created by denysburlakov on 15.12.14.
 */
public class TaskWrapper {
    private int id_task;
    private int id_order;
    private String scenarioName;
    private String login; //user's login
    private float lat;
    private float lng;
    private String officeName;
    private String serviceName;
    private String description;

    public TaskWrapper(int id_task, int id_order, String scenarioName, String login, float lat, float lng, String officeName, String serviceName, String description) {
        this.id_task = id_task;
        this.id_order = id_order;
        this.scenarioName = scenarioName;
        this.login = login;
        this.lat = lat;
        this.lng = lng;
        this.officeName = officeName;
        this.serviceName = serviceName;
        this.description = description;
    }

    public int getId_task() {return id_task; }

    public int getId_order() {
        return id_order;
    }

    public String getScenarionName() {return  scenarioName; }

    public String getLogin() {
        return login;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public String getOfficeName(){
        return officeName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getDescription() {
        return description;
    }
}
