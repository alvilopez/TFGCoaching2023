package es.usal.coaching.mappers;

import es.usal.coaching.dtos.ActionDTO;
import es.usal.coaching.entities.Action;

public class ActionDTOToEntityMapper {
    public static Action parser(ActionDTO actionDTO){
        Action action = new Action();
        action.setMin(actionDTO.getMin());
        action.setPlayer(PlayerDTOToEntityMapper.parser(actionDTO.getPlayer()));
        action.setType(actionDTO.getType());
        
        return action;
    }
}
