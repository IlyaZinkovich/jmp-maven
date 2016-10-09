package com.epam.jmp.maven.controller;

import com.epam.jmp.maven.exception.ClientNotFoundException;
import com.epam.jmp.maven.model.Client;
import com.epam.jmp.maven.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/api/v1/clients", headers = "Accept=application/json;charset=utf-8")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(method = GET)
    public List<Client> getClientList() {
        return clientService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long createClient(@RequestBody Client client) {
        clientService.createOrUpdateClient(client);
        return client.getId();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void updateClient(@RequestBody Client client, @PathVariable(value = "id") Long id) {
        client.setId(id);
        clientService.createOrUpdateClient(client);
    }

    @RequestMapping(path = "/{id}", method = GET)
    public Client getClientById(@PathVariable(value = "id") Long id) {
        Client client = clientService.getClientById(id);
        if (client == null) throw new ClientNotFoundException(id);
        return client;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void removeClientById(@PathVariable(value = "id") Long id) {
        clientService.removeClientById(id);
    }
}
