package com.emergencias;

import com.emergencias.model.UserData;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ServicioEmergencias {

    /**
     * UT10: Persistencia de datos en formato TXT.
     * Guarda la información del usuario en un archivo local.
     */
    public void guardarDatosPaciente(UserData u) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_data.txt"))) {
            if (u != null) {
                writer.write(u.toString());
                System.out.println("✅ Datos del paciente guardados en user_data.txt");
            }
        } catch (IOException e) {
            System.err.println("❌ Error al guardar los datos: " + e.getMessage());
        }
    }

    /**
     * Simulación de la funcionalidad de Botón de Pánico.
     */
    public void botonPanico() {
        String ubicacionSimulada = "Lat: 38.267, Lon: -0.698 (Ubicación GPS)";
        System.out.println("🚨 !!! ALERTA ENVIADA !!!");
        System.out.println("📞 Llamando al 112...");
        System.out.println("📍 Enviando ubicación: " + ubicacionSimulada);
    }

    /**
     * UT10: Método para mostrar tutoriales de primeros auxilios.
     */
    public void mostrarTutoriales(int opcion) {
        System.out.println("\n--- 📚 GUÍA DE ACTUACIÓN RÁPIDA ---");
        switch (opcion) {
            case 1:
                System.out.println("🚑 RCP: 30 compresiones fuertes en el centro del pecho y 2 insuflaciones.");
                break;
            case 2:
                System.out.println("🔥 QUEMADURAS: Enfriar con agua corriente (no helada) durante 10-20 minutos.");
                break;
            case 3:
                System.out.println("🥨 ATRAGANTAMIENTO: Realizar la maniobra de Heimlich.");
                break;
            default:
                System.out.println("❌ Opción no válida. Volviendo al menú.");
                break;
        }
    }
}