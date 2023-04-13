package es.usal.coaching.security.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.usal.coaching.entities.Player;
import es.usal.coaching.repositories.TeamRepository;
import es.usal.coaching.security.entity.Coach;
import es.usal.coaching.security.repository.UsuarioRepository;

import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TeamRepository teamRepository;

    public Optional<Coach> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNameUsuario(nombreUsuario);
    }

    public boolean existsByNameUsuario(String nombreUsuario){
        return usuarioRepository.existsByNameUsuario(nombreUsuario);
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void save(Coach usuario){
        usuario.getTeam().setPlayers(new HashSet<Player>());
        teamRepository.save(usuario.getTeam());
        usuarioRepository.save(usuario);
    }
}
