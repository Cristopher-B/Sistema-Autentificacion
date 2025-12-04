import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class AutenticacionBiometrica implements Autenticable, Auditable {

    private String usuario;
    private String huellaDactilar;
    private String reconocimientoFacial;
    private boolean sesionActiva;

    private List<String> historial = new ArrayList<>();

    public AutenticacionBiometrica(String usuario, String huella, String rostro) {
        this.usuario = usuario;
        this.huellaDactilar = huella;
        this.reconocimientoFacial = rostro;
    }

    @Override
    public boolean autenticar(String usuario, String credencial) {
        // credencial contiene la huella dactilar en este caso
        boolean verificado = huellaDactilar.equals(credencial);

        String nivelConfianza = verificado ? "ALTA" : "BAJA";

        registrarIntento(usuario + " (Biométrica/" + nivelConfianza + ")", verificado);

        if (verificado) {
            sesionActiva = true;
            System.out.println("Huella verificada - Confianza: " + nivelConfianza);
        } else {
            System.out.println("Huella no coincide");
        }

        return verificado;
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
        String registro = "[" + new Date() + "] AUTENTICACION " +
                (exitoso ? "EXITOSA" : "FALLIDA");
        historial.add(registro);

        System.out.println("[AUDIT] Intento " + (exitoso ? "exitoso" : "fallido")
                + ": " + usuario + " - " + new Date());
    }

    @Override
    public List<String> obtenerHistorial(String usuario) {
        return historial;
    }
}
