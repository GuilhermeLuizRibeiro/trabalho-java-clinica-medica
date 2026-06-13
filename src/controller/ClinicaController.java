package controller;

import dal.ConsultaDao;
import dal.MedicoDao;
import dal.PacienteDao;
import exception.ArquivoNaoEncontradoException;
import exception.ConsultaNaoEncontradaException;
import exception.CpfInvalidoException;
import exception.CrmInvalidoException;
import exception.DadosObrigatoriosException;
import exception.DataInvalidaException;
import exception.ErroAoLerArquivoException;
import exception.ErroAoSalvarException;
import exception.MedicoNaoEncontradoException;
import exception.PacienteNaoEncontradoException;
import exception.ValorInvalidoException;
import factory.ConsultaFactory;
import factory.MedicoFactory;
import factory.PacienteFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import model.Consulta;
import model.Especialidade;
import model.Medico;
import model.Paciente;
import model.StatusConsulta;
import model.TipoSanguineo;

public class ClinicaController {
    public void cadastrarPaciente(TipoSanguineo tipoSanguineo, String convenio, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) throws DadosObrigatoriosException, CpfInvalidoException, DataInvalidaException, ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, ClassNotFoundException {
        Paciente paciente = PacienteFactory.criarPaciente(tipoSanguineo, convenio, gerarIdPaciente(), nome, cpf, telefone, email, dataNascimento);
        PacienteDao.adicionarPaciente(paciente);
    }

    public void alterarPaciente(int id, TipoSanguineo tipoSanguineo, String convenio, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) throws DadosObrigatoriosException, CpfInvalidoException, DataInvalidaException, ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, ClassNotFoundException {
        Paciente paciente = PacienteFactory.criarPaciente(tipoSanguineo, convenio, id, nome, cpf, telefone, email, dataNascimento);
        PacienteDao.alterarPaciente(paciente);
    }

    public void deletarPaciente(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException {
        PacienteDao.deletarPaciente(id);
    }

    public List<Paciente> listarPacientes() throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ClassNotFoundException {
        return PacienteDao.listarPacientes();
    }

    public Paciente buscarPacientePorId(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, PacienteNaoEncontradoException{
        return PacienteDao.buscarPacientePorId(id);
    }

    private int gerarIdPaciente() throws ErroAoLerArquivoException, ClassNotFoundException {
        try {
            List<Paciente> pacientes = PacienteDao.listarPacientes();
            return pacientes.stream()
                    .mapToInt(Paciente::getId)
                    .max()
                    .orElse(0) + 1;
        } catch (ArquivoNaoEncontradoException e) {
            return 1;
        }
    }

    public void cadastrarMedico(String crm, Especialidade especialidade, double salarioBase, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) throws DadosObrigatoriosException, CpfInvalidoException, CrmInvalidoException, ValorInvalidoException, DataInvalidaException, ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, ClassNotFoundException {
        Medico medico = MedicoFactory.criarMedico(crm, especialidade, salarioBase, gerarIdMedico(), nome, cpf, telefone, email, dataNascimento);
        MedicoDao.adicionarMedico(medico);
    }

    public void alterarMedico(int id, String crm, Especialidade especialidade, double salarioBase, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) throws DadosObrigatoriosException, CpfInvalidoException, CrmInvalidoException, ValorInvalidoException, DataInvalidaException, ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, ClassNotFoundException {
        Medico medico = MedicoFactory.criarMedico(crm, especialidade, salarioBase, id, nome, cpf, telefone, email, dataNascimento);
        MedicoDao.alterarMedico(medico);
    }

    public void deletarMedico(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException {
        MedicoDao.deletarMedico(id);
    }

    public List<Medico> listarMedicos() throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ClassNotFoundException {
        return MedicoDao.listarMedicos();
    }

    public Medico buscarMedicoPorId(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, MedicoNaoEncontradoException {
        return MedicoDao.buscarMedicoPorId(id);
    }

    private int gerarIdMedico() throws ErroAoLerArquivoException, ClassNotFoundException {
        try {
            List<Medico> medicos = MedicoDao.listarMedicos();
            return medicos.stream()
                    .mapToInt(Medico::getId)
                    .max()
                    .orElse(0) + 1;
        } catch (ArquivoNaoEncontradoException e) {
            return 1;
        }
    }

    public void agendarConsulta(int idPaciente, int idMedico, LocalDateTime dataHora, double valor) throws PacienteNaoEncontradoException, MedicoNaoEncontradoException, DadosObrigatoriosException, DataInvalidaException, ValorInvalidoException, ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, ClassNotFoundException {
        Paciente paciente = PacienteDao.buscarPacientePorId(idPaciente);
        Medico medico = MedicoDao.buscarMedicoPorId(idMedico);
        Consulta consulta = ConsultaFactory.criarConsulta(gerarIdConsulta(), paciente, medico, dataHora, valor);
        ConsultaDao.adicionarConsulta(consulta);
    }

    public void cancelarConsulta(int id) throws ConsultaNaoEncontradaException, ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException {
        Consulta consulta = ConsultaDao.buscarConsultaPorId(id);
        consulta.cancelar();
        ConsultaDao.alterarConsulta(consulta);
    }

    public void remarcarConsulta(int id, LocalDateTime novaDataHora) throws ConsultaNaoEncontradaException, ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, DataInvalidaException {
        if (novaDataHora.isBefore(LocalDateTime.now())) {
            throw new DataInvalidaException("Data da consulta não pode ser no passado");
        }

        Consulta consulta = ConsultaDao.buscarConsultaPorId(id);
        consulta.remarcar(novaDataHora);
        ConsultaDao.alterarConsulta(consulta);
    }

    public void deletarConsulta(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ConsultaNaoEncontradaException, ErroAoSalvarException {
        ConsultaDao.deletarConsulta(id);
    }

    public List<Consulta> listarConsultas() throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ClassNotFoundException {
        return ConsultaDao.listarConsultas();
    }

    public List<Consulta> listarConsultas(int idMedico) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ClassNotFoundException {
        return ConsultaDao.buscarPorMedico(idMedico);
    }

    public List<Consulta> listarConsultas(StatusConsulta status) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ClassNotFoundException {
        return ConsultaDao.buscarPorStatus(status);
    }

    public Consulta buscarConsultaPorId(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ConsultaNaoEncontradaException {
        return ConsultaDao.buscarConsultaPorId(id);
    }

    private int gerarIdConsulta() throws ErroAoLerArquivoException, ClassNotFoundException {
        try {
            List<Consulta> consultas = ConsultaDao.listarConsultas();
            return consultas.stream()
                    .mapToInt(Consulta::getId)
                    .max()
                    .orElse(0) + 1;
        } catch (ArquivoNaoEncontradoException e) {
            return 1;
        }
    }
}