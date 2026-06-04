package exception;

public class CpfInvalidoException extends Exception {
    private static String codigo = "01";

    public CpfInvalidoException(String mensagem) {
        super(codigo + ": " + mensagem);
    }
}