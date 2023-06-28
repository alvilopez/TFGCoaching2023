package es.usal.coaching.dtos;

import java.util.ArrayList;

public class TeamDTO {

    private Long id;
    private String cod;
    private String name;
    private String category;
    private Boolean principal;    
    private ArrayList<PlayerDTO> players;    

    public TeamDTO(String cod, String name, String category, ArrayList<PlayerDTO> players) {
        this.cod = cod;
        this.name = name;
        this.category = category;
        this.players = players;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
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


    public Boolean getPrincipal() {
        return principal;
    }


    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    
    
}
