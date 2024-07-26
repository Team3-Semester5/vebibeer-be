package com.example.vebibeer_be.dto;

import com.example.vebibeer_be.model.entities.BusCompany.Route;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class TicketDTO {
    private int ticket_id;
    private int ticket_price;
    private String ticket_seat;
    private String ticket_status;
    private Route route;
    @Transient
    private boolean lockSuccess;

    public void setLockSuccess(boolean lockSuccess) {
        this.lockSuccess = lockSuccess;
    }
}
