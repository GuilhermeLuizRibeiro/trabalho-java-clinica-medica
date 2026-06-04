package model;

import java.time.LocalDateTime;

public interface Agendavel {
    void agendar(LocalDateTime dataHora);
    void cancelar();
    void remarcar(LocalDateTime horaDataRemarcada);
    boolean estaAtivo();
}