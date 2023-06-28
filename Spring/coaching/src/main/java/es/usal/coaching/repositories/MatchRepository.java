package es.usal.coaching.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.usal.coaching.entities.Match;
import es.usal.coaching.entities.Team;
import java.util.List;
import java.util.Optional;

import es.usal.coaching.entities.Action;


@Repository
public interface MatchRepository extends CrudRepository<Match, Long>{

    

    Collection<Match> findByLocalTeam(Team team);

    Match findByCod(String matchCod);

    Long findIdByCod(String cod);

    Match findByActions(Optional<Action> photoAction);
    
}
