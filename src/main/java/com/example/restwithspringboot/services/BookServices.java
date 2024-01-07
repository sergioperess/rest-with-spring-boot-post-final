package com.example.restwithspringboot.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restwithspringboot.controller.BookController;
import com.example.restwithspringboot.data.BookVO;
import com.example.restwithspringboot.exceptions.RequiredObjectIsNullException;
import com.example.restwithspringboot.exceptions.ResourceNotFoundException;
import com.example.restwithspringboot.mapper.DozerMapper;
import com.example.restwithspringboot.model.Book;
import com.example.restwithspringboot.repository.BookRepository;



@Service
public class BookServices {
    
    private Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository repository;

    public List<BookVO> findAll(){
        logger.info("Find all books");

        var books = DozerMapper.parseListObject(repository.findAll(), BookVO.class);
        books.stream().forEach(p -> p.add(linkTo(methodOn(BookController.class).
        findById(p.getKey())).withSelfRel()));

        return books;
    }

    public BookVO findById(Long id){
        logger.info("Finding a book");

        // Caso o id não for encontrado
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id Não encontrado"));

        var book = DozerMapper.parseObject(entity, BookVO.class);
        book.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());

        return book;
    }

    public BookVO update(BookVO book){
        if(book == null) throw new RequiredObjectIsNullException();

        logger.info("Updating a book");

        // Caso o id não for encontrado
        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("Id Não encontrado"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO create(BookVO book){
        if(book == null) throw new RequiredObjectIsNullException();

        logger.info("Creating a book");

        var entity = DozerMapper.parseObject(book, Book.class);                
        var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void delete(Long id){
        logger.info("Deleting a book");

        var entity = repository.findById(id)
                .orElseThrow(() -> new RequiredObjectIsNullException("Id não encontrado"));

        repository.delete(entity);

    }
}
