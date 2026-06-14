package model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Medico extends Pessoa {
    private String crm;
    private Especialidade especialidade;
    private double salario;

    public Medico(String crm, Especialidade especialidade, double salario, int id, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) {
        super(id, nome, cpf, telefone, email, dataNascimento);
        this.crm = crm;
        this.especialidade = especialidade;
        this.salario = salario;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public int calcularIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    @Override
    public String exibirResumo() {
        return "Id:             " + id + "\n" +
            "Nome:           " + nome + "\n" +
            "Idade:          " + calcularIdade() + " anos\n" +
            "CPF:            " + cpf + "\n" +
            "Telefone:       " + telefone + "\n" +
            "E-mail:         " + email + "\n" +
            "Nascimento:     " + dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
            "CRM:            " + crm + "\n" +
            "Especialidade:  " + especialidade.getEspecialidade() + "\n" +
            "Salário:   R$ " + String.format("%.2f", salario);
    }

    @Override
    public String toString() {
        return "Medico [id=" + id + ", crm=" + crm + ", nome=" + nome + ", cpf=" + cpf + ", telefone=" + telefone
                + ", especialidade=" + especialidade + ", email=" + email + ", salario=" + salario
                + ", dataNascimento=" + dataNascimento + "]";
    }
}