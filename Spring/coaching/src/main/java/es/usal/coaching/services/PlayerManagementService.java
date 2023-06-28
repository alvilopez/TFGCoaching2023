package es.usal.coaching.services;

import java.util.Collection;

import es.usal.coaching.dtos.ActionDTO;
import es.usal.coaching.dtos.PlayerDTO;


public interface PlayerManagementService {

    Collection<PlayerDTO> getPlayers(String username);

    PlayerDTO addPlayer(PlayerDTO request, Long id);

    PlayerDTO updatePlayer(PlayerDTO request);

    PlayerDTO deletePlayer(String request, String nameUsuario);

    Collection<ActionDTO> getStats(String nameUsuario);

    
}
