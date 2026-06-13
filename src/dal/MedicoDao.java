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
    private static final File CAMINHO = new File("projeto_clinica_medica/src/obj/medicos.ser");

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
            throw new ErroAoSalvarException("Erro ao salvar medico");
        } catch (ErroAoLerArquivoException e) {
            throw new ErroAoLerArquivoException("Erro ao carregar lista de medicos");
        } catch (ArquivoNaoEncontradoException e) {
            throw new ArquivoNaoEncontradoException("Arquivo não encontrado");
        } catch (ClassNotFoundException e) {
            throw new ErroAoLerArquivoException("Erro ao deserializar médicos");
        }
    }

    public static void alterarMedico(Medico medico) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException {
        try {
            List<Medico> medicos = listarMedicos();
            medicos.replaceAll(m -> m.getId() == medico.getId() ? medico : m);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO));
            oos.writeObject(medicos);
            oos.close();

        } catch (ClassNotFoundException e) {
            throw new ErroAoLerArquivoException("Erro ao deserializar médicos");
        } catch (ArquivoNaoEncontradoException e) {
            throw new ArquivoNaoEncontradoException("Arquivo não encontrado");
        } catch (ErroAoLerArquivoException e) {
            throw new ErroAoLerArquivoException("Erro ao carregar lista de médicos");
        } catch (IOException e) {
            throw new ErroAoSalvarException("Erro ao salvar alteração do médico");
        }
    }

    public static void deletarMedico(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException {
        try {
            List<Medico> medicos = listarMedicos();
            medicos.removeIf(m -> m.getId() == id);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO));
            oos.writeObject(medicos);
            oos.close();
        } catch (ClassNotFoundException e) {
            throw new ErroAoLerArquivoException("Erro ao deserializar médicos");
        } catch (ArquivoNaoEncontradoException e) {
            throw new ArquivoNaoEncontradoException("Arquivo não encontrado");
        } catch (ErroAoLerArquivoException e) {
            throw new ErroAoLerArquivoException("Erro ao carregar lista de médicos");
        } catch (IOException e) {
            throw new ErroAoSalvarException("Erro ao deletar médico");
        }
    }

    public static Medico buscarMedicoPorId(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, MedicoNaoEncontradoException {
        try {
            List<Medico> medicos = listarMedicos();
            return medicos.stream()
                    .filter(m -> m.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new MedicoNaoEncontradoException("Medico não encontrado"));
        } catch (ClassNotFoundException e) {
            throw new ErroAoLerArquivoException("Erro ao deserializar médicos");
        } catch (ArquivoNaoEncontradoException e) {
            throw new ArquivoNaoEncontradoException("Arquivo não encontrado");
        } catch (ErroAoLerArquivoException e) {
            throw new ErroAoLerArquivoException("Erro ao carregar lista de médicos");
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Medico> listarMedicos() throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ClassNotFoundException {
        try {
            if (!CAMINHO.exists() || !CAMINHO.isFile()) {
                throw new ArquivoNaoEncontradoException("Arquivo não encontrado");
            }
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO));
            return (List<Medico>) ois.readObject();
        } catch (IOException e) {
            throw new ErroAoLerArquivoException("Erro ao listar médicos");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Erro ao deserializar médicos");
        }
    }
}