package dal;

import exception.ArquivoNaoEncontradoException;
import exception.ConsultaNaoEncontradaException;
import exception.ErroAoLerArquivoException;
import exception.ErroAoSalvarException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Consulta;
import model.StatusConsulta;

public abstract class ConsultaDao {
    private static final File CAMINHO = new File("src/obj/consultas.ser");

    public static void adicionarConsulta(Consulta consulta) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException {
        try {
            CAMINHO.getParentFile().mkdirs();

            List<Consulta> consultas = new ArrayList<>();

            if (CAMINHO.exists()) {
                consultas = listarConsultas();
            }

            consultas.add(consulta);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO));
            oos.writeObject(consultas);
            oos.close();
        } catch (IOException e) {
            throw new ErroAoSalvarException("Erro ao salvar consulta");
        }
    }

    public static void alterarConsulta(Consulta consulta) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ErroAoSalvarException, ConsultaNaoEncontradaException {
        try {
            List<Consulta> consultas = listarConsultas();

            boolean existe = consultas.stream()
                .anyMatch(c -> c.getId() == consulta.getId());
            if (!existe) {
                throw new ConsultaNaoEncontradaException("Consulta não encontrada");
            }

            consultas.replaceAll(c -> c.getId() == consulta.getId() ? consulta : c);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO));
            oos.writeObject(consultas);
            oos.close();
        } catch (IOException e) {
            throw new ErroAoSalvarException("Erro ao salvar alteração da consulta");
        }
    }

    public static void deletarConsulta(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ConsultaNaoEncontradaException, ErroAoSalvarException {
        try {
            List<Consulta> consultas = listarConsultas();

            boolean existe = consultas.stream()
                .anyMatch(c -> c.getId() == id);
            if (!existe) {
                throw new ConsultaNaoEncontradaException("Consulta não encontrada");
            }

            consultas.removeIf(c -> c.getId() == id);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO));
            oos.writeObject(consultas);
            oos.close();
        } catch (IOException e) {
            throw new ErroAoSalvarException("Erro ao deletar consulta");
        }
    }

    public static Consulta buscarConsultaPorId(int id) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException, ConsultaNaoEncontradaException {
        List<Consulta> consultas = listarConsultas();
        return consultas.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada"));
    }

    public static List<Consulta> buscarPorMedico(int idMedico) throws ArquivoNaoEncontradoException, ErroAoLerArquivoException {
        try {
            return listarConsultas().stream()
                .filter(c -> c.getMedico().getId() == idMedico)
                .toList();
        } catch (ArquivoNaoEncontradoException e) {
            return new ArrayList<>();
        }
        
    }

    public static List<Consulta> buscarPorStatus(StatusConsulta status)throws ArquivoNaoEncontradoException, ErroAoLerArquivoException {
        return listarConsultas().stream()
                .filter(c -> c.getStatus() == status)
                .toList();
    }

    @SuppressWarnings("unchecked")
    public static List<Consulta> listarConsultas() throws ArquivoNaoEncontradoException, ErroAoLerArquivoException {
        try {
            if (!CAMINHO.exists() || !CAMINHO.isFile()) {
                throw new ArquivoNaoEncontradoException("Arquivo de consultas não encontrado");
            }

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO));
            return (List<Consulta>) ois.readObject();
        } catch (IOException e) {
            throw new ErroAoLerArquivoException("Erro ao ler o arquivo de consultas");
        } catch (ClassNotFoundException e) {
            throw new ErroAoLerArquivoException("Erro ao deserializar consultas: classe não encontrada");
        }
    }
}