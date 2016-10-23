package com.epam.jmp.maven.controller;

import com.epam.jmp.maven.exception.ClientNotFoundException;
import com.epam.jmp.maven.model.Client;
import com.epam.jmp.maven.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    @Test
    public void testGetClientList() {
        int clientsCount = 3;
        List<Client> testClientList = IntStream.range(1, clientsCount + 1)
                .mapToObj(i -> new Client())
                .collect(Collectors.toList());
        when(clientService.findAll()).thenReturn(testClientList);

        List<Client> clientList = clientController.getClientList();

        assertEquals(testClientList, clientList);
        verify(clientService).findAll();
    }

    @Test
    public void testCreateClient() {
        Client client = new Client();
        doNothing().when(clientService).createOrUpdateClient(client);

        clientController.createClient(client);

        verify(clientService).createOrUpdateClient(client);
    }

    @Test
    public void testUpdateClient() {
        Client client = mock(Client.class);
        Long testClientId = 1L;
        doNothing().when(clientService).createOrUpdateClient(client);

        clientController.updateClient(client, testClientId);

        verify(client).setId(testClientId);
        client.setId(testClientId);
        verify(clientService).createOrUpdateClient(client);
    }

    @Test
    public void testGetClientById() {
        Client testClient = new Client();
        Long testClientId = 1L;
        when(clientService.getClientById(testClientId)).thenReturn(testClient);

        Client client = clientController.getClientById(testClientId);

        assertEquals(testClient, client);
        verify(clientService).getClientById(testClientId);
    }

    @Test(expected = ClientNotFoundException.class)
    public void testGetClientById_nullId() {
        Long testClientId = 1L;

        clientController.getClientById(testClientId);
    }

    @Test
    public void testRemoveClientById() {
        Long testClientId = 1L;
        doNothing().when(clientService).removeClientById(testClientId);

        clientController.removeClientById(testClientId);

        verify(clientService).removeClientById(testClientId);
    }
}
