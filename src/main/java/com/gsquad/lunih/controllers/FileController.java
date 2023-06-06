package com.gsquad.lunih.controllers;

import com.gsquad.lunih.entities.MyFile;
import com.gsquad.lunih.services.myfile.MyFileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/rest/file")
public class FileController {
    private final MyFileService myFileService;

    public FileController(MyFileService myFileService) {
        this.myFileService = myFileService;
    }

    @PostMapping
    public ResponseEntity<MyFile> uploadFile(
            @Valid @RequestParam MultipartFile file,
            @RequestParam(name = "subFolder", defaultValue = "Files") String subFolder,
            Principal principal) throws Exception {
        return new ResponseEntity<>(myFileService.uploadFile(file, subFolder.toLowerCase(), principal), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyFile> getFileInfo(
            @PathVariable("id") int id) {
        return new ResponseEntity<>(myFileService.getFileInfo(id), HttpStatus.OK);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> viewFile(
            @PathVariable("id") int id,
            Principal principal
    ) {
        return new ResponseEntity<>(myFileService.viewFile(id, principal), HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable("id") int id,
            Principal principal
    ) {
        Resource resource = myFileService.downloadFile(id, principal);
        return ResponseEntity.ok()
                .header("filename", resource.getFilename())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
//        return new ResponseEntity<>(myFileService.downloadFile(id, principal), HttpStatus.OK);
    }

}
