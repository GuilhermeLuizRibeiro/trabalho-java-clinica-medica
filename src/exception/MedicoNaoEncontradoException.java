package exception;

public class MedicoNaoEncontradoException extends Exception {
    private static String codigo = "07";

    public MedicoNaoEncontradoException(String mensagem) {
        super(codigo + ": " + mensagem);
    }
}
