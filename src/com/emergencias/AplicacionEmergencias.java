package com.emergencias;

import com.emergencias.alert.AlertSender;
import com.emergencias.detector.EmergencyDetector;
import com.emergencias.model.EmergencyEvent;
import com.emergencias.model.UserData;
import com.emergencias.log.EmergencyLogger;
import models.CentroSalud;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AplicacionEmergencias {

    public static void main(String[] args) {

        System.out.println("--- INICIANDO SISTEMA DE EMERGENCIAS ---");

        try {
            // Carga de configuración inicial del sistema
            ConfiguracionSistema.cargarDatosUsuario();

            if (ConfiguracionSistema.getDatosUsuario() != null) {
                System.out.println("\n✅ Datos de Usuario Cargados con Éxito:");
                System.out.println(ConfiguracionSistema.getDatosUsuario().toString());

                // --- UT8/UT10: CARGA DINÁMICA DE CENTROS DE SALUD ---
                // Usamos un ArrayList (estructura dinámica) como pide la Unidad 8
                ArrayList<CentroSalud> listaCentros = cargarCentrosDesdeJSON();

                if (!listaCentros.isEmpty()) {
                    System.out.println("\n✅ Datos externos cargados (UT10):");
                    System.out.println("Se han encontrado " + listaCentros.size() + " centros de salud.");
                    // Ejemplo de acceso a la estructura dinámica
                    System.out.println("Centro de referencia: " + listaCentros.get(0).getNombre());
                }
                // ----------------------------------------------------

                EmergencyDetector detector = new EmergencyDetector();
                AlertSender sender = new AlertSender();

                try {
                    EmergencyEvent event = detector.detectEvent();
                    sender.sendAlert(event);

                    // Registro del evento
                    EmergencyLogger.logEvent(event);

                    crearYReportarEmergenciaEjemplo();
                } catch (Exception e) {
                    System.err.println("\n❌ ERROR al detectar o enviar la emergencia: " + e.getMessage());
                }

            } else {
                System.out.println("\n❌ ERROR CRÍTICO: No se pudo iniciar el sistema sin datos de usuario.");
            }

        } catch (Exception e) {
            System.err.println("\n❌ ERROR INESPERADO durante la carga del sistema: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n--- FIN DE LA EJECUCIÓN ---");
    }

    /**
     * Método que cumple con la UT10: Acceso a datos en ficheros JSON.
     * Utiliza la librería Gson para una lectura eficiente "en bloque".
     */
    public static ArrayList<CentroSalud> cargarCentrosDesdeJSON() {
        ArrayList<CentroSalud> centros = new ArrayList<>();
        Gson gson = new Gson();

        // El archivo centros.json debe estar en la raíz del proyecto
        try (FileReader reader = new FileReader("centros.json")) {
            // Definimos el tipo para el ArrayList parametrizado (operador Diamond)
            Type listType = new TypeToken<ArrayList<CentroSalud>>(){}.getType();
            centros = gson.fromJson(reader, listType);
        } catch (Exception e) {
            System.err.println("⚠️ Advertencia: No se pudo procesar centros.json. " + e.getMessage());
        }
        return centros;
    }

    public static void crearYReportarEmergenciaEjemplo() {
        UserData user = getDatosUsuario();

        EmergencyEvent miEmergencia = new EmergencyEvent(
                "Accidente de Tráfico",
                "Avenida Oscar Esplá, 50",
                user
        );

        System.out.println("\n--- 🚨 REPORTE DE EMERGENCIA CREADO ---");
        System.out.println(miEmergencia.toString());

        EmergencyLogger.logEvent(miEmergencia);
    }

    public static UserData getDatosUsuario() {
        return ConfiguracionSistema.getDatosUsuario();
    }
}