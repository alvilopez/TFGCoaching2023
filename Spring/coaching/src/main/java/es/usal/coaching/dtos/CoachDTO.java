package es.usal.coaching.dtos;

public class CoachDTO {

    private String cod;
    
    private String name;
    private String surname;
    private String dni;
    private String email;

    private TeamDTO team;
    private String imgSrc;

    

    public CoachDTO(){};

    public CoachDTO(String cod, String name, String dni, String email, TeamDTO team) {
        this.cod = cod;
        this.name = name;
        this.dni = dni;
        this.email = email;
        this.team = team;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TeamDTO getTeam() {
        return team;
    }

    public void setTeam(TeamDTO team) {
        this.team = team;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
    
    
}
