interface MultiFactor {
    String generarCodigoVerificacion();
    boolean verificarCodigo(String codigo);
    int intentosRestantes();
}