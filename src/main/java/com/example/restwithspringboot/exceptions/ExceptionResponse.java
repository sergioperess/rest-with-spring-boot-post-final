package com.example.restwithspringboot.exceptions;

import java.io.Serializable;
import java.util.Date;

// Utilizado para serealizar um objeto, ou seja, trasnformados em uma sequencia de bytes
public class ExceptionResponse implements Serializable{
    private static final long serialVersionUID = 1L;
    // Utilizado para saber a hora do erro
    private Date timestamp;
    private String message;
    private String details;

    public ExceptionResponse(Date timestamp, String message, String details){
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }  
}
