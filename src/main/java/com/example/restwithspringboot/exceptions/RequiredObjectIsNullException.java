package com.example.restwithspringboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Retorna um código de erro
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException{

     private static final long serialVersionUID = 1L;
     public RequiredObjectIsNullException(String ex){
          super(ex);
     }

      public RequiredObjectIsNullException(){
          super("Não é permitido persistir um objeto nulo");
     }
     
   
}