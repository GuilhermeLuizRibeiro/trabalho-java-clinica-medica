package dal;

import exception.ArquivoNaoEncontradoException;
import exception.ErroAoLerArquivoException;
import exception.ErroAoSalvarException;
import exception.PacienteNaoEncontradoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Paciente;

public abstract class PacienteDao {
    private static final File CAMINHO = new File("src/obj/pacientes.ser");

    public static void adicionarPaciente(Paciente paciente) throws Exception {
        try {
            CAMINHO.getParentFile().mkdirs();
            List<Paciente> pacientes = new ArrayList<>();
            if (CAMINHO.exists()) {
                pacientes = listarPacientes();
            }
            pacientes.add(paciente);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO));
            oos.writeObject(pacientes);
            oos.close();
        } catch (IOException e) {
            throw new ErroAoSalvarException("Erro ao salvar paciente");
        } catch (ErroAoLerArquivoException e) {
            throw new ErroAoLerArquivoException("Erro ao carregar lista de pacientes");
        } catch (ArquivoNaoEncontradoException e) {
            throw new ArquivoNaoEncontradoException("Arquivo não encontrado");
        }
    }

    public static void alterarPaciente(Paciente paciente) throws Exception {
        try {
            List<Paciente> pacientes = listarPacientes();
            pacientes.replaceAll(p -> p.getId() == paciente.getId() ? paciente : p);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO));
            oos.writeObject(pacientes);
            oos.close();

        } catch (Exception e) {
            throw new Exception("Erro ao alterar paciente");
        }
    }

    public static void deletarPaciente(int id) throws Exception {
        try {
            List<Paciente> pacientes = listarPacientes();
            pacientes.removeIf(p -> p.getId() == id);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO));
            oos.writeObject(pacientes);
            oos.close();
        } catch (Exception e) {
            throw new ErroAoSalvarException("Erro ao deletar paciente");
        }
    }

    public static Paciente buscarPacientePorId(int id) throws Exception {
        try {
            List<Paciente> pacientes = listarPacientes();
            return pacientes.stream()
                    .filter(p -> p.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));
        } catch (PacienteNaoEncontradoException e) {
            throw new PacienteNaoEncontradoException("Paciente não encontrado");
        } catch (IOException e) {
            throw new ErroAoLerArquivoException("Erro ao buscar paciente");
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Paciente> listarPacientes() throws Exception {
        try {
            if (!CAMINHO.exists() || !CAMINHO.isFile()) {
                throw new ArquivoNaoEncontradoException("Arquivo não encontrado");
            }
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO));
            return (List<Paciente>) ois.readObject();
        } catch (IOException e) {
            throw new ErroAoLerArquivoException("Erro ao listar pacientes");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Erro ao deserializar pacientes");
        }
    }
}