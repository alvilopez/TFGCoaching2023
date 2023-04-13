package es.usal.coaching.mappers;

import es.usal.coaching.dtos.MatchDTO;
import es.usal.coaching.entities.Match;

public class MatchDTOToEntityMapper {
    public static Match parser(MatchDTO matchDTO) {
        Match match = new Match();

        match.setDate(matchDTO.getDate());
        match.setMatchNum(matchDTO.getMatchNum());
        match.setVideo(matchDTO.getVideo());
        match.setLocalTeam(TeamDTOToEntityMapper.parser(matchDTO.getLocalTeam()));
        match.setVisitantTeam(TeamDTOToEntityMapper.parser(matchDTO.getVisitantTeam()));
        match.setCod(matchDTO.getCod());

        return match;
    }

    public static Match change(Match match, MatchDTO matchDTO){
        
        match.setDate(matchDTO.getDate());
        match.setMatchNum(matchDTO.getMatchNum());
        match.setVideo(matchDTO.getVideo());
        match.setLocalTeam(TeamDTOToEntityMapper.parser(matchDTO.getLocalTeam()));
        match.setVisitantTeam(TeamDTOToEntityMapper.parser(matchDTO.getVisitantTeam()));
        match.setCod(matchDTO.getCod());
        
        return match;

        
        
    }

}
