package factory;

import exception.CpfInvalidoException;
import exception.DadosObrigatoriosException;
import exception.DataInvalidaException;
import exception.ValorInvalidoException;
import java.time.LocalDate;
import model.Paciente;
import model.TipoSanguineo;

public abstract class PacienteFactory {
    public static Paciente criarPaciente(TipoSanguineo tipoSanguineo, String convenio, int id, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) throws DadosObrigatoriosException, CpfInvalidoException, DataInvalidaException, ValorInvalidoException {

        if (nome == null || nome.isBlank()) {
            throw new DadosObrigatoriosException("Nome é obrigatório");
        }
        if (cpf == null || cpf.isBlank()) {
            throw new DadosObrigatoriosException("CPF é obrigatório");
        }
        if (telefone != null && !telefone.isBlank()) {
            if (!telefone.matches("[0-9()\\-\\s]+")) {
                throw new ValorInvalidoException("Telefone inválido");
            }
        }
        if (tipoSanguineo == null) {
            throw new DadosObrigatoriosException("Tipo sanguíneo é obrigatório");
        }
        if (dataNascimento == null) {
            throw new DadosObrigatoriosException("Data de nascimento é obrigatória");
        }
        if (!validarCpf(cpf)) {
            throw new CpfInvalidoException("CPF inválido");
        }
        if (email != null && !email.isBlank()) {
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                throw new DadosObrigatoriosException("Email inválido");
            }
        }
        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new DataInvalidaException("Data de nascimento não pode ser no futuro");
        }

        return new Paciente(tipoSanguineo, convenio, id, nome, cpf, telefone, email, dataNascimento);
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