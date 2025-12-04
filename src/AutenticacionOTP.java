import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

class AutenticacionOTP implements Autenticable, MultiFactor, Auditable {

    private String usuario;
    private String password;
    private String codigoOTP;
    private int intentosOTP = 3;
    private boolean sesionActiva;
    private List<String> historial = new ArrayList<>();

    public AutenticacionOTP(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    @Override
    public boolean autenticar(String usuario, String credencial) {
        boolean correcto = (this.usuario.equals(usuario) && this.password.equals(credencial));
        if (!correcto) {
            registrarIntento(usuario, false);
            System.out.println("Usuario o password incorrecto");
        }
        return correcto;
    }

    @Override
    public String generarCodigoVerificacion() {
        codigoOTP = String.format("%06d", new Random().nextInt(999999));
        intentosOTP = 3;
        return codigoOTP;
    }

    @Override
    public boolean verificarCodigo(String codigo) {
        if (intentosOTP <= 0) {
            System.out.println("OTP bloqueado");
            return false;
        }

        boolean correcto = codigo.equals(codigoOTP);
        registrarIntento(usuario + " (OTP)", correcto);

        if (correcto) {
            System.out.println("Codigo verificado correctamente");
            sesionActiva = true;
            return true;
        } else {
            intentosOTP--;
            System.out.println("Codigo incorrecto. Intentos restantes: " + intentosOTP);
            return false;
        }
    }

    @Override
    public int intentosRestantes() {
        return intentosOTP;
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
        String registro = "[" + new Date() + "] " +
                (exitoso ? "Login OTP exitoso" : "Login OTP fallido");
        historial.add(registro);

        System.out.println("[AUDIT] Intento " + (exitoso ? "exitoso" : "fallido")
                + ": " + usuario + " - " + new Date());
    }

    @Override
    public List<String> obtenerHistorial(String usuario) {
        return historial;
    }
}
