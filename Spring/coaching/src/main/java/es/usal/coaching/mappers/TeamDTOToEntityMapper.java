package es.usal.coaching.mappers;

import java.util.ArrayList;

import es.usal.coaching.dtos.PlayerDTO;
import es.usal.coaching.dtos.TeamDTO;
import es.usal.coaching.entities.Player;
import es.usal.coaching.entities.Team;

public class TeamDTOToEntityMapper {

    public static Team parser(TeamDTO teamDTO) {
        Team team = new Team();

        team.setId(teamDTO.getId());
        team.setCategory(teamDTO.getCategory());
        team.setCod(teamDTO.getCod());
        team.setName(teamDTO.getName());
        if(team.getPlayers() == null){
            team.setPlayers(new ArrayList<Player>());
        }
        for(PlayerDTO p : teamDTO.getPlayers()){
            team.getPlayers().add(PlayerDTOToEntityMapper.parser(p));
        }
        
            
        return team;
    }
    
}
