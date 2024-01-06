package com.example.restwithspringboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Retorna um código de erro
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

     private static final long serialVersionUID = 1L;
     public ResourceNotFoundException(String ex){
          super(ex);
     }
   
}