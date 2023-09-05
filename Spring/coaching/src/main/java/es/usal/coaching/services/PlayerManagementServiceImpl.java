package es.usal.coaching.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Collection;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.usal.coaching.dtos.ActionDTO;
import es.usal.coaching.dtos.PlayerDTO;
import es.usal.coaching.entities.Action;
import es.usal.coaching.entities.Player;
import es.usal.coaching.entities.Team;
import es.usal.coaching.mappers.ActionEntityToDTOMapper;
import es.usal.coaching.mappers.PlayerDTOToEntityMapper;
import es.usal.coaching.mappers.PlayerEntityToDTOMapper;
import es.usal.coaching.repositories.ActionRepository;
import es.usal.coaching.repositories.CoachRepository;
import es.usal.coaching.repositories.PlayerRepository;
import es.usal.coaching.repositories.TeamRepository;
import es.usal.coaching.security.entity.Coach;


@Service
public class PlayerManagementServiceImpl implements PlayerManagementService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    CoachRepository coachRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    ActionRepository actionRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    FilesStorageService storageService;


    @Override
    public Collection<PlayerDTO> getPlayers(String username) {

        Coach coach = coachRepository.findByNameUsuario(username);
        Collection<PlayerDTO> response = new ArrayList<PlayerDTO>();
        for(Player p : coach.getTeam().getPlayers()){
            
            response.add(PlayerEntityToDTOMapper.parser(p));
        }
        return response;
    }

    @Override
    public PlayerDTO addPlayer(PlayerDTO request) {
        Player response;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        String username = userDetails.getUsername();
        
        try{
            Player playerToSave = PlayerDTOToEntityMapper.parser(request);
            playerToSave.setHashString(passwordEncoder.encode(request.getEmail()));
            response = playerRepository.save(playerToSave);
            
            Coach coach = coachRepository.findByNameUsuario(username);
            coach.getTeam().getPlayers().add(response);
            teamRepository.save(coach.getTeam());            

            return PlayerEntityToDTOMapper.parser(response);
        } catch (Exception e){
            return null;
        }
        
    }

    

    @Override
    public PlayerDTO updatePlayer(PlayerDTO request) {
        PlayerDTO response;
                
        try{
            Player p = playerRepository.findByDni(request.getDni());
            p = PlayerDTOToEntityMapper.change(p, request);
            
            
            response = PlayerEntityToDTOMapper.parser(playerRepository.save(p));
            return response;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public PlayerDTO deletePlayer(String request, String nameUsuario) {
        try{
        Player playerToDelete = playerRepository.findByDni(request);
        
        Coach coach = coachRepository.findByNameUsuario(nameUsuario);
        coach.getTeam().getPlayers().remove(playerToDelete);
        teamRepository.save(coach.getTeam());

        Collection<Action> actions = actionRepository.findAllByPlayerId(playerToDelete.getId());
        if(!actions.isEmpty()){
            actionRepository.deleteAll(actions);
        }
        playerRepository.delete(playerToDelete);

        return PlayerEntityToDTOMapper.parser(playerToDelete);
        } catch (Exception e){
            
            return null;
        }
    }

    @Override
    public Collection<ActionDTO> getStats(String nameUsuario) {
        // TODO Auto-generated method stub
        
        

        // Collection<Object[]> result = playerRepository.getStats(ids);

        // Collection<StatDTO> response = new ArrayList<>();
        // String antPlayer = null;
        // StatDTO stat = null;
        // for (Object[] o : result) {
        //     if(antPlayer == null || !o[0].equals(antPlayer)){
        //         if(antPlayer!=null){
        //             response.add(stat);
        //         }
        //         stat = new StatDTO();
        //         stat.setPlayerName(o[0].toString() + o[1].toString());
        //         stat.getAcciones().put(o[2].toString(), Integer.parseInt(o[3].toString()));
        //     }else{
        //         stat.getAcciones().put(o[2].toString(), Integer.parseInt(o[3].toString()));
                
        //     }
            
        //     antPlayer = o[0].toString();

            
        // }

        // response.add(stat);
        Collection<ActionDTO> response = new ArrayList<>();

        Coach coach = coachRepository.findByNameUsuario(nameUsuario);
        
        Collection<Long> ids = new ArrayList<>();

        for( Player p : coach.getTeam().getPlayers()){
             ids.add(p.getId());
        }

        Collection<Action> result = actionRepository.findAllByPlayerIdIn(new HashSet<>(ids));

        for (Action action : result) {
            response.add(ActionEntityToDTOMapper.parser(action));
        }
        return response;
    }

    @Override
    public void uploadImage(MultipartFile imgFile, Long playerId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        String username = userDetails.getUsername();
        String fileName = "IMG_Player_" + playerId.toString() + "." + FilenameUtils.getExtension(imgFile.getOriginalFilename());
        try {
            Coach coach = coachRepository.findByNameUsuario(username);
            for(Player p : coach.getTeam().getPlayers()){
                if(p.getId().equals(playerId)){
                    p.setImgName(fileName);
                    playerRepository.save(p);
                }
            }
            storageService.save(imgFile, username, fileName);
        } catch (Exception e) {         
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'uploadImage'");
        }
    }
    

}
