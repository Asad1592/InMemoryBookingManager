package com.statista.code.challenge.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
/**
 * BookingDTO is a Data Transfer Object used for handling booking data in API requests and responses.
 * It includes fields such as bookingId, description, price, currency, subscriptionStartDate, email, and department.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private String bookingId;
    private String description;
    private Double price;
    private String currency;
    private Date subscriptionStartDate;
    private String email;
    private String department;

}
