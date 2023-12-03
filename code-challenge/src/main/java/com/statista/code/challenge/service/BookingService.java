package com.statista.code.challenge.service;

import com.statista.code.challenge.dto.BookingDTO;
import com.statista.code.challenge.exception.BookingNotFoundException;
import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository repository;
    private final Map<String, Consumer<Booking>> departmentBusinessLogic = new HashMap<>();

    @Autowired
    public BookingService(BookingRepository repository) {
        this.repository = repository;
        departmentBusinessLogic.put("cool department", this::processCoolDepartment);
        departmentBusinessLogic.put("another_department", this::processAnotherDepartment);
    }

    private void processCoolDepartment(Booking booking) {
        // Example: Apply a 10% discount
        double discountRate = 0.10;
        double originalPrice = booking.getPrice();
        double discountedPrice = originalPrice - (originalPrice * discountRate);

        booking.setPrice(discountedPrice);

        // Log or notify stakeholders
        System.out.println("Applied a 10% discount to booking from 'cool department'. New Price: " + discountedPrice);
    }


    private void processAnotherDepartment(Booking booking) {
        // Example: Start subscription
        // This can be just a log statement as actual subscription logic might be complex
        System.out.println("Subscription started for booking in 'another_department'.");

        // Mock sending an email
        sendMockEmail(booking);
    }

    private void sendMockEmail(Booking booking) {
        // Mock email logic
        System.out.println("Sending welcome email to: " + booking.getEmail());
    }

    public String doBusiness(String bookingId) {
        Booking booking = getBookingById(bookingId);
        Consumer<Booking> businessLogic = departmentBusinessLogic.getOrDefault(booking.getDepartment(), b -> {
            throw new IllegalArgumentException("No business logic defined for department: " + b.getDepartment());
        });
        businessLogic.accept(booking);
        return "Business processed for department: " + booking.getDepartment();
    }

    public Booking createBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking(
                UUID.randomUUID().toString(), // Generate a new unique ID for the booking
                bookingDTO.getDescription(),
                bookingDTO.getPrice(),
                bookingDTO.getCurrency(),
                bookingDTO.getSubscriptionStartDate(),
                bookingDTO.getEmail(),
                bookingDTO.getDepartment()
        );
        return repository.save(booking);
    }

    public Booking updateBooking(String bookingId, BookingDTO bookingDTO) {
        Booking booking = getBookingById(bookingId);
        // Update booking fields from bookingDTO
        booking.setDescription(bookingDTO.getDescription());
        booking.setPrice(bookingDTO.getPrice());
        booking.setCurrency(bookingDTO.getCurrency());
        booking.setSubscriptionStartDate(bookingDTO.getSubscriptionStartDate());
        booking.setEmail(bookingDTO.getEmail());
        booking.setDepartment(bookingDTO.getDepartment());

        return repository.save(booking);
    }

    public Booking getBookingById(String bookingId) {
        return repository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + bookingId));
    }

    public List<Booking> getBookingsByDepartment(String department) {
        return repository.findByDepartment(department);
    }

    public Set<String> getAllUsedCurrencies() {
        return repository.findAll().stream()
                .map(Booking::getCurrency)
                .collect(Collectors.toSet());
    }

    public double getSumByCurrency(String currency) {
        return repository.findAll().stream()
                .filter(booking -> currency.equals(booking.getCurrency()))
                .mapToDouble(Booking::getPrice)
                .sum();
    }
}
