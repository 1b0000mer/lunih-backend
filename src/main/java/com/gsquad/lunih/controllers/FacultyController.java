package com.gsquad.lunih.controllers;

import com.gsquad.lunih.dtos.categories.FacultyDTO;
import com.gsquad.lunih.entities.categories.Faculty;
import com.gsquad.lunih.services.faculty.FacultyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest/faculty")
public class FacultyController {

    private final FacultyService service;

    public FacultyController(FacultyService facultyService) {
        this.service = facultyService;
    }

    @ApiOperation(value = "list all faculty with paging and status=true")
    @GetMapping("/paging")
    public ResponseEntity<Page<Faculty>> listAllPaging(
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "page", required = false, defaultValue = "${paging.default.page}") int page,
            @RequestParam(value = "size", required = false, defaultValue = "${paging.default.size}")int size,
            @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(value = "column", required = false, defaultValue = "id") String column
    ) {
        return new ResponseEntity<>(service.listAllPaging(search, page, size, sort, column), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get Faculty by id")
    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getById(@PathVariable int id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new faculty")
    @PostMapping
    public ResponseEntity<Faculty> create(@Valid @RequestBody FacultyDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @ApiOperation(value = "Update faculty")
    @PutMapping("/{id}")
    public ResponseEntity<Faculty> update(@PathVariable int id, @Valid @RequestBody FacultyDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @ApiOperation(value = "Change status faculty")
    @DeleteMapping("/change-status/{id}")
    public ResponseEntity<Faculty> changeStatus(@PathVariable int id) {
        return new ResponseEntity<>(service.changeStatus(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete faculty")
    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> delete(@PathVariable int id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
