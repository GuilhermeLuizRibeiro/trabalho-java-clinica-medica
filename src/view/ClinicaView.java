package view;

import controller.ClinicaController;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import model.Consulta;
import model.Especialidade;
import model.Medico;
import model.Paciente;
import model.StatusConsulta;
import model.TipoSanguineo;

public class ClinicaView {
    private ClinicaController controller;
    private static final DateTimeFormatter FORMATTER_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATTER_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private Scanner entrada;

    public ClinicaView(ClinicaController controller) {
        this.controller = controller;
        this.entrada = new Scanner(System.in);
    }

    public void iniciar() {
        boolean menu = true;
        int opcao;

        while (menu) { 
            System.out.println("================================================");
            System.out.println("== Sistema de Gerenciamento de Clínica Médica ==");
            System.out.println("================================================");
            System.out.println("[1] Paciente");
            System.out.println("[2] Médico");
            System.out.println("[3] Consulta");
            System.out.println("[0] Sair");
            System.out.println();

            opcao = lerInt("Opção");
             switch (opcao) {
                case 1:
                    menuPaciente();
                    break;
                case 2:
                    menuMedico();
                    break;
                case 3:
                    menuConsulta();
                    break;
                case 0:
                    menu = false;
                    System.out.println("== Encerrando o Sistema...");
                    break;
                default:
                    System.out.println("== Opção Inválida ==");
                    break;
             }
        }
    }

    private void menuPaciente() {
        boolean menu = true;
        int opcao;

        while (menu) { 
            System.out.println("===============");
            System.out.println("== Pacientes ==");
            System.out.println("===============");
            System.out.println("[1] Cadastrar Paciente");
            System.out.println("[2] Alterar Paciente");
            System.out.println("[3] Deletar Paciente");
            System.out.println("[4] Listar Pacientes");
            System.out.println("[5] Buscar Paciente por Id");
            System.out.println("[0] Voltar");
            System.out.println();

            opcao = lerInt("Opção");
             switch (opcao) {
                case 1:
                    cadastrarPaciente();
                    break;
                case 2:
                    alterarPaciente();
                    break;
                case 3:
                    deletarPaciente();
                    break;
                case 4:
                    listarPacientes();
                    break;
                case 5:
                    buscarPacientePorId();
                    break;
                case 0:
                    menu = false;
                    break;
                default:
                    System.out.println("== Opção Inválida ==");
                    break;
             }
        }
    }

    private void menuMedico() {
        boolean menu = true;
        int opcao;

        while (menu) { 
            System.out.println("=============");
            System.out.println("== Médicos ==");
            System.out.println("=============");
            System.out.println("[1] Cadastrar Médico");
            System.out.println("[2] Alterar Médico");
            System.out.println("[3] Deletar Médico");
            System.out.println("[4] Listar Médicos");
            System.out.println("[5] Buscar Médico por Id");
            System.out.println("[0] Voltar");
            System.out.println();

            opcao = lerInt("Opção");
             switch (opcao) {
                case 1:
                    cadastrarMedico();
                    break;
                case 2:
                    alterarMedico();
                    break;
                case 3:
                    deletarMedico();
                    break;
                case 4:
                    listarMedicos();
                    break;
                case 5:
                    buscarMedicoPorId();
                    break;
                case 0:
                    menu = false;
                    break;
                default:
                    System.out.println("== Opção Inválida ==");
                    break;
             }
        }
    }

    private void menuConsulta() {
        boolean menu = true;
        int opcao;

        while (menu) { 
            System.out.println("===============");
            System.out.println("== Consultas ==");
            System.out.println("===============");
            System.out.println("[1] Agendar Consulta");
            System.out.println("[2] Deletar Consulta");
            System.out.println("[3] Cancelar Consulta");
            System.out.println("[4] Remarcar Consulta");
            System.out.println("[5] Listar Consultas");
            System.out.println("[6] Listar Consultas por Médico");
            System.out.println("[7] Listar Consultas por Status");
            System.out.println("[0] Voltar");
            System.out.println();

            opcao = lerInt("Opção");
             switch (opcao) {
                case 1:
                    agendarConsulta();
                    break;
                case 2:
                    deletarConsulta();
                    break;
                case 3:
                    cancelarConsulta();
                    break;
                case 4:
                    remarcarConsulta();
                    break;
                case 5:
                    listarConsultas();
                    break;
                case 6:
                    listarConsultasPorMedico();
                    break;
                case 7:
                    listarConsultasPorStatus();
                    break;
                case 0:
                    menu = false;
                    break;
                default:
                    System.out.println("== Opção Inválida ==");
                    break;
             }
        }
    }

