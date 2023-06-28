package es.usal.coaching.services;

import java.util.Collection;

import org.springframework.stereotype.Service;

import es.usal.coaching.dtos.TeamDTO;

@Service
public interface TeamManagementService {

    TeamDTO deleteTeam(Long id);

    TeamDTO updateTeam(TeamDTO request);

    TeamDTO addTeam(TeamDTO request, String username);

    Collection<TeamDTO> getRivalTeams(String username);
    
}
