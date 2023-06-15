package com.gsquad.lunih.controllers;

import com.gsquad.lunih.dtos.accountDTO.CompanyAccountDTO;
import com.gsquad.lunih.entities.Company;
import com.gsquad.lunih.services.company.CompanyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rest/company")
public class CompanyController {

    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @ApiOperation(value = "list all Company with paging")
    @GetMapping("/paging")
    public ResponseEntity<Page<Company>> listAllPaging(
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "page", required = false, defaultValue = "${paging.default.page}") int page,
            @RequestParam(value = "size", required = false, defaultValue = "${paging.default.size}")int size,
            @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(value = "column", required = false, defaultValue = "id") String column
    ) {
        return new ResponseEntity<>(service.listAllPaging(search, page, size, sort, column), HttpStatus.OK);
    }

    @ApiOperation(value = "list all company")
    @GetMapping
    public ResponseEntity<List<Company>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "get company by id")
    @GetMapping("/{id}")
    public ResponseEntity<Company> getById(@PathVariable int id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @ApiOperation(value = "get company currently logged in")
    @GetMapping("/current")
    public ResponseEntity<Company> getCurrent(Principal principal) {
        return new ResponseEntity<>(service.getCurrent(principal), HttpStatus.OK);
    }

    @ApiOperation(value = "update company by id")
    @PutMapping("/{id}")
    public ResponseEntity<Company> update(@PathVariable int id, @Valid @RequestBody CompanyAccountDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @ApiOperation(value = "delete company by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Company> delete(@PathVariable int id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
