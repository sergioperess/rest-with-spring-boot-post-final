package com.example.restwithspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restwithspringboot.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    
}
