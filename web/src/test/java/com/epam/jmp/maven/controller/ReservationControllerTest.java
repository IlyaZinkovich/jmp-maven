package com.epam.jmp.maven.controller;

import com.epam.jmp.maven.model.Reservation;
import com.epam.jmp.maven.service.ReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.time.LocalDate.now;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservationControllerTest {

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private ReservationService reservationService;

    @Test
    public void testGetReservationList() {
        LocalDate testDate = now();
        String testReservationDate = testDate.toString();
        int testReservationCount = 3;
        List<Reservation> testReservations = IntStream.range(1, testReservationCount + 1)
                .mapToObj(i -> new Reservation()).collect(Collectors.toList());
        when(reservationService.findReservationsByDate(testDate)).thenReturn(testReservations);

        List<Reservation> reservations = reservationController.getReservationList(testReservationDate);

        assertEquals(testReservations, reservations);
        verify(reservationService).findReservationsByDate(testDate);
    }

    @Test
    public void testGetReservationList_nullDate() {
        String testReservationDate = null;
        int testReservationCount = 3;
        List<Reservation> testReservations = IntStream.range(1, testReservationCount + 1)
                .mapToObj(i -> new Reservation()).collect(Collectors.toList());
        when(reservationService.findAllReservations()).thenReturn(testReservations);

        List<Reservation> reservations = reservationController.getReservationList(testReservationDate);

        assertEquals(testReservations, reservations);
        verify(reservationService).findAllReservations();
    }

    @Test
    public void testCreateReservation() {
        Long testReservationNumber = 1L;
        Reservation testReservation = new Reservation();
        doAnswer(invocationOnMock ->
                modifyReservationArgumentSetNumber(testReservationNumber, invocationOnMock.getArguments()[0]))
                .when(reservationService).createOrUpdateReservation(testReservation);

        Long reservationNumber = reservationController.createReservation(testReservation);

        assertEquals(testReservationNumber, reservationNumber);
        verify(reservationService).createOrUpdateReservation(testReservation);
    }

    @Test
    public void testUpdateReservation() {
        Long testReservationNumber = 1L;
        Reservation testReservation = new Reservation();
        doNothing().when(reservationService).createOrUpdateReservation(testReservation);

        reservationController.updateReservation(testReservation, testReservationNumber);

        assertEquals(testReservationNumber, testReservation.getNumber());
        verify(reservationService).createOrUpdateReservation(testReservation);
    }

    @Test
    public void testGetReservationById() {
        Long testReservationNumber = 1L;
        Reservation testReservation = new Reservation();
        when(reservationService.getReservationById(testReservationNumber)).thenReturn(testReservation);

        Reservation reservation = reservationController.getReservationById(testReservationNumber);

        assertEquals(testReservation, reservation);
        verify(reservationService).getReservationById(testReservationNumber);
    }

    @Test
    public void testRemoveReservationById() {
        Long testReservationNumber = 1L;
        Reservation testReservation = new Reservation();
        doNothing().when(reservationService).removeReservationById(testReservationNumber);

        reservationController.removeReservationById(testReservationNumber);

        verify(reservationService).removeReservationById(testReservationNumber);
    }

    private Reservation modifyReservationArgumentSetNumber(Long testReservationNumber, Object o) {
        Reservation reservation = (Reservation) o;
        reservation.setNumber(testReservationNumber);
        return reservation;
    }
}
