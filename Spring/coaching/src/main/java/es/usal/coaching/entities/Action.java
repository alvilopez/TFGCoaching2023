package es.usal.coaching.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import es.usal.coaching.enums.ActionTypeEnum;




@Entity
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private Integer min;

    @Enumerated(EnumType.STRING)
    private ActionTypeEnum type;

    @ManyToOne
    private Player player;

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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    


    
}
