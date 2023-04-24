package com.gsquad.lunih.controllers.demo;

import com.gsquad.lunih.dtos.demo.ProductDTO;
import com.gsquad.lunih.entities.demo.Product;
import com.gsquad.lunih.services.demo.product.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.NoSuchElementException;

// For testing purposes
@RestController
@RequestMapping("/rest/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Product>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable int id) {
        try {
            Product product = service.get(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody ProductDTO dto) {
        try {
            return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable int id, @Valid @RequestBody ProductDTO dto) {
        try {
            Product existProduct = service.get(id);
            return new ResponseEntity<>(service.update(existProduct.getId(), dto), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable int id) {
        try {
            Product existProduct = service.get(id);
            return new ResponseEntity<>(service.delete(existProduct.getId()), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
