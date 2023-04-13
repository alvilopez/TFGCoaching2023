package es.usal.coaching.mappers;

import es.usal.coaching.dtos.PlayerDTO;
import es.usal.coaching.dtos.TeamDTO;
import es.usal.coaching.entities.Team;

public class TeamDTOToEntityMapper {

    public static Team parser(TeamDTO teamDTO) {
        Team team = new Team();

        team.setCategory(teamDTO.getCategory());
        team.setCod(teamDTO.getCod());
        team.setName(teamDTO.getName());
        for(PlayerDTO p : teamDTO.getPlayers()){
            team.getPlayers().add(PlayerDTOToEntityMapper.parser(p));
        }
            
        return team;
    }
    
}
