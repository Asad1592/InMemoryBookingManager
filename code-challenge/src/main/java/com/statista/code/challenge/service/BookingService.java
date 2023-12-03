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
/**
 * The BookingService class is responsible for handling the business logic associated with booking operations.
 * It interacts with the BookingRepository to perform CRUD operations and contains specialized business logic for different departments.
 */
@Service
public class BookingService {

    private final BookingRepository repository;
    private final Map<String, Consumer<Booking>> departmentBusinessLogic = new HashMap<>();

    /**
     * Constructs a BookingService with the necessary repository.
     * Initializes the department-specific business logic.
     *
     * @param repository The repository used for booking data operations.
     */
    @Autowired
    public BookingService(BookingRepository repository) {
        this.repository = repository;
        departmentBusinessLogic.put("cool department", this::processCoolDepartment);
        departmentBusinessLogic.put("another_department", this::processAnotherDepartment);
    }
    /**
     * Business logic for processing bookings from the "cool department".
     * Example logic includes applying discounts to the booking price.
     *
     * @param booking The booking to process.
     */
    private void processCoolDepartment(Booking booking) {
        // Applying a 10% discount
        double discountRate = 0.10;
        double originalPrice = booking.getPrice();
        double discountedPrice = originalPrice - (originalPrice * discountRate);

        booking.setPrice(discountedPrice);

        // Notifying stakeholders
        System.out.println("Applied a 10% discount to booking from 'cool department'. New Price: " + discountedPrice);
    }

    /**
     * Business logic for processing bookings from "another_department".
     * Example logic includes starting subscriptions and sending mock emails.
     *
     * @param booking The booking to process.
     */
    private void processAnotherDepartment(Booking booking) {
        // Example: Start subscription
        // This can be just a log statement
        System.out.println("Subscription started for booking in 'another_department'.");

        // Mock sending an email
        sendMockEmail(booking);
    }

    private void sendMockEmail(Booking booking) {
        // Mock email logic
        System.out.println("Sending welcome email to: " + booking.getEmail());
    }

    /**
     * Executes department-specific business logic for a given booking.
     *
     * @param bookingId The ID of the booking for which business logic is executed.
     * @return A string indicating the result of the business operation.
     */
    public String doBusiness(String bookingId) {
        Booking booking = getBookingById(bookingId);
        Consumer<Booking> businessLogic = departmentBusinessLogic.getOrDefault(booking.getDepartment(), b -> {
            throw new IllegalArgumentException("No business logic defined for department: " + b.getDepartment());
        });
        businessLogic.accept(booking);
        return "Business processed for department: " + booking.getDepartment();
    }
    /**
     * Creates and saves a new booking based on the provided BookingDTO.
     *
     * @param bookingDTO The DTO containing booking information.
     * @return The saved Booking entity.
     */
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

    /**
     * Updates an existing booking with new information provided in BookingDTO.
     *
     * @param bookingId  The ID of the booking to update.
     * @param bookingDTO The DTO containing updated booking information.
     * @return The updated Booking entity.
     */
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
    /**
     * Retrieves a booking by its ID.
     *
     * @param bookingId The ID of the booking to retrieve.
     * @return The found Booking entity.
     * @throws BookingNotFoundException if the booking is not found.
     */
    public Booking getBookingById(String bookingId) {
        return repository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + bookingId));
    }
    /**
     * Retrieves all bookings belonging to a specific department.
     *
     * @param department The name of the department.
     * @return A list of Bookings from the specified department.
     */
    public List<Booking> getBookingsByDepartment(String department) {
        return repository.findByDepartment(department);
    }

    /**
     * Retrieves a set of all unique currencies used in existing bookings.
     *
     * @return A set of currency strings.
     */
    public Set<String> getAllUsedCurrencies() {
        return repository.findAll().stream()
                .map(Booking::getCurrency)
                .collect(Collectors.toSet());
    }
    /**
     * Calculates the total sum of bookings for a given currency.
     *
     * @param currency The currency for which the sum is calculated.
     * @return The total sum of all bookings in the given currency.
     */
    public double getSumByCurrency(String currency) {
        return repository.findAll().stream()
                .filter(booking -> currency.equals(booking.getCurrency()))
                .mapToDouble(Booking::getPrice)
                .sum();
    }
}
