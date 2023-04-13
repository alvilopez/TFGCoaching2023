package es.usal.coaching.enums;

public enum PlayerPositionEnum {
    GOALKEEPER("POR"),
    CENTRAL_BACK("DFC"),
    RIGHT_BACK("LD"),
    LEFT_BACK("LI"),
    MIDFIELDER("LD"),
    RIGHWING("ED"),
    LEFTWING("EI"),
    STRIKER("DC");

    String initials;

    private PlayerPositionEnum(String initials) {
        this.initials = initials;
    }

    public String getInitials() {
        return initials;
    }

    

}
