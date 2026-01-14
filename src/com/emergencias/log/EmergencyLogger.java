package com.emergencias.log;

import com.emergencias.model.EmergencyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmergencyLogger {

    private static final String LOG_FILE = "emergency_log.txt";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static void logEvent(EmergencyEvent event) {
        String timestamp = dtf.format(LocalDateTime.now());
        String logEntry = timestamp + " - " + event.getDescription() + " at " + event.getLocation() + "\n";

        // Usamos try-with-resources para asegurar que el archivo se cierre automáticamente
        try (FileWriter fw = new FileWriter(LOG_FILE, true); // 'true' para añadir al final del archivo
             BufferedWriter bw = new BufferedWriter(fw)) { // Usamos buffer para eficiencia

            bw.write(logEntry);
            System.out.println("✅ Evento registrado en " + LOG_FILE);

        } catch (IOException e) {
            // Manejo de error si no se puede escribir el archivo (ej. permisos, disco lleno)
            System.err.println("❌ ERROR al escribir en el archivo de log: " + e.getMessage());
        }
    }
}
