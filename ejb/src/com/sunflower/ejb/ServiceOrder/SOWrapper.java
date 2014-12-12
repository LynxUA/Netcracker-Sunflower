package com.sunflower.ejb.ServiceOrder;

/**
 * Created by denysburlakov on 12.12.14.
 */
public class SOWrapper {
    
    private int id_order;
    private String status_name;
    private String scenario_name;

    public SOWrapper(int id_order, String status_name, String scenario_name) {
        this.id_order = id_order;
        this.status_name = status_name;
        this.scenario_name = scenario_name;
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
}
