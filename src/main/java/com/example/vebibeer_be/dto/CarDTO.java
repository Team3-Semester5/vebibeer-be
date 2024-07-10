package com.example.vebibeer_be.dto;

public class CarDTO {
    private int car_id;
    private String car_code;
    private int amount_seat;
    private String car_imgUrl1;
    private String car_imgUrl2;
    private String car_imgUrl3;
    private String car_imgUrl4;
    private String car_imgUrl5;
    private String car_imgUrl6;
    private String car_manufacturer;
    private int busCompany_id;

    public CarDTO() {
    }

    public CarDTO(int car_id, String car_code, int amount_seat, String car_imgUrl1, String car_imgUrl2,
            String car_imgUrl3, String car_imgUrl4, String car_imgUrl5, String car_imgUrl6, String car_manufacturer,
            int busCompany_id) {
        this.car_id = car_id;
        this.car_code = car_code;
        this.amount_seat = amount_seat;
        this.car_imgUrl1 = car_imgUrl1;
        this.car_imgUrl2 = car_imgUrl2;
        this.car_imgUrl3 = car_imgUrl3;
        this.car_imgUrl4 = car_imgUrl4;
        this.car_imgUrl5 = car_imgUrl5;
        this.car_imgUrl6 = car_imgUrl6;
        this.car_manufacturer = car_manufacturer;
        this.busCompany_id = busCompany_id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getCar_code() {
        return car_code;
    }

    public void setCar_code(String car_code) {
        this.car_code = car_code;
    }

    public int getAmount_seat() {
        return amount_seat;
    }

    public void setAmount_seat(int amount_seat) {
        this.amount_seat = amount_seat;
    }

    public String getCar_imgUrl1() {
        return car_imgUrl1;
    }

    public void setCar_imgUrl1(String car_imgUrl1) {
        this.car_imgUrl1 = car_imgUrl1;
    }

    public String getCar_imgUrl2() {
        return car_imgUrl2;
    }

    public void setCar_imgUrl2(String car_imgUrl2) {
        this.car_imgUrl2 = car_imgUrl2;
    }

    public String getCar_imgUrl3() {
        return car_imgUrl3;
    }

    public void setCar_imgUrl3(String car_imgUrl3) {
        this.car_imgUrl3 = car_imgUrl3;
    }

    public String getCar_imgUrl4() {
        return car_imgUrl4;
    }

    public void setCar_imgUrl4(String car_imgUrl4) {
        this.car_imgUrl4 = car_imgUrl4;
    }

    public String getCar_imgUrl5() {
        return car_imgUrl5;
    }

    public void setCar_imgUrl5(String car_imgUrl5) {
        this.car_imgUrl5 = car_imgUrl5;
    }

    public String getCar_imgUrl6() {
        return car_imgUrl6;
    }

    public void setCar_imgUrl6(String car_imgUrl6) {
        this.car_imgUrl6 = car_imgUrl6;
    }

    public String getCar_manufacturer() {
        return car_manufacturer;
    }

    public void setCar_manufacturer(String car_manufacturer) {
        this.car_manufacturer = car_manufacturer;
    }

    public int getBusCompany_id() {
        return busCompany_id;
    }

    public void setBusCompany_id(int busCompany_id) {
        this.busCompany_id = busCompany_id;
    }

    @Override
    public String toString() {
        return "CarDTO [car_id=" + car_id + ", car_code=" + car_code + ", amount_seat=" + amount_seat + ", car_imgUrl1="
                + car_imgUrl1 + ", car_imgUrl2=" + car_imgUrl2 + ", car_imgUrl3=" + car_imgUrl3 + ", car_imgUrl4="
                + car_imgUrl4 + ", car_imgUrl5=" + car_imgUrl5 + ", car_imgUrl6=" + car_imgUrl6 + ", car_manufacturer="
                + car_manufacturer + ", busCompany_id=" + busCompany_id + "]";
    }

}
