package com.example.restwithspringboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

// Retorna um código de erro
@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtAuthenticationException extends AuthenticationException{

     private static final long serialVersionUID = 1L;
     public InvalidJwtAuthenticationException(String ex){
          super(ex);
     }
   
}