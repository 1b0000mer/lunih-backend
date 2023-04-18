package com.gsquad.lunih.controllers;

import com.gsquad.lunih.dtos.StudentAccountDTO;
import com.gsquad.lunih.entities.Account;
import com.gsquad.lunih.services.account.AccountService;
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

    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable int id) {
        try {
            Account account = service.get(id);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/student")
    public ResponseEntity<Account> createNewStudent(@Valid @RequestBody StudentAccountDTO dto) {
        try {
            return new ResponseEntity<>(service.createNewStudent(dto), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Account> delete(@PathVariable int id) {
        try {
            Account existAccount = service.get(id);
            return new ResponseEntity<>(service.delete(existAccount.getId()), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
