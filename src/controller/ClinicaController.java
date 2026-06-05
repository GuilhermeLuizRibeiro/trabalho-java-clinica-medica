package controller;

import dal.ConsultaDao;
import dal.MedicoDao;
import dal.PacienteDao;
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
    public void cadastrarPaciente(TipoSanguineo tipoSanguineo, String convenio, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) throws Exception {
        Paciente paciente = PacienteFactory.criarPaciente(tipoSanguineo, convenio, gerarIdPaciente(), nome, cpf, telefone, email, dataNascimento);
        PacienteDao.adicionarPaciente(paciente);
    }

    public void alterarPaciente(int id, TipoSanguineo tipoSanguineo, String convenio, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) throws Exception {
        Paciente paciente = PacienteFactory.criarPaciente(tipoSanguineo, convenio, id, nome, cpf, telefone, email, dataNascimento);
        PacienteDao.alterarPaciente(paciente);
    }

    public void deletarPaciente(int id) throws Exception {
        PacienteDao.deletarPaciente(id);
    }

    public List<Paciente> listarPacientes() throws Exception {
        return PacienteDao.listarPacientes();
    }

    public Paciente buscarPacientePorId(int id) throws Exception{
        return PacienteDao.buscarPacientePorId(id);
    }

    private int gerarIdPaciente() throws Exception {
        List<Paciente> pacientes = PacienteDao.listarPacientes();
        return pacientes.stream()
                .mapToInt(Paciente::getId)
                .max()
                .orElse(0) + 1;
    }

    public void cadastrarMedico(String crm, Especialidade especialidade, double salarioBase, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) throws Exception {
        Medico medico = MedicoFactory.criarMedico(crm, especialidade, salarioBase, gerarIdMedico(), nome, cpf, telefone, email, dataNascimento);
        MedicoDao.adicionarMedico(medico);
    }

    public void alterarMedico(int id, String crm, Especialidade especialidade, double salarioBase, String nome, String cpf, String telefone, String email, LocalDate dataNascimento) throws Exception {
        Medico medico = MedicoFactory.criarMedico(crm, especialidade, salarioBase, id, nome, cpf, telefone, email, dataNascimento);
        MedicoDao.alterarMedico(medico);
    }

    public void deletarMedico(int id) throws Exception {
        MedicoDao.deletarMedico(id);
    }

    public List<Medico> listarMedicos() throws Exception {
        return MedicoDao.listarMedicos();
    }

    public Medico buscarMedicoPorId(int id) throws Exception {
        return MedicoDao.buscarMedicoPorId(id);
    }

    private int gerarIdMedico() throws Exception {
        List<Medico> medicos = MedicoDao.listarMedicos();
        return medicos.stream()
                .mapToInt(Medico::getId)
                .max()
                .orElse(0) + 1;
    }

    public void agendarConsulta(int idPaciente, int idMedico, LocalDateTime dataHora, double valor) throws Exception {
        Paciente paciente = PacienteDao.buscarPacientePorId(idPaciente);
        Medico medico = MedicoDao.buscarMedicoPorId(idMedico);
        Consulta consulta = ConsultaFactory.criarConsulta(gerarIdConsulta(), paciente, medico, dataHora, valor);
        ConsultaDao.adicionarConsulta(consulta);
    }

    public void alterarConsulta(int id, int idPaciente, int idMedico, LocalDateTime dataHora, StatusConsulta status, double valor) throws Exception {
        Paciente paciente = PacienteDao.buscarPacientePorId(idPaciente);
        Medico medico = MedicoDao.buscarMedicoPorId(idMedico);
        Consulta consulta = ConsultaFactory.criarConsulta(id, paciente, medico, dataHora, status, valor);
        ConsultaDao.alterarConsulta(consulta);
    }

    public void cancelarConsulta(int id) throws Exception {
        Consulta consulta = ConsultaDao.buscarConsultaPorId(id);
        consulta.cancelar();
        ConsultaDao.alterarConsulta(consulta);
    }

    public void remarcarConsulta(int id, LocalDateTime novaDataHora) throws Exception {
        Consulta consulta = ConsultaDao.buscarConsultaPorId(id);
        consulta.remarcar(novaDataHora);
        ConsultaDao.alterarConsulta(consulta);
    }

    public void deletarConsulta(int id) throws Exception {
        ConsultaDao.deletarConsulta(id);
    }

    public List<Consulta> listarConsultas() throws Exception {
        return ConsultaDao.listarConsultas();
    }

    public Consulta buscarConsultaPorId(int id) throws Exception {
        return ConsultaDao.buscarConsultaPorId(id);
    }

    public List<Consulta> listarConsultasPorPaciente(int idPaciente) throws Exception {
        return ConsultaDao.buscarPorPaciente(idPaciente);
    }

    public List<Consulta> listarConsultasPorMedico(int idMedico) throws Exception {
        return ConsultaDao.buscarPorMedico(idMedico);
    }

    public List<Consulta> listarConsultasPorStatus(StatusConsulta status) throws Exception {
        return ConsultaDao.buscarPorStatus(status);
    }

    private int gerarIdConsulta() throws Exception {
        List<Consulta> consultas = ConsultaDao.listarConsultas();
        return consultas.stream()
                .mapToInt(Consulta::getId)
                .max()
                .orElse(0) + 1;
    }
}