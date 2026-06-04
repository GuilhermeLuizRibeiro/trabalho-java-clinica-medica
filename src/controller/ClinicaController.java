package controller;

import dal.PacienteDao;
import java.util.List;
import model.Paciente;

public class ClinicaController {
    public void cadastrarPaciente(Paciente paciente) throws Exception {
        PacienteDao.adicionarPaciente(paciente);
    }

    public void alterarPaciente(Paciente paciente) throws Exception {
        PacienteDao.alterarPaciente(paciente);
    }

    public void deletarPaciente(int id) throws Exception {
        PacienteDao.deletarPaciente(id);
    }

    public List<Paciente> listarPacientes() throws Exception {
        return PacienteDao.listarPacientes();
    }
}