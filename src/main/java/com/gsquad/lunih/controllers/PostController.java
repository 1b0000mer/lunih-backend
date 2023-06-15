package com.gsquad.lunih.controllers;

import com.gsquad.lunih.dtos.PostDTO;
import com.gsquad.lunih.entities.Post;
import com.gsquad.lunih.services.post.PostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rest/post")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @ApiOperation(value = "list all post with paging")
    @GetMapping("/paging")
    public ResponseEntity<Page<Post>> listAllPaging(
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "page", required = false, defaultValue = "${paging.default.page}") int page,
            @RequestParam(value = "size", required = false, defaultValue = "${paging.default.size}")int size,
            @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(value = "column", required = false, defaultValue = "id") String column
    ) {
        return new ResponseEntity<>(service.listAllPaging(search, page, size, sort, column), HttpStatus.OK);
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
