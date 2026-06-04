package exception;

public class ConsultaNaoEncontradaException extends Exception {
    private static String codigo = "08";

    public ConsultaNaoEncontradaException(String mensagem) {
        super(codigo + ": " + mensagem);
    }
}