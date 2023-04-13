package es.usal.coaching.mappers;

import es.usal.coaching.dtos.CoachDTO;
import es.usal.coaching.security.entity.Coach;


public class CoachDTOToEntityMapper {
    public static Coach parser(CoachDTO coachDTO) {
        Coach coach = new Coach();
        coach.setCod(coachDTO.getCod());
        coach.setName(coachDTO.getName());
        coach.setDni(coachDTO.getDni());
        coach.setEmail(coachDTO.getEmail());
        coach.setTeam(TeamDTOToEntityMapper.parser(coachDTO.getTeam()));

        return coach;
    }
}
