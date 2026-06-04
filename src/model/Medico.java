package model;

import java.time.LocalDate;
import java.time.Period;

public class Medico extends Pessoa {
    private String crm;
    private Especialidade especialidade;
    private double salarioBase;

    public Medico(String crm, Especialidade especialidade, double salarioBase, int id, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) {
        super(id, nome, cpf, telefone, email, dataNascimento);
        this.crm = crm;
        this.especialidade = especialidade;
        this.salarioBase = salarioBase;
    }

    public double calcularBonus(double percentual) {
        return salarioBase + (salarioBase * (percentual / 100));
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

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    @Override
    public int calcularIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    @Override
    public String exibirResumo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return "Medico [id=" + id + ", crm=" + crm + ", nome=" + nome + ", cpf=" + cpf + ", telefone=" + telefone
                + ", especialidade=" + especialidade + ", email=" + email + ", salarioBase=" + salarioBase
                + ", dataNascimento=" + dataNascimento + "]";
    }
}