package com.epam.jmp.maven.controller;

import com.epam.jmp.maven.model.Reservation;
import com.epam.jmp.maven.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reservations", headers = "Accept=application/json;charset=utf-8")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Reservation> getReservationList(@RequestParam(name = "date") String date) {
        if (date == null) return reservationService.reservationAll();
        return reservationService.findReservationsByDate(LocalDate.parse(date));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long createReservation(@RequestBody Reservation reservation) {
        reservationService.createOrUpdateReservation(reservation);
        return reservation.getNumber();
    }

    @RequestMapping(path = "/{number}", method = RequestMethod.PUT)
    public void updateReservation(@RequestBody Reservation reservation, @PathVariable(value = "number") Long number) {
        reservation.setNumber(number);
        reservationService.createOrUpdateReservation(reservation);
    }

    @RequestMapping(path = "/{number}", method = RequestMethod.GET)
    public Reservation getReservationById(@PathVariable(value = "number") Long number) {
        return reservationService.getReservationById(number);
    }

    @RequestMapping(path = "/{number}", method = RequestMethod.DELETE)
    public void removeReservationById(@PathVariable(value = "number") Long number) {
        reservationService.removeReservationById(number);
    }
}
