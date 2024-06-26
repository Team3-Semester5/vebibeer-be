package com.example.vebibeer_be.dto;

import java.sql.Date;

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
    private String rating_content;
    private Date rating_editTime;
    private int amount_star;
    private int customer_id;
    private int busCompany_id;
   
}
