package es.usal.coaching.dtos;

import java.util.HashMap;
import java.util.Map;

public class StatDTO {
    String playerName;
    Map<String, Integer> acciones;
    Long minsPlayed;

    public StatDTO(){
        this.acciones = new HashMap<String,Integer>();
    }


    public String getPlayerName() {
        return playerName;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public Map<String, Integer> getAcciones() {
        return acciones;
    }
    public void setAcciones(Map<String, Integer> acciones) {
        this.acciones = acciones;
    }
    public Long getMinsPlayed() {
        return minsPlayed;
    }
    public void setMinsPlayed(Long minsPlayed) {
        this.minsPlayed = minsPlayed;
    }
}


