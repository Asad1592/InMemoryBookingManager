package com.statista.code.challenge.controller;

import com.statista.code.challenge.dto.BookingDTO;
import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/bookingservice")
public class BookingController {

    private final BookingService service;

    @Autowired
    public BookingController(BookingService service) {
        this.service = service;
    }

    @PostMapping("/bookings")
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO) {
        Booking booking = service.createBooking(bookingDTO);
        return ResponseEntity.ok(booking);
    }

    @PutMapping("/bookings/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable String bookingId, @RequestBody BookingDTO bookingDTO) {
        Booking updatedBooking = service.updateBooking(bookingId, bookingDTO);
        return ResponseEntity.ok(updatedBooking);
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable String bookingId) {
        Booking booking = service.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/bookings/department/{department}")
    public ResponseEntity<List<Booking>> getBookingsByDepartment(@PathVariable String department) {
        List<Booking> bookings = service.getBookingsByDepartment(department);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/currencies")
    public ResponseEntity<Set<String>> getAllUsedCurrencies() {
        Set<String> currencies = service.getAllUsedCurrencies();
        return ResponseEntity.ok(currencies);
    }

    @GetMapping("/bookings/sum/{currency}")
    public ResponseEntity<Double> getSumByCurrency(@PathVariable String currency) {
        double sum = service.getSumByCurrency(currency);
        return ResponseEntity.ok(sum);
    }

    @GetMapping("/bookings/dobusiness/{bookingId}")
    public ResponseEntity<String> doBusiness(@PathVariable String bookingId) {
        String result = service.doBusiness(bookingId);
        return ResponseEntity.ok(result);
    }
}
