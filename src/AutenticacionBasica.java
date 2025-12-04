import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AutenticacionBasica implements Autenticable, Auditable{
    private String usuario;
    private String password;
    private boolean sesionActiva;
    private int intentosFallidos;
    private List<String> historial = new ArrayList<>();

    public AutenticacionBasica(String usuario, String password){
        this.usuario = usuario;
        this.password = password;
    }

    @Override
    public boolean autenticar(String usuario, String credencial){
        if (intentosFallidos >=3){
            System.out.println("Cuenta Bloqueada-Contacte con el administrador");
            return false;
        }
        boolean exitoso = this.usuario.equals(usuario) && this.password.equals(credencial);
        registrarIntento(usuario, exitoso);

        if (exitoso){
            sesionActiva = true;
            intentosFallidos = 0;
            System.out.println("Autenticacion exitosa");
        } else {
            sesionActiva = false;
            intentosFallidos++;
            System.out.println("Autenticacion fallida");
        }
        return exitoso;
    }

    @Override
    public void cerrarSesion() {
        sesionActiva = false;
    }

    @Override
    public boolean sesionActiva() {
        return sesionActiva;
    }


    @Override
    public void registrarIntento(String usuario, boolean exitoso) {
        String estado = exitoso ? "Login exitoso" : "Login fallido";
        String registro = "[" + new Date() + "] " + estado;
        historial.add(registro);

        System.out.println("[AUDIT] Intento " + (exitoso ? "exitoso" : "fallido") +
                ": " + usuario + " - " + new Date());
    }

    @Override
    public List<String> obtenerHistorial(String usuario) {
        return historial;
    }

    public int getIntentosRestantes() {
        return 3 - intentosFallidos;
    }
}
