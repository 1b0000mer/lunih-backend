package com.gsquad.lunih.controllers;

import com.gsquad.lunih.dtos.categories.ProgramDTO;
import com.gsquad.lunih.entities.categories.Program;
import com.gsquad.lunih.services.program.ProgramService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest/program")
public class ProgramController {

    private final ProgramService service;

    public ProgramController(ProgramService programService) {
        this.service = programService;
    }

   @ApiOperation(value = "list all program with paging and status=true")
    @GetMapping("/paging")
    public ResponseEntity<Page<Program>> listAllPaging(
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "page", required = false, defaultValue = "${paging.default.page}") int page,
            @RequestParam(value = "size", required = false, defaultValue = "${paging.default.size}")int size,
            @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(value = "column", required = false, defaultValue = "id") String column
    ) {
        return new ResponseEntity<>(service.listAllPaging(search, page, size, sort, column), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Program>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get Program by id")
    @GetMapping("/{id}")
    public ResponseEntity<Program> getById(@PathVariable int id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new Program")
    @PostMapping
    public ResponseEntity<Program> create(@Valid @RequestBody ProgramDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @ApiOperation(value = "Update Program")
    @PutMapping("/{id}")
    public ResponseEntity<Program> update(@PathVariable int id, @Valid @RequestBody ProgramDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @ApiOperation(value = "Change status Program")
    @DeleteMapping("/change-status/{id}")
    public ResponseEntity<Program> changeStatus(@PathVariable int id) {
        return new ResponseEntity<>(service.changeStatus(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Program")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
