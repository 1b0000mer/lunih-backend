package com.gsquad.lunih.controllers;

import com.gsquad.lunih.dtos.categories.IndustryDTO;
import com.gsquad.lunih.entities.categories.Industry;
import com.gsquad.lunih.services.industry.IndustryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest/Industry")
public class IndustryController {

    private final IndustryService service;

    public IndustryController(IndustryService industryService) {
        this.service = industryService;
    }

    @GetMapping
    public ResponseEntity<List<Industry>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get Industry by id")
    @GetMapping("/{id}")
    public ResponseEntity<Industry> getById(@PathVariable int id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new Industry")
    @PostMapping
    public ResponseEntity<Industry> create(@Valid @RequestBody IndustryDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @ApiOperation(value = "Update Industry")
    @PutMapping("/{id}")
    public ResponseEntity<Industry> update(@PathVariable int id, @Valid @RequestBody IndustryDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @ApiOperation(value = "Change status Industry")
    @DeleteMapping("/change-status/{id}")
    public ResponseEntity<Industry> changeStatus(@PathVariable int id) {
        return new ResponseEntity<>(service.changeStatus(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Industry")
    @DeleteMapping("/{id}")
    public ResponseEntity<Industry> delete(@PathVariable int id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
