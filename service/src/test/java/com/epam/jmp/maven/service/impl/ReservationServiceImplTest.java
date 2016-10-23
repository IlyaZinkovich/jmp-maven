package com.epam.jmp.maven.service.impl;

import antlr.collections.impl.IntRange;
import com.epam.jmp.maven.model.Reservation;
import com.epam.jmp.maven.repository.ReservationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.time.LocalDate.now;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceImplTest {

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Test
    public void testCreateOrUpdateReservation() {
        Reservation testReservation = new Reservation();
        Reservation reservationWithId = new Reservation();
        Long reservationId = 1L;
        reservationWithId.setNumber(reservationId);
        when(reservationRepository.save(testReservation)).thenReturn(testReservation);

        reservationService.createOrUpdateReservation(testReservation);

        verify(reservationRepository).save(testReservation);
    }

    @Test
    public void testFindReservationByDate() {
        LocalDate testDate = now();
        int reservationsCount = 3;
        List<Reservation> testReservations = IntStream.range(1, reservationsCount + 1)
                .mapToObj(i -> createReservationWithPredefinedDate(testDate))
                .collect(Collectors.toList());
        when(reservationRepository.findByDate(testDate)).thenReturn(testReservations);

        List<Reservation> reservations = reservationService.findReservationsByDate(testDate);

        assertEquals(testReservations, reservations);
        verify(reservationRepository).findByDate(testDate);
    }

    @Test
    public void testGetReservationById() {
        Reservation testReservation = new Reservation();
        Long reservationId = 1L;
        when(reservationRepository.findOne(reservationId)).thenReturn(testReservation);

        Reservation reservation = reservationService.getReservationById(reservationId);

        assertEquals(testReservation, reservation);
        verify(reservationRepository).findOne(reservationId);
    }

    @Test
    public void testRemoveReservationById() {
        Long reservationId = 1L;
        doNothing().when(reservationRepository).delete(reservationId);

        reservationService.removeReservationById(reservationId);

        verify(reservationRepository).delete(reservationId);
    }

    @Test
    public void testFindAllReservations() {
        int reservationsCount = 3;
        List<Reservation> testReservations = IntStream.range(1, reservationsCount + 1)
                .mapToObj(i -> new Reservation())
                .collect(Collectors.toList());
        when(reservationRepository.findAll()).thenReturn(testReservations);

        List<Reservation> reservations = reservationService.findAllReservations();

        assertEquals(testReservations, reservations);
        verify(reservationRepository).findAll();
    }

    private Reservation createReservationWithPredefinedDate(LocalDate date) {
        Reservation reservation = new Reservation();
        reservation.setDate(date);
        return reservation;
    }
}
