package es.usal.coaching.dtos;

import java.util.ArrayList;

public class TeamDTO {

    
    private String cod;

    private String name;
    private String category;

    
    private ArrayList<PlayerDTO> players;


    

    public TeamDTO(String cod, String name, String category, ArrayList<PlayerDTO> players) {
        this.cod = cod;
        this.name = name;
        this.category = category;
        this.players = players;
    }


    public TeamDTO() {
    }


    public String getCod() {
        return cod;
    }


    public void setCod(String cod) {
        this.cod = cod;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }


    public ArrayList<PlayerDTO> getPlayers() {
        return players;
    }


    public void setPlayers(ArrayList<PlayerDTO> players) {
        this.players = players;
    }

    
}
