package com.gsquad.lunih.controllers;

import com.gsquad.lunih.dtos.accountDTO.StudentAccountDTO;
import com.gsquad.lunih.dtos.accountDTO.UniversityAccountDTO;
import com.gsquad.lunih.entities.Account;
import com.gsquad.lunih.services.account.AccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest/account")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @ApiOperation(value = "list all account with paging and status=true")
    @GetMapping("/paging")
    public ResponseEntity<Page<Account>> listAllPaging(
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "page", required = false, defaultValue = "${paging.default.page}") int page,
            @RequestParam(value = "size", required = false, defaultValue = "${paging.default.size}")int size,
            @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(value = "column", required = false, defaultValue = "id") String column
    ) {
        return new ResponseEntity<>(service.listAllPaging(search, page, size, sort, column), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Account>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get Account by id")
    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable int id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new Student Account")
    @PostMapping("/student")
    public ResponseEntity<Account> createNewStudent(@Valid @RequestBody StudentAccountDTO dto) {
        return new ResponseEntity<>(service.createNewStudent(dto), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new University Account")
    @PostMapping("/university")
    public ResponseEntity<Account> createNewUniversity(@Valid @RequestBody UniversityAccountDTO dto) {
        return new ResponseEntity<>(service.createNewUniversity(dto), HttpStatus.OK);
    }

}
