import controller.ClinicaController;
import util.Log;
import view.ClinicaView;

public class App {
    public static void main(String[] args) throws Exception {
        Log.registrarErros();

        ClinicaController controller = new ClinicaController();
        ClinicaView view = new ClinicaView(controller);

        view.iniciar();
    }
}
