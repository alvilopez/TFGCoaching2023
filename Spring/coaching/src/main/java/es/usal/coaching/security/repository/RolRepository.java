package es.usal.coaching.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.usal.coaching.enums.RolNombre;
import es.usal.coaching.security.entity.Rol;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    
    

    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
