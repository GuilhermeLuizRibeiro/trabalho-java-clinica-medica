package factory;

import exception.DadosObrigatoriosException;
import exception.DataInvalidaException;
import exception.ValorInvalidoException;
import java.time.LocalDateTime;
import model.Consulta;
import model.Medico;
import model.Paciente;

public abstract class ConsultaFactory {
    public static Consulta criarConsulta(int id, Paciente paciente, Medico medico, LocalDateTime dataHora, double valor) throws DadosObrigatoriosException, DataInvalidaException, ValorInvalidoException {

        if (paciente == null) {
            throw new DadosObrigatoriosException("Paciente é obrigatório");
        }
        if (medico == null) {
            throw new DadosObrigatoriosException("Médico é obrigatório");
        }
        if (dataHora == null) {
            throw new DadosObrigatoriosException("Data e hora são obrigatórias");
        }
        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new DataInvalidaException("Data da consulta não pode ser no passado");
        }
        if (valor < 0) {
            throw new ValorInvalidoException("Valor da consulta não pode ser negativo");
        }
        
        return new Consulta(id, paciente, medico, dataHora, valor);
    }
}