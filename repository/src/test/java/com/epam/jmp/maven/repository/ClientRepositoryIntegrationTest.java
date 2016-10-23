package com.epam.jmp.maven.repository;

import com.epam.jmp.maven.TestRepositoryConfig;
import com.epam.jmp.maven.model.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
@Transactional
public class ClientRepositoryIntegrationTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @SqlGroup({
        @Sql(scripts = "/data.sql"),
        @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testFindAllClients() {
        Long testId = 1L;
        String testFirstName = "Bob";
        String testLastName = "Purson";
        Sort sort = new Sort(ASC, "id");

        List<Client> allClients = clientRepository.findAll(sort);

        assertEquals(5, allClients.size());
        Client first = allClients.get(0);
        assertEquals(testId, first.getId());
        assertEquals(testFirstName, first.getFirstName());
        assertEquals(testLastName, first.getLastName());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testSaveClient() {
        Client client = new Client();
        Long testId = 1L;
        String testFirstName = "Bob";
        String testLastName = "Purson";
        client.setFirstName(testFirstName);
        client.setLastName(testLastName);

        Client savedClient = clientRepository.save(client);

        assertNotNull(savedClient.getId());
        client.setId(savedClient.getId());

        Client clientFromDb = getClientFromDb();

        assertEquals(client, clientFromDb);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testFindClientById() {
        Long testId = 1L;

        Client client = clientRepository.findOne(testId);

        Client clientFromDb = getClientByIdFromDb(testId);

        assertEquals(client, clientFromDb);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testRemoveClientById() {
        Long testId = 1L;

        long clientsCountBeforeDeletion = clientRepository.count();

        clientRepository.delete(testId);

        long clientsCountAfterDeletion = clientRepository.count();

        assertEquals(1, clientsCountBeforeDeletion - clientsCountAfterDeletion);
        assertTrue(checkIfClientWithIdDoesNotExistInDb(testId));
    }

    private boolean checkIfClientWithIdDoesNotExistInDb(Long clientId) {
        List<Map<String, Object>> rows = jdbcTemplate
                .queryForList("SELECT ID, FIRST_NAME, LAST_NAME FROM CLIENT WHERE ID=?", clientId);
        return rows.isEmpty();
    }


    private Client getClientFromDb() {
        return jdbcTemplate
                .queryForObject("SELECT ID, FIRST_NAME, LAST_NAME FROM CLIENT",
                        (resultSet, i) -> extractClientsFromResultSet(resultSet));
    }

    private Client getClientByIdFromDb(Long testId) {
        return jdbcTemplate
                    .queryForObject("SELECT ID, FIRST_NAME, LAST_NAME FROM CLIENT WHERE ID=?",
                            new Object[]{ testId },
                            (resultSet, i) -> extractClientsFromResultSet(resultSet));
    }

    private Client extractClientsFromResultSet(ResultSet resultSet) throws SQLException {
        Client client = new Client();
        client.setId(resultSet.getLong("ID"));
        client.setFirstName(resultSet.getString("FIRST_NAME"));
        client.setLastName(resultSet.getString("LAST_NAME"));
        return client;
    }
}
