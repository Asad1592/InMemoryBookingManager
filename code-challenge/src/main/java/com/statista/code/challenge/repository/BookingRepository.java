package com.statista.code.challenge.repository;

import com.statista.code.challenge.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookingRepository {
    private final Map<String, Booking> bookings = new ConcurrentHashMap<>();

    public Booking save(Booking booking) {
        bookings.put(booking.getBookingId(), booking);
        return booking;
    }

    public Optional<Booking> findById(String bookingId) {
        return Optional.ofNullable(bookings.get(bookingId));
    }

    public List<Booking> findAll() {
        return new ArrayList<>(bookings.values());
    }

    public List<Booking> findByDepartment(String department) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getDepartment().equalsIgnoreCase(department)) {
                result.add(booking);
            }
        }
        return result;
    }

    public boolean existsById(String bookingId) {
        return bookings.containsKey(bookingId);
    }

    public void deleteById(String bookingId) {
        bookings.remove(bookingId);
    }
}