    private void cadastrarPaciente() {
        System.out.println("========================");
        System.out.println("== Cadastrar Paciente ==");
        System.out.println("========================");

        try {
            String nome = lerString("Nome");
            String cpf = lerString("CPF (somente números)");
            String telefone = lerString("Telefone");
            String email = lerString("E-mail");
            LocalDate dataNasc = lerData("Data de Nascimento (dd/MM/yyyy)");
            String convenio = lerString("Convênio (deixe em branco se não tiver)");
            TipoSanguineo tipo = selecionarTipoSanguineo();

            controller.cadastrarPaciente(tipo, convenio, nome, cpf, telefone, email, dataNasc);
            System.out.println("Paciente cadastrado com sucesso");
            
        } catch (DadosObrigatoriosException e) {
            System.out.println("== Campo obrigatório não preenchido: " + e.getMessage() + " ==");

        } catch (CpfInvalidoException e) {
            System.out.println("== CPF inválido: " + e.getMessage() + " ==");

        } catch (DataInvalidaException e) {
            System.out.println("== Data inválida: " + e.getMessage() + " ==");

        } catch (ArquivoNaoEncontradoException | ErroAoLerArquivoException | ErroAoSalvarException | ClassNotFoundException e) {
            System.err.println("Erro ao cadastrar paciente: " + e.getMessage());
        }
    }

    private void alterarPaciente() {
        System.out.println("======================");
        System.out.println("== Alterar Paciente ==");
        System.out.println("======================");

        try {
            int id = lerInt("Id do Paciente");
            String nome = lerString("Nome");
            String cpf = lerString("CPF (somente números)");
            String telefone = lerString("Telefone");
            String email = lerString("E-mail");
            LocalDate dataNasc = lerData("Data de Nascimento (dd/MM/yyyy)");
            String convenio = lerString("Convênio (deixe em branco se não tiver)");
            TipoSanguineo tipo = selecionarTipoSanguineo();

            controller.alterarPaciente(id, tipo, convenio, nome, cpf, telefone, email, dataNasc);
            System.out.println("Paciente alterado com sucesso");

        } catch (DadosObrigatoriosException e) {
            System.out.println("== Campo obrigatório não preenchido: " + e.getMessage() + " ==");

        } catch (CpfInvalidoException e) {
            System.out.println("== CPF inválido: " + e.getMessage() + " ==");

        } catch (DataInvalidaException e) {
            System.out.println("== Data inválida: " + e.getMessage() + " ==");

        } catch (ArquivoNaoEncontradoException | ErroAoLerArquivoException | ErroAoSalvarException | ClassNotFoundException e) {
            System.err.println("Erro ao alterar paciente: " + e.getMessage());
        }
    }

    private void deletarPaciente() {
        System.out.println("======================");
        System.out.println("== Deletar Paciente ==");
        System.out.println("======================");

        try {
            int id = lerInt("Id do Paciente");

            controller.deletarPaciente(id);
            System.out.println("Paciente deletado com sucesso");

        } catch (ArquivoNaoEncontradoException | ErroAoLerArquivoException | ErroAoSalvarException e) {
            System.err.println("Erro ao deletar paciente: " + e.getMessage());
        }
    }

