package com.epam.jmp.maven.service.impl;

import com.epam.jmp.maven.model.Client;
import com.epam.jmp.maven.repository.ClientRepository;
import com.epam.jmp.maven.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void createOrUpdateClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findOne(id);
    }

    @Override
    public void removeClientById(Long id) {
        clientRepository.delete(id);
    }
}
