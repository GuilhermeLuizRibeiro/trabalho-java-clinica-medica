package exception;

public class ValorInvalidoException extends Exception {
    private static String codigo = "05";

    public ValorInvalidoException(String mensagem) {
        super(codigo + ": " + mensagem);
    }
}