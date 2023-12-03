package com.statista.code.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statista.code.challenge.dto.BookingDTO;
import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Date;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * BookingControllerTest includes test cases for the BookingController class.
 * It uses MockMvc to simulate HTTP requests and assert the responses for various endpoints.
 */
@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @Mock
    private BookingService service;

    @InjectMocks
    private BookingController controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createBooking() throws Exception {
        BookingDTO dto = new BookingDTO(null, "Test Booking", 100.0, "USD", new Date(), "test@email.com", "department");
        Booking booking = new Booking("1", dto.getDescription(), dto.getPrice(), dto.getCurrency(), dto.getSubscriptionStartDate(), dto.getEmail(), dto.getDepartment());

        when(service.createBooking(any())).thenReturn(booking);

        mockMvc.perform(post("/bookingservice/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId", is("1")))
                .andExpect(jsonPath("$.description", is("Test Booking")));
    }
}
