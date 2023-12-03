package com.statista.code.challenge.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private String bookingId; // Can be null when creating a new booking
    private String description;
    private Double price;
    private String currency;
    private Date subscriptionStartDate;
    private String email;
    private String department;

}
