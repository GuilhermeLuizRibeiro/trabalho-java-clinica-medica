package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Consulta implements Agendavel, Serializable {
    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dataHora;
    private StatusConsulta status;
    private double valor;

    public Consulta(int id, Paciente paciente, Medico medico, LocalDateTime dataHora, StatusConsulta status, double valor) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.status = status;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public void setStatus(StatusConsulta status) {
        this.status = status;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public void agendar(LocalDateTime dataHora) {
        this.dataHora = dataHora;
        this.status = StatusConsulta.AGENDADA;
    }

    @Override
    public void cancelar() {
        this.status = StatusConsulta.CANCELADA;
    }

    @Override
    public boolean estaAtivo() {
        if (!status.getStatus().equals("Agendada") || !status.getStatus().equals("Remarcada")) {
            return false;
        }
        return true;
    }

    @Override
    public void remarcar(LocalDateTime dataHoraRamarcada) {
        this.dataHora = dataHoraRamarcada;
        this.status = StatusConsulta.REMARCADA;
    }
}