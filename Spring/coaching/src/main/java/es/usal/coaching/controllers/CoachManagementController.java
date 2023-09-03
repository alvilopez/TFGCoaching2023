package es.usal.coaching.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.usal.coaching.security.entity.Coach;
import es.usal.coaching.security.repository.UsuarioRepository;
import es.usal.coaching.services.CoachManagementService;

@RestController
public class CoachManagementController {
    @Autowired
    CoachManagementService coachManagementService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping(value = "/coach/{userName}")
    public ResponseEntity<Optional<Coach>> getUser(@PathVariable String userName){
        Optional<Coach> response = usuarioRepository.findByNameUsuario(userName);
        return new ResponseEntity<Optional<Coach>>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/coach/img")
    public ResponseEntity<Optional<Coach>> uploadImage(@RequestParam("file") MultipartFile imgFile){
        coachManagementService.uploadImage(imgFile);
        return new ResponseEntity<Optional<Coach>>(HttpStatus.OK);
    }
}
