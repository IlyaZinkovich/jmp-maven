package com.epam.jmp.maven.repository;

import com.epam.jmp.maven.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByDate(LocalDate date);
}
