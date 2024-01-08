package com.example.restwithspringboot.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.restwithspringboot.data.BookVO;
import com.example.restwithspringboot.services.BookServices;
import com.example.restwithspringboot.util.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Books", description = "Endpoints for Managing Books")
public class BookController {
    @Autowired
    BookServices service;

    @GetMapping(produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,  MediaType.APPLICATION_YAML})
    @Operation(summary = "Listar todos os livros", description = "Listar todos os livros",
        tags = {"Books"},
        responses = {
                @ApiResponse(description = "Success", responseCode = "200",
                        content = { 
                                @Content(
                                        mediaType = "application/json",
                                        array = @ArraySchema(schema = @Schema(implementation = BookVO.class))
                                )
                                        
                        }),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content), 
                @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content), 
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content)       
        }   
    )
    public List<BookVO> findAll(){
        return service.findAll();
    }

    @GetMapping(value = {"/{id}"},
        produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,  MediaType.APPLICATION_YAML})
    @Operation(summary = "Listar uma pessoa pelo id", description = "Listar uma pessoa pelo id",
        tags = {"Books"},
        responses = {
                @ApiResponse(description = "Success", responseCode = "200",
                        content = @Content(schema = @Schema(implementation = BookVO.class))
                ),
                @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content), 
                @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content), 
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content)       
        }
    )
    public BookVO findById(@PathVariable(value = "id") Long id){
            return service.findById(id);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,  MediaType.APPLICATION_YAML},
                produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,  MediaType.APPLICATION_YAML})
    @Operation(summary = "Adcionar uma pessoa", description = "Adicionar uma pessoa passando JSON, XML ou YAML",
        tags = {"Books"},
        responses = {
            @ApiResponse(description = "Created", responseCode = "200",
                content = @Content(schema = @Schema(implementation = BookVO.class))
                ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content), 
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content)       
        }
    )   
    public BookVO create(@RequestBody BookVO book){
        return service.create(book);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,  MediaType.APPLICATION_YAML},
                produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML ,  MediaType.APPLICATION_YAML})

    @Operation(summary = "Atualizar uma informação da pessoa pelo id", description = "Atualizar uma informação da pessoa pelo id",
            tags = {"Books"},
            responses = {
                @ApiResponse(description = "Updated", responseCode = "200",
                        content = @Content(schema = @Schema(implementation = BookVO.class))
                    ),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content), 
                @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content), 
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content)       
            }
    ) 
    public BookVO update(@RequestBody BookVO book){
        return service.update(book);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Apagar uma pessoa pelo id", description = "Apagar uma pessoa pelo id",
        tags = {"Books"},
        responses = {
                @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content), 
                @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content), 
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content)       
        }
    )
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        service.delete(id);

        // Utilizado para retornar um no content
        return ResponseEntity.noContent().build();
    }
}
