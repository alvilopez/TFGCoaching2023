package es.usal.coaching.dtos;

import es.usal.coaching.enums.ActionTypeEnum;

public class ActionDTO {
    private Integer min;
    private ActionTypeEnum type;
    private PlayerDTO player;

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public ActionTypeEnum getType() {
        return type;
    }

    public void setType(ActionTypeEnum type) {
        this.type = type;
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }
    
    
}
