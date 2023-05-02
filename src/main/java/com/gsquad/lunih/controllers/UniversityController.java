package com.gsquad.lunih.controllers;

import com.gsquad.lunih.dtos.accountDTO.UniversityAccountDTO;
import com.gsquad.lunih.entities.University;
import com.gsquad.lunih.services.university.UniversityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rest/university")
public class UniversityController {

    private final UniversityService service;


    public UniversityController(UniversityService service) {
        this.service = service;
    }

    @ApiOperation(value = "list all University with paging")
    @GetMapping("/paging")
    public ResponseEntity<Page<University>> listAllPaging(
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "page", required = false, defaultValue = "${paging.default.page}") int page,
            @RequestParam(value = "size", required = false, defaultValue = "${paging.default.size}")int size,
            @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(value = "column", required = false, defaultValue = "id") String column
    ) {
        return new ResponseEntity<>(service.listAllPaging(search, page, size, sort, column), HttpStatus.OK);
    }

    @ApiOperation(value = "list all university")
    @GetMapping
    public ResponseEntity<List<University>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "get university by id")
    @GetMapping("/{id}")
    public ResponseEntity<University> getById(@PathVariable int id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @ApiOperation(value = "get university currently logged in")
    @GetMapping("/current")
    public ResponseEntity<University> getCurrent(Principal principal) {
        return new ResponseEntity<>(service.getCurrent(principal), HttpStatus.OK);
    }

    @ApiOperation(value = "update university by id")
    @PutMapping("/{id}")
    public ResponseEntity<University> update(@PathVariable int id, @Valid @RequestBody UniversityAccountDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @ApiOperation(value = "delete university by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<University> delete(@PathVariable int id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
