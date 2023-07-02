package es.usal.coaching.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.usal.coaching.services.FilesStorageService;

import java.io.File;
import java.io.IOException;


@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization"})
public class FilesStorageController {

    @Autowired
    FilesStorageService filesStorageService;

    @GetMapping("/files/{username}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, @PathVariable String username) throws IOException {
        
        StringBuilder build = new StringBuilder(username)
            .append(File.separatorChar)
            .append(fileName);

        Resource resource = filesStorageService.load(build.toString());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
}

