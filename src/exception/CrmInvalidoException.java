package exception;

public class CrmInvalidoException extends Exception {
    private static String codigo = "02";

    public CrmInvalidoException(String mensagem) {
        super(codigo + ": " + mensagem);
    }
}