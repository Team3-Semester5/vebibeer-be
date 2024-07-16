package com.example.vebibeer_be.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RouteDTO {
    private Timestamp route_startTime;
    private Timestamp route_endTime;
    private String policy;
    private String route_description;

    private int car_id;
    private int busCompany_id;
    private int driver_id;
    private int priceTicket;

    private int startLocation_id;
    private int endLocation_id;
    private boolean isDaily;
    
}
