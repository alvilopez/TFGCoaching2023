package es.usal.coaching.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.usal.coaching.security.entity.Coach;


@Repository
public interface UsuarioRepository extends JpaRepository<Coach, Integer> {
    Optional<Coach> findByNameUsuario(String nombreUsuario);
    boolean existsByNameUsuario(String nombreUsuario);
    boolean existsByEmail(String email);

}
