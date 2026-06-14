package dal;

import exception.ArquivoNaoEncontradoException;
import exception.ErroAoLerArquivoException;
import exception.ErroAoSalvarException;
import exception.MedicoNaoEncontradoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Medico;

public abstract class MedicoDao {
    private static final File CAMINHO = new File("src/obj/medicos.ser");

    public static void adicionarMedico(Medico medico) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException {
        try {
            CAMINHO.getParentFile().mkdirs();

            List<Medico> medicos = new ArrayList<>();

            if (CAMINHO.exists()) {
                medicos = listarMedicos();
            }

            medicos.add(medico);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO));
            oos.writeObject(medicos);
            oos.close();
        } catch (IOException e) {
            throw new ErroAoSalvarException("Erro ao salvar médico");
        }
    }

    public static void alterarMedico(Medico medico) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, MedicoNaoEncontradoException {
        try {
            List<Medico> medicos = listarMedicos();

            boolean existe = medicos.stream()
                .anyMatch(m -> m.getId() == medico.getId());
            if (!existe) {
                throw new MedicoNaoEncontradoException("Médico não encontrado");
            }

            medicos.replaceAll(m -> m.getId() == medico.getId() ? medico : m);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO));
            oos.writeObject(medicos);
            oos.close();
        } catch (IOException e) {
            throw new ErroAoSalvarException("Erro ao salvar alteração do médico");
        }
    }

    public static void deletarMedico(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, MedicoNaoEncontradoException {
        try {
            List<Medico> medicos = listarMedicos();

            boolean existe = medicos.stream()
                .anyMatch(m -> m.getId() == id);
            if (!existe) {
                throw new MedicoNaoEncontradoException("Médico não encontrado");
            }

            medicos.removeIf(m -> m.getId() == id);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO));
            oos.writeObject(medicos);
            oos.close();
        } catch (IOException e) {
            throw new ErroAoSalvarException("Erro ao deletar médico");
        }
    }

    public static Medico buscarMedicoPorId(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, MedicoNaoEncontradoException {
        List<Medico> medicos = listarMedicos();
        return medicos.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElseThrow(() -> new MedicoNaoEncontradoException("Médico não encontrado"));
    }

    @SuppressWarnings("unchecked")
    public static List<Medico> listarMedicos() throws ArquivoNaoEncontradoException, ErroAoLerArquivoException {
        try {
            if (!CAMINHO.exists() || !CAMINHO.isFile()) {
                throw new ArquivoNaoEncontradoException("Arquivo de médicos não encontrado");
            }

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO));
            return (List<Medico>) ois.readObject();
        } catch (IOException e) {
            throw new ErroAoLerArquivoException("Erro ao ler o arquivo de médicos");
        } catch (ClassNotFoundException e) {
            throw new ErroAoLerArquivoException("Erro ao deserializar médicos: classe não encontrada");
        }
    }

    public static boolean existePorCpf(String cpf) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException {
        try {
            return listarMedicos().stream()
                .anyMatch(m -> m.getCpf().equals(cpf));
        } catch (ArquivoNaoEncontradoException e) {
            return false;
        }
    }

    public static boolean existePorCrm(String crm) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException {
        try {
            return listarMedicos().stream()
                .anyMatch(m -> m.getCrm().equalsIgnoreCase(crm));
        } catch (ArquivoNaoEncontradoException e) {
            return false;
        }
    }
}