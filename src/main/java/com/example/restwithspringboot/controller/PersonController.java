package com.example.restwithspringboot.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restwithspringboot.data.PersonVO;
import com.example.restwithspringboot.services.PersonServices;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.restwithspringboot.util.MediaType;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

        
    // Com isso o spring cuida da instanciação da variavel em tempo de execução
    // Precisa da utilização da anotation @Service
    @Autowired
    private PersonServices service;

    @GetMapping(produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,  MediaType.APPLICATION_YAML})
    public List<PersonVO> findAll(){
            return service.findAll();
    }

    // Entre chaves é necessário passar parâmetros obrigatórios
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML , MediaType.APPLICATION_YAML})
    public PersonVO findById(@PathVariable(value = "id") Long id){
            return service.findById(id);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML , MediaType.APPLICATION_YAML},
                produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,  MediaType.APPLICATION_YAML})
    public PersonVO create(@RequestBody PersonVO PersonVO){
            return service.create(PersonVO);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML , MediaType.APPLICATION_YAML},
                produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML , MediaType.APPLICATION_YAML})
    public PersonVO update(@RequestBody PersonVO PersonVO){
            return service.update(PersonVO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
            service.delete(id);

            // Retorna o status code correto, nesse caso o status 204
            return ResponseEntity.noContent().build();
    }

   
   
}
