package es.usal.coaching.mappers;

import es.usal.coaching.dtos.ActionDTO;
import es.usal.coaching.entities.Action;


public class ActionEntityToDTOMapper {
    public static ActionDTO parser(Action action) {

        ActionDTO actionDTO = new ActionDTO();
        actionDTO.setMin(action.getMin());
        actionDTO.setPlayer(PlayerEntityToDTOMapper.parser(action.getPlayer()));
        actionDTO.setType((action.getType()));

        return actionDTO;
       
    }
}
