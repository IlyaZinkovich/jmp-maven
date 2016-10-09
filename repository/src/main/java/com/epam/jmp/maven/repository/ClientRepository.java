package com.epam.jmp.maven.repository;

import com.epam.jmp.maven.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
