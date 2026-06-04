package exception;

public class MedicoIndisponivelException extends Exception {
    private static String codigo = "10";

    public MedicoIndisponivelException(String mensagem) {
        super(codigo + ": " + mensagem);
    }
}