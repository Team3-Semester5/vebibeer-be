package com.example.vebibeer_be.dto;

import java.sql.Timestamp;

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
public class RatingDTO {
    private int rating_id;
    private String rating_content;
    private Timestamp rating_editTime;
    private int amount_star;
    private String username;
    private int busCompany_id;

    private String carCode;
    private String driverName;
    private String customerFullname;
}