    private void listarPacientes() {
        System.out.println("========================");
        System.out.println("== Lista de Pacientes ==");
        System.out.println("========================");

        try {
            List<Paciente> pacientes = controller.listarPacientes();

            if (pacientes.isEmpty()) {
                System.out.println("Nenhum paciente cadastrado.");
                return;
            }
            System.out.println("-------------------------------");
            for (Paciente p : pacientes) {
                System.out.println(p.exibirResumo());
            }
            System.out.println("-------------------------------");

        } catch (ArquivoNaoEncontradoException e) {
            System.err.println("Nenhum paciente cadastrado.");

        } catch (ErroAoLerArquivoException | ClassNotFoundException e) {
            System.err.println("Erro ao listar pacientes: " + e.getMessage());
        }
    }

    private void buscarPacientePorId() {
        System.out.println("=====================");
        System.out.println("== Buscar Paciente ==");
        System.out.println("=====================");
        
        try {
            int id = lerInt("Id do Paciente");
            Paciente p = controller.buscarPacientePorId(id);

            System.out.println(p.exibirResumo());

        } catch (PacienteNaoEncontradoException e) {
            System.out.println("Paciente não encontrado: " + e.getMessage() + " ==");

        } catch (ArquivoNaoEncontradoException | ErroAoLerArquivoException e) {
            System.err.println("Erro ao buscar paciente: " + e.getMessage());
        }
    }

    private void cadastrarMedico() {
        System.out.println("=======================");
        System.out.println("== Cadastrar Médico ==");
        System.out.println("=======================");
    
        try {
            String nome = lerString("Nome");
            String cpf = lerString("CPF (somente números)");
            String telefone = lerString("Telefone");
            String email = lerString("E-mail");
            LocalDate dataNasc = lerData("Data de Nascimento (dd/MM/yyyy)");
            String crm = lerString("CRM (ex: CRM/SP 123456)");
            double salario = lerDouble("Salário Base");
            Especialidade especialidade = selecionarEspecialidade();
    
            controller.cadastrarMedico(crm, especialidade, salario, nome, cpf, telefone, email, dataNasc);
            System.out.println("Médico cadastrado com sucesso");
    
        } catch (DadosObrigatoriosException e) {
            System.out.println("== Campo obrigatório não preenchido: " + e.getMessage() + " ==");
    
        } catch (CpfInvalidoException e) {
            System.out.println("== CPF inválido: " + e.getMessage() + " ==");
    
        } catch (CrmInvalidoException e) {
            System.out.println("== CRM inválido: " + e.getMessage() + " ==");
    
        } catch (ValorInvalidoException e) {
            System.out.println("== Salário inválido: " + e.getMessage() + " ==");
    
        } catch (DataInvalidaException e) {
            System.out.println("== Data inválida: " + e.getMessage() + " ==");
    
        } catch (ArquivoNaoEncontradoException | ErroAoLerArquivoException | ErroAoSalvarException | ClassNotFoundException e) {
            System.err.println("Erro ao cadastrar médico: " + e.getMessage());
            System.out.println("== Erro ao cadastrar médico. Contate o administrador. ==");
        }
    }
    
    private void alterarMedico() {
        System.out.println("=====================");
        System.out.println("== Alterar Médico ==");
        System.out.println("=====================");
    
        try {
            int id = lerInt("Id do Médico");
            String nome = lerString("Nome");
            String cpf = lerString("CPF (somente números)");
            String telefone = lerString("Telefone");
            String email = lerString("E-mail");
            LocalDate dataNasc = lerData("Data de Nascimento (dd/MM/yyyy)");
            String crm = lerString("CRM (ex: CRM/SP 123456)");
            double salario = lerDouble("Salário Base");
            Especialidade especialidade = selecionarEspecialidade();
    
            controller.alterarMedico(id, crm, especialidade, salario, nome, cpf, telefone, email, dataNasc);
            System.out.println("Médico alterado com sucesso");
    
        } catch (DadosObrigatoriosException e) {
            System.out.println("== Campo obrigatório não preenchido: " + e.getMessage() + " ==");
    
        } catch (CpfInvalidoException e) {
            System.out.println("== CPF inválido: " + e.getMessage() + " ==");
    
        } catch (CrmInvalidoException e) {
            System.out.println("== CRM inválido: " + e.getMessage() + " ==");
    
        } catch (ValorInvalidoException e) {
            System.out.println("== Salário inválido: " + e.getMessage() + " ==");
    
        } catch (DataInvalidaException e) {
            System.out.println("== Data inválida: " + e.getMessage() + " ==");
    
        } catch (ArquivoNaoEncontradoException | ErroAoLerArquivoException | ErroAoSalvarException | ClassNotFoundException e) {
            System.err.println("Erro ao alterar médico: " + e.getMessage());
            System.out.println("== Erro ao alterar médico. Contate o administrador. ==");
        }
    }
    
