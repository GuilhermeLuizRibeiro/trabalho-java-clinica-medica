package model;

import java.time.LocalDate;
import java.time.Period;

public class Paciente extends Pessoa {
    private TipoSanguineo tipoSanguineo;
    private String convenio;

    public Paciente(TipoSanguineo tipoSanguineo, String convenio, int id, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) {
        super(id, nome, cpf, telefone, email, dataNascimento);
        this.tipoSanguineo = tipoSanguineo;
        this.convenio = convenio;
    }

    public TipoSanguineo getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    @Override
    public int calcularIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    @Override
    public String exibirResumo() {
        return "Paciente: " + nome + " | Idade: " + calcularIdade() + " anos";
    }

    @Override
    public String toString() {
        return "Paciente [id=" + id + ", tipoSanguineo=" + tipoSanguineo + ", nome=" + nome + ", cpf=" + cpf
                + ", telefone=" + telefone + ", email=" + email + ", convenio=" + convenio + ", dataNascimento="
                + dataNascimento + "]";
    }
}