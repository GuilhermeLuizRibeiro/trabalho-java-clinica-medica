package exception;

public class ConsultaJaCanceladaException extends Exception {
    private static String codigo = "09";

    public ConsultaJaCanceladaException(String mensagem) {
        super(codigo + ": " + mensagem);
    }
}