package com.gsquad.lunih.controllers;

import com.gsquad.lunih.dtos.accountDTO.StudentAccountDTO;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.services.student.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/rest/student")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Student>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @GetMapping("/{studentID}")
    public ResponseEntity<Student> getById(@PathVariable String studentID) {
        try {
            Student student = service.get(studentID);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/current")
    public ResponseEntity<Student> getCurrent(Principal principal) {
        try {
            return new ResponseEntity<>(service.getCurrent(principal), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{studentID}")
    public ResponseEntity<Student> update(@PathVariable String studentID, @Valid @RequestBody StudentAccountDTO dto) {
        try {
            Student existStudent = service.get(studentID);
            return new ResponseEntity<>(service.update(existStudent.getStudentID(), dto), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{studentID}")
    public ResponseEntity<Student> delete(@PathVariable String studentID) {
        try {
            Student existStudent = service.get(studentID);
            return new ResponseEntity<>(service.delete(existStudent.getStudentID()), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
