package com.epam.jmp.maven.service.impl;

import com.epam.jmp.maven.model.Client;
import com.epam.jmp.maven.repository.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Collections.singletonList;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientRepository clientRepository;

    @Test
    public void testFindAllClients() {
        Client client = new Client();
        List<Client> clients = singletonList(client);
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> allClients = clientService.findAll();

        assertEquals(clients, allClients);
        verify(clientRepository).findAll();
    }

    @Test
    public void testCreateOrUpdateClient() {
        Long testClientId = 1L;
        Client client = new Client();
        Client clientWithId = new Client();
        client.setId(testClientId);
        when(clientRepository.save(client)).thenReturn(clientWithId);

        clientService.createOrUpdateClient(client);

        assertEquals(testClientId, client.getId());
        verify(clientRepository).save(client);
    }

    @Test
    public void testGetClientById() {
        Long testClientId = 1L;
        Client client = new Client();
        client.setId(testClientId);
        when(clientRepository.findOne(testClientId)).thenReturn(client);

        Client returnedClient = clientService.getClientById(testClientId);

        assertEquals(client, returnedClient);
        verify(clientRepository).findOne(testClientId);
    }

    @Test
    public void test() {
        Long testClientId = 1L;
        doNothing().when(clientRepository).delete(testClientId);

        clientService.removeClientById(testClientId);

        verify(clientRepository).delete(testClientId);
    }
}
