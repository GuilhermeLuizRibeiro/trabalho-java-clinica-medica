package exception;

public class ConsultaNaoFinalizavelException extends Exception {
    public ConsultaNaoFinalizavelException(String mensagem) {
        super(mensagem);
    }
}