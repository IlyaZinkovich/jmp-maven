package com.epam.jmp.maven.repository;

import com.epam.jmp.maven.TestRepositoryConfig;
import com.epam.jmp.maven.model.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
@Transactional
public class ClientRepositoryIntegrationTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @SqlGroup({
        @Sql(scripts = "/data.sql"),
        @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void test() {
        List<Client> allClients = clientRepository.findAll();
        assertEquals(5, allClients.size());
    }
}
