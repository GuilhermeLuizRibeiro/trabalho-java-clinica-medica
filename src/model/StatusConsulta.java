package model;

public enum StatusConsulta {
    AGENDADA("Agendada"),
    REALIZADA("Realizada"),
    CANCELADA("Cancelada"),
    REMARCADA("Remarcada");

    private String status;

    private StatusConsulta(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}