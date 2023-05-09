package com.gsquad.lunih.controllers;

import com.gsquad.lunih.dtos.categories.SpectrumDTO;
import com.gsquad.lunih.entities.categories.Spectrum;
import com.gsquad.lunih.services.spectrum.SpectrumService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest/spectrum")
public class SpectrumController {

    private final SpectrumService service;

    public SpectrumController(SpectrumService spectrumService) {
        this.service = spectrumService;
    }



    @GetMapping
    public ResponseEntity<List<Spectrum>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get Spectrum by id")
    @GetMapping("/{id}")
    public ResponseEntity<Spectrum> getById(@PathVariable int id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new Spectrum")
    @PostMapping
    public ResponseEntity<Spectrum> create(@Valid @RequestBody SpectrumDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @ApiOperation(value = "Update Spectrum")
    @PutMapping("/{id}")
    public ResponseEntity<Spectrum> update(@PathVariable int id, @Valid @RequestBody SpectrumDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @ApiOperation(value = "Change status Spectrum")
    @DeleteMapping("/change-status/{id}")
    public ResponseEntity<Spectrum> changeStatus(@PathVariable int id) {
        return new ResponseEntity<>(service.changeStatus(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Spectrum")
    @DeleteMapping("/{id}")
    public ResponseEntity<Spectrum> delete(@PathVariable int id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
