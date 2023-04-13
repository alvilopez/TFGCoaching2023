package es.usal.coaching.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.usal.coaching.dtos.ActionDTO;
import es.usal.coaching.dtos.PlayerDTO;
import es.usal.coaching.entities.Action;
import es.usal.coaching.entities.Player;
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

    @Override
    public List<PlayerDTO> getPlayers(String username) {

        Coach coach = coachRepository.findByNameUsuario(username);
        List<PlayerDTO> response = new ArrayList<PlayerDTO>();
        for(Player p : coach.getTeam().getPlayers()){
            
            response.add(PlayerEntityToDTOMapper.parser(p));
        }
        return response;
    }

    @Override
    public PlayerDTO addPlayer(PlayerDTO request, String nameUsuario) {
        Player response;

        try{
            response = playerRepository.save(PlayerDTOToEntityMapper.parser(request));

            Coach coach = coachRepository.findByNameUsuario(nameUsuario);
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

        List<Action> actions = actionRepository.findAllByPlayerId(playerToDelete.getId());
        if(!actions.isEmpty()){
            for (Action action : actions) {
                action.setPlayer(null);
            }
        }
        playerRepository.delete(playerToDelete);

        return PlayerEntityToDTOMapper.parser(playerToDelete);
        } catch (Exception e){
            
            return null;
        }
    }

    @Override
    public List<ActionDTO> getStats(String nameUsuario) {
        // TODO Auto-generated method stub
        
        

        // List<Object[]> result = playerRepository.getStats(ids);

        // List<StatDTO> response = new ArrayList<>();
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
        List<ActionDTO> response = new ArrayList<>();

        Coach coach = coachRepository.findByNameUsuario(nameUsuario);
        
        List<Long> ids = new ArrayList<>();

        for( Player p : coach.getTeam().getPlayers()){
             ids.add(p.getId());
        }

        List<Action> result = actionRepository.findAllByPlayerIdIn(new HashSet<>(ids));

        for (Action action : result) {
            response.add(ActionEntityToDTOMapper.parser(action));
        }
        return response;
    }
    

}
