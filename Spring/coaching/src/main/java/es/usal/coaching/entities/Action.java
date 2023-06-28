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
    private Long min;

    @ManyToOne
    private Match match;    

    @Enumerated(EnumType.STRING)
    private ActionTypeEnum type;
    
    private String imgSrc;

    @ManyToOne
    private Player player;

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
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

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    
    
    


    
}
