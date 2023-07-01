package es.usal.coaching.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.usal.coaching.entities.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long>{
    Collection<Player> findAll();

    Player deleteByDni(String request);

    boolean existsByDni(String dni);

    Player findByDni(String dni);

    @Query(value = "select name, surname, a.type, count(*) from coachingbd.action as a JOIN coachingbd.player as p ON p.id = a.player_id WHERE p.id in (:ids) group by TYPE HAVING count(*);" , nativeQuery = true)
    Collection<Object[]> getStats(Collection<Long> ids);

    Player findByHashString(String hash);

    
}
