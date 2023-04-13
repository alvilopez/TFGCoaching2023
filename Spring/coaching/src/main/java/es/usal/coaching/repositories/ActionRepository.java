package es.usal.coaching.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import es.usal.coaching.entities.Action;

@Repository
public interface ActionRepository extends CrudRepository<Action, Long>{

    
    List<Action> findAllByPlayerIdIn(Set<Long> ids);

    List<Action> findAllByPlayerId(Long id);
}
