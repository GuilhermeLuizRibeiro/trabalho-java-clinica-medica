package factory;

import exception.CpfInvalidoException;
import exception.CrmInvalidoException;
import exception.DadosObrigatoriosException;
import exception.DataInvalidaException;
import exception.ValorInvalidoException;
import java.time.LocalDate;
import model.Especialidade;
import model.Medico;

public abstract class MedicoFactory {
    public static Medico criarMedico(String crm, Especialidade especialidade, double salarioBase, int id, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) throws DadosObrigatoriosException, CpfInvalidoException, CrmInvalidoException, ValorInvalidoException, DataInvalidaException {

        if (nome == null || nome.isBlank()) {
            throw new DadosObrigatoriosException("Nome é obrigatório");
        }
        if (cpf == null || cpf.isBlank()) {
            throw new DadosObrigatoriosException("CPF é obrigatório");
        }
        if (crm == null || crm.isBlank()) {
            throw new DadosObrigatoriosException("CRM é obrigatório");
        }
        if (especialidade == null) {
            throw new DadosObrigatoriosException("Especialidade é obrigatória");
        }
        if (dataNascimento == null) {
            throw new DadosObrigatoriosException("Data de nascimento é obrigatória");
        }
        String cpfNumeros = cpf.replaceAll("[^0-9]", "");
        if (cpfNumeros.length() != 11) {
            throw new CpfInvalidoException("CPF deve conter 11 dígitos");
        }
        if (!crm.matches("CRM/[A-Z]{2} \\d{4,6}")) {
            throw new CrmInvalidoException("CRM inválido, formato esperado: CRM/UF 123456");
        }
        if (salarioBase < 0) {
            throw new ValorInvalidoException("Salário base não pode ser negativo");
        }
        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new DataInvalidaException("Data de nascimento não pode ser no futuro");
        }
        
        return new Medico(crm, especialidade, salarioBase, id, nome, cpf, telefone, email, dataNascimento);
    }
}