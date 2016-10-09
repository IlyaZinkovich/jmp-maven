package com.epam.jmp.maven.repository;

import com.epam.jmp.maven.TestRepositoryConfig;
import com.epam.jmp.maven.model.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
public class ReservationRepositoryIntegrationTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void test() {
        long testClientId = 1L;
        String testFilmName = "testFilm";
        double testPrice = 20D;
        int testSeat = 12;
        Reservation reservation = new Reservation();
        reservation.setClient(clientRepository.findOne(testClientId));
        reservation.setDate(LocalDate.now());
        reservation.setFilmName(testFilmName);
        reservation.setPrice(testPrice);
        reservation.setSeat(testSeat);
        reservationRepository.save(reservation);
        Reservation persistedReservation = reservationRepository.findOne(reservation.getNumber());
        assertEquals(reservation, persistedReservation);
    }
}
