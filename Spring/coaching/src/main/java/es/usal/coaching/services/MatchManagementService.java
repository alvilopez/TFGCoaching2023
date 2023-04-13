package es.usal.coaching.services;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import es.usal.coaching.dtos.ActionDTO;
import es.usal.coaching.dtos.MatchDTO;

public interface MatchManagementService {

    List<MatchDTO> getMatchs(String userCod);

    MatchDTO addMatch(MatchDTO request, String string);

    MatchDTO updateMatch(MatchDTO request);

    MatchDTO deleteMatch(String request);

    ActionDTO addAction(ActionDTO request, String matchCod);

    String addMatchVideo(MultipartFile file, String username);

    Resource loadVideo(String video);

    void splitVideo(String videoCod, String userName);

}
