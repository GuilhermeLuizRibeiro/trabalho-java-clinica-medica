package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Log {
    public static String CAMINHO = "projeto_clinica_medica/src/log/";
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void registrarErros() throws IOException {
        try {
            File pasta = new File(CAMINHO);

            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            File arquivo = new File(CAMINHO + "log.txt");

            PrintStream logStream = new PrintStream(new FileOutputStream(arquivo, true)) {
                @Override
                public void println(String mensagem) {
                    String dataHora = LocalDateTime.now().format(FORMATO);
                    super.println("[ERRO] " + dataHora + " | " + mensagem);
                }
            };

            System.setErr(logStream);

        } catch (IOException e) {
            System.err.println("Falha ao redirecionar log de erros: " + e.getMessage());
        }
    }
}