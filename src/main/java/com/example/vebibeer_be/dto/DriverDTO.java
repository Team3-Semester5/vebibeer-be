package com.example.vebibeer_be.dto;

public class DriverDTO {
    private int driver_id;

    private String driver_name;
    private String driver_avaUrl;
    private String driver_description;

    private int busCompany_id;

    public DriverDTO() {
    }

    public DriverDTO(int driver_id, String driver_name, String driver_avaUrl, String driver_description,
            int busCompany_id) {
        this.driver_id = driver_id;
        this.driver_name = driver_name;
        this.driver_avaUrl = driver_avaUrl;
        this.driver_description = driver_description;
        this.busCompany_id = busCompany_id;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_avaUrl() {
        return driver_avaUrl;
    }

    public void setDriver_avaUrl(String driver_avaUrl) {
        this.driver_avaUrl = driver_avaUrl;
    }

    public String getDriver_description() {
        return driver_description;
    }

    public void setDriver_description(String driver_description) {
        this.driver_description = driver_description;
    }

    public int getBusCompany_id() {
        return busCompany_id;
    }

    public void setBusCompany_id(int busCompany_id) {
        this.busCompany_id = busCompany_id;
    }

    @Override
    public String toString() {
        return "DriverDTO [driver_id=" + driver_id + ", driver_name=" + driver_name + ", driver_avaUrl=" + driver_avaUrl
                + ", driver_description=" + driver_description + ", busCompany_id=" + busCompany_id + "]";
    }

}
