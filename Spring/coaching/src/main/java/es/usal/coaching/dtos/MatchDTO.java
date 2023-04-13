package es.usal.coaching.dtos;

import java.sql.Date;
import java.util.List;



public class MatchDTO {
    
    

    private Date date;
    private Integer matchNum;
    private String video;
    private String cod;
    
    private TeamDTO localTeam;
    private TeamDTO visitantTeam;

    private List<ActionDTO> actions;


    


    public MatchDTO( Date date, Integer matchNum, String video, TeamDTO localTeam, TeamDTO visitantTeam) {
        this.date = date;
        this.matchNum = matchNum;
        this.video = video;
        this.localTeam = localTeam;
        this.visitantTeam = visitantTeam;
    }


    public MatchDTO() {
    }



    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Integer getMatchNum() {
        return matchNum;
    }
    public void setMatchNum(Integer matchNum) {
        this.matchNum = matchNum;
    }
    public String getVideo() {
        return video;
    }
    public void setVideo(String video) {
        this.video = video;
    }

    public TeamDTO getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(TeamDTO localTeam) {
        this.localTeam = localTeam;
    }


    public TeamDTO getVisitantTeam() {
        return visitantTeam;
    }


    public void setVisitantTeam(TeamDTO visitantTeam) {
        this.visitantTeam = visitantTeam;
    }


    public List<ActionDTO> getActions() {
        return actions;
    }


    public void setActions(List<ActionDTO> actions) {
        this.actions = actions;
    }


    public String getCod() {
        return cod;
    }


    public void setCod(String cod) {
        this.cod = cod;
    }
    
    
    


    

}
