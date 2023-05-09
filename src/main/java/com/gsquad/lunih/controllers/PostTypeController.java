package com.gsquad.lunih.controllers;

import com.gsquad.lunih.dtos.categories.PostTypeDTO;
import com.gsquad.lunih.entities.categories.PostType;
import com.gsquad.lunih.services.post_type.PostTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest/post-type")
public class PostTypeController {

    private final PostTypeService service;

    public PostTypeController(PostTypeService postTypeService) {
        this.service = postTypeService;
    }



    @GetMapping
    public ResponseEntity<List<PostType>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get Post Type by id")
    @GetMapping("/{id}")
    public ResponseEntity<PostType> getById(@PathVariable int id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new Post Type")
    @PostMapping
    public ResponseEntity<PostType> create(@Valid @RequestBody PostTypeDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @ApiOperation(value = "Update Post type")
    @PutMapping("/{id}")
    public ResponseEntity<PostType> update(@PathVariable int id, @Valid @RequestBody PostTypeDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @ApiOperation(value = "Change status Post type")
    @DeleteMapping("/change-status/{id}")
    public ResponseEntity<PostType> changeStatus(@PathVariable int id) {
        return new ResponseEntity<>(service.changeStatus(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Post Type")
    @DeleteMapping("/{id}")
    public ResponseEntity<PostType> delete(@PathVariable int id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
