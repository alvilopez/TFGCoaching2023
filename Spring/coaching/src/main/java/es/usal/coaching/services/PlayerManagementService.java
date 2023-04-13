package es.usal.coaching.services;

import java.util.List;

import es.usal.coaching.dtos.ActionDTO;
import es.usal.coaching.dtos.PlayerDTO;


public interface PlayerManagementService {

    List<PlayerDTO> getPlayers(String username);

    PlayerDTO addPlayer(PlayerDTO request, String nameUsuario);

    PlayerDTO updatePlayer(PlayerDTO request);

    PlayerDTO deletePlayer(String request, String nameUsuario);

    List<ActionDTO> getStats(String nameUsuario);

    
}
