package com.example.vebibeer_be.model.entities.report;

public class TopCustomer {
    private int customer_id;
    private String customer_fullname;
    private long number_of_tickets_purchased;

    public TopCustomer() {
    }

    public TopCustomer(int customer_id, String customer_fullname, long number_of_tickets_purchased) {
        this.customer_id = customer_id;
        this.customer_fullname = customer_fullname;
        this.number_of_tickets_purchased = number_of_tickets_purchased;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_fullname() {
        return customer_fullname;
    }

    public void setCustomer_fullname(String customer_fullname) {
        this.customer_fullname = customer_fullname;
    }

    public long getNumber_of_tickets_purchased() {
        return number_of_tickets_purchased;
    }

    public void setNumber_of_tickets_purchased(long number_of_tickets_purchased) {
        this.number_of_tickets_purchased = number_of_tickets_purchased;
    }

    @Override
    public String toString() {
        return "TopCustomer [customer_id=" + customer_id + ", customer_fullname=" + customer_fullname
                + ", number_of_tickets_purchased=" + number_of_tickets_purchased + "]";
    }

}
