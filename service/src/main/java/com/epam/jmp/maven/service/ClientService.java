package com.epam.jmp.maven.service;

import com.epam.jmp.maven.model.Client;

import java.util.List;

public interface ClientService {

    void createOrUpdateClient(Client client);
    List<Client> findAll();
    Client getClientById(Long id);
    void removeClientById(Long id);
}
