package com.gsquad.lunih.controllers;

import com.gsquad.lunih.dtos.PostDTO;
import com.gsquad.lunih.dtos.deliverables.ChangeStatusDTO;
import com.gsquad.lunih.dtos.deliverables.DeliverableDTO;
import com.gsquad.lunih.entities.Deliverable;
import com.gsquad.lunih.entities.Post;
import com.gsquad.lunih.services.deliverable.DeliverableService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rest/deliverable")
public class DeliverableController {

    private final DeliverableService service;

    public DeliverableController(DeliverableService deliverableService) {
        this.service = deliverableService;
    }

    @ApiOperation(value = "list all deliverable with paging and status=true")
    @GetMapping("/paging")
    public ResponseEntity<Page<Deliverable>> listAllPaging(
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "page", required = false, defaultValue = "${paging.default.page}") int page,
            @RequestParam(value = "size", required = false, defaultValue = "${paging.default.size}") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(value = "column", required = false, defaultValue = "id") String column
    ) {
        return new ResponseEntity<>(service.listAllPaging(search, page, size, sort, column), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Deliverable>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get Deliverable by id")
    @GetMapping("/{id}")
    public ResponseEntity<Deliverable> getById(@PathVariable int id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new deliverable")
    @PostMapping
    public ResponseEntity<Deliverable> create(@Valid @RequestBody DeliverableDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @ApiOperation(value = "Update deliverable")
    @PutMapping("/{id}")
    public ResponseEntity<Deliverable> update(@PathVariable int id, @Valid @RequestBody DeliverableDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @ApiOperation(value = "Change status deliverable")
    @DeleteMapping("/change-status/{id}")
    public ResponseEntity<Deliverable> changeStatus(@PathVariable int id, @Valid @RequestBody ChangeStatusDTO dto) {
        return new ResponseEntity<>(service.changeStatus(id, dto), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete deliverable")
    @DeleteMapping("/{id}")
    public ResponseEntity<Deliverable> delete(@PathVariable int id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @PostMapping("/deliverable")
//    @PreAuthorize("hasRole('UNIVERSITY')")
    public ResponseEntity<Deliverable> studentUplodeDeliverable(@Valid @RequestBody DeliverableDTO dto, Principal principal) {
        return new ResponseEntity<>(service.studentUplodeDeliverable(principal, dto), HttpStatus.OK);
    }
}