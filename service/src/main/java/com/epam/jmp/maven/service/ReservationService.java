package com.epam.jmp.maven.service;

import com.epam.jmp.maven.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {

    void createOrUpdateReservation(Reservation reservation);
    Reservation getReservationById(Long id);
    List<Reservation> findReservationsByDate(LocalDate date);
    List<Reservation> reservationAll();
    void removeReservationById(Long id);
}
