package es.usal.coaching.services;



import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.usal.coaching.dtos.PlayerDTO;
import es.usal.coaching.dtos.TeamDTO;
import es.usal.coaching.entities.Player;
import es.usal.coaching.entities.Team;
import es.usal.coaching.mappers.PlayerDTOToEntityMapper;
import es.usal.coaching.mappers.TeamDTOToEntityMapper;
import es.usal.coaching.mappers.TeamEntityToDTOMapper;
import es.usal.coaching.repositories.CoachRepository;
import es.usal.coaching.repositories.PlayerRepository;
import es.usal.coaching.repositories.TeamRepository;
import es.usal.coaching.security.entity.Coach;

@Service
public class TeamManagementServiceImpl implements TeamManagementService{

    @Autowired
    private TeamRepository teamRepository;

    @Autowired 
    private CoachRepository coachRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public TeamDTO deleteTeam(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TeamDTO updateTeam(TeamDTO request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TeamDTO addTeam(TeamDTO request, String username) {
        
        Coach coach = coachRepository.findByNameUsuario(username);
        Team team = TeamDTOToEntityMapper.parser(request);
        team.setPlayers(new ArrayList<Player>());
        for(PlayerDTO player : request.getPlayers()){
            Player playerSaved = playerRepository.save(PlayerDTOToEntityMapper.parser(player));
            team.getPlayers().add(playerSaved);
        }
        team = teamRepository.save(team);
        coach.getRivalTeams().add(team);
        coachRepository.save(coach);
        return TeamEntityToDTOMapper.parser(team);
        
    }

    @Override
    public Collection<TeamDTO> getRivalTeams(String username) {
        Collection<TeamDTO> response = new ArrayList<TeamDTO>();
        Coach coach = coachRepository.findByNameUsuario(username);
        for(Team t : coach.getRivalTeams()){
            response.add(TeamEntityToDTOMapper.parser(t));
        }

        return response;
    }
    
}
