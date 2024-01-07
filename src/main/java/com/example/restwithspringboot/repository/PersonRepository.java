package com.example.restwithspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restwithspringboot.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    
}
