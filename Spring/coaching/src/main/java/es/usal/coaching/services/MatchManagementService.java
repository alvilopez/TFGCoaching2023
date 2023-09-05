package es.usal.coaching.services;

import java.util.Collection;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import es.usal.coaching.dtos.ActionDTO;
import es.usal.coaching.dtos.MatchDTO;

public interface MatchManagementService {

    Collection<MatchDTO> getMatchs(String userCod);

    MatchDTO addMatch(MatchDTO request, String string);

    MatchDTO updateMatch(MatchDTO request);

    MatchDTO deleteMatch(Long request);

    ActionDTO addAction(ActionDTO request, Long id);

    String addMatchVideo(MultipartFile file, String username, Long id);

    Resource loadVideo(String video);

    String splitVideo(String videoCod, String userName);

    ActionDTO deleteAction(Long id);
    
    ActionDTO obtenerFotoDeVideo(Long id);

    MatchDTO notificarJugadores(MatchDTO matchDTO);

    Collection<MatchDTO> getMatchsForPlayer(String hash);

    

}
