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

    public static void adicionarPaciente(Paciente paciente) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException {
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
        }      
    }

    public static void alterarPaciente(Paciente paciente) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, PacienteNaoEncontradoException {
        try {
            List<Paciente> pacientes = listarPacientes();

            boolean existe = pacientes.stream()
                .anyMatch(p -> p.getId() == paciente.getId());
            if (!existe) {
                throw new PacienteNaoEncontradoException("Paciente não encontrado");
            }

            pacientes.replaceAll(p -> p.getId() == paciente.getId() ? paciente : p);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO));
            oos.writeObject(pacientes);
            oos.close();
        } catch (IOException e) {
            throw new ErroAoSalvarException("Erro ao salvar alteração do paciente");
        }
    }

    public static void deletarPaciente(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, PacienteNaoEncontradoException {
        try {
            List<Paciente> pacientes = listarPacientes();

            boolean existe = pacientes.stream()
                .anyMatch(p -> p.getId() == id);
            if (!existe) {
                throw new PacienteNaoEncontradoException("Paciente não encontrado");
            }

            pacientes.removeIf(p -> p.getId() == id);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO));
            oos.writeObject(pacientes);
            oos.close();
        } catch (IOException e) {
            throw new ErroAoSalvarException("Erro ao deletar paciente");
        }
    }

    public static Paciente buscarPacientePorId(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, PacienteNaoEncontradoException {
            List<Paciente> pacientes = listarPacientes();
            return pacientes.stream()
                    .filter(p -> p.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));
    }

    @SuppressWarnings("unchecked")
    public static List<Paciente> listarPacientes() throws ArquivoNaoEncontradoException, ErroAoLerArquivoException {
        try {
            if (!CAMINHO.exists() || !CAMINHO.isFile()) {
                throw new ArquivoNaoEncontradoException("Arquivo de pacientes não encontrado");
            }

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO));
            return (List<Paciente>) ois.readObject();
        } catch (IOException e) {
            throw new ErroAoLerArquivoException("Erro ao ler o arquivo de pacientes");
        } catch (ClassNotFoundException e) {
            throw new ErroAoLerArquivoException("Erro ao deserializar pacientes: classe não encontrada");
        }
    }
}