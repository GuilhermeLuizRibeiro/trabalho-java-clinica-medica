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

    public static void adicionarConsulta(Consulta consulta) throws Exception {
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
        } catch (ErroAoLerArquivoException e) {
            throw new ErroAoLerArquivoException("Erro ao carregar lista de consultas");
        } catch (ArquivoNaoEncontradoException e) {
            throw new ArquivoNaoEncontradoException("Arquivo não encontrado");
        }
    }

    public static void alterarConsulta(Consulta consulta) throws Exception {
        try {
            List<Consulta> consultas = listarConsultas();
            consultas.replaceAll(c -> c.getId() == consulta.getId() ? consulta : c);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO));
            oos.writeObject(consultas);
            oos.close();
        } catch (Exception e) {
            throw new Exception("Erro ao alterar consulta");
        }
    }

    public static void deletarConsulta(int id) throws Exception {
        try {
            List<Consulta> consultas = listarConsultas();
            consultas.removeIf(c -> c.getId() == id);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO));
            oos.writeObject(consultas);
            oos.close();
        } catch (Exception e) {
            throw new ErroAoSalvarException("Erro ao deletar consulta");
        }
    }

    public static Consulta buscarConsultaPorId(int id) throws Exception {
        try {
            List<Consulta> consultas = listarConsultas();
            return consultas.stream()
                    .filter(c -> c.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada"));
        } catch (ConsultaNaoEncontradaException e) {
            throw new ConsultaNaoEncontradaException("Consulta não encontrada");
        } catch (IOException e) {
            throw new ErroAoLerArquivoException("Erro ao buscar consulta");
        }
    }

    public static List<Consulta> buscarPorPaciente(int idPaciente) throws Exception {
        try {
            return listarConsultas().stream()
                    .filter(c -> c.getPaciente().getId() == idPaciente)
                    .toList();
        } catch (Exception e) {
            throw new ErroAoLerArquivoException("Erro ao buscar consultas do paciente");
        }
    }

    public static List<Consulta> buscarPorMedico(int idMedico) throws Exception {
        try {
            return listarConsultas().stream()
                    .filter(c -> c.getMedico().getId() == idMedico)
                    .toList();
        } catch (Exception e) {
            throw new ErroAoLerArquivoException("Erro ao buscar consultas do médico");
        }
    }

    public static List<Consulta> buscarPorStatus(StatusConsulta status) throws Exception {
        try {
            return listarConsultas().stream()
                    .filter(c -> c.getStatus() == status)
                    .toList();
        } catch (Exception e) {
            throw new ErroAoLerArquivoException("Erro ao buscar consultas por status");
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Consulta> listarConsultas() throws Exception {
        try {
            if (!CAMINHO.exists() || !CAMINHO.isFile()) {
                throw new ArquivoNaoEncontradoException("Arquivo não encontrado");
            }
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO));
            return (List<Consulta>) ois.readObject();
        } catch (IOException e) {
            throw new ErroAoLerArquivoException("Erro ao listar consultas");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Erro ao deserializar consultas");
        }
    }
}