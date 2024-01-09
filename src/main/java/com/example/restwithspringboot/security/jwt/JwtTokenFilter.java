package com.example.restwithspringboot.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenFilter extends GenericFilterBean{

    @Autowired
    private JwtTockenProvider tockenProvider;

    public JwtTokenFilter(JwtTockenProvider tockenProvider) {
        this.tockenProvider = tockenProvider;
    }

    // Obtem o token e depois valida, se houver validação, o token será autenticado e 
    // setado no spring
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = tockenProvider.resolveToken((HttpServletRequest) request);

        if (token != null && tockenProvider.validateToken(token)) {
            Authentication auth = tockenProvider.getAuthentication(token);
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }
    
}
