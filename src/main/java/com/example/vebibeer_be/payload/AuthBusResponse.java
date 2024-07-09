package com.example.vebibeer_be.payload;

import com.example.vebibeer_be.model.entities.BusCompany.BusCompany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AuthBusResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private BusCompany busCompany;

    public AuthBusResponse(String accessToken, BusCompany busCompany){
        this.accessToken = accessToken;
        this.busCompany = busCompany;
    }

}
