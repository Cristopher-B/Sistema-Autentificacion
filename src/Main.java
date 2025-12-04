public class Main {
    public static void main(String[] args) {

        System.out.println("=== SISTEMA DE AUTENTICACIÓN ===");

        //Basica
        System.out.println("\n--- Autenticación Básica ---");
        AutenticacionBasica basic = new AutenticacionBasica("admin", "admin123");

        basic.autenticar("admin", "admin123");
        basic.autenticar("admin", "wrongpass");

        //OTP
        System.out.println("\n--- Autenticación OTP ---");
        AutenticacionOTP otp = new AutenticacionOTP("juan.perez", "secure456");

        if (otp.autenticar("juan.perez", "secure456")) {
            String codigo = otp.generarCodigoVerificacion();
            System.out.println("Código OTP generado: " + codigo);
            System.out.println("Ingresando código: " + codigo);

            otp.verificarCodigo(codigo);
        }

        //Biometrica
        System.out.println("\n--- Autenticación Biométrica ---");
        AutenticacionBiometrica bio = new AutenticacionBiometrica("maria.lopez", "HUELLA123", "ROSTRO_OK");

        System.out.println("Escaneando huella dactilar...");
        bio.autenticar("maria.lopez", "HUELLA123");

        // -------------------- HISTORIAL -----------------
        System.out.println("\n--- Historial de Usuario: admin ---");
        for (String h : basic.obtenerHistorial("admin")) {
            System.out.println(h);
        }

        // -------------------- BLOQUEO -------------------
        System.out.println("\n--- Prueba de Bloqueo ---");
        AutenticacionBasica bloqueable = new AutenticacionBasica("root", "1234");

        for (int i = 1; i <= 3; i++) {
            System.out.print("Intento " + i + ": ");
            bloqueable.autenticar("root", "malpass");
            System.out.println("Intentos restantes: " + bloqueable.getIntentosRestantes());
        }

        bloqueable.autenticar("root", "1234");
    }
}
