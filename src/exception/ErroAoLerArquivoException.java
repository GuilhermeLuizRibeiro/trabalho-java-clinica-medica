package exception;

public class ErroAoLerArquivoException extends Exception {
    private static String codigo = "12";

    public ErroAoLerArquivoException(String mensagem) {
        super(codigo + ": " + mensagem);
    }
}