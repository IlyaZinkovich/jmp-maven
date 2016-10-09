package com.epam.jmp.maven.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Client Not Found")
public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(Long id){
        super("ClientNotFoundException with id=" + id);
    }
}
