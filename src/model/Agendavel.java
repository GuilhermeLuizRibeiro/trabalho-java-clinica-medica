package model;

import java.time.LocalDateTime;

public interface Agendavel {
    void cancelar();
    void remarcar(LocalDateTime dataHoraRemarcada);
    void finalizar();
    boolean estaAtivo();
}