    private void deletarMedico() {
        System.out.println("=====================");
        System.out.println("== Deletar Médico ==");
        System.out.println("=====================");
    
        try {
            int id = lerInt("Id do Médico");
    
            controller.deletarMedico(id);
            System.out.println("Médico deletado com sucesso");
    
        } catch (ArquivoNaoEncontradoException | ErroAoLerArquivoException | ErroAoSalvarException e) {
            System.err.println("Erro ao deletar médico: " + e.getMessage());
            System.out.println("== Erro ao deletar médico. Contate o administrador. ==");
        }
    }
    
    private void listarMedicos() {
        System.out.println("======================");
        System.out.println("== Lista de Médicos ==");
        System.out.println("======================");
    
        try {
            List<Medico> medicos = controller.listarMedicos();
    
            if (medicos.isEmpty()) {
                System.out.println("Nenhum médico cadastrado.");
                return;
            }
            System.out.println("-------------------------------");
            for (Medico m : medicos) {
                System.out.println(m.exibirResumo());
            }
            System.out.println("-------------------------------");
    
        } catch (ArquivoNaoEncontradoException e) {
            System.out.println("Nenhum médico cadastrado.");
    
        } catch (ErroAoLerArquivoException | ClassNotFoundException e) {
            System.err.println("Erro ao listar médicos: " + e.getMessage());
            System.out.println("== Erro ao carregar lista de médicos. Contate o administrador. ==");
        }
    }
    
    private void buscarMedicoPorId() {
        System.out.println("====================");
        System.out.println("== Buscar Médico ==");
        System.out.println("====================");
    
        try {
            int id = lerInt("Id do Médico");
            Medico m = controller.buscarMedicoPorId(id);
    
            System.out.println(m.exibirResumo());
    
        } catch (MedicoNaoEncontradoException e) {
            System.out.println("Médico não encontrado: " + e.getMessage());
    
        } catch (ArquivoNaoEncontradoException | ErroAoLerArquivoException e) {
            System.err.println("Erro ao buscar médico: " + e.getMessage());
            System.out.println("== Erro ao buscar médico. Contate o administrador. ==");
        }
    }

    private void agendarConsulta() {
        System.out.println("=======================");
        System.out.println("== Agendar Consulta ==");
        System.out.println("=======================");

        try {
            int idPaciente = lerInt("Id do Paciente");
            int idMedico = lerInt("Id do Médico");
            LocalDateTime dataHora = lerDataHora("Data e Hora (dd/MM/yyyy HH:mm)");
            double valor = lerDouble("Valor da Consulta");

            controller.agendarConsulta(idPaciente, idMedico, dataHora, valor);
            System.out.println("Consulta agendada com sucesso");

        } catch (PacienteNaoEncontradoException e) {
            System.out.println("== Paciente não encontrado: " + e.getMessage() + " ==");

        } catch (MedicoNaoEncontradoException e) {
            System.out.println("== Médico não encontrado: " + e.getMessage() + " ==");

        } catch (DadosObrigatoriosException e) {
            System.out.println("== Campo obrigatório não preenchido: " + e.getMessage() + " ==");

        } catch (DataInvalidaException e) {
            System.out.println("== Data inválida: " + e.getMessage() + " ==");

        } catch (ValorInvalidoException e) {
            System.out.println("== Valor inválido: " + e.getMessage() + " ==");

        } catch (ArquivoNaoEncontradoException | ErroAoLerArquivoException | ErroAoSalvarException | ClassNotFoundException e) {
            System.err.println("Erro ao agendar consulta: " + e.getMessage());
            System.out.println("== Erro ao agendar consulta. Contate o administrador. ==");
        }
    }

