package com.emergencias;

import com.emergencias.alert.AlertSender;
import com.emergencias.detector.EmergencyDetector;
import com.emergencias.model.EmergencyEvent;
import com.emergencias.model.UserData;
import com.emergencias.log.EmergencyLogger; // Importación añadida para la nueva funcionalidad

public class AplicacionEmergencias {

    public static void main(String[] args) {

        System.out.println("--- INICIANDO SISTEMA DE EMERGENCIAS ---");

        try {
            // Potencial punto de fallo: cargar datos de un archivo, BD, etc.
            ConfiguracionSistema.cargarDatosUsuario();

            if (ConfiguracionSistema.getDatosUsuario() != null) {
                System.out.println("\n✅ Datos de Usuario Cargados con Éxito:");
                System.out.println(ConfiguracionSistema.getDatosUsuario().toString());

                EmergencyDetector detector = new EmergencyDetector();
                AlertSender sender = new AlertSender();

                // Estas operaciones también podrían fallar (ej. si no hay conexión de red)
                try {
                    EmergencyEvent event = detector.detectEvent();
                    sender.sendAlert(event);
                    
                    // --- NUEVA FUNCIONALIDAD AÑADIDA ---
                    EmergencyLogger.logEvent(event); 
                    // ------------------------------------

                    crearYReportarEmergenciaEjemplo();
                } catch (Exception e) {
                    System.err.println("\n❌ ERROR al detectar o enviar la emergencia: " + e.getMessage());
                    // Continúa la ejecución a pesar del error puntual
                }

            } else {
                // Este else-if ya maneja un fallo específico de carga
                System.out.println("\n❌ ERROR CRÍTICO: No se pudo iniciar el sistema sin datos de usuario.");
            }

        } catch (Exception e) {
            // Captura cualquier otro error inesperado durante la carga inicial
            System.err.println("\n❌ ERROR INESPERADO durante la carga del sistema: " + e.getMessage());
            e.printStackTrace(); // Opcional: imprime la traza completa del error para depuración
        }

        System.out.println("\n--- FIN DE LA EJECUCIÓN ---");
    }

    public static void crearYReportarEmergenciaEjemplo() {
        // Esta función asume que getDatosUsuario() devolverá algo válido ahora
        UserData user = getDatosUsuario();

        EmergencyEvent miEmergencia = new EmergencyEvent(
                "Accidente de Tráfico",
                "Avenida Oscar Esplá, 50",
                user
        );

        System.out.println("\n--- 🚨 REPORTE DE EMERGENCIA CREADO ---");
        System.out.println(miEmergencia.toString());
        
        // --- NUEVA FUNCIONALIDAD AÑADIDA ---
        EmergencyLogger.logEvent(miEmergencia);
        // ------------------------------------
    }

    public static UserData getDatosUsuario() {
        // Se asume que este método debe llamar al método real en ConfiguracionSistema
        // en lugar de lanzar una excepción sin implementar.
        return ConfiguracionSistema.getDatosUsuario();
    }
}

