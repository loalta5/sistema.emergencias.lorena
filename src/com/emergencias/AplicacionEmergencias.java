package com.emergencias;

import com.emergencias.alert.AlertSender; // Importación necesaria
import com.emergencias.detector.EmergencyDetector; // Importación necesaria
import com.emergencias.model.EmergencyEvent;
import com.emergencias.model.UserData;

public class AplicacionEmergencias {

       public static void main(String[] args) {

        System.out.println("--- INICIANDO SISTEMA DE EMERGENCIAS ---");

        ConfiguracionSistema.cargarDatosUsuario(); 

            if (ConfiguracionSistema.getDatosUsuario() != null) {
            System.out.println("\n✅ Datos de Usuario Cargados con Éxito:");
            System.out.println(ConfiguracionSistema.getDatosUsuario().toString());

            EmergencyDetector detector = new EmergencyDetector();
            AlertSender sender = new AlertSender();

            EmergencyEvent event = detector.detectEvent();
            sender.sendAlert(event);
            
            crearYReportarEmergenciaEjemplo();

        } else {
            System.out.println("\n❌ ERROR CRÍTICO: No se pudo iniciar el sistema sin datos de usuario.");
        }

        System.out.println("\n--- FIN DE LA EJECUCIÓN ---");
    }

   
    public static void crearYReportarEmergenciaEjemplo() {
      
        EmergencyEvent miEmergencia = new EmergencyEvent(
                "Accidente de Tráfico",
                "Avenida Oscar Esplá, 50",
                ConfiguracionSistema.getDatosUsuario() 
        );

        System.out.println("\n--- 🚨 REPORTE DE EMERGENCIA CREADO ---");
        System.out.println(miEmergencia.toString());
    }

}
