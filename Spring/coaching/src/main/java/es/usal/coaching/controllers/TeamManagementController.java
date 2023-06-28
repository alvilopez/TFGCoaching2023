package es.usal.coaching.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.usal.coaching.dtos.TeamDTO;
import es.usal.coaching.services.TeamManagementService;

@RestController
public class TeamManagementController {
    @Autowired
    TeamManagementService teamManagementService;
    
    @GetMapping(value="/team")
    public ResponseEntity<Collection<TeamDTO>> getTeams() {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        String username = userDetails.getUsername();
        
        Collection<TeamDTO> response = teamManagementService.getRivalTeams(username);
        if(response !=null){
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value="/team")
    public ResponseEntity<TeamDTO> addTeam(@RequestBody TeamDTO request){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        String username = userDetails.getUsername();

        TeamDTO response = teamManagementService.addTeam(request, username);
        if(response !=null){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping(value="/team")
    public ResponseEntity<TeamDTO> updateTeam(@RequestBody TeamDTO request){
        TeamDTO response = teamManagementService.updateTeam(request);
        if(response !=null){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping(value="/team/{id}")
    public ResponseEntity<TeamDTO> deleteTeam(@PathVariable Long id){
        TeamDTO response = teamManagementService.deleteTeam(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
   
}
