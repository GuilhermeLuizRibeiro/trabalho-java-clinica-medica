package controller;

import dal.ConsultaDao;
import dal.MedicoDao;
import dal.PacienteDao;
import exception.ArquivoNaoEncontradoException;
import exception.ConsultaNaoCancelavelException;
import exception.ConsultaNaoEncontradaException;
import exception.ConsultaNaoFinalizavelException;
import exception.ConsultaNaoRemarcavelException;
import exception.CpfInvalidoException;
import exception.CrmInvalidoException;
import exception.DadosObrigatoriosException;
import exception.DataInvalidaException;
import exception.ErroAoLerArquivoException;
import exception.ErroAoSalvarException;
import exception.MedicoIndisponivelException;
import exception.MedicoNaoEncontradoException;
import exception.OperacaoNaoPermitidaException;
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
    public void cadastrarPaciente(TipoSanguineo tipoSanguineo, String convenio, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) throws DadosObrigatoriosException, CpfInvalidoException, DataInvalidaException, ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, ValorInvalidoException {
        if (PacienteDao.existePorCpf(cpf)) {
            throw new CpfInvalidoException("CPF já cadastrado");
        }

        Paciente paciente = PacienteFactory.criarPaciente(tipoSanguineo, convenio, gerarIdPaciente(), nome, cpf, telefone, email, dataNascimento);
        PacienteDao.adicionarPaciente(paciente);
    }

    public void alterarPaciente(int id, TipoSanguineo tipoSanguineo, String convenio, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) throws DadosObrigatoriosException, CpfInvalidoException, DataInvalidaException, ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, PacienteNaoEncontradoException, ValorInvalidoException {
        Paciente paciente = PacienteFactory.criarPaciente(tipoSanguineo, convenio, id, nome, cpf, telefone, email, dataNascimento);
        PacienteDao.alterarPaciente(paciente);
    }

    public void deletarPaciente(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, OperacaoNaoPermitidaException, PacienteNaoEncontradoException {
        List<Consulta> consultas = ConsultaDao.listarConsultas();
        boolean temConsulta = consultas.stream()
            .anyMatch(c -> c.getPaciente().getId() == id);

        if (temConsulta) {
            throw new OperacaoNaoPermitidaException("Paciente possui consultas vinculadas e não pode ser deletado");
        }

        PacienteDao.deletarPaciente(id);
    }

    public List<Paciente> listarPacientes() throws ArquivoNaoEncontradoException, ErroAoLerArquivoException {
        return PacienteDao.listarPacientes();
    }

    public Paciente buscarPacientePorId(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, PacienteNaoEncontradoException{
        return PacienteDao.buscarPacientePorId(id);
    }

    private int gerarIdPaciente() throws ErroAoLerArquivoException {
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

    public void cadastrarMedico(String crm, Especialidade especialidade, double salario, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) throws DadosObrigatoriosException, CpfInvalidoException, CrmInvalidoException, ValorInvalidoException, DataInvalidaException, ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException {
        if (MedicoDao.existePorCpf(cpf)) {
            throw new CpfInvalidoException("CPF já cadastrado");
        }
        if (MedicoDao.existePorCrm(crm)) {
            throw new CrmInvalidoException("CRM já cadastrado");
        }

        Medico medico = MedicoFactory.criarMedico(crm, especialidade, salario, gerarIdMedico(), nome, cpf, telefone, email, dataNascimento);
        MedicoDao.adicionarMedico(medico);
    }

    public void alterarMedico(int id, String crm, Especialidade especialidade, double salario, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) throws DadosObrigatoriosException, CpfInvalidoException, CrmInvalidoException, ValorInvalidoException, DataInvalidaException, ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, MedicoNaoEncontradoException {
        Medico medico = MedicoFactory.criarMedico(crm, especialidade, salario, id, nome, cpf, telefone, email, dataNascimento);
        MedicoDao.alterarMedico(medico);
    }

    public void deletarMedico(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, OperacaoNaoPermitidaException, MedicoNaoEncontradoException {
        List<Consulta> consultas = ConsultaDao.listarConsultas();
        boolean temConsulta = consultas.stream()
            .anyMatch(c -> c.getMedico().getId() == id);

        if (temConsulta) {
            throw new OperacaoNaoPermitidaException("Médico possui consultas vinculadas e não pode ser deletado");
        }

        MedicoDao.deletarMedico(id);
    }

    public List<Medico> listarMedicos() throws ArquivoNaoEncontradoException, ErroAoLerArquivoException {
        return MedicoDao.listarMedicos();
    }

    public Medico buscarMedicoPorId(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, MedicoNaoEncontradoException {
        return MedicoDao.buscarMedicoPorId(id);
    }

    private int gerarIdMedico() throws ErroAoLerArquivoException {
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

    public void agendarConsulta(int idPaciente, int idMedico, LocalDateTime dataHora, double valor) throws PacienteNaoEncontradoException, MedicoNaoEncontradoException, DadosObrigatoriosException, DataInvalidaException, ValorInvalidoException, ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, MedicoIndisponivelException {
        Paciente paciente = PacienteDao.buscarPacientePorId(idPaciente);
        Medico medico = MedicoDao.buscarMedicoPorId(idMedico);

        boolean medicoOcupado = ConsultaDao.buscarPorMedico(idMedico).stream()
            .filter(Consulta::estaAtivo)
            .anyMatch(c -> c.getDataHora().equals(dataHora));

        if (medicoOcupado) {
            throw new MedicoIndisponivelException("Médico já possui consulta agendada nesse horário");
        }

        Consulta consulta = ConsultaFactory.criarConsulta(gerarIdConsulta(), paciente, medico, dataHora, valor);
        ConsultaDao.adicionarConsulta(consulta);
    }

    public void cancelarConsulta(int id) throws ConsultaNaoEncontradaException, ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, ConsultaNaoCancelavelException {
        Consulta consulta = ConsultaDao.buscarConsultaPorId(id);

        if (!consulta.estaAtivo()) {
            throw new ConsultaNaoCancelavelException("Consulta já foi finalizada ou cancelada");
        }

        consulta.cancelar();
        ConsultaDao.alterarConsulta(consulta);
    }

    public void remarcarConsulta(int id, LocalDateTime novaDataHora) throws ConsultaNaoEncontradaException, ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, DataInvalidaException, ConsultaNaoRemarcavelException, MedicoIndisponivelException {
        if (novaDataHora.isBefore(LocalDateTime.now())) {
            throw new DataInvalidaException("Data da consulta não pode ser no passado");
        }

        Consulta consulta = ConsultaDao.buscarConsultaPorId(id);

        if (!consulta.estaAtivo()) {
            throw new ConsultaNaoRemarcavelException("Consulta não pode ser remarcada");
        }

        boolean medicoOcupado = ConsultaDao.buscarPorMedico(consulta.getMedico().getId()).stream()
            .filter(Consulta::estaAtivo)
            .filter(c -> c.getId() != consulta.getId())
            .anyMatch(c -> c.getDataHora().equals(novaDataHora));

        if (medicoOcupado) {
            throw new MedicoIndisponivelException("Médico já possui consulta nesse horário");
        }

        consulta.remarcar(novaDataHora);
        ConsultaDao.alterarConsulta(consulta);
    }

    public void finalizarConsulta(int id) throws ConsultaNaoEncontradaException, ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, ConsultaNaoFinalizavelException {
        Consulta consulta = ConsultaDao.buscarConsultaPorId(id);

        if (!consulta.estaAtivo()) {
            throw new ConsultaNaoFinalizavelException("Consulta já foi finalizada ou cancelada");
        }

        consulta.finalizar();
        ConsultaDao.alterarConsulta(consulta);
    }

    public void deletarConsulta(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ConsultaNaoEncontradaException, ErroAoSalvarException {
        ConsultaDao.deletarConsulta(id);
    }

    public List<Consulta> listarConsultas() throws ArquivoNaoEncontradoException, ErroAoLerArquivoException {
        return ConsultaDao.listarConsultas();
    }

    public List<Consulta> listarConsultas(int idMedico) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException {
        return ConsultaDao.buscarPorMedico(idMedico);
    }

    public List<Consulta> listarConsultas(StatusConsulta status) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException {
        return ConsultaDao.buscarPorStatus(status);
    }

    public Consulta buscarConsultaPorId(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ConsultaNaoEncontradaException {
        return ConsultaDao.buscarConsultaPorId(id);
    }

    private int gerarIdConsulta() throws ErroAoLerArquivoException {
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