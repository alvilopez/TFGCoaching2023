package es.usal.coaching.mappers;

import es.usal.coaching.dtos.PlayerDTO;
import es.usal.coaching.entities.Player;


public class PlayerEntityToDTOMapper {
    public static PlayerDTO parser(Player player){
        PlayerDTO playerDTO = new PlayerDTO();
        
        playerDTO.setId(player.getId());
        playerDTO.setAge(player.getAge());
        playerDTO.setDni(player.getDni());
        playerDTO.setEmail(player.getEmail());
        playerDTO.setHight(player.getHight());
        playerDTO.setName(player.getName());
        playerDTO.setNumber(player.getNumber());
        playerDTO.setPosition(player.getPosition());
        playerDTO.setSurname(player.getSurname());
        playerDTO.setWeight(player.getWeight());
        playerDTO.setImgName(player.getImgName());



        return playerDTO;
        
    }
}

