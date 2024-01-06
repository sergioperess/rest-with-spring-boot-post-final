package com.example.restwithspringboot.services;


import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.restwithspringboot.controller.PersonController;
import com.example.restwithspringboot.data.PersonVO;
import com.example.restwithspringboot.model.Person;
import com.example.restwithspringboot.exceptions.RequiredObjectIsNullException;
import com.example.restwithspringboot.exceptions.ResourceNotFoundException;
import com.example.restwithspringboot.mapper.DozerMapper;
import com.example.restwithspringboot.repository.PersonRepository;

// Utilizado pelo spring para injetar dados em outras classes durante a aplicação
@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<PersonVO> findAll(){
        logger.info("Finding All people");
        
        var people = DozerMapper.parseListObject(repository.findAll(), PersonVO.class);
        people.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).
        findById(p.getKey())).withSelfRel()));

        return people;
    }

    public PersonVO create(PersonVO person){
        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one person");
        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO person){
        if(person == null) throw new RequiredObjectIsNullException();
        
        logger.info("Updating one people");

        // Procura a pessoa pelo Id e após achar coloca em um atributo
        // para poder mudar os atributos
        var entity = repository.findById(person.getKey())
                        .orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setGender(person.getGender());
        entity.setAdress(person.getAdress());

        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id){
        logger.info("Deleting one people");

        var entity = repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));

        repository.delete(entity);
    }

    public PersonVO findById(Long id){
        logger.info("Finding one person");

        var entity = repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));

        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }
}
