package es.usal.coaching.mappers;

import java.util.ArrayList;

import es.usal.coaching.dtos.ActionDTO;
import es.usal.coaching.dtos.MatchDTO;
import es.usal.coaching.entities.Action;
import es.usal.coaching.entities.Match;

public class MatchEntityToDTOMapper {
    public static MatchDTO parser(Match match) {
        MatchDTO matchDTO = new MatchDTO();

        matchDTO.setId(match.getId());
        matchDTO.setDate(match.getDate());
        matchDTO.setMatchNum(match.getMatchNum());
        matchDTO.setVideo(match.getVideo());
        matchDTO.setLocalTeam(TeamEntityToDTOMapper.parser(match.getLocalTeam()));
        matchDTO.setCod(match.getCod());
        if(match.getVisitantTeam() != null)
        matchDTO.setVisitantTeam(TeamEntityToDTOMapper.parser(match.getVisitantTeam()));
        
        matchDTO.setActions(new ArrayList<ActionDTO>());
        for(Action a : match.getActions()){
            matchDTO.getActions().add(ActionEntityToDTOMapper.parser(a));
        }

        return matchDTO;
    }
}
