
package es.usal.coaching.enums;




public enum ActionTypeEnum {
    FALTA_A_FAVOR(1, "Falta a favor"),
    FALTA_EN_CONTRA(2, "Falta en contra"),
    PENALTY_A_FAVOR(3, "Penalti a favor"),
    PENALTY_EN_CONTRA(4, "Penalti en contra"),
    ROBO_DE_BALON(5, "Robo de balon"),
    PERDIDA_DE_BALON(6, "Perdida de balon"),
    OCASION_A_FAVOR(7, "Ocasion a favor"),
    OCASION_EN_CONTRA(8, "Ocasion en contra"),
    CORNER_A_FAVOR(9, "Corner a favor"),
    CORNER_EN_CONTRA(10, "Corner en contra"),
    GOL_A_FAVOR(11, "Gol a favor"),
    GOL_EN_CONTRA(12, "Gol en contra"),
    PARADA(13, "Parada"),
    
    INICIAL(14, "Jugador en el 11 inicial"),
    CAMBIO_ENTRA(15, "Jugador entra al campo"),
    CAMBIO_SALE(16, "Jugador sale del campo");

    Integer id;
    String descripcion;

    private ActionTypeEnum(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
}
