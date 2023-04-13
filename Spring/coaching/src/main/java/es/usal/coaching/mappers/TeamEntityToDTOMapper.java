package es.usal.coaching.mappers;

import java.util.ArrayList;

import es.usal.coaching.dtos.PlayerDTO;
import es.usal.coaching.dtos.TeamDTO;
import es.usal.coaching.entities.Player;
import es.usal.coaching.entities.Team;

public class TeamEntityToDTOMapper {

    public static TeamDTO parser(Team team) {
        TeamDTO teamDTO = new TeamDTO();

        teamDTO.setCategory(team.getCategory());
        teamDTO.setCod(team.getCod());
        teamDTO.setName(team.getName());
        teamDTO.setPlayers(new ArrayList<PlayerDTO>());
        if(team.getPlayers() != null && !team.getPlayers().isEmpty()){
            for(Player p : team.getPlayers()){
            
                teamDTO.getPlayers().add(PlayerEntityToDTOMapper.parser(p));
            }
        }
        
            
        return teamDTO;
    }
    
}
