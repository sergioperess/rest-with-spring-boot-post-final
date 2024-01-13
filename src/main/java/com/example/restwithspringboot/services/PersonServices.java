package com.example.restwithspringboot.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
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

import jakarta.transaction.Transactional;

// Utilizado pelo spring para injetar dados em outras classes durante a aplicação
@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PagedResourcesAssembler<PersonVO> assembler;

    public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable){
        logger.info("Finding All people");
        
        var personPage = repository.findAll(pageable);

        var personVosPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));

        personVosPage.map(p -> p.add(linkTo(methodOn(PersonController.class).
        findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class).findAll(pageable.getPageNumber()
            , pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(personVosPage, link);
    }

    public PagedModel<EntityModel<PersonVO>> findPeopleByName(String firstname, Pageable pageable){
        logger.info("Finding All people");
        
        var personPage = repository.findPeopleByName(firstname, pageable);

        var personVosPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));

        personVosPage.map(p -> p.add(linkTo(methodOn(PersonController.class).
        findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class).findAll(pageable.getPageNumber()
            , pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(personVosPage, link);
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


    // Necessário utiliza-lo pois está sendo feito uma modificação no banco
    @Transactional
    public PersonVO disablePerson(Long id){
        logger.info("Disabling one person");

        repository.disablePerson(id);

        var entity = repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));

        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }
}
