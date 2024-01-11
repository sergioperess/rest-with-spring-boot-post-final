package com.example.restwithspringboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.restwithspringboot.data.security.AccountCredentialsVO;
import com.example.restwithspringboot.data.security.TokenVO;
import com.example.restwithspringboot.repository.UserRepository;
import com.example.restwithspringboot.security.jwt.JwtTockenProvider;

@Service
public class AuthServices {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTockenProvider jwtTockenProvider;

    @Autowired
    private UserRepository repository;
    
    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentialsVO data){
        try {
            var username = data.getUserName();
            var password = data.getPassword();
            
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            var user = repository.findByUserName(username);

            var tokenResponse = new TokenVO();

            if (user != null) {
                tokenResponse = jwtTockenProvider.createAccessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Usuário " + username +" não encontrado");
            }
            return ResponseEntity.ok(tokenResponse);            
        } catch (Exception e) {
            throw new BadCredentialsException("Username ou senha inválidos");
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String username, String refreshToken){
        var user = repository.findByUserName(username);

        var tokenResponse = new TokenVO();

        if (user != null) {
            tokenResponse = jwtTockenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Usuário " + username +" não encontrado");
        }
        return ResponseEntity.ok(tokenResponse); 
    }
}
