package model;

public enum TipoSanguineo {
    A_POS("A positivo"),
    A_NEG("A negativo"),
    B_POS("B positivo"),
    B_NEG("B negativo"),
    AB_POS("AB positivo"),
    AB_NEG("AB negativo"),
    O_POS("O positivo"),
    O_NEG("O negativo");

    private String tipo;

    private TipoSanguineo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}