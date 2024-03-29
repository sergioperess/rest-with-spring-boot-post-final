package com.example.restwithspringboot.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.restwithspringboot.data.BookVO;
import com.example.restwithspringboot.data.PersonVO;
import com.example.restwithspringboot.model.Book;
import com.example.restwithspringboot.model.Person;


public class DozerMapper {
    private static ModelMapper mapper = new ModelMapper();

    static{
        mapper.createTypeMap(
            Person.class, PersonVO.class)
            .addMapping(Person::getId, PersonVO::setKey);
        
         mapper.createTypeMap(
            PersonVO.class, Person.class)
            .addMapping(PersonVO::getKey, Person::setId);
    }

    static{
        mapper.createTypeMap(
            Book.class, BookVO.class)
            .addMapping(Book::getId, BookVO::setKey);
        
         mapper.createTypeMap(
            BookVO.class, Book.class)
            .addMapping(BookVO::getKey, Book::setId);
    }

    

    // O -> origem; D -> destino
    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObject(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<D>();

        for (O o : origin) {
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }
}
