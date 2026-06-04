package exception;

public class ErroAoSalvarException extends Exception{
    private static String codigo = "11";

    public ErroAoSalvarException(String mensagem) {
        super(codigo + ": " + mensagem);
    }
}