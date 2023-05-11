package com.gsquad.lunih.controllers;

import com.gsquad.lunih.dtos.PostDTO;
import com.gsquad.lunih.entities.Post;
import com.gsquad.lunih.services.post.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/rest/post")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

//    @GetMapping
//    public ResponseEntity<List<Post>> listAll() {
//        return null;
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Post> getById(@PathVariable int id) {
//        return null;
//    }
//
//    @PostMapping
//    public ResponseEntity<Post> create(@Valid @RequestBody PostDTO dto) {
//        return null;
//    }
}
