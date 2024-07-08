package com.example.vebibeer_be.dto;

import java.sql.Date;

public class RouteDTO {
    private Date route_startTime;
    private Date route_endTime;
    private String policy;
    private String route_description;

    private int car_id;
    private int busCompany_id;
    private int driver_id;
    private int priceTicket;

    private int startLocation_id;
    private int endLocation_id;

    public RouteDTO() {
    }

    public RouteDTO(Date route_startTime, Date route_endTime, String policy, String route_description, int car_id,
            int busCompany_id, int driver_id, int priceTicket, int startLocation_id, int endLocation_id) {
        this.route_startTime = route_startTime;
        this.route_endTime = route_endTime;
        this.policy = policy;
        this.route_description = route_description;
        this.car_id = car_id;
        this.busCompany_id = busCompany_id;
        this.driver_id = driver_id;
        this.priceTicket = priceTicket;
        this.startLocation_id = startLocation_id;
        this.endLocation_id = endLocation_id;
    }

    public Date getRoute_startTime() {
        return route_startTime;
    }

    public void setRoute_startTime(Date route_startTime) {
        this.route_startTime = route_startTime;
    }

    public Date getRoute_endTime() {
        return route_endTime;
    }

    public void setRoute_endTime(Date route_endTime) {
        this.route_endTime = route_endTime;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getRoute_description() {
        return route_description;
    }

    public void setRoute_description(String route_description) {
        this.route_description = route_description;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public int getBusCompany_id() {
        return busCompany_id;
    }

    public void setBusCompany_id(int busCompany_id) {
        this.busCompany_id = busCompany_id;
    }

    public int getPriceTicket() {
        return priceTicket;
    }

    public void setPriceTicket(int priceTicket) {
        this.priceTicket = priceTicket;
    }

    @Override
    public String toString() {
        return "RouteDTO [route_startTime=" + route_startTime + ", route_endTime=" + route_endTime + ", policy="
                + policy + ", route_description=" + route_description + ", car_id=" + car_id + ", busCompany_id="
                + busCompany_id + ", priceTicket=" + priceTicket + "]";
    }

    public int getStartLocation_id() {
        return startLocation_id;
    }

    public void setStartLocation_id(int startLocation_id) {
        this.startLocation_id = startLocation_id;
    }

    public int getEndLocation_id() {
        return endLocation_id;
    }

    public void setEndLocation_id(int endLocation_id) {
        this.endLocation_id = endLocation_id;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }

}
