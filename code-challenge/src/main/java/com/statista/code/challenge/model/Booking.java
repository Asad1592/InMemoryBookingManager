package com.statista.code.challenge.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
/**
 * Booking represents the booking entity with fields like bookingId, description, price, currency, subscriptionStartDate, email, and department.
 * It is used to store booking information within the application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private String bookingId;
    private String description;
    private Double price;
    private String currency;
    private Date subscriptionStartDate;
    private String email;
    private String department;
}
