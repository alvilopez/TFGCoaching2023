package es.usal.coaching.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.usal.coaching.entities.Player;
import es.usal.coaching.entities.Team;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long>{

    Team findByPlayers(Player player);
    
}
