package com.gsquad.lunih.controllers;

import com.gsquad.lunih.dtos.PostDTO;
import com.gsquad.lunih.dtos.deliverables.DeliverableDTO;
import com.gsquad.lunih.entities.Deliverable;
import com.gsquad.lunih.entities.Post;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.services.post.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/rest/post")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Post>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @PutMapping("/student-apply/{id}")
    public ResponseEntity<Post> studentApplyPost(Principal principal, @PathVariable int id) {
        return new ResponseEntity<>(service.studentApplyPost(principal, id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable int id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> create(@Valid @RequestBody PostDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable int id, @Valid @RequestBody PostDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/change-status/{id}")
    public ResponseEntity<Post> changeStatus(@PathVariable int id) {
        return new ResponseEntity<>(service.changeStatus(id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Post> delete(@PathVariable int id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @PostMapping("/university")
//    @PreAuthorize("hasRole('UNIVERSITY')")
    public ResponseEntity<Post> universityCreatePost(@Valid @RequestBody PostDTO dto, Principal principal) {
        return new ResponseEntity<>(service.universityPublishPost(principal, dto), HttpStatus.OK);
    }
    @PostMapping("/company")
//    @PreAuthorize("hasRole('UNIVERSITY')")
    public ResponseEntity<Post> companyCreatePost(@Valid @RequestBody PostDTO dto, Principal principal) {
        return new ResponseEntity<>(service.companyPublishPost(principal, dto), HttpStatus.OK);
    }

//    @PostMapping("/deliverable")
////    @PreAuthorize("hasRole('UNIVERSITY')")
//    public ResponseEntity<Deliverable> studentUplodeDeliverable(@Valid @RequestBody DeliverableDTO dto, Principal principal) {
//        return new ResponseEntity<>(service.studentUplodeDeliverable(principal, dto), HttpStatus.OK);
//    }
}
