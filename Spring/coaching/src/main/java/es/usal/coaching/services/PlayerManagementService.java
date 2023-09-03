package es.usal.coaching.services;

import java.util.Collection;

import org.springframework.web.multipart.MultipartFile;

import es.usal.coaching.dtos.ActionDTO;
import es.usal.coaching.dtos.PlayerDTO;


public interface PlayerManagementService {

    Collection<PlayerDTO> getPlayers(String username);

    PlayerDTO addPlayer(PlayerDTO request);

    PlayerDTO updatePlayer(PlayerDTO request);

    PlayerDTO deletePlayer(String request, String nameUsuario);

    Collection<ActionDTO> getStats(String nameUsuario);

    void uploadImage(MultipartFile imgFile, Long playerId);

    
}
