package com.statista.code.challenge.service;

import com.statista.code.challenge.dto.BookingDTO;
import com.statista.code.challenge.exception.BookingNotFoundException;
import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Date;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * BookingServiceTest comprises unit tests for the BookingService class.
 * It mocks the BookingRepository and tests the business logic of the service layer.
 */
class BookingServiceTest {

    @Mock
    private BookingRepository repository;

    @InjectMocks
    private BookingService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBooking() {
        BookingDTO dto = new BookingDTO(null, "Test", 100.0, "USD", new Date(), "test@email.com", "department");
        when(repository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Booking booking = service.createBooking(dto);

        assertNotNull(booking.getBookingId());
        assertEquals(dto.getDescription(), booking.getDescription());
    }

    @Test
    void getBookingByIdNotFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        assertThrows(BookingNotFoundException.class, () -> service.getBookingById("1"));
    }

    // Additional tests for updateBooking, getBookingsByDepartment, etc.
}
