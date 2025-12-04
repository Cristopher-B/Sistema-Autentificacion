public interface MultiFactor {
    String generarCodigoVerficacion();
    boolean verificarCodigo(String codigo);
    int intentosRestantes();
}
