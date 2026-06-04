package exception;

public class DataInvalidaException extends Exception {
    private static String codigo = "04";

    public DataInvalidaException(String mensagem) {
        super(codigo + ": " + mensagem);
    }
}