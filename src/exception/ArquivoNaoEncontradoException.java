package exception;

public class ArquivoNaoEncontradoException extends Exception {
    private static String codigo = "13";

    public ArquivoNaoEncontradoException(String mensagem) {
        super(codigo + ": " + mensagem);
    }
}