package model;

public enum Especialidade {
    CLINICA_GERAL("Clinica Geral"),
    CARDIOLOGIA("Cardiologia"),
    PEDIATRIA("Pediatria"),
    ORTOPEDIA("Ortopedia"),
    DERMATOLOGIA("Dermatologia"),
    NEUROLOGIA("Neurologia");

    private String especialidade;

    private Especialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }
}