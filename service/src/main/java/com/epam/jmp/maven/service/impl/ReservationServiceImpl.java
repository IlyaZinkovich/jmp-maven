package com.epam.jmp.maven.service.impl;

import com.epam.jmp.maven.model.Reservation;
import com.epam.jmp.maven.repository.ReservationRepository;
import com.epam.jmp.maven.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public void createOrUpdateReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    @Override
    public Reservation getReservationById(Long id) {
        return reservationRepository.findOne(id);
    }

    @Override
    public List<Reservation> findReservationsByDate(LocalDate date) {
        return reservationRepository.findByDate(date);
    }

    @Override
    public List<Reservation> reservationAll() {
        return reservationRepository.findAll();
    }

    @Override
    public void removeReservationById(Long id) {
        reservationRepository.delete(id);
    }
}
