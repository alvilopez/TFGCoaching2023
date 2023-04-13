package es.usal.coaching.repositories;

import java.util.List;


import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;


import es.usal.coaching.entities.Match;
import es.usal.coaching.entities.Team;
import es.usal.coaching.security.entity.Coach;

@Repository
public interface CoachRepository extends CrudRepository<Coach, Long>{

    

    List<Match> findAllMatchByCod(String cod);

    Coach findByCod(String userCod);

    Team findTeamByCod(String string);

   

    Coach findByEmail(String username);

    Coach findByNameUsuario(String username);

    

}
