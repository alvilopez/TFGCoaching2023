package es.usal.coaching.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.usal.coaching.dtos.TeamDTO;
import es.usal.coaching.mappers.TeamDTOToEntityMapper;
import es.usal.coaching.mappers.TeamEntityToDTOMapper;
import es.usal.coaching.repositories.TeamRepository;

@Service
public class TeamManagementServiceImpl implements TeamManagementService{

    @Autowired
    TeamRepository teamRepository;

    @Override
    public TeamDTO deleteTeam(String dni) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TeamDTO updateTeam(TeamDTO request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TeamDTO addTeam(TeamDTO request) {
        // TODO Auto-generated method stub
        return TeamEntityToDTOMapper.parser(teamRepository.save(TeamDTOToEntityMapper.parser(request)));
        
    }
    
}
