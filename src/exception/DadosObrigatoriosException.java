package exception;

public class DadosObrigatoriosException extends Exception {
    private static String codigo = "03";

    public DadosObrigatoriosException(String mensagem) {
        super(codigo + ": " + mensagem);
    }
}
