package com.example.restwithspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restwithspringboot.model.Person;

public interface PersonRepository extends JpaRepository<Person,Long> {
    
}
