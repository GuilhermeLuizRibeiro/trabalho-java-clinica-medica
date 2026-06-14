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
    public static Medico criarMedico(String crm, Especialidade especialidade, double salario, int id, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) throws DadosObrigatoriosException, CpfInvalidoException, CrmInvalidoException, ValorInvalidoException, DataInvalidaException {

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
        if (!validarCpf(cpf)) {
            throw new CpfInvalidoException("CPF inválido");
        }
        if (!crm.matches("CRM/[A-Z]{2} \\d{4,6}")) {
            throw new CrmInvalidoException("Formato esperado: CRM/UF 123456");
        }
        if (salario < 0) {
            throw new ValorInvalidoException("Salário não pode ser negativo");
        }
        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new DataInvalidaException("Data de nascimento não pode ser no futuro");
        }
        
        return new Medico(crm, especialidade, salario, id, nome, cpf, telefone, email, dataNascimento);
    }

    private static boolean validarCpf(String cpf) {
        String cpfNumeros = cpf.replaceAll("[^0-9]", "");

        if (cpfNumeros.length() != 11) {
            return false;
        }

        if (cpfNumeros.matches("(\\d)\\1{10}")) {
            return false;
        }

        int soma = 0;

        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpfNumeros.charAt(i)) * (10 - i);
        }

        int resto = soma % 11;
        int digito1 = (resto < 2) ? 0 : 11 - resto;

        soma = 0;

        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpfNumeros.charAt(i)) * (11 - i);
        }

        resto = soma % 11;
        int digito2 = (resto < 2) ? 0 : 11 - resto;

        return digito1 == Character.getNumericValue(cpfNumeros.charAt(9)) && digito2 == Character.getNumericValue(cpfNumeros.charAt(10));
    }
}