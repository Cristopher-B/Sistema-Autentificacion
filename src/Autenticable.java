public interface Autenticable {
    boolean autenticar(String usuario, String credencial);
    void cerrarSesion();
    boolean sesionActiva();
}
