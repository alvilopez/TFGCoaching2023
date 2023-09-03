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
public class FilesStorageController {

    @Autowired
    FilesStorageService filesStorageService;

     @GetMapping("/assets/{fileName:.+}")
    public ResponseEntity<Resource> assetFile(@PathVariable String fileName, @PathVariable String username) throws IOException {
        
        StringBuilder build = new StringBuilder(username)
            .append(File.separatorChar)
            .append(fileName);

        Resource resource = filesStorageService.load(build.toString());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }


    @GetMapping("/files/{username}/{fileName:.+}")
    @CrossOrigin
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, @PathVariable String username) throws IOException {
       
        
        StringBuilder build = new StringBuilder(username)
            .append(File.separatorChar)
            .append(fileName);

            HttpHeaders headers = new HttpHeaders();
            // Configura las cabeceras CORS permitidas
            headers.set("Access-Control-Allow-Origin", "*");
            headers.set("Access-Control-Allow-Methods", "GET, OPTIONS");
            headers.set("Access-Control-Allow-Headers", "Content-Type, Authorization");

        Resource resource = filesStorageService.load(build.toString());
        
        return ResponseEntity.ok().headers(headers).body(resource);
    }
}

