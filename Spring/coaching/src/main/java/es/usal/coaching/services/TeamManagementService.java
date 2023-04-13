package es.usal.coaching.services;

import org.springframework.stereotype.Service;

import es.usal.coaching.dtos.TeamDTO;

@Service
public interface TeamManagementService {

    TeamDTO deleteTeam(String dni);

    TeamDTO updateTeam(TeamDTO request);

    TeamDTO addTeam(TeamDTO request);
    
}
