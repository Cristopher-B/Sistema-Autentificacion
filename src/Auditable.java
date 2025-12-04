import java.util.List;

interface Auditable {
    void registrarIntento(String usuario, boolean exitoso);
    List<String> obtenerHistorial(String usuario);
}