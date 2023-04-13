package es.usal.coaching.security.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.usal.coaching.enums.RolNombre;
import es.usal.coaching.security.entity.Rol;
import es.usal.coaching.security.repository.RolRepository;

import java.util.Optional;

import javax.annotation.PostConstruct;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    @PostConstruct
    private void onInit(){
        if(rolRepository.findByRolNombre(RolNombre.ROLE_ADMIN).isEmpty()){
            rolRepository.save(new Rol(RolNombre.ROLE_ADMIN));
        }

        if(rolRepository.findByRolNombre(RolNombre.ROLE_USER).isEmpty()){
            rolRepository.save(new Rol(RolNombre.ROLE_USER));
        }

    }

    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }

    public void save(Rol rol){
        rolRepository.save(rol);
    }
}
