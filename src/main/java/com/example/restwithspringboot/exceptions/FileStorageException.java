package com.example.restwithspringboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Retorna um código de erro
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileStorageException extends RuntimeException{

     private static final long serialVersionUID = 1L;
     public FileStorageException(String ex){
          super(ex);
     }

     public FileStorageException(String ex, Throwable cause){
          super(ex, cause);
     }
   
}