    private void deletarConsulta() {
        System.out.println("======================");
        System.out.println("== Deletar Consulta ==");
        System.out.println("======================");

        try {
            int id = lerInt("Id da Consulta");

            controller.deletarConsulta(id);
            System.out.println("Consulta deletada com sucesso");

        } catch (ConsultaNaoEncontradaException e) {
            System.out.println("== Consulta não encontrada: " + e.getMessage() + " ==");

        } catch (ArquivoNaoEncontradoException | ErroAoLerArquivoException | ErroAoSalvarException e) {
            System.err.println("Erro ao deletar consulta: " + e.getMessage());
            System.out.println("== Erro ao deletar consulta. Contate o administrador. ==");
        }
    }

    private void cancelarConsulta() {
        System.out.println("========================");
        System.out.println("== Cancelar Consulta ==");
        System.out.println("========================");

        try {
            int id = lerInt("Id da Consulta");

            controller.cancelarConsulta(id);
            System.out.println("Consulta cancelada com sucesso");

        } catch (ConsultaNaoEncontradaException e) {
            System.out.println("== Consulta não encontrada: " + e.getMessage() + " ==");

        } catch (ArquivoNaoEncontradoException | ErroAoLerArquivoException | ErroAoSalvarException e) {
            System.err.println("Erro ao cancelar consulta: " + e.getMessage());
            System.out.println("== Erro ao cancelar consulta. Contate o administrador. ==");
        }
    }

    private void remarcarConsulta() {
        System.out.println("=======================");
        System.out.println("== Remarcar Consulta ==");
        System.out.println("=======================");

        try {
            int id = lerInt("Id da Consulta");
            LocalDateTime novaDataHora = lerDataHora("Nova Data e Hora (dd/MM/yyyy HH:mm)");

            controller.remarcarConsulta(id, novaDataHora);
            System.out.println("Consulta remarcada com sucesso");

        } catch (ConsultaNaoEncontradaException e) {
            System.out.println("== Consulta não encontrada: " + e.getMessage() + " ==");

        } catch (DataInvalidaException e) {
            System.out.println(e.getMessage());

        } catch (ArquivoNaoEncontradoException | ErroAoLerArquivoException | ErroAoSalvarException e) {
            System.err.println("Erro ao remarcar consulta: " + e.getMessage());
            System.out.println("== Erro ao remarcar consulta. Contate o administrador. ==");
        }
    }

    private void listarConsultas() {
        System.out.println("========================");
        System.out.println("== Lista de Consultas ==");
        System.out.println("========================");

        try {
            List<Consulta> consultas = controller.listarConsultas();

            if (consultas.isEmpty()) {
                System.out.println("Nenhuma consulta cadastrada.");
                return;
            }
            System.out.println("-------------------------------");
            for (Consulta c : consultas) {
                System.out.println(c.exibirResumo());
                System.out.println("-------------------------------");
            }

        } catch (ArquivoNaoEncontradoException e) {
            System.out.println("Nenhuma consulta cadastrada.");

        } catch (ErroAoLerArquivoException | ClassNotFoundException e) {
            System.err.println("Erro ao listar consultas: " + e.getMessage());
            System.out.println("== Erro ao carregar lista de consultas. Contate o administrador. ==");
        }
    }

    private void listarConsultasPorMedico() {
        System.out.println("==============================");
        System.out.println("== Consultas por Médico ==");
        System.out.println("==============================");

        try {
            int idMedico = lerInt("Id do Médico");
            List<Consulta> consultas = controller.listarConsultas(idMedico);

            if (consultas.isEmpty()) {
                System.out.println("Nenhuma consulta encontrada para esse médico.");
                return;
            }
            System.out.println("-------------------------------");
            for (Consulta c : consultas) {
                System.out.println(c.exibirResumo());
                System.out.println("-------------------------------");
            }

        } catch (ArquivoNaoEncontradoException e) {
            System.out.println("Nenhuma consulta cadastrada.");

        } catch (ErroAoLerArquivoException | ClassNotFoundException e) {
            System.err.println("Erro ao listar consultas por médico: " + e.getMessage());
            System.out.println("== Erro ao carregar consultas. Contate o administrador. ==");
        }
    }

