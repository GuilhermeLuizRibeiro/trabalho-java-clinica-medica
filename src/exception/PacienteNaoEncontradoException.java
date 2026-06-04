package exception;

public class PacienteNaoEncontradoException extends Exception {
    private static String codigo = "06";

    public PacienteNaoEncontradoException(String mensagem) {
        super(codigo + ": " + mensagem);
    }
}