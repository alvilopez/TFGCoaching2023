package es.usal.coaching.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.usal.coaching.dtos.TeamDTO;
import es.usal.coaching.services.TeamManagementService;

public class TeamManagementController {
    @Autowired
    TeamManagementService teamManagementService;
    
    /*@GetMapping(value="/team")
    public ResponseEntity<List<TeamDTO>> getTeams() {
        List<TeamDTO> response = teamManagementService.getTeams();
        if(response !=null){
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }*/

    @PostMapping(value="/team")
    public ResponseEntity<TeamDTO> addTeam(@RequestBody TeamDTO request){
        TeamDTO response = teamManagementService.addTeam(request);
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

    @DeleteMapping(value="/team/{dni}")
    public ResponseEntity<TeamDTO> deleteTeam(@PathVariable String dni){
        TeamDTO response = teamManagementService.deleteTeam(dni);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