    private void listarConsultasPorStatus() {
        System.out.println("==============================");
        System.out.println("== Consultas por Status ==");
        System.out.println("==============================");

        try {
            StatusConsulta status = selecionarStatusConsulta();
            List<Consulta> consultas = controller.listarConsultas(status);

            if (consultas.isEmpty()) {
                System.out.println("Nenhuma consulta encontrada com esse status.");
                return;
            }
            System.out.println("-------------------------------");
            for (Consulta c : consultas) {
                System.out.println(c.exibirResumo());
                System.out.println("-------------------------------");
            }

        } catch (ArquivoNaoEncontradoException e) {
            System.out.println("Nenhuma consulta cadastrada.");

        } catch (ErroAoLerArquivoException | ClassNotFoundException e) {
            System.err.println("Erro ao listar consultas por status: " + e.getMessage());
            System.out.println("== Erro ao carregar consultas. Contate o administrador. ==");
        }
    }

    private int lerInt (String campo) {
        while (true) {
            System.out.print(campo + ": ");
            try {
                int valor = entrada.nextInt();
                entrada.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                entrada.nextLine();
                System.out.println("Entrada inválida. Digite um número inteiro");
            }
        }
    }

    private String lerString(String campo) {
        System.out.print(campo + ": ");
        return entrada.nextLine().trim();
    }
 
    private LocalDate lerData(String campo) {
        while (true) {
            System.out.print(campo + ": ");
            String texto = entrada.nextLine().trim();
            try {
                return LocalDate.parse(texto, FORMATTER_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use o formato dd/MM/yyyy (ex: 25/12/1990).");
            }
        }
    }

    private double lerDouble(String campo) {
        while (true) {
            System.out.print(campo + ": ");
            try {
                double valor = entrada.nextDouble();
                entrada.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                entrada.nextLine();
                System.out.println("Entrada inválida. Digite um número (ex: 150.00).");
            }
        }
    }

    private LocalDateTime lerDataHora(String campo) {
        while (true) {
            System.out.print(campo + ": ");
            String texto = entrada.nextLine().trim();
            try {
                return LocalDateTime.parse(texto, FORMATTER_DATA_HORA);
            } catch (DateTimeParseException e) {
                System.out.println("Data/hora inválida. Use o formato dd/MM/yyyy HH:mm (ex: 25/12/2025 14:30).");
            }
        }
    }

    private TipoSanguineo selecionarTipoSanguineo() {
        TipoSanguineo[] tipos = TipoSanguineo.values();
        System.out.println("  Tipo Sanguíneo:");
        for (int i = 0; i < tipos.length; i++) {
            System.out.println("  [" + (i + 1) + "] " + tipos[i].getTipo());
        }
        while (true) {
            int opcao = lerInt("Opção");
            if (opcao >= 1 && opcao <= tipos.length) {
                return tipos[opcao - 1];
            }
            System.out.println("Opção inválida. Tente novamente.");
        }
    }    

    private Especialidade selecionarEspecialidade() {
        Especialidade[] especialidades = Especialidade.values();
        System.out.println("Especialidade:");
        for (int i = 0; i < especialidades.length; i++) {
            System.out.println("  [" + (i + 1) + "] " + especialidades[i].getEspecialidade());
        }
        while (true) {
            int opcao = lerInt("Opção");
            if (opcao >= 1 && opcao <= especialidades.length) {
                return especialidades[opcao - 1];
            }
            System.out.println("  Opção inválida. Tente novamente.");
        }
    }

    private StatusConsulta selecionarStatusConsulta() {
        StatusConsulta[] statuses = StatusConsulta.values();
        System.out.println("Status:");
        for (int i = 0; i < statuses.length; i++) {
            System.out.println("  [" + (i + 1) + "] " + statuses[i].getStatus());
        }
        while (true) {
            int opcao = lerInt("Opção");
            if (opcao >= 1 && opcao <= statuses.length) {
                return statuses[opcao - 1];
            }
            System.out.println("Opção inválida. Tente novamente.");
        }
    }
}