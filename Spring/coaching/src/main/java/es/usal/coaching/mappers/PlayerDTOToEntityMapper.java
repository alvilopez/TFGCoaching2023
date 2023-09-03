package es.usal.coaching.mappers;

import es.usal.coaching.dtos.PlayerDTO;
import es.usal.coaching.entities.Player;

public class PlayerDTOToEntityMapper {
    public static Player parser(PlayerDTO playerDTO){
        Player player = new Player();

        player.setId(playerDTO.getId());
        player.setAge(playerDTO.getAge());
        player.setDni(playerDTO.getDni());
        player.setEmail(playerDTO.getEmail());
        player.setHight(playerDTO.getHight());
        player.setName(playerDTO.getName());
        player.setNumber(playerDTO.getNumber());
        player.setPosition(playerDTO.getPosition());
        player.setSurname(playerDTO.getSurname());
        player.setWeight(playerDTO.getWeight());
        player.setImgName(playerDTO.getImgName());



        return player;
        
    }

    public static Player change(Player player, PlayerDTO playerDTO){
        

        player.setAge(playerDTO.getAge());
        player.setHight(playerDTO.getHight());
        player.setName(playerDTO.getName());
        player.setNumber(playerDTO.getNumber());
        player.setPosition(playerDTO.getPosition());
        player.setSurname(playerDTO.getSurname());
        player.setWeight(playerDTO.getWeight());



        return player;
        
    }
}
