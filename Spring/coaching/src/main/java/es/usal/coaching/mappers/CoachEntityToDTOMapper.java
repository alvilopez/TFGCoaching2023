package es.usal.coaching.mappers;

import es.usal.coaching.dtos.CoachDTO;
import es.usal.coaching.security.entity.Coach;

public class CoachEntityToDTOMapper {
    public static CoachDTO parser(Coach coach) {
        
        CoachDTO coachDTO = new CoachDTO();
        coachDTO.setCod(coach.getCod());
        coachDTO.setName(coach.getName());
        coachDTO.setSurname(coach.getSurname());
        coachDTO.setDni(coach.getDni());
        coachDTO.setEmail(coach.getEmail());
        coachDTO.setTeam(TeamEntityToDTOMapper.parser(coach.getTeam()));
        coachDTO.setImgSrc(coachDTO.getImgSrc());


        return coachDTO;
    }
}
