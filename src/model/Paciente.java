package model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

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
        return "Id:             " + id + "\n" +
           "Nome:           " + nome + "\n" +
           "CPF:            " + cpf + "\n" +
           "Telefone:       " + telefone + "\n" +
           "E-mail:         " + email + "\n" +
           "Nascimento:     " + dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
           "Tipo Sanguíneo: " + tipoSanguineo.getTipo() + "\n" +
           "Convênio:       " + (convenio.isBlank() ? "Nenhum" : convenio);
    }

    @Override
    public String toString() {
        return "Paciente [id=" + id + ", tipoSanguineo=" + tipoSanguineo + ", nome=" + nome + ", cpf=" + cpf
                + ", telefone=" + telefone + ", email=" + email + ", convenio=" + convenio + ", dataNascimento="
                + dataNascimento + "]";
    }
}