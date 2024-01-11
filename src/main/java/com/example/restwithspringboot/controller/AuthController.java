package com.example.restwithspringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.restwithspringboot.data.security.AccountCredentialsVO;
import com.example.restwithspringboot.services.AuthServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;


@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthServices authServices;

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Autenticação do usuário e retorna o token")
    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialsVO data){
        if (checkIfParamsIsNotNull(data)) 
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");

        var token = authServices.signin(data);

        if (token == null) 
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");            
        
        return token;
    }

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Refresh Token para autenticação de usuuário")
    @PutMapping(value = "/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable ("username") String username,
            @RequestHeader ("Authorization") String refreshToken){
        if (checkIfParamsIsNotNull(username, refreshToken)) 
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");

        var token = authServices.refreshToken(username, refreshToken);

        if (token == null) 
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");            
        
        return token;
    }

    private Boolean checkIfParamsIsNotNull(AccountCredentialsVO data){
        return data == null || data.getUserName() == null || data.getUserName().isBlank()
        || data.getPassword() == null || data.getPassword().isBlank();
    }

    private Boolean checkIfParamsIsNotNull(String username, String refreshToken){
        return refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank();
    }


}
