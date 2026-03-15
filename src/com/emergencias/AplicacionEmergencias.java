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
import java.util.Scanner;

public class AplicacionEmergencias {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO SISTEMA DE EMERGENCIAS ---");

        try {
            // 1. CARGA DE CONFIGURACIÓN
            ConfiguracionSistema.cargarDatosUsuario();

            if (ConfiguracionSistema.getDatosUsuario() != null) {
                System.out.println("\n✅ Datos de Usuario Cargados con Éxito:");
                System.out.println(ConfiguracionSistema.getDatosUsuario().toString());

                // 2. UT8/UT10: CARGA DINÁMICA DE CENTROS (JSON)
                ArrayList<CentroSalud> listaCentros = cargarCentrosDesdeJSON();

                if (!listaCentros.isEmpty()) {
                    System.out.println("\n✅ Datos externos cargados (UT10):");
                    System.out.println("Se han encontrado " + listaCentros.size() + " centros de salud.");
                    System.out.println("Centro de referencia: " + listaCentros.get(0).getNombre());
                }

                // 3. INTEGRACIÓN DEL SERVICIO Y BOTÓN DE AYUDA
                ServicioEmergencias servicio = new ServicioEmergencias();
                System.out.println("\n--- [SIMULANDO BOTÓN DE AYUDA ACTIVADO] ---");
                servicio.guardarDatosPaciente(ConfiguracionSistema.getDatosUsuario());
                servicio.botonPanico();

                // 4. DETECCIÓN DE EVENTO
                EmergencyDetector detector = new EmergencyDetector();
                AlertSender sender = new AlertSender();

                try {
                    EmergencyEvent event = detector.detectEvent();
                    sender.sendAlert(event);
                    EmergencyLogger.logEvent(event);

                    crearYReportarEmergenciaEjemplo();
                } catch (Exception e) {
                    System.err.println("\n❌ ERROR en el detector: " + e.getMessage());
                }

                // 5. NUEVA SECCIÓN: INTERACCIÓN CON EL USUARIO (TUTORIALES)
                Scanner teclado = new Scanner(System.in);
                System.out.println("\n📖 ¿Deseas consultar un tutorial de primeros auxilios? (S/N):");
                String respuesta = teclado.nextLine();

                if (respuesta.equalsIgnoreCase("S")) {
                    System.out.println("Elija una opción: 1-RCP, 2-Quemaduras, 3-Atragantamiento");
                    try {
                        int opcion = Integer.parseInt(teclado.nextLine());
                        servicio.mostrarTutoriales(opcion);
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Error: Debes introducir un número.");
                    }
                }

            } else {
                System.out.println("\n❌ ERROR CRÍTICO: No hay datos de usuario.");
            }

        } catch (Exception e) {
            System.err.println("\n❌ ERROR INESPERADO: " + e.getMessage());
        }

        System.out.println("\n--- FIN DE LA EJECUCIÓN ---");
    }

    public static ArrayList<CentroSalud> cargarCentrosDesdeJSON() {
        ArrayList<CentroSalud> centros = new ArrayList<>();
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("centros.json")) {
            Type listType = new TypeToken<ArrayList<CentroSalud>>(){}.getType();
            centros = gson.fromJson(reader, listType);
        } catch (Exception e) {
            System.err.println("⚠️ Advertencia: Error con centros.json. " + e.getMessage());
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