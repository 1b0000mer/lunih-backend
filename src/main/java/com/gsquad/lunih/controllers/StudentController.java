package com.gsquad.lunih.controllers;

import com.gsquad.lunih.dtos.accountDTO.StudentAccountDTO;
import com.gsquad.lunih.dtos.student.ApproveStudentDTO;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.services.student.StudentService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "list all student")
    @GetMapping
    public ResponseEntity<List<Student>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "get student by id")
    @GetMapping("/{studentID}")
    public ResponseEntity<Student> getById(@PathVariable String studentID) {
        return new ResponseEntity<>(service.get(studentID), HttpStatus.OK);
    }

    @ApiOperation(value = "get student currently logged in")
    @GetMapping("/current")
    public ResponseEntity<Student> getCurrent(Principal principal) {
        return new ResponseEntity<>(service.getCurrent(principal), HttpStatus.OK);
    }

    @PutMapping("/{studentID}")
    public ResponseEntity<Student> update(@PathVariable String studentID, @Valid @RequestBody StudentAccountDTO dto) {
        return new ResponseEntity<>(service.update(studentID, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{studentID}")
    public ResponseEntity<Student> delete(@PathVariable String studentID) {
        return new ResponseEntity<>(service.delete(studentID), HttpStatus.OK);
    }

    @ApiOperation(value = "approve a student, must include reason for refuse request")
    @PostMapping("/approve/{studentID}")
    public ResponseEntity<Student> delete(@PathVariable String studentID, @Valid @RequestBody ApproveStudentDTO approveStudentDTO) {
        return new ResponseEntity<>(service.approveStudent(studentID, approveStudentDTO), HttpStatus.OK);
    }
}
