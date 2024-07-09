package com.example.vebibeer_be.dto;

import java.time.LocalDate;

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
    private LocalDate rating_editTime;
    private int amount_star;
    private String username;
    private int busCompany_id;
   
}
