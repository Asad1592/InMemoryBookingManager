package com.statista.code.challenge.repository;

import com.statista.code.challenge.model.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
/**
 * BookingRepositoryTest contains test cases for the BookingRepository class.
 * It verifies the functionality of in-memory booking storage and retrieval operations.
 */
public class BookingRepositoryTest {

    private BookingRepository repository;

    @BeforeEach
    void setUp() {
        repository = new BookingRepository();
    }

    @Test
    void saveAndFindById() {
        Booking booking = new Booking("1", "Test Booking", 100.0, "USD", new Date(), "test@email.com", "department");
        repository.save(booking);

        Booking found = repository.findById("1").orElse(null);
        assertNotNull(found);
        assertEquals("Test Booking", found.getDescription());
    }

    @Test
    void findByDepartment() {
        repository.save(new Booking("1", "Test Booking", 100.0, "USD", new Date(), "test@email.com", "department"));
        List<Booking> bookings = repository.findByDepartment("department");

        assertFalse(bookings.isEmpty());
        assertEquals(1, bookings.size());
    }

    // Additional tests for findAll, deleteById, etc.
}