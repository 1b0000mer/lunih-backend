package com.gsquad.lunih.controllers;

import com.gsquad.lunih.dtos.accountDTO.StudentAccountDTO;
import com.gsquad.lunih.entities.Account;
import com.gsquad.lunih.services.account.AccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/rest/account")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
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

}
