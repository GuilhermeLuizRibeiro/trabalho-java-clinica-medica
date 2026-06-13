package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consulta implements Agendavel, Serializable {
    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dataHora;
    private StatusConsulta status;
    private double valor;

    public Consulta(int id, Paciente paciente, Medico medico, LocalDateTime dataHora, double valor) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.status = StatusConsulta.AGENDADA;
        this.valor = valor;
    }

    public Consulta(int id, Paciente paciente, Medico medico, LocalDateTime dataHora, StatusConsulta status, double valor) {
    this.status = status;
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

    public String exibirResumo() {
        return "Id:       " + id + "\n" +
            "Paciente: " + paciente.getNome() + "\n" +
            "Médico:   " + medico.getNome() + "\n" +
            "Data:     " + dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n" +
            "Status:   " + status.getStatus() + "\n" +
            "Valor:    R$ " + String.format("%.2f", valor);
    }

    @Override
    public void cancelar() {
        this.status = StatusConsulta.CANCELADA;
    }

    @Override
    public boolean estaAtivo() {
        return (status == StatusConsulta.AGENDADA || status == StatusConsulta.REMARCADA);
    }

    @Override
    public void remarcar(LocalDateTime dataHoraRamarcada) {
        this.dataHora = dataHoraRamarcada;
        this.status = StatusConsulta.REMARCADA;
    }
}