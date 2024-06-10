package com.example.vebibeer_be.dto;

import com.example.vebibeer_be.model.entities.BusCompany.Route;

public class TicketDTO {
    private int ticket_id;
    private int ticket_price;
    private String ticket_seat;
    private String ticket_status;
    private Route route;

    public TicketDTO() {
    }

    public TicketDTO(int ticket_id, int ticket_price, String ticket_seat, String ticket_status, Route route) {
        this.ticket_id = ticket_id;
        this.ticket_price = ticket_price;
        this.ticket_seat = ticket_seat;
        this.ticket_status = ticket_status;
        this.route = route;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public int getTicket_price() {
        return ticket_price;
    }

    public void setTicket_price(int ticket_price) {
        this.ticket_price = ticket_price;
    }

    public String getTicket_seat() {
        return ticket_seat;
    }

    public void setTicket_seat(String ticket_seat) {
        this.ticket_seat = ticket_seat;
    }

    public String getTicket_status() {
        return ticket_status;
    }

    public void setTicket_status(String ticket_status) {
        this.ticket_status = ticket_status;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "TicketDTO [ticket_id=" + ticket_id + ", ticket_price=" + ticket_price + ", ticket_seat=" + ticket_seat
                + ", ticket_status=" + ticket_status + ", route=" + route + "]";
    }

}
