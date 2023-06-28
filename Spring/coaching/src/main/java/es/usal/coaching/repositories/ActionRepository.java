package es.usal.coaching.repositories;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import es.usal.coaching.entities.Action;

@Repository
public interface ActionRepository extends CrudRepository<Action, Long>{

    
    Collection<Action> findAllByPlayerIdIn(Set<Long> ids);

    Collection<Action> findAllByPlayerId(Long id);
}
