package es.usal.coaching.controllers;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.usal.coaching.dtos.ActionDTO;
import es.usal.coaching.dtos.PlayerDTO;
import es.usal.coaching.security.entity.Coach;
import es.usal.coaching.services.PlayerManagementService;


@RestController
public class PlayerManagementController {

    @Autowired
    PlayerManagementService playerManagementService;
    
    
    @GetMapping(value="/player")
    public ResponseEntity<Collection<PlayerDTO>> getPlayers() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        String username = userDetails.getUsername();

        Collection<PlayerDTO> response = playerManagementService.getPlayers(username);
        if(response !=null){
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }
   
    @PostMapping(value="/player")
    public ResponseEntity<PlayerDTO> addPlayer(@RequestBody PlayerDTO playerDTO){
        
        PlayerDTO response = playerManagementService.addPlayer(playerDTO);
        if(response !=null){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }
    
    @PutMapping(value="/player")
    public ResponseEntity<PlayerDTO> updatePlayer(@RequestBody PlayerDTO request){
        PlayerDTO response = playerManagementService.updatePlayer(request);
        if(response !=null){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }
    
    @DeleteMapping(value="/player/{dni}")
    public ResponseEntity<PlayerDTO> deletePlayer(@PathVariable String dni){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        String username = userDetails.getUsername();

        PlayerDTO response = playerManagementService.deletePlayer(dni, username);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
    
    @GetMapping(value = "player/getStats")
    public ResponseEntity<Collection<ActionDTO>> getStats(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        String username = userDetails.getUsername();
        Collection<ActionDTO> response = playerManagementService.getStats(username);
        if(response !=null){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value = "/player/img")
    public ResponseEntity<Optional<Coach>> uploadImage(@RequestParam("file") MultipartFile imgFile, @RequestParam("playerId") String playerId){
        playerManagementService.uploadImage(imgFile,  Long.parseLong(playerId));
        
        return new ResponseEntity<Optional<Coach>>(HttpStatus.OK);

    }
    
}
