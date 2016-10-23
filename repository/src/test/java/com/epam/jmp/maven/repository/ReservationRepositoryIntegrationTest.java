package com.epam.jmp.maven.repository;

import com.epam.jmp.maven.TestRepositoryConfig;
import com.epam.jmp.maven.model.Client;
import com.epam.jmp.maven.model.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
public class ReservationRepositoryIntegrationTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testSaveReservation() {
        String testFilmName = "testFilm";
        double testPrice = 20D;
        int testSeat = 12;
        Reservation testReservation = new Reservation();
        testReservation.setDate(LocalDate.now());
        testReservation.setFilmName(testFilmName);
        testReservation.setPrice(testPrice);
        testReservation.setSeat(testSeat);
        Long testClientId = 1L;
        String testClientFirstName = "Bob";
        String testClientLastName = "Purson";
        Client testClient = new Client();
        testClient.setId(testClientId);
        testClient.setFirstName(testClientFirstName);
        testClient.setLastName(testClientLastName);
        testReservation.setClient(testClient);

        Reservation reservation = reservationRepository.save(testReservation);
        Long reservationNumber = reservation.getNumber();
        assertNotNull(reservationNumber);

        Reservation reservationFromDb = getReservationByNumberFromDb(reservationNumber);
        assertEquals(reservation, reservationFromDb);

        Long reservationClientIdFromDb = getReservationClientIdFromDb(reservationNumber);

        assertEquals(testClientId, reservationClientIdFromDb);
    }

    private Long getReservationClientIdFromDb(Long reservationNumber) {
        return jdbcTemplate.queryForObject("SELECT CLIENT_ID FROM RESERVATION WHERE NUMBER=?", Long.class,
                    reservationNumber);
    }

    private Reservation getReservationByNumberFromDb(Long reservationNumber) {
        return jdbcTemplate.queryForObject("SELECT * FROM RESERVATION WHERE NUMBER=?", new Object[]{reservationNumber},
                    (resultSet, i) -> extractReservationFromResultSet(resultSet));
    }

    private Reservation extractReservationFromResultSet(ResultSet resultSet) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setNumber(resultSet.getLong("NUMBER"));
        reservation.setDate(resultSet.getDate("DATE").toLocalDate());
        reservation.setSeat(resultSet.getInt("SEAT"));
        reservation.setFilmName(resultSet.getString("FILM_NAME"));
        reservation.setPrice(resultSet.getDouble("PRICE"));
        return reservation;
    }
}
